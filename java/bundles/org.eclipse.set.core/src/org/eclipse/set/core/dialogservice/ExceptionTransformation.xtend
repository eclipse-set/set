/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.dialogservice

import org.eclipse.set.model.planpro.BasisTypen.Zeiger_TypeClass
import org.eclipse.set.basis.exceptions.ErrorStatus
import org.eclipse.set.basis.exceptions.FileExportException
import org.eclipse.set.basis.exceptions.IllegalReference
import org.eclipse.set.basis.exceptions.NotWritable
import org.eclipse.set.basis.files.ToolboxFileFilter
import org.eclipse.set.basis.files.ToolboxFileFilter.InvalidFilterFilename
import org.eclipse.set.core.Messages
import java.io.FileNotFoundException
import java.lang.reflect.InvocationTargetException
import org.eclipse.core.runtime.IStatus
import org.eclipse.core.runtime.MultiStatus
import org.eclipse.core.runtime.Status
import org.osgi.framework.Bundle
import org.osgi.framework.FrameworkUtil
import java.nio.file.AccessDeniedException
import java.nio.file.FileSystemException

/**
 * Transform an {@link Throwable} to an {@link IStatus}.
 * 
 * @author Schaefer
 */
class ExceptionTransformation {

	val Messages messages

	/**
	 * @param messages the translations
	 */
	new(Messages messages) {
		this.messages = messages
	}

	var Bundle bundle
	var String technicalMessage

	/**
	 * @param e the throwable
	 * 
	 * @return the status
	 */
	def ErrorStatus create new ErrorStatusImpl() transform(Throwable e,
		String technicalMessage) {
		bundle = FrameworkUtil.getBundle(this.class)
		this.technicalMessage = technicalMessage
		errorTitle = e.transformToTitle
		userMessage = e.transformToUserMessage
		status = e.transformToStatus(false)

		return
	}

	private def dispatch String transformToTitle(Void e) {
		return messages.ExceptionTransformation_DefaultTitle
	}

	private def dispatch String transformToTitle(Throwable e) {
		return messages.ExceptionTransformation_DefaultTitle
	}

	private def dispatch String transformToTitle(FileExportException e) {
		return messages.ExceptionTransformation_ExportTitle
	}

	private def dispatch String transformToTitle(InvocationTargetException e) {
		return e.cause.transformToTitle
	}

	private def dispatch String transformToTitle(InvalidFilterFilename e) {
		return messages.ExceptionTransformation_InvalidFilterFilenameTitle
	}

	private def dispatch String transformToUserMessage(Void e) {
		return null
	}

	private def dispatch String transformToUserMessage(Throwable e) {
		return messages.ExceptionTransformation_DefaultMessage
	}

	private def dispatch String transformToUserMessage(FileExportException e) {
		return String.format(
			messages.ExceptionTransformation_ExportMessagePattern,
			e.exportTarget
		)
	}

	private def dispatch String transformToUserMessage(
		InvocationTargetException e
	) {
		return e.cause.transformToUserMessage
	}

	private def dispatch String transformToUserMessage(
		InvalidFilterFilename e
	) {
		return String.format(
			messages.
				ExceptionTransformation_InvalidFilterFilenameMessagePattern,
			ToolboxFileFilter.getFilterExtensions(e.extensions)
		)
	}

	private def dispatch String transformToUserMessage(
		NullPointerException e
	) {
		return messages.ExceptionTransformation_NullPointerException_Message
	}

	private def dispatch String transformToUserMessage(
		FileNotFoundException e
	) {
		return messages.ExceptionTransformation_FileNotFoundException_Message
	}

	private def dispatch String transformToUserMessage(
		AccessDeniedException e
	) {
		return messages.ExceptionTransformation_FileNotFoundException_Message
	}

	private def dispatch String transformToUserMessage(
		FileSystemException e
	) {
		return messages.ExceptionTransformation_FileNotFoundException_Message
	}

	private def dispatch String transformToUserMessage(
		IllegalReference e
	) {
		return String.format(
			messages.ExceptionTransformation_IllegalReference_MessagePattern,
			e.transformToObject
		)
	}

	private def dispatch String transformToTechnicalMessage(
		Void e,
		boolean showType
	) {
		if (technicalMessage !== null) {
			return technicalMessage
		}
		return messages.ExceptionTransformation_NoMessage
	}

