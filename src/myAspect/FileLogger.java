package myAspect;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileLogger {
	public final static String RECORDER_FILE_FOLDER_PATH = "logs";
	public final static String EXCEPTION_RECORDER_FILE_PATH = "logs/error.log";
	public final static String DIARY_OPERATION_RECORDER_FILE_PATH = "logs/diary.log";
	public final static String FOLDER_OPERATION_RECORDER_FILE_PATH = "logs/diaryType.log";
	public final static String USER_OPERATION_RECORDER_FILE_PATH = "logs/user.log";
	
	public final static String TIMEFORMATE_STRING = "yyyy-MM-dd HH:mm:ss";

	private static FileLogger fileLogger = null;
	
	public static void CreateFile() {
		File root = new File(RECORDER_FILE_FOLDER_PATH);
		if (!root.exists()) {
			root.mkdir();
		}
		
		new File(EXCEPTION_RECORDER_FILE_PATH);
		new File(DIARY_OPERATION_RECORDER_FILE_PATH);
		new File(FOLDER_OPERATION_RECORDER_FILE_PATH);
		new File(USER_OPERATION_RECORDER_FILE_PATH);
	}
	
	private static void writeLog(String path, String log){
		File logFile = new File(path);
		
		SimpleDateFormat formatter = new SimpleDateFormat(TIMEFORMATE_STRING);
		String time =  formatter.format(new Date());
		String content = String.format("[%s]: %s", time, log);
		
		try {
			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile,true)));
			output.write(content);
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void logError(String log) {
		writeLog(EXCEPTION_RECORDER_FILE_PATH, log);
	}
	
	public static void logDiary(String log) {
		writeLog(DIARY_OPERATION_RECORDER_FILE_PATH, log);
	}
	
	public static void logType(String log) {
		writeLog(FOLDER_OPERATION_RECORDER_FILE_PATH, log);
	}
	
	public void logUser(String log){
		writeLog(USER_OPERATION_RECORDER_FILE_PATH, log);
	}
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
