package entity.prop;

import entity.map.Cell;
import entity.player.Direction;
import entity.player.Player;

import java.io.*;

import controler.GameGUI;
import controler.MessageDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Barrier extends Prop {

	public Barrier(){
		super(null,PropType.barrier);
		price = 24;
	}
	public Barrier(Player owner) {
		super(owner,PropType.barrier);
		price = 24;
	}
	@Override
	public String getDescription() {
		return "卡片名：路障\n"
				+ "卡片适用对象：无\n"
				+ "卡片使用效果：\n"
				+ "可以在前后8步之内安放一个路障，\n    任意玩家经过路障时会停在路障所在位置不能前行";
	}

	@Override 
	public String getName() {
		// TODO Auto-generated method stub
		return "路障";
	}
	
	public int getPrice() {
		// TODO Auto-generated method stub
		return price;
	}
	@Override
	public boolean canWork() {
		return true;
	}
	@Override
	public void _work(Stage primaryStage,GameGUI game) {
		final Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(primaryStage);
		stage.setTitle("Where to put the barrier?");
		
		Group root = new Group();
		Scene scene = new Scene(root,400,170);
		stage.setScene(scene);
		
		GridPane gp = new GridPane();
		ToggleGroup tg = new ToggleGroup();
		RadioButton forwardRb = new RadioButton("Forward");
		forwardRb.setSelected(true);
		forwardRb.setToggleGroup(tg);
		forwardRb.setUserData(0);
		RadioButton backwardRb = new RadioButton("Backward");
		backwardRb.setToggleGroup(tg);
		backwardRb.setUserData(1);
		Label disLabel = new Label("Distance(1-8):");
		TextField disField = new TextField();
		Button okBt = new Button("OK");
		Button exitBt = new Button("Exit");
		
		gp.setPadding(new Insets(20,0,0,20));
		gp.setVgap(10);
		gp.setHgap(10);
		gp.add(forwardRb, 1, 1);
		gp.add(backwardRb, 2, 1);
		gp.add(disLabel, 1, 2);
		GridPane.setColumnSpan(disField, 2);
		gp.add(disField, 2, 2);
		gp.add(okBt, 3, 3);
		gp.add(exitBt, 4, 3);
		
		okBt.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				try{
					int direction = (int) tg.getSelectedToggle().getUserData();
					int dis = Integer.parseInt(disField.getText());
					if(dis>8 || dis<1) 
						throw new NumberFormatException();
					owner.getCell().getAnotherCell(
							owner.getDirection()==Direction.clockwise&&direction==0 || 
							owner.getDirection()==Direction.anticlockwise&&direction==1, dis)
							.setBlocked();
					removeFromOwner();
					new MessageDialog(primaryStage,"Done!",140,70);
					game.freshMessage();
					stage.hide();
				}catch(NumberFormatException e){
					new MessageDialog(primaryStage,"Incorrect distance input! Try again!",200,100);
					disField.setText("");
				}
			}
		});
		
		exitBt.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				stage.hide();
			}
		});
		root.getChildren().add(gp);
		stage.show();
	}
}
