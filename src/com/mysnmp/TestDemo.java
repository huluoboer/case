package com.mysnmp;

import java.util.Timer;

public class TestDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Timer timer = new Timer();
        timer.schedule(new TestMy(), 1000, 60000);
	}

}
