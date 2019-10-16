import EmptyContent from '../EmptyContent';
import LoadingState from '../loading/LoadingState';
import React from 'react';

/**
 * ListView.
 * @extends React.Component
 */
export default class ListView extends React.Component {
	render() {
		const {
			children,
			emptyMessageText,
			emptyTitleText,
			isFetching,
			isLoading,
			isSearching
		} = this.props;

		const emptyContentRender = secondaryRender =>
			isFetching ? (
				<EmptyContent message={emptyMessageText} title={emptyTitleText} />
			) : (
				secondaryRender
			);

		const emptyLoadingState = secondaryRender =>
			isLoading ? <LoadingState /> : secondaryRender;

		const emptySearchRender = secondaryRender =>
			isSearching ? (
				<EmptyContent message={emptyMessageText} type="not-found" />
			) : (
				secondaryRender
			);

		return (
			<div>
				{emptyLoadingState(emptySearchRender(emptyContentRender(children)))}
			</div>
		);
	}
}