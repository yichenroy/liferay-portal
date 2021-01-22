package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.spira.SpiraTestCaseResultsUtil;

public class ConsistencyTestReportTester {

	public static void main(String[] args) {
		SpiraTestCaseResultsUtil.sendUpstreamTestSuiteSlackNotification(
			"master", "#t-forms-test-failures-test-channel", "forms");
	}

}