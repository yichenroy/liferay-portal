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

import {
	addClickOutsideListener,
	removeClickOutsideListener,
	handleClickOutside
} from '../../../shared/components/filter/util/filterEvents.es';
import React, {useEffect, useRef} from 'react';
import Icon from '../../../shared/components/Icon.es';
import MaskedInput from 'react-text-mask';
import {sub} from '../../../shared/util/lang.es';
import {TimeRangeContext} from './store/TimeRangeStore.es';
import {useContext} from 'react';
import {useCustomTimeRange} from './store/CustomTimeRangeStore.es';

const CustomTimeRangeForm = ({filterKey}) => {
	const {
		applyCustomFilter,
		dateEnd,
		dateStart,
		errors = {},
		setDateEnd,
		setDateStart,
		validate
	} = useCustomTimeRange(filterKey);
	const {setShowCustomForm} = useContext(TimeRangeContext);
	const wrapperRef = useRef();

	const dateFormat = 'MM/DD/YYYY';

	const onBlur = ({target: {name, value}}) => {
		validate(name, value);
	};

	const onCancel = () => {
		setShowCustomForm(false);
	};

	const onChange = setter => ({target: {value}}) => {
		setter(value);
	};

	const onApply = () => {
		const errors = validate();

		if (!errors) {
			applyCustomFilter();
			setShowCustomForm(false);
		}
	};

	const dateMask = [/\d/, /\d/, '/', /\d/, /\d/, '/', /\d/, /\d/, /\d/, /\d/];

	useEffect(() => {
		const onClickOutside = handleClickOutside(() => {
			setShowCustomForm(false);
		}, wrapperRef.current);

		addClickOutsideListener(onClickOutside);

		return () => removeClickOutsideListener(onClickOutside);
	}, [setShowCustomForm]);

	return (
		<div className="custom-range-wrapper" ref={wrapperRef}>
			<form className="custom-range-form">
				<h4 className="mb-2">{Liferay.Language.get('custom-range')}</h4>

				<span className="form-text mb-3 text-semi-bold">
					{sub(Liferay.Language.get('default-date-format-is-x'), [
						dateFormat
					])}
				</span>

				<div className="form-group-autofit">
					<FormGroupItem error={errors['dateStart']}>
						<label htmlFor="dateStart">
							{Liferay.Language.get('from')}
						</label>

						<MaskedInput
							className="form-control"
							defaultValue={dateStart}
							mask={dateMask}
							name="dateStart"
							onBlur={onBlur}
							onChange={onChange(setDateStart)}
							placeholder={dateFormat}
						/>
					</FormGroupItem>

					<FormGroupItem error={errors['dateEnd']}>
						<label htmlFor="dateEnd">
							{Liferay.Language.get('to')}
						</label>

						<MaskedInput
							className="form-control"
							defaultValue={dateEnd}
							mask={dateMask}
							name="dateEnd"
							onBlur={onBlur}
							onChange={onChange(setDateEnd)}
							placeholder={dateFormat}
						/>
					</FormGroupItem>
				</div>
			</form>

			<div className="dropdown-divider" />

			<div className="custom-range-footer">
				<button className="btn btn-secondary" onMouseDown={onCancel}>
					{Liferay.Language.get('cancel')}
				</button>

				<button className="btn btn-primary ml-3" onClick={onApply}>
					{Liferay.Language.get('apply')}
				</button>
			</div>
		</div>
	);
};

const FormGroupItem = ({children, error}) => (
	<div className={`form-group-item ${error ? 'has-error' : ''}`}>
		<div className="input-group">
			<div className="input-group-item">{children}</div>
		</div>

		{error && (
			<div className="form-feedback-group">
				<div className="form-feedback-item">
					<span className="form-feedback-indicator mr-2">
						<Icon iconName="exclamation-full" />
					</span>

					<span className="text-semi-bold">{error}</span>
				</div>
			</div>
		)}
	</div>
);

export {CustomTimeRangeForm};
