/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainWindow;

import basicDataStructure.Comment;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

/**
 *@主要作用：提供一个清爽的界面让用户发表评论，并收集，通过给予的接口反馈
 * @author Administrator
 */
public class Panel_Comment extends  JPanel{
    private JTextArea myComment=new JTextArea();
    
    private JPanel commentList=new JPanel();
    
    private JButton but_Comment=new JButton("发表评论");
    private JButton but_Edit=new JButton("编辑评论");
    private JButton but_Del=new JButton("删除评论");
    private I_RespondingListener listener;
    private String currentUser;
    public Panel_Comment(){
         Init();
    }
    public Panel_Comment(I_RespondingListener listener){
        this.listener=listener;
        Init();
    }
    private void Init(){
        this.setLayout(new BorderLayout());
        this.add(setPanelTop(),BorderLayout.NORTH);        
        this.add(setPanelCenter(),BorderLayout.CENTER);        
        commentList.setBackground(new java.awt.Color(255, 255, 255));
        commentList.setLayout(new FlowLayout());
        
        but_Comment.addActionListener(new ActionListener(){
            public void actionPerformed(final ActionEvent arg0){
                Comment c=getComment();
                listener.addComment(c);
                SubPanel_CommentTextArea ct=Comment_To_Panel(c);
                commentList.add(ct,0);
                myComment.setText("");
                repaint();
                validate();
            }
        });
        
        
        setEnabled(false);
    }
    public void setCurrentUser(String user){
        currentUser=user;
    }
    private Comment getComment(){
        Comment c=new Comment();
        c.setWriter(currentUser);
        c.setDateTime(UiCommonFuctions.getCurrentDateTime());
        c.setComment(myComment.getText());
        return c;
    }
    public void setEnabled(boolean f){
        but_Comment.setEnabled(f);
        but_Del.setEnabled(f);
        but_Edit.setEnabled(f);
    }
    private SubPanel_CommentTextArea Comment_To_Panel(Comment c){
         SubPanel_CommentTextArea ct=new SubPanel_CommentTextArea();
        ct.setWriter(c.getWriter());
        ct.setTime(c.getDateTime());
        ct.setComment(c.getComment());
        ct.showText();
        return ct;
    }
    public void setCommentList(List<Comment> lc){
        commentList.removeAll();
        for(Comment c:lc){
            SubPanel_CommentTextArea ct= Comment_To_Panel(c);
            commentList.add(ct);
        }
        repaint();
        validate();
    }
    private JPanel setPanelCenter(){
        JPanel p=new JPanel();
        p.setLayout(new BorderLayout());
        commentList.setPreferredSize(new Dimension(598, 400)); 
        
        JScrollPane j=new JScrollPane(commentList);
        j.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        p.add(j,BorderLayout.WEST);
        JPanel pp=new JPanel();//布局用
        pp.setBackground(new java.awt.Color(255, 255, 255));
        p.add(pp,BorderLayout.CENTER);
        return p;
    }
    private JPanel setPanelTop(){
        JPanel pantop=new JPanel();
        
        pantop.setLayout(new BorderLayout());       
        pantop.add(myComment,BorderLayout.CENTER);
        JPanel pantop_bottom=new JPanel();
        JPanel panButton=new JPanel();
        pantop_bottom.setPreferredSize(new Dimension(300, 30));
        pantop_bottom.setLayout(new BorderLayout());
        panButton.setPreferredSize(new Dimension(280,30));
        panButton.setLayout(new FlowLayout());
        panButton.setMinimumSize(new Dimension(0, 0));
        panButton.add(but_Comment);
        panButton.add(but_Edit);
        panButton.add(but_Del);
        pantop_bottom.add(panButton,BorderLayout.WEST);
        pantop_bottom.add(new JPanel(),BorderLayout.CENTER);
        
        pantop.add(pantop_bottom,BorderLayout.SOUTH);
        pantop.setPreferredSize(new Dimension(300, 90));
        return pantop;
    }
    public static void main(String[] args) {
        JFrame fm=new JFrame();
        fm.setSize(700, 1000);
        fm.add(new Panel_Comment());
        fm.setVisible(true);

    }
}
