/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainWindow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *@主要作用：提供一个方便的界面让用户输入，并收集用户输入的查询条件，通过给予的接口反馈
 * @author Administrator
 */
public class Panel_CombinedInquiry extends JPanel{
    private SubPanel_Inquiry pInquire=new SubPanel_Inquiry();
    private I_RespondingListener listener;
    private JPanel p_inquire_Container=new JPanel();
    
    public Panel_CombinedInquiry(I_RespondingListener listener){
        //设置可调整大小的查询面板
        setInquirePanel();
        this.listener=listener;
        pInquire.setListener(listener);
        //设置有滚动条的树
        JScrollPane scrol_tree=new JScrollPane();
        scrol_tree.setViewportView(setTimeTree());
        
        this.setLayout(new BorderLayout() );
        this.add(scrol_tree,BorderLayout.CENTER);
    }
    public void setInquirePanelVisible(boolean f){
        if(f){
             this.remove(p_inquire_Container);
             this.add(p_inquire_Container,BorderLayout.NORTH);
             repaint();
            validate();
        }
        else{

            this.remove(p_inquire_Container);
            repaint();
            validate();
        }
    }
    private void setInquirePanel(){
        p_inquire_Container.setLayout(new BorderLayout());
        p_inquire_Container.add(pInquire,BorderLayout.WEST);
        pInquire.setMinimumSize(new Dimension(0,0));
        p_inquire_Container.add(new JPanel(),BorderLayout.CENTER);
    }
            
    public void setTypeComboBox(List<String> types){
        pInquire.setTypeComboBox(types);
    }
    private JTree setTimeTree(){
        final DefaultMutableTreeNode root = new javax.swing.tree.DefaultMutableTreeNode("快速查询"); 
        final JTree jtree_YearMonth = new JTree(root);
        DefaultMutableTreeNode myrecent=new DefaultMutableTreeNode("我最近修改");
        root.add(myrecent);
        DefaultMutableTreeNode mycomment=new DefaultMutableTreeNode("我最近评论");
        root.add(mycomment);
        DefaultMutableTreeNode otherpublic=new DefaultMutableTreeNode("他人最近公开");
        root.add(otherpublic);

        Date da=new Date();
        @SuppressWarnings("deprecation")
        int y=da.getYear()+1900;
        for(int i=0;i<5;i++)
        {
                String name=String.valueOf(y-i);
                name+="年";
                DefaultMutableTreeNode fchild=new DefaultMutableTreeNode(name);
                for(int j=12;j>0;j--)
                {
                        String name2=String.valueOf(j);
                        if(name2.length()<2)
                                name2="0"+name2;
                        name2+="月";
                        DefaultMutableTreeNode schild=new DefaultMutableTreeNode(name2);
                        fchild.add(schild);
                }
                root.add(fchild);			
        }
        jtree_YearMonth.expandRow(0);
        jtree_YearMonth.addTreeSelectionListener(new TreeSelectionListener(){

                @Override
                public void valueChanged(TreeSelectionEvent e) {
                        // TODO 自动生成的方法存根
                        DefaultMutableTreeNode node=(DefaultMutableTreeNode)jtree_YearMonth.getLastSelectedPathComponent();
                        String nodeText=(String)node.getUserObject();
                            if(nodeText.equals("我最近修改"))
                            {
                                listener.searchUserRecentModiDiary(); 
                                return;
                            }
                            if(nodeText.equals("我最近评论"))
                            {
                                listener.searchUserRecentCommentDiary();
                                return;
                            }
                            if(nodeText.equals("他人最近公开"))
                            {
                                listener.searchOtherRecentPublic();
                                return;
                            }
                        if(node.isRoot()||node.getDepth()==1)
                        {

                            return;
                        }
                        String month=(String)node.getUserObject();
                        DefaultMutableTreeNode fathernode=(DefaultMutableTreeNode) node.getParent();				
                        String year=(String)fathernode.getUserObject();
                        year=year.substring(0, 4);			
                        month=month.substring(0, 2);
                        String m=year+"-"+month+"-01";
                        listener.searchUserDiaryByMonth(m);
                }

        });
        return jtree_YearMonth;
    }
}
