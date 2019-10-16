/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.rankings.web.internal.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.tuning.rankings.web.internal.index.Ranking;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bryan Engler
 */
public class RankingEntryDisplayContextBuilder {

	public static final String INACTIVE_INDICATOR = "X";

	public RankingEntryDisplayContextBuilder(Ranking ranking) {
		_ranking = ranking;
	}

	public RankingEntryDisplayContext build() {
		RankingEntryDisplayContext rankingEntryDisplayContext =
			new RankingEntryDisplayContext();

		_setHiddenResultsCount(rankingEntryDisplayContext);
		_setIndex(rankingEntryDisplayContext);
		_setName(rankingEntryDisplayContext);
		_setPinnedResultsCount(rankingEntryDisplayContext);
		_setQueryStrings(rankingEntryDisplayContext);
		_setStatus(rankingEntryDisplayContext);
		_setUid(rankingEntryDisplayContext);

		return rankingEntryDisplayContext;
	}

	protected static String getSizeString(List<?> list) {
		return String.valueOf(list.size());
	}

	private List<String> _getQueryStrings() {
		List<String> queryStrings = new ArrayList<>(_ranking.getQueryStrings());

		if (_ranking.isInactive()) {
			queryStrings.add(INACTIVE_INDICATOR);
		}

		return queryStrings;
	}

	private int _getStatus() {
		if (_ranking.isInactive()) {
			return WorkflowConstants.STATUS_INACTIVE;
		}

		return WorkflowConstants.STATUS_APPROVED;
	}

	private void _setHiddenResultsCount(
		RankingEntryDisplayContext rankingEntryDisplayContext) {

		rankingEntryDisplayContext.setHiddenResultsCount(
			getSizeString(_ranking.getBlockIds()));
	}

	private void _setIndex(
		RankingEntryDisplayContext rankingEntryDisplayContext) {

		rankingEntryDisplayContext.setIndex(_ranking.getIndex());
	}

	private void _setName(
		RankingEntryDisplayContext rankingEntryDisplayContext) {

		rankingEntryDisplayContext.setKeywords(_ranking.getName());
	}

	private void _setPinnedResultsCount(
		RankingEntryDisplayContext rankingEntryDisplayContext) {

		rankingEntryDisplayContext.setPinnedResultsCount(
			getSizeString(_ranking.getPins()));
	}

	private void _setQueryStrings(
		RankingEntryDisplayContext rankingEntryDisplayContext) {

		rankingEntryDisplayContext.setAliases(
			StringUtil.merge(_getQueryStrings(), StringPool.COMMA_AND_SPACE));
	}

	private void _setStatus(
		RankingEntryDisplayContext rankingEntryDisplayContext) {

		rankingEntryDisplayContext.setStatus(String.valueOf(_getStatus()));
	}

	private void _setUid(
		RankingEntryDisplayContext rankingEntryDisplayContext) {

		rankingEntryDisplayContext.setUid(_ranking.getId());
	}

	private final Ranking _ranking;

}