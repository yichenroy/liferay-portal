import React, {Component} from 'react';
import PropTypes from 'prop-types';
import ClayToggle from '../shared/ClayToggle.es';
import CriteriaGroup from './CriteriaGroup.es';
import {insertAtIndex, removeAtIndex, replaceAtIndex} from '../../utils/utils.es';
import ClaySelect from '../shared/ClaySelect.es';

const CRITERIA_GROUP_SHAPE = {
	conjunctionName: PropTypes.string,
	groupId: PropTypes.string,
	items: PropTypes.array,
};

const CRITERION_SHAPE = {
	operatorName: PropTypes.string,
	propertyName: PropTypes.string,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.array]),
};

const propTypes = {
	criteria: PropTypes.shape(
		{
			conjunctionName: PropTypes.string,
			groupId: PropTypes.string,
			items: PropTypes.arrayOf(
				PropTypes.oneOfType(
					[
						PropTypes.shape(CRITERIA_GROUP_SHAPE),
						PropTypes.shape(CRITERION_SHAPE),
					]
				)
			),
		}
	),
	modelLabel: PropTypes.string,
	onChange: PropTypes.func,
	supportedConjunctions: PropTypes.arrayOf(
		PropTypes.shape(
			{
				label: PropTypes.string,
				name: PropTypes.string.isRequired,
			}
		)
	),
	supportedOperators: PropTypes.array,
	supportedProperties: PropTypes.arrayOf(
		PropTypes.shape(
			{
				entityUrl: PropTypes.string,
				label: PropTypes.string,
				name: PropTypes.string.isRequired,
				options: PropTypes.array,
				type: PropTypes.string.isRequired,
			}
		)
	).isRequired,
	supportedPropertyTypes: PropTypes.object,
};

/**
 *
 *
 * @class CriteriaBuilder
 * @extends {Component}
 */
class CriteriaBuilder extends Component {
	/**
	 *Creates an instance of CriteriaBuilder.
	 * @param {*} props
	 * @memberof CriteriaBuilder
	 */
	constructor(props) {
		super(props);
		this._handleToggleEdit = this._handleToggleEdit.bind(this);
		this._handleCriterionMove = this._handleCriterionMove.bind(this);
		this._handleCriteriaChange = this._handleCriteriaChange.bind(this);
		this._searchAndUpdateCriteria = this._searchAndUpdateCriteria.bind(this);
	}
	static defaultProps = {
		readOnly: false,
	};

	/**
	 * Cleans criteria items by performing the following:
	 * 1. Remove any groups with no items.
	 * 2. Flatten groups that directly contain a single group.
	 * 3. Flatten groups that contain a single criterion.
	 * @param {Array} criteriaItems A list of criterion and criteria groups
	 * @param {boolean} root True if the criteriaItems are from the root group.
	 * to clean.
	 * @return {*}
	 */
	_cleanCriteriaMapItems(criteriaItems, root) {
		const criteria = criteriaItems
			.filter(
				({items}) => {
					return items ? items.length : true;
				}
			)
			.map(
				item => {
					let cleanedItem = item;

					if (item.items) {
						if (item.items.length === 1) {
							const soloItem = item.items[0];

							if (soloItem.items) {
								cleanedItem = {
									conjunctionName: soloItem.conjunctionName,
									groupId: soloItem.groupId,
									items: this._cleanCriteriaMapItems(soloItem.items),
								};
							}
							else {
								cleanedItem = root ? item : soloItem;
							}
						}
						else {
							cleanedItem = {
								...item,
								items: this._cleanCriteriaMapItems(item.items),
							};
						}
					}

					return cleanedItem;
				}
			);

		return criteria;
	}

	/**
	 * Switches the edit state between true and false.
	 */
	_handleToggleEdit() {
		const {id, editing} = this.props;

		this.props.onEditionToggle && this.props.onEditionToggle(id, editing);
	}

	/**
	 * Cleans and updates the criteria with the newer criteria.
	 * @param {Object} newCriteria The criteria with the most recent changes.
	 */
	_handleCriteriaChange(newCriteria) {
		const items = this._cleanCriteriaMapItems([newCriteria], true);

		this.props.onChange(items[items.length - 1], this.props.id);
	}

