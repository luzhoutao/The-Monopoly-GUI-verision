package entity.land;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;

import controler.Game;
import controler.GameGUI;
import controler.MessageDialog;
import entity.map.Cell;
import entity.player.Direction;
import entity.player.Player;
import entity.prop.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.WindowEvent;


public class News extends Land {
	/*
	 * GUI version
	 */
	public News(Cell cell,GameGUI game) {
		super(cell,LandType.news,game);
		name = "小智新闻站";	
		this.image = new ImageView(new Image("file:icons/news.gif"));
	}

	@Override
	public void _response(Player controlPlayer) {
		switch((int)(Math.random()*5)+1){
		case 0:
			_praise(); break;
		case 1:
			_supply(); break;
		case 2:
			_interest(); break;
		case 3:
			_financialTax(); break;
		case 4:
			_cardGift(); break;
		case 5:
			_putIntoHospital(controlPlayer); break;
			
		}
	}
	private void _putIntoHospital(Player cp) {
		MessageDialog md = new MessageDialog(game.getStage(),"Sorry to inform you that you need to stay in hospital for 2 rounds!",400,100);
		md.getBt().addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				cp.setCell(cell.getMap().getHospital());
				cp.setWaitingRound(2);
				cp.setDirection(Direction.anticlockwise);	
				Game.getGame().getMenuControler().go_on();
			}
		});	
	}
	private void _cardGift() {
		String message = "";
		Collection<Player> players = cell.getMap().getPlayers();
		message = players.stream().map(e->{
			 Prop p = PropType.getProp(e, PropType.allVirtualProp().get((int)(Math.random()*PropType.allVirtualProp().size())).getType());
			 e.addProp(p);
			 return e.getName()+" got a card \""+p.getName()+"\"！\n";
		}).reduce(message,(a,b)->(a+b));	
		MessageDialog md = new MessageDialog(game.getStage(),message,300,120);
		md.getBt().addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				Game.getGame().getMenuControler().go_on();
			}
		});
	}
	private void _financialTax() {
		String message = "Everyone is obliged to pay 10% for property tax!\n";
		message = cell.getMap().getPlayers().stream().map(e->{
			String tax = new DecimalFormat("0.00").format(e.getDeposite()*0.1);
			e.addDeposite(e.getDeposite()*(-0.1));
			return e.getName()+" paid "+tax+" yuan.\n";
		}).reduce(message,(a,b)->(a+b));	
		game.freshPlayerInfo();
		MessageDialog md = new MessageDialog(game.getStage(),message,300,120);	
		md.getBt().addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				Game.getGame().getMenuControler().go_on();
			}
		});
	}
	private void _interest() {
		String message = "Share is issued to everyone for 10% of deposite!\n";
		message = cell.getMap().getPlayers().stream().map(e->{
			String share = new DecimalFormat("0.00").format(e.getDeposite()*0.1);
			e.addDeposite(e.getDeposite()*0.1);
			return e.getName()+" got share "+share+" yuan.\n";
		}).reduce(message,(a,b)->(a+b));	
		game.freshPlayerInfo();
		MessageDialog md = new MessageDialog(game.getStage(),message,300,120);	
		md.getBt().addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				Game.getGame().getMenuControler().go_on();
			}
		});
	}
	private void _supply() {
		List<Player> p = cell.getMap().getMost(false);
		double money = Math.random()*1000+1;
		String names="";
		MessageDialog md = new MessageDialog(game.getStage(),"Player who own least lands ( "
					+p.stream().map(e->(e.getName())).reduce(names, (a,b)->(a+b+" "))+") \n"
							+ "is supplied with "+new DecimalFormat("0.00").format(money)+" yuan。\n",250,120);
		p.forEach(e->(e.addCash(money)));	
		game.freshPlayerInfo();
		md.getBt().addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				Game.getGame().getMenuControler().go_on();
			}
		});
	}
	private void _praise() {
		List<Player> p = cell.getMap().getMost(true);
		double money = Math.random()*1000+1;
		String names="";
		MessageDialog md = new MessageDialog(game.getStage(),"Player who own most lands ( "
				+p.stream().map(e->(e.getName())).reduce(names, (a,b)->(a+b+" "))+") \n"
						+ "is supplied with "+new DecimalFormat("0.00").format(money)+" yuan。\n",250,120);
		p.forEach(e->(e.addCash(money)));
		game.freshPlayerInfo();
		md.getBt().addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				Game.getGame().getMenuControler().go_on();
			}
		});
		md.setOnCloseRequest(new EventHandler<WindowEvent>(){
			@Override
			public void handle(WindowEvent arg0) {
				Game.getGame().getMenuControler().go_on();
			}
		});
	}
	
	/*
	 * general part
	 */
	@Override
	public String getDiscription() {
		return "类型：新闻点\n"
				+ "名称：小智新闻站";
	}
}
