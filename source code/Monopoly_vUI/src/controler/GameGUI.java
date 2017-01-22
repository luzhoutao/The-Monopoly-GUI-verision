package controler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entity.land.Bank;
import entity.map.Map;
import entity.player.Player;
import entity.stock.Stock;
import GUI.scenes.PlayScene;
import GUI.scenes.StartScene;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.*;

public class GameGUI extends Application{
	
	private Game game;
	private Stage stage;
	private PlayScene playScene;

	@Override
	public void start(Stage primaryStage){
		game = Game.getGame();
		this.stage = primaryStage;
		this.stage.setTitle("The Monopoly");
		stage.setResizable(false);
		stage.centerOnScreen();
		stage.show();
		stage.setScene(new StartScene(new Group(),this));//set players
	}

	public static void main(String[] args){
		launch(args);
	}
	/*
	 * setting for message pane
	 */
	public void setMessage(String text){
		this.playScene.getMessagePane().setMessage(text);
	}
	public void freshMessage() {
		playScene.getMessagePane().update();
	}
	public Stage getStage(){
		return this.stage;
	}
	
	/*
	 * setting for player information pane
	 */
	public void freshPlayerInfo(){
		playScene.getPlayerPane().refresh();
	}
	
	/*
	 * setter and getter for play scene
	 */
	public void setPlayScene() {
		game.setController(game.getMap().getPlayers().get(0));
		playScene= new PlayScene(new HBox(),this);
		stage.setScene(playScene);		
	}
	
	public PlayScene getPlayScene(){
		return this.playScene;
	}
}
