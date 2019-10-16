import InstanceListItem from './InstanceListItem';
import React from 'react';

export default class InstanceListTable extends React.Component {
	render() {
		const { items } = this.props;

		return (
			<div className="table-responsive">
				<table className="show-quick-actions-on-hover table table-fixed table-heading-nowrap table-hover table-list">
					<thead>
						<tr>
							<th
								className="text-center table-head-title"
								style={{ width: '10%' }}
							>
								{Liferay.Language.get('id')}
							</th>

							<th
								className="table-cell-expand table-head-title"
								style={{ width: '30%' }}
							>
								{Liferay.Language.get('item-subject')}
							</th>

							<th className="table-head-title" style={{ width: '25%' }}>
								{Liferay.Language.get('process-step')}
							</th>

							<th className="table-head-title" style={{ width: '20%' }}>
								{Liferay.Language.get('created-by')}
							</th>

							<th
								className="pr-4 table-head-title text-right"
								style={{ width: '15%' }}
							>
								{Liferay.Language.get('creation-date')}
							</th>
						</tr>
					</thead>

					<tbody>
						{items.map((item, index) => (
							<InstanceListItem {...item} key={index} />
						))}
					</tbody>
				</table>
			</div>
		);
	}
}