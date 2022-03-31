/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.sessionservice;

import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.set.basis.PlanProContextFunction;
import org.eclipse.set.core.services.font.FontService;
import org.osgi.service.component.annotations.Component;

/**
 * Create and publish {@link FontService}.
 * 
 * @author Stuecker
 *
 */
@Component(service = IContextFunction.class, property = "service.context.key:String=org.eclipse.set.core.services.font.FontService")
public class SetFontServiceContextFunction
		extends PlanProContextFunction<FontService, SetFontService> {
	//
}
