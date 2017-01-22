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
		return "��Ƭ����������\n"
				+ "��Ƭʹ�ö����Լ�\n"
				+ "��Ƭʹ��Ч����\n"
				+ "�ûغ�ͣ����ԭ�أ����ٴδ���ԭ���¼�";
	}

	@Override
	public String getName() {
		return "������";
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
