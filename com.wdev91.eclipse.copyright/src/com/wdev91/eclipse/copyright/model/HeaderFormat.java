/*******************************************************************************
 * Copyright (c) 2008-2012, 2023 Eric Wuillai and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Eric Wuillai - initial API and implementation
 ******************************************************************************/
package com.wdev91.eclipse.copyright.model;

import java.util.regex.Pattern;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.runtime.content.IContentTypeManager;

import com.wdev91.eclipse.copyright.Constants;

public class HeaderFormat implements Cloneable {
  public static final String CT_CSOURCE = "org.eclipse.cdt.core.cSource"; //$NON-NLS-1$
  public static final String CT_CHEADER = "org.eclipse.cdt.core.cHeader"; //$NON-NLS-1$
  public static final String CT_CXXSOURCE = "org.eclipse.cdt.core.cxxSource"; //$NON-NLS-1$
  public static final String CT_CXXHEADER = "org.eclipse.cdt.core.cxxHeader"; //$NON-NLS-1$
  public static final String CT_JAVA = "org.eclipse.jdt.core.javaSource"; //$NON-NLS-1$
  public static final String CT_XML = "org.eclipse.core.runtime.xml"; //$NON-NLS-1$

  public static final HeaderFormat CSOURCE_HEADER;
  public static final HeaderFormat CHEADER_HEADER;
  public static final HeaderFormat CXXSOURCE_HEADER;
  public static final HeaderFormat CXXHEADER_HEADER;
  public static final HeaderFormat JAVA_HEADER;
  public static final HeaderFormat TEXT_HEADER;
  public static final HeaderFormat XML_HEADER;

  private IObservableValue<String> contentId = new WritableValue<>();
  private IObservableValue<Boolean> excluded = new WritableValue<>();
  private IObservableValue<Boolean> lineCommentFormat = new WritableValue<>();
  private IObservableValue<String> beginLine = new WritableValue<>();
  private IObservableValue<String> endLine = new WritableValue<>();
  private IObservableValue<String> linePrefix = new WritableValue<>();
  private IObservableValue<Integer> postBlankLines = new WritableValue<>();
  private IObservableValue<Boolean> preserveFirstLine = new WritableValue<>();
  private IObservableValue<String> firstLinePattern = new WritableValue<>();

  private Pattern pattern = null;

  static {
    CSOURCE_HEADER = new HeaderFormat(CT_CSOURCE, false,
        "/*******************************************************************************", //$NON-NLS-1$
        " * ", //$NON-NLS-1$
        " ******************************************************************************/", //$NON-NLS-1$
        0, false, false, null);

    CHEADER_HEADER = new HeaderFormat(CT_CHEADER, false,
        "/*******************************************************************************", //$NON-NLS-1$
        " * ", //$NON-NLS-1$
        " ******************************************************************************/", //$NON-NLS-1$
        0, false, false, null);

    CXXSOURCE_HEADER = new HeaderFormat(CT_CXXSOURCE, false,
        "////////////////////////////////////////////////////////////////////////////////", //$NON-NLS-1$
        "// ", //$NON-NLS-1$
        "////////////////////////////////////////////////////////////////////////////////", //$NON-NLS-1$
        0, true, false, null);

    CXXHEADER_HEADER = new HeaderFormat(CT_CXXHEADER, false,
        "////////////////////////////////////////////////////////////////////////////////", //$NON-NLS-1$
        "// ", //$NON-NLS-1$
        "////////////////////////////////////////////////////////////////////////////////", //$NON-NLS-1$
        0, true, false, null);

    JAVA_HEADER = new HeaderFormat(CT_JAVA, false,
        "/*******************************************************************************", //$NON-NLS-1$
        " * ", //$NON-NLS-1$
        " ******************************************************************************/", //$NON-NLS-1$
        0, false, false, null);

    TEXT_HEADER = new HeaderFormat(IContentTypeManager.CT_TEXT, false,
        "#-------------------------------------------------------------------------------", //$NON-NLS-1$
        "# ", //$NON-NLS-1$
        "#-------------------------------------------------------------------------------", //$NON-NLS-1$
        0, true, false, null);

    XML_HEADER = new HeaderFormat(CT_XML, false, "<!--", //$NON-NLS-1$
        "  ", //$NON-NLS-1$
        "-->", //$NON-NLS-1$
        0, false, true, "<\\?xml version=.*\\?>"); //$NON-NLS-1$
  }

