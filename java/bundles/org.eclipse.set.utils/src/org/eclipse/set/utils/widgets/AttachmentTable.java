/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.widgets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.set.basis.DomainElementList;
import org.eclipse.set.basis.DomainElementList.ChangeListener;
import org.eclipse.set.basis.attachments.Attachment;
import org.eclipse.set.basis.attachments.AttachmentInfo;
import org.eclipse.set.basis.attachments.FileKind;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.basis.files.AttachmentContentService;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.files.ToolboxFileFilter;
import org.eclipse.set.basis.files.ToolboxFileFilter.InvalidFilterFilename;
import org.eclipse.set.basis.guid.Guid;
import org.eclipse.set.basis.observable.ObservableValue;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.toolboxmodel.Basisobjekte.ENUMDateityp;
import org.eclipse.set.utils.Messages;
import org.eclipse.set.utils.attachment.Attachments;
import org.eclipse.set.utils.internal.DomainElementListContentProvider;
import org.eclipse.set.utils.internal.FileKindEditingSupport;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A table with attachments.
 * 
 * @author Schaefer
 */
public class AttachmentTable {
	// Chromium support format:
	// Audio/Video : https://www.chromium.org/audio-video/
	// Image:
	// https://en.wikipedia.org/wiki/Comparison_of_web_browsers#Image_format_support
	private static final List<ENUMDateityp> unSupportFileFormats = List.of(
			ENUMDateityp.ENUM_DATEITYP_MPEG, ENUMDateityp.ENUM_DATEITYP_TIF);

	static final Logger logger = LoggerFactory.getLogger(AttachmentTable.class);

	private static TableViewerColumn createTableViewerColumn(final String title,
			final int bound, final TableViewer viewer) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	private List<Attachment> attachments = null;
	private final AttachmentContentService contentProvider;
	private List<ToolboxFileFilter> extensions = null;
	private Consumer<Path> attachmentViewer = null;
	private String tempDir;
	private final ToolboxFile toolboxFile;
	DomainElementList<Attachment, AttachmentInfo<Attachment>> attachmentList = null;
	final DialogService dialogService;
	List<FileKind> fileKinds = null;
	final Messages messages;
	ObservableValue<Attachment> selectedAttachment = new ObservableValue<>();
	Composite tableParent;
	TableViewer viewer;

	/**
	 * @param parent
	 *            the parent
	 * @param messages
	 *            the messages
	 * @param contentProvider
	 *            the provider for attachment contents
	 * @param dialogService
	 *            the dialog service
	 * @param toolboxFile
	 *            the toolbox file
	 */
	public AttachmentTable(final Composite parent, final Messages messages,
			final AttachmentContentService contentProvider,
			final DialogService dialogService, final ToolboxFile toolboxFile) {
		this.tableParent = parent;
		this.messages = messages;
		this.contentProvider = contentProvider;
		this.dialogService = dialogService;
		this.toolboxFile = toolboxFile;
	}

	/**
	 * Creates the control.
	 * 
	 * @return the control for this attachment table
	 */
	public Control createControl() {
		createControl(getTableParent());
		return viewer.getControl();
	}

	/**
	 * Restrict extensions to the given filter of valid extensions.
	 * 
	 * @param extensionFilter
	 *            the extension filter
	 */
	public void setExtensionFilter(
			final List<ToolboxFileFilter> extensionFilter) {
		this.extensions = extensionFilter;
	}

	/**
	 * This model does support the manipulation of the attachment list.
	 * 
	 * @param attachmentList
	 *            the attachment list
	 * @param fileKinds
	 *            the file kind list (must not be empty, first entry is the
	 *            default file kind)
	 */
	public void setModel(
			final DomainElementList<Attachment, AttachmentInfo<Attachment>> attachmentList,
			final List<FileKind> fileKinds) {
		Assert.isNotNull(fileKinds);
		Assert.isTrue(!fileKinds.isEmpty());
		this.attachmentList = attachmentList;
		this.fileKinds = fileKinds;
	}

	/**
	 * This model does not support the manipulation of the attachment list.
	 * 
	 * @param attachments
	 *            the attachments
	 */
	public void setModel(final List<Attachment> attachments) {
		this.attachments = attachments;
	}

