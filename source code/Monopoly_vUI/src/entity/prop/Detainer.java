package entity.prop;

import java.io.IOException;

import controler.GameGUI;
import controler.MessageDialog;
import entity.player.Player;
import javafx.stage.Stage;

public class Detainer extends Prop {
	
	public Detainer() {
		super(null,PropType.detainer);
		price = 20;
	}
	public Detainer(Player owner) {
		super(owner,PropType.detainer);
		price = 20;
	}

	@Override
	public String getDescription() {
		return "卡片名：滞留卡\n"
				+ "卡片使用对象：自己\n"
				+ "卡片使用效果：\n"
				+ "该回合停留在原地，并再次触发原地事件";
	}

	@Override
	public String getName() {
		return "滞留卡";
	}
	
	public int getPrice() {
		return price;
	}
	@Override
	public boolean canWork() {
		return true;
	}
	@Override
	public void _work(Stage stage, GameGUI game) {
		owner.getWorkingProp().add(this);
		removeFromOwner();
		new MessageDialog(stage,"You are prohibited to move in this round.",280,100);
	}

}
