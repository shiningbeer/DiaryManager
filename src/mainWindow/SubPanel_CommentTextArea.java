/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainWindow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Administrator
 */
public class SubPanel_CommentTextArea extends JScrollPane{
    private JTextArea clist=new JTextArea();
    private String writer="";
    private String time="";
    private String comment="";
    public SubPanel_CommentTextArea()
    {
        showText();
        this.setPreferredSize(new Dimension(590, 80));
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.setViewportView(clist);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        clist.setLineWrap(true);
        clist.setEditable(false);
        
    }
    public void setWriter(String w){
        writer=w;
    }
    public String getWriter(){
        return writer;
    }
    public void setTime(String w){
        time=w;
    }
    public String getTime(){
        return time;
    }
    public void setComment(String w){
        comment=w;
    }
    public String getComment(){
        return comment;
    }
    public void showText()
    {
        clist.setText("用户："+writer+"   于    "+time+"    评论称：\r\n"+comment);
    }
    public static void main(String[] args) {
        JFrame fm=new JFrame();
        fm.setLayout(new FlowLayout());
        fm.setSize(600, 800);
        fm.setPreferredSize(new Dimension(600,800));
        SubPanel_CommentTextArea ds=new SubPanel_CommentTextArea();                
        fm.add(ds);
        fm.setVisible(true);


    }
}
