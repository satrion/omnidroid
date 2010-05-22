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
package edu.nyu.cs.omnidroid.app.external.attributes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import edu.nyu.cs.omnidroid.app.core.TimeTickEvent;

/**
 * Monitors Time, send out intent every 1 minute . @see android.intent.action.TIME_TICK
 * This is different from other systemServiceEventMonitor, it gets intent directly from system.
 * The purpose of this monitor is that we can get Time as a stand alone event, so we can create
 * rules like "when time is between 3 and 5, turn off ring..."
 */
public class TimeMonitor extends BroadcastReceiver implements SystemServiceEventMonitor {
  private static final String SYSTEM_SERVICE_NAME = "TIME_SERVICE";
  private static final String MONITOR_NAME = "TimeMonitor";

  private Context context;

  public TimeMonitor(Context context) {
    this.context = context;
  }

  public void init() {
    IntentFilter intentFilter = new IntentFilter(Intent.ACTION_TIME_TICK );
    context.registerReceiver(this, intentFilter);
  }

  /**
   * In this monitor, we only receive intent, no need to do anything here
   */
  public void stop() {
    return;
  }

  public String getMonitorName() {
    return MONITOR_NAME;
  }

  public String getSystemServiceName() {
    return SYSTEM_SERVICE_NAME;
  }

  @Override
  public void onReceive(Context arg0, Intent arg1) {
    Log.d("TimeMonitor", "Intent received");
    Intent intent = new Intent(TimeTickEvent.ACTION_NAME);
    context.sendBroadcast(intent);    
  }
}
