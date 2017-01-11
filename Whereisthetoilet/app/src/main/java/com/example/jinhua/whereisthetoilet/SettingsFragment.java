package com.example.jinhua.whereisthetoilet;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Jinhua on 30-5-2015.
 */
public class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);


            //Laad de preference
            addPreferencesFromResource(R.xml.pref_general);
        }
}
