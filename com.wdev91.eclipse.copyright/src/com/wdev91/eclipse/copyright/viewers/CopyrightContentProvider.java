/*******************************************************************************
 * Copyright (c) 2008-2012 Eric Wuillai.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Eric Wuillai - initial API and implementation
 ******************************************************************************/
package com.wdev91.eclipse.copyright.viewers;

import org.eclipse.jface.viewers.IStructuredContentProvider;

public class CopyrightContentProvider implements IStructuredContentProvider {
  public Object[] getElements(Object inputElement) {
    return inputElement instanceof CopyrightsInput ? ((CopyrightsInput) inputElement).getCopyrights() : new Object[] {};
  }
}
