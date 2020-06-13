package com.DevelopersWork.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class OptionsScreen extends AppCompatActivity {

    int boardSize = 3;
    private JSONObject json;
    private RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_screen);

        readFile("config.json");

        RadioButton rb = findViewById(R.id.s3);
        try {
            boardSize = Integer.parseInt(json.getString("boardSize").toString());
        }catch (Exception e){
            e.printStackTrace();
        }

        rg = findViewById(R.id.boardSize);
//        Log.d("size",""+boardSize);
        if(boardSize == 3)
            rb = findViewById(R.id.s3);
        if(boardSize == 5)
            rb = findViewById(R.id.s5);
        if(boardSize == 7)
            rb = findViewById(R.id.s7);
        rb.setChecked(true);

        Switch sw = (Switch) findViewById(R.id.multiplayer);
        try {

//            Log.d("multiplayer",""+json.getString("multiplayer"));
            sw.setChecked(json.getString("multiplayer").compareTo("true") == 0);
        }catch (Exception e){e.printStackTrace();}
    }

    public void updateSize(View view){
        RadioButton rb = (RadioButton) view;
        String name = rb.getText().toString();
        boardSize = Integer.parseInt(name.split(" ")[0]);
        try {
            json.put("boardSize", boardSize);
        }catch (Exception e){e.printStackTrace();}
//        Log.d("boardSize",""+boardSize);
    }

    public void updateMode(View view){
        Switch sw = (Switch) view;
        try {
            json.put("multiplayer",sw.isChecked());
        }catch (Exception e){e.printStackTrace();}
    }

    public JSONObject readFile(String filename){
        json = new JSONObject();
        String str = "";
        String buffer;
        try {
            FileInputStream fis = openFileInput(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            buffer = br.readLine();
            while (buffer != null) {
                str = str + buffer;
                buffer = br.readLine();
            }
        } catch (Exception e) {
//            return json;
        }
//        Log.d("file",str);
        if(str.length() > 0)
            str = str.substring(1,str.length()-1);
//        Log.d("file",str);
        for(String pair: str.split(",")){
            try {
                pair = pair.replaceAll("\"","");
                String buf[] = pair.split(":");
                json.put(buf[0], buf[1]);
//                Log.d("put",buf[0]+":"+buf[1]);
            }catch (Exception e){
                e.printStackTrace();
//                return json;
            }
        }

        return json;
    }

    public void save(View view){
        try {
            FileOutputStream fos = openFileOutput("config.json",MODE_PRIVATE);
            fos.write(json.toString().getBytes());
            fos.flush();
            fos.close();
        }catch (Exception e){
            return;
        }finally {
            finish();
        }
    }
}