  public HeaderFormat(String contentId) {
    this.contentId.setValue(contentId);
    this.excluded.setValue(false);
    this.lineCommentFormat.setValue(true);
    this.linePrefix.setValue(Constants.EMPTY_STRING);
    this.postBlankLines.setValue(0);
    this.preserveFirstLine.setValue(false);
  }

  private HeaderFormat(String contentId, boolean excluded, String beginLine, String linePrefix, String endLine,
      int postBlankLines, boolean lineCommentFormat, boolean preserveFirstLine, String firstLinePattern) {
    this.contentId.setValue(contentId);
    this.excluded.setValue(excluded);
    this.lineCommentFormat.setValue(lineCommentFormat);
    this.beginLine.setValue(beginLine);
    this.endLine.setValue(endLine);
    this.linePrefix.setValue(linePrefix);
    this.postBlankLines.setValue(postBlankLines);
    this.preserveFirstLine.setValue(preserveFirstLine);
    this.firstLinePattern.setValue(firstLinePattern);
  }

  @Override
  public HeaderFormat clone() {
    try {
      return (HeaderFormat) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

  static HeaderFormat createExcluded(String contentId) {
    HeaderFormat format = new HeaderFormat(contentId);
    format.setExcluded(true);
    return format;
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof HeaderFormat) ? ((HeaderFormat) obj).getContentId().equals(this.contentId) : false;
  }

  public boolean isExcluded() {
    return excluded.getValue();
  }

  public boolean isLineCommentFormat() {
    return lineCommentFormat.getValue();
  }

  public String getBeginLine() {
    return beginLine.getValue();
  }

  public String getContentId() {
    return contentId.getValue();
  }

  public String getEndLine() {
    return endLine.getValue();
  }

  public String getFirstLinePattern() {
    return firstLinePattern.getValue();
  }

  public String getLinePrefix() {
    return linePrefix.getValue();
  }

  public int getPostBlankLines() {
    return postBlankLines.getValue();
  }

  public boolean isPreserveFirstLine() {
    return preserveFirstLine.getValue();
  }

  public void setExcluded(boolean excluded) {
    this.excluded.setValue(excluded);
  }

  public void setLineCommentFormat(boolean lineCommentFormat) {
    this.lineCommentFormat.setValue(lineCommentFormat);
  }

  public void setBeginLine(String beginLine) {
    this.beginLine.setValue(beginLine);
  }

  public void setEndLine(String endLine) {
    this.endLine.setValue(endLine);
  }

  public void setFirstLinePattern(String firstLinePattern) {
    this.firstLinePattern.setValue(firstLinePattern);
    pattern = null;
  }

  public void setLinePrefix(String linePrefix) {
    this.linePrefix.setValue(linePrefix);
  }

  public void setPostBlankLines(int postBlankLines) {
    this.postBlankLines.setValue(postBlankLines);
  }

  public void setPreserveFirstLine(boolean preserveFirstLine) {
    this.preserveFirstLine.setValue(preserveFirstLine);
    pattern = null;
  }

  public boolean skipFirstLine(String line) {
    if (preserveFirstLine.getValue()) {
      if (firstLinePattern != null) {
        if (pattern == null) {
          pattern = Pattern.compile(firstLinePattern.getValue());
        }
        return pattern.matcher(line).matches();
      }
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    return this.getContentId();
  }
}
