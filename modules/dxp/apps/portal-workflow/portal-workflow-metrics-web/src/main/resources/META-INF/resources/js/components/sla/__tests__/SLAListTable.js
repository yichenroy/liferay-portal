import React from 'react';
import renderer from 'react-test-renderer';
import {MockRouter as Router} from '../../../test/mock/MockRouter';
import SLAListTable from '../SLAListTable';

test('Should render component', () => {
	const data = [
		{
			description: 'Total time to complete the request.',
			duration: '4d 6h 30min',
			name: 'Total resolution time'
		},
		{
			description: 'Total time to complete the request.',
			duration: '4d 6h 30min',
			name: 'Total resolution time'
		},
		{
			description: 'Total time to complete the request.',
			duration: '4d 6h 30min',
			name: 'Total resolution time'
		}
	];

	const component = renderer.create(
		<Router>
			<SLAListTable items={data} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});