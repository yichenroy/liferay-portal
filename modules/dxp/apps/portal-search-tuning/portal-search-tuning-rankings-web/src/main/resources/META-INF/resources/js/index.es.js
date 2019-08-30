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

import React from 'react';
import ResultsRankingForm from './components/ResultsRankingForm.es';
import ThemeContext from './ThemeContext.es';
import {render} from 'frontend-js-react-web';

function renderComponent({props, context}) {
	render(
		<ThemeContext.Provider value={context}>
			<div className="results-rankings-root">
				<ResultsRankingForm {...props} />
			</div>
		</ThemeContext.Provider>
	);
}

export default function(containerId, data) {
	render(renderComponent, data, containerId);
}
