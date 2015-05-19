$(function(){
	
	$("#text_revert_time_long").val(Date.parse(new Date()));
	$("#btn_revert_time").click(function() { 
        $.ajax( { 
            type : "GET", 
            url : "/v2/help/revert_time/time/"+$("#text_revert_time_long").val()
            	+"/format/"+$("#text_revert_time_format").val(), 
//            data : "name=zhangsan&age=20", 
//            dataType: "text", 
            success : function(msg) { 
            	$("#div_revert_time_string").html(msg); 
            } 
        }); 
    });
	
	$("#option_placehold_pic").change(function() {
	    $("#option_placehold_pic option:selected" ).each(function() {
	    	if($( this ).val() == "http://placehold.it/"){
	    		$("#div_placehold_pic_type").attr("hidden","true");
	    		$("#div_placehold_pic_pbg").attr("hidden","true");
	    		$("#div_placehold_pic_tbg").attr("hidden","true");
	    	}else if($( this ).val() == "http://lorempixel.com/"){
	    		$("#div_placehold_pic_type").removeAttr("hidden");
	    		$("#div_placehold_pic_pbg").attr("hidden","true");
	    		$("#div_placehold_pic_tbg").attr("hidden","true");
	    	}else if($( this ).val() == "http://fpoimg.com/"){
	    		$("#div_placehold_pic_type").attr("hidden","true");
	    		$("#div_placehold_pic_pbg").removeAttr("hidden");
	    		$("#div_placehold_pic_tbg").removeAttr("hidden");
	    	}
//	      str += $( this ).text() + " ";
	    });
	  });
//	  .trigger( "change" );
	
	
	$("#btn_placehold_pic").click(function() { 
		var option_placehold_pic_val = encodeURI($("#option_placehold_pic").val());
		var text_placehold_pic_width = encodeURI($("#text_placehold_pic_width").val());
		var text_placehold_pic_height = encodeURI($("#text_placehold_pic_height").val());
		var text_placehold_pic_text = encodeURI($("#text_placehold_pic_text").val());
		var option_placehold_pic_type = encodeURI($("#option_placehold_pic_type").val());
		//图片底色
		var text_placehold_pic_pbg = encodeURI($("#text_placehold_pic_pbg").val());
		//文字底色
		var text_placehold_pic_tbg = encodeURI($("#text_placehold_pic_tbg").val());
		
		if(option_placehold_pic_val == "http://placehold.it/"){
//			http://placehold.it/350x200&text=xxxxxx
			var src_val = "http://placehold.it/"+text_placehold_pic_width
			+"x"+text_placehold_pic_height+"&text="+text_placehold_pic_text;
			$("#img_placehold_pic").attr("src", src_val); 
		}else if(option_placehold_pic_val == "http://lorempixel.com/"){
//			http://lorempixel.com/1000/200/food/Dummy-Text/
			var src_val = "http://lorempixel.com/"+text_placehold_pic_width
			+"/"+text_placehold_pic_height;
			if(option_placehold_pic_type != ""){
				src_val = src_val 
					+ "/" + option_placehold_pic_type
					+ "/" + text_placehold_pic_text;
			}
			src_val = src_val + "?timestamp="+Date.parse(new Date());
			$("#img_placehold_pic").attr("src", src_val); 
		}else if(option_placehold_pic_val == "http://fpoimg.com/"){
//			http://fpoimg.com/350x200?text=Advertisement&bg_color=000000&text_color=ffffff
			var src_val = "http://fpoimg.com/"+text_placehold_pic_width
			+"x"+text_placehold_pic_height+"?";
			if(text_placehold_pic_text != ""){
				src_val = src_val + "text="+text_placehold_pic_text+"&";
			}
			if(text_placehold_pic_pbg != ""){
				src_val = src_val + "bg_color="+text_placehold_pic_pbg+"&";
			}
			if(text_placehold_pic_tbg != ""){
				src_val = src_val + "text_color="+text_placehold_pic_tbg+"&";
			}
			$("#img_placehold_pic").attr("src", src_val); 
		}
    });
});