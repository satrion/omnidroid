package edu.nyu.cs.omnidroid.core;

import java.util.Date;

import edu.nyu.cs.omnidroid.core.datatypes.OmniDate;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.util.Log;

/**
 * This class encapsulates an SMS event. It wraps the intent that triggered this event and provides
 * access to any attribute data associated with it.
 */
public class SMSReceivedEvent extends Event {
  /** Event name (to match record in database) */
  public static final String APPLICATION_NAME = "SMS";
  public static final String EVENT_NAME = "SMS Received";

  /** Attribute field names */
  public static final String ATTRIB_PHONE_NO = "SMS Phonenumber";
  public static final String ATTRIB_MESSAGE_TEXT = "SMS Text";
  public static final String ATTRIB_MESSAGE_TIME = "SMS Time";

  /** Cache any values that are requested because it is likely they will be asked for again */
  protected String phoneNumber;
  protected String messageText;
  protected String messageTime;

  /**
   * Constructs a new SMS object that holds an SMS event fired intent. This intent holds the data
   * needed to check the event against user defined rules.
   * 
   * @param intent
   *          the intent received when the SMS received event was fired by and external app
   */
  public SMSReceivedEvent(Intent intent) {
    super(APPLICATION_NAME, EVENT_NAME, intent);
    Date date = new Date(System.currentTimeMillis());
    OmniDate omniDate = new OmniDate(date);
    messageTime = omniDate.toString();
    Log.d("SMSReceivedEvent", "The message received at : " + messageTime);
  }

  /**
   * Looks up attributes associated with this event.
   * 
   * @param attributeName
   *          the name of the attribute associated with this event
   * @return the data associated with the attribute
   * @throws IllegalArgumentException
   *           if the attribute is not of a type supported by this event
   */
  @Override
  public String getAttribute(String attributeName) {
    if (attributeName.equals(ATTRIB_PHONE_NO)) {
      if (phoneNumber == null) {
        getMessageData();
      }
      return phoneNumber;
    } else if (attributeName.equals(ATTRIB_MESSAGE_TEXT)) {
      if (messageText == null) {
        getMessageData();
      }
      return messageText;
    } else if (attributeName.equals(ATTRIB_MESSAGE_TIME)) {
      return messageTime;
    } else {
      throw (new IllegalArgumentException());
    }
    // TODO(londinop): Add exception for invalid data field name
  }

  /**
   * Examines the Protocol Description Unit (PDU) data in the text message intent to reconstruct the
   * phone number and text message data. Caches the information in global variables in case they are
   * needed again.<br>
   * TODO(londinop): Further test this method with texts longer than 160 characters, there may be a
   * bug in the emulator
   */
  private void getMessageData() {

    // TODO(londinop): Add text message data retrieval code and write a test for it
    Bundle bundle = intent.getExtras();
    Object[] pdusObj = (Object[]) bundle.get("pdus");

    // Create an array of messages out of the PDU byte stream
    SmsMessage[] messages = new SmsMessage[pdusObj.length];
    for (int i = 0; i < pdusObj.length; i++) {
      messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
    }
    // Get the sender phone number from the first message
    // TODO(londinop): Can there be multiple originating addresses in a single intent?
    phoneNumber = messages[0].getOriginatingAddress();

    // Concatenate all message texts into a single message (for texts longer than 160 characters)
    StringBuilder sb = new StringBuilder();
    for (SmsMessage currentMessage : messages) {
      sb.append(currentMessage.getDisplayMessageBody());
    }
    messageText = sb.toString();
  }
}
