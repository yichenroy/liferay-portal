import {buildFallbackItems} from '../../../shared/components/filter/util/filterEvents';
import moment from '../../../shared/util/moment';

const buildFallbackTimeRange = (fallbackKeys, queryDateEnd, queryDateStart) => {
	const fallbackItems = buildFallbackItems(fallbackKeys);

	if (fallbackItems && fallbackItems.length) {
		const customTimeRange = fallbackItems.filter(
			item => item.key === 'custom'
		)[0];

		if (customTimeRange) {
			customTimeRange.dateEnd = parseQueryDate(queryDateEnd, true);
			customTimeRange.dateStart = parseQueryDate(queryDateStart, true);
		}
	}

	return fallbackItems;
};

const formatDate = date => moment.utc(date).format('L');

const formatDescriptionDate = date => moment.utc(date).format('ll');

const formatQueryDate = date => parseDateMoment(date).format('YYYY-MM-DD');

const formatTimeRange = timeRange => {
	const {dateEnd, dateStart} = timeRange;

	if (!dateEnd && !dateStart) {
		return null;
	}

	const dateEndMoment = moment.utc(dateEnd);
	const dateStartMoment = moment.utc(dateStart);

	const {dateEndPattern, dateStartPattern} = getFormatPattern(
		dateEndMoment,
		dateStartMoment
	);

	return `${dateStartMoment.format(
		dateStartPattern
	)} - ${dateEndMoment.format(dateEndPattern)}`;
};

const getFormatPattern = (dateEndMoment, dateStartMoment) => {
	let dateStartPattern = Liferay.Language.get('mmm-dd-yyyy');

	if (dateEndMoment.diff(dateStartMoment, 'days') <= 1) {
		dateStartPattern = Liferay.Language.get('mmm-dd-hh-a');
	} else if (dateEndMoment.diff(dateStartMoment, 'years') < 1) {
		dateStartPattern = Liferay.Language.get('mmm-dd');
	}

	let dateEndPattern = dateStartPattern;
	const yesterday = moment.utc().subtract(1, 'd');

	if (
		dateEndMoment.date() === yesterday.date() &&
		dateStartMoment.date() === yesterday.date()
	) {
		dateEndPattern = Liferay.Language.get('mmm-dd-hh-mm-a');
	} else if (dateEndMoment.diff(dateStartMoment, 'days') > 90) {
		dateEndPattern = Liferay.Language.get('mmm-dd-yyyy');
	}

	return {
		dateEndPattern,
		dateStartPattern
	};
};

const parseDate = (date, isEndDate, format = 'L') => {
	let utcDate = parseDateMoment(date, format);

	if (isEndDate) {
		utcDate = utcDate
			.hours(23)
			.minutes(59)
			.seconds(59);
	} else {
		utcDate = utcDate.hours(0);
	}

	return utcDate.toDate();
};

const parseDateMoment = (date, format = 'L') =>
	moment.utc(moment(date, format, true));

const parseQueryDate = (date, isEndDate) =>
	parseDate(date, isEndDate, 'YYYY-MM-DD');

export {
	buildFallbackTimeRange,
	formatDate,
	formatDescriptionDate,
	formatQueryDate,
	formatTimeRange,
	parseDate,
	parseDateMoment,
	parseQueryDate
};
