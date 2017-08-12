package com.callback.demo;

public class Hotel {
          //提供的服务  需要服务对象  以及服务内容
	           public   void   recordAndWake(Callback callback,String  date) {
	        	   System.out.println("hotel记录时间为====="+date);
	        	   //模拟长夜慢慢
	        	   try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	   

	        	       date="时间到了。。。。客人";
	        	      callback.callback(date);
	           }
}
