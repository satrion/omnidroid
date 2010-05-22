/*******************************************************************************
 * Copyright 2009 OmniDroid - http://code.google.com/p/omnidroid
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

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

/**
 * Database helper class for the RuleActions table. Defines basic CRUD methods. 
 * 
 * <p>
 * This table contains ruleActions associated with each rule.
 * FK_RuleID points to the rule it belongs to.
 * FK_ActionID points to the action it is going to fire.
 * </p>
 */
public class RuleActionDbAdapter extends DbAdapter {

  /* Column names */
  public static final String KEY_RULEACTIONID = "RuleActionID";
  public static final String KEY_RULEID = "FK_RuleID";
  public static final String KEY_ACTIONID = "FK_ActionID";

  /* An array of all column names */
  public static final String[] KEYS = { KEY_RULEACTIONID, KEY_RULEID, KEY_ACTIONID };

  /* Table name */
  private static final String DATABASE_TABLE = "RuleActions";

  /* Create and drop statement. */
  protected static final String DATABASE_CREATE = "create table " + DATABASE_TABLE + " ("
      + KEY_RULEACTIONID + " integer primary key autoincrement, " 
      + KEY_RULEID + " integer not null, " 
      + KEY_ACTIONID + " integer not null);";
  protected static final String DATABASE_DROP = "DROP TABLE IF EXISTS " + DATABASE_TABLE;

  /**
   * Constructor.
   * 
   * @param database
   *          is the database object to work within.
   */
  public RuleActionDbAdapter(SQLiteDatabase database) {
    super(database);
  }

  /**
   * Insert a new RuleAction record
   * 
   * @param ruleID
   *          is id of the rule it belongs to
   * @param actionID
   *          is id of its action type
   * @return RuleActionID or -1 if creation failed.
   * @throws IllegalArgumentException
   *           if there is null within parameters
   */
  public long insert(Long ruleID, Long actionID) {
    if (ruleID == null || actionID == null) {
      throw new IllegalArgumentException("insert parameter null.");
    }
    ContentValues initialValues = new ContentValues();
    initialValues.put(KEY_RULEID, ruleID);
    initialValues.put(KEY_ACTIONID, actionID);
    // Set null because don't use 'null column hack'.
    return database.insert(DATABASE_TABLE, null, initialValues);
  }

  /**
   * Delete a RuleAction record.
   * 
   * @param ruleActionID
   *          is the id of the record.
   * @return true if success, or false otherwise.
   * @throws IllegalArgumentException
   *           if ruleActionID is null
   */
  public boolean delete(Long ruleActionID) {
    if (ruleActionID == null) {
      throw new IllegalArgumentException("primary key null.");
    }
    // Set the whereArgs to null here.
    return database.delete(DATABASE_TABLE, KEY_RULEACTIONID + "=" + ruleActionID, null) > 0;
  }

  /**
   * Delete all RuleAction records.
   * 
   * @return true if success, or false if failed or nothing to be deleted.
   */
  public boolean deleteAll() {
    // Set where and whereArgs to null here.
    return database.delete(DATABASE_TABLE, null, null) > 0;
  }

  /**
   * Return a Cursor pointing to the record matches the ruleActionID.
   * 
   * @param ruleActionID
   *          is the id of the record to be fetched.
   * @return a Cursor pointing to the found record.
   * @throws IllegalArgumentException
   *           if ruleActionID is null
   */
  public Cursor fetch(Long ruleActionID) {
    if (ruleActionID == null) {
      throw new IllegalArgumentException("primary key null.");
    }
    // Set selectionArgs, groupBy, having, orderBy and limit to be null.
    Cursor mCursor = database.query(true, DATABASE_TABLE, KEYS, KEY_RULEACTIONID + "="
        + ruleActionID, null, null, null, null, null);
    if (mCursor != null) {
      mCursor.moveToFirst();
    }
    return mCursor;
  }

  /**
   * @return a Cursor that contains all RuleAction records.
   */
  public Cursor fetchAll() {
    // Set selections, selectionArgs, groupBy, having, orderBy to null to fetch all rows.
    return database.query(DATABASE_TABLE, KEYS, null, null, null, null, null);
  }

  /**
   * Return a Cursor that contains all RuleAction records which matches the parameters.
   * 
   * @param ruleID
   *          is id of the rule it belongs to, or null to fetch any.
   * @param actionID
   *          is id of its action type, or null to fetch any.
   * @return a Cursor that contains all RuleAction records which matches the parameters.
   */
  public Cursor fetchAll(Long ruleID, Long actionID) {
    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
    qb.setTables(DATABASE_TABLE);
    qb.appendWhere("1=1");
    if (ruleID != null) {
      qb.appendWhere(" AND " + KEY_RULEID + " = " + ruleID);
    }
    if (actionID != null) {
      qb.appendWhere(" AND " + KEY_ACTIONID + " = " + actionID);
    }

    // Not using additional selections, selectionArgs, groupBy, having, orderBy, set them to null.
    return qb.query(database, KEYS, null, null, null, null, null);
  }

  /**
   * Update a RuleAction record with specific parameters.
   * 
   * @param ruleActionID
   *          is id of the record to be updated.
   * @param ruleID
   *          is id of the rule it belongs to, or null if not updating it.
   * @param actionID
   *          is id of its action type, or null if not updating it.
   * @return true if success, or false otherwise.
   * @throws IllegalArgumentException
   *           if ruleActionID is null
   */
  public boolean update(Long ruleActionID, Long ruleID, Long actionID) {
    if (ruleActionID == null) {
      throw new IllegalArgumentException("primary key null.");
    }
    ContentValues args = new ContentValues();
    if (ruleID != null) {
      args.put(KEY_RULEID, ruleID);
    }
    if (actionID != null) {
      args.put(KEY_ACTIONID, actionID);
    }

    if (args.size() > 0) {
      // Set whereArg to null here
      return database.update(DATABASE_TABLE, args, KEY_RULEACTIONID + "=" + ruleActionID, null) > 0;
    }
    return false;
  }

}
