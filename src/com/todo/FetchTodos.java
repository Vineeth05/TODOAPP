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
public class FetchTodos extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		System.out.println("Coming inside fetch todos function");
		resp.setContentType("application/json");
   PersistenceManager pmf=PMF.get().getPersistenceManager();
	  ObjectWriter objectwriter=new ObjectMapper().writer().withDefaultPrettyPrinter();
   try{
	   System.out.println("coming inside try block of fetch function");
	   String username=req.getParameter("username");
	   System.out.println(username);
	   TodoType todofetch=pmf.getObjectById(TodoType.class,username);
	   System.out.println("feteched row now gonna map it to json");
	   String json1=objectwriter.writeValueAsString(todofetch);
	   String json2=todofetch.getTodoEditedString();
	   System.out.println("mapping is successfull");
	   String json="["+json1+","+json2+"]"; 
	   System.out.println(json);
	   resp.getWriter().println(json);
   }
   catch(JDOObjectNotFoundException e){
	   resp.getWriter().println("{}");
   }
}
}