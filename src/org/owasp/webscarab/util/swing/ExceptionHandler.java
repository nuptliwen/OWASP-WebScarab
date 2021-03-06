/*
 * ExceptionHandler.java
 *
 * Created on 02 June 2005, 02:27
 */

package org.owasp.webscarab.util.swing;

import java.awt.Component;
import java.awt.Frame;

import java.io.PrintStream;

/**
 *
 * @author  rogan
 */
public class ExceptionHandler extends javax.swing.JDialog {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 7592992184923087071L;
	private static Frame _parent = null;
    private static boolean _disabled = false;
    
    /** Creates new form ExceptionHandler */
    public ExceptionHandler() {
        super(_parent, true);
        initComponents();
        getRootPane().setDefaultButton(okButton);
    }
    
    public static void setParentComponent(Component component) {
        _parent = null;
        while (component != null && component.getParent() != null) 
            component = component.getParent();
        if (component != null && component instanceof Frame) 
            _parent = (Frame) component;
    }
    
    public void handle(Throwable t) {
        System.setProperty("sun.awt.exception.handler", "");
        t.printStackTrace();
        
        if (_disabled) 
            return;
        DocumentOutputStream dos = new DocumentOutputStream();
        t.printStackTrace(new PrintStream(dos));
        exceptionTextArea.setDocument(dos.getDocument());
        
        setVisible(true);
        
        if (!disableCheckBox.isSelected()) {
            System.setProperty("sun.awt.exception.handler", this.getClass().getName());
        } else {
            _disabled = true;
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jScrollPane1 = new javax.swing.JScrollPane();
        exceptionTextArea = new javax.swing.JTextArea();
        disableCheckBox = new javax.swing.JCheckBox();
        okButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(600, 300));
        exceptionTextArea.setBackground(new java.awt.Color(204, 204, 204));
        exceptionTextArea.setEditable(false);
        jScrollPane1.setViewportView(exceptionTextArea);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        disableCheckBox.setMnemonic('S');
        disableCheckBox.setText("Stop displaying exceptions");
        getContentPane().add(disableCheckBox, java.awt.BorderLayout.NORTH);

        okButton.setText("Ok");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        getContentPane().add(okButton, java.awt.BorderLayout.SOUTH);

        pack();
    }//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        dispose();
    }//GEN-LAST:event_okButtonActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        System.setProperty("sun.awt.exception.handler", ExceptionHandler.class.getName());
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                throw new RuntimeException("blah");
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox disableCheckBox;
    private javax.swing.JTextArea exceptionTextArea;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables
    
}
