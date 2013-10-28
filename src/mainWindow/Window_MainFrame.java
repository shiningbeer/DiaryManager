/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainWindow;

import basicDataStructure.Comment;
import basicDataStructure.Diary;
import basicDataStructure.DiaryDescription;
import basicDataStructure.DiaryMultiMedia;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import programManagers.I_ManagerGeneral_Service;

/**
 *主窗口
 * @author Administrator
 */
public class Window_MainFrame extends JFrame implements I_RespondingListener{
    private I_ManagerGeneral_Service gmService;//数据库服务
    private Panel_CombinedInquiry pInquire=new Panel_CombinedInquiry(this);//查询面板
    private Panel_DiaryDetailReadOrEdit pDiaryReadOrEdit;//查看、编辑、新建面板
    private Panel_InquireResult pInquireResult;//查询结果面板
    private Panel_Comment pComment=new Panel_Comment(this);//评论面板
    private SubPanel_DiaryDescription selectedDiaryDescription;//当前选中的日记，更新日记、发表评论，都需要它的参数
    private Panel_MyCard pDiary_View_Edit_Comment=new Panel_MyCard();//将评论面板和查看面板组成一个card布局
    public Window_MainFrame(){
        InitComponents();
        this.setVisible(true);
        
    }
   
    public Window_MainFrame(I_ManagerGeneral_Service gm){
        gmService=gm;
        pDiaryReadOrEdit= new Panel_DiaryDetailReadOrEdit(this);
        pInquireResult=new Panel_InquireResult(this);
        pComment.setCurrentUser(gm.GetCurrentUser());
        InitComponents();
        this.setTitle("生活日记管理--------登录用户："+gm.GetCurrentUser());
        List<String> typelist=gm.GetUserTypesList();
        pInquire.setTypeComboBox(typelist);
        pDiaryReadOrEdit.setTypeComboBox(typelist);
        pInquireResult.setDiaryList(gm.GetRecentDiaryByWriter(10));
        this.setVisible(true);
        
    }
     //----以下为接口实现-----也就是响应界面元素的操作-------------
     public void setWhichElementIsSelected(SubPanel_DiaryDescription poe){
         selectedDiaryDescription=poe;
         pDiaryReadOrEdit.setDiaryEditable(false);
         List<Comment> lc=gmService.getCommentListByDiarID(poe.getDiaryID());
         pComment.setCommentList(lc);
         Diary editingdiary=new Diary();
         editingdiary.setId(poe.getDiaryID());
         editingdiary.setWriter(poe.getWriter());
         editingdiary.setDateTime(poe.getDiaryTime());
         editingdiary.setTitle(poe.getDiaryTitle());
         editingdiary.setStringContent(poe.getDiaryStringContent());
         editingdiary.setDiaryType(poe.getDiaryType());
         editingdiary.setVisibletType(gmService.GetDiaryVisibleTType(poe.getDiaryID()));
         DiaryMultiMedia dmm=gmService.GetDiaryMultiMedia(poe.getDiaryID());
        editingdiary.setRichContent(dmm.getrichContent());
        editingdiary.setVoice(dmm.getVoice());
         pDiaryReadOrEdit.setEditingDiary(editingdiary);
         pComment.setEnabled(true);
         pDiary_View_Edit_Comment.ShowFirstPanel();
         repaint();
         validate();
     }
    public void saveDiary(Diary editingDiary){
        
        if(editingDiary.getId()==-1){ 
               int id=gmService.getIdByWriterAndTime( editingDiary.getWriter(),editingDiary.getDateTime());
               //如果这个时间，这个用户，已经有一份日记了，那么再等等
               if(id!=-1)
               {
                   JOptionPane.showMessageDialog(null, "请隔一秒再保存！");
                   return;
               }
               gmService.AddNewDiary(editingDiary);
               editingDiary.setId(gmService.getIdByWriterAndTime( editingDiary.getWriter(),editingDiary.getDateTime()));
               pDiaryReadOrEdit.setEditingDiaryId(editingDiary.getId());
               pInquireResult.addOneDiaryDescriptionAtTop(editingDiary.getDescription());
        }
        else{
                   gmService.UpdateDiary(editingDiary);
                   pInquireResult.removeDiaryDescription(selectedDiaryDescription);                   
                   selectedDiaryDescription=null;
                   pInquireResult.addOneDiaryDescriptionAtTop(editingDiary.getDescription());
        }
        JOptionPane.showMessageDialog(null, "保存成功！");
        selectedDiaryDescription=null;
        pComment.setEnabled(false);
        repaint();
        validate();
    }
    public void addComment(Comment c){    
        c.setDiaryID(selectedDiaryDescription.getDiaryID());
        gmService.AddComment(c);
    }
    public void searchUserDiaryByMultiCondition(String ds, String de, String type, String key){
        pInquireResult.setDiaryList(gmService.GetInquiredDairiyDescription(ds, de, type, key));
    }
    public void searchUserRecentCommentDiary(){
        pInquireResult.setDiaryList((gmService.getDiaryDesRecentlyCommentedByUser(10)));
        
    }
    public void searchCommentedDiaryByMultiCondition(String ds,String de,String user, String key){
        pInquireResult.setDiaryList(gmService.getDiaryDesByConditionOfComments(ds, de, user, key));
    }
            
