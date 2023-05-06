package cn.edu.fzu.sm.wuweida.util;

import javax.swing.*;
import java.awt.*;

public class spinnerMain extends JFrame {
    public spinnerMain() {
        this.setLocationRelativeTo(null);
        this.setSize(500,500);
        this.setLayout(null);

        Spinner spinner=new Spinner();
        spinner.setBounds(0,0,100,80);
        spinner.setBorder(null);
        spinner.setOpaque(true);
        this.add(spinner);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new spinnerMain();

    }
}
