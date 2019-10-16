'use strict';

describe(
	'Liferay.KaleoDesignerXMLDefinition',
	function() {
		before(
			function(done) {
				AUI().use(
					'liferay-kaleo-designer-xml-definition',
					function(A) {
						done();
					}
				);
			}
		);

		describe(
			'unit',
			function() {
				describe(
					'.getDefinitionMetadata()',
					function() {
						it(
							'should have name',
							function(done) {
								Liferay.Test.loadResource('metadata-only.xml')
								.then(
									function(definition) {
										var xmlDefinition = new Liferay.KaleoDesignerXMLDefinition(
											{
												value: definition
											}
										);

										var metadata = xmlDefinition.getDefinitionMetadata();

										assert.equal(metadata.description, 'It only has metadata');
										assert.equal(metadata.name, 'Metadata only');
										assert.equal(metadata.version, 42);

										done();
									}
								);
							}
						);
					}
				);
			}
		);

		describe(
			'regression',
			function() {
				describe(
					'.forEachField()',
					function() {
						it(
							'should retrieve "receptionType" attribute value',
							function(done) {
								Liferay.Test.loadResource('recipients-with-reception-type-bcc.xml')
								.then(
									function(definition) {
										var xmlDefinition = new Liferay.KaleoDesignerXMLDefinition(
											{
												value: definition
											}
										);

										xmlDefinition.forEachField(
											function(tagName, fieldData) {
												var result = fieldData.results[0];

												var notification = result.notifications[0];

												var recipient = notification.recipients[0];

												assert.equal(recipient.receptionType, 'bcc');
											}
										);

										done();
									}
								);
							}
						);

						it(
							'should have "users" as recipient.',
							function(done) {
								Liferay.Test.loadResource('recipients-with-user.xml')
								.then(
									function(definition) {
										var xmlDefinition = new Liferay.KaleoDesignerXMLDefinition(
											{
												value: definition
											}
										);

										xmlDefinition.forEachField(
											function(tagName, fieldData) {
												var result = fieldData.results[0];

												var notification = result.notifications[0];

												var recipient = notification.recipients[0];

												assert.equal(recipient.user.length, 1);
											}
										);

										done();
									}
								);
							}
						);
					}
				);
			}
		);
	}
);