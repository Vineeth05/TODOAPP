package com.todo;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.jdo.annotations.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonPropertyOrder({"curr",
	"prev",
	"username",
	"date"})
public class TodoEditFormat {
 @JsonProperty("prev")
 private String previousTodo;
 @JsonProperty("curr")
 private String currTodo;
 @JsonProperty("username")
 private String username;
 @JsonProperty("date")
 private Date editedDate;
 @JsonProperty("prev")
 public String getPrevious(){
	 return this.previousTodo;
 }
 @JsonProperty("curr")
 public String getCurrent(){
	 return this.currTodo;
 }
 @JsonProperty("username")
 public String getUsername(){
	 return this.username;
 }
 @JsonProperty("date")
 public Date getEditedDate(){
	 return this.editedDate;
 }
}
