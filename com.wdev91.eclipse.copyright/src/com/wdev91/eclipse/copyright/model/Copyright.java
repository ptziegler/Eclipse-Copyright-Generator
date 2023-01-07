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

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;

import com.wdev91.eclipse.copyright.Constants;

/**
 * Copyright definition.
 */
public class Copyright {
  /** Name of the copyright */
  private IObservableValue<String> label = new WritableValue<>();
  /** Text to insert as header of files */
  private IObservableValue<String> headerText = new WritableValue<>();
  /** File name of the license file */
  private IObservableValue<String> licenseFilename = new WritableValue<>();
  /** Text content of the license file */
  private IObservableValue<String> licenseText = new WritableValue<>();

  /**
   * Constructor. Creates a copyright with the given label.
   */
  public Copyright(String label) {
    this.label.setValue(label.trim());
    this.licenseFilename.setValue(Constants.EMPTY_STRING);
    this.licenseText.setValue(Constants.EMPTY_STRING);
  }

  public String getHeaderText() {
    return headerText.getValue();
  }

  public String getLabel() {
    return label.getValue();
  }

  public String getLicenseFilename() {
    return licenseFilename.getValue();
  }

  public String getLicenseText() {
    return licenseText.getValue();
  }

  public void setHeaderText(String headerText) {
    this.headerText.setValue(headerText);
  }

  public void setLabel(String label) {
    this.label.setValue(label.trim());
  }

  public void setLicenseFilename(String licenseFilename) {
    this.licenseFilename.setValue(licenseFilename.trim());
  }

  public void setLicenseText(String licenseText) {
    this.licenseText.setValue(licenseText);
  }
}
