package gravityswarm;

import processing.core.PApplet;


public class GravitySwarm extends PApplet {

	Particle[] Z = new Particle[6000]; //Cria um array com 6000 particulas
	float colour = random(1); //TODO: Escolhe uma cor qualquer, escolha a sua !!
	 
	public void setup() {
	  smooth(); //Suaviza todas as formas geometricas
	  
	  size(500,500,P2D); //Tamanho 500, 500 2D
	  
	  //TODO: fundo branco
	   
	  for(int i = 0; i < Z.length; i++) {
	    Z[i] = new Particle( random(width), random(height), 0, 0, 1 );
	  }
	   
	  frameRate(60);
	  colorMode(RGB,255);
	 
	}

	public void draw() {
		  float r;
		 
		  stroke(0);
		  fill(255);
		  rect(0,0,width,height);
		  
		  colorMode(HSB,1);
		  for(int i = 0; i < Z.length; i++) {
		    if( mousePressed && mouseButton == LEFT ) {
		      Z[i].gravitate( new Particle( mouseX, mouseY, 0, 0, 1 ) );
		    }
		    else if( mousePressed && mouseButton == RIGHT ) {
		      Z[i].repel( new Particle( mouseX, mouseY, 0, 0, 1 ) );
		    }
		    else {
		      Z[i].deteriorate();
		    }
		    Z[i].update();
		    r = (float)i/Z.length;
		    stroke( colour, (float)Math.pow(r,0.1), 1-r, (float) 0.15 );
		    Z[i].display();
		  }
		  colorMode(RGB,255);
		   
		  colour+=Math.random();
		  if( colour > 1 ) {
		    colour = colour%1;
		  }
		 
		}

	
	
	
	
	
	
	
	class Particle {
		   
		  float x;
		  float y;
		  float px;
		  float py;
		  float magnitude;
		  float angle;
		  float mass;
		   
		  Particle( float dx, float dy, float V, float A, float M ) {
		    x = dx;
		    y = dy;
		    px = dx;
		    py = dy;
		    magnitude = V;
		    angle = A;
		    mass = M;
		  }
		   
		  void reset( float dx, float dy, float V, float A, float M ) {
		    x = dx;
		    y = dy;
		    px = dx;
		    py = dy;
		    magnitude = V;
		    angle = A;
		    mass = M;
		  }
		   
		  void gravitate( Particle Z ) {
		    float F, mX, mY, A;
		    if( sq( x - Z.x ) + sq( y - Z.y ) != 0 ) {
		      F = mass * Z.mass;
		      mX = ( mass * x + Z.mass * Z.x ) / ( mass + Z.mass );
		      mY = ( mass * y + Z.mass * Z.y ) / ( mass + Z.mass );
		      A = findAngle( mX - x, mY - y );
		       
		      mX = F * cos(A);
		      mY = F * sin(A);
		       
		      mX += magnitude * cos(angle);
		      mY += magnitude * sin(angle);
		       
		      magnitude = sqrt( sq(mX) + sq(mY) );
		      angle = findAngle( mX, mY );
		    }
		  }
		 
		  void repel( Particle Z ) {
		    float F, mX, mY, A;
		    if( sq( x - Z.x ) + sq( y - Z.y ) != 0 ) {
		      F = mass * Z.mass;
		      mX = ( mass * x + Z.mass * Z.x ) / ( mass + Z.mass );
		      mY = ( mass * y + Z.mass * Z.y ) / ( mass + Z.mass );
		      A = findAngle( x - mX, y - mY );
		       
		      mX = F * cos(A);
		      mY = F * sin(A);
		       
		      mX += magnitude * cos(angle);
		      mY += magnitude * sin(angle);
		       
		      magnitude = sqrt( sq(mX) + sq(mY) );
		      angle = findAngle( mX, mY );
		    }
		  }
		   
		  void deteriorate() {
		    magnitude *= 0.925;
		  }
		   
		  void update() {
		     
		    x += magnitude * cos(angle);
		    y += magnitude * sin(angle);
		     
		  }
		   
		  void display() {
		    line(px,py,x,y);
		    px = x;
		    py = y;
		  }
		   
		   
		}
		 
		float findAngle( float x, float y ) {
		  float theta;
		  if(x == 0) {
		    if(y > 0) {
		      theta = HALF_PI;
		    }
		    else if(y < 0) {
		      theta = 3*HALF_PI;
		    }
		    else {
		      theta = 0;
		    }
		  }
		  else {
		    theta = atan( y / x );
		    if(( x < 0 ) && ( y >= 0 )) { theta += PI; }
		    if(( x < 0 ) && ( y < 0 )) { theta -= PI; }
		  }
		  return theta;
		}
	
}





















