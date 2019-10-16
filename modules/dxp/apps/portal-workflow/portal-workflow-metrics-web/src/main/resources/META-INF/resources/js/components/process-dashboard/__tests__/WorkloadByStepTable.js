import React from 'react';
import renderer from 'react-test-renderer';
import { MockRouter as Router } from '../../../test/mock/MockRouter';
import WorkloadByStepTable from '../WorkloadByStepTable';

test('Should display hyphen when the task has no count', () => {
	const data = [
		{
			name: 'Single Approver'
		}
	];

	const component = renderer.create(
		<Router client={fetch(data)}>
			<WorkloadByStepTable items={data} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component', () => {
	const data = [
		{
			instanceCount: 1,
			name: 'Single Approver',
			onTimeInstanceCount: 1,
			overdueInstanceCount: 0
		}
	];

	const component = renderer.create(
		<Router client={fetch(data)}>
			<WorkloadByStepTable items={data} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});