/*******************************************************************************
 * Copyright (c) 2008 Standards for Technology in Automotive Retail
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     David Carver - STAR - bug 224197 - initial API and implementation
 *                    based on work from Apache Xalan 2.7.0
 *******************************************************************************/
/*
 * Copyright 1999-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * $Id: VarNameCollector.java,v 1.1 2008/03/27 01:08:54 dacarver Exp $
 */
package org.eclipse.wst.xsl.core.internal.compiler.xslt10.templates;

import java.util.Vector;

import org.apache.xml.utils.QName;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.operations.Variable;

/**
 * This class visits variable refs in an XPath and collects their QNames.
 */
public class VarNameCollector extends XPathVisitor
{
	Vector m_refs = new Vector();
	
	/**
	 * Reset the list for a fresh visitation and collection.
	 */
	public void reset()
	{
		m_refs.removeAllElements(); //.clear();
	}
	
	/**
	 * Get the number of variable references that were collected.
	 * @return the size of the list.
	 */
	public int getVarCount()
	{
		return m_refs.size();
	}
	
	/**
	 * Tell if the given qualified name occurs in 
	 * the list of qualified names collected.
	 * 
	 * @param refName Must be a valid qualified name.
	 * @return true if the list contains the qualified name.
	 */
	boolean doesOccur(QName refName)
	{
		return m_refs.contains(refName);
	}

	/**
	 * Visit a variable reference.
	 * @param owner The owner of the expression, to which the expression can 
	 *              be reset if rewriting takes place.
	 * @param var The variable reference object.
	 * @return true if the sub expressions should be traversed.
	 */
	public boolean visitVariableRef(ExpressionOwner owner, Variable var)
	{
		m_refs.addElement(var.getQName());
		return true;
	}

}
