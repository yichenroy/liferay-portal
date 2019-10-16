/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.jasper.internal.exporter;

import com.liferay.portal.reports.engine.ReportFormatExporter;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true, property = "reportFormat=xml",
	service = ReportFormatExporter.class
)
public class XmlReportFormatExporter extends BaseReportFormatExporter {

	@Override
	protected JRExporter getJRExporter() {
		return new JRXmlExporter();
	}

}