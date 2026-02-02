/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.model.tablemodel.extensions;

import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.model.planpro.BasisTypen.BasisTypenFactory;
import org.eclipse.set.model.planpro.BasisTypen.ID_Bearbeitungsvermerk_TypeClass;
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.BasisobjekteFactory;
import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk;
import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk_Allg_AttributeGroup;
import org.eclipse.set.model.planpro.Basisobjekte.ENUMObjektzustandBesonders;

/**
 * Extension for table footnote
 * 
 * @author truong
 */
public class FootnoteExtensions {
	/**
	 * Transformation {@link ENUMObjektzustandBesonders} to
	 * {@link Bearbeitungsvermerk} and referenced to
	 * {@link ID_Bearbeitungsvermerk_TypeClass} without GUID for used in table
	 * Foot note
	 * 
	 * @param owner
	 *            the owner object
	 * 
	 * @param objState
	 *            the {@link ENUMObjektzustandBesonders}
	 * @return the {@link ID_Bearbeitungsvermerk_TypeClass} without reference
	 *         GUID, only value
	 */
	public static ID_Bearbeitungsvermerk_TypeClass transformObjectStateEnum(
			final Basis_Objekt owner,
			final ENUMObjektzustandBesonders objState) {
		if (objState == null) {
			return null;
		}
		final EnumTranslationService enumTranslationService = Services
				.getEnumTranslationService();
		final Bearbeitungsvermerk bearbeitungsvermerk = createBearbeitungsvermerkWithoutGuid(
				enumTranslationService.translate(objState).getPresentation());
		bearbeitungsvermerk.setIdentitaet(
				BasisobjekteFactory.eINSTANCE.createIdentitaet_TypeClass());
		// Add virtual guid for used later in Compare
		bearbeitungsvermerk.getIdentitaet()
				.setWert(objState.getClass().getName() + "-" //$NON-NLS-1$
						+ owner.getIdentitaet().getWert());
		final ID_Bearbeitungsvermerk_TypeClass idBv = BasisTypenFactory.eINSTANCE
				.createID_Bearbeitungsvermerk_TypeClass();
		idBv.setValue(bearbeitungsvermerk);
		return idBv;

	}

	/**
	 * @param infoText
	 *            the information text
	 * @return the {@link Bearbeitungsvermerk} without guid
	 */
	public static Bearbeitungsvermerk createBearbeitungsvermerkWithoutGuid(
			final String infoText) {
		final Bearbeitungsvermerk bv = BasisobjekteFactory.eINSTANCE
				.createBearbeitungsvermerk();
		final Bearbeitungsvermerk_Allg_AttributeGroup bvAttr = BasisobjekteFactory.eINSTANCE
				.createBearbeitungsvermerk_Allg_AttributeGroup();
		bvAttr.setKommentar(
				BasisobjekteFactory.eINSTANCE.createKommentar_TypeClass());
		bvAttr.getKommentar().setWert(infoText);

		bvAttr.setKurztext(
				BasisobjekteFactory.eINSTANCE.createKurztext_TypeClass());
		bvAttr.getKurztext().setWert(infoText);

		bv.setBearbeitungsvermerkAllg(bvAttr);
		return bv;
	}
}
