import {getCustomTimeRangeName, TimeRangeContext} from './store/TimeRangeStore';
import React, {useContext} from 'react';
import {CustomTimeRangeForm} from './CustomTimeRangeForm';
import Filter from '../../../shared/components/filter/Filter';

const TimeRangeFilter = ({
	filterKey = 'timeRange',
	hideControl = false,
	position = 'left',
	showFilterName = true
}) => {
	const {
		defaultTimeRange,
		getSelectedTimeRange,
		setShowCustomForm,
		showCustomForm,
		timeRanges
	} = useContext(TimeRangeContext);

	const isCustomFilter = currentFilter => currentFilter.key === 'custom';

	const onChangeFilter = selectedFilter => {
		const preventDefault = isCustomFilter(selectedFilter);

		return preventDefault;
	};

	const onClickFilter = clickedFilter => {
		if (isCustomFilter(clickedFilter)) {
			setShowCustomForm(true);

			if (clickedFilter.active) {
				document.dispatchEvent(new Event('mousedown'));
			}
		} else {
			setShowCustomForm(false);
		}

		return true;
	};

	const selectedTimeRange = getSelectedTimeRange();

	return (
		<Filter
			defaultItem={defaultTimeRange}
			filterKey={filterKey}
			hideControl={hideControl}
			items={[...timeRanges]}
			multiple={false}
			name={getFilterName(selectedTimeRange, showFilterName)}
			onChangeFilter={onChangeFilter}
			onClickFilter={onClickFilter}
			position={position}
		>
			{showCustomForm && <CustomTimeRangeForm filterKey={filterKey} />}
		</Filter>
	);
};

const getFilterName = (selectedTimeRange, showFilterName) => {
	if (showFilterName) {
		return Liferay.Language.get('completion-period');
	}

	if (!selectedTimeRange) {
		return '';
	}

	if (selectedTimeRange.key === 'custom') {
		return getCustomTimeRangeName(selectedTimeRange);
	}

	return selectedTimeRange.name;
};

export {TimeRangeFilter};
