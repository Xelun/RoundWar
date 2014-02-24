package Entities;

public class Enemy extends LivingEntity{
	public int lvl;
    public int score;
	
	public Enemy(Type type, String name) {
		super(type, name);
		lvl = 0;
    	score = 0;	
	}

	public void setPosition(int h, int w){
    	//Paso 6
        //Movimiento de la Gamba, el bucle render es ciclico, así que aprobecharemos para ir 
        //restandole -0.5 a su coordenada x contantemente.
        entityCircle.x = entityCircle.x - 0.5f;
        //Si llega al -60 de la pantalla, es decir se esconde en ella, vuelve a empezar.
        if(entityCircle.x < -64){
        	entityCircle.x = w;
        }
    }
}
