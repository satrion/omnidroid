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
package edu.nyu.cs.omnidroid.app.model;

/**
 * This class represents an General{@code Log}. Logs are displayed on the ActivityLogs for
 * users to see what is going on.
 */
public class GeneralLog extends Log {
  public static final String TAG = GeneralLog.class.getSimpleName();

  // Log level (higher is less severe, more informational) see {@ Logger} for details
  protected int level;
  
  /**
   * @param text
   *          to create a GeneralLog of
   * 
   */
  public GeneralLog(String text, int level) {
    super();
    this.text = text;
    this.level = level;  
  }

  /**
   * Copy constructor
   * 
   * @param log
   *          GeneralLog to duplicate
   * 
   */
  public GeneralLog(GeneralLog log) {
    super(log);
    this.level = log.level;
  }

  /**
   * Create a Log item that stores relevant general log data.
   * 
   * @param id
   *          the database id for this log entry
   * @param timeStamp
   *          the time stamp of the action taken.
   * @param text
   *          a textual description of the Log
   */
  public GeneralLog(long id, long timestamp, String text, int level) {
    super(id, timestamp, text);
    this.level = level;
  }

  public String toString() {
    return "ID: " + id + "\n" + "Timestamp: " + timestamp + "\nLevel: " + level + "\nText: " + text;
  }

  public void setLevel(int level) {
    this.level = level;
  }
  
  public int getLevel() {
    return level;
  }
}