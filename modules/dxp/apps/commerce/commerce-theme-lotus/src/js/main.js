(function() {
	AUI().ready(
		function(A) {
			var WIN = A.getWin();

			var navbarEcommerce = A.one('.navbar-ecommerce');

			var animateNodes = A.all('.animate');

			var cartIcon = A.one('#cartIcon > a');

			var wishlistIcon = A.one('#wishlistIcon > a');

			var ScrollPos = function(e) {
				var ScrollPosY = (WIN.get('docScrollY'));

				if (ScrollPosY > 100) {
					if (!navbarEcommerce.hasClass('navbar-reduced')) {
						navbarEcommerce.addClass('navbar-reduced');
					}
				}
				else {
					navbarEcommerce.removeClass('navbar-reduced');
				}
			};

			A.on('mousewheel', ScrollPos);

			animateNodes.each(
				function(node) {
					node.getDOMNode().addEventListener('animationend', animationEnd);
				}
			);

			Liferay.after(
				'commerce:productAddedToCart',
				function(event) {
					Liferay.Portlet.refresh('#p_p_id_com_liferay_commerce_cart_content_web_internal_portlet_CommerceCartContentMiniPortlet_INSTANCE_commerceCartContentMiniPortlet_0_');

					if (cartIcon) {
						cartIcon.addClass('animBounce');

						var cartIconCount = A.one('#cartIcon > a .sticker');

						if (cartIconCount) {
							var cartItemCount = event.commerceCartItemsCount;

							cartIconCount.html(cartItemCount);
						}
						else {
							var bagFullIcon = A.one('#cartIcon > a .icon-bag-full');
							var bagIcon = A.one('#cartIcon > a .icon-bag');

							bagFullIcon.show();
							bagIcon.remove();

							cartIcon.append('<span class="sticker sticker-outside">' + event.commerceCartItemsCount + '</span>');
						}
					}
				}
			);

			Liferay.after(
				'commerce:productAddedToWishList',
				function(event) {
					Liferay.Portlet.refresh('#p_p_id_com_liferay_commerce_cart_content_web_internal_portlet_CommerceCartContentMiniPortlet_INSTANCE_commerceCartContentMiniPortlet_1_');

					if (wishlistIcon) {
						wishlistIcon.addClass('animBounce');

						var wishlistIconCount = A.one('#wishlistIcon > a .sticker');

						if (wishlistIconCount) {
							var cartItemCount = event.commerceCartItemsCount;

							wishlistIconCount.html(cartItemCount);
						}
						else {
							var bagFullIcon = A.one('#wishlistIcon > a .icon-heart-full');
							var bagIcon = A.one('#wishlistIcon > a .icon-heart');

							bagFullIcon.show();
							bagIcon.remove();

							wishlistIcon.append('<span class="sticker sticker-outside">' + event.commerceCartItemsCount + '</span>');
						}
					}
				}
			);

			function animationEnd(event) {
				A.one(event.target).removeClass('animBounce');
			}
		}
	);
})();