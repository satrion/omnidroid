/*******************************************************************************
 * Copyright 2009, 2010 Omnidroid - http://code.google.com/p/omnidroid 
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
package edu.nyu.cs.omnidroid.app.controller.events;

import edu.nyu.cs.omnidroid.app.controller.Event;
import android.content.Intent;

/**
 * This class encapsulates an LocationChanged event. It wraps the intent that triggered this event
 * and provides access to any attribute data associated with it.
 */
public class LocationChangedEvent extends Event {
  /** Event name (to match record in database) */
  public static final String APPLICATION_NAME = "GPS";
  public static final String EVENT_NAME = "GPS Location Changed";
  public static final String ACTION_NAME = "LOCATION_CHANGED";

  /**
   * This constant is already deprecated, use the the global attribute
   * {@link Event#ATTRIBUTE_LOCATION} instead.
   */
  @Deprecated public static final String ATTRIBUTE_CURRENT_LOCATION = "Current Location";

  /** Cache any values that are requested because it is likely they will be asked for again */
  protected String location;

  /**
   * Constructs a new LocationChanged object that holds an LocationChanged event fired intent. This
   * intent holds the data needed to check the event against user defined rules.
   * 
   * @param intent
   *          the intent received when the LocationChanged event was fired
   */
  public LocationChangedEvent(Intent intent) {
    super(APPLICATION_NAME, EVENT_NAME, intent);
  }
}
