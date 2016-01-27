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
public class TodoDelete extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		System.out.println("coming inside tododelte function");
		resp.setContentType("application/json");
		PersistenceManager pmf=PMF.get().getPersistenceManager();
		String getJson=req.getParameter("message");
		ObjectMapper objectmapper=new ObjectMapper();
		ObjectWriter objectwriter=new ObjectMapper().writer().withDefaultPrettyPrinter();
		System.out.println("read the json values");
		DeleteType todotype=objectmapper.readValue(getJson,DeleteType.class);
		 String todoDelete=todotype.getTodoDelete();
		 System.out.println("todo tobe deleted is "+ todoDelete);
			String username="vineeth";
		 try{
			 System.out.println("coming inside try block of tododelete");
			 TodoType todotypedelete=pmf.getObjectById(TodoType.class,username);
			 System.out.println("got jdo object");
			 String tobemap=todotypedelete.getTodoEditedString();

			 if(tobemap != null){
			 System.out.println(tobemap);
			 HashMap<String,String> map = new HashMap<String,String>();
	    		map=objectmapper.readValue(tobemap, new TypeReference<HashMap<String,String>>(){}); 
	    		for(String key:map.keySet()){
					if(key.equals(todoDelete)){
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
				 if(current.equals(todoDelete)){
					 it.remove();
					 break;
				 }
				 count++; 
			 }
			 todoalldate.remove(count);
			 todotypedelete.decNoOftasks();
	 System.out.println("coming out of arraylist iterator");
			todotypedelete.setTodoAll(todoalldelete);
			todotypedelete.setTodoDate(todoalldate);
			
			JSONObject jsonobject=new JSONObject(todotypedelete.getTodoEdited());
			todotypedelete.setTodoEditedString(jsonobject.toString());
			System.out.println("gonna make persistent");
			pmf.makePersistent(todotypedelete);
			System.out.println("coming out of map iterator");
			 String json1=objectwriter.writeValueAsString(todotypedelete);
			 System.out.println(json1);
			System.out.println("try block is executed succesfully"); 
			String json2=todotypedelete.getTodoEditedString();
			String json="["+json1+","+json2+"]";  
			resp.getWriter().print(json);
		 }
		 catch(JDOObjectNotFoundException e){ 
			 System.out.println("coming inside catch block"); 
		 }
		 catch(NullPointerException e){
		 }
		 finally{
			 pmf.close();
		 } 
	} 
} 