package mindstormer;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.Motor;
import lejos.utility.Delay;

public class Main {

    public static void main(String[] args) {
	GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();
	int SW = g.getWidth();
	int SH = g.getHeight();
	Button.LEDPattern(4);
	Sound.beepSequenceUp();
	g.setFont(Font.getDefaultFont());
	g.drawString("Lejos EV3 - Prototype", SW / 2, SH / 2, GraphicsLCD.BASELINE | GraphicsLCD.HCENTER);
	g.refresh();
	Delay.msDelay(2500);

	Sound.beepSequence();
	Motor.A.setSpeed(720);// 2 RPM
	Motor.A.forward();
	try {
	    Thread.sleep(1000);
	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	Motor.A.stop();
    }
}
