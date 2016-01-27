package com.todo;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.jdo.annotations.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@PersistenceCapable
@JsonPropertyOrder({"todos","date","username"})
public class TodoType {
	@PrimaryKey
	@Persistent
	@JsonProperty("username")
	private String username;
	@Persistent
	private ArrayList<String> todoAll=new ArrayList<String>();
	@Persistent
	private ArrayList<Date> todoDate=new ArrayList<Date>();
	@Persistent
	private String todoEditedString;
	@NotPersistent
	private HashMap<String,String> todoEdited=new HashMap<String,String>();
	@NotPersistent
	private HashMap<String,String> completedDate=new HashMap<String,String>(); 
	@Persistent
	@JsonProperty("len")
	private int noOfTasks=0;
	@Persistent
	ArrayList<String> todoCompleted=new ArrayList<String>();
	@Persistent
	ArrayList<Date> todoCompletedDate=new ArrayList<Date>();
	@Persistent
	ArrayList<Date> createdDate=new ArrayList<Date>();
    @NotPersistent
	@JsonProperty("todos") 
	private String todo;
    @NotPersistent
	@JsonProperty("date")
	private Date task_date;
	@JsonProperty("todos")
	public void setTodo(String todo){
		this.todo=todo;
	}
	@JsonProperty("date")
	public void setDate(Date date){
		this.task_date=date;
	}
	@JsonProperty("todos")
	public String getTodo(){
		return this.todo;
	}
	@JsonProperty("date")
	public Date getDate(){
		return this.task_date;
	}
	@JsonProperty("username")
	public void setUsername(String username){
		this.username=username;
	}
	@JsonProperty("username")
	public String getUsername(){
		return this.username;
	}
	public void setTodoAll(ArrayList<String> todoAll){
		this.todoAll=todoAll;
	}
	public  ArrayList<String> getTodoAll(){
		return this.todoAll;
	}
	public void setTodoDate(ArrayList<Date> todoDate){
		this.todoDate=todoDate;
	}
	
	public ArrayList<Date> getTodoDate(){
		return this.todoDate;
	}
	public void setNoOfTasks(){
	 this.noOfTasks=this.noOfTasks+1;
	}
	public int getNoOfTasks(){
		return this.noOfTasks;
	}
	public void decNoOftasks(){
		this.noOfTasks=this.noOfTasks-1;
	}
	
	public void setTodoEdited(HashMap<String, String> map){
		this.todoEdited=map;
	}
	public HashMap<String,String> getTodoEdited(){
		return this.todoEdited;
	}
	
	public String getTodoEditedString(){
		return this.todoEditedString;
	}
	public void setTodoEditedString(String todoEditedString){
		this.todoEditedString=todoEditedString;
	}

	public void setTodoCompleted(ArrayList<String> todoCompleted){
		this.todoCompleted=todoCompleted;
	}
	public ArrayList<String> getTodoCompleted(){
		return this.todoCompleted;
	}
	public void setTodoCompletedDate(ArrayList<Date> todoCompletedDate){
		this.todoCompletedDate=todoCompletedDate;
	}
	public ArrayList<Date> getTodoCompletedDate(){
		return this.todoCompletedDate;
	}
	
	public void setCreatedDate(ArrayList<Date> createdDate){
		this.createdDate=createdDate;
	}
	
	  public ArrayList<Date> getCreatedDate(){
		return this.createdDate;
	}
}
