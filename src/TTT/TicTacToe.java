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
    VBox vBox;
    MenuBar menuBar;
    Menu menu1;
    Menu menu2;
    MenuItem newGameMenuItem;
    MenuItem closeMenuItem;
    MenuItem aboutMenuItem;
    Button button00,button01,button02;
    Button button10,button11,button12;
    Button button20,button21,button22;
    Button [][] buttons;
    GridPane gridPane;
    Label labelActivePlayer;
    Board board;

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

        button00 = new Button("");button01 = new Button("");button02 = new Button("");
        button10 = new Button("");button11 = new Button("");button12 = new Button("");
        button20 = new Button("");button21 = new Button("");button22 = new Button("");


        buttons = new Button[][]{{button00, button01, button02}, {button10, button11, button12}, {button20, button21, button22}};
        for(int i =0; i<buttons.length; i++){
            for(int j=0; j<buttons[i].length; j++){
                buttons[i][j].setMinWidth(Region.USE_COMPUTED_SIZE);
                buttons[i][j].setMaxWidth(Region.USE_COMPUTED_SIZE);
                buttons[i][j].setMinHeight(Region.USE_COMPUTED_SIZE);
                buttons[i][j].setMaxHeight(Region.USE_COMPUTED_SIZE);
                buttons[i][j].setPrefWidth(100);
                buttons[i][j].setPrefHeight(100);
                buttons[i][j].setFont(Font.font("Arial", FontWeight.BOLD, 40));
                buttons[i][j].setTextFill(Color.BLACK);
            }
        }

        gridPane=new GridPane();
        gridPane.addRow(0, button00,button01,button02);
        gridPane.addRow(1, button10,button11,button12);
        gridPane.addRow(2, button20,button21,button22);
        gridPane.setPadding(new Insets(20));
        //gridPane.setGridLinesVisible(true);
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
        gridPane.setMaxHeight(Region.USE_COMPUTED_SIZE);
        gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);

        labelActivePlayer = new Label("");

        vBox.getChildren().addAll(menuBar,gridPane,labelActivePlayer);

        Scene scene = new Scene(vBox, 300,320);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Kółko i krzyżyk");
        primaryStage.show();

        labelActivePlayer.setText("Twój ruch");
        board = new Board();
        board.setBoard();
        buttonsAction();


        newGameMenuItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                labelActivePlayer.setText("Twój ruch");
                board = new Board();
                board.setBoard();
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
                        int player = board.getActivePlayer();
                        if(board.playerMove(new Coordy(x, y))){

                            if(player ==1) {
                                buttons[x][y].setText("X");
                                buttons[x][y].setDisable(true);
                                if(board.checkWin(player) == false) {
                                    labelActivePlayer.setText("Ruch komputera");
                                    board.setActivePlayer();
                                } else { buttonsWinner(); }
                            }
                            else {
                                buttons[x][y].setText("O");
                                buttons[x][y].setDisable(true);
                                if(board.checkWin(player) == false) {
                                    labelActivePlayer.setText("Twój ruch");
                                    board.setActivePlayer();
                                } else { buttonsWinner(); }
                            }

                        }
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
        List temp = new ArrayList<>();
        temp = board.getWinnerList();
        for(int i =0; i<buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j].setDisable(true);
            }
        }
        for(int i=0; i<temp.size(); i++){
            Coordy t = (Coordy) temp.get(i);
            buttons[t.getX()][t.getY()].setTextFill(Color.GREEN);
        }
        labelActivePlayer.setText("Wygrana!!!");
    }
}
