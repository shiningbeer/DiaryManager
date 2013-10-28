package programManagers;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import basicDataStructure.*;
import dataProvider.*;
import java.util.ArrayList;
//import dataProvider.Diary;


public class Manager_Diary {
	
	public boolean AddDiary(Diary newDairy)
	{ 
		return DataProvider_DiaryInfo.AddDairy(newDairy);
	}
        public  int GetDiaryVisibleTType(int diaryID){
            return  DataProvider_DiaryInfo.GetDiaryVisibleTType(diaryID);
        }
	public boolean upDateDiary(Diary newDairy)
	{   
		return DataProvider_DiaryInfo.UpdateDiary(newDairy);
	}
        public int getIdByWriterAndTime(String user,String time)
        {
            return DataProvider_DiaryInfo.getIdByWriterAndTime(user, time);
        }
	public List<DiaryDescription> GetRecentDiaryByWriter(String user, int num)
        {
            return DataProvider_DiaryInfo.GetRecentDairyDescriptionByWriter(user,num);
        }
	public  List<DiaryDescription> GetInquiredDairyDescription(String user, String ds,String de, String type, String key)
	{   
                return DataProvider_DiaryInfo.GetInquiredDairyDescription(user, ds,de, type, key);		
	}
	public boolean DeleteDiary(int dairyID)
        {
            return DataProvider_DiaryInfo.DeleteDiary(dairyID);
	}
        
        public List<DiaryDescription> GetMonthDairyDescription(String user, String m) 
	{
		Date ds=new Date();
		Date de=new Date();
		SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			ds=f.parse(m);
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		Calendar c=Calendar.getInstance();
		c.setTime(ds);
		c.add(Calendar.MONTH, +1);
                c.add(Calendar.DATE, -1);
		de=c.getTime();
		String dstart=m;
		String dend=f.format(de);
		List<DiaryDescription> MonthDiary = DataProvider_DiaryInfo.GetInquiredDairyDescription(user,dstart, dend, "","" );
		
		return MonthDiary;
	}
        public List<DiaryDescription> GetRecentDairyOtherPublic(String user, int num){
            return DataProvider_DiaryInfo.GetRecentDairyOtherPublic(user, num);
        }
        public List<DiaryDescription> getDiaryDesRecentlyCommentedByUser(String user, int num){
            List<Integer> li=DataProvider_CommentInfo.getDiaryIDListRecentlyCommentedByUser(user, num);
            List<DiaryDescription> ldd=new ArrayList<DiaryDescription>();
            for(int id:li){
                DiaryDescription dd=DataProvider_DiaryInfo.GetDiaryByID(id);
                if(dd!=null)
                ldd.add(dd);
            }
            return ldd;
        }
        public List<DiaryDescription> GetDiaryDesByConditionOfComments(String ds,String de,String user, String key){
            //先获取满足条件的diaryid
            List<Integer> li=DataProvider_CommentInfo.getDiaryIDListByMultiCondition(ds, de, user, key);
            List<DiaryDescription> ldd=new ArrayList<DiaryDescription>();
            for(int id:li){
                DiaryDescription dd=DataProvider_DiaryInfo.GetDiaryByID(id);
                if(dd!=null)
                ldd.add(dd);
            }
            return ldd;
        }
        public List<Comment> getCommentListByDiarID(int id){
            return DataProvider_CommentInfo.GetCommentsByDiaryID(id);
        }
        public boolean AddComment(Comment c){
            return DataProvider_CommentInfo.AddComment(c);
        }
        public List<DiaryDescription> GetOtherPublicDiaryDescriptions(String writer,String ds,String de,String key)
        {
            return DataProvider_DiaryInfo.GetOtherPublicDiaryDescriptions(writer, ds, de, key);
        }
        public DiaryMultiMedia GetDiaryMultiMedia(int diaryid)
        {
            return DataProvider_DiaryInfo.GetDairyDiaryMultiMedia(diaryid);
        }

}
