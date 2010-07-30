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
package edu.nyu.cs.omnidroid.app.controller.actions;

import java.util.HashMap;
import android.content.Intent;
import edu.nyu.cs.omnidroid.app.controller.Action;
import edu.nyu.cs.omnidroid.app.controller.util.ExceptionMessageMap;
import edu.nyu.cs.omnidroid.app.controller.util.OmnidroidException;

/**
 * This action wraps an intent to send to Twitter Update Status service. It contains necessary information for the
 * Twitter update service.
 */
public class UpdateTwitterStatusAction extends Action {

  //  attributes field names
  public static final String ACTION_NAME = "UPDATE TWITTER";
  public static final String APP_NAME = "Twitter";
  public static final String TWITTER_INTENT = "omnidroid.intent.action.TWITTER_UPDATE";

  // Username is no longer used as part of the action parameter.
  @Deprecated public static final String PARAM_USERNAME = "Username";
  // Password is no longer used as part of the action parameter.
  @Deprecated public static final String PARAM_PASSWORD = "Password";
  public static final String PARAM_USER_ACCOUNT = "UserAccount";
  public static final String PARAM_MESSAGE = "Message";
  
  // Content of the action
  private String accountID;
  private String message;
  
  public UpdateTwitterStatusAction(HashMap<String, String> parameters) throws OmnidroidException {
    super(UpdateTwitterStatusAction.TWITTER_INTENT, Action.BY_SERVICE);
    accountID = parameters.get(PARAM_USER_ACCOUNT);
    message = parameters.get(PARAM_MESSAGE);
    if (accountID == null || message == null) {
      throw new OmnidroidException(120002, ExceptionMessageMap.getMessage(new Integer(120002)
          .toString()));
    }
  }

  @Override
  public Intent getIntent() {
    Intent intent = new Intent(getActionName());
    intent.putExtra(PARAM_USER_ACCOUNT, accountID);
    intent.putExtra(PARAM_MESSAGE, message);
    intent.putExtra(DATABASE_ID, databaseId);
    intent.putExtra(ACTION_TYPE, actionType);
    intent.putExtra(NOTIFICATION, showNotification);
    return intent;
  }

  @Override
  public String getDescription() {
    return APP_NAME + "-" + ACTION_NAME;
  }

  public String getAppName() {
    return APP_NAME;
  }
}
