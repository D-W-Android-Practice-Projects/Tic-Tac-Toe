package com.DevelopersWork.tictactoe;


// Source Code : https://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-3-tic-tac-toe-ai-finding-optimal-move/
public class GameAI extends Thread{
    int nextMove[];
    private Game game;
    int player;
    public GameAI(Game game,int bot){
        nextMove = new int[2];
        this.game = game;
        player = bot;
    }

    class Move{
        int row,col;
    }

    public void run(){
//        while(true){
            findBestMove();
//        }
    }

    public void findBestMove()
    {
        int[][] board = this.game.getBoard();
        int boardSize = this.game.getBoardSize();

        int bestVal = -1000;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;

        // Traverse all cells, evaluate minimax function for
        // all empty cells. And return the cell with optimal
        // value.
        for (int i = 0; i<boardSize; i++)
        {
            for (int j = 0; j<boardSize; j++)
            {
                // Check if cell is empty
                if (board[i][j] == 0)
                {
                    // Make the move
                    board[i][j] = player;

                    // compute evaluation function for this
                    // move.
                    int moveVal = minimax( 0, false);

                    // Undo the move
                    board[i][j] = 0;

                    // If the value of the current move is
                    // more than the best value, then update
                    // best/
                    if (moveVal > bestVal)
                    {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        nextMove[0] = bestMove.row;
        nextMove[1] = bestMove.col;
    }

    int minimax(int depth, boolean isMax)
    {
        int[][] board = this.game.getBoard();
        int boardSize = this.game.getBoardSize();

        if (this.game.checkGameOver())
            return 0;

        if(depth == 100)
            if(isMax)
                return 0;
            else
                return 1000;

        // If this maximizer's move
        if (isMax)
        {
            int best = -1000;

            // Traverse all cells
            for (int i = 0; i<boardSize; i++)
            {
                for (int j = 0; j<boardSize; j++)
                {
                    // Check if cell is empty
                    if (board[i][j] == 0)
                    {
                        // Make the move
                        board[i][j] = player;

                        // Call minimax recursively and choose
                        // the maximum value
                        best = Math.max( best,
                                minimax(depth+1, !isMax) );

                        // Undo the move
                        board[i][j] = 0;
                    }
                }
            }
            return best;
        }

        // If this minimizer's move
        else
        {
            int best = 1000;

            // Traverse all cells
            for (int i = 0; i<boardSize; i++)
            {
                for (int j = 0; j<boardSize; j++)
                {
                    // Check if cell is empty
                    if (board[i][j]==0)
                    {
                        // Make the move
                        board[i][j] = player == 1 ? 2 : 1;

                        // Call minimax recursively and choose
                        // the minimum value
                        best = Math.min(best,
                                minimax(depth+1, !isMax));

                        // Undo the move
                        board[i][j] = 0;
                    }
                }
            }
            return best;
        }
    }
}
