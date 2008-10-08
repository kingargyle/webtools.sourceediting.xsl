package org.eclipse.wst.xsl.core.model;

import org.eclipse.core.runtime.PlatformObject;

/**
 * @author dcarver
 * @since 1.0
 *
 */
public abstract class XSLModelObject extends PlatformObject
{
	public enum Type {STYLESHEET_MODEL,IMPORT,INCLUDE,TEMPLATE,VARIABLE, CALL_TEMPLATE, STYLESHEET, ATTRIBUTE, OTHER_ELEMENT};
	
	public abstract Type getModelType();
}