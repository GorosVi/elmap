package com.dev.api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Transaction;



//@WebServlet("/LoginServlet")
public class Tree extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}
	public static DatastoreService ds=DatastoreServiceFactory.getDatastoreService();
	
public static boolean createTree(String id, String latitude, String longitude, String type, String radius, String status, String power) {
		Entity tree = new Entity ("Tree");
		//tree.setProperty("id", id);
		tree.setProperty("latitude", latitude);
		tree.setProperty("longitude", longitude);
		tree.setProperty("type", type);
		tree.setProperty("radius", radius);
		tree.setProperty("status", status);
		tree.setProperty("power", power);
		ds.put(tree);
		System.out.println("ID: "+tree.getKey()+" Question: "+tree.getKind());
		return true;
	}

}