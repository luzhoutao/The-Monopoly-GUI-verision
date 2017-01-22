package entity.land;

import java.io.IOException;

import controler.GameGUI;
import controler.MessageDialog;
import entity.map.Cell;
import entity.player.Player;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Land {
	
	protected Cell cell;
	protected String name;
	private LandType type;
	protected ImageView image;
	protected GameGUI game;
	
	/*
	 * GUI version 
	 */
	public Land(Cell cell,LandType type,GameGUI game) {
		this.cell = cell;
		this.type = type;
		this.game = game;
	}
	public Node toGraphics(){
		int w = Cell.w;  
		Rectangle r = new Rectangle();
		r.setHeight(w);
		r.setWidth(w);
		r.setArcWidth(w/3);
		r.setArcHeight(w/3);
		r.setFill(Color.WHITE);
		r.setOpacity(0.2);
		r.setStroke(Color.BLACK.brighter().brighter());
		return r;
	}
	public abstract void _response(Player controlPlayer);
	public ImageView getImage() {
		return this.image;
	}
	
	/*
	 * general part
	 */
	public LandType getType() {
		return type;
	}
	public String getName(){
		return this.name;
	}
	public abstract String getDiscription();

	public static Land getLand(LandType type, Cell cell, GameGUI game) {
		switch (type){
		case house: return new House(cell,game); 
		case propShop: return new PropShop(cell,game); 
		case bank: return new Bank(cell,game);
		case news: return new News(cell,game); 
		case cardGift: return new CardGift(cell,game); 
		case emptyLand: return new EmptyLand(cell,game); 
		case couponGift: return new CouponGift(cell,game); 
		case lottery: return new Lottery(cell,game); 
		case hospital: return new Hospital(cell,game);
		default: return null;
		}
	}
}
