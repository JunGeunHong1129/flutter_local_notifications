package com.dexterous.flutterlocalnotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.dexterous.flutterlocalnotifications.isolate.IsolatePreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.EventChannel.EventSink;
import io.flutter.plugin.common.EventChannel.StreamHandler;
import io.flutter.view.FlutterCallbackInformation;
import io.flutter.view.FlutterMain;

public class ActionBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION_TAPPED = "com.dexterous.flutterlocalnotifications.ActionBroadcastReceiver.ACTION_TAPPED";

    @Nullable
    private static ActionEventSink actionEventSink;

    @Nullable
    private static FlutterEngine engine;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("onReceive","received");
        final Map<String, Object> record = (HashMap<String, Object>) intent.getSerializableExtra("record");

//        final Map<String, Object> action = new HashMap<>();
//        action.put("id",id);
        if(actionEventSink == null) actionEventSink = new ActionEventSink();

        actionEventSink.addItem(record);

        startEngine(context);
    }

    private static class ActionEventSink implements StreamHandler {

        final List<Map<String, Object>> cache = new ArrayList<>();

        @Nullable
        private EventSink eventSink;

        public void addItem(Map<String, Object> item){
            if (eventSink != null){
                eventSink.success(item);
            }else{
                cache.add(item);
            }
        }

        @Override
        public void onListen(Object arguments, EventSink events) {
            Log.d("onListen","start to listen");
            for(Map<String, Object> item : cache)
                events.success(item);

            cache.clear();
            eventSink = events;
        }

        @Override
        public void onCancel(Object arguments) { eventSink=null; }
    }

    private void startEngine(Context context) {
        Log.d("onStartEngine","engine is began");
        long dispatcherHandle = IsolatePreferences.getCallbackDispatcherHandle(context);

        if(dispatcherHandle != -1L && engine == null) {
            engine = new FlutterEngine(context);
            FlutterMain.ensureInitializationComplete(context, null);

            FlutterCallbackInformation callbackInformation = FlutterCallbackInformation.lookupCallbackInformation(dispatcherHandle);
            String dartBundlePath = FlutterMain.findAppBundlePath();

            EventChannel channel = new EventChannel(engine.getDartExecutor().getBinaryMessenger(),"dexterous.com/flutter/local_notifications/actions");
            channel.setStreamHandler(actionEventSink);

            engine.getDartExecutor().executeDartCallback(new DartExecutor.DartCallback(context.getAssets(),dartBundlePath,callbackInformation));

        }

    }
}
