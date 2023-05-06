package cn.edu.fzu.sm.wuweida.frame;

import cn.edu.fzu.sm.wuweida.bean.CustomerUser;
import cn.edu.fzu.sm.wuweida.dao.JdbcConfig;
import cn.edu.fzu.sm.wuweida.dao.JdbcImpl;
import cn.edu.fzu.sm.wuweida.util.FilesLooping;
import cn.edu.fzu.sm.wuweida.util.PasswordField;
import cn.edu.fzu.sm.wuweida.util.TextField;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CustomerLoginFrame extends JFrame {
    JdbcImpl jdbcImpl=new JdbcImpl();

    public CustomerLoginFrame() throws HeadlessException {
        this.setUndecorated(true);
        this.setLayout(null);
        this.setSize( 747+300,420);
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("image/logo.png").getImage());


        JPanel contentPanel=(JPanel) this.getContentPane();
        contentPanel.setBounds(0,0,300,450);
        contentPanel.setBackground(new Color(4, 15, 16));


        //幻灯片放映
        JLabel eastLabel=new JLabel();
        eastLabel.setBounds(300,0,747,420);
        eastLabel.setBackground(Color.BLACK);
        eastLabel.setOpaque(true);
        MyThreadRunnable target=new MyThreadRunnable(eastLabel);
        Thread pptThread=new Thread(target);
        pptThread.start();
        this.add(eastLabel);




        JLabel loginLabel=new JLabel();
        loginLabel.setIcon(new ImageIcon("image/loginLogo.png"));
        loginLabel.setBounds(100,40,100,100);
        loginLabel.setFont(new Font("草书",Font.ITALIC,30));
        contentPanel.add(loginLabel);



        TextField usernameText=new TextField();
        usernameText.setLabelText("用户名");
        usernameText.setBounds(50,170,200,20);
        usernameText.setOpaque(false);
        usernameText.setForeground(Color.WHITE);
        usernameText.setFont(new Font("微软雅黑",Font.PLAIN,10));
        usernameText.setBorder(new MatteBorder(0,0,1,0,Color.WHITE));

        contentPanel.add(usernameText);



        PasswordField passwordText=new PasswordField();
        passwordText.setLabelText("密码");
        passwordText.setLineColor(new Color(3, 104, 158));
        passwordText.setShowAndHide(true);
        passwordText.setBounds(50,220,200,20);
        passwordText.setOpaque(false);
        passwordText.setForeground(Color.WHITE);
        passwordText.setFont(new Font("微软雅黑",Font.PLAIN,10));
        passwordText.setBorder(new MatteBorder(0,0,1,0,Color.LIGHT_GRAY));
        contentPanel.add(passwordText);


        //注册
        JButton registerBtn=new JButton("未有账号? 点击此处注册");
        registerBtn.setContentAreaFilled(false);
        registerBtn.setBounds(75,370,150,20);
        registerBtn.setBorder(null);
        registerBtn.setForeground(Color.LIGHT_GRAY);
        registerBtn.setFont(new Font("宋体",Font.PLAIN,10));
        registerBtn.setFocusPainted(false);
        registerBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                CustomerLoginFrame.this.setCursor(Cursor.HAND_CURSOR);
                registerBtn.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                CustomerLoginFrame.this.setCursor(Cursor.DEFAULT_CURSOR);
                registerBtn.setForeground(Color.LIGHT_GRAY);
            }
        });
        contentPanel.add(registerBtn);

        //确定按钮
        JLabel okLabel=new JLabel("登录");
        okLabel.setFont(new Font("微软雅黑",Font.PLAIN,10));
        okLabel.setBounds(75,275,150,25);
        okLabel.setBackground(new Color(10, 39, 41));
        okLabel.setBorder(null);
        okLabel.setOpaque(true);
        okLabel.setHorizontalAlignment(SwingConstants.CENTER);
        okLabel.setForeground(Color.LIGHT_GRAY);
        okLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                CustomerLoginFrame.this.setCursor(Cursor.HAND_CURSOR);
                okLabel.setForeground(Color.WHITE);
                okLabel.setBackground(new Color(8, 23, 25));
                okLabel.setFont(new Font("微软雅黑",Font.BOLD,10));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                CustomerLoginFrame.this.setCursor(Cursor.DEFAULT_CURSOR);
                okLabel.setForeground(Color.LIGHT_GRAY);
                okLabel.setBackground(new Color(10, 39, 41));
                okLabel.setFont(new Font("微软雅黑",Font.PLAIN,10));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                String text1=usernameText.getText();
                String text2=passwordText.getText();
                if(text1.equals(null)||text1.equals("")){
                    JOptionPane.showMessageDialog(CustomerLoginFrame.this,"请输入用户名",null,JOptionPane.WARNING_MESSAGE);
                }else if(text2.equals(null)||text2.equals("")){
                    JOptionPane.showMessageDialog(CustomerLoginFrame.this,"请输入密码",null,JOptionPane.WARNING_MESSAGE);
                }else{
                    CustomerUser enteredUser=new CustomerUser();
                    enteredUser.setUsername(text1);
                    enteredUser.setPassword(text2);
                    if(!jdbcImpl.doUsernameExist(enteredUser)){
                        JOptionPane.showMessageDialog(CustomerLoginFrame.this,"用户名不存在",null,JOptionPane.WARNING_MESSAGE);
                    }else if (!jdbcImpl.doUserExist(enteredUser)){
                        JOptionPane.showMessageDialog(CustomerLoginFrame.this,"密码错误",null,JOptionPane.WARNING_MESSAGE);
                    }else{
                        //管理员登录
                        if(text1.equals("admin")){
                            CustomerLoginFrame.this.dispose();
                            AdminFrame adminFrame=new AdminFrame();
                        }else{
                        CustomerLoginFrame.this.dispose();
                        CustomerMainFrame customerMainFrame=new CustomerMainFrame(text1);
                        }
                    }
                }
            }
        });
        contentPanel.add(okLabel);

        //取消按钮
        JLabel cancelLabel=new JLabel("取消");
        cancelLabel.setFont(new Font("微软雅黑",Font.PLAIN,10));
        cancelLabel.setBounds(75,320,150,25);
        cancelLabel.setBackground(new Color(29, 40, 39));
        cancelLabel.setBorder(null);
        cancelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cancelLabel.setOpaque(true);
        cancelLabel.setForeground(Color.LIGHT_GRAY);
        cancelLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                CustomerLoginFrame.this.setCursor(Cursor.HAND_CURSOR);
                cancelLabel.setForeground(Color.WHITE);
                cancelLabel.setBackground(new Color(15, 29, 30));
                cancelLabel.setFont(new Font("微软雅黑",Font.BOLD,10));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                CustomerLoginFrame.this.setCursor(Cursor.DEFAULT_CURSOR);
                cancelLabel.setForeground(Color.LIGHT_GRAY);
                cancelLabel.setBackground(new Color(29, 40, 39));
                cancelLabel.setFont(new Font("微软雅黑",Font.PLAIN,10));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                CustomerLoginFrame.this.dispose();
            }
        });
        contentPanel.add(cancelLabel);



        class MoveListener implements MouseListener, MouseMotionListener {

            private Point pressedPoint;
            private Rectangle frameBounds;

            @Override
            public void mouseClicked(MouseEvent event) {
            }

            @Override
            public void mousePressed(MouseEvent event) {
                this.frameBounds = CustomerLoginFrame.this.getBounds();
                this.pressedPoint = event.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent event) {
                moveJFrame(event);
            }

            @Override
            public void mouseEntered(MouseEvent event) {
            }

            @Override
            public void mouseExited(MouseEvent event) {
            }

            @Override
            public void mouseDragged(MouseEvent event) {
                moveJFrame(event);
            }

            @Override
            public void mouseMoved(MouseEvent event) {
            }

            private void moveJFrame(MouseEvent event) {
                Point endPoint = event.getPoint();

                int xDiff = endPoint.x - pressedPoint.x;
                int yDiff = endPoint.y - pressedPoint.y;
                frameBounds.x += xDiff;
                frameBounds.y += yDiff;
                CustomerLoginFrame.this.setBounds(frameBounds);
            }

        }
        MoveListener listener=new MoveListener();
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);



        this.setVisible(true);
    }

    class MyThreadRunnable implements Runnable{
        private JLabel eastLabel_local;
        private List<File> imgFileList=new ArrayList<>();
        private File imgFolder;
        private ImageIcon imageIcon;
        private int loopingPtr;
        private int listSize;

        public MyThreadRunnable(JLabel eastLabel) {
            eastLabel_local=eastLabel;
            imgFolder=new File("bgImg");
            FilesLooping.getFilesList(imgFolder,imgFileList);
            loopingPtr=0;
            listSize=imgFileList.size();
        }

        @Override
        public void run() {
            while (true){

                imageIcon=new ImageIcon(imgFileList.get((loopingPtr++)%(listSize)).getPath());
                eastLabel_local.setIcon(imageIcon);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
