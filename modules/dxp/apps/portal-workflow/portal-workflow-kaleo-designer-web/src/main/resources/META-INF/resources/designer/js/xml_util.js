AUI.add(
	'liferay-kaleo-designer-xml-util',
	function(A) {
		var Lang = A.Lang;
		var LString = Lang.String;

		var isNull = Lang.isNull;
		var isValue = Lang.isValue;

		var BUFFER_ATTR = [null, '="', null, '" '];

		var BUFFER_CLOSE_NODE = ['</', null, '>'];

		var BUFFER_OPEN_NODE = ['<', null, null, '>'];

		var STR_BLANK = '';

		var STR_CDATA_CLOSE = ']]>';

		var STR_CDATA_OPEN = '<![CDATA[';

		var STR_CHAR_CRLF = '\r\n';

		var STR_CHAR_TAB = '\t';

		var STR_DASH = '-';

		var STR_SPACE = ' ';

		var XMLUtil = {
			REGEX_TOKEN_1: /(>)(<)(\/*)/g,
			REGEX_TOKEN_2: /.+<\/\w[^>]*>$/,
			REGEX_TOKEN_3: /^<\/\w/,
			REGEX_TOKEN_4: /^<\w[^>]*[^\/]>.*$/,

			create: function(name, content, attrs) {
				var instance = this;

				var node = instance.createObj(name, attrs);

				return (
					node.open +
					(isValue(content) ? content : STR_BLANK) +
					node.close
				);
			},

			createObj: function(name, attrs) {
				var instance = this;

				var attrBuffer = [STR_SPACE];
				var normalizedName = LString.uncamelize(
					name,
					STR_DASH
				).toLowerCase();

				A.each(attrs, function(item, index, collection) {
					if (item !== undefined) {
						BUFFER_ATTR[0] = index;
						BUFFER_ATTR[2] = item;

						attrBuffer.push(BUFFER_ATTR.join(STR_BLANK));
					}
				});

				var attributes = Lang.trimRight(attrBuffer.join(STR_BLANK));

				BUFFER_CLOSE_NODE[1] = normalizedName;

				BUFFER_OPEN_NODE[1] = normalizedName;
				BUFFER_OPEN_NODE[2] = attributes;

				return {
					close: BUFFER_CLOSE_NODE.join(STR_BLANK),
					open: BUFFER_OPEN_NODE.join(STR_BLANK)
				};
			},

			format: function(xml) {
				var instance = this;

				var formatted = STR_BLANK;
				var inCDATA = false;
				var pad = 0;

				var lines = xml
					.replace(
						instance.REGEX_TOKEN_1,
						'$1' + STR_CHAR_CRLF + '$2$3'
					)
					.split(/\r?\n/g);

				lines.forEach(function(item, index, collection) {
					var indent = 0;

					if (!inCDATA) {
						if (item.match(instance.REGEX_TOKEN_2)) {
							indent = 0;
						} else if (item.match(instance.REGEX_TOKEN_3)) {
							if (pad !== 0) {
								pad -= 1;
							}
						} else if (item.match(instance.REGEX_TOKEN_4)) {
							indent = 1;
						}

						formatted += LString.repeat(STR_CHAR_TAB, pad);
					}

					formatted += item + STR_CHAR_CRLF;

					if (item.indexOf(STR_CDATA_OPEN) > -1) {
						inCDATA = true;
					} else if (item.indexOf(STR_CDATA_CLOSE) > -1) {
						inCDATA = false;
					}

					pad += indent;
				});

				return formatted;
			},

			validateDefinition: function(definition) {
				var doc = A.DataType.XML.parse(definition);

				var valid = true;

				if (
					isNull(doc) ||
					isNull(doc.documentElement) ||
					A.one(doc).one('parsererror')
				) {
					valid = false;
				}

				return valid;
			}
		};

		Liferay.XMLUtil = XMLUtil;
	},
	'',
	{
		requires: ['aui-base']
	}
);
