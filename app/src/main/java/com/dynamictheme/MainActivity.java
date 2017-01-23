package com.dynamictheme;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Transition;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout layout_green, layout_purple;
    private SwitchCompat switch_dark;
    private boolean isDarkTheme;
    private static final String Theme_Current = "ThemeCurrent";
    private static final String Dark_Theme = "DarkTheme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

            //set the transition
            Transition ts = new Explode();
            ts.setDuration(5000);
            getWindow().setEnterTransition(ts);
            getWindow().setExitTransition(ts);
        }

        super.onCreate(savedInstanceState);

        setAppTheme();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskStackBuilder.create(MainActivity.this)
                        .addNextIntent(new Intent(MainActivity.this, MainActivity.class))
                        .addNextIntent(getIntent())
                        .startActivities();

            }
        });


        switch_dark = (SwitchCompat) findViewById(R.id.switch_darkTheme);
        layout_green = (LinearLayout) findViewById(R.id.Layout_green);
        layout_purple = (LinearLayout) findViewById(R.id.Layout_purple);


        switch_dark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    isDarkTheme = true;
                    MainController.preferencePutBoolean(Dark_Theme, true);
                } else {
                    isDarkTheme = false;
                    MainController.preferencePutBoolean(Dark_Theme, false);
                }

            }
        });

        if (MainController.preferenceGetBoolean(Dark_Theme, false)) {
            switch_dark.setChecked(true);
        }


        layout_green.setOnClickListener(this);
        layout_purple.setOnClickListener(this);


    }


    private void setAppTheme() {

        if (!MainController.preferenceGetString(Theme_Current, "").equals("")) {
            if (MainController.preferenceGetString(Theme_Current, "").equals("Green")) {
                setTheme(R.style.ThemeApp_Green);
            } else if (MainController.preferenceGetString(Theme_Current, "").equals("Green_Dark")) {
                setTheme(R.style.ThemeApp_Green_Dark);
            } else if (MainController.preferenceGetString(Theme_Current, "").equals("Purple_Dark")) {
                setTheme(R.style.ThemeApp_Purple_Dark);
            } else if (MainController.preferenceGetString(Theme_Current, "").equals("Purple")) {
                setTheme(R.style.ThemeApp_Purple);
            }
        } else {
            setTheme(R.style.ThemeApp_Green);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Layout_green:
                if (isDarkTheme)
                    MainController.preferencePutString(Theme_Current, "Green_Dark");
                else
                    MainController.preferencePutString(Theme_Current, "Green");


                break;

            case R.id.Layout_purple:

                if (isDarkTheme)
                    MainController.preferencePutString(Theme_Current, "Purple_Dark");
                else
                    MainController.preferencePutString(Theme_Current, "Purple");


                /*TaskStackBuilder.create(this)
                        .addNextIntent(new Intent(this, Listactivity.class))
                        .addNextIntent(getIntent())
                        .startActivities();*/
                break;
        }
    }
}
