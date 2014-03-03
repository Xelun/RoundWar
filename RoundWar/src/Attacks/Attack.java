package Attacks;

import screenControl.HudControl;
import Entities.LivingEntity;

public class Attack {
	private static float damage;
	
	public static boolean doAttack(LivingEntity entity, HudControl.Attack type, float posX, float posY, RangeAttack range) {
		if (range.inRange(posX, posY)){
			switch(type) {
			case NEAR:
				entity.updateMana(-10);
				damage = 10;
				break;
			case FAR:
				damage = 60;
				break;
			case RUN:
				damage = 40;
				break;
			case INAREA:
				damage = 80;
				break;
			default:
				damage = 0;
				break;
		}
		} else {
			
		}
		return false;
	}
	
}
