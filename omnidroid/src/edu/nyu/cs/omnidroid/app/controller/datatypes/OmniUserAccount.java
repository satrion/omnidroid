/*******************************************************************************
 * Copyright 2010 Omnidroid - http://code.google.com/p/omnidroid 
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
 *******************************************************************************/

package edu.nyu.cs.omnidroid.app.controller.datatypes;

/**
 * Place holder for the user account datatype. Currently, there is no meaning in the {@code value}
 * held by this class, but it can be used in the future as an identifier to a particular user
 * account.
 */
public class OmniUserAccount extends DataType {
  private final String value;

  /* data type name to be stored in db */
  public static final String DB_NAME = "UserAccount";

  public OmniUserAccount(String accountID) {
    value = accountID;
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.nyu.cs.omnidroid.core.datatypes.DataType#matchFilter(java.lang.String,
   * java.lang.String)
   */
  public boolean matchFilter(DataType.Filter filter, DataType userDefinedValue)
      throws IllegalArgumentException {
    throw new IllegalArgumentException("Matching filter not found for the datatype "
        + userDefinedValue.getClass().toString() + ". ");
  }

  public String toString() {
    return value;
  }

  public String getValue() {
    return value;
  }
}
