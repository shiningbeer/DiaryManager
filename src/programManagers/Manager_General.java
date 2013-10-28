package programManagers;

import auxWindows.Window_Register;
import auxWindows.Window_Login;
import auxWindows.Window_ModifyPw;
import auxWindows.Window_TypeManage;
import mainWindow.Window_MainFrame;
import java.util.Date;
import java.util.List;

import javax.print.DocFlavor.STRING;
import javax.swing.JFrame;

import basicDataStructure.*;
//总管类，实现I_ManagerGeneral_Service接口
public class Manager_General implements I_ManagerGeneral_Service{
    
	Manager_Diary Dmanage = new Manager_Diary();
	Manager_User Umanage=new Manager_User();
	Manager_Type Cmanage=new Manager_Type();
	Window_MainFrame Mframe;	
	String currentUser;
        
	public void ShowMainWindow()
	{	
            Mframe=new Window_MainFrame(this);
	}
        public boolean AddNewType(String type)
        {
            return Cmanage.AddUserDiaryType(currentUser, type);
        }
        public int getIdByWriterAndTime(String user,String time)
        {
            return Dmanage.getIdByWriterAndTime(user, time);
        }
        public boolean DeleteDairy(int dairyID)
        {
            return Dmanage.DeleteDiary(dairyID);
        }
        public boolean IsUserExist(String user){
            return Umanage.IsUserExist(user);
        }
        public String GetCurrentUser()
        {
            return currentUser;
        }
        public List<DiaryDescription> GetRecentDiaryByWriter(int num)
        {
            return Dmanage.GetRecentDiaryByWriter(currentUser,num);
        }
        public DiaryMultiMedia GetDiaryMultiMedia(int diaryid)
        {
            return Dmanage.GetDiaryMultiMedia(diaryid);
        }
	public void Login()
	{
            new Window_Login(this);
            
	}
        public List<DiaryDescription> GetRecentDairyOtherPublic(int num){
            return Dmanage.GetRecentDairyOtherPublic(currentUser, num);
        }
        public List<DiaryDescription> getDiaryDesRecentlyCommentedByUser(int num){
            return Dmanage.getDiaryDesRecentlyCommentedByUser(currentUser, num);
        }
        public List<DiaryDescription> GetInquiredDairiyDescription(String Key,String cls, String dateStart, String dateEnd)
        {
            return Dmanage.GetInquiredDairyDescription(currentUser,Key, cls, dateStart, dateEnd);
        }
        public List<String> GetUserList()
        {
            return Umanage.GetAllUser();
        }
	public List<String> GetUserTypesList()
        {
            return Cmanage.GetAllDiaryTypeByUser(currentUser);
                    
        }
        public List<DiaryDescription> getDiaryDesByConditionOfComments(String ds,String de,String user, String key){
            return Dmanage.GetDiaryDesByConditionOfComments(ds, de, user, key);
        }
        public List<DiaryDescription> GetOtherPublicDiaryDescriptions(String writer,String ds,String de,String key)
        {
            return Dmanage.GetOtherPublicDiaryDescriptions(writer, ds, de, key);
        
        }
        public List<Comment> getCommentListByDiarID(int id){
            return Dmanage.getCommentListByDiarID(id);
        }
        public boolean AddComment(Comment c){
            return Dmanage.AddComment(c);
        }
        public  int GetDiaryVisibleTType(int diaryID){
            return Dmanage.GetDiaryVisibleTType(diaryID);
        }
	public boolean UpdateDiary(Diary dairy) {
		// TODO Auto-generated method stub
		return Dmanage.upDateDiary(dairy);
	}
	public boolean AddNewDiary(Diary dairy)
	{
		return Dmanage.AddDiary(dairy);
	}
	public boolean DeleteDiary(int dairyID) {
		return Dmanage.DeleteDiary(dairyID);
	}
	public void ShowTypeManageWindow(){
		new Window_TypeManage(this);
	}
	public List<DiaryDescription> GetMonthDairyDescription(String m)
        {
		return Dmanage.GetMonthDairyDescription(currentUser, m);
	}
	public void ShowModiPasswordWindow()
	{
		new Window_ModifyPw(this);		
	}
	public List<DiaryDescription> GetInquiredDairDescription(String ds,String de, String type, String key)
	{
		return Dmanage.GetInquiredDairyDescription(currentUser, ds,de, type, key);
	}
	public boolean VerifyUser(String user, String pw)
	{
		return Umanage.VerifiedUser(user, pw);
	}
        public boolean VerifyUser(String pw)
	{
		return Umanage.VerifiedUser(currentUser, pw);
	}
        public void SetCurrentUser(String user)
        {
            currentUser=user;
        }
	public void ChangePw(String pw)
	{
		Umanage.UpdataPw(currentUser,pw);
	}
	
	public void ShowRegiserWindow()
	{
		new Window_Register(this);
	}
	//����ʵ��RegisterAction
	public boolean AddNewUser(String user, String pw)
	{
		return Umanage.AddUser(user, pw);
	}
	
	//����ʵ��ClassWindowAction�ӿ�
	public boolean AddNewDiaryType(String cla)
	{
		return Cmanage.AddUserDiaryType(currentUser, cla);
	
	}
	public boolean DeleteType(String cls)
	{
		return Cmanage.DeleteUserDiaryType(currentUser, cls);
	}
}
