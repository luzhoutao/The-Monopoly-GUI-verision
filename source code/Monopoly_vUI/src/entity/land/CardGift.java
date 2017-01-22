package entity.land;

import java.util.List;


















import controler.Game;
import controler.GameGUI;
import controler.MessageDialog;
import entity.map.Cell;
import entity.player.Player;
import entity.prop.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.WindowEvent;


public class CardGift extends Land {
	/*
	 * GUI version
	 */
	public CardGift(Cell cell,GameGUI game) {
		super(cell,LandType.cardGift,game);
		name = "道具大放送";
		this.image = new ImageView(new Image("file:icons/card_gift.jpg"));
	}

	@Override
	public void _response(Player cp) {
		List<Prop> allProp = PropType.allVirtualProp();
		Prop gift = allProp.get((int)(Math.random()*allProp.size()));
		cp.addProp(gift);
		MessageDialog md = new MessageDialog(game.getStage(),
				"Such a lucky guy! "+cp.getName()+", you've got a card \""+gift.getName()+"\".",300,100);
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
		return "类型：道具赠送点\n"
				+ "名称："+name;
	}
	
	
}
