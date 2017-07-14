<!DOCTYPE html>

<#include init />

<html class="${root_css_class}" dir="<@liferay.language key="lang.dir" />" lang="${w3c_language_id}">
	<head>
		<title>${the_title} - ${company_name}</title>

		<meta content="initial-scale=1.0, width=device-width" name="viewport" />

		<@liferay_util["include"] page=top_head_include />
	</head>

	<body class="${css_class}">
		<@liferay_ui["quick-access"] contentId="#main-content" />

		<@liferay_util["include"] page=body_top_include />

		<@liferay.control_menu />

		<div id="wrapper">
			<header id="banner" role="banner">
				<div class="container-fluid-1280">
					<nav class="">
						<div class="">
							<a class="${logo_css_class}" href="${site_default_url}" rel="home" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
								<img alt="${logo_description}" height="${company_logo_height}" src="${site_logo}" width="${company_logo_width}" />
							</a>

							<div class="commerce-navigation">
								<@commerce_category_navigation_menu default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />
							</div>
						</div>
					</nav>
				</div>
			</header>

			<main id="content" role="main">
				<h1 class="hide-accessible">${the_title}</h1>

				<#if selectable>
					<@liferay_util["include"] page=content_include />
				<#else>
					${portletDisplay.recycle()}

					${portletDisplay.setTitle(the_title)}

					<@liferay_theme["wrap-portlet"] page="portlet.ftl">
						<@liferay_util["include"] page=content_include />
					</@>
				</#if>
			</main>

			<footer id="footer" role="contentinfo">
				<div class="container-fluid-1280">
					<#if has_navigation>
						<#include "${full_templates_path}/footer_navigation.ftl" />
					</#if>

					<div class="col-md-4 col-md-offset-2">
						<div id="foocont">
							<#if fullScreenNavigation>
								<@liferay_ui["asset-display"]
									className="com.liferay.journal.model.JournalArticle"
									classPK=footer_content_id
								/>
							</#if>
						</div>

						<p id="copyright">
							<small><@liferay.language key="powered-by" /> <a href="http://www.liferay.com" rel="external">Liferay</a></small>
						</p>
					</div>
				</div>
			</footer>
		</div>

		<@liferay_util["include"] page=body_bottom_include />

		<@liferay_util["include"] page=bottom_include />
	</body>
</html>