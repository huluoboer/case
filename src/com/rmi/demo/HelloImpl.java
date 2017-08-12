package com.rmi.demo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloImpl  extends UnicastRemoteObject implements IHello {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HelloImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String sayHello(String name) {
		// TODO Auto-generated method stub
		return "hello";
	}

}
