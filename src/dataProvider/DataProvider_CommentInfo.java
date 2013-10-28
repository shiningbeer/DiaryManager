/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dataProvider;

import basicDataStructure.Comment;
import dataProvider.dbo.SqlBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class DataProvider_CommentInfo {
//    public static List<Comment> GetRecentCommentByWriter(String user, int num)
//    {
//            String sql="select * from diaries where writer=? order by updateTime limit "+num;
//            List<Map<Object, Object>> rs=SqlBean.executeQuery(sql, user);
//            return null;
//
//    }
    public static boolean AddComment(Comment c)
	{
		String sql="insert into comments (updateTime,diaryId,writer,dt,comment) values(time(),?,?,?,?)";
		int i=SqlBean.executeUpdate(sql,c.getDiaryID(),c.getWriter(),c.getDateTime(),c.getComment());
			if(i==0)
				return false;
			else
				return true;
	}
//    public static List<Comment> GetRecentCommentsByAllUser(int num)
//    {
//
//            String sql="select * from diaries where writer<>? and visibleType=2 order by updateTime limit "+num;
//            List<Map<Object, Object>> rs=SqlBean.executeQuery(sql);
//            return null;
//
//    }
    public static List<Integer> getDiaryIDListRecentlyCommentedByUser(String user,int num){
         String sql="select distinct(diaryId) from comments where writer=? order by updateTime desc limit "+num;
         List<Map<Object, Object>> rs=SqlBean.executeQuery(sql,user);
         List<Integer> li=new ArrayList<Integer>();
         for(Map<Object,Object> oneMap: rs)
		{
                    int i=(int)oneMap.get("diaryId");
                    li.add(i);
			
		}
         return li;
    }
    public static List<Integer> getDiaryIDListByMultiCondition(String ds,String de,String writer, String key){
        String sql="select distinct(diaryId) from comments where dt>=date(?) and dt<=date(?,'+1day') and comment like ?";
        if(!writer.equals(""))
			sql+=" and writer='"+writer+"'";
		sql+= " order by dt";
		List<Map<Object, Object>> rs=SqlBean.executeQuery(sql,ds,de,"%"+key+"%");
                 List<Integer> li=new ArrayList<Integer>();
                for(Map<Object,Object> oneMap: rs)
		{
                    int i=(int)oneMap.get("diaryId");
                    li.add(i);
			
		}
         return li;
    }
    public static List<Comment> GetCommentsByDiaryID(int id)
    {

            String sql="select * from comments where diaryId=? order by dt desc";
            List<Map<Object, Object>> rs=SqlBean.executeQuery(sql,id);
            return ListMapToListComment(rs);

    }
    public static boolean DeleteComment(int id)
    {
            String sql="delete from diaries where id=?";
            int i=SqlBean.executeUpdate(sql, id);
            if(i==0)
                    return false;
            else
                    return true;
    }
    public static boolean DeleteCommentsByDiaryID(int id)
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
    public static boolean UpdateDiary(Comment c)
    {
            String sql="update diaries set title=?,type=?,dt=?,stringContent"
                    + "=?, visibleType=?, richContent=?, voice=?, updateTime=time() where id=?";
            int i=SqlBean.executeUpdate(sql);
                    if(i==0)
                            return false;
                    else
                            return true;
    }
    private static List<Comment> ListMapToListComment(List<Map<Object,Object>> rs)
    {
            List<Comment> result=new ArrayList<Comment>();
            for(Map<Object,Object> oneMap:rs)
            {

                    int id=(Integer)oneMap.get("id");
                    int did=(Integer)oneMap.get("diaryId");
                    String userName=(String)oneMap.get("writer");
                    String content=(String)oneMap.get("comment");
                    String datetime=(String)oneMap.get("dt");

                    Comment da=new Comment();
                    da.setId(id);
                    da.setDiaryID(did);
                    da.setWriter(userName);
                    da.setDateTime(datetime);
                    da.setComment(content);

                    result.add(da);
            }
            return result;
    }
    public static List<Comment> GetInquiredDairyDescription(String user, String ds,String de, String type, String key)
    {
            String sql="select * from diaries where writer=? and (title like ? or stringContent like ?) and dt>= date(?) and dt<= date(?,'+1day')";
            if(!type.equals(""))
                    sql+=" and type='"+type+"'";
            sql+= " order by dt";
            List<Map<Object, Object>> rs=SqlBean.executeQuery(sql, user, "%"+key+"%","%"+ key+"%", ds, de);
            return ListMapToListComment(rs);
    }
        
}
