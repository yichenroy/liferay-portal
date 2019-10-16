import React from 'react';
import renderer from 'react-test-renderer';
import { MockRouter as Router } from '../../../test/mock/MockRouter';
import SLAListItem from '../SLAListItem';

test('Should render component', () => {
	const component = renderer.create(
		<Router>
			<SLAListItem
				id={1234}
				instancesCount="10"
				onTime="5"
				overdue="5"
				processName="Process test"
			/>
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component', () => {
	const component = mount(
		<Router>
			<SLAListItem
				instancesCount="10"
				onTime="5"
				overdue="5"
				processName="Process test"
			/>
		</Router>
	);

	const instance = component.find(SLAListItem).instance();

	instance.context = {
		showConfirmDialog: () => {}
	};

	instance.showConfirmDialog();

	expect(component).toMatchSnapshot();
});