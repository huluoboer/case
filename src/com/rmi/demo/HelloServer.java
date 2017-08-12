package com.rmi.demo;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class HelloServer {
        public static void main(String args[]) {
        	try {
				IHello hello = new  HelloImpl();
				 LocateRegistry.createRegistry(8888);  
				 Naming.bind("rmi://127.0.0.1:8888/RHello", hello);
				 System.out.println("远程绑定成功");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AlreadyBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
}
