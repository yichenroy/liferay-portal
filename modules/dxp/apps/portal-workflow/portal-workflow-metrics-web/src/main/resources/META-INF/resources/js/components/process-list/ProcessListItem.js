import Icon from '../../shared/components/Icon';
import Link from '../../shared/components/router/Link';
import React from 'react';
import {sub} from '../../shared/util/lang';

/**
 * @class
 * @memberof processes-list
 * */
export default class ProcessListItem extends React.Component {
	render() {
		const {
			id,
			instanceCount,
			ontimeInstanceCount,
			overdueInstanceCount,
			title
		} = this.props;

		let instanceMessage = Liferay.Language.get('x-items');

		if (instanceCount === 1) {
			instanceMessage = Liferay.Language.get('x-item');
		}

		return (
			<tr>
				<td className="table-cell-expand">
					<div className="table-list-title">
						<span className="text-truncate-inline">
							<span title={title}>{title}</span>
						</span>
					</div>
				</td>

				<td>{sub(instanceMessage, [instanceCount])}</td>

				<td>{ontimeInstanceCount}</td>

				<td>{overdueInstanceCount}</td>
				<td>
					<div className="dropdown dropdown-action">
						<a
							aria-expanded="false"
							aria-haspopup="true"
							className="component-action dropdown-toggle"
							data-toggle="dropdown"
							href="#1"
							id="dropdownAction1"
							role="button"
						>
							<Icon iconName="ellipsis-v" />
						</a>
						<ul
							aria-labelledby=""
							className="dropdown-menu dropdown-menu-right"
						>
							<li>
								<Link
									className="dropdown-item"
									query={{processId: id}}
									text={Liferay.Language.get('set-up-slas')}
									to="sla-list"
								/>
							</li>
						</ul>
					</div>
				</td>
			</tr>
		);
	}
}