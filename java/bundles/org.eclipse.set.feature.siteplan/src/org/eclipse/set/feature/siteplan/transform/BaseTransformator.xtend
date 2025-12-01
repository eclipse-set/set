/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform

import java.lang.reflect.ParameterizedType
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EReference
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt
import org.eclipse.set.model.siteplan.Coordinate
import org.eclipse.set.model.siteplan.SiteplanFactory
import org.eclipse.set.model.siteplan.SiteplanObject
import org.eclipse.set.model.siteplan.SiteplanState
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup

/**
 * Base transformator class for common transformation utilities 
 * 
 * @author Stuecker
 */
abstract class BaseTransformator<T extends Ur_Objekt> implements Transformator {
	protected SiteplanState state
	protected MultiContainer_AttributeGroup container
	Class<T> classT = getClassT()

	private def Class<T> getClassT() {
		val paramT = class.genericSuperclass as ParameterizedType;
		val typeArgs = paramT.getActualTypeArguments();
		return typeArgs.get(0) as Class<T>;
	}

	override transformContainer(SiteplanState state,
		MultiContainer_AttributeGroup container) {
		this.state = state
		this.container = container
		
		initializeTransform()
		val transformObjects = container.contents.filter[classT.isInstance(it)]
		var i = 0
		while(!Thread.currentThread.isInterrupted && i < transformObjects.size) {
			try {
				transform(transformObjects.get(i) as T)	
			} catch (Exception e) {
				System.out.println("failed transform: " +e.getMessage())
				recordError((transformObjects.get(i) as T).identitaet?.wert, e.toString)
			}
			
			i++
		}
		
		
		finalizeTransform()
	}

	protected def <U extends SiteplanObject> void addSiteplanElement(U value,
		EReference ref) {
		synchronized (state) {
			val refList = state.eGet(ref) as EList<SiteplanObject>
			refList.add(value)
		}
	}

	/**
	 * Additional handler to perform an action before elements of type
	 * T are being transformed
	 */
	protected def void initializeTransform() {
		// Default implementation is blank
	}

	/**
	 * Abstract handler to transform an object of type T
	 */
	abstract def void transform(T object);

	/**
	 * Additional handler to perform an action after all elements of type
	 * T have been transformed
	 */
	protected def void finalizeTransform() {
		// Default implementation is blank
	}

	/**
	 * Records an error to be shown in the siteplan 
	 * 
	 * @param guid a GUID relevant to this error
	 * @param message the message to show to the user
	 */
	protected def void recordError(String guid, String message) {
		recordError(guid, message, null)
	}

	/**
	 * Records an error to be shown in the siteplan at a specific position
	 * 
	 * @param guid a GUID relevant to this error
	 * @param message the message to show to the user
	 * @param coordinate the location of the error
	 */
	protected def void recordError(String guid, String message,
		Coordinate coordinate) {
		recordError(#[guid], message, coordinate)
	}

	/**
	 * Records an error to be shown in the siteplan at a specific position
	 * 
	 * @param guids an iterable of GUIDs relevant to this error
	 * @param message the message to show to the user
	 * @param coordinate the location of the error
	 */
	protected def void recordError(Iterable<String> guids, String message,
		Coordinate coord) {
		val error = SiteplanFactory.eINSTANCE.createError
		guids.forEach[guid|error.relevantGUIDs.add(guid)]
		error.message = message
		error.position = coord
		synchronized (state) {
			state.errors.add(error)
		}
	}

}
