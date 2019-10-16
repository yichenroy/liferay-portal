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

const fetch = data => ({
	delete: () => Promise.resolve({data}),
	get: () => Promise.resolve({data}),
	post: () => Promise.resolve({data}),
	put: () => Promise.resolve({data})
});

export default fetch;
