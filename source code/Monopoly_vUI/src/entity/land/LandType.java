package entity.land;

public enum LandType {
	house,
	propShop,
	bank,
	news,
	cardGift,
	emptyLand,
	couponGift,
	lottery,
	hospital;
	
	public static LandType getLandType(char c){
		switch (c){
		case 't': return LandType.house; 
		case 'c': return LandType.lottery; 
		case 'q': return LandType.couponGift;
		case 'x': return LandType.news; 
		case 'k': return LandType.emptyLand; 
		case 'y': return LandType.bank; 
		case 'd': return LandType.propShop; 
		case 'n': return LandType.cardGift; 
		case 'h': return LandType.hospital;
		default: return null;
		}
	}
}
