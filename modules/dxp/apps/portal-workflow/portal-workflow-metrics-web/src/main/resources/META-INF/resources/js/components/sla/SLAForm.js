import {ALERT_MESSAGE, DAYS, DURATION, HOURS, NAME} from './Constants';
import {
	hasErrors,
	validateDays,
	validateDuration,
	validateHours,
	validateName
} from './util/slaFormUtil';
import autobind from 'autobind-decorator';
import createNumberMask from 'text-mask-addons/dist/createNumberMask';
import {durationAsMilliseconds} from '../../shared/util/duration';
import FieldError from './form/fieldError';
import FieldLabel from './form/fieldLabel';
import Icon from '../../shared/components/Icon';
import Link from '../../shared/components/router/Link';
import MaskedInput from 'react-text-mask';
import post from '../../shared/rest/post';
import React from 'react';
import redirect from '../../shared/components/router/redirect';

/**
 * SLA form component.
 * @extends React.Component
 */
export default class SLAForm extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			days: null,
			description: '',
			errors: {},
			hours: '',
			name: ''
		};
	}

	@autobind
	handleChange(
		{
			target: {name, value = ''}
		},
		callback
	) {
		this.setState({[name]: value}, callback);
	}

	@autobind
	handleSubmit() {
		const {days, description, hours, name} = this.state;
		const {errors} = this.state;

		errors[DAYS] = validateDays(days);
		errors[DURATION] = validateDuration(days, hours);
		errors[HOURS] = validateHours(hours);
		errors[NAME] = validateName(name);

		if (hasErrors(errors)) {
			errors[ALERT_MESSAGE] = Liferay.Language.get(
				'please-fill-in-the-required-fields'
			);

			this.setState({errors});
		}
		else {
			const {client, processId} = this.props;
			const duration = durationAsMilliseconds(days, hours);

			errors[ALERT_MESSAGE] = '';

			return post(client)(`/processes/${processId}/slas`, {
				description,
				duration,
				name,
				processId
			})
				.then(() => {
					redirect('sla-list', {processId});
				});
		}
	}

	@autobind
	onDaysBlurred() {
		const {days, errors} = this.state;

		errors[ALERT_MESSAGE] = '';
		errors[DAYS] = validateDays(days);

		this.setState({errors});
	}

	@autobind
	onDurationChanged() {
		const {days, errors, hours} = this.state;

		errors[ALERT_MESSAGE] = '';
		errors[DURATION] = validateDuration(days, hours);

		this.setState({errors});
	}

	@autobind
	onHoursBlurred() {
		const {errors, hours} = this.state;

		errors[ALERT_MESSAGE] = '';
		errors[HOURS] = validateHours(hours);

		this.setState({errors});
	}

	@autobind
	onNameChanged() {
		const {errors, name} = this.state;

		errors[ALERT_MESSAGE] = '';
		errors[NAME] = validateName(name);

		this.setState({errors});
	}

	render() {
		const daysMask = createNumberMask({
			includeThousandsSeparator: false,
			prefix: ''
		});
		const {errors} = this.state;
		const {processId} = this.props;
		const onChangeHandler = validationFunc => evt => {
			this.handleChange(evt, validationFunc);
		};

		return (
			<div>
				{(errors[ALERT_MESSAGE] || errors[DURATION]) && (
					<div className="alert-container">
						<div className="alert alert-danger" role="alert">
							<span className="alert-indicator">
								<Icon iconName="exclamation-full" />
							</span>

							<strong className="lead">{Liferay.Language.get('error')}</strong>

							<span>{errors[ALERT_MESSAGE] || errors[DURATION]}</span>
						</div>
					</div>
				)}
				<form className="sheet sheet-lg" role="form">
					<div className="sheet-header">
						<h2 className="sheet-title">
							{Liferay.Language.get('sla-definition')}
						</h2>
					</div>

					<div className="sheet-section">
						<div className="row">
							<div
								className={`form-group col col-sm-5 ${
									errors[NAME] ? 'has-error' : ''
								}`}
							>
								<FieldLabel
									fieldId="sla_name"
									required
									text={Liferay.Language.get('name')}
								/>

								<input
									autoFocus
									className="form-control"
									id="sla_name"
									name="name"
									onChange={onChangeHandler(this.onNameChanged)}
									type="text"
									value={this.state.name}
								/>

								{errors[NAME] && <FieldError error={errors[NAME]} />}
							</div>

							<div className="form-group col col-sm-7">
								<FieldLabel
									fieldId="sla_description"
									text={Liferay.Language.get('description')}
								/>

								<input
									className="form-control"
									id="sla_description"
									name="description"
									onChange={onChangeHandler()}
									type="text"
									value={this.state.description}
								/>
							</div>
						</div>

						<h3 className="sheet-subtitle">
							<FieldLabel
								fieldId="sla_description"
								required
								text={Liferay.Language.get('duration-time').toUpperCase()}
							/>
						</h3>

						<div className="sheet-text">
							{`${Liferay.Language.get(
								'define-a-duration-time-to-be-met'
							)} ${Liferay.Language.get(
								'enter-at-least-one-of-the-following-fields'
							)}`}
						</div>

						<div className={`row ${errors[DURATION] ? 'has-error' : ''}`}>
							<div
								className={`form-group col col-sm-5 ${
									errors[DAYS] ? 'has-error' : ''
								}`}
							>
								<FieldLabel
									fieldId="sla_duration_days"
									text={Liferay.Language.get('days')}
								/>

								<MaskedInput
									className="form-control"
									id="sla_duration_days"
									mask={daysMask}
									name={DAYS}
									onBlur={this.onDaysBlurred}
									onChange={onChangeHandler(this.onDurationChanged)}
									value={this.state.days}
								/>

								<div className="form-text">
									{Liferay.Language.get('enter-a-whole-number')}
								</div>

								{errors[DAYS] && <FieldError error={errors[DAYS]} />}
							</div>

							<div
								className={`form-group col col-sm-3 ${
									errors[HOURS] ? 'has-error' : ''
								}`}
							>
								<FieldLabel
									fieldId="sla_duration_hours"
									text={Liferay.Language.get('hours')}
								/>

								<MaskedInput
									className="form-control"
									id="sla_duration_hours"
									mask={[/\d/, /\d/, ':', /\d/, /\d/]}
									name={HOURS}
									onBlur={this.onHoursBlurred}
									onChange={onChangeHandler(this.onDurationChanged)}
									placeholder="00:00"
									value={this.state.hours}
								/>

								{errors[HOURS] && <FieldError error={errors[HOURS]} />}
							</div>
						</div>
					</div>

					<div className="sheet-footer sheet-footer-btn-block-sm-down">
						<div className="btn-group">
							<div className="btn-group-item">
								<button
									className="btn btn-primary"
									onClick={this.handleSubmit}
									type="button"
								>
									{Liferay.Language.get('save')}
								</button>
							</div>

							<div className="btn-group-item">
								<Link
									className="btn btn-secondary"
									query={{processId}}
									text={Liferay.Language.get('cancel')}
									to="sla-list"
								/>
							</div>
						</div>
					</div>
				</form>
			</div>
		);
	}
}