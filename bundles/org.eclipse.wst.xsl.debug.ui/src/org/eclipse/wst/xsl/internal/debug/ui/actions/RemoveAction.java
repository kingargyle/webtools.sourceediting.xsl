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
package org.eclipse.wst.xsl.internal.debug.ui.actions;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.wst.xsl.internal.debug.ui.tabs.main.StylesheetViewer;
import org.eclipse.wst.xsl.launching.config.LaunchTransform;

public class RemoveAction extends AbstractStylesheetAction
{
	public RemoveAction(StylesheetViewer viewer)
	{
		super(ActionMessages.RemoveAction_Text, viewer);
	}

	@Override
	public void run()
	{
		IStructuredSelection selection = (IStructuredSelection) getViewer().getSelection();
		getViewer().removeEntries((LaunchTransform[]) selection.toList().toArray(new LaunchTransform[0]));
	}

	@Override
	protected boolean updateSelection(IStructuredSelection selection)
	{
		if (selection.isEmpty())
		{
			return false;
		}
		return getViewer().updateSelection(getActionType(), selection);
	}

	@Override
	protected int getActionType()
	{
		return MOVE;
	}
}