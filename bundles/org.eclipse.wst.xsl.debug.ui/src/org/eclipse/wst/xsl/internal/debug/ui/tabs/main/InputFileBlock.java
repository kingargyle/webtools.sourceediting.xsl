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
package org.eclipse.wst.xsl.internal.debug.ui.tabs.main;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.wst.xsl.debug.ui.XSLDebugUIPlugin;
import org.eclipse.wst.xsl.internal.debug.ui.ResourceSelectionBlock;
import org.eclipse.wst.xsl.launching.FastXMLInfo;
import org.eclipse.wst.xsl.launching.XSLLaunchConfigurationConstants;

public class InputFileBlock extends ResourceSelectionBlock
{
	private final IFile defaultFile;
	private final ListenerList listeners = new ListenerList();

	public InputFileBlock(IFile defaultFile)
	{
		super(IResource.FILE, false);
		this.defaultFile = defaultFile;
	}

	@Override
	protected String[] getFileExtensions()
	{
		return new String[]
		{ "xml", "xhtml" };
	}

	public void setDefaults(ILaunchConfigurationWorkingCopy configuration)
	{
		String path = "";
		if (defaultFile != null)
			path = VariablesPlugin.getDefault().getStringVariableManager().generateVariableExpression("workspace_loc", defaultFile.getFullPath().toPortableString());
		configuration.setAttribute(XSLLaunchConfigurationConstants.ATTR_INPUT_FILE, path);
	}

	public void initializeFrom(ILaunchConfiguration configuration)
	{
		setLaunchConfiguration(configuration);
		try
		{
			String wd = configuration.getAttribute(XSLLaunchConfigurationConstants.ATTR_INPUT_FILE, (String) null);
			if (wd != null)
			{
				setText(wd);
			}
		}
		catch (CoreException e)
		{
			setErrorMessage(MainTabMessages.InputFileBlock_Exception_occurred_reading_configuration + e.getStatus().getMessage());
			XSLDebugUIPlugin.log(e);
		}
	}

	public void performApply(ILaunchConfigurationWorkingCopy configuration)
	{
		configuration.setAttribute(XSLLaunchConfigurationConstants.ATTR_INPUT_FILE, getText());
	}

	public String getName()
	{
		return MainTabMessages.InputFileBlock_Name;
	}

	@Override
	protected void setDefaultResource()
	{
	}

	@Override
	protected void textModified()
	{
		IPath path = null;
		String workingDirPath = getText();
		if (workingDirPath.indexOf("${") >= 0)
		{
			IStringVariableManager manager = VariablesPlugin.getDefault().getStringVariableManager();
			try
			{
				manager.validateStringVariables(workingDirPath);
				path = new Path(manager.performStringSubstitution(workingDirPath));
			}
			catch (CoreException e)
			{
			}
		}
		else if (workingDirPath.length() > 0)
		{
			path = new Path(workingDirPath);
		}
		if (path != null && path.toFile().isFile())
		{
			try
			{
				FastXMLInfo info = FastXMLInfo.getBasicInfo(path.toFile());
				notifyFileChanged(info);
			}
			catch (CoreException e)
			{
				e.printStackTrace();
			}
		}
	}

	private void notifyFileChanged(FastXMLInfo info)
	{
		Object[] l = listeners.getListeners();
		for (Object element : l)
		{
			((IInputFileChangedListener) element).fileChanged(info);
		}
	}

	public void addInputFileChangedListener(IInputFileChangedListener listener)
	{
		listeners.add(listener);
	}

	public void removeInputFileChangedListener(IInputFileChangedListener listener)
	{
		listeners.remove(listener);
	}

	@Override
	protected String getMessage(int type)
	{
		switch (type)
		{
			case ERROR_DIRECTORY_NOT_SPECIFIED:
				return MainTabMessages.InputFileBlock_DIRECTORY_NOT_SPECIFIED;
			case ERROR_DIRECTORY_DOES_NOT_EXIST:
				return MainTabMessages.InputFileBlock_DIRECTORY_DOES_NOT_EXIST;
			case GROUP_NAME:
				return MainTabMessages.InputFileBlock_GROUP_NAME;
			case USE_DEFAULT_RADIO:
				return MainTabMessages.InputFileBlock_DEFAULT_RADIO;
			case USE_OTHER_RADIO:
				return MainTabMessages.InputFileBlock_OTHER_RADIO;
			case DIRECTORY_DIALOG_MESSAGE:
				return MainTabMessages.InputFileBlock_DIALOG_MESSAGE;
			case WORKSPACE_DIALOG_MESSAGE:
				return MainTabMessages.InputFileBlock_WORKSPACE_DIALOG_MESSAGE;
			case VARIABLES_BUTTON:
				return MainTabMessages.InputFileBlock_VARIABLES_BUTTON;
			case FILE_SYSTEM_BUTTON:
				return MainTabMessages.InputFileBlock_FILE_SYSTEM_BUTTON;
			case WORKSPACE_BUTTON:
				return MainTabMessages.InputFileBlock_WORKSPACE_BUTTON;
			case WORKSPACE_DIALOG_TITLE:
				return MainTabMessages.InputFileBlock_WORKSPACE_DIALOG_TITLE;
		}
		return "" + type;
	}

	@Override
	protected void updateResourceText(boolean useDefault)
	{
	}
}
