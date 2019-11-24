package com.example.myapplication;

import android.os.Bundle;

import com.treasuredata.android.*;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {
    private TreasureData td;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Treasuredata mobile SDK related codes from here

        // Initilize SDK
        TreasureData.initializeDefaultApiKey("YOUR_API_KEY_HERE");
        TreasureData.initializeEncryptionKey("Hello World encryptyion");
        // TreasureData.sharedInstance.enableAutoAppendUniqId();


        // create a new instance
        td = new TreasureData(this);


        Map<String, Object> event = new HashMap<String, Object>();

        // Define the data to be sent
        event.put("data_type", "something");
        event.put("action", "Launched");
        event.put("mobile_type", "Galaxy S8");
        event.put("OS", "Android 8.0");
        event.put("Name", "Kohki Sato");

        // Append Auto Unique ID
        td.enableAutoAppendUniqId();

        // Append Model Information
        td.enableAutoAppendModelInformation();

        // Append application package version information
        td.enableAutoAppendAppInformation();

        // Apend locale information
        td.enableAutoAppendLocaleInformation();


        // create data events and upload to TD
        td.addEvent("aws_s3_sato","mobile_sdk_android", event);
        td.uploadEvents();

        // create data event with callback function
        td.addEventWithCallback("aws_s3_sato", "mobile_sdk_android", event, new TDCallback() {
            @Override
            public void onSuccess() {
                Log.i("ExampleApp", "Treasuredata mobile SDK DEBUG message: success!");
            }

            @Override
            public void onError(String errorCode, Exception e) {
                Log.w("ExampleApp", "errorCode: " + errorCode + ", detail: " + e.toString());
            }
        });

        // Enable logging
        TreasureData.enableLogging();

        // Enable to capture life cycle event https://github.com/treasure-data/td-android-sdk#track-app-lifecycle-events-automatically
        td.enableAppLifecycleEvent();

        // Enable app purchase event (https://github.com/treasure-data/td-android-sdk#track-in-app-purchase-events-automatically)
        // track the record via Google play store purchase record?
        td.enableInAppPurchaseEvent();





        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

