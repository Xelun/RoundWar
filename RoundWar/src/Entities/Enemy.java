package Entities;

import java.util.LinkedList;

import roundwar.PathFinder;
import screenControl.GameScreen;

import com.badlogic.gdx.math.Vector2;

public class Enemy extends LivingEntity{
	private static MainCharacter mainpj;
	private Vector2 nextStep;
	public int lvl;
	protected PathFinder pathFinder;
	protected LinkedList<Vector2> path;
	
	public Enemy(Type type, GameScreen game) {
		super(type, game);
		lvl = 0;
		setPosition(300, 80);
    	pathFinder = new PathFinder();
		path = pathFinder.findPath(this, getCenterX(), getCenterY(), mainpj.getCenterX(), mainpj.getCenterY());
		nextStep = path.getFirst();
		//System.out.println("nextStep: " + nextStep);
		//System.out.println("Enemy:    [" + getCenterX() + ":" + getCenterY() + "]");
		//System.out.println("path:     " + path);
		//System.out.println("Mainpj:   [" + mainpj.getCenterX() + ":" + mainpj.getCenterY() + "]");
	}
	
	public static void setEnemy(LivingEntity enemy) {
		Enemy.mainpj = (MainCharacter) enemy;
	}

	@Override
	public void act (float delta){
		super.act(delta);
		float deltaX = 0, deltaY = 0;
		if(nextStep.x != bounds.x) {
			if(nextStep.x - bounds.x > 0)
				deltaX = statVel;
			else
				deltaX = -1*statVel;
		}
		if(nextStep.y != bounds.y) {
			if(nextStep.y - bounds.y > 0)
				deltaY = statVel;
			else
				deltaY = -1*statVel;
		}
		float rot = getRotation() - (float) Math.atan2(deltaY, deltaX)*57.3f;
		if((rot >= 180) || (rot < 0 && rot > -180)) {
			rotate(0.5f);
		} else if ((rot > 0 && rot < 180) || (rot <= -180)){
			rotate(-0.5f);
		}
		//setRotation((float) Math.atan2(deltaY, deltaX)*57.3f); //Rota hacia donde avanza
		moveEntity(deltaX, deltaY);
		path = pathFinder.findPath(this, getCenterX(), getCenterY(), mainpj.getCenterX(), mainpj.getCenterY());
		if(path !=null) {
			nextStep = path.getFirst();
		} else {
			System.out.println("Tocado");
		}
			
		//}
	}
}
