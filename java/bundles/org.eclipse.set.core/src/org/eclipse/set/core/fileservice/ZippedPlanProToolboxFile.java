/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.fileservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.basis.guid.Guid;
import org.eclipse.set.model.zipmanifest.Manifest;

/**
 * Toolbox file support for zipped *.planpro files.
 * 
 * @author Schaefer
 */
public class ZippedPlanProToolboxFile extends AbstractToolboxFile {

	private static final int BUFFER_SIZE = 1024;
	private static final String MANIFEST_FILE = "manifest.xml"; //$NON-NLS-1$
	private static final String MEDIA_DIR = "media"; //$NON-NLS-1$
	private static final String MODEL_FILE = "content.xml"; //$NON-NLS-1$
	private static final String TRASH_CAN = "trash"; //$NON-NLS-1$
	private static final String ZIP_SEPARATOR = "/"; //$NON-NLS-1$
	private static final String LAYOUT_FILE = "layout.xml"; //$NON-NLS-1$
	private static final String DEFAULT_RESOURCE_CONTENT_TYPE = "content"; //$NON-NLS-1$

	private static void deleteDir(final Path directory) throws IOException {
		if (Files.exists(directory)) {
			try (Stream<Path> files = Files.walk(directory)) {
				files.sorted(Comparator.reverseOrder()).map(Path::toFile)
						.forEach(File::delete);
			}
		}
	}

	private static boolean isDirectory(final ZipEntry zipEntry) {
		return zipEntry.getName().endsWith(ZIP_SEPARATOR);
	}

	private static File newFile(final Path dir, final ZipEntry entry)
			throws IOException {
		final File file = new File(dir.toString(), entry.getName());

		// test file position against dir
		final String filePath = file.getCanonicalPath();
		final String dirPath = dir.toFile().getCanonicalPath() + File.separator;
		if (filePath.startsWith(dirPath)) {
			return file;
		}
		throw new IOException(
				String.format("%s is outside of %s", filePath, dirPath)); //$NON-NLS-1$
	}

	private final EditingDomain editingDomain;
	private final Format format;
	private boolean loadable;
	private Path path;
	private ToolboxFileRole role;
	private Path temporaryDirectory;

	ZippedPlanProToolboxFile(final Format format,
			final EditingDomain editingDomain, final ToolboxFileRole role) {
		this.path = null;
		this.format = format;
		this.editingDomain = editingDomain;
		this.loadable = false;
		this.temporaryDirectory = null;
		this.role = role;
	}

	ZippedPlanProToolboxFile(final Path path, final Format format,
			final EditingDomain editingDomain, final boolean loadable,
			final ToolboxFileRole role) {
		this.path = path;
		this.format = format;
		this.editingDomain = editingDomain;
		this.loadable = loadable;
		this.temporaryDirectory = null;
		this.role = role;

	}

	ZippedPlanProToolboxFile(final ZippedPlanProToolboxFile toolboxFile) {
		this.path = toolboxFile.path;
		this.format = toolboxFile.format;
		this.editingDomain = toolboxFile.editingDomain;
		this.loadable = false;
		this.temporaryDirectory = toolboxFile.temporaryDirectory;
		this.role = toolboxFile.role;
		addResource(PathExtensions.getBaseFileName(toolboxFile.getModelPath()),
				toolboxFile.getPlanProResource());
		addResource(PathExtensions.getBaseFileName(toolboxFile.getLayoutPath()),
				toolboxFile.getLayoutResource());
	}

	@Override
	public void close() throws IOException {
		super.close();
		deleteDir(getUnzipDirectory());
	}

	@Override
	public void copyAllMedia(final ToolboxFile toolboxfile) throws IOException {
		final List<String> allMedia = toolboxfile.getAllMedia();
		allMedia.forEach(id -> {
			try {
				this.copyMedia(toolboxfile, id);
			} catch (final IOException e) {
				throw new RuntimeException(
						String.format("Can't copy %s from %s to %s ", id, //$NON-NLS-1$
								toolboxfile.getPath().getFileName().toString(),
								this.getPath().getFileName().toString()));
			}
		});
	}

