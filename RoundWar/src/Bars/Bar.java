/*******************************************************************************
 * Copyright (c) 2014
 *
 * @author Elisabet Romero Vaquero
 *******************************************************************************/
package Bars;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Bar extends Actor {

	protected SpriteBatch batch;
	protected NinePatch full, empty;
	protected float maxWidthBar, widthBar, heightBar, maxValue, value; 
	protected float x, y;

	/**
	 * Constructor.
	 * @param maxWidthBar Tamaño de la barra
	 * @param heightBar Ancho de la barra
	 * @param maxValue Valor máximo
	 * @param value Valor actual
	 */
	public Bar(float maxWidthBar, float heightBar, float maxValue, float value) {
		this.maxWidthBar = maxWidthBar;
		this.maxValue = maxValue;
		this.value = value;
		this.widthBar = this.maxWidthBar*(this.value/this.maxValue);
		this.heightBar = heightBar;
	}

	/**
	 * Dibuja el fondo de la barra y la superior.
	 */
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		empty.draw(batch, x, y, maxWidthBar+10, heightBar);
		full.draw(batch, x, y, widthBar+10, heightBar);
	}
	
	/**
	 * Establece el valor máximo de la barra.
	 * @param value
	 */
	public void setMaxValue(float value) {
		this.maxValue = value;
		if(this.value > maxValue) value = maxValue;
	}
	
	/**
	 * Actualiza el valor actual de la barra.
	 * @param value
	 */
	public void updateValue(float value){
		if(this.value != value){ //Ha cambiado el valor
			if (value > maxValue){
				widthBar = maxWidthBar;
				this.value = maxValue;
			} else if (value <= 0){
				widthBar = 0;
				this.value = 0;
			} else {
				this.value = value;
				widthBar = maxWidthBar*(value/maxValue);
			}
		}
	}
	
	/**
	 * Pone la posición de la barra.
	 */
	@Override
	public void setPosition(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Redimensiona la barra.
	 * @param width
	 * @param height
	 */
	public void resize(float width, float height) {
		maxWidthBar = width;
		widthBar = maxWidthBar*(value/maxValue);
		heightBar = height;
    }
}
