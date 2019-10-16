/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.multi.factor.authentication.checker.setup.visitor;

import aQute.bnd.annotation.ProviderType;

import com.liferay.multi.factor.authentication.checker.visitor.PredicateCollectorMFACheckerVisitor;

/**
 * @author Carlos Sierra Andrés
 */
@ProviderType
public class WaitingForSetupMFACheckerVisitor
	extends PredicateCollectorMFACheckerVisitor {

	public WaitingForSetupMFACheckerVisitor(
		boolean onlyForcedSetup, long userId) {

		super(
			mfaChecker -> {
				if (!mfaChecker.accept(_supportsSetupVisitor)) {
					return false;
				}

				if (mfaChecker.accept(
						new IsUserSetupCompleteMFACheckerVisitor(userId))) {

					return false;
				}

				if (onlyForcedSetup &&
					!mfaChecker.accept(
						new ForceUserSetupMFACheckerVisitor(userId))) {

					return false;
				}

				return true;
			});
	}

	private static final SupportsSetupMFACheckerVisitor _supportsSetupVisitor =
		new SupportsSetupMFACheckerVisitor();

}