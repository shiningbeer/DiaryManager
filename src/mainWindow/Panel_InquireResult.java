/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainWindow;

import basicDataStructure.DiaryDescription;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *@主要作用：提供一个漂亮的界面展示用户的查询结果，感应用户的点击选择操作，通过给予的接口反馈
 * @author Administrator
 */
public class Panel_InquireResult extends JPanel{
    private I_RespondingListener listener;
    public Panel_InquireResult(I_RespondingListener listener){
        this.listener=listener;
        setBackground(new java.awt.Color(255, 255, 255));
        this.setLayout(new FlowLayout());        
        this.setPreferredSize(new Dimension(300, 410));
        SubPanel_DiaryDescription ddd=new SubPanel_DiaryDescription();
        ddd.setMinimumSize(new Dimension(0, 0));
        this.add(ddd);
        
    }
    public void removeDiaryDescription(SubPanel_DiaryDescription pdd){
        
        this.remove(pdd);
        
    }
    public void addOneDiaryDescriptionAtTop(DiaryDescription dd){
        SubPanel_DiaryDescription de=changeDiaryToPanel(dd);
        this.add(de,0);
        repaint();
        validate();
    }
   private SubPanel_DiaryDescription changeDiaryToPanel(DiaryDescription dd){
       SubPanel_DiaryDescription de=new SubPanel_DiaryDescription();
             de.setDiaryID(dd.getId());
             de.setDiaryTime(dd.getDateTime());
             de.setDiaryTitle(dd.getTitle());
             de.setDiaryType(dd.getDiaryType());
             de.setWriter(dd.getWriter());
             de.setDiaryStringContent(dd.getStringContent());
             de.setListeners(listener);
             de.setMinimumSize(new Dimension(0,0));
             return de;
   }
    public void setDiaryList(List<DiaryDescription> ldd){
        this.removeAll();
        Dimension d=new Dimension(500, 410);
        if(ldd.size()>9)
        {
            d=new Dimension(500, ldd.size()*62);
        }
        
        this.setPreferredSize(d);
        for(DiaryDescription dd:ldd)
        {
             SubPanel_DiaryDescription de=changeDiaryToPanel(dd);
             this.add(de);
        }
        repaint();
        validate();
        
    }
    
}
