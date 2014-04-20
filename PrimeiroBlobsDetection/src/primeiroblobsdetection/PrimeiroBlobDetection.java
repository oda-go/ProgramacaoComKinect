package primeiroblobsdetection;

import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PImage;
import blobDetection.Blob;
import blobDetection.BlobDetection;
import blobDetection.EdgeVertex;

public class PrimeiroBlobDetection extends PApplet {
	PImage img;
	BlobDetection theBlobDetection;

	public void setup() {

		img = loadImage("bob.jpg");
		img.resize(640, 480);

		size(640, 480);

		theBlobDetection = new BlobDetection(img.width, img.height);
		theBlobDetection.setThreshold(0.6f);
		theBlobDetection.computeBlobs(img.pixels);
	}

	public void draw() {
		background(0);
		contornar();
	}

	public void contornar() {
		Blob b;
		EdgeVertex eA, eB;
		for (int n = 0; n < theBlobDetection.getBlobNb(); n++) {
			b = theBlobDetection.getBlob(n);
			if (b != null) {
				stroke(255, 100, 100);

				for (int m = 0; m < b.getEdgeNb(); m++) {
					eA = b.getEdgeVertexA(m);
					eB = b.getEdgeVertexB(m);

					if (eA != null && eB != null)
						line(
								eA.x * img.width, eA.y * img.height,
								
								eB.x * img.width, eB.y * img.height
						);
				}
			}
		}
	}

}
