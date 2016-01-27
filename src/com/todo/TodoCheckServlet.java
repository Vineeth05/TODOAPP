package com.todo;
import java.io.IOException;
import java.util.ArrayList;

import javax.jdo.Query;
import javax.jdo.PersistenceManager;
import javax.servlet.http.*; 

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import javax.jdo.JDOObjectNotFoundException;

import org.datanucleus.exceptions.NucleusObjectNotFoundException;
@SuppressWarnings("serial")
public class TodoCheckServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		System.out.println("Function comes inside todolist servlet");
		PersistenceManager pmf=PMF.get().getPersistenceManager();
		resp.setContentType("application/json");
		String jsonGet=req.getParameter("message");
		System.out.println(jsonGet);
		ObjectMapper objectmapper=new ObjectMapper();
		TodoType todo=objectmapper.readValue(jsonGet,TodoType.class);
		System.out.println("json value are setted");
		String username=todo.getUsername();
		ObjectWriter objectwriter=new ObjectMapper().writer().withDefaultPrettyPrinter();
	  try{
		TodoType todoUpdate=pmf.getObjectById(TodoType.class,username);
	    todoUpdate.setTodo(todo.getTodo());
	    todoUpdate.getTodoAll().add(todo.getTodo());
	    todoUpdate.getTodoDate().add(todo.getDate());
	    todoUpdate.setNoOfTasks();
	    System.out.println("the date that is gonna set is:"+todo.getDate()); 
	    pmf.makePersistent(todoUpdate);
		  String json1 = objectwriter.writeValueAsString(todoUpdate);
		  String json2=todoUpdate.getTodoEditedString();
		  String json="["+json1+","+json2+"]";
		  resp.getWriter().print(json);
		  System.out.println(json); 
	  }
	  catch(JDOObjectNotFoundException e){
		 System.out.println("coming inside jdoobjectnotfoundexception catch block");
		 todo.getTodoAll().add(todo.getTodo());
		 todo.getTodoDate().add(todo.getDate());
		 todo.setNoOfTasks();
		  pmf.makePersistent(todo);
		  String json1 = objectwriter.writeValueAsString(todo);
		  String json2 = todo.getTodoEditedString();
		  String json="["+json1+","+json2+"]";
		  resp.getWriter().print(json); 
		  System.out.println(json);
	  }
	  finally{
		  System.out.println("coming inside finally block ");
		  pmf.close();   
		  System.out.flush(); 
	  }
		}	
	}     