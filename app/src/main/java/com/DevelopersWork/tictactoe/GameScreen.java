package com.DevelopersWork.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class GameScreen extends AppCompatActivity {
    private Game tictactoe;
    private JSONObject json;
    private Worker job;
    private boolean multiplayer;
    private int player;

    TextView turnO;
    TextView turnX;

    private String call;
    private int boardSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        Bundle data = getIntent().getExtras();

        turnO = (TextView) findViewById(R.id.turnO);
        turnX = (TextView) findViewById(R.id.turnX);

        int turn = 2;
        try {
            if (data.getString("player Selection").equals("O"))
                turn = 2;
            else
                turn = 1;
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            call = "readFile";
            job = new Worker(this);
            job.start();
            job.join();

            boardSize = 3;
            try {
                boardSize = Integer.parseInt(json.getString("boardSize"));
            }catch (Exception e){
                e.printStackTrace();
            }

            call = "setup";
            job = new Worker(this);
            job.start();
            job.join();
            player = turn;
            multiplayer = json.getString("multiplayer").compareTo("true")==0;

            tictactoe = new Game(turn, boardSize);

            call = "show";
            job = new Worker(this);
            job.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class Worker extends Thread{
        private GameScreen scope;
        Worker(GameScreen scope){
            this.scope = scope;
        }

        @Override
        public void run(){
            if(this.scope.call.compareTo("show") == 0)
                this.scope.showBoard();
            else if(this.scope.call.compareTo("setup") == 0)
                this.scope.setupBoard(this.scope.boardSize);
            else if(this.scope.call.compareTo("readFile") == 0)
                this.scope.readFile("config.json");
        }
    }

    public void setupBoard(int boardSize){
        final GameScreen scope = this;
        TableLayout main = (TableLayout) findViewById(R.id.gameboard);
        main.setGravity(Gravity.CENTER);
//        main.setWeightSum((float) (1/(boardSize*1.0)));
//        main.setLayoutParams(new TableLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, (float) (1/(boardSize*1.0))));
        for(int i=0;i<boardSize;i++){

            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 0, (float) (1/(boardSize*1.0))));
            row.setGravity(Gravity.CENTER);

            for(int j=0;j<boardSize;j++){
                Button element = new Button(this);
                element.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        scope.update(v);
                    }
                });
                element.setId(boardSize*(i+1)-boardSize+j);
                element.setTag("b"+i+j);
                element.setGravity(Gravity.CENTER);
                element.setLayoutParams(new TableRow.LayoutParams(0, TableLayout.LayoutParams.MATCH_PARENT, 1));
                row.addView(element);
            }

            main.addView(row);
        }
    }

    public void backToMenu(View view){
        Intent mainMenu = new Intent(this,MainScreen.class);
        startActivity(mainMenu);
        finish();
    }

    public void update(View view){
        String id = (String)view.getTag();
//        Log.d("ID_update",view.getId()+"");
        try {
            view.setEnabled(false);
            tictactoe.update(Integer.parseInt(id.charAt(1) + ""), Integer.parseInt(id.charAt(2) + ""));
            if(tictactoe.checkGameOver()){
                tictactoe.resetBoard();
            }
        }catch (Exception e){
            // Verify the log there is some exception caused...
//            Log.d("ID : ",id);
            e.printStackTrace();
        }
        this.showBoard();
    }

    public void showBoard(){
        int board[][] = this.tictactoe.getBoard();
        int boardSize = this.tictactoe.getBoardSize();
        Button element;
        for(int i=0;i<boardSize;i++)
            for(int j=0;j<boardSize;j++){

//                Log.d("ID_showBoard",boardSize*(i+1)-boardSize+j+"");
                element = (Button)findViewById(boardSize*(i+1)-boardSize+j);//findViewById(getResources().getIdentifier(""+(i+boardSize-j),"id",getPackageName()));
                if(board[i][j] == 0) {
                    element.setText("");
                    element.setEnabled(true);
                }
                if(board[i][j] == 1)
                    element.setText("O");
                if(board[i][j] == 2)
                    element.setText("X");
            }
        try {
            TextView score = (TextView) findViewById(R.id.scoreO);
            score.setText(Integer.toString(this.tictactoe.getScoreO()));
            score = (TextView) findViewById(R.id.scoreX);
            score.setText(Integer.toString(this.tictactoe.getScoreX()));
        }catch (Exception e){}

        if(tictactoe.getTurn() == 2){
            turnO.setText(".");
            turnX.setText("");
        }else{
            turnO.setText("");
            turnX.setText(".");
        }

        if(!multiplayer && tictactoe.getTurn() != player){
            GameAI bot = new GameAI(this.tictactoe, player == 1 ? 2 : 1);
            bot.start();
            try {
                bot.join();
            }catch (Exception e){}
            int[] nextMove = bot.nextMove;
            Button piece = findViewById(boardSize*(nextMove[0]+1)-boardSize+nextMove[1]);
            piece.callOnClick();
        }
    }

    public void readFile(String filename) {
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
        Log.d("file", str);
        if (str.length() > 0)
            str = str.substring(1, str.length() - 1);
//        Log.d("file", str);
        for (String pair : str.split(",")) {
            try {
                pair = pair.replaceAll("\"", "");
                String buf[] = pair.split(":");
                json.put(buf[0], buf[1]);
                Log.d("put", buf[0] + ":" + buf[1]);
            } catch (Exception e) {
                e.printStackTrace();
//                return json;
            }
        }
    }
}
