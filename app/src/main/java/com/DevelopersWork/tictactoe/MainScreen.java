package com.DevelopersWork.tictactoe;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

// ads
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
    }

    public void start(View view){
        Intent startGame = new Intent(this,PlayerSelection.class);
        startActivity(startGame);
    }

    public void options(View view){
        Intent options = new Intent(this,OptionsScreen.class);
        startActivity(options);
    }

    public void exit(View view){
        Intent closeActivity = new Intent(Intent.ACTION_MAIN);
        closeActivity.addCategory(Intent.CATEGORY_HOME);
        closeActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(closeActivity);
    }
}
