package com.DevelopersWork.tictactoe;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SplashScreen splash = new SplashScreen(this);
        splash.start();
    }

    private class SplashScreen extends Thread{
        private MainActivity scope;
        SplashScreen(MainActivity scope){
            this.scope = scope;
        }

        public void run(){
            try{

//                Intent gameScreen = new Intent(this.scope,GameScreen.class);
//                gameScreen.putExtra("player Selection","X");
                Intent menu = new Intent(this.scope,MainScreen.class);
                Thread.sleep(2000);
                finish();
                startActivity(menu);
//                startActivity(gameScreen);
            }catch (Exception e){

            }
        }
    }
}
