package entity.prop;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collection;

import controler.GameGUI;
import controler.MessageDialog;
import entity.player.Player;
import javafx.stage.Stage;

public class CashRedistributor extends Prop {

	public CashRedistributor() {
		super(null,PropType.cashRedistributor);
		price = 37;
	}
	public CashRedistributor(Player owner) {
		super(owner,PropType.cashRedistributor);
		price = 37;
	}
	@Override
	public String getDescription() {
		return "��Ƭ����������\n"
				+ "��Ƭ���ö���������ң����Ӿ��룩\n"
				+ "��Ƭʹ��Ч����\n"
				+ "�������˵��ֽ�ƽ������";
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
	public void _work(Stage stage,GameGUI game) {
		Collection<Player> players = owner.getCell().getMap().getPlayers();
		double totalCash = players.stream().map(e->(e.getCash())).reduce(0.0, (a,b)->(a+b));
		double meanCash = totalCash/players.size();
		players.forEach(e->(e.setCash(meanCash)));
		removeFromOwner();
		game.freshPlayerInfo();
		new MessageDialog(stage,"Done! \nEneryone's cash are reset to "+new DecimalFormat("#.00").format(meanCash)+" yuan!",280,120);		
	}
}
