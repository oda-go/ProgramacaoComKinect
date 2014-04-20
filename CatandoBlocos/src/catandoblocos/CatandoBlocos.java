package catandoblocos;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.contacts.Contact;

import pbox2d.PBox2D;
import processing.core.PApplet;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;

public class CatandoBlocos extends PApplet {

	PBox2D mundo;
	SimpleOpenNI kinect;
	List<Integer> userIds;
	List<Box> caixas;
	
	public void setup() {
		size(640,400);
		smooth();
		frameRate(30);
		userIds = new ArrayList<Integer>();
		caixas = new ArrayList<Box>();
		mundo = new PBox2D(this);
		mundo.createWorld();
		mundo.setGravity(0, -2);
		
		mundo.listenForCollisions();
		
		kinect = new SimpleOpenNI(this);
		kinect.enableDepth();
		kinect.enableUser(SimpleOpenNI.SKEL_PROFILE_ALL);
		kinect.setMirror(true);
	}
	
	
	
	

	public void draw() {
		kinect.update();
		background(255);
		mundo.step();
		image(kinect.depthImage(), 0, 0);
		for(Integer userId : userIds){
			if(kinect.isTrackingSkeleton(userId)){
				PVector maoDireita = new PVector();
				kinect.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_RIGHT_HAND, maoDireita);
				PVector maoDireitaConvertida = new PVector();
				kinect.convertRealWorldToProjective(maoDireita, maoDireitaConvertida);
				fill(255,0,0);
//				ellipse(maoDireitaConvertida.x, maoDireitaConvertida.y, 20, 20);
				Box box = new Box(maoDireitaConvertida.x, maoDireitaConvertida.y, 16, 16, true);
				box.display();
//				fill(255,0,0);
//				ellipse(maoEsquerda.x, maoEsquerda.y, 20, 20);
//				System.out.println(maoEsquerda.x + " " + maoEsquerda.y);
			}
		}
		
		if(mousePressed){
			Box b = new Box(mouseX, mouseY, 16, 16, false);
			caixas.add(b);
		}
		
		for(Box box : caixas){
			box.display();
		}
		
	}
	
	public void onNewUser(int id){
		println("Detectei alguem");
		kinect.startPoseDetection("Psi", id);
		userIds.add(id);
	}
	public void onStartPose(String pose, int id){
		println("fazendo a pose do ladrao " + pose);
		kinect.stopPoseDetection(id); //Pare de esperar pela pose
		kinect.requestCalibrationSkeleton(id, true);
	}
	
	public void onStartCalibration(int id){
		println("Iniciando calibracao do esqueleto ... fique parado");
	}
	
	public void onEndCalibration(int id, boolean sucesso){
		println("Finalizando calibracao");
		if(sucesso){
			println("SUCESSSSSOOOOO !!!");
			kinect.startTrackingSkeleton(id);
		}else{
			println("Tente novamente ...");
		}
	}
	
	public void beginContact(Contact c){
		System.out.println("Colidiu !!");
		
//		Fixture textura1 = c.getFixtureA();
//		Fixture textura2 = c.getFixtureB();
//		
//		Body corpoA = textura1.getBody();
//		Body corpoB = textura2.getBody();
//		
//		Vec2 posB = mundo.getBodyPixelCoord(corpoB);
//		fill(0,0,255); // cor do preenchimento
//		stroke(0); // cor da borda
//		rectMode(CENTER);
//		rect(posB.x, posB.y, 16, 16);
//		
//		mundo.destroyBody(corpoA);
		
		
	}
	
	public void endContact(Contact c){
		System.out.println("Terminou o contato");
		
	}
	
	class Box {
		Body corpo;

		float x, y, w, h;
		boolean fixa;
		public Box(float _x, float _y, float _w, float _h, boolean fixa) {
			x = _x;
			y = _y;
			w = _w;
			h = _h;
			this.fixa = fixa;
			BodyDef definicao = new BodyDef();
			Vec2 posicao = mundo.coordPixelsToWorld(x, y);
			definicao.position.set(posicao);
			definicao.bullet = true;

			if (fixa) {
				definicao.type = BodyType.STATIC;
			} else {
				definicao.type = BodyType.DYNAMIC;
			}

			corpo = mundo.createBody(definicao);

			PolygonShape shape = new PolygonShape();

			float wMundoReal = mundo.scalarPixelsToWorld(w / 2);
			float hMundoReal = mundo.scalarPixelsToWorld(h / 2);

			shape.setAsBox(wMundoReal, hMundoReal);

			FixtureDef textura = new FixtureDef();
			textura.shape = shape;
			textura.density = 1;
			textura.friction = 0.3f;
			textura.restitution = 2f;

			corpo.createFixture(textura);

		}
		void display(){
			fill(175); // cor do preenchimento
			stroke(0); // cor da borda
			Vec2 posB = mundo.getBodyPixelCoord(corpo);
			float a = corpo.getAngle();
			pushMatrix();
			translate(posB.x, posB.y);
			rotate(-a);
			rectMode(CENTER);
			rect(0, 0, w, h);
			popMatrix();
		}
	}
}
