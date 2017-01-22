package entity.prop;

import java.util.*;

import entity.player.Player;

public enum PropType {
	barrier,
	cashRedistributor,
	detainer,
	diceControler,
	predator,
	taxChecker,
	turnover;
	
	public static List<Prop> allVirtualProp(){
		List<Prop> all = new ArrayList<Prop>();
		all.add(new Barrier());
		all.add(new CashRedistributor());
		all.add(new Detainer());
		all.add(new DiceControler());
		all.add(new Predator());
		all.add(new TaxChecker());
		all.add(new Turnover());
		return all;
	}
	public static Prop getProp(Player p,PropType t){
		switch(t){
		case barrier:return new Barrier(p);
		case cashRedistributor:return new CashRedistributor(p);
		case detainer:return new Detainer(p);
		case diceControler:return new DiceControler(p);
		case predator:return new Predator(p);
		case taxChecker:return new TaxChecker(p);
		case turnover:return new Turnover(p);
		default:
			return null;
		}
	}
}
