import autobind from 'autobind-decorator';
import DisplayResult from '../../shared/components/pagination/DisplayResult';
import EmptyContent from '../../shared/components/EmptyContent';
import Icon from '../../shared/components/Icon';
import Link from '../../shared/components/router/Link';
import openToast from 'frontend-js-web/liferay/toast/commands/OpenToast.es';
import PageSizeEntries from '../../shared/components/pagination/PageSizeEntries';
import Pagination from '../../shared/components/pagination/Pagination';
import React from 'react';
import Search from '../../shared/components/pagination/Search';
import SLAConfirmDialog from './SLAConfirmDialog';
import SLAListTable from './SLAListTable';

const REQUEST_ORIGIN_TYPE_FETCH = 'REQUEST_ORIGIN_TYPE_FETCH';
const REQUEST_ORIGIN_TYPE_SEARCH = 'REQUEST_ORIGIN_TYPE_SEARCH';

export default class SLAListCard extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			items: [],
			page: 1,
			pageSize: 20,
			requestOriginType: null,
			totalCount: 0
		};
	}

	componentDidMount() {
		const {processId} = this.props;
		const {page, pageSize} = this.state;

		this.requestData({
			page,
			pageSize,
			processId
		}).then(({items, totalCount}) =>
			this.setState({
				items,
				totalCount
			})
		);
	}

	componentDidUpdate() {
		const {itemRemoved} = this.props;

		if (itemRemoved) {
			this.removeItem();
		}
	}

	removeItem() {
		openToast({message: Liferay.Language.get('sla-deleted')});
	}

	/**
	 * @param {Object} configuration
	 * @param {number} configuration.page
	 * @param {number} configuration.pageSize
	 * @param {number} configuration.processId
	 * @param {string} configuration.title
	 */
	requestData({page, pageSize, processId, title}) {
		const {client} = this.props;

		this.state.requestOriginType =
			typeof title === 'string'
				? REQUEST_ORIGIN_TYPE_SEARCH
				: REQUEST_ORIGIN_TYPE_FETCH;

		return client(
			`/processes/${processId}/slas?page=${page}&pageSize=${pageSize}`
		);
	}

	@autobind
	onSearch(title) {
		const {pageSize} = this.state;
		const {processId} = this.props;
		const page = 1;

		this.requestData({page, pageSize, processId, title}).then(
			({items, totalCount}) => this.setState({items, page, totalCount})
		);
	}

	@autobind
	setPage({page, pageSize}) {
		const {processId} = this.props;

		return this.requestData({page, pageSize, processId}).then(
			({items, totalCount}) => this.setState({items, page, totalCount})
		);
	}

	@autobind
	setPageSize(pageSize) {
		const {processId} = this.props;
		const page = 1;

		return this.requestData({page, pageSize, processId}).then(
			({items, totalCount}) =>
				this.setState({items, page, pageSize, totalCount})
		);
	}

	render() {
		const {items, page, pageSize, requestOriginType, totalCount} = this.state;
		const pageSizes = [5, 10, 20, 30, 50, 75];
		const {itemToRemove, processId} = this.props;

		const emptySearchRender = secondaryRender =>
			requestOriginType === REQUEST_ORIGIN_TYPE_SEARCH && totalCount === 0 ? (
				<EmptyContent
					message={Liferay.Language.get('no-results-were-found')}
					type="not-found"
				/>
			) : (
				secondaryRender
			);

		const emptyContentRender = secondaryRender =>
			requestOriginType === REQUEST_ORIGIN_TYPE_FETCH && totalCount === 0 ? (
				<EmptyContent
					message={Liferay.Language.get(
						'once-there-are-active-processes-metrics-will-appear-here'
					)}
					title={Liferay.Language.get('no-current-slas')}
				/>
			) : (
				secondaryRender
			);

		const listRender = secondaryRender =>
			totalCount > 0 ? (
				<div>
					{itemToRemove && <SLAConfirmDialog item={itemToRemove} />}

					<SLAListTable sla={items} />

					{totalCount > pageSizes[0] && (
						<div className="pagination-bar">
							<PageSizeEntries
								onSelectPageSize={this.setPageSize}
								pageSizeEntries={pageSizes}
								selectedPageSize={pageSize}
							/>

							<DisplayResult
								page={page}
								pageCount={items.length}
								pageSize={pageSize}
								totalCount={totalCount}
							/>

							<Pagination
								onSelectPage={this.setPage}
								page={page}
								pageSize={pageSize}
								totalCount={totalCount}
							/>
						</div>
					)}
				</div>
			) : (
				secondaryRender
			);

		const loadingRender = () => (
			<span aria-hidden="true" className="loading-animation" />
		);

		return (
			<div>
				<nav className="management-bar management-bar-light navbar navbar-expand-md">
					<div className="container-fluid container-fluid-max-xl">
						<ul className="navbar-nav">
							<li className="nav-item">
								<div className="custom-control custom-checkbox">
									<label>
										<input className="custom-control-input" type="checkbox" />

										<span className="custom-control-label" />
									</label>
								</div>
							</li>
						</ul>

						<div className="navbar-form navbar-form-autofit">
							<Search
								disabled={
									requestOriginType === REQUEST_ORIGIN_TYPE_FETCH &&
									totalCount === 0
								}
								onSearch={this.onSearch}
							/>
						</div>

						<ul className="navbar-nav">
							<li className="nav-item">
								<Link
									className="btn btn-primary nav-btn nav-btn-monospaced navbar-breakpoint-down-d-none"
									query={{processId}}
									to="sla-form"
								>
									<Icon iconName="plus" />
								</Link>
							</li>
						</ul>
					</div>
				</nav>

				<div className="container-fluid-1280">
					<div className="alert alert-info" role="alert">
						<span className="alert-indicator">
							<Icon iconName="reload" />
						</span>

						<strong className="lead">{Liferay.Language.get('updating')}</strong>

						<span>
							{Liferay.Language.get(
								'instances-in-this-process-are-being-updated-according-to-sla-changes'
							)}
						</span>
					</div>

					{emptySearchRender(emptyContentRender(listRender(loadingRender())))}
				</div>
			</div>
		);
	}
}