package com.todo;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.jdo.Query;
import javax.jdo.PersistenceManager;
import javax.servlet.http.*; 

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import javax.jdo.JDOObjectNotFoundException;

public class TodoDone extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		System.out.println("coming inside tododone function");
		resp.setContentType("application/json");
		PersistenceManager pmf=PMF.get().getPersistenceManager();
		ObjectMapper objectmapper=new ObjectMapper();
		ObjectWriter objectwriter=new ObjectMapper().writer().withDefaultPrettyPrinter();
		   String getJson=req.getParameter("message");
		   System.out.println("mapping json value which is equal to "+getJson); 
		   DoneType donetype=objectmapper.readValue(getJson,DoneType.class);
		   String tododone=donetype.getTodoDone();
		   Date completedDate=donetype.getCompletedDate();
		   System.out.println("tododone string is"+tododone+" "+completedDate);
		   
		   try{
			    System.out.println("coming inside try block of tododone");
			   TodoType todotypedelete=pmf.getObjectById(TodoType.class,"vineeth"); //fetching object from the datastore
			     System.out.println("got jdo object");
			     Date createdDate;
			     String tobemap=todotypedelete.getTodoEditedString();
			    ArrayList<String> todoCompleted=todotypedelete.getTodoCompleted();
			    ArrayList<Date> todoCompletedDate=todotypedelete.getTodoCompletedDate();
			    //adding present task to the completed list
			    todoCompleted.add(tododone);
			    todotypedelete.setTodoCompleted(todoCompleted);
			    
			    //adding the completed taks date to the commpleted list
			    todoCompletedDate.add(completedDate);
			    todotypedelete.setTodoCompletedDate(todoCompletedDate);
			    
			   
			    if(tobemap != null){
					 System.out.println(tobemap);
			HashMap<String,String> map = new HashMap<String,String>();
			  map=objectmapper.readValue(tobemap, new TypeReference<HashMap<String,String>>(){}); 
			    		for(String key:map.keySet()){
							if(key.equals(tododone)){
								map.remove(key);  
								break;  
							} 
						}
			    		todotypedelete.setTodoEdited(map);
			    		}
					 ArrayList<String> todoalldelete=todotypedelete.getTodoAll();
					 ArrayList<Date> todoalldate=todotypedelete.getTodoDate();
					 int count=0;
					 Iterator it=todoalldelete.iterator();
					 while(it.hasNext()){
						 String current=(String)it.next();
						 if(current.equals(tododone)){
							 it.remove();
							 break;
						 }
						 count++; 
					 }
					 createdDate=todoalldate.get(count);
					 ArrayList<Date> cd=todotypedelete.getCreatedDate();
					 cd.add(createdDate);
					 todotypedelete.setCreatedDate(cd); 
					 todoalldate.remove(count);
					 todotypedelete.decNoOftasks(); 
			 System.out.println("coming out of arraylist iterator");
					todotypedelete.setTodoAll(todoalldelete);
					todotypedelete.setTodoDate(todoalldate);
					
					JSONObject jsonobject=new JSONObject(todotypedelete.getTodoEdited());
					todotypedelete.setTodoEditedString(jsonobject.toString());
					System.out.println("gonna make persistent");
					pmf.makePersistent(todotypedelete); 
					System.out.println("made persistent");
					String json1=objectwriter.writeValueAsString(todotypedelete);
					String json2=todotypedelete.getTodoEditedString();
					String json="["+json1+","+json2+"]";
					resp.getWriter().println(json); 
					
		   }
		   catch(JDOObjectNotFoundException e){
			    
		   }
	}

}