	@Override
	public void copyMedia(final ToolboxFile toolboxfile, final String id)
			throws IOException {
		if (!toolboxfile.hasMedia(id) || this.hasMedia(id)) {
			return;
		}
		final byte[] mediaDaten = toolboxfile.getMedia(Guid.create(id));
		if (mediaDaten == null) {
			return;
		}
		this.createMedia(Guid.create(id), mediaDaten);
	}

	@Override
	public void createMedia(final Guid guid, final byte[] data)
			throws IOException {
		final Path mediaDirectory = getMediaDirectory();
		if (!Files.exists(mediaDirectory)) {
			Files.createDirectories(mediaDirectory);
		}
		Files.write(getMediaPath(guid), data);
	}

	@Override
	public void delete(final boolean close) throws IOException {
		if (close) {
			close();
		}
		Files.delete(getPath());
	}

	@Override
	public void deleteMedia(final Guid guid) throws IOException {
		final Path source = getMediaPath(guid);
		Files.move(source, getDeletePath().resolve(source.getFileName()),
				StandardCopyOption.REPLACE_EXISTING);
	}

	@Override
	public List<String> getAllMedia() throws IOException {
		final List<String> listMedia = new LinkedList<>();
		if (!Files.isDirectory(getMediaDirectory())) {
			return List.of();
		}

		try (final Stream<Path> listFiles = Files.list(getMediaDirectory())) {
			listFiles.forEach(media -> {
				listMedia.add(media.getFileName().toString());
			});
		}

		return listMedia;
	}

	@Override
	public EditingDomain getEditingDomain() {
		return editingDomain;
	}

	@Override
	public Format getFormat() {
		return format;
	}

	@Override
	public byte[] getMedia(final Guid guid) throws IOException {
		return Files.readAllBytes(getMediaPath(guid));
	}

	@Override
	public Path getModelPath() {
		if (temporaryDirectory != null) {
			return Paths.get(temporaryDirectory.toString(),
					role.toDirectoryName(), MODEL_FILE).toAbsolutePath();
		}
		throw new IllegalStateException("No temporary directory set."); //$NON-NLS-1$
	}

	@Override
	public Path getLayoutPath() {
		if (temporaryDirectory != null) {
			return Paths.get(temporaryDirectory.toString(),
					role.toDirectoryName(), LAYOUT_FILE).toAbsolutePath();
		}
		throw new IllegalStateException("No temporary directory set."); //$NON-NLS-1$
	}

	@Override
	public Path getPath() {
		return path;
	}

	/**
	 * Returns the role of this file
	 * 
	 * @return the role
	 */
	public ToolboxFileRole getRole() {
		return role;
	}

	@Override
	public boolean hasDetachedAttachments() {
		return true;
	}

	@Override
	public boolean hasMedia(final String id) {
		final Path mediaPath = Paths.get(getMediaDirectory().toString(), id);
		return Files.exists(mediaPath);
	}

	@Override
	public boolean isLoadable() {
		return loadable;
	}

	@Override
	public void open() throws IOException {
		if (isLoadable()) {
			generateMD5CheckSum();
			unzip();
			loadResource(getModelPath(), editingDomain);
			if (Files.exists(getLayoutPath())) {
				loadResource(getLayoutPath(), editingDomain);
			}
		} else {
			throw new IllegalStateException("Toolbox file not loadable."); //$NON-NLS-1$
		}
	}

	@Override
	public void save() throws IOException {
		super.save();
		this.saveResource(getLayoutResource());
		zip();
	}

