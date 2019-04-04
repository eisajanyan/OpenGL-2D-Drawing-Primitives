/***************************************************************
 * file: Isajanyan_Program1.java
 * author: Edward Isajanyan
 * class: CS 4450
 *
 * assignment: program 1
 * date: 2/11/2019
 *
 * purpose: Reads coordinates from a text file 'coordinates.txt'
 * and draws the primitives in a window.
 *
 ****************************************************************/

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.lwjgl.opengl.GL11.*;


public class Isajanyan_Program1 {
	// stores all primitives
	private ArrayList<float[]> primitives = new ArrayList<>();
	
	// start
	private void start() {
		try {
			// init primitives
			readCoordinates();
			
			// begin render
			createWindow();
			initGL();
			render();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// read coordinates from file
	private void readCoordinates() throws FileNotFoundException {
		Scanner  scanner = new Scanner(new File("coordinates.txt"));
		String   readLine;
		String[] tokens;
		float[]  primitive;
		
		while(scanner.hasNextLine()) {
			readLine = scanner.nextLine();
			tokens   = readLine.split("[ ,]");
			
			if(tokens[0].equals("l")) { // line
				primitive = new float[5];
				primitive[0] = Float.parseFloat(tokens[0]);
				
				// store vertex 1
				primitive[1] = Float.parseFloat(tokens[1]);
				primitive[2] = Float.parseFloat(tokens[2]);
				
				// store vertex 2
				primitive[3] = Float.parseFloat(tokens[3]);
				primitive[4] = Float.parseFloat(tokens[4]);
				
				primitives.add(primitive);
			}
			else if(tokens[0].equals("c")) { // circle
				primitive = new float[4];
				primitive[0] = Float.parseFloat(tokens[0]);
				
				// store center
				primitive[1] = Float.parseFloat(tokens[1]);
				primitive[2] = Float.parseFloat(tokens[2]);
				
				// store radius
				primitive[3] = Float.parseFloat(tokens[3]);
				
				primitives.add(primitive);
			}
			else if(tokens[0].equals("e")) { // ellipse
				primitive = new float[5];
				primitive[0] = Float.parseFloat(tokens[0]);
				
				// store center
				primitive[1] = Float.parseFloat(tokens[1]);
				primitive[2] = Float.parseFloat(tokens[2]);
				
				// store rx
				primitive[3] = Float.parseFloat(tokens[3]);
				
				// store ry
				primitive[4] = Float.parseFloat(tokens[4]);
				
				primitives.add(primitive);
			}
			
		} // while
		
		scanner.close();
	}
	
	// create window
	private void createWindow() throws Exception{
		Display.setFullscreen(false);
		
		Display.setDisplayMode(new DisplayMode(640, 480));
		Display.setTitle("Isajanyan_Program1");
		Display.create( );
	}
	
	// init GL
	private void initGL() {
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		
		glOrtho(-320, 320, -240, 240, 1, -1);
		
		glMatrixMode(GL_MODELVIEW);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
	}
	
	// render
	private void render() {
		// render loop
		while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			try {
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
				glLoadIdentity( );
				
				// draw primitives
				primitives.forEach(this::drawPrimitive);
				
				Display.update();
				Display.sync(60);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		Display.destroy( );
	}
	
	// draw given primitive
	private void drawPrimitive(float[] floats) {
		switch((char)floats[0]) {
			case 'l':
				drawLine(floats[1], floats[2], floats[3], floats[4]);
				break;
			case 'c':
				drawCircle(floats[1], floats[2], floats[3]);
				break;
			case 'e':
				drawEllipse(floats[1], floats[2], floats[3], floats[4]);
				break;
			default:
				System.out.println();
		}
		
	}
	
	// draw line
	private void drawLine(float x1, float y1, float x2, float y2) {
		glColor3f(1.0f, 0.0f, 0.0f);
		glPointSize(10);
		
		glBegin(GL_LINE_LOOP);
		glVertex2f(150, 200);
		glVertex2f(80, 145);
		glEnd( );
	}
	
	// draw circle
	private void drawCircle(float x, float y, float r) {
		glColor3f(0.0f, 0.0f, 1.0f);
		glPointSize(5);
		
		glBegin(GL_LINE_LOOP);
		glVertex2f(150, 200);
		glVertex2f(80, 145);
		glEnd( );
	}
	
	// draw ellipse
	private void drawEllipse(float x, float y, float rx, float ry) {
		glColor3f(0.0f, 1.0f, 0.0f);
		glPointSize(5);
		
		glBegin(GL_LINE_LOOP);
		glVertex2f(150, 200);
		glVertex2f(80, 145);
		glEnd( );
	}
	
	// main
	public static void main(String[] args) {
		Isajanyan_Program1 program1 = new Isajanyan_Program1();
		program1.start();
	}
}