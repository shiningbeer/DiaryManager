/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainWindow;

import audio.AudioPlayer;
import audio.VoiceRecorder;
import basicDataStructure.Diary;
import basicDataStructure.DiaryMultiMedia;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;
import programManagers.I_ManagerGeneral_Service;

/**
 *@主要作用：提供一个清爽的界面显示日记详细内容，提供一个界面让用户输入日记，并通过给予的接口反馈
 * @author Administrator
 */
public class Panel_DiaryDetailReadOrEdit extends JPanel{
    private JTextArea diaryDes=new JTextArea();
    private JPanel diaryDes_Container;
    private JTextPane diaryContent=new JTextPane();
    private I_RespondingListener listener;
    private Diary editingDiary;
    private SubPanel_DiaryDesEditing diaryEdit=new SubPanel_DiaryDesEditing();
    //获取编辑面板上的按键
    private JButton but_InsertPic;
    private JButton but_RecordVoice;
    private JButton but_PlayVoice;
    private JButton but_SaveDiary;
    private JButton but_Cancel;
    private JLabel jl_PlayVoice;
    public Panel_DiaryDetailReadOrEdit(I_RespondingListener listener){
        this.listener=listener;
        diaryDes.setEditable(false);
        diaryContent.setEditable(false);
        
        //设置播放声音的标签
        jl_PlayVoice=new JLabel();
        jl_PlayVoice.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jl_PlayVoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AudioPlayer ap=new AudioPlayer();
                try{
                ap.play(editingDiary.getVoice());
                }
                catch(Exception e){return;}
            }
        });
        this.setLayout(new BorderLayout());
        diaryDes.setPreferredSize(new Dimension(400, 60)); 
        diaryEdit.setPreferredSize(new Dimension(400, 80)); 
        diaryEdit.setMinimumSize(new Dimension(0,0));
        diaryDes_Container=setdiaryDesPanel();
        diaryDes_Container.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        this.add(diaryContent,BorderLayout.CENTER);
        setDiaryEditable(false);
        
        //绑定编辑面板上的按键行为
        but_InsertPic=diaryEdit.getPictureInsertButton();
        but_InsertPic.addActionListener(new ActionListener(){
            public void actionPerformed(final ActionEvent arg0){
                File pfile=UiCommonFuctions.ChoosePictureFile();
                if(pfile==null)
                {
                    return;
                }  
                ImageIcon ficon=new ImageIcon(pfile.getPath());
                diaryContent.insertIcon(UiCommonFuctions.getScalcedImageIcon(ficon, 300));
            }
        });
        but_RecordVoice=diaryEdit.getVoiceRecordButton();
        but_RecordVoice.addActionListener(new ActionListener(){
            public void actionPerformed(final ActionEvent arg0){
               final VoiceRecorder vr=new VoiceRecorder();
               JButton b=vr.getSaveButton();
               b.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            editingDiary.setVoice(vr.save());
                            

                        }
                });
            }
        });
        but_PlayVoice=diaryEdit.getVoicePlayButton();
        but_PlayVoice.addActionListener(new ActionListener(){
            public void actionPerformed(final ActionEvent arg0){
               AudioPlayer ap=new AudioPlayer();
                try{
                ap.play(editingDiary.getVoice());
                }
                catch(Exception e){return;}
            }
        });
        but_SaveDiary=diaryEdit.getDiarySaveButton();
        but_SaveDiary.addActionListener(new ActionListener(){
            public void actionPerformed(final ActionEvent arg0){
                try{
                    assert diaryEdit.getDiaryTitle().length()<=20: "标题长度不得超过20";
                }
                catch(AssertionError e){
                    JOptionPane.showMessageDialog(null, e.getMessage());
                                        return;
                }
               StyledDocument doc=(StyledDocument)diaryContent.getStyledDocument();
               byte[] richContent=UiCommonFuctions.ConvertObjectToByteArray(doc);
               editingDiary.setDateTime(UiCommonFuctions.getCurrentDateTime());
               editingDiary.setTitle(diaryEdit.getDiaryTitle());
               editingDiary.setDiaryType(diaryEdit.getDiaryType());
               editingDiary.setVisibletType(diaryEdit.getDiaryVisibleType());
               editingDiary.setStringContent(diaryContent.getText());
               editingDiary.setRichContent(richContent);
               
               setDiaryDesText(editingDiary.getWriter(),editingDiary.getDateTime(),editingDiary.getTitle(),editingDiary.getDiaryType());
               setUnEditable();
                repaint();
                validate();
               save(editingDiary);
            }
        });
        but_Cancel=diaryEdit.getCancelButton();
        but_Cancel.addActionListener(new ActionListener(){
            public void actionPerformed(final ActionEvent arg0){
               setUnEditable();
               repaint();
               validate();
            }
        });
    }
    private void save(Diary d){
        listener.saveDiary(d);
    }
    private ImageIcon getPlayVoiceIcon(String imagepath){
        ImageIcon ico = new ImageIcon(imagepath);
        Image temp=ico.getImage().getScaledInstance(30,30,ico.getImage().SCALE_DEFAULT);
        ico=new ImageIcon(temp);
        return ico;
    }        
    public void setEditingDiaryAsNew(Diary d){
        editingDiary=d;
        clearAllText();
        setEditable();
        repaint();
        validate();
                
    }
            
    public void setTypeComboBox(List<String> types)
    {
        diaryEdit.setTypeComboBox(types);
    }
    public void setDiaryEditable(boolean f){
        if(f)
            setEditable();
        else
            setUnEditable();
    }
    private void setUnEditable(){
        this.remove(diaryEdit);
        this.add(diaryDes_Container,BorderLayout.NORTH);
        diaryContent.setEditable(false);
    }
    private void setEditable(){
        this.remove(diaryDes_Container);
        this.add(diaryEdit,BorderLayout.NORTH);
        diaryContent.setEditable(true);
    }
    private JPanel setdiaryDesPanel(){
        JPanel container= new JPanel();
        JPanel p=new JPanel();
        p.setPreferredSize(new Dimension(30, 30));
        p.setLayout(new BorderLayout());
        p.add(jl_PlayVoice,BorderLayout.CENTER);
        p.setBackground(new java.awt.Color(255, 255, 255));
        container.setLayout(new BorderLayout());
        container.add(diaryDes,BorderLayout.CENTER);
        container.add(p,BorderLayout.EAST);
        return container;
    }
            
    public void setEditingDiaryId(int id){
        editingDiary.setId(id);
    }
    public void clearAllText(){
        diaryDes.setText("");
        diaryContent.setText("");
        diaryEdit.setDiaryTitle("");
        diaryEdit.setDiaryType("");
    }
            
    
    //编辑时
    private void setDiaryDesText(String writer,String time,String title, String type){
        diaryDes.setText(writer+"于"+time+"编写\r\n"+
                "标题："+ title+"\r\n"+
                "分类："+type+"\r\n");
    }
            
    public void setEditingDiary(Diary d){
        editingDiary=d;
        setDiaryDesText(d.getWriter(),d.getDateTime(), d.getTitle(), d.getDiaryType());
        if(d.getRichContent()!=null)
        {
            Object ob=UiCommonFuctions.ConvertByteArrayToObject(d.getRichContent());
            if (ob!=null)
            {
            StyledDocument doc=(StyledDocument)ob;
            diaryContent.setStyledDocument(doc);
            repaint();
            validate();
            }
    
        }
        if(d.getVoice()==null)
            jl_PlayVoice.setIcon(getPlayVoiceIcon("images/mute.png"));
        else
            jl_PlayVoice.setIcon(getPlayVoiceIcon("images/sound.png"));
        diaryEdit.setDiaryTitle(d.getTitle());
        diaryEdit.setDiaryType(d.getDiaryType());
        diaryEdit.setDiaryVisibleType(d.getVisibleType());
        
    }
    
}
