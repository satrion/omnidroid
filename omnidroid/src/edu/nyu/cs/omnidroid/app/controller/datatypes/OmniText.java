/*******************************************************************************
 * Copyright 2009 Omnidroid - http://code.google.com/p/omnidroid 
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

import edu.nyu.cs.omnidroid.app.controller.util.DataTypeValidationException;

/**
 * Provides data type that can be used to provide text filters.
 */
public class OmniText extends DataType {
  private String value;
  
  /* data type name to be stored in db */
  public static final String DB_NAME = "Text";
  
  public enum Filter implements DataType.Filter {
    CONTAINS("contains"), EQUALS("equals");
    
    public final String displayName;
    
    Filter(String displayName) {
     this.displayName = displayName; 
    }
  }

  public OmniText(String str) {
    this.value = str;
  }

  public OmniText(Object obj) {
    this.value = obj.toString();
  }

  /**
   * 
   * @param str
   *          the filter name.
   * @return Filter
   * @throws IllegalArgumentException
   *           when the filter with the given name does not exist.
   */
  public static Filter getFilterFromString(String str) throws IllegalArgumentException {
    return Filter.valueOf(str.toUpperCase());
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.nyu.cs.omnidroid.core.datatypes.DataType#matchFilter(DataType.Filter, DataType)
   */
  public boolean matchFilter(DataType.Filter filter, DataType userDefinedValue)
      throws IllegalArgumentException {
    if (!(filter instanceof Filter)) {
      throw new IllegalArgumentException("Invalid filter type '" + filter.toString()
          + "' provided.");
    }
    if(userDefinedValue instanceof OmniText){
      return matchFilter((Filter) filter, (OmniText) userDefinedValue);
    }
    throw new IllegalArgumentException("Matching filter not found for the datatype " + 
        userDefinedValue.getClass().toString()+ ". ");
  }

  public boolean matchFilter(Filter filter, OmniText comparisonValue) {
    switch (filter) {
    case CONTAINS:
      return value.toLowerCase().contains(comparisonValue.toString().toLowerCase());
    case EQUALS:
      return value.equalsIgnoreCase(comparisonValue.toString());
    default:
      return false;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.nyu.cs.omnidroid.core.datatypes.DataType#validateUserDefinedValue(java.lang.String,
   * java.lang.String)
   */
  public static void validateUserDefinedValue(DataType.Filter filter, String userInput)
      throws DataTypeValidationException, IllegalArgumentException {
    if (!(filter instanceof Filter)) {
      throw new IllegalArgumentException("Invalid filter type '" + filter.toString()
          + "' provided.");
    }
    if (userInput == null) {
      throw new DataTypeValidationException("The user input cannot be null.");
    }
  }

  /**
   * Indicates whether or not the given filter is supported by the data type.
   * 
   * @param filter
   * @return true if the filter is supported, false otherwise.
   */
  public static boolean isValidFilter(String filter) {
    try {
      getFilterFromString(filter);
    } catch (IllegalArgumentException e) {
      return false;
    }
    return true;
  }

  public String getValue() {
    return this.value;
  }

  public String toString() {
    return this.value;
  }
}
