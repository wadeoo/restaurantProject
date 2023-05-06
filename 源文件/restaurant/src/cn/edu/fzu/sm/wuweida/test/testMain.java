package cn.edu.fzu.sm.wuweida.test;

import javax.swing.*;
import java.awt.*;

public class testMain {


    static class Frame extends JFrame {
        public Frame() throws HeadlessException {
            setSize(300, 300);
            setLocationRelativeTo(null);
            TextField textField = new TextField();
            textField.setBounds(0, 0, 200, 50);
            textField.setPreferredSize(new Dimension(200,50));
            textField.setLabelText("username");


            PasswordField passwordField = new PasswordField();
            passwordField.setBounds(0, 100, 200, 50);
            passwordField.setPreferredSize(new Dimension(200,50));
            passwordField.setLabelText("password");
            passwordField.setShowAndHide(true);

            JPanel jPanel = new JPanel();
            jPanel.setBounds(0,0,300,300);
            add(jPanel);

            jPanel.add(textField);
            jPanel.add(passwordField);
        }
    }

    public static void main(String[] args) {
        Frame frame = new Frame();
        frame.setVisible(true);
    }
}
