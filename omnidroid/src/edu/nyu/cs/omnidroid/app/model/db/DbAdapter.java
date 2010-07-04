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
package edu.nyu.cs.omnidroid.app.model.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * A generic class of all database adapter of Omnidroid.
 */
public class DbAdapter {

  protected SQLiteDatabase database;

  /**
   * Constructor.
   * 
   * @param database
   *          is the database object to work within.
   */
  public DbAdapter(SQLiteDatabase database) {
    this.database = database;
  }

}
