package com.shishuo.cms.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Herbert
 * 
 */
public class SSUtils {

	/**
	 * 把骆驼命名法的变量，变为大写字母变小写且之前加下划线
	 * 
	 * @param str
	 * @return
	 */
	public static String toUnderline(String str) {
		if(str == null){
			return null;
		}
		str = StringUtils.uncapitalize(str);
		char[] letters = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char letter : letters) {
			if (Character.isUpperCase(letter)) {
				sb.append("_" + letter + "");
			} else {
				sb.append(letter + "");
			}
		}
		return StringUtils.lowerCase(sb.toString());
	}

	public static void main(String[] args) {
		System.out.println(SSUtils.toUnderline("SSUtils"));
		System.out.println(SSUtils.toUnderline("FolderPathTag"));
		System.out.println(SSUtils.toUnderline("DDDDddAddadsfDDDDDDasdfD"));
		
		System.out.println(SSUtils.toUnderline(null));
		System.out.println(SSUtils.toUnderline(""));
		
		System.out.println(StringUtils.uncapitalize("abcDef"));
		System.out.println(StringUtils.uncapitalize("ABCDEF"));
	}
}
