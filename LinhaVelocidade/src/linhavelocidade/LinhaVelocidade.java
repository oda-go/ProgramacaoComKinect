package linhavelocidade;

import processing.core.PApplet;


public class LinhaVelocidade extends PApplet {

	public void setup() {
		size(600,600);
		background(255);
		
	}

	public void draw() {
		int velocidade = abs(mouseX - pmouseX);
		
		
		line(pmouseX, pmouseY, mouseX, mouseY);
		strokeWeight(velocidade);
		
	}
	
}
