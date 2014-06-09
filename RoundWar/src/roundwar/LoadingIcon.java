package roundwar;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LoadingIcon extends Actor {

    Animation animation;
    TextureRegion reg;
    float stateTime;

    /**
     * Constructor.
     * @param animation
     */
    public LoadingIcon(Animation animation) {
        this.animation = animation;
        reg = animation.getKeyFrame(0);
    }

    /**
     * Actualiza el gif de carga.
     */
    @Override
    public void act(float delta) {
        stateTime += delta;
        reg = animation.getKeyFrame(stateTime);
    }

    /**
     * Dibuja el gif de carga.
     */
    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        batch.draw(reg, getX(), getY());
    }
}