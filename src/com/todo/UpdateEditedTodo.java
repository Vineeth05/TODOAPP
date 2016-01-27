package com.todo;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
public class UpdateEditedTodo extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException { 
		   resp.setContentType("applicaton/json");
              System.out.println("function coming inside updateeditedtodo function");
              PersistenceManager pmf=PMF.get().getPersistenceManager();
              ObjectMapper objectmapper=new ObjectMapper();
              String json=req.getParameter("edited");
              System.out.println(json);
              TodoEditFormat todoeditformat=objectmapper.readValue(json, TodoEditFormat.class);
              String username=todoeditformat.getUsername();
              String oldTodo=todoeditformat.getPrevious();
              String newTodo=todoeditformat.getCurrent();
              Date editedDate=todoeditformat.getEditedDate();               
              System.out.println(editedDate); 
              ObjectWriter objectwriter=new ObjectMapper().writer().withDefaultPrettyPrinter();
              try{
            	  System.out.println("coming inside try block of edit function");
              TodoType todoEditUpdate=pmf.getObjectById(TodoType.class,username);
              ArrayList<String> todoArrayUpdate=todoEditUpdate.getTodoAll();
            for(int i=0;i<todoArrayUpdate.size();i++){
            	System.out.println("coming inside for loop of edit function");
            	String check=todoArrayUpdate.get(i);
            	if(check.equals(oldTodo)){ 
        			HashMap<String,String> map = new HashMap<String,String>();
            		todoArrayUpdate.set(i,newTodo);
            		String tobeMap=todoEditUpdate.getTodoEditedString();
            		if(tobeMap!=null){
            		System.out.println("tobemap string value is"+tobeMap);
            		map=objectmapper.readValue(tobeMap, new TypeReference<HashMap<String,String>>(){});
            		 map.put(newTodo, editedDate.toString());
            		 System.out.println("value of map after setting is"+map);
            		todoEditUpdate.setTodoEdited(map);
            		System.out.println("edited map value is "+todoEditUpdate.getTodoEdited());
            		}
            		else{
            			
            		     System.out.println("entering map else block");
            			todoEditUpdate.getTodoEdited().put(newTodo,editedDate.toString());
            			System.out.println(todoEditUpdate.getTodoEdited());
            		}
                    JSONObject jsonobject=new JSONObject(todoEditUpdate.getTodoEdited());
                    todoEditUpdate.setTodoEditedString(jsonobject.toString());
            		break; 
            		} //if block       
            } // for block
            System.out.println("edited things are made persistent successfully");
            pmf.makePersistent(todoEditUpdate);
            String json1=objectwriter.writeValueAsString(todoEditUpdate);
            String json2=todoEditUpdate.getTodoEditedString();
            String jsonout="["+json1+","+json2+"]";
            System.out.println(jsonout);
            resp.getWriter().println(jsonout);
           
	} //try block
              catch(JDOObjectNotFoundException e){
            	  
              }
              catch(NullPointerException ne){
            	  
              }
              finally{
            	  pmf.close();
              }
}
}