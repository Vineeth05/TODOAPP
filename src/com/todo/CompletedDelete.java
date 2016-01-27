package com.todo;
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

import org.datanucleus.exceptions.NucleusObjectNotFoundException;
@SuppressWarnings("serial")
public class CompletedDelete extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		System.out.println("coming inside completed delete function");
		resp.setContentType("application/json");
		String getJson=req.getParameter("message");
		System.out.println("the getjson value is"+getJson);
		PersistenceManager pmf=PMF.get().getPersistenceManager();
		ObjectMapper objectmapper=new ObjectMapper();
		ObjectWriter objectwriter=new ObjectMapper().writer().withDefaultPrettyPrinter();
		  DeleteType tododelete=objectmapper.readValue(getJson,DeleteType.class);
		  String todelete=tododelete.getTodoDelete();
		  System.out.println(tododelete);
		  try{
			  System.out.println("coming inside try block of completed delete function");
			  TodoType todoUpdate=pmf.getObjectById(TodoType.class,"vineeth");
			  ArrayList<String> todoCompleted=todoUpdate.getTodoCompleted();
			  ArrayList<Date> todoCompletedDate=todoUpdate.getTodoCompletedDate();
			  ArrayList<Date> todoCreatedDate=todoUpdate.getCreatedDate();
			  Iterator it=todoCompleted.iterator();
			  int count=0;
			  while(it.hasNext()){
				  String curr=(String) it.next();
				  if(curr.equals(todelete)){
					  it.remove();
					  break;
				  }
				  count++;  
			  }
			  System.out.println("came out of the while block succesfully"+count);
			  todoCompletedDate.remove(count);  
			  todoCreatedDate.remove(count);
			  todoUpdate.setCreatedDate(todoCreatedDate);
			  todoUpdate.setTodoCompletedDate(todoCompletedDate);
			  todoUpdate.setTodoCompleted(todoCompleted);
			  System.out.println("assing all the updated values is completed");
			  pmf.makePersistent(todoUpdate);
			  System.out.println("persisted the updated value successfully");
			  String json1=objectwriter.writeValueAsString(todoUpdate);
			  System.out.println(json1);
			  String json2=todoUpdate.getTodoEditedString();
			  String json="["+json1+","+json2+"]";
			  System.out.println("the json value back to ajax is"+json);
			  resp.getWriter().println(json); 
		  }
		  catch(JDOObjectNotFoundException e){
			  
		  }
		  finally{
			  pmf.close();
		  } 
		  
	}
}
	