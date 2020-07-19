package TTT;
import java.util.*;

public class Board {
    private static final int SIZE = 3;
    private int activePlayer;
    private int board[][];
    private List<Coordy> list;
    public static final int HUMAN = 1;
    public static final int COMPUTER = -1;
    public static final int EMPTYFIELD = 0;

    public Board(){
        board = new int[SIZE][SIZE];
        activePlayer = HUMAN;
        list = new ArrayList<>();
    }

    public void setBoard(){
        for(int row = 0; row < board.length; row++){
            for(int column = 0; column < board[row].length; column++){
                board[row][column] = EMPTYFIELD;
            }
        }
    }
    public int getActivePlayer(){ return activePlayer; }
    public void switchActivePlayer() { activePlayer = -activePlayer; }
    public int getSIZE() { return SIZE; }
    public boolean checkMove(int row, int column){
        if(board[row][column] == EMPTYFIELD) return true;
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
        boolean winner = false;

        if(checkWinInRow(player) || checkWinInColumns(player) || checkWinInFirstDiagonal(player) || checkWinInSecondDiagonal(player)){
            winner = true;
        }
        return winner;
    }
    public boolean checkWinInRow(int player){
        boolean winner = false;
        for(int x = 0; x < SIZE; x++){
            if(board[x][0] == player && board[x][1] == player && board[x][2] == player) {
                winner = true;

                list.add(new Coordy(x,0));
                list.add(new Coordy(x,1));
                list.add(new Coordy(x,2));
                break; }
        }
        return winner;
    }
    public boolean checkWinInColumns(int player){
        boolean winner = false;
        for(int y = 0; y < SIZE; y++){
            if(board[0][y] == player && board[1][y] == player && board[2][y] == player) {
                winner = true;

                list.add(new Coordy(0,y));
                list.add(new Coordy(1,y));
                list.add(new Coordy(2,y));
                break;
            }
        }
        return winner;
    }
    public boolean checkWinInFirstDiagonal(int player){
        boolean winner = false;
        if(board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            winner = true;

            list.add(new Coordy(0,0));
            list.add(new Coordy(1,1));
            list.add(new Coordy(2,2));
        }
        return winner;
    }
    public boolean checkWinInSecondDiagonal(int player){
        boolean winner = false;
        if(board[0][2] == player && board[1][1] == player && board[2][0] == player){
            winner = true;

            list.add(new Coordy(0,2));
            list.add(new Coordy(1,1));
            list.add(new Coordy(2,0));
        }
        return winner;
    }
    public List<Coordy> getWinnerList(){
        return list;
    }
    public Coordy computerRandomMove(){
        Random random = new Random();
        boolean check = false;
        int row;
        int column;

         do {
             row = random.nextInt(getSIZE());
             column = random.nextInt(getSIZE());
             check = checkMove(row, column);
         } while(check == false);

         Coordy coordy = new Coordy(row, column);
         putMove(coordy, COMPUTER);
        return coordy;
    }
}
