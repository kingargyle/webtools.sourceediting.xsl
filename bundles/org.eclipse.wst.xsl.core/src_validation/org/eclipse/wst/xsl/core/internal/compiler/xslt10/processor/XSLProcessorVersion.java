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
 * $Id: XSLProcessorVersion.java,v 1.1 2008/03/27 01:08:55 dacarver Exp $
 */
package org.eclipse.wst.xsl.core.internal.compiler.xslt10.processor;

/**
 * Administrative class to keep track of the version number of
 * the Xalan release.
 * <P>See also: org/apache/xalan/res/XSLTInfo.properties</P>
 * @deprecated To be replaced by org.apache.xalan.Version.getVersion()
 * @xsl.usage general
 */
public class XSLProcessorVersion
{

  /**
   * Print the processor version to the command line.
   *
   * @param argv command line arguments, unused.
   */
  public static void main(String argv[])
  {
    System.out.println(S_VERSION);
  }

  /**
   * Constant name of product.
   */
  public static final String PRODUCT = "Xalan";

  /**
   * Implementation Language.
   */
  public static final String LANGUAGE = "Java";

  /**
   * Major version number.
   * Version number. This changes only when there is a
   *          significant, externally apparent enhancement from
   *          the previous release. 'n' represents the n'th
   *          version.
   *
   *          Clients should carefully consider the implications
   *          of new versions as external interfaces and behaviour
   *          may have changed.
   */
  public static final int VERSION = 2;

  /**
   * Release Number.
   * Release number. This changes when:
   *            -  a new set of functionality is to be added, eg,
   *               implementation of a new W3C specification.
   *            -  API or behaviour change.
   *            -  its designated as a reference release.
   */
  public static final int RELEASE = 7;

  /**
   * Maintenance Drop Number.
   * Optional identifier used to designate maintenance
   *          drop applied to a specific release and contains
   *          fixes for defects reported. It maintains compatibility
   *          with the release and contains no API changes.
   *          When missing, it designates the final and complete
   *          development drop for a release.
   */
  public static final int MAINTENANCE = 0;

  /**
   * Development Drop Number.
   * Optional identifier designates development drop of
   *          a specific release. D01 is the first development drop
   *          of a new release.
   *
   *          Development drops are works in progress towards a
   *          compeleted, final release. A specific development drop
   *          may not completely implement all aspects of a new
   *          feature, which may take several development drops to
   *          complete. At the point of the final drop for the
   *          release, the D suffix will be omitted.
   *
   *          Each 'D' drops can contain functional enhancements as
   *          well as defect fixes. 'D' drops may not be as stable as
   *          the final releases.
   */
  public static final int DEVELOPMENT = 0;
  
  /**
   * Version String like <CODE>"<B>Xalan</B> <B>Language</B>
   * v.r[.dd| <B>D</B>nn]"</CODE>.
   * <P>Semantics of the version string are identical to the Xerces project.</P>
   */
  public static final String S_VERSION = PRODUCT+" "+LANGUAGE+" "
                                   +VERSION+"."+RELEASE+"."
                                   +(DEVELOPMENT > 0 ? ("D"+DEVELOPMENT)
                                     : (""+MAINTENANCE));

}