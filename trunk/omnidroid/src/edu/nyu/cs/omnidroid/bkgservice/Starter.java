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
package edu.nyu.cs.omnidroid.bkgservice;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import edu.nyu.cs.omnidroid.util.OmLogger;

/*Broadcast Receiver to detect system bootup*/
public class Starter extends BroadcastReceiver {
  private ComponentName comp = null;
  private ComponentName service = null;

  public void onReceive(Context context, Intent intent) {
    // If we're booting up, start our service
    if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())
        || "OmniStart".equals(intent.getAction())) {
      comp = new ComponentName(context.getPackageName(), BRService.class.getName());
      service = context.startService(new Intent().setComponent(comp));
      if (null == service) {
        Toast.makeText(context, "Failed to start OmniDroid Service", 5).show();
        Log.i("Starter", "Could not start service " + comp.toString());
        OmLogger.write(context, "Starter could not start Service");
      }
    } else if ("OmniRestart".equals(intent.getAction())) {
      comp = new ComponentName(context.getPackageName(), BRService.class.getName());
      context.stopService(new Intent().setComponent(comp));
      service = context.startService(new Intent().setComponent(comp));
      if (null == service) {
        Toast.makeText(context, "Failed to start OmniDroid Service", 5).show();
        Log.i("Starter", "Could not start service " + comp.toString());
        OmLogger.write(context, "Starter could not start Service");
      }
    }
  }
}