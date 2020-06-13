package com.DevelopersWork.tictactoe;

class Game{
    private int[][] board;
    private GameData data;
    private int boardSize;
    Game(int turn,int boardSize){
        this.data = new GameData(turn);
        this.boardSize = boardSize;
        this.board = new int[boardSize][boardSize];
    }

    public void resetBoard(){
        for(int i=0;i<boardSize;i++){
            for(int j=0;j<boardSize;j++) {
                board[i][j] = 0;
            }
        }
    }

    public int getTurn(){
        return this.data.getTurn();
    }
    public int getScoreX(){
        return this.data.getPlayerXWins();
    }
    public int getScoreO(){
        return this.data.getPlayerOWins();
    }
    public int getBoardSize(){ return  this.boardSize; }

    public void update(int x,int y){
        this.board[x][y] = this.data.getNextTurn();

        if(checkGameOverPlayer(this.data.getTurn())){
            this.data.incrementWinCount(this.data.getTurn());
            this.resetBoard();
        }
    }

    public int[][] getBoard(){
        return this.board;
    }


    public  boolean checkGameOver(){
        int flag = 0;
        for(int i=0;i<boardSize;i++){
            for(int j=0;j<boardSize;j++) {
                if (board[i][j] == 0) {
                    flag = 1;
                    break;
                }
            }
            if(flag == 1)
                break;
        }

        if(flag == 1)
            return false;
        return true;
    }

    private boolean checkGameOverPlayer(int player){
        int flag = 0;
        for(int i=0;i<boardSize;i++){
            flag = 0;
            // check all the rows
            for(int j=0;j<boardSize;j++){
                if(board[i][j] != player){
                    flag = 1;
                    break;
                }
            }
            if(flag == 0)
                return true;
            flag = 0;
            // check all the columns
            for(int j=0;j<boardSize;j++){
                if(board[j][i] != player){
                    flag = 1;
                    break;
                }
            }
            if(flag == 0)
                return true;
        }
        flag = 0;
        // check the diagonal
        for(int j=0;j<boardSize;j++){
            if(board[j][j] != player){
                flag = 1;
                break;
            }
        }
        if(flag == 0)
            return true;
        flag = 0;
        // check the diagonal
        for(int j=0;j<boardSize;j++){
            if(board[j][2-j] != player){
                flag = 1;
                break;
            }
        }
        if(flag == 0)
            return true;
        return false;
    }
}