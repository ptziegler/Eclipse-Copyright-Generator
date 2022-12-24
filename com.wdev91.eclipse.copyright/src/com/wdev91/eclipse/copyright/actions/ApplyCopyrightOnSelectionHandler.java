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

import static org.eclipse.e4.ui.services.IServiceConstants.ACTIVE_SELECTION;
import static org.eclipse.e4.ui.services.IServiceConstants.ACTIVE_SHELL;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;

import com.wdev91.eclipse.copyright.Messages;
import com.wdev91.eclipse.copyright.actions.internal.CopyrightUtils;
import com.wdev91.eclipse.copyright.actions.internal.ResourcesUtils;
import com.wdev91.eclipse.copyright.model.CopyrightManager;
import com.wdev91.eclipse.copyright.wizards.ApplyCopyrightWizard;

/**
 * Apply copyright... command. Allow to apply a copyright on selected resources
 * from a popup menu. Mainly concern Eclipse navigator and package explorer.
 */
public class ApplyCopyrightOnSelectionHandler {
  @Inject
  @Named(ACTIVE_SHELL)
  private Shell shell;

  @Execute
  public void execute(@Named(ACTIVE_SELECTION) IStructuredSelection selection) throws CoreException {
    // Creates list of selected files
    List<IFile> resources = ResourcesUtils.getAllFiles(selection);

    // List of projects containing the selected files
    List<IProject> projects = ResourcesUtils.getAllProjects(resources);

    // Apply the copyrights
    if (CopyrightUtils.shouldOpenCopyrightWizard(projects)) {
      ApplyCopyrightWizard.openWizard(shell, projects, resources);
    } else {
      String title = Messages.ApplyCopyrightOnSelectionHandler_messageTitle;
      String message = NLS.bind(Messages.ApplyCopyrightOnSelectionHandler_confirmMessage, resources.size());

      if (MessageDialog.openConfirm(shell, title, message)) {
        CopyrightManager.applyCopyrightJob(resources);
      }
    }

  }

  @CanExecute
  public boolean canExecute(@Optional @Named(ACTIVE_SELECTION) IStructuredSelection selection) {
    if (selection == null || selection.isEmpty()) {
      return false;
    }

    for (Object resource : selection) {
      if (Adapters.adapt(resource, IResource.class) == null || resource instanceof IProject) {
        return false;
      }
    }

    return true;
  }

  @Evaluate
  public boolean isVisible(@Optional @Named(ACTIVE_SELECTION) IStructuredSelection selection) {
    return canExecute(selection);
  }
}
