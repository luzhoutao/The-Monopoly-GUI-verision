package entity.land;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Collection;

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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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


public class Bank extends Land {
	/*
	 * GUI version
	 */
	public Bank(Cell cell,GameGUI game) {
		super(cell,LandType.bank,game);
		name = "富翁银行";
		this.image = new ImageView(new Image("file:icons/bank.gif"));
	}

	@Override
	public String getDiscription() {
		// TODO Auto-generated method stub
		return "类型：银行\n"
				+ "名称："+name;
	}

	public static void monthInterest(Collection<Player> players, Stage stage) {
		String message = "--Bank--\nToday is the last day of this month for issuing share.\n";
		message += players.stream().map(e->{
			String share = "" + e.getDeposite()*0.1;
			e.addDeposite(e.getDeposite()*0.1);
			return e.getName()+"got share "+share+"yuan.";
		}).reduce("",(a,b)->(a+b+"\n"));
		new MessageDialog(stage,message,400,150);
	}

	@Override
	public void _response(Player cp) {
		
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(this.game.getStage());
		stage.setTitle("Bank");
		
		Group root = new Group();
		Scene scene = new Scene(root,400,150);
		stage.setScene(scene);
		
		GridPane gp = new GridPane();
		gp.setHgap(10);
		gp.setVgap(10);
		Text info = new Text("Now you have cash "+cp.getCash()+" yuan and deposite "+cp.getDeposite()+" yuan.");
		Label ser_l = new Label("Service:");
		RadioButton deposite_bt =  new RadioButton("deposite");
		RadioButton withdraw_bt = new RadioButton("withdraw");
		deposite_bt.setSelected(true);
		ToggleGroup tg = new ToggleGroup();
		deposite_bt.setToggleGroup(tg);
		deposite_bt.setUserData(0);
		withdraw_bt.setToggleGroup(tg);
		withdraw_bt.setUserData(1);
		Label amount_l = new Label("Amount:");
		TextField amount_t = new TextField();
		Button apply_bt = new Button("Apply");
		Button exit_bt = new Button("Exit");
		
		GridPane.setColumnSpan(info, 4);
		gp.add(info, 1, 1);
		gp.add(ser_l, 1, 2);
		gp.add(deposite_bt, 2, 2);
		gp.add(withdraw_bt, 3, 2);
		gp.add(amount_l, 1, 3);
		GridPane.setColumnSpan(amount_t, 3);
		gp.add(amount_t, 2, 3);
		gp.add(apply_bt, 6, 4);
		gp.add(exit_bt, 7, 4);
		
		root.getChildren().add(gp);
		stage.show();
		
		apply_bt.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				try{
					int service = (int) tg.getSelectedToggle().getUserData();
					double amount = Double.parseDouble(amount_t.getText());
					if(amount<0) throw new NumberFormatException();
					if(service==0){
						if(amount>=0.01&&amount<=cp.getCash()){
							cp.deposite(amount);
							info.setText("Now you have cash "+cp.getCash()+" yuan and deposite "+cp.getDeposite()+" yuan.");
							amount_t.setText("");
							game.freshPlayerInfo();
						}else
							throw new NumberFormatException();
					}else if(service==1){
						if(amount>=0.01&&amount<=cp.getDeposite()){
							cp.withdraw(amount);
							info.setText("Now you have cash "+cp.getCash()+" yuan and deposite "+cp.getDeposite()+" yuan.");
							amount_t.setText("");
							game.freshPlayerInfo();
						}else
							throw new NumberFormatException();
					}
				}catch(NumberFormatException e){
					new MessageDialog(stage,"Error amount input! Try again!",180,50);
					amount_t.setText("");
				}
			}
			
		});
		
		exit_bt.setOnAction(new EventHandler<ActionEvent>(){

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
	}
}