	private def dispatch String transformToTechnicalMessage(
		Throwable e,
		boolean showType
	) {
		if (technicalMessage !== null) {
			return technicalMessage
		}
		if (showType) {
			return e.class.simpleName
		}
		var message = messages.ExceptionTransformation_NoMessage
		if (!e.message.nullOrEmpty) {
			message = e.message
		}
		return message
	}

	private def dispatch String transformToTechnicalMessage(
		AccessDeniedException e,
		boolean showType
	) {
		return String.format(
			messages.ExceptionTransformation_AccessDeniedPattern, e.message);
	}

	private def dispatch String transformToTechnicalMessage(
		InvocationTargetException e,
		boolean showType
	) {
		if (technicalMessage !== null) {
			return technicalMessage
		}
		return e.cause.transformToTechnicalMessage(showType)
	}

	private def dispatch String transformToTechnicalMessage(
		FileExportException e,
		boolean showType
	) {
		if (technicalMessage !== null) {
			return technicalMessage
		}
		if (showType) {
			return e.class.simpleName
		}
		return e.exception.transformToTechnicalMessage(showType)
	}

	private def dispatch String transformToTechnicalMessage(
		NotWritable e,
		boolean showType
	) {
		if (technicalMessage !== null) {
			return technicalMessage
		}
		if (showType) {
			return e.class.simpleName
		}
		return String.format(
			messages.ExceptionTransformation_NotWriteablePattern,
			e.getFilePath()
		)
	}

	private def dispatch String transformToTechnicalMessage(
		IllegalReference e,
		boolean showType
	) {
		if (technicalMessage !== null) {
			return technicalMessage
		}
		if (showType) {
			return e.class.simpleName
		}
		return String.format(
			messages.
				ExceptionTransformation_IllegalReference_DetailMessagePattern,
			e.transformToObject,
			e.transformToReference,
			e.transformToValue
		)
	}

	private def dispatch String transformToTechnicalMessage(
		IndexOutOfBoundsException e,
		boolean showType
	) {
		if (technicalMessage !== null) {
			return technicalMessage
		}
		if (showType) {
			return e.class.simpleName
		}
		return messages.
			ExceptionTransformation_IndexOutOfBoundsException_DetailMessage
	}

	private def dispatch IStatus create new MultiStatus(bundle.symbolicName, 0, null.transformToTechnicalMessage(showType), null)
	transformToStatus(
		Void e,
		boolean showType
	) {
		add(
			new Status(
				IStatus.ERROR,
				bundle.symbolicName,
				'''«messages.ExceptionTransformation_ExceptionName»: No exception provided.'''
			)
		)
		return
	}

	private def dispatch IStatus create new MultiStatus(bundle.symbolicName, 0, e.transformToTechnicalMessage(showType), e)
	transformToStatus(
		Throwable e,
		boolean showType
	) {
		val status = it
		val stackTrace = e.stackTrace
		add(
			new Status(
				IStatus.ERROR,
				bundle.symbolicName,
				'''«messages.ExceptionTransformation_ExceptionName»: «e.class.simpleName»'''
			)
		)
		stackTrace.forEach[status.add(transformToStatus(showType))]
		return
	}

	private def dispatch IStatus create new Status(IStatus.ERROR, bundle.symbolicName, t.toString)
	transformToStatus(
		StackTraceElement t,
		boolean showType
	) {
	}

	private def dispatch IStatus create new MultiStatus(bundle.symbolicName, 0, e.transformToTechnicalMessage(showType), e)
	transformToStatus(
		InvocationTargetException e,
		boolean showType
	) {
		add(e.cause.transformToStatus(true))
		return
	}

	private def dispatch IStatus create new MultiStatus(bundle.symbolicName, 0, e.transformToTechnicalMessage(showType), e)
	transformToStatus(
		FileExportException e,
		boolean showType
	) {
		add(e.exception.transformToStatus(true))
		return
	}

	private def String transformToObject(IllegalReference e) {
		return e.object.eClass.name
	}

	private def String transformToReference(IllegalReference e) {
		return e.reference.name
	}

	private def dispatch String transformToValue(Object object) {
		return object.toString
	}

	private def dispatch String transformToValue(Void e) {
		return messages.ExceptionTransformation_MissingReference
	}

	private def dispatch String transformToValue(Zeiger_TypeClass p) {
		return p.wert.toString
	}

	private def dispatch String transformToValue(IllegalReference e) {
		return e.object.eGet(e.reference).transformToValue
	}
}
