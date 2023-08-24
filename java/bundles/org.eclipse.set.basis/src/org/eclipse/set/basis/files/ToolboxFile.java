/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.files;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.set.basis.guid.Guid;
import org.w3c.dom.Document;

/**
 * Abstraction from concrete file format.
 * 
 * @author Schaefer
 */
public interface ToolboxFile {

	/**
	 * The format of the toolbox file.
	 */
	public static interface Format {

		/**
		 * @return whether the format is a plain (non-zip) format
		 */
		boolean isPlain();

		/**
		 * @return whether the format is the special temporary integration file
		 *         format
		 */
		boolean isTemporaryIntegration();

		/**
		 * @return whether the format is a PlanPro zip format
		 */
		boolean isZippedPlanPro();
	}

	/**
	 * Closes the toolbox file without saving.
	 * 
	 * @throws IOException
	 *             if close the toolbox file fails
	 */
	void close() throws IOException;

	/**
	 * Copy all media from Toolboxfile
	 * 
	 * @param toolboxfile
	 *            the toolboxfile to copy media
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	void copyAllMedia(ToolboxFile toolboxfile) throws IOException;

	/**
	 * Copy a media from Toolboxfile
	 * 
	 * @param toolboxfile
	 *            the toolboxfile to copy media
	 * @param id
	 *            id of media to copy
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	void copyMedia(ToolboxFile toolboxfile, String id) throws IOException;

	/**
	 * @param guid
	 *            the guid
	 * @param data
	 *            the data of the media file
	 * 
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	void createMedia(Guid guid, byte[] data) throws IOException;

	/**
	 * Delete the file.
	 * 
	 * @param close
	 *            whether to close the file before deleting
	 * 
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	void delete(boolean close) throws IOException;

	/**
	 * Delete the media file with the given guid.
	 * 
	 * @param guid
	 *            the guid of the media file
	 * 
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	void deleteMedia(Guid guid) throws IOException;

	/**
	 * 
	 * @return list media id
	 * 
	 * @throws IOException
	 *             if an I/O error has occurred
	 */
	List<String> getAllMedia() throws IOException;

	/**
	 * Get MD5 checksum of file
	 * 
	 * @return md5 hash
	 */
	String getChecksum();

	/**
	 * @return the editing domain
	 */
	EditingDomain getEditingDomain();

	/**
	 * @return the format of the toolbox file
	 */
	Format getFormat();

	/**
	 * @param guid
	 *            the guid
	 * 
	 * @return the media content
	 * 
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	byte[] getMedia(Guid guid) throws IOException;

	/**
	 * Return the model path of the toolbox file. This may be the toolbox file
	 * itself or a temporary content file on the file system. The returned file
	 * is always a plain, uncompressed xml file.
	 * 
	 * @return the model path
	 */
	Path getModelPath();

	/**
	 * Return the layout model path of the toolbox file. This may be the toolbox
	 * file itself or a temporary content file on the file system. The returned
	 * file is always a plain, uncompressed xml file.
	 * 
	 * @return the layout model path
	 */
	Path getLayoutPath();

	/**
	 * @return the path to the file
	 */
	Path getPath();

	/**
	 * @return the planpro schnittstelle resources
	 */
	XMLResource getPlanProResource();

	/**
	 * @return the planpro layout resource
	 */
	XMLResource getLayoutResource();

	/**
	 * @return DOM document with line number
	 */
	Document getXMLDocument();

	/**
	 * create DOM document
	 * 
	 * @param doc
	 *            DOM document with line number
	 */
	void setXMLDocument(Document doc);

	/**
	 * @return whether the toolbox file (true = detached) or the model (false =
	 *         embedded) contain the attachments
	 */
	boolean hasDetachedAttachments();

	/**
	 * @param id
	 *            the media id
	 * 
	 * @return whether this toolbox file contains a media with the given id
	 */
	boolean hasMedia(String id);

	/**
	 * @return whether the toolbox file can load the content
	 */
	boolean isLoadable();

	/**
	 * open content from {@link #getPath()}.
	 * 
	 * @throws IOException
	 *             if an I/O error has occurred
	 */
	void open() throws IOException;

	/**
	 * Saves the toolbox file.
	 * 
	 * @throws IOException
	 *             if saving the toolbox file fails
	 */
	void save() throws IOException;

	/**
	 * @param path
	 *            the new path
	 */
	void setPath(Path path);

	/**
	 * @param role
	 *            the new role
	 */
	void setRole(ToolboxFileRole role);

	/**
	 * @param path
	 *            the temporary directory for provisional data
	 */
	void setTemporaryDirectory(Path path);

	/**
	 * Returns the loaded PlanPro model. Note that this model is *not* the
	 * toolbox model and should only be used if access to the plain model is
	 * required
	 * 
	 * @return the planpro model
	 */
	org.eclipse.set.model.model11001.PlanPro.DocumentRoot getPlanProSourceModel();

	/**
	 * Return the loaded PlanPro Layout model. Note that this model is *not* the
	 * toolbox model and should only be used if access to the plain model is
	 * required
	 * 
	 * @return the planpro layout model
	 */
	org.eclipse.set.model.model11001.Layoutinformationen.DocumentRoot getLayoutSourceModel();
}
