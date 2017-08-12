package com.rmi.demo;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class HelloClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
                 try {
				IHello  hello=(IHello) Naming.lookup("rmi://127.0.0.1:8888/RHello");
			  System.out.println(hello.sayHello("test")+"00000");  
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

}
