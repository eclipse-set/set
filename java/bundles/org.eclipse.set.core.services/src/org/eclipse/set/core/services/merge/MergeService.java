/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services.merge;

import java.util.Optional;
import java.util.function.Supplier;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.set.basis.integration.ChangeDescription;
import org.eclipse.set.basis.integration.DiffLabelProvider;
import org.eclipse.set.basis.integration.Matcher;

/**
 * Merge two container. A container is an {@link EObject} with a number of
 * {@link EList} providing containment references to the elements of the model.
 * 
 * @param <T>
 *            the type of the implementation specific change model
 * 
 * @author Schaefer
 */
public interface MergeService<T> {

	/**
	 * Indicates which container is responsible for a match.
	 */
	public static enum Authority {
		/**
		 * Neither the primary nor the secondary container is responsible
		 */
		NONE,

		/**
		 * The primary container is responsible
		 */
		PRIMARY,

		/**
		 * The secondary container is responsible
		 */
		SECONDARY
	}

	/**
	 * The merge configuration.
	 */
	public static interface Configuration {

		/**
		 * @return the element provider
		 */
		ElementProvider getElementProvider();

		/**
		 * @return the guid provider
		 */
		GuidProvider getGuidProvider();

		/**
		 * @return the label provider
		 */
		DiffLabelProvider getLabelProvider();

		/**
		 * @return the matcher
		 */
		Matcher getMatcher();
	}

	/**
	 * The context of a merge.
	 */
	public static interface Context {

		/**
		 * @return the merge configuration
		 */
		Configuration getConfiguration();

		/**
		 * @return the primary container
		 */
		EObject getPrimaryContainer();

		/**
		 * Maps two matching guid's to the authority.
		 * 
		 * @return the responsible container
		 */
		Responsibility getResponsibility();

		/**
		 * @return the secondary container
		 */
		EObject getSecondaryContainer();
	}

	/**
	 * Provides a merge context.
	 */
	public static interface ContextProvider {

		/**
		 * @param primaryContainer
		 *            the primary container
		 * @param secondaryContainer
		 *            the secondary container
		 * 
		 * @return the merge context
		 */
		Context getContext(EObject primaryContainer,
				EObject secondaryContainer);
	}

	/**
	 * Provides elements for containers + GUIDs.
	 */
	public static interface ElementProvider {

		/**
		 * @param container
		 *            the container
		 * @param guid
		 *            the guid
		 * @param type
		 *            the element type
		 * 
		 * @return the optional element
		 */
		Optional<EObject> getElement(EObject container, String guid,
				String type);
	}

	/**
	 * Provides GUIDs for elements.
	 */
	public static interface GuidProvider {

		/**
		 * @param element
		 *            the element
		 * 
		 * @return the guid
		 */
		String getGuid(EObject element);
	}

	/**
	 * Maps a primary and a secondary guid to an authority.
	 */
	public static interface Responsibility {

		/**
		 * @param primaryGuid
		 *            the primary guid
		 * @param secondaryGuid
		 *            the secondary guid
		 * 
		 * @return the authority
		 */
		public Authority getAuthority(String primaryGuid, String secondaryGuid);
	}

	/**
	 * Merges all automatically resolvable changes into the primary container of
	 * the context.
	 * 
	 * @param context
	 *            the merge context
	 * @param description
	 *            the changes to be merged
	 * @param monitor
	 *            the optional progress monitor
	 */
	void automaticMerge(Context context, ChangeDescription<T> description);

	/**
	 * @param context
	 *            the merge context
	 * @param matchIdGenerator
	 *            the generator for generating match ids
	 * @param monitor
	 *            the optional progress monitor
	 * 
	 * @return the change description
	 */
	ChangeDescription<T> createChangeDescription(Context context,
			Supplier<Integer> matchIdGenerator);
}
