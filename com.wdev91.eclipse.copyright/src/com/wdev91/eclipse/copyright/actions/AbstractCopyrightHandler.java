/*******************************************************************************
 * Copyright (c) 2023 Patrick Ziegler.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Patrick Ziegler - initial API and implementation
 ******************************************************************************/
package com.wdev91.eclipse.copyright.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;

import com.wdev91.eclipse.copyright.model.CopyrightManager;
import com.wdev91.eclipse.copyright.model.ProjectPreferences;

public class AbstractCopyrightHandler {

  /**
   * Computes a list of all distinct projects containing the given resources.
   * 
   * @param resources A list of workspace resources.
   * @return An immutable list of workspace projects.
   */
  protected static List<IProject> getAllProjects(Iterable<?> resources) {
    Set<IProject> result = new LinkedHashSet<>();

    for (Object object : resources) {
      IResource resource = Adapters.adapt(object, IResource.class);

      result.add(resource.getProject());
    }

    return List.copyOf(result);
  }

  /**
   * Recursively computes a list of all distinct files contained by the given list
   * of resources, in the order in which they are traversed. If a file is visited
   * multiple times, only the first occurrence is recored.
   * 
   * @param resources A collection of workspace resources.
   * @return An immutable list of workspace files.
   * @throws CoreException If one of the member resources couldn't be accessed.
   */
  protected static List<IFile> getAllFiles(Iterable<?> resources) throws CoreException {
    Set<IFile> result = new LinkedHashSet<>();

    for (Object object : resources) {
      IResource resource = Adapters.adapt(object, IResource.class);

      if (resource instanceof IFile) {
        result.add((IFile) resource);
      } else if (resource instanceof IContainer) {
        result.addAll(getAllFiles((IContainer) resource));
      }
    }

    return List.copyOf(result);
  }

  /**
   * Recursively computes a list of all files contained by the given container, in
   * the order in which they are traversed.
   * 
   * @param container A workspace container.
   * @return An immutable list of workspace files.
   * @throws CoreException If the member resources of the container couldn't be
   *                       accessed.
   */
  protected static List<IFile> getAllFiles(IContainer container) throws CoreException {
    List<IResource> children = List.of(container.members(IContainer.EXCLUDE_DERIVED));
    List<IFile> result = new ArrayList<>();

    result.addAll(getAllFiles(children));

    return Collections.unmodifiableList(result);
  }

  /**
   * Checks whether at least of the given projects declares a custom header text.
   * Only if all projects specify such a property can the wizard be skipped.
   * 
   * @param projects A list of workspace projects
   * @return {@code true}, if the copyright wizard should be opened.
   */
  protected static boolean shouldOpenCopyrightWizard(Collection<IProject> projects) {
    return projects.stream().anyMatch(AbstractCopyrightHandler::shouldOpenCopyrightWizard);
  }

  /**
   * Checks whether the given project declares a custom header text. If such a
   * property exists, we can skip the wizard and directly perform the copyright
   * update.
   * 
   * @param project A workspace project.
   * @return {@code true}, if the copyright wizard should be opened.
   */
  protected static boolean shouldOpenCopyrightWizard(IProject project) {
    ProjectPreferences prefs = CopyrightManager.getProjectPreferences(project);

    return prefs == null || prefs.getHeaderText() == null;
  }
}
