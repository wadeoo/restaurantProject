package cn.edu.fzu.sm.wuweida.frame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel {
    private Image image;

    public ImagePanel(String imgUrl) {
        try {
            image= ImageIO.read(new File(imgUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image,0,0,450,300,null);
    }
}
