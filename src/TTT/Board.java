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
        int sum;

        for(int x = 0; x < SIZE; x++){
            sum = 0;
            for(int y =0; y < SIZE; y++){
                if(board[x][y] == player) sum++;
                if(sum == SIZE) {
                    winner = true;
                    for(int j=0; j < SIZE; j++){
                        list.add(new Coordy(x,j));
                    }
                }
            }
        }
        return winner;
    }
    public boolean checkWinInColumns(int player){
        boolean winner = false;
        int sum;

        for(int y = 0; y < SIZE; y++){
            sum = 0;
            for(int x =0; x < SIZE; x++){
                if(board[x][y] == player) sum++;
                if(sum == SIZE) {
                    winner = true;
                    for(int j=0; j < SIZE; j++){
                        list.add(new Coordy(j,y));
                    }
                }
            }
        }
        return winner;
    }
    public boolean checkWinInFirstDiagonal(int player){
        boolean winner = false;
        int sum =0;
        for(int x =0; x < SIZE; x++){
            if(board[x][x] == player) sum++;
            if(sum == SIZE) {
                winner = true;
                for(int j=0; j < SIZE; j++){
                    list.add(new Coordy(j,j));
                }
            }
        }
        return winner;
    }
    public boolean checkWinInSecondDiagonal(int player){
        boolean winner = false;
        int sum = 0;
        for(int x =0; x < SIZE; x++){
            if(board[x][SIZE - 1 - x] == player) sum++;
            if(sum == SIZE) {
                winner = true;
                for(int j=0; j < SIZE; j++){
                    list.add(new Coordy(j,SIZE-1-j));
                }
            }
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
    public Coordy computerBlockMove(){

        for(int x = 0; x<SIZE; x++){
            for(int y=1,i=SIZE-1; y<SIZE;y++,i=i-2){
                if((board[x][y-1] == HUMAN && board[x][y] == HUMAN) &&
                        board[x][i] == EMPTYFIELD) {
                    Coordy coordy = new Coordy(x,i);
                    putMove(coordy,COMPUTER);
                    return coordy;
                }
            }
        }
        for(int y=0;y<SIZE;y++){
            for(int x=1,i=SIZE-1; x<SIZE;x++,i=i-2){
                if((board[x-1][y] == HUMAN && board[x][y] == HUMAN) &&
                        board[i][y] == EMPTYFIELD) {
                    Coordy coordy = new Coordy(i,y);
                    putMove(coordy,COMPUTER);
                    return coordy;
                }
            }
        }
        for(int x=0,i=SIZE-1; x<SIZE-1; x++,i=i-2){
            if(board[x][x] == HUMAN && board[x+1][x+1] == HUMAN && board[i][i] == EMPTYFIELD){
                Coordy coordy = new Coordy(i,i);
                putMove(coordy,COMPUTER);
                return coordy;
            }
        }
        for(int x =0; x<SIZE-1; x++){
            if(board[x][SIZE-1-x] == HUMAN && board[x+1][SIZE-2-x] == HUMAN
                    && board[SIZE-1-x-x][x+x] == EMPTYFIELD){
                Coordy coordy = new Coordy(SIZE-1-x-x,x+x);
                putMove(coordy,COMPUTER);
                return coordy;
            }
        }
        Coordy coordy = computerRandomMove();
        return coordy;
    }
}
