package dataProvider;
import java.util.ArrayList;
import java.util.Date;
import dataProvider.dbo.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.io.*;

import basicDataStructure.*;
import javax.swing.*;
import javax.imageio.*;
/**
 * 主要作用：
 * 提供“日记表”的数据，业务逻辑需要的有关“日记表”的数据全由该类提供,并提供对日记表的增删改操作
 * 
 * 该类可调用的方法均为静态方法，所以无需要实例化该类
 * 
 * @作者 wave
*/

/**
 * 主要作用：
 提供“日记表”的数据，业务逻辑需要的有关“日记表”的数据全由该类提供,并提供对日记表的增删改操作
 
 该类可调用的方法均为静态方法，所以无需要实例化该类
 * @作者 wave
 */
public class DataProvider_DiaryInfo 
{
	  /**
	   * @作用：根据用户名和数目取出最近的日记
	   * @param user 传入用户名
	   * @param num 传入需要的日记数量
       * @return 返回r指定用户最近指定数量的日记列表
      */
	public static List<DiaryDescription> GetRecentDairyDescriptionByWriter(String user, int num)
	{
		String sql="select * from diaries where writer=? order by updateTime desc limit "+num;
		List<Map<Object, Object>> rs=SqlBean.executeQuery(sql, user);
		return ListMapToListDairyDes(rs);
	
	}
        public static List<DiaryDescription> GetRecentDairyOtherPublic(String user, int num)
	{
		
                String sql="select * from diaries where writer<>? and visibleType=2 order by updateTime limit "+num;
		List<Map<Object, Object>> rs=SqlBean.executeQuery(sql, user);
		return ListMapToListDairyDes(rs);
	
	}
	/**
	 * @作用：增加一篇日记
	 * @param dairy 所要添加的那一篇日记
     * @return 成功添加返回true，否则返回false
    */
	public static boolean AddDairy(Diary dairy)
	{
		String sql="insert into diaries (updateTime,writer,title,type,dt,stringContent,visibleType,richContent,voice) values(time(),?,?,?,?,?,?,?,?)";
		int i=SqlBean.executeUpdate(sql, dairy.getWriter(),dairy.getTitle(),dairy.getDiaryType(),
                        dairy.getDateTime(),dairy.getStringContent(),dairy.getVisibleType(), dairy.getRichContent(),dairy.getVoice());
			if(i==0)
				return false;
			else
				return true;
	}
	/**
	 * @作用：删除一篇日记
	 * @param id 所要删除的那一篇日记的id
     * @return 成功删除返回true，否则返回false
    */
	public static boolean DeleteDiary(int id)
	{
		String sql="delete from diaries where id=?";
		int i=SqlBean.executeUpdate(sql, id);
		if(i==0)
			return false;
		else
			return true;
	}
        public static int getIdByWriterAndTime(String user,String time)
        {
            String sql="select id from diaries where writer=? and dt=?";
            List<Map<Object, Object>> rs=SqlBean.executeQuery(sql, user,time);
            if(rs.size()<1)
                return -1;
            int i=(int)rs.get(0).get("id");
            return i;
        }
	/**
	 * @作用：更新一篇日记的内容、标题、分类，不包括图片和声音，图片和声音必须调用其它方法进行更新
	 * @param dairy 修改的日记新内容
     * @return 成功返回true，否则返回false
    */
	public static boolean UpdateDiary(Diary dairy)
	{
		String sql="update diaries set title=?,type=?,dt=?,stringContent"
                        + "=?, visibleType=?, richContent=?, voice=?, updateTime=time() where id=?";
		int i=SqlBean.executeUpdate(sql, dairy.getTitle(),dairy.getDiaryType(),dairy.getDateTime(),
                        dairy.getStringContent(),dairy.getVisibleType(),dairy.getRichContent(),dairy.getVoice(),dairy.getId());
			if(i==0)
				return false;
			else
				return true;
	}
	/**
	 * 私有函数，
	 * @作用：将从SqlBean类获得的数据格式化成List<Dairy>类型
	 * @param 
	 * @将输入转化为日记列表
	 */
        private static DiaryDescription MaptoDiaryDes(Map<Object,Object> oneMap)
        {
            int id=(Integer)oneMap.get("id");
			String userName=(String)oneMap.get("writer");
			String title=(String)oneMap.get("title");
			String datetime=(String)oneMap.get("dt");
			String cla=(String)oneMap.get("type");
			String content=(String)oneMap.get("stringContent");
			
			DiaryDescription da=new DiaryDescription();
			da.setId(id);
			da.setWriter(userName);
			da.setTitle(title);
			da.setDateTime(datetime);
			da.setDiaryType(cla);
			da.setStringContent(content);
                        return da;
        }
	private static List<DiaryDescription> ListMapToListDairyDes(List<Map<Object,Object>> rs)
	{
		List<DiaryDescription> result=new ArrayList<DiaryDescription>();
		for(Map<Object,Object> oneMap:rs)
		{
			DiaryDescription da=MaptoDiaryDes(oneMap);
			result.add(da);
		}
		return result;
	}
	/**
	 * @作用：根据条件给出查询的日记结果
	   * @param user 传入用户名
	   * @param titleKey 查询条件：需要标题里包含的关键词
	   * @param contentKey 查询条件：需要内容里包含的关键词
	   * @param dateStart 查询条件：开始时间
	   * @param dateEnd 查询条件：结束时间
     * @return 返回r指定用户最近指定数量的日记列表
    */
	public static List<DiaryDescription> GetInquiredDairyDescription(String user, String ds,String de, String type, String key)
	{
		String sql="select * from diaries where writer=? and (title like ? or stringContent like ?) and dt>= date(?) and dt<= date(?,'+1day')";
		if(!type.equals(""))
			sql+=" and type='"+type+"'";
		sql+= " order by dt";
		List<Map<Object, Object>> rs=SqlBean.executeQuery(sql, user, "%"+key+"%","%"+ key+"%", ds, de);
		return ListMapToListDairyDes(rs);
	}
	public static List<DiaryDescription> GetOtherPublicDiaryDescriptions(String writer,String ds,String de,String key)
        {
            String sql="select * from diaries where (title like ? or stringContent like ?) and visibleType=2 and dt>= date(?) and dt<= date(?,'+1day')";
		if(!writer.equals(""))
			sql+=" and writer='"+writer+"'";
		sql+= " order by dt";
		List<Map<Object, Object>> rs=SqlBean.executeQuery(sql,"%"+key+"%","%"+ key+"%", ds, de);
		return ListMapToListDairyDes(rs);
        }
	public static int GetDiaryVisibleTType(int diaryID)
        {
            String sql="select visibleType from diaries where id=?";
            List<Map<Object, Object>> rs=SqlBean.executeQuery(sql, diaryID);
            if(rs.size()<1)
                return -1;
            int i=(int)rs.get(0).get("visibleType");
            return i;
        }
        public static DiaryDescription GetDiaryByID(int diaryID)
        {
            String sql="select * from diaries where id=?";
            List<Map<Object, Object>> rs=SqlBean.executeQuery(sql, diaryID);
            if(rs.size()<1)
                return null;
            else{
                return MaptoDiaryDes(rs.get(0));
            }
        }
	public static DiaryMultiMedia GetDairyDiaryMultiMedia(int dairyID)
	{
		String sql="select richContent, voice from diaries where id=?";
		List<Map<Object, Object>> rs=SqlBean.executeQuery(sql, dairyID);
		List<DiaryMultiMedia> lp=new ArrayList<DiaryMultiMedia>();
		for(Map<Object,Object> oneMap: rs)
		{
			DiaryMultiMedia pi=new DiaryMultiMedia();
			pi.setRichContent((byte[])oneMap.get("richContent"));
                        pi.setVoice((byte[])oneMap.get("voice"));
			lp.add(pi);
			
		}
                if(lp.size()>0)
		    return lp.get(0);
                else
                    return null;
	}
        
        
	
}
