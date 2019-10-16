import HeaderMenuBackItem from '../HeaderMenuBackItem';
import { MockRouter } from '../../../../test/mock/MockRouter';
import React from 'react';
import ReactDOM from 'react-dom';
import renderer from 'react-test-renderer';

beforeAll(() => {
	const vbody = document.createElement('div');

	vbody.innerHTML = '<div id="workflow"></div>';
	document.body.appendChild(vbody);

	ReactDOM.createPortal = jest.fn(element => {
		return element;
	});
});

test('Should render component on container', () => {
	const container = document.getElementById('workflow');

	const component = renderer.create(
		<MockRouter>
			<HeaderMenuBackItem
				basePath="/"
				container={container}
				location={{
					pathname: '/slas',
					search: 'backPath=%2Fprocesses'
				}}
			/>
		</MockRouter>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});