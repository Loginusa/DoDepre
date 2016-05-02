/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package id.loginusa.dosis.backgroundprocess.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import id.loginusa.dosis.MainActivity;
import id.loginusa.dosis.R;
import id.loginusa.dosis.GCMRegActivity;
import id.loginusa.dosis.util.Logging;


public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {

        String message = data.getString("message");
        String param = data.getString("param");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);
        Log.d(TAG, "new param: " + param);

        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
       // message = message +" <> "+ data.getString("success");
        //Log.e("Cek error message : ","success : "+data.getString("success")+" failure : "+data.getString("failure"));
        Logging.log('e',"Cek error message : ","success : "+data.getString("success")+" failure : "+data.getString("failure"));
//        response : {"multicast_id":6249000835845860064,"success":1,"failure":0,"canonical_ids":0,"results":[{"message_id":"0:1441450803086062%0f482e94f9fd7ecd"}]}jgcmdata : {"registration_ids":["fMTiT-c6G0s:APA91bHDE5DG3M5V4VAF7eSfHpC2Sxqxw5nEl6fjN1BZZlEnCfJYOtu3_f6WB_CETqPt1SjRPTXHzJFGMwXJBxVLP_YOw054niyyshmfQUt56W76RKYLx1NCk6BzRuSZ1mHCGqldofrG"],"data":{"message":"HELLOOOOOOOOOOOOOOO, TEST~","success":"true"}}
        sendNotification(message,param);
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message,String param) {
        Intent intent = new Intent(MyGcmListenerService.this, MainActivity.class); //kahade MainActivity
        intent.putExtra("param",param);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_loginusa_notif)
                .setContentTitle("GCM Message")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
