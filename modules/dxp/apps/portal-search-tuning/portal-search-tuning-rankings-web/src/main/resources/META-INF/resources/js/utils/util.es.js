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

/**
 * Inserts an item into a list at the specified index.
 * @param {Array} list The list where the item will be inserted into.
 * @param {number} index The position where the item will be inserted.
 * @param {*} item The item that will be inserted.
 * @return {Array}
 */
export function insertAtIndex(list, index, item) {
	return [...list.slice(0, index), item, ...list.slice(index, list.length)];
}

/**
 * Checks if a value is an array.
 * @param {*} val The value to test.
 */
export function isArray(val) {
	return Object.prototype.toString.call(val) === '[object Array]';
}

/**
 * Checks if a value is null or undefined.
 * @param {*} val The variable to check.
 */
export function isNil(val) {
	return val == null;
}

/**
 * Checks if a value is null.
 * @param {*} val The value to check.
 */
export function isNull(val) {
	return val === null;
}

/**
 * Removes an item at the specified index.
 * @param {Array} list The list the where an item will be removed.
 * @param {number} index The position where the item will be removed.
 * @return {Array}
 */
export function removeAtIndex(list, index) {
	return list.filter((fItem, fIndex) => fIndex !== index);
}

/**
 * Moves an item in an array. Does not mutate the original array.
 * @param {Array} list The list to move an item.
 * @param {number} from The index of the item being moved.
 * @param {number} to The new index that the item will be moved to.
 */
export function move(list, from, to) {
	const listWithInserted = insertAtIndex(list, to, list[from]);

	return removeAtIndex(listWithInserted, from > to ? from + 1 : from);
}

/**
 * Removes an item from a list. If an array is given to the param 'toRemove',
 * each item in the array will be removed.
 * @param {Array} list The list of IDs to remove an item
 * @param {Array|number|string} toRemove The id or ids to remove.
 */
export function removeIdFromList(list = [], toRemove) {
	return list.filter(curId => {
		return isArray(toRemove)
			? !toRemove.includes(curId)
			: curId !== toRemove;
	});
}

/**
 * Converts an array of search result objects to a map of id to result object.
 * If an id already exists in the dataMap it will be ignored.
 * Use `updateDataMap` to update specific ids.
 * @param {Array} resultsData The items in search results.
 * @param {Object} initialMap The initial map data to prevent overwrites.
 * @return {Object} The new object that uses id as the key.
 */
export function resultsDataToMap(resultsData, initialMap = {}) {
	return resultsData.reduce((acc, cur) => {
		return acc[cur.id]
			? acc
			: {
					...acc,
					[cur.id]: cur
			  };
	}, initialMap);
}

/**
 * Toggles an item to be added or removed from a list. Used for maintaining
 * selected ids in a list.
 * @param {Array} list The list to toggle an item.
 * @param {number|string} id The value of the item to add or remove.
 */
export function toggleListItem(list, id) {
	return list.includes(id)
		? list.filter(value => value !== id)
		: [...list, id];
}

/**
 * Updates the specified ids in the dataMap with new properties.
 * @param {Object} dataMap The data map that will be updated.
 * @param {Array} ids The list of ids to update.
 * @param {Object} properties The item's new properties.
 * @returns {Object} The updated dataMap.
 */
export function updateDataMap(dataMap, ids, properties) {
	return ids.reduce((updatedDataMap, id) => {
		return {
			...updatedDataMap,
			[id]: {
				...dataMap[id],
				...properties
			}
		};
	}, dataMap);
}
