package com.todo;
import java.util.Date;

import javax.jdo.annotations.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonPropertyOrder({"todoDone","date"})
public class DoneType {
	@JsonProperty("todoDone")
	private String todoDone;
	@JsonProperty("date")
	private Date dateCompleted; 
	
	@JsonProperty("todoDone")
	public void setTodoDone(String todoDone){
		this.todoDone=todoDone;
	}
  @JsonProperty("todoDone")
  public String getTodoDone(){
	  return this.todoDone;
  }
  
  @JsonProperty("date")
  public void setCompletedDate(Date completedDate){
	  this.dateCompleted=completedDate;
  }
  @JsonProperty("date")
  public Date getCompletedDate(){
	  return this.dateCompleted;
  }
  
}

