const fetch = data => ({
	get: () => Promise.resolve({data}),
	post: () => Promise.resolve({data}),
	put: () => Promise.resolve({data})
});

export default fetch;