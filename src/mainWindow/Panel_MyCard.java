/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainWindow;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 *@主要作用：可添加面板及相应选择按钮，提供在同一容器中不同面板的转换功能。
 * @author Administrator
 */
public class Panel_MyCard extends JPanel{
     private JToolBar toolbar=new JToolBar();
     private CardLayout card =new CardLayout();
     private JPanel cardPane=new JPanel();
     private int num=0;
    public Panel_MyCard(){
        this.setLayout(new BorderLayout());
        toolbar.setPreferredSize(new Dimension(480, 25)); 
        toolbar.setLayout(null);
        this.add(toolbar,BorderLayout.NORTH);
        this.add(cardPane,BorderLayout.CENTER);
        cardPane.setLayout(card);;
    }
    public void ShowFirstPanel(){
        card.first(cardPane);
    }
    public void addPane(JPanel pane, final String paneName){
        num++;
        JButton newB=new JButton(paneName);
        newB.setBounds(70*(num-1),0,70, 25);
        newB.addActionListener(new ActionListener(){
            public void actionPerformed(final ActionEvent arg0){
                card.show(cardPane, paneName);
                }
            });
        toolbar.add(newB);
        cardPane.add(pane,paneName);
        card.show(cardPane, paneName);
        card.next(cardPane);
        
    }
    
}