    public void searchUserDiaryByMonth(String month){
        pInquireResult.setDiaryList(gmService.GetMonthDairyDescription(month));
    }
    public void searchOtherRecentPublic(){
        pInquireResult.setDiaryList(gmService.GetRecentDairyOtherPublic(10));
    }
    public void searchPublicDiaryByMultiCondition(String ds,String de,String user,String key){
        pInquireResult.setDiaryList(gmService.GetOtherPublicDiaryDescriptions(user, ds, de, key));
        
    }
            
    public void searchUserRecentModiDiary(){
         pInquireResult.setDiaryList(gmService.GetRecentDiaryByWriter(10));
    }
    //----------------------------------------------------------------------
    
    private void InitComponents(){
        this.setSize(1280,780);
        this.setLocationRelativeTo(null);
        this.setTitle("生活日记管理");
        pDiary_View_Edit_Comment.addPane(pDiaryReadOrEdit, "详细内容");
        pDiary_View_Edit_Comment.addPane(pComment, "发表评论");
        this.setLayout(new BorderLayout());
        this.add(setToolBar(),BorderLayout.NORTH);
        this.add(setJSplitPane(),BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    private JSplitPane setJSplitPane(){
        
        
        
        //先创造第2、3列的splitter，第2列是查询结果，第3列是一张card根据用户点击换日记和评论的视图。
        JSplitPane split_2=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,pInquireResult,pDiary_View_Edit_Comment);
        split_2.setDividerSize(8);
        split_2.setDividerLocation(400);
        split_2.setOneTouchExpandable(true);
        
        //加入第1列，即查询面板
        JSplitPane split_1=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,pInquire,split_2);
        split_1.setDividerSize(8);
        split_1.setDividerLocation(250);
        split_1.setOneTouchExpandable(true);
        return split_1;

        
    }
   
