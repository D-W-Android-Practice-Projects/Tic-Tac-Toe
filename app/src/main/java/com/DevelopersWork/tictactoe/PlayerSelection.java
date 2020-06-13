package com.DevelopersWork.tictactoe;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlayerSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_selection);
    }

    public void selectPlayer(View view){
        Intent gameScreen = new Intent(this,GameScreen.class);
        Button selection = (Button) view;
        gameScreen.putExtra("player Selection",selection.getText().toString());
        startActivity(gameScreen);
        Worker job = new Worker();
        job.start();
    }

    private class Worker extends Thread{
        public void run(){
           try{
               finish();
           }catch (Exception e){}
        }
    }
}
