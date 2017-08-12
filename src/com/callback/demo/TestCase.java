package com.callback.demo;

public class TestCase {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
                    Hotel  hotel = new Hotel();
                    Guest  guest = new Guest(hotel);
                    guest.awake("明天早上6.00");            
	}
}
