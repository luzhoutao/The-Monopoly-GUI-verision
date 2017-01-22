package entity.prop;

import java.io.IOException;

import controler.GameGUI;
import entity.player.Player;
import javafx.stage.Stage;

public abstract class Prop {
	protected Player owner;
	protected int price;
	private PropType type;
	public Prop(PropType type){
		this.owner = null;
		this.type = type;
	}
	public Prop(Player owner, PropType type){
		this.owner = owner;
		this.type = type;
	}
	/*The card itself is responsible for removing from owner's list*/
	public abstract void _work(Stage stage, GameGUI game);
	public abstract boolean canWork();
	public abstract String getDescription();
	public abstract String getName();
	public PropType getType(){
		return this.type;
	}
	public abstract int getPrice();
	
	public void removeFromOwner(){
		this.owner.getMyProp().remove(this);
	}
	public void addToWorkingProp(){
		this.owner.getWorkingProp().add(this);
	}
}
