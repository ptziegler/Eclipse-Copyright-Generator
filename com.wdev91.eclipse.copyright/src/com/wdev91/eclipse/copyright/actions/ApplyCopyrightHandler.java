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
package com.wdev91.eclipse.copyright.actions;

import static org.eclipse.e4.ui.services.IServiceConstants.ACTIVE_SHELL;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.resources.IProject;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.wdev91.eclipse.copyright.actions.internal.ResourcesUtils;
import com.wdev91.eclipse.copyright.wizards.ApplyCopyrightWizard;

/**
 * Opens the wizard to check and apply a copyright header in selected resource
 * files of a given project.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class ApplyCopyrightHandler {
  private static final String PACKAGE_EXPLORER_PART_ID = "org.eclipse.jdt.ui.PackageExplorer";

  @Inject
  private ESelectionService selectionService;

  @Execute
  public void execute(@Named(ACTIVE_SHELL) Shell shell) {
    ApplyCopyrightWizard.openWizard(shell, getSelectedProjects());
  }

  private List<IProject> getSelectedProjects() {
    return ResourcesUtils.getAllProjects(getSelection());
  }

  private IStructuredSelection getSelection() {
    Object selection = selectionService.getSelection(PACKAGE_EXPLORER_PART_ID);

    return selection instanceof IStructuredSelection ? (IStructuredSelection) selection : StructuredSelection.EMPTY;
  }
}
