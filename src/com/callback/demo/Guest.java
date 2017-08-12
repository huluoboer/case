package com.callback.demo;

public class Guest implements Callback {
           Hotel hotel;
           public Guest(Hotel hotel) {
        	   this.hotel=hotel;
           }
           //客人的告诉宾馆叫醒服务（时间）
           public  void  awake(String  date ) {
        	   System.out.println("走到前台。。。要求服务");
        	   //宾馆登记
        	   new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					hotel.recordAndWake(Guest.this, date);
				}
        		   
        	   }) {
        		
        	   }.start();
        	   //等待服务办理
        	   System.out.println("等待服务办理");
        	   try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	   doOtherThing();
        	   
           }
           //长夜慢慢  无心睡眠
           void  doOtherThing() {
        	   System.out.println("吃吃宵夜。。。。。。");
        	   System.out.println("睡觉了。。。。。。等待叫醒");
           }
           
	@Override
	public void callback(String result) {
		// TODO Auto-generated method stub
		System.out.println("hotel=============叫醒服务"+result);
	}

}
