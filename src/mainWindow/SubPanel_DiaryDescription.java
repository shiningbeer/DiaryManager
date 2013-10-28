/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainWindow;

import basicDataStructure.DiaryDescription;
import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class SubPanel_DiaryDescription extends javax.swing.JPanel {

    
    private I_RespondingListener listener;
    private int diaryID;
    
    public void setListeners(I_RespondingListener Listener){
        this.listener=Listener;
    }
    public void setDiaryTitle(String title){
        jL_title.setText(title);
        
    }
    public void setDiaryID(int id)
    {
        diaryID=id;
    }
    public int getDiaryID()
    {
        return diaryID;
    }
    public void setWriter(String writer)
    {
        jl_writer.setText(writer);
    }
    public String getWriter()
    {
        return jl_writer.getText();
    }
    public String getDiaryTitle(){
        return jL_title.getText();
    }
    public void setDiaryTime(String t){
        jL_time.setText(t);
    }
    public String getDiaryTime(){
        return jL_time.getText();
    }
    public void setDiaryStringContent(String con){
             if(con.length()>30)
                 con=con.substring(0,28)+"...";
        jL_content.setText(con);
    }
    public String getDiaryStringContent(){
        return jL_content.getText();
    }
    public void setDiaryType(String type){
        jL_type.setText(type);
    }
    public String getDiaryType(){
        return jL_type.getText();
    }
            
    /**
     * Creates new form DiaryPanel
     */    
    public SubPanel_DiaryDescription() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jL_time = new javax.swing.JLabel();
        jL_title = new javax.swing.JLabel();
        jL_type = new javax.swing.JLabel();
        jL_content = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jl_writer = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
        });
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                formFocusLost(evt);
            }
        });

        jL_time.setFont(new java.awt.Font("宋体", 2, 12)); // NOI18N
        jL_time.setText("2013-10-19 08:33:03");

        jL_title.setFont(new java.awt.Font("仿宋", 1, 12)); // NOI18N
        jL_title.setText("这是一个标题");

        jL_type.setFont(new java.awt.Font("宋体", 1, 12)); // NOI18N
        jL_type.setForeground(new java.awt.Color(102, 0, 102));
        jL_type.setText("分类名");

        jL_content.setText("这是我的文本内容，是不是应该多写一点，让他可以见到边框上呗...");

        jLabel1.setFont(new java.awt.Font("宋体", 2, 12)); // NOI18N
        jLabel1.setText("作者：");

        jl_writer.setFont(new java.awt.Font("宋体", 1, 12)); // NOI18N
        jl_writer.setForeground(new java.awt.Color(102, 102, 102));
        jl_writer.setText("wave");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jl_writer, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jL_time, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jL_type, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jL_content, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                    .addComponent(jL_title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jL_type)
                        .addComponent(jL_time))
                    .addComponent(jl_writer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jL_title)
                .addGap(0, 0, 0)
                .addComponent(jL_content, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        if(this.hasFocus())
            return;
        setBackground(new java.awt.Color(204, 255, 204));
    }//GEN-LAST:event_formMouseEntered

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        if(this.hasFocus())
            return;
        setBackground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_formMouseExited

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        this.grabFocus();
        listener.setWhichElementIsSelected(this);
    }//GEN-LAST:event_formMouseClicked

    private void formFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusLost
          setBackground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_formFocusLost

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
       setBackground(new java.awt.Color(153, 204, 255));
    }//GEN-LAST:event_formFocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jL_content;
    private javax.swing.JLabel jL_time;
    private javax.swing.JLabel jL_title;
    private javax.swing.JLabel jL_type;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jl_writer;
    // End of variables declaration//GEN-END:variables
}
