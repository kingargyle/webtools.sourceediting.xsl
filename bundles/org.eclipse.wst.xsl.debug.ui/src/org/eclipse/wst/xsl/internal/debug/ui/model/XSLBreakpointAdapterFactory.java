/*******************************************************************************
 * Copyright (c) 2007 Chase Technology Ltd - http://www.chasetechnology.co.uk
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Doug Satchwell (Chase Technology Ltd) - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xsl.internal.debug.ui.model;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Creates a toggle breakpoint adapter
 */
public class XSLBreakpointAdapterFactory implements IAdapterFactory
{
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	public Object getAdapter(Object adaptableObject, Class adapterType)
	{
		if (adaptableObject instanceof ITextEditor)
		{
			ITextEditor editorPart = (ITextEditor) adaptableObject;
			IResource resource = (IResource) editorPart.getEditorInput().getAdapter(IResource.class);
			if (resource != null)
			{
				String extension = resource.getFileExtension();
				if (extension != null && extension.equalsIgnoreCase("xsl")) //$NON-NLS-1$
				{
					return new XSLLineBreakpointAdapter();
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked") //$NON-NLS-1$
	public Class[] getAdapterList()
	{
		return new Class[]
		{ IToggleBreakpointsTarget.class };
	}
}