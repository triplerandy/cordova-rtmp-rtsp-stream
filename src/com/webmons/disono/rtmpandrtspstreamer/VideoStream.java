package com.webmons.disono.rtmpandrtspstreamer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Author: Archie, Disono (webmonsph@gmail.com)
 * Website: http://www.webmons.com
 *
 * Created at: 1/09/2018
 */

/**
 * Wowza Configuration
 *
 * Config
 * http://your-ip/enginemanager
 * https://www.wowza.com/docs/how-to-set-up-live-streaming-using-an-rtmp-based-encoder
 * https://www.wowza.com/docs/how-to-set-up-live-streaming-using-a-native-rtp-encoder-with-sdp-file
 *
 * Server URL: rtmp://[wowza-ip-address]/live
 * Stream Name: myStream
 * User: publisherName
 * password: [password]
 *
 * Verify your server or endpoint using ffmpeg
 * ffmpeg -i pathtomp4file -f flv rtmp://yourendpoint
 */

public class VideoStream extends CordovaPlugin {
	private CallbackContext callbackContext;
    private Activity activity;
	
	@Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        // application context
        activity = cordova.getActivity();
        this.callbackContext = callbackContext;

        String url;
        PluginResult pluginResult;

        String username;
        String password;

        switch (action) {
            case "streamRTSP":
                url = args.getString(0);
				_startRTSP(url, null, null);

                // Don't return any result now
                pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
                pluginResult.setKeepCallback(true);
                callbackContext.sendPluginResult(pluginResult);

                return true;
			case "streamRTSPAuth":
                url = args.getString(0);
                username = args.getString(1);
                password = args.getString(2);
				_startRTSP(url, username, password);

                // Don't return any result now
                pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
                pluginResult.setKeepCallback(true);
                callbackContext.sendPluginResult(pluginResult);

                return true;
            case "streamRTMP":
                url = args.getString(0);
				_startRTMP(url, null, null);

                // Don't return any result now
                pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
                pluginResult.setKeepCallback(true);
                callbackContext.sendPluginResult(pluginResult);

                return true;
			case "streamRTMPAuth":
                url = args.getString(0);
                username = args.getString(1);
                password = args.getString(2);
				_startRTMP(url, username, password);

                // Don't return any result now
                pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
                pluginResult.setKeepCallback(true);
                callbackContext.sendPluginResult(pluginResult);

                return true;
            case "streamStop":
                _filters("stop");

                return true;
        }

        return false;
    }

    @Override
    public void onPause(boolean p) {
        super.onPause(p);
        _filters("stop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _filters("stop");
    }

    private void _startRTSP(String uri, String username, String password)
    {
        Intent intent = new Intent(activity, RTSPActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        intent.putExtra("url", uri);
        activity.startActivity(intent);
    }

    private void _startRTMP(String uri, String username, String password)
    {
        Intent intent = new Intent(activity, RTMPActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        intent.putExtra("url", uri);
        activity.startActivity(intent);
    }

    private void _filters(String methodName) {
        Intent intent = new Intent();
        intent.setAction("com.webmons.disono.rtmpandrtspstreamer");
        intent.putExtra("method", methodName);
        activity.sendBroadcast(intent);
    }
}
