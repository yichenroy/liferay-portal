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

package com.liferay.portal.workflow.metrics.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Histogram;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Metric;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.test.helper.WorkflowMetricsRESTTestHelper;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Inácio Nery
 */
@RunWith(Arquillian.class)
public class MetricResourceTest extends BaseMetricResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseTaskResourceTestCase.setUpClass();

		_workflowMetricsRESTTestHelper = new WorkflowMetricsRESTTestHelper(
			_queries, _searchEngineAdapter);
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_process = _workflowMetricsRESTTestHelper.addProcess(
			testGroup.getCompanyId());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		if (_process != null) {
			_workflowMetricsRESTTestHelper.deleteProcess(
				testGroup.getCompanyId(), _process);
		}

		_deleteInstances();
	}

	@Override
	@Test
	public void testGetProcessMetric() throws Exception {
		LocalDate localDate = LocalDate.now(ZoneId.of("GMT"));

		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		_testGetProcessMetric(
			nowLocalDateTime,
			LocalDateTime.of(localDate.minusDays(6), LocalTime.MIDNIGHT));

		_testGetProcessMetric(
			nowLocalDateTime,
			LocalDateTime.of(localDate.minusDays(29), LocalTime.MIDNIGHT));

		_testGetProcessMetric(
			nowLocalDateTime,
			LocalDateTime.of(localDate.minusDays(89), LocalTime.MIDNIGHT));

		_testGetProcessMetric(
			nowLocalDateTime,
			LocalDateTime.of(localDate.minusDays(179), LocalTime.MIDNIGHT));

		_testGetProcessMetric(
			nowLocalDateTime,
			LocalDateTime.of(localDate.minusDays(364), LocalTime.MIDNIGHT));

		_testGetProcessMetric(
			nowLocalDateTime, LocalDateTime.of(localDate, LocalTime.MIDNIGHT));

		_testGetProcessMetric(
			LocalDateTime.of(localDate.minusDays(1), LocalTime.MAX),
			LocalDateTime.of(localDate.minusDays(1), LocalTime.MIDNIGHT));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"histograms", "value"};
	}

	private void _assertMetric(
			Set<Histogram> histograms, LocalDateTime endLocalDateTime,
			LocalDateTime startLocalDateTime, String unit)
		throws Exception {

		_deleteInstances();

		_instances.add(
			_workflowMetricsRESTTestHelper.addInstance(
				testGroup.getCompanyId(), _toDate(startLocalDateTime),
				_process.getId()));

		assertEquals(
			_createMetric(
				histograms,
				ChronoUnit.DAYS.between(startLocalDateTime, endLocalDateTime),
				unit),
			metricResource.getProcessMetric(
				_process.getId(), _toDate(endLocalDateTime),
				_toDate(startLocalDateTime), unit));
	}

	private Histogram _createHistogram(String key, Double value) {
		Histogram histogram = new Histogram();

		histogram.setKey(key);
		histogram.setValue(value);

		return histogram;
	}

	private LocalDateTime _createLocalDateTime() {
		LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("GMT"));

		localDateTime = localDateTime.withMinute(0);
		localDateTime = localDateTime.withNano(0);

		return localDateTime.withSecond(0);
	}

	private Metric _createMetric(
			Set<Histogram> histograms, long timeRange, String unit)
		throws Exception {

		Metric metric = new Metric();

		metric.setHistograms(histograms.toArray(new Histogram[0]));
		metric.setValue(_getMetricValue(histograms, timeRange, unit));

		return metric;
	}

	private void _deleteInstances() throws Exception {
		for (Instance instance : _instances) {
			_workflowMetricsRESTTestHelper.deleteInstance(
				testGroup.getCompanyId(), instance);
		}
	}

	private double _getMetricValue(
		Collection<Histogram> histograms, long timeRange, String unit) {

		double timeAmount = histograms.size();

		if (Objects.equals(unit, Metric.Unit.MONTHS.getValue())) {
			timeAmount = timeRange / 30.0;
		}
		else if (Objects.equals(unit, Metric.Unit.WEEKS.getValue())) {
			timeAmount = timeRange / 7.0;
		}
		else if (Objects.equals(unit, Metric.Unit.YEARS.getValue())) {
			timeAmount = timeRange / 365.0;
		}

		return 1D / timeAmount;
	}

	private void _testGetProcessMetric(
			LocalDateTime endLocalDateTime, LocalDateTime startLocalDateTime)
		throws Exception {

		Set<Histogram> histograms = new LinkedHashSet<Histogram>() {
			{
				add(_createHistogram(startLocalDateTime.toString(), 1D));
			}
		};

		_assertMetric(
			new LinkedHashSet<Histogram>(histograms) {
				{
					LocalDateTime firstDayofMonthLocalDateTime =
						startLocalDateTime.withDayOfMonth(1);

					Period period = Period.between(
						firstDayofMonthLocalDateTime.toLocalDate(),
						endLocalDateTime.toLocalDate());

					for (int i = 1; i <= period.toTotalMonths(); i++) {
						LocalDateTime tempLocalDateTime =
							firstDayofMonthLocalDateTime.plusMonths(i);

						add(_createHistogram(tempLocalDateTime.toString(), 0D));
					}
				}
			},
			endLocalDateTime, startLocalDateTime, "Months");
		_assertMetric(
			new LinkedHashSet<Histogram>(histograms) {
				{
					LocalDateTime sundayLocalDateTime = startLocalDateTime.with(
						DayOfWeek.SUNDAY);

					Duration duration = Duration.between(
						sundayLocalDateTime, endLocalDateTime);

					for (int i = 0; i <= duration.toDays(); i = i + 7) {
						LocalDateTime tempLocalDateTime =
							sundayLocalDateTime.plusDays(i);

						if (tempLocalDateTime.equals(startLocalDateTime) ||
							tempLocalDateTime.isAfter(endLocalDateTime)) {

							continue;
						}

						add(_createHistogram(tempLocalDateTime.toString(), 0D));
					}
				}
			},
			endLocalDateTime, startLocalDateTime, "Weeks");
		_assertMetric(
			new LinkedHashSet<Histogram>(histograms) {
				{
					LocalDateTime firstDayofYearLocalDateTime =
						startLocalDateTime.withDayOfYear(1);

					Period period = Period.between(
						firstDayofYearLocalDateTime.toLocalDate(),
						endLocalDateTime.toLocalDate());

					for (int i = 1; i <= period.getYears(); i++) {
						LocalDateTime tempLocalDateTime =
							firstDayofYearLocalDateTime.plusYears(i);

						add(_createHistogram(tempLocalDateTime.toString(), 0D));
					}
				}
			},
			endLocalDateTime, startLocalDateTime, "Years");
		_assertMetric(
			new LinkedHashSet<Histogram>(histograms) {
				{
					Duration duration = Duration.between(
						startLocalDateTime, endLocalDateTime);

					for (int i = 1; i <= duration.toDays(); i++) {
						LocalDateTime tempLocalDateTime =
							startLocalDateTime.plusDays(i);

						add(_createHistogram(tempLocalDateTime.toString(), 0D));
					}
				}
			},
			endLocalDateTime, startLocalDateTime, "Days");
		_assertMetric(
			new LinkedHashSet<Histogram>(histograms) {
				{
					Duration duration = Duration.between(
						startLocalDateTime, endLocalDateTime);

					for (int i = 1; i <= duration.toHours(); i++) {
						LocalDateTime tempLocalDateTime =
							startLocalDateTime.plusHours(i);

						add(_createHistogram(tempLocalDateTime.toString(), 0D));
					}
				}
			},
			endLocalDateTime, startLocalDateTime, "Hours");
	}

	private Date _toDate(LocalDateTime localDateTime) {
		ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("GMT"));

		return Date.from(zonedDateTime.toInstant());
	}

	@Inject
	private static Queries _queries;

	@Inject(blocking = false, filter = "search.engine.impl=Elasticsearch")
	private static SearchEngineAdapter _searchEngineAdapter;

	private static WorkflowMetricsRESTTestHelper _workflowMetricsRESTTestHelper;

	private final List<Instance> _instances = new ArrayList<>();
	private Process _process;

}