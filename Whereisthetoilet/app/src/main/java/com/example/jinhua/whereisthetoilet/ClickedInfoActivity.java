package com.example.jinhua.whereisthetoilet;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ClickedInfoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_clicked_info);

        //Get toiletName from intent
        Intent intent = getIntent();

        String nameToilet = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");

        TextView textView = new TextView(this);
        TextView textViewDescription = new TextView(this);


        textView.setTextSize(15);
        textViewDescription.setTextSize(13);

        textView.setText(nameToilet);
        textViewDescription.setText(description);

        setContentView(textView);
        setContentView(textViewDescription);
    }


}
