package TTT;

import java.util.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class TicTacToe extends Application {
    private VBox vBox;
    private MenuBar menuBar;
    private Menu menu1;
    private Menu menu2;
    private MenuItem newGameMenuItem;
    private MenuItem closeMenuItem;
    private MenuItem aboutMenuItem;
    private Button [][] buttons;
    private GridPane gridPane;
    private Label labelInfo;
    private Board board;
    private int countOfMove;

    @Override
    public void start(Stage primaryStage) throws Exception {
        vBox = new VBox();
        menuBar = new MenuBar();
        menu1 = new Menu("Edycja");
        menu2 = new Menu("Pomoc");
        newGameMenuItem = new MenuItem("Nowa gra");
        closeMenuItem = new MenuItem("Zamknij");
        menu1.getItems().addAll(newGameMenuItem,closeMenuItem);
        aboutMenuItem = new MenuItem("O grze");
        menu2.getItems().addAll(aboutMenuItem);
        menuBar.getMenus().addAll(menu1,menu2);
        gridPane=new GridPane();

        board = new Board();
        board.setBoard();
        countOfMove = board.getSIZE() * board.getSIZE();

        buttons = new Button[board.getSIZE()][board.getSIZE()];
        for(int i =0; i<buttons.length; i++){
            for(int j=0; j<buttons[i].length; j++){
                buttons[i][j] = new Button("");
                buttons[i][j].setMinWidth(Region.USE_COMPUTED_SIZE);
                buttons[i][j].setMaxWidth(Region.USE_COMPUTED_SIZE);
                buttons[i][j].setMinHeight(Region.USE_COMPUTED_SIZE);
                buttons[i][j].setMaxHeight(Region.USE_COMPUTED_SIZE);
                buttons[i][j].setPrefWidth(100);
                buttons[i][j].setPrefHeight(100);
                buttons[i][j].setFont(Font.font("Arial", FontWeight.BOLD, 40));
                buttons[i][j].setTextFill(Color.BLACK);
                gridPane.addRow(i, buttons[i][j]);
            }
        }
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
        gridPane.setMaxHeight(Region.USE_COMPUTED_SIZE);
        gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);

        labelInfo = new Label("");

        vBox.getChildren().addAll(menuBar,gridPane,labelInfo);

        Scene scene = new Scene(vBox, 300,320);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Kółko i krzyżyk");
        primaryStage.show();

        labelInfo.setText("Twój ruch");
        buttonsAction();

        newGameMenuItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                labelInfo.setText("Twój ruch");
                board = new Board();
                board.setBoard();
                countOfMove = board.getSIZE() * board.getSIZE();
                buttonsClean();
                buttonsAction();
            }
        });
        closeMenuItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

    }
    public static void main(String[] args){
        launch(args);

    }
    private void buttonsAction(){
        for(int i = 0; i < buttons.length; i++){
            for(int j = 0; j < buttons[i].length; j++){
                final int x = i, y = j;
                buttons[i][j].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        playerAction(x,y);
                    }
                });
            }
        }
    }

    private void buttonsClean(){
        for(int i =0; i<buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setDisable(false);
                buttons[i][j].setTextFill(Color.BLACK);
            }
        }
    }
    private void buttonsWinner(){
        List temp = board.getWinnerList();
        for(int i =0; i<buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j].setDisable(true);
            }
        }
       if(temp.isEmpty()){
            labelInfo.setText("Remis!!!");
        } else {
            for(int i=0; i<temp.size(); i++){
                Coordy t = (Coordy) temp.get(i);
                buttons[t.getX()][t.getY()].setTextFill(Color.GREEN);
            }
            labelInfo.setText("Wygrana!!!");
        }
    }
    private void playerAction(int x, int y){
        if(board.playerMove(new Coordy(x, y))){
            if(board.getActivePlayer() == board.HUMAN && countOfMove > 0) {
                buttons[x][y].setText("X");
                buttons[x][y].setDisable(true);
                countOfMove--;
                if (board.checkWin(board.getActivePlayer()) == false && countOfMove >0) {
                    labelInfo.setText("Ruch komputera");
                    board.switchActivePlayer();
                    computerAction();
                } else {
                    buttonsWinner();
                }
            }
        }
    }
    private void computerAction(){
        if(board.getActivePlayer() == board.COMPUTER ) {
            Coordy coordy = board.computerBlockMove();

            buttons[coordy.getX()][coordy.getY()].setText("O");
            buttons[coordy.getX()][coordy.getY()].setDisable(true);
            countOfMove--;

            if(board.checkWin(board.getActivePlayer()) == false && countOfMove>0) {
                labelInfo.setText("Twój ruch");
                board.switchActivePlayer();
            } else { buttonsWinner(); }
        }
    }
}
