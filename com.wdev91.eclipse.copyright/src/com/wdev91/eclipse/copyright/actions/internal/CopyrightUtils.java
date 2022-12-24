/*******************************************************************************
 * Copyright (c) 2022 Patrick Ziegler.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Patrick Ziegler - initial API and implementation
 ******************************************************************************/
package com.wdev91.eclipse.copyright.actions.internal;

import java.util.Collection;

import org.eclipse.core.resources.IProject;

import com.wdev91.eclipse.copyright.model.CopyrightManager;
import com.wdev91.eclipse.copyright.model.ProjectPreferences;

/**
 * Utility class for project-related copyright operations.
 */
public abstract class CopyrightUtils {

  private CopyrightUtils() {
  }

  /**
   * Checks whether at least of the given projects declares a custom header text.
   * Only if all projects specify such a property can the wizard be skipped.
   * 
   * @param projects A list of workspace projects
   * @return {@code true}, if the copyright wizard should be opened.
   */
  public static boolean shouldOpenCopyrightWizard(Collection<IProject> projects) {
    return projects.stream().anyMatch(CopyrightUtils::shouldOpenCopyrightWizard);
  }

  /**
   * Checks whether the given project declares a custom header text. If such a
   * property exists, we can skip the wizard and directly perform the copyright
   * update.
   * 
   * @param project A workspace project.
   * @return {@code true}, if the copyright wizard should be opened.
   */
  public static boolean shouldOpenCopyrightWizard(IProject project) {
    ProjectPreferences prefs = CopyrightManager.getProjectPreferences(project);

    return prefs == null || prefs.getHeaderText() == null;
  }
}
