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
package edu.nyu.cs.omnidroid.tests;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import edu.nyu.cs.omnidroid.R;
import edu.nyu.cs.omnidroid.core.CP;
import edu.nyu.cs.omnidroid.util.AGParser;
import edu.nyu.cs.omnidroid.util.UGParser;

/**
 * This class will present an interface for the user to input data that they want the
 * <code>ActionThrowerActions</code> to access.
 * 
 */
public class SaveDialog extends Activity implements OnClickListener {
  // Intent Data
  private EditText appData;
  private String eventApp;
  private String eventName;
  private String filterType;
  private String filterData;
  private String throwerApp;
  private String throwerName;
  private String instanceName;

  // Activity results
  private static final int RESULT_ADD_SUCCESS = 1;

  // Standard Menu options (Android menus require int, so no enums)
  private static final int MENU_HELP = 0;

  /**
   * Ask the user for data about the OmniHandler
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.save_dialog);

    // Get data passed to us
    Intent i = getIntent();
    Bundle extras = i.getExtras();
    eventApp = extras.getString(AGParser.KEY_APPLICATION);
    eventName = extras.getString(UGParser.KEY_EVENT_TYPE);
    // TODO(acase): Allow more than one filter
    filterType = extras.getString(UGParser.KEY_FILTER_TYPE);
    filterData = extras.getString(UGParser.KEY_FILTER_DATA);
    throwerApp = extras.getString(UGParser.KEY_ACTION_APP);
    throwerName = extras.getString(UGParser.KEY_ACTION_TYPE);

    // Setup our UI
    appData = (EditText) findViewById(R.id.save_dialog_name);
    // Button save = (Button) findViewById(R.id.save_dialog_save);

    // Listen for the save button click
    // save.setOnClickListener(this);
  }

  /*
   * (non-Javadoc) Add OmniHandler to OmniDroid if appropriate
   * 
   * @see android.view.View.OnClickListener#onClick(android.view.View)
   */
  public void onClick(View v) {
    // TODO: Pull out to it's own Save Dialog class
    LayoutInflater factory = LayoutInflater.from(this);
    final View textEntryView = factory.inflate(R.layout.save_dialog, null);
    Builder save_dialog = new AlertDialog.Builder(this);
    save_dialog.setIcon(android.R.drawable.ic_dialog_alert);
    save_dialog.setTitle(R.string.save_as);
    save_dialog.setView(textEntryView);
    save_dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int whichButton) {
      }
    });
    save_dialog.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int whichButton) {
        EditText v = (EditText) textEntryView.findViewById(R.id.save_dialog_name);
        instanceName = v.getText().toString();
        save();
      }
    });
    save_dialog.create();
    save_dialog.show();
  }

  // private void save(String iName) {
  private void save() {
    // Get data from our user
    String aData = appData.getText().toString();

    // Add OmniHandler to OmniDroid
    if (instanceName.length() < 1) {
      Toast.makeText(getBaseContext(), R.string.missing_name, Toast.LENGTH_SHORT).show();
    } else if (aData.length() < 1) {
      Toast.makeText(getBaseContext(), R.string.missing_data, Toast.LENGTH_SHORT).show();
    } else {
      // Add OmniHandler to the CP
      ContentValues values = new ContentValues();
      values.put("i_name", instanceName);
      values.put("a_data", aData);
      Uri uri = getContentResolver().insert(CP.CONTENT_URI, values);

      // Add OmniHandler to the UGConfig
      UGParser ug = new UGParser(getApplicationContext());
      HashMap<String, String> HM = new HashMap<String, String>();
      HM.put(UGParser.KEY_INSTANCE_NAME, instanceName);
      HM.put(UGParser.KEY_EVENT_TYPE, eventName);
      HM.put(UGParser.KEY_EVENT_APP, eventApp);
      HM.put(UGParser.KEY_ACTION_TYPE, throwerName);
      HM.put(UGParser.KEY_ACTION_APP, throwerApp);
      HM.put(UGParser.KEY_ENABLE_INSTANCE, "True");
      HM.put(UGParser.KEY_ACTION_DATA1, uri.toString());
      if ((filterType != null) && (filterData != null)) {
        HM.put(UGParser.KEY_FILTER_TYPE, filterType);
        HM.put(UGParser.KEY_FILTER_DATA, filterData);
      } else {
        HM.put(UGParser.KEY_FILTER_TYPE, "");
        HM.put(UGParser.KEY_FILTER_DATA, "");
      }
      ug.writeRecord(HM);

      // Added by Pradeep to restart the service to register new
      // IntentFilter
      Intent i = new Intent();
      i.setAction("OmniRestart");
      sendBroadcast(i);

      // Go back to our start page
      i = new Intent();
      setResult(RESULT_ADD_SUCCESS, i);
      finish();
    }
  }

  /**
   * Creates the options menu items
   * 
   * @param menu
   *          - the options menu to create
   */
  public boolean onCreateOptionsMenu(Menu menu) {
    menu.add(0, MENU_HELP, 0, R.string.help).setIcon(android.R.drawable.ic_menu_help);
    return true;
  }

  /**
   * Handles menu item selections
   */
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case MENU_HELP:
      Help();
      return true;
    }
    return false;
  }

  /**
   * Call our Help dialog
   */
  private void Help() {
    Builder help = new AlertDialog.Builder(this);
    // TODO(acase): Move to some kind of resource
    String help_msg = "Select data to pass to the application responding to the event.";
    help.setTitle(R.string.help);
    help.setIcon(android.R.drawable.ic_menu_help);
    help.setMessage(Html.fromHtml(help_msg));
    help.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int whichButton) {
      }
    });
    help.show();
  }
}