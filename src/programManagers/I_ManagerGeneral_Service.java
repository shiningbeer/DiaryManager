package programManagers;
import java.util.List;
import java.util.Date;
import basicDataStructure.*;
//定义所有界面需要调用的接口
public interface I_ManagerGeneral_Service
{
	public List<DiaryDescription> GetMonthDairyDescription(String m);
	public boolean DeleteDairy(int dairyID);
        public List<DiaryDescription> GetInquiredDairiyDescription(String ds,String de, String type, String key);
        public boolean AddNewDiary(Diary dairy);
	public boolean UpdateDiary(Diary dairy);
        public boolean AddNewUser(String user, String pw);
        public boolean AddNewType(String type);
        public boolean DeleteType(String type);
        public boolean VerifyUser(String user, String pw);
        public boolean VerifyUser(String pw);
	public void ChangePw(String pw);
        public List<String> GetUserList();
        public List<String> GetUserTypesList();
        public void ShowMainWindow();
        public void ShowTypeManageWindow();
        public void ShowRegiserWindow();
        public void ShowModiPasswordWindow();
        public void SetCurrentUser(String user);
	public String GetCurrentUser();
        public List<DiaryDescription> GetRecentDiaryByWriter(int num);
        public DiaryMultiMedia GetDiaryMultiMedia(int diaryid);
        public int getIdByWriterAndTime(String user,String time);
        public  int GetDiaryVisibleTType(int diaryID);
        public List<DiaryDescription> GetOtherPublicDiaryDescriptions(String writer,String ds,String de,String key);
        public List<DiaryDescription> GetRecentDairyOtherPublic(int num);
        public List<Comment> getCommentListByDiarID(int id);
        public boolean AddComment(Comment c);
        public List<DiaryDescription> getDiaryDesRecentlyCommentedByUser(int num);
        public List<DiaryDescription> getDiaryDesByConditionOfComments(String ds,String de,String user, String key);
        public void Login();
        public boolean IsUserExist(String user);

}

