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

import ClayButton from '@clayui/button';
import React, {Component} from 'react';
import {PropTypes} from 'prop-types';

const DEFAULT_DESCRIPTION = Liferay.Language.get(
	'sorry-there-are-no-results-found'
);

const DEFAULT_TITLE = Liferay.Language.get('no-results-found');

const STATE_IMAGES_PATH = '/o/admin-theme/images/states';

export const DISPLAY_STATES = {
	EMPTY: 'empty',
	SEARCH: 'search',
	SUCCESS: 'success'
};

class ClayEmptyState extends Component {
	static propTypes = {
		actionLabel: PropTypes.string,
		description: PropTypes.string,
		displayState: PropTypes.oneOf([
			DISPLAY_STATES.EMPTY,
			DISPLAY_STATES.SEARCH,
			DISPLAY_STATES.SUCCESS
		]),
		onClickAction: PropTypes.func,
		title: PropTypes.string
	};

	static defaultProps = {
		displayState: DISPLAY_STATES.SEARCH
	};

	render() {
		const {
			actionLabel,
			description,
			displayState,
			onClickAction,
			title
		} = this.props;

		return (
			<div className="empty-state-root">
				<img
					alt={Liferay.Language.get('empty-state-image')}
					className="empty-state-image"
					src={`${STATE_IMAGES_PATH}/${displayState}_state.gif`}
				/>

				<div className="empty-state-title">
					{title || DEFAULT_TITLE}
				</div>

				<div className="empty-state-description">
					{description || DEFAULT_DESCRIPTION}
				</div>

				{actionLabel && onClickAction && (
					<div className="empty-state-action">
						<ClayButton
							displayType="secondary"
							onClick={onClickAction}
						>
							{actionLabel}
						</ClayButton>
					</div>
				)}
			</div>
		);
	}
}

export default ClayEmptyState;
