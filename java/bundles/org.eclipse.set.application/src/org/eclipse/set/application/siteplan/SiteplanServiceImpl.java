/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.application.siteplan;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.siteplan.SiteplanService;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schloss;
import org.eclipse.set.model.siteplan.SiteplanObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

/**
 * Implementation for {@link SiteplanService}
 * 
 * @author Truong
 */
@Component(property = { EventConstants.EVENT_TOPIC + "=" + Events.MODEL_CHANGED,
		EventConstants.EVENT_TOPIC + "=" + Events.SITEPLAN_OPENING,
		EventConstants.EVENT_TOPIC + "="
				+ Events.SITEPLAN_OPEN_FIRST_TIME }, service = {
						SiteplanService.class, EventHandler.class })
public class SiteplanServiceImpl implements SiteplanService, EventHandler {
	private Set<SiteplanObject> siteplanElements;
	private boolean isSiteplanLoading = false;
	private boolean isNotFirstTimeOpenSiteplan = false;

	@Override
	public void handleEvent(final Event event) {
		switch (event.getTopic()) {
			case Events.MODEL_CHANGED: {
				siteplanElements = new HashSet<>();
				isNotFirstTimeOpenSiteplan = false;
				Services.setSiteplanService(this);
				break;
			}
			case Events.SITEPLAN_OPENING: {
				if (event.getProperty(
						IEventBroker.DATA) instanceof final Boolean isLoading) {
					isSiteplanLoading = isLoading.booleanValue();
				}
				break;
			}
			case Events.SITEPLAN_OPEN_FIRST_TIME:
				isNotFirstTimeOpenSiteplan = true;
				break;
			default:
				break;
		}

	}

	@Override
	public void addSiteplanElement(final SiteplanObject object) {
		siteplanElements.add(object);
	}

	@Override
	public Optional<SiteplanObject> getSiteplanElement(final Ur_Objekt object) {
		if (object == null) {
			return Optional.empty();
		}

		return switch (object) {
			case final Schloss schloss -> getSiteplanSspElement(schloss);
			default -> findSiteplanElementWithGuid(
					object.getIdentitaet().getWert());
		};
	}

	@Override
	public boolean isSiteplanElement(final String guid) {
		if (!isNotFirstTimeOpenSiteplan) {
			return true;
		}
		final Set<String> guids = siteplanElements.stream()
				.map(ele -> ele.getGuid())
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
		return guids.contains(guid);
	}

	@Override
	public boolean isSiteplanElement(final Ur_Objekt object) {
		if (!isNotFirstTimeOpenSiteplan) {
			return true;
		}
		return getSiteplanElement(object).isPresent();
	}

	private Optional<SiteplanObject> getSiteplanSspElement(
			final Schloss shloss) {
		if (shloss.getSchlossSsp() != null
				&& shloss.getSchlossSsp().getIDSchluesselsperre() != null) {
			return findSiteplanElementWithGuid(
					shloss.getSchlossSsp().getIDSchluesselsperre().getWert());
		}
		return findSiteplanElementWithGuid(shloss.getIdentitaet().getWert());
	}

	private Optional<SiteplanObject> findSiteplanElementWithGuid(
			final String guid) {
		return siteplanElements.stream()
				.filter(ele -> ele.getGuid().equals(guid))
				.findFirst();
	}

	@Override
	public boolean isSiteplanLoading() {
		return isSiteplanLoading;
	}

	@Override
	public boolean isNotFirstTimeOpenSiteplan() {
		return isNotFirstTimeOpenSiteplan;
	}
}