    private JToolBar setToolBar(){
        JButton but_New=UiCommonFuctions.constructButtonInALine("新建", "images/file-new.png", 0, 100, 30);
        JButton but_Del=UiCommonFuctions.constructButtonInALine("删除", "images/delete.png", 1, 100, 30);
        JButton but_Edit=UiCommonFuctions.constructButtonInALine("编辑", "images/write.png", 2, 100, 30);
        final JButton but_inquire=UiCommonFuctions.constructButtonInALine("详细查询", "images/inquire.png", 3, 100, 30);
        JButton but_TypeMgr=UiCommonFuctions.constructButtonInALine("分类管理", "images/email_new.png", 4, 100, 30);
        JButton but_ModiPw=UiCommonFuctions.constructButtonInALine("更改密码", "images/key.png", 5, 100, 30);
        JButton but_Exit=UiCommonFuctions.constructButtonInALine("退出", "images/door.png", 6, 100, 30);
       
    	
        but_New.addActionListener(new ActionListener(){
            public void actionPerformed(final ActionEvent arg0){
                //新建时构建editingDiary对象，并设置id为－1，这样保存时可以用id来判断是插入还是更新
                Diary editingdiary=new Diary();
                editingdiary.setId(-1);
                editingdiary.setWriter(gmService.GetCurrentUser());
                pDiaryReadOrEdit.setEditingDiaryAsNew(editingdiary);
                
                selectedDiaryDescription=null;
                repaint();
                validate();
            }
        });
        but_Del.addActionListener(new ActionListener(){
            public void actionPerformed(final ActionEvent arg0){
                    if(selectedDiaryDescription==null){
                    JOptionPane.showMessageDialog(null, "请先选择一篇日记");
                    return;
                }
                    if(!selectedDiaryDescription.getWriter().equals(gmService.GetCurrentUser())){
                        JOptionPane.showMessageDialog(null, "不好意思，这不是您的日记，删不了。");
                        return;
                }
                    if(JOptionPane.showConfirmDialog(null, "是否删除标题为：\r\n“"+selectedDiaryDescription.getDiaryTitle()+
                            "”\r\n的日记？", "确认删除", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                         gmService.DeleteDairy(selectedDiaryDescription.getDiaryID());
                         pDiaryReadOrEdit.clearAllText();
                         pDiaryReadOrEdit.setDiaryEditable(false);
                         pInquireResult.removeDiaryDescription(selectedDiaryDescription);
                        selectedDiaryDescription=null;
                        repaint();
                        validate();
                    }
            }
        });
        but_Edit.addActionListener(new ActionListener(){
            public void actionPerformed(final ActionEvent arg0){
                if(selectedDiaryDescription==null){
                    JOptionPane.showMessageDialog(null, "请先选择一篇日记");
                    return;
                }
                if(!selectedDiaryDescription.getWriter().equals(gmService.GetCurrentUser())){
                        JOptionPane.showMessageDialog(null, "不好意思，这不是您的日记，编辑不了。");
                        return;
                }
                pDiaryReadOrEdit.setDiaryEditable(true);
                repaint();
                validate();
            }
        });
        but_inquire.addActionListener(new ActionListener(){
            public void actionPerformed(final ActionEvent arg0){
                if(but_inquire.getText().equals("详细查询")){
                    pInquire.setInquirePanelVisible(true);
                    but_inquire.setText("隐藏查询");
                }
                else{
                    pInquire.setInquirePanelVisible(false);
                    but_inquire.setText("详细查询");
                }
            }
        });

        but_TypeMgr.addActionListener(new ActionListener(){
            public void actionPerformed(final ActionEvent arg0){
                gmService.ShowTypeManageWindow();
                List<String> typelist=gmService.GetUserTypesList();
                pInquire.setTypeComboBox(typelist);
                pDiaryReadOrEdit.setTypeComboBox(typelist);
            }
        });

        but_ModiPw.addActionListener(new ActionListener(){
            public void actionPerformed(final ActionEvent arg0){
                gmService.ShowModiPasswordWindow();
            }
        });

        but_Exit.addActionListener(new ActionListener(){
            public void actionPerformed(final ActionEvent arg0){
                System.exit(0);
                }
            });
        JToolBar toolbar=new JToolBar();
    	toolbar.setLayout(null);
        toolbar.add(but_New);
        toolbar.add(but_Del);
        toolbar.add(but_Edit);
        toolbar.add(but_inquire);
        toolbar.add(but_TypeMgr);
        toolbar.add(but_ModiPw);
        toolbar.add(but_Exit);
        toolbar.setFloatable(true);
        toolbar.setPreferredSize(new Dimension(1028, 31));
        return toolbar;
    }
            
    public static void main(String[] args){
        new Window_MainFrame();
    }
}
