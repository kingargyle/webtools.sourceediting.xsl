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

import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.wst.xsl.internal.debug.ui.tabs.main.StylesheetViewer;

public class MoveDownAction extends AbstractStylesheetAction
{
	public MoveDownAction(StylesheetViewer viewer)
	{
		super(ActionMessages.MoveDownAction_Text, viewer);
	}

	@Override
	public void run()
	{
		List targets = getOrderedSelection();
		if (targets.isEmpty())
		{
			return;
		}
		List list = getEntriesAsList();
		int bottom = list.size() - 1;
		int index = 0;
		for (int i = targets.size() - 1; i >= 0; i--)
		{
			Object target = targets.get(i);
			index = list.indexOf(target);
			if (index < bottom)
			{
				bottom = index + 1;
				Object temp = list.get(bottom);
				list.set(bottom, target);
				list.set(index, temp);
			}
			bottom = index;
		}
		setEntries(list);
	}

	@Override
	protected boolean updateSelection(IStructuredSelection selection)
	{
		if (selection.isEmpty())
		{
			return false;
		}
		return getViewer().updateSelection(getActionType(), selection) && !isIndexSelected(selection, getEntriesAsList().size() - 1);
	}

	@Override
	protected int getActionType()
	{
		return MOVE;
	}
}
