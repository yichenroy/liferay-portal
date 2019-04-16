AUI.add(
	'liferay-navigation-touch',
	function(A) {
		var Util = Liferay.Util;

		var SELECTOR_DRAG_HANDLE = '.drag-handle';

		var SELECTOR_LFR_NAV_SORTABLE = '.lfr-nav-sortable';

		var TPL_DRAG_HANDLE = Util.getLexiconIconTpl('bars', 'drag-handle');

		var afterMakeSortable = Liferay.Navigation.prototype._afterMakeSortable;

		A.mix(
			Liferay.Navigation.prototype,
			{
				_afterMakeSortable: function(sortable) {
					var instance = this;

					var navItems = instance.get('navBlock').all(instance._navItemSelector);

					var sortableDD = sortable.delegate.dd;

					instance._sortableDD = sortableDD;

					instance._createDragHandles(navItems);

					afterMakeSortable.call(instance, sortable);

					sortableDD.plug(
						A.Plugin.DDWinScroll,
						{
							horizontal: false,
							scrollDelay: 150,
							vertical: true
						}
					);

					sortableDD.on(
						['drag:drophit', 'drag:dropmiss'],
						function(event) {
							event.halt();
						}
					);

					instance._toggleDragConfig(sortableDD);

					A.getBody().delegate(
						'touchstart',
						function(event) {
							event.preventDefault();
						},
						SELECTOR_DRAG_HANDLE
					);

					A.on('windowresize', A.bind('_onWindowResize', instance));
				},

				_createDragHandles: function(items) {
					var instance = this;

					var layoutIds = instance.get('layoutIds');

					items.each(
						function(item, index) {
							var layoutConfig = layoutIds[index];

							if (layoutConfig && layoutConfig.sortable && layoutConfig.id == item.getData('layoutId')) {
								item.append(TPL_DRAG_HANDLE);
								item.addClass('lfr-nav-sortable');
							}
						}
					);
				},

				_onWindowResize: function() {
					var instance = this;

					if (!Util.isPhone() && Util.isTablet()) {
						instance._removeDragHandle();
					}
					else {
						var navItems = instance.get('navBlock').all(instance._navItemSelector);

						var navClass = navItems.hasClass('lfr-nav-sortable')

						if (navClass && !navClass[0]) {
							instance._createDragHandles(navItems);
						}

						instance._toggleDragConfig(instance._sortableDD);
					}
				},

				_removeDragHandle: function() {
					var instance = this;

					var items = instance.get('navBlock').all(instance._navItemSelector);

					items.each(
						function(item) {
							var svgElement = item._node.children[1];

							if (svgElement && svgElement.tagName == 'svg') {
								svgElement.remove();

								item.removeClass('lfr-nav-sortable');
							}
						}
					);
				},

				_toggleDragConfig: function(dd) {
					var instance = this;

					var tablet = Util.isTablet();

					var addHandleString = SELECTOR_DRAG_HANDLE;
					var removeHandleString = SELECTOR_LFR_NAV_SORTABLE;

					if (!tablet) {
						addHandleString = SELECTOR_LFR_NAV_SORTABLE;
						removeHandleString = SELECTOR_DRAG_HANDLE;
					}

					dd.addHandle(addHandleString);
					dd.removeHandle(removeHandleString);
				}
			},
			true
		);
	},
	'',
	{
		requires: ['dd-constrain', 'event-resize', 'event-touch', 'liferay-navigation']
	}
);