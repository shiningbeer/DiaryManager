package myAspect;
import basicDataStructure.*;

public aspect Log {
	pointcut diaryAdd(Diary d): 
		call (boolean AddDiary(Diary)) && args(d);
	after(Diary d) : diaryAdd(d){
		FileLogger.CreateFile();
		FileLogger.logDiary("用户：“"+d.getWriter()+"”添加一篇日记，标题为："+d.getTitle()+"\r\n");
	}
	pointcut diaryDel(int d): 
		call (boolean DeleteDiary(int)) && args(d);
	after(int d) : diaryDel(d){
		FileLogger.CreateFile();
		FileLogger.logDiary("删除了一篇日记，id为："+String.valueOf(d)+"\r\n");
	}
	pointcut diaryUpdate(Diary d): 
		call (boolean UpdateDiary(Diary)) && args(d);
	after(Diary d) : diaryUpdate(d){
		FileLogger.CreateFile();
		FileLogger.logDiary("用户：“"+d.getWriter()+"”修改一篇日记，新标题为："+d.getTitle()+"\r\n");
	}
	
	pointcut typeAdd(String user, String type): 
		call (boolean AddDClass(String, String)) && args(user,type);
	after(String user, String type) : typeAdd(user,type){
		FileLogger.CreateFile();
		FileLogger.logType("用户：“"+user+"”添加了一个新分类："+type+"\r\n");
	}
	pointcut typeDel(String user, String type): 
		call (boolean DeleteUserDiarType(String, String)) && args(user,type);
	after(String user, String type) : typeDel(user,type){
		FileLogger.CreateFile();
		FileLogger.logType("用户：“"+user+"”删除了一个分类："+type+"\r\n");
	}



}
