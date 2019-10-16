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

import {AppContext} from '../../AppContext.es';
import Icon from '../../../shared/components/Icon.es';
import ListView from '../../../shared/components/list/ListView.es';
import PaginationBar from '../../../shared/components/pagination/PaginationBar.es';
import Panel from '../../../shared/components/Panel.es';
import React from 'react';
import ReloadButton from '../../../shared/components/list/ReloadButton.es';
import Tooltip from '../../../shared/components/Tooltip.es';
import WorkloadByStepTable from './WorkloadByStepTable.es';

class WorkloadByStepCard extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			error: null,
			items: [],
			loading: false,
			totalCount: 0
		};
	}

	componentDidMount() {
		return this.loadData(this.props);
	}

	componentWillUnmount() {
		this.unmounted = true;
	}

	componentWillReceiveProps(nextProps) {
		return this.loadData(nextProps);
	}

	loadData(props = this.props) {
		const {loading} = this.state;

		if (loading) {
			return;
		}

		return this.requestData(props)
			.then(({items, totalCount}) =>
				this.setState({
					error: null,
					items,
					totalCount
				})
			)
			.catch(() =>
				this.setState({
					error: Liferay.Language.get(
						'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
					),
					loading: false
				})
			);
	}

	requestData({page, pageSize, processId, sort}) {
		const {client} = this.context;

		this.setState({
			loading: true
		});

		return client
			.get(
				`/processes/${processId}/tasks?page=${page}&pageSize=${pageSize}&sort=${sort}`
			)
			.then(({data}) => {
				this.setState({
					loading: false
				});

				return data;
			});
	}

	setState(newState) {
		if (!this.unmounted) {
			super.setState(newState);
		}
	}

	render() {
		const emptyMessageText = Liferay.Language.get(
			'there-are-no-pending-items-at-the-moment'
		);
		const {error, items = [], loading, totalCount} = this.state;
		const {page, pageSize, processId} = this.props;

		const fetching = !loading && !totalCount;

		return (
			<Panel className="container-fluid-1280 mt-4">
				<Panel.Header elementClasses={'dashboard-panel-header'}>
					<div>
						<span className={'mr-2'}>
							{Liferay.Language.get('workload-by-step')}
						</span>
						<Tooltip
							message={Liferay.Language.get(
								'workload-by-step-description'
							)}
							position="right"
							width="288"
						>
							<Icon iconName={'question-circle-full'} />
						</Tooltip>
					</div>
				</Panel.Header>
				<Panel.Body>
					<ListView
						className="border-0"
						emptyActionButton={<ReloadButton />}
						emptyMessageClassName="small"
						emptyMessageText={emptyMessageText}
						errorMessageText={error}
						fetching={fetching}
						hideAnimation
						loading={loading}
					>
						<WorkloadByStepTable
							items={items}
							processId={processId}
						/>

						<PaginationBar
							page={page}
							pageCount={items.length}
							pageSize={pageSize}
							totalCount={totalCount}
						/>
					</ListView>
				</Panel.Body>
			</Panel>
		);
	}
}

WorkloadByStepCard.contextType = AppContext;
export default WorkloadByStepCard;
