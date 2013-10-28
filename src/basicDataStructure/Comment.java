/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package basicDataStructure;

/**
 *
 * @author Administrator
 */
public class Comment {
    private String DateTime;
    private int Id;
    private String Writer;
    private String StringContent;
    private int DiaryID;
    public void setDateTime(String dateTime) {
            DateTime = dateTime;
    }
    public String getDateTime() {
            return DateTime;
    }
    public void setId(int id) {
            Id = id;
    }
    public int getId() {
            return Id;
    }
    public void setWriter(String user) {
            Writer = user;
    }
    public String getWriter() {
            return Writer;
    }
    public void setComment(String content) {
            StringContent = content;
    }
    public String getComment() {
            return StringContent;
    }
    public void setDiaryID(int ID) {
            DiaryID=ID;
    }
    public int getDiaryID() {
            return DiaryID;
    }
}
