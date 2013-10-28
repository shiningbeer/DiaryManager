/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainWindow;

import basicDataStructure.Comment;
import basicDataStructure.Diary;
import basicDataStructure.DiaryDescription;
import java.util.List;
import mainWindow.SubPanel_DiaryDescription;


/**
 *
 * @author Administrator
 */
public interface I_RespondingListener {
    public void setWhichElementIsSelected(SubPanel_DiaryDescription poe);
    public void searchUserDiaryByMultiCondition(String ds, String de, String type, String key);
    public void searchUserRecentCommentDiary();
    public void searchUserDiaryByMonth(String month);
    public void searchOtherRecentPublic();
    public void searchPublicDiaryByMultiCondition(String ds,String de,String user,String key);
    public void searchUserRecentModiDiary();
    public void saveDiary(Diary d);
    public void addComment(Comment c);
    public void searchCommentedDiaryByMultiCondition(String ds,String de,String user, String key);
            
    
}
