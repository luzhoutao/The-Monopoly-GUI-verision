package GUI.panes;

import entity.map.Cell;

import java.util.ArrayList;
import java.util.Arrays;

import controler.Game;
import controler.GameGUI;
import controler.MenuControler;
import controler.MessageDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class DiceView extends Pane{
	public static final Image[] dice_image = {
		new Image("file:icons/dice1.gif"),
		new Image("file:icons/dice2.gif"),
		new Image("file:icons/dice3.gif"),
		new Image("file:icons/dice4.gif"),
		new Image("file:icons/dice5.gif"),
		new Image("file:icons/dice6.gif")
	};
	private ImageView view = new ImageView(dice_image[0]);
	private MenuControler mc;
	
	public DiceView(GameGUI game_gui){
		this.mc = Game.getGame().getMenuControler();
		view.setFitWidth(Cell.w*2);
		view.setFitHeight(Cell.w*2);
		
		view.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				int steps = mc.getSteps();
				if(steps<0){
					new MessageDialog(game_gui.getStage(),"Please wait for "+Game.getGame().getControlPlayer().getWaitingRounding()+" round!",180,80);
					Game.getGame().nextPlayer(game_gui);
				}else if(steps==0){
					MessageDialog md = new MessageDialog(game_gui.getStage(),"\"ÖÍÁô¿¨\" worked!",180,50);
					md.getBt().addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
						@Override
						public void handle(ActionEvent arg0) {
							Game.getGame().getControlPlayer().getCell().getLand()._response(Game.getGame().getControlPlayer());
						}
					});
					
				}else{
					showResult(steps);
					mc._go();
					Game.getGame().getControlPlayer().getCell().getLand()._response(Game.getGame().getControlPlayer());
					//game.nextPlayer();
				}
			}
			
		});
		this.getChildren().add(view);
	}
	
	public void showResult(int i){
		this.view.setImage(dice_image[i-1]);
	}
	
	public ImageView getView(){
		return this.view;
	}
}
