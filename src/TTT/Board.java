package TTT;
import java.util.*;

public class Board {
    private static final int SIZE = 3;
    private int activePlayer; // 0 - puste pole, 1 - gracz, -1 - komputer
    private int board[][];
    private List<Coordy> list;

    public Board(){
        board = new int[SIZE][SIZE];
        activePlayer = 1;
    }

    public void setBoard(){
        for(int row = 0; row < board.length; row++){
            for(int column = 0; column < board[row].length; column++){
                board[row][column] = 0;
            }
        }
    }
    public int getActivePlayer(){ return activePlayer; }
    public void setActivePlayer() { activePlayer = -activePlayer; }
    public int getSIZE() { return SIZE; }
    public boolean checkMove(int row, int column){
        if(board[row][column] == 0) return true;
        else return false;
    }
    public void putMove(Coordy coordy, int activePlayer){
        board[coordy.getX()][coordy.getY()] = activePlayer;
    }
    public boolean playerMove(Coordy coordy){
        if(checkMove(coordy.getX(), coordy.getY())) {
            putMove(coordy,activePlayer);
            return true;
        }
        else return false;
    }
    public boolean checkWin(int player){
        int column = 0;
        int row = 0;
        boolean winner = false;

        //wiersze
        for(int x = 0; x < SIZE; x++){
            if(board[x][column] == player && board[x][column + 1] == player && board[x][column + 2] == player) {
                winner = true;
                list = new ArrayList<>();
                list.add(new Coordy(x,column));
                list.add(new Coordy(x,column + 1));
                list.add(new Coordy(x,column +2));
                break; }
        }
        //kolumny
        for(int y = 0; y < SIZE; y++){
            if(board[row][y] == player && board[row + 1][y] == player && board[row + 2][y] == player) {
                winner = true;
                list = new ArrayList<>();
                list.add(new Coordy(row,y));
                list.add(new Coordy(row +1,y));
                list.add(new Coordy(row +2,y));
                break;
            }
        }
        //przekątne
        if(board[row][column] == player && board[row + 1][ column + 1] == player && board[row + 2][ column + 2] == player) {
            winner = true;
            list = new ArrayList<>();
            list.add(new Coordy(row,column));
            list.add(new Coordy(row +1,column +1));
            list.add(new Coordy(row + 2,column +2));
        }
        if(board[row][column +2] == player && board[row + 1][column + 1] == player && board[row + 2][column] == player){
            winner = true;
            list = new ArrayList<>();
            list.add(new Coordy(row,column +2));
            list.add(new Coordy(row + 1,column + 1));
            list.add(new Coordy(row +2,column));
        }

        return winner;
    }
    public List<Coordy> getWinnerList(){
        return list;
    }

}
