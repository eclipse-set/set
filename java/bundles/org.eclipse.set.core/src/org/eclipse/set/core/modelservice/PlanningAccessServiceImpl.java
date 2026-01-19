/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.core.modelservice;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.Assert;
import org.eclipse.set.core.services.planningaccess.PlanningAccessService;
import org.eclipse.set.model.planpro.PlanPro.ENUMUntergewerkArt;
import org.eclipse.set.model.planpro.PlanPro.PlanProFactory;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.model.planpro.PlanPro.Planung_Gruppe;
import org.eclipse.set.model.planpro.PlanPro.Planung_Projekt;
import org.eclipse.set.model.planpro.PlanPro.Untergewerk_Art_TypeClass;
import org.eclipse.set.ppmodel.extensions.PlanungEinzelExtensions;
import org.osgi.service.component.annotations.Component;

/**
 * Implementation of {@link PlanningAccessService}.
 * 
 * @author Schaefer
 */
@Component
public class PlanningAccessServiceImpl implements PlanningAccessService {

	private ENUMUntergewerkArt currentUntergewerkArt = null;

	private static void createPrerequisiteElements(
			final PlanPro_Schnittstelle planProIterface) {
		if (planProIterface.getLSTPlanung() == null) {
			planProIterface.setLSTPlanung(PlanProFactory.eINSTANCE
					.createLST_Planung_AttributeGroup());
		}

		if (planProIterface.getLSTPlanung().getObjektmanagement() == null) {
			planProIterface.getLSTPlanung()
					.setObjektmanagement(PlanProFactory.eINSTANCE
							.createObjektmanagement_AttributeGroup());
		}
	}

	private static ENUMUntergewerkArt getUntergewerkArt(
			final Planung_Gruppe group) {
		final Untergewerk_Art_TypeClass untergewerkArt = PlanungEinzelExtensions
				.getAusgabeFachdaten(group.getLSTPlanungEinzel())
				.getUntergewerkArt();
		if (untergewerkArt == null) {
			return null;
		}
		return untergewerkArt.getWert();
	}

	private static boolean isEstw(final Planung_Projekt project) {
		return project.getLSTPlanungGruppe()
				.stream()
				.anyMatch(x -> getUntergewerkArt(
						x) == ENUMUntergewerkArt.ENUM_UNTERGEWERK_ART_ESTW);
	}

	private static boolean isGeo(final Planung_Projekt project) {
		return project.getLSTPlanungGruppe()
				.stream()
				.anyMatch(x -> getUntergewerkArt(
						x) == ENUMUntergewerkArt.ENUM_UNTERGEWERK_ART_GEO);
	}

	@Override
	public Planung_Projekt getLSTPlanungProjekt(
			final PlanPro_Schnittstelle planProIterface) {
		// Single states have neither LST Planung nor Planung Projekt
		if (planProIterface.getLSTPlanung() != null) {
			final List<Planung_Projekt> projects = planProIterface
					.getLSTPlanung()
					.getObjektmanagement()
					.getLSTPlanungProjekt();
			final Optional<Planung_Projekt> firstESTWProject = projects.stream()
					.filter(PlanningAccessServiceImpl::isEstw)
					.findFirst();
			final Optional<Planung_Projekt> firstGeoProject = projects.stream()
					.filter(PlanningAccessServiceImpl::isGeo)
					.findFirst();

			// return the first ESTW project (if present)
			if (firstESTWProject.isPresent()) {
				return firstESTWProject.orElseThrow();
			}

			// return the first Geo project (if present)
			if (firstGeoProject.isPresent()) {
				return firstGeoProject.orElseThrow();
			}

			// return the first project, if no ESTW or Geo project is present
			if (!projects.isEmpty()) {
				return projects.get(0);
			}
		}
		// return null, if no project is present
		return null;
	}

	@Override
	public Planung_Gruppe getLeadingPlanungGruppe(
			final Planung_Projekt project) {

		final List<Planung_Gruppe> planingGroups = project
				.getLSTPlanungGruppe();

		if (planingGroups.isEmpty()) {
			return null;
		}

		final var currentArt = getCurrentUntergewerkArt();
		if (currentArt == null) {
			return planingGroups.get(0);
		}

		final Map<ENUMUntergewerkArt, List<Planung_Gruppe>> groupsBySubWork = planingGroups
				.stream()
				.filter(group -> {
					final var groupArt = getUntergewerkArt(group);
					final var currentArt = getCurrentUntergewerkArt();
					if (groupArt == null && currentArt == null) {
						return true;
					}

					if (groupArt == null || currentArt == null) {
						return false;
					}

					return groupArt.equals(currentArt);
				})
				.collect(Collectors
						.groupingBy(group -> getUntergewerkArt(group)));
		if (groupsBySubWork.containsKey(getCurrentUntergewerkArt())) {
			return groupsBySubWork.get(getCurrentUntergewerkArt()).getFirst();
		}

		return planingGroups.getFirst();
	}

	@Override
	public void setLSTPlanungProjekt(
			final PlanPro_Schnittstelle planProIterface,
			final Planung_Projekt project) {
		final Planung_Projekt projectToBeReplaced = getLSTPlanungProjekt(
				planProIterface);
		if (projectToBeReplaced == null) {
			// if there is no project, we just add the new one
			createPrerequisiteElements(planProIterface);
			planProIterface.getLSTPlanung()
					.getObjektmanagement()
					.getLSTPlanungProjekt()
					.add(project);
		} else {
			// if there is a project to be replaced, we replace it with the new
			// one (if it's really new)
			final List<Planung_Projekt> projects = planProIterface
					.getLSTPlanung()
					.getObjektmanagement()
					.getLSTPlanungProjekt();
			final int index = projects.indexOf(projectToBeReplaced);
			Assert.isTrue(index >= 0);
			if (projects.get(index) != project) {
				projects.add(index, project);
				projects.remove(projectToBeReplaced);
			}
		}
	}

	@Override
	public void setPlanungGruppe(final Planung_Projekt project,
			final Planung_Gruppe group) {
		final Planung_Gruppe groupToBeReplaced = getLeadingPlanungGruppe(
				project);
		final List<Planung_Gruppe> groups = project.getLSTPlanungGruppe();
		final int index = groups.indexOf(groupToBeReplaced);
		if (index < 0) {
			// if there is no group, we just add the new one
			groups.add(group);
		} else {
			// if there is a group to be replaced, we replace it with the new
			// one (if it's really new)
			if (groups.get(index) != group) {
				groups.add(index, group);
				groups.remove(groupToBeReplaced);
			}
		}
	}

	@Override
	public ENUMUntergewerkArt getCurrentUntergewerkArt() {
		return currentUntergewerkArt;
	}

	@Override
	public void setCurrentUntergewerkArt(
			final ENUMUntergewerkArt untergewerkArt) {
		currentUntergewerkArt = untergewerkArt;
	}
}
