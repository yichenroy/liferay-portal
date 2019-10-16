import {
	Redirect,
	Route,
	HashRouter as Router,
	Switch
} from 'react-router-dom';
import { AppContext } from './AppContext';
import fetch from '../shared/rest/fetch';
import HeaderController from '../shared/components/header-controller/HeaderController';
import ProcessDashboard from './process-dashboard/ProcessDashboard';
import ProcessListCard from './process-list/ProcessListCard';
import React from 'react';
import SLAForm from './sla/SLAForm';
import SLAListCard from './sla/SLAListCard';

/**
 * @class
 * @classdesc Application starter.
 */
export default class AppComponent extends React.Component {
	constructor(props) {
		super(props);

		this.contextState = {
			client: fetch,
			companyId: props.companyId,
			defaultDelta: props.defaultDelta,
			deltas: props.deltas,
			maxPages: props.maxPages,
			namespace: props.namespace,
			setTitle: this.setTitle.bind(this)
		};

		this.state = {
			title: null
		};
	}

	setTitle(title) {
		this.setState({ title });
	}

	render() {
		const { namespace } = this.contextState;
		const { title } = this.state;
		const withParams = Component => ({ match: { params } }) => (
			<Component {...params} />
		);

		const { defaultDelta } = this.contextState;

		return (
			<Router>
				<AppContext.Provider value={this.contextState}>
					<HeaderController
						basePath="/processes"
						namespace={namespace}
						title={title}
					/>

					<div className="portal-workflow-metrics-app">
						<Switch>
							<Redirect
								exact
								from="/"
								to={`/processes/${defaultDelta}/1/${encodeURIComponent(
									'overdueInstanceCount:asc'
								)}`}
							/>

							<Route
								path="/processes/:pageSize/:page/:sort/:search?"
								render={withParams(ProcessListCard)}
							/>

							<Route
								path="/process-dashboard/:processId"
								render={withParams(ProcessDashboard)}
							/>

							<Route
								exact
								path="/slas/:processId/:pageSize?/:page?"
								render={withParams(SLAListCard)}
							/>

							<Route
								exact
								path="/sla/new/:processId"
								render={withParams(SLAForm)}
							/>

							<Route
								exact
								path="/sla/edit/:processId/:id"
								render={withParams(SLAForm)}
							/>
						</Switch>
					</div>
				</AppContext.Provider>
			</Router>
		);
	}
}