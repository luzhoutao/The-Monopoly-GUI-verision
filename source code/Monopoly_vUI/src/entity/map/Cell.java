package entity.map;
import java.util.ArrayList;
import java.util.Collection;

import controler.GameGUI;
import entity.land.*;
import entity.player.Direction;
import entity.player.Player;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;


public class Cell extends StackPane{

	public final static int w = 40;
	private int x;
	private int y;
	private Land land;
	private Map map;
	private boolean isBlocked = false;
	private Cell nextCell;
	private Cell prevCell;
	private ImageView barrier = new ImageView(new Image("file:icons/barrier.gif"));
	private Collection<Player> players = new ArrayList<Player>();
	
	/*
	 * GUI part
	 */
	public Cell(Map map,int x,int y,LandType type,GameGUI game) {
		this.map = map;
		this.x = x;
		this.y = y;
		this.land = Land.getLand(type,this,game);
		this.toGraghic();
		barrier.setFitHeight(Cell.w);
		barrier.setFitWidth(Cell.w);
	}
	public void toGraghic(){
		this.getChildren().add(land.toGraphics());
		ImageView iv = land.getImage();
		if(iv!=null){
			iv.setFitHeight(w);
			iv.setFitWidth(w);
			this.getChildren().add(iv);
		}
	}
	/*
	 * general functions
	 */
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Land getLand() {
		return land;
	}

	public void addPlayer(Player player){
		players.add(player);
		this.getChildren().add(player.getImageView());
	}
	
	public void removePlayer(Player player) {
		players.remove(player);
		this.getChildren().remove(player.getImageView());
	}
	
	public Map getMap() {
		return map;
	}

	//assert there is no intersection path
	public Cell getCell(Direction d){
		if(d == Direction.clockwise)
			return nextCell;
		else if(d == Direction.anticlockwise)
			return prevCell;
		else 
			return null;
	}

	public Cell getAnotherCell(boolean isForward, int distance){
		Cell checkCell = this;
		for(int i = 0;i<distance;i++){
			checkCell = isForward?checkCell.nextCell:checkCell.prevCell;
		}
		return checkCell;
	}
	
	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked() {
		this.isBlocked = true;
		this.getChildren().add(barrier);
	}
	public Cell getNextCell() {
		return nextCell;
	}

	public Cell getPrevCell() {
		return prevCell;
	}
	
	public void setNextCell(Cell nextCell) {
		this.nextCell = nextCell;
	}

	public void setPrevCell(Cell prevCell) {
		this.prevCell = prevCell;
	}

	/*
	 * @return the distance between the target cell on the assigned direction
	 */
	public int getDistance(Direction d, Cell target){
		int d1 = 0;
		Cell curCell = this;
		while(curCell!=target){
			curCell = curCell.getCell(d);
			d1++;
		}
		curCell = this;
		int d2 = 0;
		while(curCell!=target){
			curCell = curCell.getCell(Direction.counter(d));
			d2++;
		}
		return Math.min(d1, d2);
	}

	public void removeBarrier() {
		this.isBlocked = false;
		this.getChildren().remove(barrier);
	}

	public ArrayList<Cell> getStreet() {
		ArrayList<Cell> street = new ArrayList<Cell>();
		int x,y;
		if((x=this.prevCell.getX())==this.nextCell.getX()){
			street.add(this);
			Cell pre=prevCell;
			while(pre.getX()==x){
				street.add(pre);
				pre=pre.prevCell;
			}
			Cell next=nextCell;
			while(next.getX()==x){
				street.add(next);
				next=next.nextCell;
			}
		}else if((y=this.prevCell.getY())==this.nextCell.getY()){
			street.add(this);
			Cell pre=prevCell;
			while(pre.getY()==y){
				street.add(pre);
				pre=pre.prevCell;
			}
			Cell next=nextCell;
			while(next.getY()==y){
				street.add(next);
				next=next.nextCell;
			}
		}
		return street;
	}

}
