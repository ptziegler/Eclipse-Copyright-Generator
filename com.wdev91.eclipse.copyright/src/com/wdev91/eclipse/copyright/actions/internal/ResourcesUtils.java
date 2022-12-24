package com.wdev91.eclipse.copyright.actions.internal;

import java.util.ArrayList;
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

/**
 * Utility class for resource-related operations.
 */
public abstract class ResourcesUtils {
  private ResourcesUtils() {
  }

  /**
   * Computes a list of all distinct projects containing the given resources.
   * 
   * @param resources A list of workspace resources.
   * @return An immutable list of workspace projects.
   */
  public static List<IProject> getAllProjects(List<? extends IResource> resources) {
    Set<IProject> result = new LinkedHashSet<>();

    for (IResource resource : resources) {
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
  public static List<IFile> getAllFiles(Iterable<?> resources) throws CoreException {
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
  public static List<IFile> getAllFiles(IContainer container) throws CoreException {
    List<IResource> children = List.of(container.members(IContainer.EXCLUDE_DERIVED));
    List<IFile> result = new ArrayList<>();

    result.addAll(getAllFiles(children));

    return Collections.unmodifiableList(result);
  }
}
