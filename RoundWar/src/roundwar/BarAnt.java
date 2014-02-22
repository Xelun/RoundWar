package roundwar;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Pools;

public abstract class BarAnt extends Actor{
	
	protected ProgressBarStyle style;
	protected float max, stepSize;
	protected float value, animateFromValue;
	protected float w, h;
	protected float progressPos;
	int draggingPointer = -1;
	protected float animateDuration, animateTime;
	protected Interpolation animateInterpolation = Interpolation.linear;
	protected float[] snapValues;
	protected float threshold;
	
	public BarAnt (float max, float stepSize, Skin skin, float width, float height, float value) {
		if (max < 0) throw new IllegalArgumentException("max must be > 0: " + max);
	    if (stepSize <= 0) throw new IllegalArgumentException("stepSize must be > 0: " + stepSize);
	    setStyle(skin.get("default", ProgressBarStyle.class));
	    this.max = max;
	    this.stepSize = stepSize;
	    this.value = value;
	    this.w = width;
	    this.h = height;
	}
	
	public void setStyle (ProgressBarStyle style) {
		if (style == null) throw new IllegalArgumentException("style cannot be null.");
		this.style = style;
	}

	   /** Returns the progress bar's style. Modifying the returned style may not have an effect until {@link #setStyle(ProgressBarStyle)} is
	    * called. */
	public ProgressBarStyle getStyle () {
		return style;
	}

	public void act (float delta) {
		animateTime -= delta;
	}

	public void resize(int width, int height){
		this.w = width;
		this.h = height;
	}
	   
	public void draw (SpriteBatch batch) {
		Drawable bg = style.background;
		Drawable knobBefore = style.progress;

		float value = getVisualValue();
      
		batch.setColor(255, 0, 0, 1);

		bg.draw(batch, (int)((w - bg.getMinWidth()) * 0.5f), 0, bg.getMinWidth(), h);

		float progressPosHeight = h - (bg.getTopHeight() + bg.getBottomHeight());
		if (max != 0) {
			progressPos = Math.max(0, value / max * (progressPosHeight));
			progressPos = Math.min(progressPosHeight, progressPos) + bg.getBottomHeight();
		}

		if (knobBefore != null) {
			knobBefore.draw(batch, (int)((w - knobBefore.getMinWidth()) * 0.5f), 0, knobBefore.getMinWidth(),
    			  (int)(progressPos));
		}
	}

	void calculatePositionAndValue (float x, float y) {
		final Drawable bg = style.background;
	
		float value;
		float oldPosition = progressPos;

	    float height = h - bg.getTopHeight() - bg.getBottomHeight();
	    progressPos = y - bg.getBottomHeight();
	    value = max * (progressPos / (height));
	    progressPos = Math.max(0, progressPos);
	    progressPos = Math.min(height, progressPos);
	      

	    float oldValue = value;
	    setValue(value);
	    if (value == oldValue) progressPos = oldPosition;
	}

	public float getValue () {
		return value;
	}

	   /** If {@link #setAnimateDuration(float) animating} the progress bar value, this returns the value current displayed. */
	public float getVisualValue () {
		if (animateTime > 0) return animateInterpolation.apply(animateFromValue, value, 1 - animateTime / animateDuration);
      	return value;
	}

   /** Sets the progress bar position, rounded to the nearest step size and clamped to the minumum and maximim values.
    * {@link #clamp(float)} can be overidden to allow values outside of the progress bars min/max range.
    * @return false if the value was not changed because the progress bar already had the value or it was canceled by a listener. */
	public void setValue (float value) {
		value = snap(clamp(Math.round(value / stepSize) * stepSize));
		float oldValue = this.value;
		if (value != oldValue) {
			float oldVisualValue = getVisualValue();
			this.value = value;
			ChangeEvent changeEvent = Pools.obtain(ChangeEvent.class);
			animateFromValue = oldVisualValue;
			animateTime = animateDuration;
			Pools.free(changeEvent);
		}
	}

	/** Clamps the value to the progress bars min/max range. */
	protected float clamp (float value) {
		return MathUtils.clamp(value, 0, max);
	}

	   /** Sets the range of this progress bar. The progress bar's current value is reset to min. */
	   public void setRange (float max) {
	      if (max < 0) throw new IllegalArgumentException("max must be > 0");
	      this.max = max;
	      if (value < 0) setValue(0);
	      else if (value > max) setValue(max);
	   }

	   /** Sets the step size of the progress bar */
	   public void setStepSize (float stepSize) {
	      if (stepSize <= 0) throw new IllegalArgumentException("steps must be > 0: " + stepSize);
	      this.stepSize = stepSize;
	   }

	   public float getMaxValue () {
	      return this.max;
	   }

	   public float getStepSize () {
	      return this.stepSize;
	   }

	   /** If > 0, changes to the progress bar value via {@link #setValue(float)} will happen over this duration in seconds. */
	   public void setAnimateDuration (float duration) {
	      this.animateDuration = duration;
	   }

	   /** Sets the interpolation to use for {@link #setAnimateDuration(float)}. */
	   public void setAnimateInterpolation (Interpolation animateInterpolation) {
	      if (animateInterpolation == null) throw new IllegalArgumentException("animateInterpolation cannot be null.");
	      this.animateInterpolation = animateInterpolation;
	   }

	   /** Will make this progress bar snap to the specified values, if the knob is within the threshold */
	   public void setSnapToValues (float[] values, float threshold) {
	      this.snapValues = values;
	      this.threshold = threshold;
	   }

	   /** Returns a snapped value, or the original value */
	   private float snap (float value) {
	      if (snapValues == null) return value;
	      for (int i = 0; i < snapValues.length; i++) {
	         if (Math.abs(value - snapValues[i]) <= threshold) return snapValues[i];
	      }
	      return value;
	   }


	   /** The style for a progress bar, see {@link ProgressBar}.*/
	   static public class ProgressBarStyle {
	      /** The progress bar background, stretched only in one direction. */
	      public Drawable background;
	      /** ProgressBar progress. */
	      public Drawable progress;

	      public ProgressBarStyle () {
	      }

	      public ProgressBarStyle (Drawable background, Drawable progress) {
	         this.background = background;
	         this.progress = progress;
	      }

	      public ProgressBarStyle (ProgressBarStyle style) {
	         this.background = style.background;
	         this.progress = style.progress;
	      }
	   }
	}
