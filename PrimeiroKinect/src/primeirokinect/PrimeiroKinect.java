package primeirokinect;

import java.util.ArrayList;
import java.util.List;

import jogamp.opengl.glu.mipmap.Image;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;


public class PrimeiroKinect extends PApplet {
	
	SimpleOpenNI kinect;
	List<Integer> userIds;
	int menorDistancia = 9999;
	
	PImage icone;
	public void setup() {
		size(640, 480);
		frameRate(30);
		kinect = new SimpleOpenNI(this);
		kinect.enableDepth();
		//kinect.enableRGB();
		kinect.enableUser(SimpleOpenNI.SKEL_PROFILE_ALL);
		
		kinect.setMirror(true);
		userIds = new ArrayList<Integer>();
		icone = loadImage("chris.png");
		
	}

	public void draw() {
		kinect.update();
		background(0);
		image(kinect.depthImage(), 0, 0);
		//image(kinect.rgbImage(), 0, 0);
		
		for(Integer userId : userIds){
			if(kinect.isTrackingSkeleton(userId)){
				desenharEsqueleto(userId);
				int[] arrayPixels = kinect.depthMap();
				for(int i =0; i < arrayPixels.length; i++){
					if(arrayPixels[i] < menorDistancia){
						menorDistancia = arrayPixels[i];
					}
				}
				PVector maoDireita = new PVector();
				kinect.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_RIGHT_HAND, maoDireita);
				PVector maoDireitaConvertida = new PVector();
				kinect.convertRealWorldToProjective(maoDireita, maoDireitaConvertida);
				fill(255,0,0);
				ellipse(maoDireitaConvertida.x, maoDireitaConvertida.y, 20, 20);
			
				PVector cabeca = new PVector();
				kinect.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_HEAD, cabeca);
				PVector cabecaConvertida = new PVector();
				kinect.convertRealWorldToProjective(cabeca, cabecaConvertida);
				
				image(icone, cabecaConvertida.x - 70, cabecaConvertida.y - 100);
				
			}
		}
		
		
		
		
		
	}
	
	
	public void desenharEsqueleto(int userId){
		kinect.drawLimb(userId, SimpleOpenNI.SKEL_HEAD, SimpleOpenNI.SKEL_NECK);
		kinect.drawLimb(userId, SimpleOpenNI.SKEL_NECK, SimpleOpenNI.SKEL_LEFT_SHOULDER);
		kinect.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_SHOULDER, SimpleOpenNI.SKEL_LEFT_ELBOW);
		kinect.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_ELBOW, SimpleOpenNI.SKEL_LEFT_HAND);
		 
		kinect.drawLimb(userId, SimpleOpenNI.SKEL_NECK, SimpleOpenNI.SKEL_RIGHT_SHOULDER);
		kinect.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_SHOULDER, SimpleOpenNI.SKEL_RIGHT_ELBOW);
		kinect.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_ELBOW, SimpleOpenNI.SKEL_RIGHT_HAND);
		 
		kinect.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_SHOULDER, SimpleOpenNI.SKEL_TORSO);
		kinect.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_SHOULDER, SimpleOpenNI.SKEL_TORSO);
		 
		kinect.drawLimb(userId, SimpleOpenNI.SKEL_TORSO, SimpleOpenNI.SKEL_LEFT_HIP);
		kinect.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_HIP, SimpleOpenNI.SKEL_LEFT_KNEE);
		kinect.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_KNEE, SimpleOpenNI.SKEL_LEFT_FOOT);
		 
		kinect.drawLimb(userId, SimpleOpenNI.SKEL_TORSO, SimpleOpenNI.SKEL_RIGHT_HIP);
		kinect.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_HIP, SimpleOpenNI.SKEL_RIGHT_KNEE);
		kinect.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_KNEE, SimpleOpenNI.SKEL_RIGHT_FOOT);  

	
	
	}
	
	
	public void mousePressed(){
		int[] arrayPixels = kinect.depthMap();
		int cliquei = mouseX + mouseY * 640;
		int mm = arrayPixels[cliquei];
		println("Distancia = " + mm + "mm");
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
	
	
	
}
