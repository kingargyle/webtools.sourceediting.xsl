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
package org.eclipse.wst.xsl.internal.launching.registry;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.wst.xsl.internal.launching.LaunchingPlugin;

public class ProcessorTypeRegistryReader extends AbstractRegistryReader
{
	public static final String TAG_processorType = "processorType";
	public static final String ATT_ID = "id";
	public static final String ATT_LABEL = "label";
	public static final String ATT_TRANSFORMER_FACTORY_NAME = "transformerFactory";
	public static final String ATT_OUTPUT_PROPERTIES = "outputProperties";
	public static final String ATT_FEATURE_PROPERTIES = "featureProperties";

	private ProcessorTypeRegistry registry;

	@Override
	protected boolean readElement(IConfigurationElement element)
	{
		if (!element.getName().equals(TAG_processorType))
			return false;

		String id = element.getAttribute(ATT_ID);
		if (id == null)
		{
			logMissingAttribute(element, ATT_ID);
			return true;
		}

		String label = element.getAttribute(ATT_LABEL);
		if (label == null)
		{
			logMissingAttribute(element, ATT_LABEL);
			return true;
		}

		String outputProperties = element.getAttribute(ATT_OUTPUT_PROPERTIES);
		if (outputProperties == null)
		{
			logMissingAttribute(element, ATT_OUTPUT_PROPERTIES);
			return true;
		}

		String featureProperties = element.getAttribute(ATT_FEATURE_PROPERTIES);
		if (featureProperties == null)
		{
			logMissingAttribute(element, ATT_FEATURE_PROPERTIES);
			return true;
		}

		registry.addType(element);

		return true;
	}

	public void readElement(ProcessorTypeRegistry registry, IConfigurationElement element)
	{
		this.registry = registry;
		readElement(element);
	}

	protected void addConfigs(ProcessorTypeRegistry registry)
	{
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		this.registry = registry;
		readRegistry(extensionRegistry, LaunchingPlugin.PLUGIN_ID, "processorType");
	}
}
