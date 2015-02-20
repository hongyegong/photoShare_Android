package com.myou.appback.test;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

/***
 * 
 * @author Steven
 * http://www.lifeba.org
 */
public class Client {
	public static void main(String[] args){
		Client client = new Client();
		client.student_post();
	}
	
	public void student_post(){
		try {
			Form queryForm = new Form();
			queryForm.add("name","酒继亚");
			queryForm.add("password","201002");
			ClientResource client = new ClientResource("http://localhost:8088/app/login");
			Representation representation =client.post(queryForm.getWebRepresentation());  
			System.out.println(representation.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}

	
	
	
  }
	
	
}
