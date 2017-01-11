package com.example.jinhua.whereisthetoilet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;


public class MainActivity extends Activity implements OnClickListener{

    public static final String LOG_TAG = "Where is the toitlet yooo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.btn_search)).setOnClickListener(this);
    }

    @Override
    public void onResume(){
        super.onResume();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        TextView welcomeTextView = (TextView) findViewById(R.id.textView);

        String defaultWelcomeText = getResources().getString(R.string.hello_world);
        //String welcomeText = preferences.getString("welcome_text", defaultWelcomeText);
        welcomeTextView.setText(defaultWelcomeText);

        //Show de text when true
        boolean showText = preferences.getBoolean("show_welcome_text", true);

        if(showText)
            welcomeTextView.setVisibility(View.VISIBLE);
        else{
            welcomeTextView.setVisibility(View.INVISIBLE);
        }

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

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {

            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));

        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_search:
                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
                break;
//
//            case R.id.btn_info:
//                Log.d(LOG_TAG, "Hij doet het" + "Ohjee");
//                break;
        }
    }
}
