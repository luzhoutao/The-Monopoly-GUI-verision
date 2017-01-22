package entity.land;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;

import controler.Game;
import controler.GameGUI;
import controler.MessageDialog;
import entity.map.Cell;
import entity.player.Player;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class House extends Land {
	
	private int rank = 1;
	private final int HIGHEST_RANK=6;
	private int price;
	private Player owner;
	private TagPane tag_pane;
	private static ArrayList<String> landInfo = new ArrayList<String>(); 
	static{
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/landInfo")));
			String record;
			while((record=br.readLine())!=null){
				landInfo.add(record);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * GUI version
	 */
	public House(Cell cell,GameGUI game) {
		super(cell,LandType.house,game);
		try{
			String info = landInfo.remove(0);
			String[] is = info.split("\t\t");
			this.name = is[0];
			this.price = Integer.parseInt(is[1]);
		}catch(IndexOutOfBoundsException e){
			System.out.println("Have run out of information record for land");
		}catch(NumberFormatException e){
			System.out.println("文件内容格式错误！");   
		}
	}

	// the view of the house with rank number on it
	public StackPane getTag(){
		if(tag_pane == null)
			return (tag_pane = new TagPane());
		return tag_pane;
	}

	private class TagPane extends StackPane{
		Circle c= new Circle();
		Text t = new Text();
		TagPane(){
			c.setRadius(Cell.w/2);
			getChildren().addAll(c,t);
			c.setFill(Color.WHITE);
			t.setFont(new Font(15));
		}
		
		void refresh(){
			if(owner==null){
				c.setFill(Color.WHITE);
				t.setFill(Color.BLACK);
			}else{
				c.setFill(owner.getMyLandColor());
				t.setText(String.valueOf(rank));
				t.setFill(Color.WHITE);
			}
		}
	}
	@Override
	public void _response(Player cp) {
		if(owner==null){
			_sellTo(cp);
		}else if(owner==cp){
			_upgradeBy(cp);
		}else{
			_charge(cp);
		}
		game.freshPlayerInfo();
		this.tag_pane.refresh();
	}

	private void _charge(Player cp) {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(this.game.getStage());
		stage.setTitle(name);
		
		Group root = new Group();
		Scene scene = new Scene(root,400,80);
		stage.setScene(scene);
		
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(20,0,0,20));
		Text message = new Text();
		Button cash_bt = new Button("Pay by cash");
		cash_bt.setUserData(getPassFee());
		Button deposite_bt = new Button("Pay by deposite");
		Button mortgage_bt = new Button("Pay by mortgage");
		Button ok_bt = new Button("OK");
		GridPane.setRowSpan(message, 2);
		GridPane.setColumnSpan(message, 3);
		gp.add(message, 1, 1);
		gp.add(cash_bt, 4, 4);
		root.getChildren().add(gp);
		
		stage.show();
		
		message.setText("You need to pay "+owner.getName()+" pass fee "+getPassFee()+"yuan \nincluding street plus "+getAddition()+" yuan.");
		cash_bt.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				if(cp.payByCash((Double)cash_bt.getUserData())){
					owner.addCash(getPassFee());
					message.setText("You paid all of the fee by cash! Thank you!\n");
					game.freshPlayerInfo();
					cash_bt.setVisible(false);
					gp.add(ok_bt, 4, 4);
				}else{
					double amount=(Double)cash_bt.getUserData()-cp.getCash();
					message.setText("You've paid the pass fee "+cp.getCash()+" yuan，\nAnd still left "
									+new DecimalFormat("0.00").format(amount)+"yuan to pay!");
					cp.payByCash(cp.getCash());
					game.freshPlayerInfo();
					deposite_bt.setUserData(amount);
					cash_bt.setVisible(false);
					gp.add(deposite_bt, 4, 4);
				}
			}
		});
		deposite_bt.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				if(cp.payByDeposite((Double)deposite_bt.getUserData())){
					owner.addCash(getPassFee());
					message.setText("You've paid the left fee by deposite. Thanks\n");
					game.freshPlayerInfo();
					deposite_bt.setVisible(false);
					gp.add(ok_bt, 4, 4);
				}else{
					double amount = (Double)deposite_bt.getUserData() - cp.getDeposite();
					message.setText("You've paid "+cp.getDeposite()+" yuan，"
							+ "\nAnd still left "+new DecimalFormat("0.00").format(amount)+"yuan to pay。");
					cp.payByDeposite(cp.getDeposite());
					game.freshPlayerInfo();
					mortgage_bt.setUserData(amount);
					deposite_bt.setVisible(false);
					gp.add(mortgage_bt, 4, 4);
				}
			}
			
		});
		mortgage_bt.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				double paidBack = 0;
				double amount = (double) mortgage_bt.getUserData();
				while(cp.getMyHouse().size()!=0){
					paidBack += cp.mortageHouse();
					if(paidBack>=amount)
						break;
				}
				if(paidBack<amount){
					stage.hide();
					String message = "All of your houses are used to mortgage, but still cannot pay off the pass fee"
							+ "\nYou have broken down now.";
					amount-=paidBack;
					owner.addCash(getPassFee()-amount);
					message += owner.getName()+" finally got "+new DecimalFormat("0.00").format(getPassFee()-amount)+"yuan.";
					cell.getMap().getPlayers().remove(cp);
					MessageDialog md = new MessageDialog(game.getStage(),message,400,200);
					md.getBt().addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
						@Override
						public void handle(ActionEvent arg0) {
							Game.getGame().getMenuControler().go_on();
						}
					});
				}else{
					MessageDialog md = new MessageDialog(game.getStage(),"Part of mortgage was used to pay for the pass fee\nThe rest "+new DecimalFormat("0.00").format(paidBack-amount)+"yuan has been put into your cash，\nThanks!",400,250);
					stage.hide();
					cp.addCash(paidBack-amount);
					owner.addCash(getPassFee());
					game.freshPlayerInfo();
					md.getBt().addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
						@Override
						public void handle(ActionEvent arg0) {
							Game.getGame().getMenuControler().go_on();
						}
					});
				}
				gp.add(ok_bt, 4, 4);
			}
		});
		
		ok_bt.setOnAction(new EventHandler<ActionEvent>(){

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

	private void _upgradeBy(Player cp) {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(this.game.getStage());
		stage.setTitle(name);
		
		Group root = new Group();
		Scene scene = new Scene(root,450,100);
		stage.setScene(scene);
		
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(20,0,0,20));
		gp.setHgap(10);
		Text mes = new Text("The upgrade fee for "+name+" is "+new DecimalFormat("0.00").format(getUpgradeFee())+"yuan.\nDo you want to upgrade?");
		Button upgrade_bt = new Button("Upgrade");
		Button leave_bt = new Button("Leave");
		gp.add(mes, 1, 1);
		gp.add(upgrade_bt,3, 3);
		gp.add(leave_bt, 4, 3);
		
		root.getChildren().add(gp);
		stage.show();
		
		upgrade_bt.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				if(canUpgrade()){
					boolean done = cp.payByCash(getUpgradeFee());
					if(done){
						MessageDialog md = new MessageDialog(game.getStage(),name+"has been upgraded to rank "+upgrade()+"~~\n",300,100);
						tag_pane.refresh();
						game.freshPlayerInfo();
						stage.hide();
						md.getBt().addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
							@Override
							public void handle(ActionEvent arg0) {
								Game.getGame().getMenuControler().go_on();
							}
						});
					}else{
						new MessageDialog(game.getStage(),"The cash is not enough!",200,100);
					}
				}else{
					new MessageDialog(game.getStage(),"The house is at the highest rank, not able to upgrade!",200,100);
					game.freshPlayerInfo();
				}
			}
			
		});
		leave_bt.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				stage.hide();
			}
			
		});		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>(){

			@Override
			public void handle(WindowEvent arg0) {
				Game.getGame().getMenuControler().go_on();
			}
			
		});
	}


	private void _sellTo(Player cp) {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(this.game.getStage());
		stage.setTitle(name);
		
		Group root = new Group();
		Scene scene = new Scene(root,360,100);
		stage.setScene(scene);
		
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(0,0,0,20));
		gp.setHgap(10);
		gp.setVgap(20);
		Text mes = new Text("The price of "+this.name+" is "+this.getValue()+" yuan. Buy it?");
		Button buy_bt = new Button("Buy");
		Button leave_bt = new Button("Leave");
		GridPane.setColumnSpan(mes, 4);
		gp.add(mes, 1, 1);
		gp.add(buy_bt, 3,2);
		gp.add(leave_bt, 4, 2);
		root.getChildren().add(gp);
		
		stage.show();
		buy_bt.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				boolean done = cp.payByCash(getValue());
				if(done){
					buy(cp);
					MessageDialog md = new MessageDialog(game.getStage(),"You've bought it successfully!",200,100);
					game.freshPlayerInfo();
					tag_pane.refresh();
					stage.hide();
					md.getBt().addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
						@Override
						public void handle(ActionEvent arg0) {
							Game.getGame().getMenuControler().go_on();
						}
					});
				}else{
					new MessageDialog(game.getStage(),"The cash is not enough!",200,100);
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
	}
	/*
	 * general part
	 */
	public Player getOwner() {
		return owner;
	}
	
	private void buy(Player p){
		owner = p;
		p.addHouse(this);
		
	}
	private int upgrade() {
		return ++rank;
	}
	private boolean canUpgrade() {
		return rank<HIGHEST_RANK;
	}
	private double getUpgradeFee() {
		return price*0.5;
	}
	@Override
	public String getDiscription() {
		return "类型：房产\n"
				+ "名称："+name+"\n"
				+ "初始价格："+price+"\n"
				+ "当前等级："+rank+"级\n"
				+ "拥有者："+(owner==null?"<可供出售状态>":("玩家"+owner.getName()));
	}
	public double getValue() {
		return price*rank;
	}
	private double getPassFee() {
		return this.getValue()*0.3+getAddition();
	}
	private double getAddition() {
		ArrayList<Cell> street = this.cell.getStreet();
		return 0.1*street.stream().map(e->(e.getLand())).filter(e->(e.getType()==LandType.house)).map(e->((House)e)).filter(e->(e.getOwner()==this.getOwner() && e!=this))
				.map(e->(e.getValue())).reduce(0.0,(a,b)->(a+b));
	}
	public void reclaim() {
		this.owner = null;
		this.tag_pane.refresh();
	}

	
}