	<div class="col-sm-3 col-sm-offset-1 blog-sidebar">
				<!--
				<div class="sidebar-module sidebar-module-inset">
					<h4>本站介绍</h4>
					<p>
						师说CMS是用Java开发的内容管理系统，目前是开源中国比较活跃的开源项目之一。师说CMS采用目前最流行的Maven项目架构，设计简单易懂，是一个轻量级的Java CMS。
						Spring MVC为表现层，Spring为业务层，MyBatis为数据层。项目大量使用注解，代码注释清晰，文档齐全，是学习和二次开发的首选。 
					</p>
				</div>
				-->
				<#if folder?? && folder.path??>
					<#if folder.path?contains("#") >
						<#assign root_parent_id=folder.path?substring(0, folder.path?index_of("#"))/>
					<#else>
						<#assign root_parent_id=folder.path/>
					</#if>
					<div class="sidebar-module sidebar-module-inset">
					<h4>
						<a href="<@shishuo_folder_url_tag folderId=root_parent_id/>">
							<@shishuo_folder_tag folderId=root_parent_id>
								${tag_folder.name}
							</@shishuo_folder_tag>
						</a></h4>
					<ol class="list-unstyled">
						<@shishuo_folder_list_tag folderId= root_parent_id>
		                		<#list tag_folder_list as tag_folder>
									<li>
										<a href="<@shishuo_folder_url_tag folderId=tag_folder.folderId/>">${tag_folder.name}</a>
										<@shishuo_folder_list_tag folderId= tag_folder.folderId>
					                		<#list tag_folder_list as tag_sub_folder>
												<li>
													&nbsp;&nbsp;&nbsp;&nbsp;<a href="<@shishuo_folder_url_tag folderId=tag_sub_folder.folderId/>">${tag_sub_folder.name}</a>
												</li>
											</#list>
				               			</@shishuo_folder_list_tag>
									</li>
								</#list>
	               		</@shishuo_folder_list_tag>
					</ol>
				</div>
				</#if>
				
			</div>