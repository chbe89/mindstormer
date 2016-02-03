package edu.kit.mindstormer.util;

public class TimePadTest {

	public static void main(String[] args) throws InterruptedException {

		TimePad tp = new TimePad(100);
		for (int i = 0; i < 5; i++) {
			Thread.sleep(50);
			System.out.println("request granted: " + tp.requestTime());
			System.out.println("---");
		}
	}
}
