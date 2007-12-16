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
package org.eclipse.wst.xsl.launching;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.wst.xsl.internal.launching.PreferenceUtil;
import org.eclipse.wst.xsl.internal.launching.ProcessorType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class FeaturePreferences
{
	private Map typeFeatures;

	public Map getFeaturesValues(String typeId)
	{
		return (Map) typeFeatures.get(typeId);
	}

	public void setTypeFeatures(Map typeFeatures)
	{
		this.typeFeatures = typeFeatures;
	}

	public String getAsXML() throws ParserConfigurationException, IOException, TransformerException
	{
		Document doc = PreferenceUtil.getDocument();
		Element config = doc.createElement("featurePreferences"); 
		doc.appendChild(config);

		for (Iterator iter = typeFeatures.entrySet().iterator(); iter.hasNext();)
		{
			Map.Entry entry = (Map.Entry) iter.next();
			ProcessorType type = (ProcessorType) entry.getKey();
			Element processorTypeElement = typeAsElement(doc, type.getId());
			Map featureValues = (Map) entry.getValue();
			featureValuesAsElement(doc, processorTypeElement, featureValues);
			config.appendChild(processorTypeElement);
		}

		// Serialize the Document and return the resulting String
		return PreferenceUtil.serializeDocument(doc);
	}

	public static FeaturePreferences fromXML(InputStream inputStream) throws CoreException
	{
		FeaturePreferences prefs = new FeaturePreferences();

		// Do the parsing and obtain the top-level node
		Document doc = PreferenceUtil.getDocument(inputStream);
		Element config = doc.getDocumentElement();

		Map typeFeatures = new HashMap();
		Element[] processorTypeEls = PreferenceUtil.getChildElements(config, "processorType");
		for (int i = 0; i < processorTypeEls.length; ++i)
		{
			Element processorTypeEl = processorTypeEls[i];
			String type = elementAsType(processorTypeEl);
			Map featureValues = elementAsFeatureValues(processorTypeEl);
			typeFeatures.put(type, featureValues);
		}

		prefs.setTypeFeatures(typeFeatures);

		return prefs;
	}

	private static String elementAsType(Element parent)
	{
		String id = parent.getAttribute("id");
		return id;
	}

	private static Element typeAsElement(Document doc, String type)
	{
		Element element = doc.createElement("processorType");
		element.setAttribute("id", type);
		return element;
	}

	private static Map elementAsFeatureValues(Element element)
	{
		Element[] featureEls = PreferenceUtil.getChildElements(element, "feature");
		Map featureValues = new HashMap(featureEls.length);
		for (Element featureEl : featureEls)
		{
			String uri = featureEl.getAttribute("uri");
			String value = featureEl.getAttribute("value");
			featureValues.put(uri, value);
		}
		return featureValues;
	}

	private static void featureValuesAsElement(Document doc, Element featuresEl, Map featureValues)
	{
		if (featureValues != null)
		{
			for (Iterator iterator = featureValues.entrySet().iterator(); iterator.hasNext();)
			{
				Map.Entry entry2 = (Map.Entry) iterator.next();
				String uri = (String) entry2.getKey();
				String value = (String) entry2.getValue();
				Element element = doc.createElement("feature");
				element.setAttribute("uri", uri);
				element.setAttribute("value", value);
				featuresEl.appendChild(element);
			}
		}
	}
}