	/**
	 * @param attchmentViewer
	 *            the attachment viewer
	 */
	public void setAttachmentViewer(final Consumer<Path> attchmentViewer) {
		this.attachmentViewer = attchmentViewer;
	}

	/**
	 * @param tempDir
	 *            the temporary directory
	 */
	public void setTempDir(final String tempDir) {
		this.tempDir = tempDir;
	}

	/**
	 * @param newAttachments
	 *            the new attachments
	 */
	public void updateModel(final List<Attachment> newAttachments) {
		attachments.clear();
		newAttachments.forEach(a -> attachments.add(a));
		viewer.refresh();
	}

	// IMPROVE replace java.util.Observer
	@SuppressWarnings("deprecation")
	private void createButton(final ButtonRow buttonRow, final String message,
			final Runnable action, final BooleanSupplier condition) {
		final Button button = buttonRow.add(message);
		button.setEnabled(false);
		final Listener selectionListener = new Listener() {
			@Override
			public void handleEvent(final Event e) {
				if (e.type == SWT.Selection) {
					action.run();
				}
			}
		};
		button.addListener(SWT.Selection, selectionListener);
		selectedAttachment.addObserver(new java.util.Observer() {
			@Override
			public void update(final java.util.Observable o, final Object arg) {
				button.setEnabled(selectedAttachment.getValue().isPresent()
						&& condition.getAsBoolean());
			}
		});
	}

	private void createColumns() {
		@SuppressWarnings("nls") // empty column titles are not translated
		final String[] titles = { messages.AttachmentTable_columnTitleFilename,
				messages.AttachmentTable_columnTitleFiletype, "", "", "" };
		// final int[] bounds = { 300, 200, 100, 100, 100 };
		final int[] bounds = { 300, 200 };

		// first column is for the filename
		createFilenameColumn(titles, bounds);

		// second column is for the kind of the attachment
		createFileKindColumn(titles, bounds);
	}

	private void createControl(final Composite parent) {
		// table composite
		final Composite tableComposite = new Composite(parent, SWT.NONE);
		final GridData gdTableComposite = new GridData(SWT.FILL, SWT.TOP, true,
				true);
		tableComposite.setLayoutData(gdTableComposite);
		tableComposite.setLayout(new GridLayout());

		// table
		final Table table = new Table(tableComposite,
				SWT.BORDER | SWT.FULL_SELECTION);

		final int tableHeight = table.getItemHeight() * (attachments.size() + 1)
				+ table.getHeaderHeight();
		final GridData gdTable = new GridData(SWT.DEFAULT, tableHeight);
		gdTable.grabExcessHorizontalSpace = true;
		table.setLayoutData(gdTable);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		// table viewer
		viewer = new TableViewer(table);
		createColumns();

		if (hasSupportForListManipulation()) {
			viewer.setContentProvider(new DomainElementListContentProvider<>());
			viewer.setInput(attachmentList);

			// refresh the viewer...
			final ChangeListener<Attachment> changeListener = msg -> viewer
					.refresh();
			attachmentList.addChangeListener(changeListener);
			// ...as long as its control is not disposed
			viewer.getControl().addDisposeListener(
					e -> attachmentList.removeChangeListener(changeListener));
		} else {
			viewer.setContentProvider(new ArrayContentProvider());
			viewer.setInput(attachments);
		}

		// selections
		viewer.addSelectionChangedListener(event -> {
			final ISelection selection = event.getSelection();
			if (selection instanceof final StructuredSelection structuredSelection
					&& structuredSelection.size() == 1) {
				final Object element = structuredSelection.getFirstElement();
				selectedAttachment.setValue((Attachment) element);

			}

		});

		// controls
		createListControls(tableComposite);
	}

