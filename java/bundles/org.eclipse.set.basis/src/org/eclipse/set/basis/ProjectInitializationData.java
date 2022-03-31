/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

import java.util.Date;

/**
 * Additional data for project initialization.
 * 
 * @author Schaefer
 */
public final class ProjectInitializationData extends ModelObject
		implements InitializationData {
	private String bauzustand;

	private Date creationDate;

	private String directory;

	private String fuehrendeOertlichkeit;

	private String index;

	private String lfdNummer;

	/**
	 * @return den Bauzustand
	 */
	public String getBauzustand() {
		return bauzustand;
	}

	/**
	 * @return the creation date.
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public String getDirectory() {
		return directory;
	}

	/**
	 * @return die führende Örtlichkeit.
	 */
	public String getFuehrendeOertlichkeit() {
		return fuehrendeOertlichkeit;
	}

	/**
	 * @return der Index.
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * @return die laufende Nummer.
	 */
	public String getLfdNummer() {
		return lfdNummer;
	}

	/**
	 * setzt den Bauzustand.
	 * 
	 * @param bauzustand
	 *            der Bauzustand
	 */
	public void setBauzustand(final String bauzustand) {
		firePropertyChange("bauzustand", this.bauzustand, //$NON-NLS-1$
				this.bauzustand = bauzustand);
	}

	@Override
	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * setzt das Ausgabeverzeichnis.
	 * 
	 * @param directory
	 *            das Verzeichnis
	 */
	public void setDirectory(final String directory) {
		firePropertyChange("directory", this.directory, //$NON-NLS-1$
				this.directory = directory);
	}

	/**
	 * setzt die führende Örtlichkeit
	 * 
	 * @param fuehrendeOertlichkeit
	 *            die führende Örtlichkeit
	 */
	public void setFuehrendeOertlichkeit(final String fuehrendeOertlichkeit) {
		firePropertyChange("fuehrendeOertlichkeit", this.fuehrendeOertlichkeit, //$NON-NLS-1$
				this.fuehrendeOertlichkeit = fuehrendeOertlichkeit);
	}

	/**
	 * setzt den Index.
	 * 
	 * @param index
	 *            der Index
	 */
	public void setIndex(final String index) {
		firePropertyChange("index", this.index, this.index = index); //$NON-NLS-1$
	}

	/**
	 * setzt die laufende Nummer.
	 * 
	 * @param lfdNummer
	 *            die laufende Nummer
	 */
	public void setLfdNummer(final String lfdNummer) {
		firePropertyChange("lfdNummer", this.lfdNummer, //$NON-NLS-1$
				this.lfdNummer = lfdNummer);
	}
}
