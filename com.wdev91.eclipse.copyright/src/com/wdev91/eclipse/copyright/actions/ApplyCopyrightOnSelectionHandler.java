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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import com.wdev91.eclipse.copyright.Messages;
import com.wdev91.eclipse.copyright.actions.internal.CopyrightUtils;
import com.wdev91.eclipse.copyright.actions.internal.ResourcesUtils;
import com.wdev91.eclipse.copyright.model.CopyrightManager;
import com.wdev91.eclipse.copyright.wizards.ApplyCopyrightWizard;

/**
 * Apply copyright... command. Allow to apply a copyright on selected resources
 * from a popup menu. Mainly concern Eclipse navigator and package explorer.
 */
public class ApplyCopyrightOnSelectionHandler extends AbstractHandler {
  public static final String COMMAND_ID = "com.wdev91.eclipse.copyright.ApplyCopyrightCommand"; //$NON-NLS-1$

  public Object execute(ExecutionEvent event) throws ExecutionException {
    IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getActiveMenuSelection(event);
    // Creates list of selected files
    List<IFile> resources = null;
    try {
      resources = ResourcesUtils.getAllFiles(selection);
    } catch (CoreException e) {
    }

    // List of projects containing the selected files
    List<IProject> projects = ResourcesUtils.getAllProjects(resources);

    // Apply the copyrights
    Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
    if (CopyrightUtils.shouldOpenCopyrightWizard(projects)) {
      ApplyCopyrightWizard.openWizard(shell, projects, resources);
    } else {
      String title = Messages.ApplyCopyrightOnSelectionHandler_messageTitle;
      String message = NLS.bind(Messages.ApplyCopyrightOnSelectionHandler_confirmMessage, resources.size());

      if (MessageDialog.openConfirm(shell, title, message)) {
        CopyrightManager.applyCopyrightJob(resources);
      }
    }

    return null;
  }
}
