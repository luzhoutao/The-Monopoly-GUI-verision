package entity.land;

import entity.map.Cell;
import entity.player.Player;

import java.io.IOException;

import controler.Game;
import controler.GameGUI;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Hospital extends Land{	
	
	/*
	 * GUI version
	 */
	public Hospital(Cell cell,GameGUI game) {
		super(cell,LandType.hospital,game);
		name = "����ҽԺ";
		this.image = new ImageView(new Image("file:icons/hospital.gif"));
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
		return "���ͣ�ҽԺ\n"
				+ "���ƣ�"+name;
	}
}
