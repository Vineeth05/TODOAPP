$(document).ready(function(){
	todoLoad();
	$("#taskadd").click(function(){
		console.log("add button is clicked");
  getTodo(); 
  alert("successfull"); 
});
 $(document).on('click','#todosPending .todo-btn-edit',function(){
	 var fields={}
	 var temp=$(this).parent().find('.todo-val-field').text();
	 console.log(temp);
	 alert(temp);
	 fields.prev=temp;
	 fields.id=$(this).parent();
	 $(this).parent().find(".todo-val-field").html('<input type="text" class="todo-edit-val"></input>');
	$('.todo-edit-val').attr('value',temp).focus();
	$(document).keypress(function(e){
		console.log("coming inside keypress");
		if(e.which ==13){
			console.log("coming inside keypress function");
			var updatedTodo=$('.todo-edit-val').val();
			console.log(updatedTodo);
			$(fields.id).find('.todo-val-field').text(updatedTodo);
			fields.curr=$(fields.id).find('.todo-val-field').text();
			$.ajax({
				url:'todoedited', 
				type:'POST',
				dataType:'json',
				async:false,
				data:{edited:JSON.stringify({"prev":fields.prev,"curr":fields.curr,"username":"vineeth","date":new Date()})},
				success:function(json){
					alert("editing is succesfull");
					$('#todosPending').empty();
					for(i=0;i<json[0].len;i++){
						var date=moment(json[0].todoDate[i]); 
						var formatted =date.format('MMMM Do YYYY, h:mm:ss a');
						var presentTodo=json[0].todoAll[i]; 
						var editedTime='';
						/*var dt=moment(json[1].presentTodo);
						var editedTimeValue=dt.format('MMMM Do YYYY,h:mm:ss a');*/
						if(json[1]!==null){
							console.log("present todo is "+presentTodo+" and its date is "+json[1][presentTodo]); 
							if(json[1][presentTodo] ===undefined || json[1][presentTodo]===null){
								console.log("coming inside editeddate if block");
							   	editedTime=" "; 
							}
							else{
								console.log("coming inside else block");

								var	editedTimeValue=json[1][presentTodo]; 
					                   editedTimeValue=editedTimeValue.replace("IST","");
					                   var dt=moment(new Date(editedTimeValue)).format('MMMM Do YYYY, h:mm:ss a');
					                   var editedTime=dt;
							}
						}
						$("#todosPending").append('<li class="list-group-item"><span class="todo-val-field">'+json[0].todoAll[i]+'</span><span class="timestamp pull-right">'+formatted+'</span><br><button class="btn btn-primary btn-info text-center todo-btn-done  glyphicon glyphicon-ok"></button><button class="btn btn-primary btn-info text-center todo-btn-delete  glyphicon glyphicon-remove"></button><button class="btn btn-primary text-center btn-info todo-btn-edit  glyphicon glyphicon-pencil"></button><span class="timestamp pull-right" style="color:##808080 !important;"><b>'+editedTime+'</b></span></li>');
				}
		}
			});
		}
	}); // keyPress function closes here
 });// edit function closes here
 $(document).on('click','#todosPending .todo-btn-delete',function(){
	 var fields={}
	 var temp=$(this).parent().find('.todo-val-field').text();
       $.ajax({
    	   url:'/tododelete',   
    	   type:'post',
    	   dataType:'json',
    	   data:{message:JSON.stringify({"todoDelete":temp})},
    	   success:function(json){
    		   alert("success");  
    		   $('#todosPending').empty();
    		   for(i=0;i<json[0].len;i++){
					var date=moment(json[0].todoDate[i]);
					var formatted =date.format('MMMM Do YYYY, h:mm:ss a');
					var presentTodo=json[0].todoAll[i]; 
					var editedTime='';
					/*var dt=moment(json[1].presentTodo);
					var editedTimeValue=dt.format('MMMM Do YYYY,h:mm:ss a');*/
					if(json[1]!==null){
						console.log("present todo is "+presentTodo+" and its date is "+json[1][presentTodo]); 
						if(json[1][presentTodo] ===undefined || json[1][presentTodo]===null){
							console.log("coming inside editeddate if block");
						   	editedTime=" "; 
						}
						else{
							console.log("coming inside else block"); 

							var	editedTimeValue=json[1][presentTodo]; 
				                   editedTimeValue=editedTimeValue.replace("IST","");
				                   var dt=moment(new Date(editedTimeValue)).format('MMMM Do YYYY, h:mm:ss a');
				                   var editedTime=dt;						}
					}
					$("#todosPending").append('<li class="list-group-item"><span class="todo-val-field">'+json[0].todoAll[i]+'</span><span class="timestamp pull-right">'+formatted+'</span><br><button class="btn btn-primary btn-info text-center todo-btn-done  glyphicon glyphicon-ok"></button><button class="btn btn-primary btn-info text-center todo-btn-delete  glyphicon glyphicon-remove"></button><button class="btn btn-primary text-center btn-info todo-btn-edit  glyphicon glyphicon-pencil"></button><span class="timestamp pull-right" style="color:##808080 !important;"><b>'+editedTime+'</b></span></li>');
			} 
    	   } 
       });
 });//delete function closes here
 $(document).on('click','#todosPending .todo-btn-done',function(){
	 var fields={}
	 var temp=$(this).parent().find('.todo-val-field').text();
	 console.log(temp); 
	 $.ajax({
		 url:'/tododone',
		 type:'post',
		 dataType:'json',
		 data:{message:JSON.stringify({"todoDone":temp,"date":new Date()})},
		 success:function(json){
			 alert("moving to completed is done");
			 console.log(json);
			   $('#todosPending').empty();
			   $('#todosCompleted').empty();
    		   for(i=0;i<json[0].len;i++){
					var date=moment(json[0].todoDate[i]);
					var formatted =date.format('MMMM Do YYYY, h:mm:ss a');
					var presentTodo=json[0].todoAll[i]; 
					var editedTime='';
					/*var dt=moment(json[1].presentTodo);
					var editedTimeValue=dt.format('MMMM Do YYYY,h:mm:ss a');*/
					if(json[1]!==null){
						console.log("present todo is "+presentTodo+" and its date is "+json[1][presentTodo]); 
						if(json[1][presentTodo] ===undefined || json[1][presentTodo]===null){
							console.log("coming inside editeddate if block");
						   	editedTime=" "; 
						}
						else{
							console.log("coming inside else block"); 

							var	editedTimeValue=json[1][presentTodo]; 
				                   editedTimeValue=editedTimeValue.replace("IST","");
				                   var dt=moment(new Date(editedTimeValue)).format('MMMM Do YYYY, h:mm:ss a');
				                   var editedTime=dt;
						}
					}
					$("#todosPending").append('<li class="list-group-item"><span class="todo-val-field">'+json[0].todoAll[i]+'</span><span class="timestamp pull-right">'+formatted+'</span><br><button class="btn btn-primary btn-info text-center todo-btn-done  glyphicon glyphicon-ok"></button><button class="btn btn-primary btn-info text-center todo-btn-delete  glyphicon glyphicon-remove"></button><button class="btn btn-primary text-center btn-info todo-btn-edit  glyphicon glyphicon-pencil"></button><span class="timestamp pull-right" style="color:##808080 !important;"><b>'+editedTime+'</b></span></li>');
		      	} 
    		   if(json[0]["todoCompleted"]!== null){
    		   for(i=0;i<json[0]["todoCompleted"].length;i++){
    			   var dateCompleted=moment(json[0]["todoCompletedDate"][i]);
					var dateCompletedFormatted =dateCompleted.format('MMMM Do YYYY, h:mm:ss a');
					var dateCreated=moment(json[0]["createdDate"][i]);
					var dateCreatedFormatted=dateCreated.format('MMMM Do YYYY, h:mm:ss a');
					var presentTodo=json[0].todoCompleted[i]; 
					$("#todosCompleted").append('<li class="list-group-item"><span class="todo-val-field">'+json[0].todoCompleted[i]+'</span><span class="timestamp pull-right">'+dateCreatedFormatted+'</span><br><button class="btn btn-primary btn-info text-center todo-btn-delete  glyphicon glyphicon-remove"></button><span class="timestamp pull-right" style="color:##808080 !important;"><b>'+dateCompletedFormatted+'</b></span></li>');
    		   }
    		   }
		 }
	 });  
 });//todo complete function
 
 $(document).on('click','#todosCompleted .todo-btn-delete',function(){
	 var fields={}
	 var temp=$(this).parent().find('.todo-val-field').text();
	 $.ajax({
		 url:'/completeddelete',
		 type:'post',
		 dataType:'json',
		 data:{message:JSON.stringify({"todoDelete":temp})},
		 async:false,
		 success:function(json){
			 console.log("COMING INSIDE SUCCESS CALL FUNCTION");
			   $('#todosCompleted').empty();
			   if(json[0]["todoCompleted"]!== null){
	    		   for(i=0;i<json[0]["todoCompleted"].length;i++){
	    			   var dateCompleted=moment(json[0]["todoCompletedDate"][i]);
						var dateCompletedFormatted =dateCompleted.format('MMMM Do YYYY, h:mm:ss a');
						var dateCreated=moment(json[0]["createdDate"][i]);
						var dateCreatedFormatted=dateCreated.format('MMMM Do YYYY, h:mm:ss a');
						var presentTodo=json[0].todoCompleted[i]; 
						$("#todosCompleted").append('<li class="list-group-item"><span class="todo-val-field">'+json[0].todoCompleted[i]+'</span><span class="timestamp pull-right">'+dateCreatedFormatted+'</span><br><button class="btn btn-primary btn-info text-center todo-btn-delete  glyphicon glyphicon-remove"></button><span class="timestamp pull-right" style="color:##808080 !important;"><b>'+dateCompletedFormatted+'</b></span></li>');
	    		   }
	    		   
	    		   }
		 }
	 });
 });//completed todo done click event 
	 
	 
});// document ready function closes here
function todoLoad(){
	$.getJSON('fetchTodos',{username:"vineeth"},function(json){    
		var d=new Date("Wed Jan 27 14:54:07  2016");
		console.log("dddddddddd"+ d); 
		var z=moment(new Date("Wed Jan 27 14:54:07  2016")).format('MMMM Do YYYY, h:mm:ss a');
		console.log("The moment value is"+ z); 
		/*var z=moment("Wed Jan 27 14:54:07  2016",'ddd MMM DD  HH:mm:ss YYYY');*/
		
		for(i=0;i<json[0].len;i++){ 
			var date=moment(json[0].todoDate[i]);  
			var formatted =date.format('MMMM Do YYYY, h:mm:ss a');
			var presentTodo=json[0].todoAll[i]; 
			var editedTime='';
			/*var dt=moment(json[1].presentTodo);
			var editedTimeValue=dt.format('MMMM Do YYYY,h:mm:ss a');*/
			if(json[1]!==null){
				console.log("present todo is "+presentTodo+" and its date is "+json[1][presentTodo]); 
				if(json[1][presentTodo] ===undefined || json[1][presentTodo]===null){
					console.log("coming inside editeddate if block");
				   	editedTime=" "; 
				}
				else{
					console.log("coming inside else block"); 
		 
				var	editedTimeValue=json[1][presentTodo]; 
	                   editedTimeValue=editedTimeValue.replace("IST","");
	                   var dt=moment(new Date(editedTimeValue)).format('MMMM Do YYYY, h:mm:ss a');
	                   var editedTime=dt;
					/*var editedTime=dt.format('MMMM Do YYYY,h:mm:ss a'); 
					console.log("the new value after the change is "+editedTime); */
				} 
			} 
			$("#todosPending").append('<li class="list-group-item"><span class="todo-val-field">'+json[0].todoAll[i]+'</span><span class="timestamp pull-right">'+formatted+'</span><br><button class="btn btn-primary btn-info text-center todo-btn-done  glyphicon glyphicon-ok"></button><button class="btn btn-primary btn-info text-center todo-btn-delete  glyphicon glyphicon-remove"></button><button class="btn btn-primary text-center btn-info todo-btn-edit  glyphicon glyphicon-pencil"></button><span class="timestamp pull-right" style="color:##808080 !important;"><b>'+editedTime+'</b></span></li>');
	}
		console.log(json[0]["todoCompleted"]);
		if(json[0]["todoCompleted"]!== null){
 		   for(i=0;i<json[0]["todoCompleted"].length;i++){
 			   var dateCompleted=moment(json[0]["todoCompletedDate"][i]);
					var dateCompletedFormatted =dateCompleted.format('MMMM Do YYYY, h:mm:ss a');
					var dateCreated=moment(json[0]["createdDate"][i]);
					var dateCreatedFormatted=dateCreated.format('MMMM Do YYYY, h:mm:ss a');
					var presentTodo=json[0].todoCompleted[i]; 
					$("#todosCompleted").append('<li class="list-group-item"><span class="todo-val-field">'+json[0].todoCompleted[i]+'</span><span class="timestamp pull-right">'+dateCreatedFormatted+'</span><br><button class="btn btn-primary btn-info text-center todo-btn-delete  glyphicon glyphicon-remove"></button><span class="timestamp pull-right" style="color:##808080 !important;"><b>'+dateCompletedFormatted+'</b></span></li>');
 		   }
 		   }
		
	});
}
function getTodo(){
	var todo=$("#todoInput").val();
	if(todo!==""){ 
	console.log(todo);
	resetall();
	$.ajax({
			type:"POST",
			url:"/todolist",
			dataType:"json",
			data:{message:JSON.stringify({"todos":todo,"date":new Date(),"username":"vineeth"})},
			success:function(json){
				for(i=0;i<json[0].len;i++){
					var date=moment(json[0].todoDate[i]);
					var formatted =date.format('MMMM Do YYYY, h:mm:ss a');
					var presentTodo=json[0].todoAll[i]; 
					var editedTime='';
					/*var dt=moment(json[1].presentTodo);
					var editedTimeValue=dt.format('MMMM Do YYYY,h:mm:ss a');*/
					if(json[1]!==null){
						console.log("present todo is "+presentTodo+" and its date is "+json[1][presentTodo]); 
						if(json[1][presentTodo] ===undefined || json[1][presentTodo]===null){
							console.log("coming inside editeddate if block");
						   	editedTime=" "; 
						}
						else{
							console.log("coming inside else block");

							var	editedTimeValue=json[1][presentTodo]; 
				                   editedTimeValue=editedTimeValue.replace("IST","");
				                   var dt=moment(new Date(editedTimeValue)).format('MMMM Do YYYY, h:mm:ss a');
				                   var editedTime=dt;
						}
					}
					$("#todosPending").append('<li class="list-group-item"><span class="todo-val-field">'+json[0].todoAll[i]+'</span><span class="timestamp pull-right">'+formatted+'</span><br><button class="btn btn-primary btn-info text-center todo-btn-done  glyphicon glyphicon-ok"></button><button class="btn btn-primary btn-info text-center todo-btn-delete  glyphicon glyphicon-remove"></button><button class="btn btn-primary text-center btn-info todo-btn-edit  glyphicon glyphicon-pencil"></button><span class="timestamp pull-right" style="color:##808080 !important;"><b>'+editedTime+'</b></span></li>');
			} 
	    		   
				if(json[0]["todoCompleted"]!== null){
		    		   for(i=0;i<json[0]["todoCompleted"].length;i++){
		    			   var dateCompleted=moment(json[0]["todoCompletedDate"][i]);
							var dateCompletedFormatted =dateCompleted.format('MMMM Do YYYY, h:mm:ss a');
							var dateCreated=moment(json[0]["createdDate"][i]);
							var dateCreatedFormatted=dateCreated.format('MMMM Do YYYY, h:mm:ss a');
							var presentTodo=json[0].todoCompleted[i]; 
							$("#todosCompleted").append('<li class="list-group-item"><span class="todo-val-field">'+json[0].todoCompleted[i]+'</span><span class="timestamp pull-right">'+dateCreatedFormatted+'</span><br><button class="btn btn-primary btn-info text-center todo-btn-delete  glyphicon glyphicon-remove"></button><span class="timestamp pull-right" style="color:##808080 !important;"><b>'+dateCompletedFormatted+'</b></span></li>');
		    		   }
		    		   }

			}
		});
	}
	else
		{
		alert("Please add a task");
}
}

function resetall(){
	$("#todosPending").empty();
	$('#todosCompleted').empty();
	$("#todoInput").val("");
	$("#todoInput").focus();
	}