package entity.land;

import controler.Game;
import controler.GameGUI;
import entity.map.Cell;
import entity.player.Player;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


public class EmptyLand extends Land {

	/*
	 * GUI version
	 */
	public EmptyLand(Cell cell,GameGUI game) {
		super(cell,LandType.emptyLand,game);
		name = "空地";
	}
	
	@Override
	public Node toGraphics() {
		int w = Cell.w;
		Circle r = new Circle();
		r.setRadius(w/2);
		r.setFill(Color.WHITE);
		r.setOpacity(0.2);
		r.setStroke(Color.BLACK);
		return r;
	}

	@Override
	public void _response(Player controlPlayer) {
		Game.getGame().getMenuControler().go_on();
	}
	/*
	 * general part
	 */
	@Override
	public String getDiscription() {
		// TODO Auto-generated method stub
		return "类型：空地";
	}

	
}
