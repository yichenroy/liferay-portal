import { AppContext, AppStatus } from '../AppContext';
import { openErrorToast, openSuccessToast } from '../../shared/util/toast';
import { ChildLink } from '../../shared/components/router/routerWrapper';
import Icon from '../../shared/components/Icon';
import ListView from '../../shared/components/list/ListView';
import PaginationBar from '../../shared/components/pagination/PaginationBar';
import React from 'react';
import { REQUEST_ORIGIN_TYPE_FETCH } from './Constants';
import SLAConfirmDialog from './SLAConfirmDialog';
import SLAListCardContext from './SLAListCardContext';
import SLAListTable from './SLAListTable';

class SLAListCard extends React.Component {
	constructor(props) {
		super(props);

		this.requestOriginType = null;
		this.state = {
			items: [],
			itemToRemove: null,
			showConfirmDialog: false,
			showSLAsUpdatingAlert: false,
			totalCount: 0
		};

		this.slaContextState = {
			hideConfirmDialog: () => this.setConfirmDialogVisibility(null, false),
			removeItem: this.removeItem.bind(this),
			showConfirmDialog: (id, callback) =>
				this.setConfirmDialogVisibility(id, true, callback)
		};
	}

	componentWillMount() {
		this.context.setTitle(Liferay.Language.get('slas'));
		this.showStatusMessage();
	}

	componentWillReceiveProps(nextProps) {
		this.loadData(nextProps);
	}

	loadData(props = this.props) {
		const { page, pageSize, processId } = props;

		this.requestData({
			page,
			pageSize,
			processId
		}).then(({ items, totalCount }) =>
			this.setState({
				items,
				totalCount
			})
		);
	}

	removeItem(id) {
		const { client } = this.context;

		client
			.delete(`/slas/${id}`)
			.then(() => {
				this.loadData();
				openSuccessToast(Liferay.Language.get('sla-was-deleted'));
			})
			.catch(openErrorToast);

		this.setState({
			itemToRemove: null,
			showConfirmDialog: false
		});
	}

	setConfirmDialogVisibility(id, visible, callback) {
		this.setState(
			{
				itemToRemove: id,
				showConfirmDialog: visible
			},
			callback
		);
	}

	showStatusMessage() {
		const { status } = this.context;

		if (status === AppStatus.slaUpdated || status === AppStatus.slaSaved) {
			if (status === AppStatus.slaUpdated) {
				openSuccessToast(Liferay.Language.get('sla-was-updated'));
			}
			else {
				openSuccessToast(Liferay.Language.get('sla-was-saved'));
			}

			this.state.showSLAsUpdatingAlert = true;
			this.context.setStatus(null);
		}
	}

	/**
	 * @param {Object} configuration
	 * @param {number} configuration.page
	 * @param {number} configuration.pageSize
	 * @param {number} configuration.processId
	 */
	requestData({ page, pageSize, processId }) {
		const { client } = this.context;

		return client
			.get(`/processes/${processId}/slas?page=${page}&pageSize=${pageSize}`)
			.then(({ data }) => {
				this.requestOriginType = REQUEST_ORIGIN_TYPE_FETCH;

				return data;
			})
			.catch(openErrorToast);
	}

	render() {
		const { requestOriginType } = this;
		const {
			itemToRemove,
			items,
			showConfirmDialog,
			showSLAsUpdatingAlert,
			totalCount
		} = this.state;
		const { page, pageSize, processId } = this.props;

		const emptyMessageText = Liferay.Language.get(
			'sla-allows-to-define-and-measure-process-performance'
		);
		const emptyTitleText = Liferay.Language.get('no-slas-yet');

		const isFetching =
			requestOriginType === REQUEST_ORIGIN_TYPE_FETCH && totalCount === 0;
		const isLoading = !requestOriginType && totalCount === 0;

		return (
			<SLAListCardContext.Provider value={this.slaContextState}>
				<nav className="management-bar management-bar-light navbar navbar-expand-md">
					<div className="container-fluid container-fluid-max-xl">
						<ul className="navbar-nav autofit-row">
							<li className="nav-item autofit-col-expand autofit-float-end">
								<ChildLink
									className="btn btn-primary nav-btn nav-btn-monospaced navbar-breakpoint-down-d-none"
									to={`/sla/new/${processId}`}
								>
									<Icon iconName="plus" />
								</ChildLink>
							</li>
						</ul>
					</div>
				</nav>

				{showConfirmDialog && <SLAConfirmDialog itemToRemove={itemToRemove} />}

				<div className="lfr-search-container-wrapper container-fluid-1280">
					{showSLAsUpdatingAlert && (
						<div className="alert alert-dismissible alert-info" role="alert">
							<span className="alert-indicator">
								<Icon iconName="reload" />
							</span>

							<strong className="lead">{Liferay.Language.get('info')}</strong>

							<span>
								{`${Liferay.Language.get(
									'one-or-more-slas-are-being-updated'
								)} ${Liferay.Language.get(
									'there-may-be-a-delay-before-sla-changes-are-fully-propagated'
								)}`}
							</span>

							<button
								aria-label="Close"
								className="close"
								data-dismiss="alert"
								type="button"
							>
								<Icon iconName="times" />
							</button>
						</div>
					)}

					<ListView
						emptyMessageText={emptyMessageText}
						emptyTitleText={emptyTitleText}
						isFetching={isFetching}
						isLoading={isLoading}
					>
						<SLAListTable items={items} />

						<PaginationBar
							page={page}
							pageCount={items.length}
							pageSize={pageSize}
							totalCount={totalCount}
						/>
					</ListView>
				</div>
			</SLAListCardContext.Provider>
		);
	}
}

SLAListCard.contextType = AppContext;
export default SLAListCard;