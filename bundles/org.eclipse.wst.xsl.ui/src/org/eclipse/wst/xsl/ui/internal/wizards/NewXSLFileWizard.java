/*******************************************************************************
 * Copyright (c) 2008, 2009 Chase Technology Ltd - http://www.chasetechnology.co.uk
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Doug Satchwell (Chase Technology Ltd) - initial API and implementation
 *     David Carver (STAR) - bug 271883 - ArrayIndexOutOfBounds on New XSL with no projects
 *******************************************************************************/
package org.eclipse.wst.xsl.ui.internal.wizards;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.wst.sse.core.internal.encoding.CommonEncodingPreferenceNames;
import org.eclipse.wst.xml.core.internal.XMLCorePlugin;
import org.eclipse.wst.xsl.ui.internal.Messages;
import org.eclipse.wst.xsl.ui.internal.XSLUIPlugin;

public class NewXSLFileWizard extends Wizard implements INewWizard
{
	private NewXSLFileWizardPage fNewFilePage;
	private NewXSLFileTemplatesWizardPage fNewFileTemplatesPage;
	private IStructuredSelection fSelection;
	private IWorkbench workbench;

	@Override
	public void addPages()
	{
		IStructuredSelection ssel = new StructuredSelection(IDE.computeSelectedResources(fSelection));
		
		fNewFilePage = new NewXSLFileWizardPage("NewFileCreationPage", ssel); //$NON-NLS-1$ 
		fNewFilePage.setTitle(Messages.NewXSLFilePageTitle);
		fNewFilePage.setDescription(Messages.NewXSLFilePageDescription);
		addPage(fNewFilePage);

		fNewFileTemplatesPage = new NewXSLFileTemplatesWizardPage();
		addPage(fNewFileTemplatesPage);
	}

	public void init(IWorkbench aWorkbench, IStructuredSelection aSelection)
	{
		this.workbench = aWorkbench;
		fSelection = aSelection;
		setWindowTitle(Messages.NewXSLFilePageWindowTitle);
	}

	@Override
	public boolean performFinish()
	{
		boolean performedOK = false;

		// save user options for next use
		fNewFileTemplatesPage.saveLastSavedPreferences();

		// no file extension specified so add default extension
		String fileName = fNewFilePage.getFileName();
		if (fileName.lastIndexOf('.') == -1)
		{
			String newFileName = fNewFilePage.addDefaultExtension(fileName);
			fNewFilePage.setFileName(newFileName);
		}

		// create a new empty file
		IFile file = fNewFilePage.createNewFile();

		// if there was problem with creating file, it will be null, so make
		// sure to check
		if (file != null)
		{
			// put template contents into file
			String templateString = fNewFileTemplatesPage.getTemplateString();
			if (templateString != null)
			{
				// determine the encoding for the new file
				Preferences preference = XMLCorePlugin.getDefault().getPluginPreferences();
				String charSet = preference.getString(CommonEncodingPreferenceNames.OUTPUT_CODESET);

				try
				{
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					OutputStreamWriter outputStreamWriter = null;
					if (charSet == null || charSet.trim().equals("")) { //$NON-NLS-1$
						// just use default encoding
						outputStreamWriter = new OutputStreamWriter(outputStream);
					}
					else
					{
						outputStreamWriter = new OutputStreamWriter(outputStream, charSet);
					}
					outputStreamWriter.write(templateString);
					outputStreamWriter.flush();
					outputStreamWriter.close();
					ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
					file.setContents(inputStream, true, false, null);
					inputStream.close();
				}
				catch (Exception e)
				{
					XSLUIPlugin.log(e);
				}
			}

			// open the file in editor
			openEditor(file);

			// everything's fine
			performedOK = true;
		}
		return performedOK;
	}

	private void openEditor(final IFile file)
	{
		if (file != null)
		{
			getShell().getDisplay().asyncExec(new Runnable()
			{
				public void run()
				{
					try
					{
						IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						IDE.openEditor(page, file, true);
					}
					catch (PartInitException e)
					{
						XSLUIPlugin.log(e);
					}
				}
			});
		}
	}
}