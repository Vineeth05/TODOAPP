package com.todo;
import javax.jdo.annotations.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonPropertyOrder({"todoDelete"})
public class DeleteType {
	@JsonProperty("todoDelete")
	private String todoDelete;
	
	@JsonProperty("todoDelete")
	public void setTodoDelete(String todoDelete){
		this.todoDelete=todoDelete;
	}
  @JsonProperty("todoDelete")
  public String getTodoDelete(){
	  return this.todoDelete;
  }
}
