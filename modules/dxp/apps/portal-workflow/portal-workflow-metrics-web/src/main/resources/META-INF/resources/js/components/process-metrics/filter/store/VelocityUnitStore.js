import React, {useEffect, useState, useContext, createContext} from 'react';
import moment from 'moment';
import {TimeRangeContext} from './TimeRangeStore';

const useVelocityUnit = velocityUnitKeys => {
	const {getSelectedTimeRange} = useContext(TimeRangeContext);
	const [velocityUnits, setVelocityUnits] = useState([]);

	const timeRange = getSelectedTimeRange();

	const fetchData = timeRange => {
		const {dateEnd, dateStart} = timeRange;

		const daysDiff = moment
			.utc(dateEnd)
			.diff(moment.utc(dateStart), 'days');

		const velocityUnits = (
			Object.keys(velocityUnitsMap)
				.filter(key => daysDiff < key)
				.map(key => velocityUnitsMap[key])[0] || [asDefault(yearsUnit)]
		).map(unit => ({
			...unit,
			active: velocityUnitKeys.includes(unit.key)
		}));

		setVelocityUnits(velocityUnits);
	};

	const defaultVelocityUnit = getDefaultVelocityUnit(velocityUnits);

	const getSelectedVelocityUnit = () => {
		if (!velocityUnits || !velocityUnits.length) {
			return null;
		}

		const selectedVelocityUnits = velocityUnits.filter(item => item.active);

		return selectedVelocityUnits.length ? selectedVelocityUnits[0] : null;
	};

	useEffect(() => {
		if (timeRange) {
			fetchData(timeRange);
		}
	}, [timeRange]);

	return {
		defaultVelocityUnit,
		getSelectedVelocityUnit,
		velocityUnits
	};
};

const asDefault = velocityUnit => {
	return {
		...velocityUnit,
		active: true,
		defaultVelocityUnit: true
	};
};

const getDefaultVelocityUnit = velocityUnits => {
	const defaultVelocityUnits = velocityUnits.filter(
		velocityUnit => velocityUnit.defaultVelocityUnit
	);

	return defaultVelocityUnits.length ? defaultVelocityUnits[0] : null;
};

const daysUnit = {
	key: 'Days',
	name: Liferay.Language.get('inst-day')
};

const hoursUnit = {
	key: 'Hours',
	name: Liferay.Language.get('inst-hour')
};

const monthsUnit = {
	key: 'Months',
	name: Liferay.Language.get('inst-month')
};

const weeksUnit = {
	key: 'Weeks',
	name: Liferay.Language.get('inst-week')
};

const yearsUnit = {
	key: 'Years',
	name: Liferay.Language.get('inst-year')
};

const velocityUnitsMap = {
	1: [asDefault(hoursUnit)],
	7: [asDefault(daysUnit)],
	30: [asDefault(daysUnit), weeksUnit],
	90: [daysUnit, asDefault(weeksUnit), monthsUnit],
	180: [weeksUnit, asDefault(monthsUnit)],
	366: [weeksUnit, asDefault(monthsUnit)],
	730: [asDefault(monthsUnit), yearsUnit]
};

const VelocityUnitContext = createContext(null);

const VelocityUnitProvider = ({children, unitKeys}) => {
	return (
		<VelocityUnitContext.Provider value={useVelocityUnit(unitKeys)}>
			{children}
		</VelocityUnitContext.Provider>
	);
};

export {VelocityUnitContext, VelocityUnitProvider, useVelocityUnit};