	@Override
	public void saveResource(final XMLResource resource) throws IOException {
		if (resource.getURI().isFile()) {
			resource.save(null);
			writeManifest();
			editingDomain.getCommandStack().flush();
			loadable = true;
		} else {
			throw new IllegalStateException(Optional
					.ofNullable(resource.getURI())
					.map(u -> "Illegal uri " + u.toString()).orElse("no uri")); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	@Override
	public void setPath(final Path path) {
		this.path = path;
		setResourcePath(getModelPath());
	}

	@Override
	public void setRole(final ToolboxFileRole role) {
		this.role = role;
	}

	@Override
	public void setTemporaryDirectory(final Path path) {
		temporaryDirectory = path;
	}

	private Path getDeletePath() throws IOException {
		final Path deletePath = Paths.get(temporaryDirectory.toString(),
				TRASH_CAN);
		if (!Files.exists(deletePath)) {
			Files.createDirectories(deletePath);
		}
		return deletePath;
	}

	private Path getMediaDirectory() {
		return Paths.get(getUnzipDirectory().toString(), MEDIA_DIR);
	}

	private Path getMediaPath(final Guid guid) {
		return Paths.get(getMediaDirectory().toString(), guid.toString());
	}

	private Path getUnzipDirectory() {
		return getModelPath().getParent();
	}

	private void unzip() throws IOException {
		final Path unzipDir = getUnzipDirectory();
		// delete an existing zip dir
		deleteDir(unzipDir);
		// create zip dir
		Files.createDirectories(unzipDir);
		// unzip the archive
		final byte[] buffer = new byte[BUFFER_SIZE];
		try (final ZipInputStream zipIn = new ZipInputStream(
				new FileInputStream(getPath().toString()))) {
			for (ZipEntry zipEntry = zipIn
					.getNextEntry(); zipEntry != null; zipEntry = zipIn
							.getNextEntry()) {
				final File newFile = newFile(unzipDir, zipEntry);
				if (isDirectory(zipEntry)) {
					newFile.mkdirs();
				} else {
					// Create parent directories if not present
					newFile.getParentFile().mkdirs();

					// Extract the file
					try (final FileOutputStream fos = new FileOutputStream(
							newFile)) {
						int len;
						while ((len = zipIn.read(buffer)) > 0) {
							fos.write(buffer, 0, len);
						}
					}
				}
			}
			zipIn.closeEntry();
		}
	}

	private void writeManifest() throws IOException {
		final ManifestTransformation transformation = new ManifestTransformation();
		final Manifest manifest = transformation.transform(this);
		manifest.eResource().save(null);
	}

	private void zip() throws IOException {
		final Path zipDir = getModelPath().getParent();

		try (final FileOutputStream fileOutputStream = new FileOutputStream(
				getPath().toString());
				final ZipOutputStream zipOut = new ZipOutputStream(
						fileOutputStream)) {
			zip(zipDir.toFile(), Paths.get(""), zipOut); //$NON-NLS-1$
		}
	}

	private void zip(final File file, final Path zipPath,
			final ZipOutputStream zipOut) throws IOException {
		if (file.isDirectory()) {
			// ignore the top directory with the empty name
			if (!zipPath.toString().isEmpty()) {
				// write an entry for the directory
				final String entryName = zipPath.toString() + ZIP_SEPARATOR;
				zipOut.putNextEntry(new ZipEntry(entryName));
				zipOut.closeEntry();
			}

			// recursively zip the content of the directory
			final File[] dirContents = file.listFiles();
			for (final File child : dirContents) {
				zip(child, Paths.get(zipPath.toString(), child.getName()),
						zipOut);
			}
		} else {
			// add the file to the archive
			try (final FileInputStream fileIn = new FileInputStream(file)) {
				final ZipEntry zipEntry = new ZipEntry(zipPath.toString());
				zipOut.putNextEntry(zipEntry);
				final byte[] buffer = new byte[BUFFER_SIZE];
				int len;
				while ((len = fileIn.read(buffer)) >= 0) {
					zipOut.write(buffer, 0, len);
				}
			}
		}
	}

	URI getManifestUri() {
		if (temporaryDirectory != null) {
			return URI
					.createFileURI(Paths
							.get(temporaryDirectory.toString(),
									role.toDirectoryName(), MANIFEST_FILE)
							.toString());
		}
		throw new IllegalStateException("No temporary directory set."); //$NON-NLS-1$
	}

	@Override
	protected String getContentType(final Path modelPath) {
		return modelPath == null ? DEFAULT_RESOURCE_CONTENT_TYPE
				: PathExtensions.getBaseFileName(modelPath);
	}

	@Override
	public XMLResource getPlanProResource() {
		return getResource(PathExtensions.getBaseFileName(getModelPath()));
	}

	@Override
	public XMLResource getLayoutResource() {
		return getResource(PathExtensions.getBaseFileName(getLayoutPath()));
	}
}
