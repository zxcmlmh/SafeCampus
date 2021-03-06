//获取知识点列表
var knowledgelist;
		$.ajax({  
	          type:"POST", 
	          url:"GetKnowledge", 
	          dataType:"json", 
	          async : false,
   			  success:function(data){   //function1()
   				knowledgelist=data;
   				for(i=0;i<data.length;i++)
				{
					//count++;
					$("#searchknowledge").append("<option value='"+data[i].id+"'>"+data[i].text+"</option>");
				}
	        }
	  	});
		var knowledgeid=$("#searchknowledge").val();
		var questiontype=$("#searchtype").val();
		var pagecount=$("#pagecount").val();
		var pages=1;
		var allpages=0;
		var addid=-1;
		//加载题目列表
		function loadquestions()
		{
			$("#questionlist").empty();
			$.post("GetQuestions",{
				knowledgeid:knowledgeid,
				questiontype:questiontype,
				pagecount:pagecount,
				pages:pages
				},
				function(data){
					var html="";
					var opt="";
					var counts=data.counts;
					if(counts%pagecount==0)
						counts--;
					allpages=(parseInt(counts/pagecount)+1);
					$("#currentpageNpages").text("第"+pages+"/"+allpages+"页"); // 只支持修改文本
					var questions=data.questions;
					for(i=0;i<questions.length;i++)
					{
						var question = document.createElement('fieldset'); //创建元素
						question.id=questions[i].id;
						var id=questions[i].id;
						var text=questions[i].text;
						var choices=questions[i].choices;
						var type=questions[i].type;
						var knowledgeid=questions[i].knowledgeid;
						var knowledgetext="";
						for(j=0;j<knowledgelist.length;j++)
						{
							if(knowledgeid==knowledgelist[j].id)
								knowledgetext=knowledgelist[j].text;
						}
						var answer=questions[i].answer;
						var html="<div class='row'><section class='col col-md-2'><label class='label'>知识点:</label><div class='col-md-6'><select class='form-control' id='KnowSelect"+id+"'><option value='"+knowledgeid+"'>"+knowledgetext+"</option></select></div></section>";
						if(type=="check")
							{
							html=html+"<section class='col col-md-2'><label class='label'>题型:</label><div class='col-md-6'><select class='form-control typeselect'><option value='single'>单选</option><option value='multy'>多选</option><option value='check' selected='selected'>判断</option></select></div></section></div>";
							}
						else
							if(type=="single")
								{
									html=html+"<section class='col col-md-2'><label class='label'>题型:</label><div class='col-md-6'><select class='form-control typeselect'><option value='single'  selected='selected'>单选</option><option value='multy'>多选</option><option value='check'>判断</option></select></div></section></div>";
								}
							else
								html=html+"<section class='col col-md-2'><label class='label'>题型:</label><div class='col-md-6'><select class='form-control typeselect'><option value='single'>单选</option><option value='multy'  selected='selected'>多选</option><option value='check'>判断</option></select></div></section></div>";
						html=html+"<section><label class='label'>题干：</label><label class='textarea'> <i class='icon-append fa fa-comment'></i><textarea rows='4' name='comment'>"+text+"</textarea></label></section>";
						if(type=="check")
							{
								if(answer=="0")
									html=html+"<section class='checkchoice'><label class='label'>答案：</label><div class='row'><div class='col col-4'><label class='toggle'><input type='checkbox' name='checkbox-toggle'><i data-swchon-text='正确' data-swchoff-text='错误'></i>正误：</label></div></div></section>";
								else
									html=html+"<section class='checkchoice'><label class='label'>答案：</label><div class='row'><div class='col col-4'><label class='toggle'><input type='checkbox' name='checkbox-toggle' checked='checked'><i data-swchon-text='正确' data-swchoff-text='错误'></i>正误：</label></div></div></section>";
							}
						else
						{
							html=html+"<section class='checkchoice' style='display:none'><label class='label'>答案：</label><div class='row'><div class='col col-4'><label class='toggle'><input type='checkbox' name='checkbox-toggle' checked='checked'><i data-swchon-text='正确' data-swchoff-text='错误'></i>正误：</label></div></div></section>";
						}
						if(type=="single")
							html=html+"<section class='singlechoice'><label class='label'>选项：</label><div class='row'><div class='col col-4'>";
						else
							html=html+"<section class='singlechoice' style='display:none'><label class='label'>选项：</label><div class='row'><div class='col col-4'>";
						var optarr=choices.split('|');
						opt="";
						for(j=0;j<optarr.length;j++)
						{
							if(answer.indexOf(String.fromCharCode(65+j)) != -1)
								opt=opt+"<div class=‘row’><label class='radio col col-2'><input type='radio' name='single"+id+"' checked='checked'><i></i></label><section class='col col-10'><label class='input'><input type='text' name='"+String.fromCharCode(65+j)+"' value='"+optarr[j]+"'></label></section></div>";
							else
								opt=opt+"<div class=‘row’><label class='radio col col-2'><input type='radio' name='single"+id+"'><i></i></label><section class='col col-10'><label class='input'><input type='text' name='"+String.fromCharCode(65+j)+"' value='"+optarr[j]+"'></label></section></div>";
						}
						html=html+opt;
						html=html+"</div></div></section>";
						if(type=="multy")
							html=html+"<section class='multychoice'><label class='label'>选项：</label><div class='row'><div class='col col-4'>";
						else
							html=html+"<section class='multychoice' style='display:none'><label class='label'>选项：</label><div class='row'><div class='col col-4'>";
						opt="";
						for(j=0;j<optarr.length;j++)
						{
							if(answer.indexOf(String.fromCharCode(65+j)) != -1)
								opt=opt+"<div class=‘row’><label class='checkbox col col-2'><input type='checkbox' name='multy"+id+"' checked='checked'><i></i></label><section class='col col-10'><label class='input'><input type='text' name='"+String.fromCharCode(65+j)+"' value='"+optarr[j]+"'></label></section></div>";
							else
								opt=opt+"<div class=‘row’><label class='checkbox col col-2'><input type='checkbox' name='multy"+id+"'><i></i></label><section class='col col-10'><label class='input'><input type='text' name='"+String.fromCharCode(65+j)+"' value='"+optarr[j]+"'></label></section></div>";
						}
						html=html+opt;
						html=html+"</div></div></section>";
						question.innerHTML=html;
						var field = document.getElementById('questionlist'); //2、找到父级元素
						field.appendChild(question);//插入到最左边
						for(j=0;j<knowledgelist.length;j++)
						{
							if(knowledgelist[j].id!=knowledgeid)
							{
								$("#KnowSelect"+id).append("<option value='"+knowledgelist[j].id+"'>"+knowledgelist[j].text+"</option>");
							}
						}
					}
					addtrigger();
				}
			);
		}
		loadquestions();
		//上一页
		function prepage()
		{
			if(pages>1)
				{
					pages--;
					loadquestions();
				}
				
		}
		//下一页
		function nextpage()
		{
			if(pages<allpages)
				{
					pages++;
					loadquestions();
				}
		}
		//跳转页面
		function jumppage()
		{
			var jp=$("#jumppages").val();
			if(jp>0&&jp<=allpages)
				{
					pages=jp;
					loadquestions();
				}
		}
		//按条件检索
		function searchquestions()
		{
			knowledgeid=$("#searchknowledge").val();
			questiontype=$("#searchtype").val();
			pagecount=$("#pagecount").val();
			pages=1;
			allpages=0;
			loadquestions();
		}
		//添加题目
		function addquestion()
		{
			var question = document.createElement('fieldset'); //创建元素
			question.id=addid;
			var id=addid;
			addid--;
			var text="";
			var choices="A、|B、|C、|D、";
			var type="check";
			var knowledgeid=0;
			var knowledgetext="全部";
			var knowledgeoption=""
			var html="<div class='row'><section class='col col-md-2'><label class='label'>知识点:</label><div class='col-md-6'><select class='form-control' id='KnowSelect"+id+"'><option value='0'>全部</option>";
			for(j=0;j<knowledgelist.length;j++)
			{
				html=html+"<option value='"+knowledgelist[j].id+"'>"+knowledgelist[j].text+"</option>"
			}
			html=html+"</select></div></section>";
			var answer="0";
			if(type=="check")
				{
				html=html+"<section class='col col-md-2'><label class='label'>题型:</label><div class='col-md-6'><select class='form-control typeselect'><option value='single'>单选</option><option value='multy'>多选</option><option value='check' selected='selected'>判断</option></select></div></section></div>";
				}
			else
				if(type=="single")
					{
						html=html+"<section class='col col-md-2'><label class='label'>题型:</label><div class='col-md-6'><select class='form-control typeselect'><option value='single'  selected='selected'>单选</option><option value='multy'>多选</option><option value='check'>判断</option></select></div></section></div>";
					}
				else
					html=html+"<section class='col col-md-2'><label class='label'>题型:</label><div class='col-md-6'><select class='form-control typeselect'><option value='single'>单选</option><option value='multy'  selected='selected'>多选</option><option value='check'>判断</option></select></div></section></div>";
			html=html+"<section><label class='label'>题干：</label><label class='textarea'> <i class='icon-append fa fa-comment'></i><textarea rows='4' name='comment'>"+text+"</textarea></label></section>";
			if(type=="check")
				{
					if(answer=="0")
						html=html+"<section class='checkchoice'><label class='label'>答案：</label><div class='row'><div class='col col-4'><label class='toggle'><input type='checkbox' name='checkbox-toggle'><i data-swchon-text='正确' data-swchoff-text='错误'></i>正误：</label></div></div></section>";
					else
						html=html+"<section class='checkchoice'><label class='label'>答案：</label><div class='row'><div class='col col-4'><label class='toggle'><input type='checkbox' name='checkbox-toggle' checked='checked'><i data-swchon-text='正确' data-swchoff-text='错误'></i>正误：</label></div></div></section>";
				}
			else
			{
				html=html+"<section class='checkchoice' style='display:none'><label class='label'>答案：</label><div class='row'><div class='col col-4'><label class='toggle'><input type='checkbox' name='checkbox-toggle' checked='checked'><i data-swchon-text='正确' data-swchoff-text='错误'></i>正误：</label></div></div></section>";
			}
			if(type=="single")
				html=html+"<section class='singlechoice'><label class='label'>选项：</label><div class='row'><div class='col col-4'>";
			else
				html=html+"<section class='singlechoice' style='display:none'><label class='label'>选项：</label><div class='col col-6'>";
			var optarr=choices.split('|');
			opt="";
			for(j=0;j<optarr.length;j++)
			{
				if(answer.indexOf(String.fromCharCode(65+j)) != -1)
					opt=opt+"<div class=‘row’><label class='radio col col-2'><input type='radio' name='single"+id+"' checked='checked'><i></i></label><section class='col col-10'><label class='input'><input type='text' name='"+String.fromCharCode(65+j)+"' value='"+optarr[j]+"'></label></section></div>";
				else
					opt=opt+"<div class=‘row’><label class='radio col col-2'><input type='radio' name='single"+id+"'><i></i></label><section class='col col-10'><label class='input'><input type='text' name='"+String.fromCharCode(65+j)+"' value='"+optarr[j]+"'></label></section></div>";
			}
			html=html+opt;
			html=html+"</div></div></section>";
			if(type=="multy")
				html=html+"<section class='multychoice'><label class='label'>选项：</label><div class='row'><div class='col col-4'>";
			else
				html=html+"<section class='multychoice' style='display:none'><label class='label'>选项：</label><div class='row'><div class='col col-4'>";
			opt="";
			for(j=0;j<optarr.length;j++)
			{
				if(answer.indexOf(String.fromCharCode(65+j)) != -1)
					opt=opt+"<div class=‘row’><label class='checkbox col col-2'><input type='checkbox' name='multy"+id+"' checked='checked'><i></i></label><section class='col col-10'><label class='input'><input type='text' name='"+String.fromCharCode(65+j)+"' value='"+optarr[j]+"'></label></section></div>";
				else
					opt=opt+"<div class=‘row’><label class='checkbox col col-2'><input type='checkbox' name='multy"+id+"'><i></i></label><section class='col col-10'><label class='input'><input type='text' name='"+String.fromCharCode(65+j)+"' value='"+optarr[j]+"'></label></section></div>";
			}
			html=html+opt;
			html=html+"</div></div></section>";
			question.innerHTML=html;
			var field = document.getElementById('questionlist'); //2、找到父级元素
			field.prepend(question);//插入到最前边
			addtrigger();
		}
		//为提醒选择添加触发器
		function addtrigger()
		{
			$(".typeselect").change(function(){  
				var choice=$(this).find('option:selected').val();
				var fieldset=$(this).parent().parent().parent().parent();
				var singlesection=fieldset.find(".singlechoice");
				var multysection=fieldset.find(".multychoice");
				var checksection=fieldset.find(".checkchoice");
				if(choice=="check")
				{
					singlesection[0].style.display="none";
					multysection[0].style.display="none";
					checksection[0].style.display="";
				}
				else
					if(choice=="single")
					{
						singlesection[0].style.display="";
						multysection[0].style.display="none";
						checksection[0].style.display="none";
					}
				else
					{
						singlesection[0].style.display="none";
						multysection[0].style.display="";
						checksection[0].style.display="none";
					}
			});
		}
		//保存当前页面题目
		function save()
		{
			var fieldlist=$("#questionlist").find("fieldset");
			var json="[";
			//console.log(fieldlist);
			for(i=0;i<fieldlist.length;i++)
				{
					if(i>0)
						json=json+",";
					var field=fieldlist[i];
					var knowledgeid=field.childNodes[0].childNodes[0].childNodes[1].childNodes[0].value;//知识点，题型
					var type=field.childNodes[0].childNodes[1].childNodes[1].childNodes[0].value;
					var text=field.childNodes[1].childNodes[1].childNodes[2].value; //题干
					var choices="A、|B、|C、|D、";
					var answer="";
					var id=field.id;
					
					if(id<0)
						{
							if(type=="check")
							{
								ischeck=field.childNodes[2].childNodes[1].childNodes[0].childNodes[0].childNodes[0].checked;
								if(ischeck)
									answer="1";
								else
									answer="0";
							}
							else
								if(type=="single")
									{
										var choiceA=field.childNodes[3].childNodes[1].childNodes[0].childNodes[1].childNodes[0].childNodes[0].value;
										var choiceB=field.childNodes[3].childNodes[1].childNodes[1].childNodes[1].childNodes[0].childNodes[0].value;
										var choiceC=field.childNodes[3].childNodes[1].childNodes[2].childNodes[1].childNodes[0].childNodes[0].value;
										var choiceD=field.childNodes[3].childNodes[1].childNodes[3].childNodes[1].childNodes[0].childNodes[0].value;
										choices=choiceA+"|"+choiceB+"|"+choiceC+"|"+choiceD;
										//var answer=field.childNodes[3].find('option:selected').val();
										if(field.childNodes[3].childNodes[1].childNodes[0].childNodes[0].childNodes[0].checked)
											answer="A";
										else
											if(field.childNodes[3].childNodes[1].childNodes[1].childNodes[0].childNodes[0].checked)
												answer="B";
											else
												if(field.childNodes[3].childNodes[1].childNodes[2].childNodes[0].childNodes[0].checked)
													answer="C";
												else
													answer="D";
									}
							else
								{
									var choiceA=field.childNodes[4].childNodes[1].childNodes[0].childNodes[0].childNodes[1].childNodes[0].childNodes[0].value;
									var choiceB=field.childNodes[4].childNodes[1].childNodes[0].childNodes[1].childNodes[1].childNodes[0].childNodes[0].value;
									var choiceC=field.childNodes[4].childNodes[1].childNodes[0].childNodes[2].childNodes[1].childNodes[0].childNodes[0].value;
									var choiceD=field.childNodes[4].childNodes[1].childNodes[0].childNodes[3].childNodes[1].childNodes[0].childNodes[0].value;
									choices=choiceA+"|"+choiceB+"|"+choiceC+"|"+choiceD;
									if(field.childNodes[4].childNodes[1].childNodes[0].childNodes[0].childNodes[0].childNodes[0].checked)
										answer=answer+"A";
									if(field.childNodes[4].childNodes[1].childNodes[0].childNodes[1].childNodes[0].childNodes[0].checked)
										answer=answer+"B";
									if(field.childNodes[4].childNodes[1].childNodes[0].childNodes[2].childNodes[0].childNodes[0].checked)
										answer=answer+"C";
									if(field.childNodes[4].childNodes[1].childNodes[0].childNodes[3].childNodes[0].childNodes[0].checked)
										answer=answer+"D";
								}
							json=json+"{\"id\":"+id+",\"text\":\""+text+"\",\"type\":\""+type+"\",\"choices\":\""+choices+"\",\"answer\":\""+answer+"\",\"knowledgeid\":\""+knowledgeid+"\"}";
						}
					else
						{
							if(type=="check")
							{
								ischeck=field.childNodes[2].childNodes[1].childNodes[0].childNodes[0].childNodes[0].checked;
								if(ischeck)
									answer="1";
								else
									answer="0";
							}
							else
								if(type=="single")
									{
										var choiceA=field.childNodes[3].childNodes[1].childNodes[0].childNodes[0].childNodes[1].childNodes[0].childNodes[0].value;
										var choiceB=field.childNodes[3].childNodes[1].childNodes[0].childNodes[1].childNodes[1].childNodes[0].childNodes[0].value;
										var choiceC=field.childNodes[3].childNodes[1].childNodes[0].childNodes[2].childNodes[1].childNodes[0].childNodes[0].value;
										var choiceD=field.childNodes[3].childNodes[1].childNodes[0].childNodes[3].childNodes[1].childNodes[0].childNodes[0].value;
										choices=choiceA+"|"+choiceB+"|"+choiceC+"|"+choiceD;
										//var answer=field.childNodes[3].find('option:selected').val();
										if(field.childNodes[3].childNodes[1].childNodes[0].childNodes[0].childNodes[0].childNodes[0].checked)
											answer="A";
										else
											if(field.childNodes[3].childNodes[1].childNodes[0].childNodes[1].childNodes[0].childNodes[0].checked)
												answer="B";
											else
												if(field.childNodes[3].childNodes[1].childNodes[0].childNodes[2].childNodes[0].childNodes[0].checked)
													answer="C";
												else
													answer="D";
									}
							else
								{
									var choiceA=field.childNodes[4].childNodes[1].childNodes[0].childNodes[0].childNodes[1].childNodes[0].childNodes[0].value;
									var choiceB=field.childNodes[4].childNodes[1].childNodes[0].childNodes[1].childNodes[1].childNodes[0].childNodes[0].value;
									var choiceC=field.childNodes[4].childNodes[1].childNodes[0].childNodes[2].childNodes[1].childNodes[0].childNodes[0].value;
									var choiceD=field.childNodes[4].childNodes[1].childNodes[0].childNodes[3].childNodes[1].childNodes[0].childNodes[0].value;
									choices=choiceA+"|"+choiceB+"|"+choiceC+"|"+choiceD;
									if(field.childNodes[4].childNodes[1].childNodes[0].childNodes[0].childNodes[0].childNodes[0].checked)
										answer=answer+"A";
									if(field.childNodes[4].childNodes[1].childNodes[0].childNodes[1].childNodes[0].childNodes[0].checked)
										answer=answer+"B";
									if(field.childNodes[4].childNodes[1].childNodes[0].childNodes[2].childNodes[0].childNodes[0].checked)
										answer=answer+"C";
									if(field.childNodes[4].childNodes[1].childNodes[0].childNodes[3].childNodes[0].childNodes[0].checked)
										answer=answer+"D";
								}
							json=json+"{\"id\":"+id+",\"text\":\""+text+"\",\"type\":\""+type+"\",\"choices\":\""+choices+"\",\"answer\":\""+answer+"\",\"knowledgeid\":\""+knowledgeid+"\"}";
						}
				}
			json=json+"]";
			//console.log(json);
			$.post("SaveQuestions",{
					questions:json
				},
				function(data){
					if(data=="success")
					{
						alert("保存成功！");
						location.reload();
					}
						
				}
			);
		}