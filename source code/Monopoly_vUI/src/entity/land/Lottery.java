package entity.land;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import controler.Game;
import controler.GameGUI;
import controler.MessageDialog;
import entity.map.Cell;
import entity.player.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Lottery extends Land {
	/*
	 * GUI version
	 */
	public Lottery(Cell cell,GameGUI game) {
		super(cell,LandType.lottery,game);
		name="≤ ∆±ª∂¿÷π∫";
		this.image = new ImageView(new Image("file:icons/lottery.gif"));
	}

	@Override
	public void _response(Player controlPlayer) {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(this.game.getStage());
		stage.setTitle("Lottery");
		
		Group root = new Group();
		Scene scene = new Scene(root,400,100);
		stage.setScene(scene);
		
		GridPane gp = new GridPane();
		Text t = new Text("Enter the number between 1 and 20:");
		TextField input = new TextField();
		Button bt = new Button("Correct?");
		Button leave_bt = new Button("Leave");
		
		gp.setVgap(10);
		gp.setHgap(10);
		GridPane.setColumnSpan(t, 4);
		gp.add(t, 1, 1);
		GridPane.setColumnSpan(input, 3);
		gp.add(input, 2, 2);
		gp.add(bt, 7, 3);
		gp.add(leave_bt, 8, 3);
		root.getChildren().add(gp);
		
		bt.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				try{
					int guess = Integer.parseInt(input.getText());
					if(!controlPlayer.payByCash(100)){
						game.freshPlayerInfo();
						new MessageDialog(stage,"Cash is not enough!",180,50);
						input.setText("");
						return;
					}
					game.freshPlayerInfo();
					int target = (int)(Math.random()*20)+1;
					if(target==guess){
						new MessageDialog(stage,"Congratulation! You've won 1000 yuan.",180,50);
						controlPlayer.addCash(1000);
						game.freshPlayerInfo();
						input.setText("");
					}else{
						new MessageDialog(stage,"Sorry,it is "+target+" !",180,50);
						input.setText("");
					}
				}catch(NumberFormatException e){
					new MessageDialog(stage,"Error input number! Try again!",180,50);
					input.setText("");
				}
			}
			
		});
		leave_bt.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				stage.hide();
				Game.getGame().getMenuControler().go_on();
			}
			
		});
		stage.setOnCloseRequest(new EventHandler<WindowEvent>(){

			@Override
			public void handle(WindowEvent arg0) {
				Game.getGame().getMenuControler().go_on();
			}
			
		});
		stage.show();
	}
	
	/*
	 * general part
	 */
	@Override
	public String getDiscription() {
		// TODO Auto-generated method stub
		return "¿‡–Õ£∫≤ ∆±\n"
				+ "√˚≥∆£∫≤ ∆±ª∂¿÷π∫";
	}

	
}
