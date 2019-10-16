package com.liferay.commerce.product.type.virtual.web.internal.portlet;

import com.liferay.commerce.product.constants.CPPortletKeys;
import org.osgi.service.component.annotations.Component;

import javax.portlet.Portlet;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
    immediate = true,
    property = {
        "com.liferay.portlet.add-default-resource=true",
        "com.liferay.portlet.css-class-wrapper=portlet-commerce-product-type-virtual",
        "com.liferay.portlet.display-category=category.hidden",
        "com.liferay.portlet.layout-cacheable=true",
        "com.liferay.portlet.preferences-owned-by-group=true",
        "com.liferay.portlet.preferences-unique-per-layout=false",
        "com.liferay.portlet.private-request-attributes=false",
        "com.liferay.portlet.private-session-attributes=false",
        "com.liferay.portlet.render-weight=50",
        "com.liferay.portlet.scopeable=true",
        "javax.portlet.display-name=Virtual Products",
        "javax.portlet.expiration-cache=0",
        "javax.portlet.init-param.view-template=/view.jsp",
        "javax.portlet.name=" + CPPortletKeys.COMMERCE_PRODUCT_DEFINITION_VIRTUAL_SETTINGS,
        "javax.portlet.resource-bundle=content.Language",
        "javax.portlet.security-role-ref=power-user,user",
        "javax.portlet.supports.mime-type=text/html"
    },
    service = {CPDefinitionVirtualSettingsPortlet.class, Portlet.class}
)
public class CPDefinitionVirtualSettingsPortlet {
}