	/**
	 * Moves the criterion to the specified index by removing and adding, and
	 * updates the criteria.
	 * @param {string} startGroupId Group ID of the item to remove.
	 * @param {number} startIndex Index in the group to remove.
	 * @param {string} destGroupId Group ID of the item to add.
	 * @param {number} destIndex Index in the group where the criterion will
	 * be added.
	 * @param {object} criterion The criterion that is being moved.
	 * @param {boolean} replace True if the destIndex should replace rather than
	 * insert.
	 */
	_handleCriterionMove(...args) {
		const newCriteria = this._searchAndUpdateCriteria(
			this.props.criteria,
			...args
		);

		this._handleCriteriaChange(newCriteria);
	}

	/**
	 * Checks if an item is a group item by checking if it contains an items
	 * property with at least 1 item.
	 * @param {object} item The criteria item to check.
	 * @return {number} True if the item is a group.
	 */
	_isGroupItem(item) {
		return item.items && item.items.length;
	}

	/**
	 * Searches through the criteria object and adds or replaces and removes
	 * the criterion at their respective specified index. insertAtIndex must
	 * come before removeAtIndex since the startIndex is incremented by 1
	 * when the destination index comes before the start index in the same
	 * group. The startIndex is not incremented if a replace is occurring.
	 * This is used for moving a criterion between groups.
	 * @param {object} criteria The criteria object to update.
	 * @param {string} startGroupId Group ID of the item to remove.
	 * @param {number} startIndex Index in the group to remove.
	 * @param {string} destGroupId Group ID of the item to add.
	 * @param {number} destIndex Index in the group where the criterion will
	 * be added.
	 * @param {object} addCriterion The criterion that is being moved.
	 * @param {boolean} replace True if the destIndex should replace rather than
	 * insert.
	 * @return {*}
	 */
	_searchAndUpdateCriteria(
		criteria,
		startGroupId,
		startIndex,
		destGroupId,
		destIndex,
		addCriterion,
		replace
	) {
		let updatedCriteriaItems = criteria.items;

		if (criteria.groupId === destGroupId) {
			updatedCriteriaItems = replace ?
				replaceAtIndex(
					addCriterion,
					updatedCriteriaItems,
					destIndex
				) :
				insertAtIndex(
					addCriterion,
					updatedCriteriaItems,
					destIndex
				);
		}

		if (criteria.groupId === startGroupId) {
			updatedCriteriaItems = removeAtIndex(
				updatedCriteriaItems,
				(destGroupId === startGroupId) &&
				(destIndex < startIndex) &&
				!replace ?
					startIndex + 1 :
					startIndex
			);
		}

		return {
			...criteria,
			items: updatedCriteriaItems.map(
				item => {
					return this._isGroupItem(item) ?
						this._searchAndUpdateCriteria(
							item,
							startGroupId,
							startIndex,
							destGroupId,
							destIndex,
							addCriterion,
							replace
						) :
						item;
				}
			),
		};
	}

	/**
	 *
	 *
	 * @return {*}
	 * @memberof CriteriaBuilder
	 */
	render() {
		const {
			criteria,
			modelLabel,
			supportedConjunctions,
			supportedOperators,
			supportedProperties,
			supportedPropertyTypes,
			editing,
			propertyKey,
		} = this.props;

		return (
			<div className="sheet sheet-lg">
				<div className="criteria-builder-toolbar">
					<ClaySelect
						className={`mw15`}
						options={this.props.supportedPropertyGroups.map(p => ({
							label: p.label,
							value: p.value,
						}))}
						selected={propertyKey}
						onChange={(e) =>this.props.onPropertyGroupSelection(e.target.value, this.props.id)}
					></ClaySelect>
					<ClayToggle
						checked={editing}
						iconOff="pencil"
						iconOn="pencil"
						onChange={this._handleToggleEdit}
					/>
				</div>

				<CriteriaGroup
					criteria={criteria}
					editing={editing}
					groupId={criteria && criteria.groupId}
					modelLabel={modelLabel}
					propertyKey={propertyKey}
					onChange={this._handleCriteriaChange}
					onMove={this._handleCriterionMove}
					root
					supportedConjunctions={supportedConjunctions}
					supportedOperators={supportedOperators}
					supportedProperties={supportedProperties}
					supportedPropertyTypes={supportedPropertyTypes}
				/>
			</div>
		);
	}
}

CriteriaBuilder.propTypes = propTypes;

export default CriteriaBuilder;