	private void createFileKindColumn(final String[] titles,
			final int[] bounds) {
		TableViewerColumn col;
		col = createTableViewerColumn(titles[1], bounds[1], viewer);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				final Attachment attachment = (Attachment) element;
				return attachment.getFileKind().getTranslation();
			}
		});
		if (hasSupportForListManipulation()) {
			col.setEditingSupport(
					new FileKindEditingSupport(viewer, fileKinds));
		}
	}

	private void createFilenameColumn(final String[] titles,
			final int[] bounds) {
		final TableViewerColumn col = createTableViewerColumn(titles[0],
				bounds[0], viewer);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				final Attachment attachment = (Attachment) element;
				return attachment.getFullFilename();
			}
		});
	}

	private void createListControls(final Composite parent) {
		final ButtonRow buttonRow = new ButtonRow(parent);

		createButton(buttonRow, messages.AttachmentTable_export,
				() -> getSelectedAttachment()
						.ifPresent(attachment -> Attachments
								.export(getTableParent().getShell(), attachment,
										dialogService, toolboxFile.getPath()
												.getParent().toString())),
				() -> true);

		// view attachment
		createButton(buttonRow, messages.AttachmentTable_viewPdf,
				() -> getSelectedAttachment().ifPresent(this::viewAttachment),
				() -> attachmentViewer != null);

		if (hasSupportForListManipulation()) {
			createButton(buttonRow, messages.AttachmentTable_remove, () -> {
				getSelectedAttachment().ifPresent(
						attachment -> attachmentList.remove(attachment));
				selectedAttachment.setValue(null);
			}, () -> true);
		}

		// add new attachment
		if (hasSupportForListManipulation()) {
			final Button addAttachment = buttonRow
					.add(messages.AttachmentTable_addAttachment);
			addAttachment.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetDefaultSelected(final SelectionEvent e) {
					logger.debug("widgetDefaultSelected"); //$NON-NLS-1$
					addAttachment(addAttachment.getShell());
				}

				@Override
				public void widgetSelected(final SelectionEvent e) {
					logger.debug("widgetSelected"); //$NON-NLS-1$
					addAttachment(addAttachment.getShell());
				}
			});
			buttonRow.getComposite().setLayoutData(
					new GridData(SWT.RIGHT, SWT.BOTTOM, false, false));
		}
	}

	private boolean hasSupportForListManipulation() {
		return attachmentList != null;
	}

	private void saveAttachment(final Attachment attachment, final Path path) {
		try {
			final byte[] content = contentProvider.getContent(attachment);
			final Path parent = path.getParent();
			if (parent != null) {
				Files.createDirectories(parent);
			}
			Files.write(path, content);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void startAttachmentViewer(final Path path) {
		final String extension = PathExtensions.getExtension(path);
		final ENUMDateityp enumDateityp = ENUMDateityp.get(extension);
		if (unSupportFileFormats.contains(enumDateityp)) {
			dialogService.openInformation(tableParent.getShell(),
					messages.AttachmentTable_UnsupportFormatTitle,
					messages.AttachmentTable_UnsupportFormatMsg);
			return;
		}
		if (attachmentViewer != null) {
			attachmentViewer.accept(path);
		}
	}

	void addAttachment(final Shell shell) {
		logger.debug("addAttachment"); //$NON-NLS-1$
		try {
			final Attachment loadedAttachment = Attachments.load(shell,
					fileKinds.get(0), dialogService, extensions);
			if (loadedAttachment != null) {
				final AttachmentInfo<Attachment> loadedAttachmentInfo = new AttachmentInfo<>();
				loadedAttachmentInfo.setElement(loadedAttachment);
				loadedAttachmentInfo.setData(loadedAttachment.getData());
				loadedAttachmentInfo.setToolboxFile(toolboxFile);
				loadedAttachmentInfo.setGuidProvider(
						(final Attachment a) -> Guid.create(a.getId()));
				attachmentList.add(loadedAttachmentInfo);
			}
		} catch (final InvalidFilterFilename e) {
			dialogService.error(shell, e);
		}
	}

	Optional<Attachment> getSelectedAttachment() {
		return selectedAttachment.getValue();
	}

	void viewAttachment(final Attachment attachment) {
		final Path path = Paths.get(tempDir, attachment.getId(),
				attachment.getFullFilename());
		saveAttachment(attachment, path);
		startAttachmentViewer(path);
	}

	/**
	 * @return the table parent
	 */
	public Composite getTableParent() {
		return tableParent;
	}
}
