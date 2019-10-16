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

package com.liferay.multi.factor.authentication.spi.checker.visitor;

import com.liferay.multi.factor.authentication.spi.checker.MFAChecker;
import com.liferay.multi.factor.authentication.spi.checker.composite.MandatoryCompositeMFAChecker;
import com.liferay.multi.factor.authentication.spi.checker.composite.OptionalCompositeMFAChecker;

/**
 * @author Carlos Sierra Andrés
 */
public interface MFACheckerVisitor<T> {

	public T visit(MandatoryCompositeMFAChecker mandatoryMFAChecker);

	public T visit(MFAChecker mfaChecker);

	public T visit(OptionalCompositeMFAChecker optionalMFAChecker);

}