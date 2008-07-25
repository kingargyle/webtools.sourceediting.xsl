/*******************************************************************************
 * Copyright (c) 2008 Chase Technology Ltd - http://www.chasetechnology.co.uk
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Doug Satchwell (Chase Technology Ltd) - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xsl.ui.internal;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.ui.editors.text.templates.ContributionContextTypeRegistry;
import org.eclipse.ui.editors.text.templates.ContributionTemplateStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class XSLUIPlugin extends AbstractUIPlugin {
	
	/**
	 * The template store for the xsl editor.
	 */
	private TemplateStore fTemplateStore;

	
    private ScopedPreferenceStore preferenceStore;
    
	/**
	 * The template context type registry for the xml editor.
	 */
	private ContributionContextTypeRegistry fContextTypeRegistry;
	
	/**
	 * The plugin id for this plugin.
	 */
	static public String PLUGIN_ID = "org.eclipse.wst.xsl.ui"; //$NON-NLS-1$

	// The shared instance
	private static XSLUIPlugin plugin;

	/**
	 * The constructor
	 */
	public XSLUIPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static XSLUIPlugin getDefault() {
		return plugin;
	}

	public static void log(Exception e)
	{
		getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, 0, "", e)); //$NON-NLS-1$
	}

	public static void log(CoreException e)
	{
		getDefault().getLog().log(e.getStatus());
	}

	/**
	 * Returns the template store for the xsl templates.
	 * 
	 * @return the template store for the xsl templates
	 */
	public TemplateStore getTemplateStore()
	{
		if (fTemplateStore == null)
		{
			fTemplateStore = new ContributionTemplateStore(getTemplateContextRegistry(), getPreferenceStore(), "org.eclipse.wst.xsl.ui.custom_templates"); //$NON-NLS-1$
			try
			{
				fTemplateStore.load();
			}
			catch (IOException e)
			{
				log(e);
			}
		}
		return fTemplateStore;
	}

	/**
	 * Returns the template context type registry for the xsl plugin.
	 * 
	 * @return the template context type registry for the xsl plugin
	 */
	public ContextTypeRegistry getTemplateContextRegistry()
	{
		if (fContextTypeRegistry == null)
		{
			ContributionContextTypeRegistry registry = new ContributionContextTypeRegistry();
			registry.addContextType("xsl_new"); //$NON-NLS-1$
			fContextTypeRegistry = registry;
		}
		return fContextTypeRegistry;
	}
	
}
