package entity.land;

import controler.Game;
import controler.GameGUI;
import controler.MessageDialog;
import entity.map.Cell;
import entity.player.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.WindowEvent;


public class CouponGift extends Land {
	/*
	 * GUI version
	 */
	public CouponGift(Cell cell,GameGUI game) {
		super(cell,LandType.couponGift,game);
		name = "点券大放送";
		this.image = new ImageView(new Image("file:icons/coupon_gift.gif"));
	}

	@Override
	public void _response(Player cp) {
		int coupon = (int)(Math.random()*40)+20;
		cp.addCoupon(coupon);
		MessageDialog md = new MessageDialog(this.game.getStage(),"Cheer for you! "+cp.getName()+", you've got "+coupon+" coupon.",300,60);
		this.game.freshPlayerInfo();
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
		// TODO Auto-generated method stub
		return "类型：点券赠送点\n"
				+ "名称：点券大放送";
	}

	
}
