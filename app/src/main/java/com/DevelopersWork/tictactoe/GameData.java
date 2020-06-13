package com.DevelopersWork.tictactoe;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.logging.FileHandler;

import static android.content.Context.MODE_PRIVATE;

class GameData{

    private class Player{
        private int score;
        Player(){
            this.score = 0;
        }
        public void incrementScore(){
            this.score++;
        }
        public int getPlayerScore(){
            return this.score;
        }
    }
    private Player playerO,playerX;
    private int matches;
    private int playerTurn;
    
    GameData(){
        playerO = new Player();
        playerX = new Player();
        this.matches = 0;
        this.playerTurn = 0;
    }
    GameData(int turn){
        playerO = new Player();
        playerX = new Player();
        this.matches = 0;
        this.playerTurn = turn;
    }
    public int getNextTurn(){
        this.playerTurn++;
        this.playerTurn = this.playerTurn % 3;
        this.playerTurn = this.playerTurn == 0 ? 1 : this.playerTurn;

        return this.playerTurn;
    }
    public int getTurn(){
        return this.playerTurn;
    }

    private void incrementMatch(){
        this.matches++;
    }
    public void incrementWinCount(int player){
        this.incrementMatch();
        if(player == 1){
            playerO.incrementScore();
        }else if(player == 2){
            playerX.incrementScore();
        }
    }
    public int getMatchesCount(){
        return this.matches;
    }
    public int getPlayerXWins(){
        return this.playerX.getPlayerScore();
    }
    public int getPlayerOWins(){
        return this.playerO.getPlayerScore();
    }
}