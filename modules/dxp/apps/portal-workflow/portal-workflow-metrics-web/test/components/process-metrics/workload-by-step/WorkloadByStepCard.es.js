/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import fetch from '../../../mock/fetch.es';
import fetchFailure from '../../../mock/fetchFailure.es';
import React from 'react';
import renderer from 'react-test-renderer';
import {MockRouter as Router} from '../../../mock/MockRouter.es';
import WorkloadByStepCard from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/workload-by-step/WorkloadByStepCard.es';

test('Should component receive props', () => {
	const data = {
		items: [
			{
				instanceCount: 1,
				name: 'Task Name',
				onTimeInstanceCount: 1,
				overdueInstanceCount: 0
			}
		],
		totalCount: 1
	};

	const component = mount(
		<Router client={fetch(data)}>
			<WorkloadByStepCard page={1} pageSize={10} processId={35315} />
		</Router>
	);

	const instance = component.find(WorkloadByStepCard).instance();

	instance.componentWillReceiveProps({
		...instance.props,
		page: 2,
		sort: 'name:desc'
	});

	expect(component).toMatchSnapshot();
});

test('Should component set error state after request fails', () => {
	const component = mount(
		<Router client={fetchFailure()}>
			<WorkloadByStepCard page={1} pageSize={10} processId={35315} />
		</Router>
	);

	const instance = component.find(WorkloadByStepCard).instance();

	instance.setState({
		loading: false
	});

	return instance.loadData().catch(() => {
		expect(instance.state.errors).toEqual(
			'There was a problem retrieving data. Please try reloading the page.'
		);
	});
});

test('Should component shows empty state when items is undefined', () => {
	const component = mount(
		<Router client={fetch({})}>
			<WorkloadByStepCard />
		</Router>
	);

	const instance = component.find(WorkloadByStepCard).instance();

	instance.setState({
		items: undefined
	});

	expect(component).toMatchSnapshot();
});

test('Should not reload component while loading state is true', () => {
	const component = mount(
		<Router client={fetch(null)}>
			<WorkloadByStepCard
				page={1}
				pageSize={10}
				processId={35315}
				sort="name:desc"
			/>
		</Router>
	);

	const instance = component.find(WorkloadByStepCard).instance();

	instance.state.loading = true;

	const result = instance.loadData({...instance.props, page: 2});

	expect(result).toEqual(undefined);
});

test('Should render component', () => {
	const data = {
		items: [
			{
				instanceCount: 1,
				name: 'Task Name',
				onTimeInstanceCount: 1,
				overdueInstanceCount: 0
			}
		],
		totalCount: 1
	};

	const component = mount(
		<Router client={fetch(data)}>
			<WorkloadByStepCard page={1} pageSize={10} processId={35315} />
		</Router>
	);

	const instance = component.find(WorkloadByStepCard).instance();

	instance.componentWillReceiveProps({page: 2});

	expect(component).toMatchSnapshot();
});

test('Should render component with empty data', () => {
	const data = {
		items: null,
		totalCount: 0
	};

	const component = renderer.create(
		<Router client={fetch(data)}>
			<WorkloadByStepCard processId={35315} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component with error state', () => {
	const component = renderer.create(
		<Router client={fetchFailure()}>
			<WorkloadByStepCard processId={35315} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});
