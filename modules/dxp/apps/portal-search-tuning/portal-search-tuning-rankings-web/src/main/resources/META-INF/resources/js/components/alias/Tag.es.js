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
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {Component} from 'react';

class Tag extends Component {
	static propTypes = {
		label: PropTypes.string,
		onClickDelete: PropTypes.func
	};

	state = {
		label: this.props.label
	};

	_handleDelete = event => {
		event.preventDefault();

		this.props.onClickDelete(this.props.label);
	};

	render() {
		const {label} = this.props;

		return (
			<span className="label label-dismissible label-lg label-secondary">
				<span className="label-item label-item-expand">{label}</span>

				<span className="label-item label-item-after">
					<ClayButton
						aria-label={Liferay.Language.get('close')}
						className="close"
						displayType="secondary"
						onClick={this._handleDelete}
					>
						<ClayIcon symbol="times" />
					</ClayButton>
				</span>
			</span>
		);
	}
}

export default Tag;
