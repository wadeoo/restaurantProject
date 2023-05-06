package cn.edu.fzu.sm.wuweida.frame;

import cn.edu.fzu.sm.wuweida.bean.Order;
import cn.edu.fzu.sm.wuweida.dao.JdbcImpl;
import cn.edu.fzu.sm.wuweida.util.ModernScrollBarUI;
import cn.edu.fzu.sm.wuweida.util.Spinner;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static java.awt.Cursor.*;

public class CartDialog extends JDialog {

    private JdbcImpl jdbcImpl = new JdbcImpl();

    public CartDialog(HashMap<String, Integer> chosenFoodHashMap, String username) {
        this.setAlwaysOnTop(true);
        this.setUndecorated(true);
        this.setSize(300, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        JPanel contentPanelForCartDialog = (JPanel) this.getContentPane();
        contentPanelForCartDialog.setBackground(new Color(20, 28, 33));


        //左上角标签
        JLabel jLabel = new JLabel("  您的购物车");
        jLabel.setBounds(0, 0, 100, 50);
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel.setVerticalAlignment(SwingConstants.CENTER);
        jLabel.setForeground(Color.LIGHT_GRAY);
        jLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        this.add(jLabel);

        //右上角退出键
        JLabel closeLabel = new JLabel("╳");
        closeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        closeLabel.setVerticalAlignment(SwingConstants.CENTER);
        closeLabel.setForeground(Color.GRAY);
        closeLabel.setBounds(250, 0, 50, 50);
        closeLabel.setOpaque(true);
        closeLabel.setBackground(new Color(20, 28, 33));
        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                CartDialog.this.dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                CartDialog.this.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
                closeLabel.setBackground(new Color(133, 37, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                CartDialog.this.setCursor(Cursor.getPredefinedCursor(DEFAULT_CURSOR));
                closeLabel.setBackground(new Color(20, 28, 34));
            }
        });
        this.add(closeLabel);


        scrollPanelProcess(chosenFoodHashMap);

        southPanelProcess(chosenFoodHashMap, username);

        MoveListener moveListener = new MoveListener();
        this.addMouseListener(moveListener);
        this.addMouseMotionListener(moveListener);


        this.setVisible(true);
    }


    //滚动面板内容创建
    private void scrollPanelProcess(HashMap<String, Integer> chosenFoodHashMap) {
        //滚动面板
        JScrollPane scrollPanel = new JScrollPane();
        JScrollBar customScrollBar = new JScrollBar();
        customScrollBar.setUI(new ModernScrollBarUI());
        customScrollBar.setPreferredSize(new Dimension(8, 8));
        customScrollBar.setForeground(new Color(26, 36, 43));
        customScrollBar.setBackground(new Color(14, 25, 32));
        customScrollBar.setOrientation(Adjustable.VERTICAL);
        scrollPanel.setVerticalScrollBar(customScrollBar);
        scrollPanel.setPreferredSize(new Dimension(300, 400));
        scrollPanel.setBounds(0, 50, 300, 400);
        scrollPanel.setBackground(new Color(26, 36, 43));
        scrollPanel.getHorizontalScrollBar().setOpaque(false);
        scrollPanel.setBorder(null);
        scrollPanel.remove(scrollPanel.getHorizontalScrollBar());
        this.add(scrollPanel);


        //滚动面板的内容面板
        JPanel contentPanelForScroll = new JPanel();
        contentPanelForScroll.setPreferredSize(new Dimension(300, 400));
        contentPanelForScroll.setBackground(new Color(20, 28, 33));
        contentPanelForScroll.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));


        //列出购物车清单
        int i = 1;
        if (!chosenFoodHashMap.isEmpty()) {
            for (String key : chosenFoodHashMap.keySet()) {

                //内容面板里 每个菜品所属的元面板
                JPanel foodPanel = new JPanel();
                foodPanel.setPreferredSize(new Dimension(300, 50));
                foodPanel.setBackground(new Color(26, 36, 43));
                foodPanel.setBorder(new MatteBorder(0, 0, 1, 0, new Color(134, 134, 134)));
                foodPanel.setLayout(null);

                //序号
                JLabel numberLabel = new JLabel((i++) + ".");
                numberLabel.setForeground(Color.LIGHT_GRAY);
                numberLabel.setBounds(10, 0, 20, 50);
                foodPanel.add(numberLabel);

                //菜式名称
                JLabel nameLabel = new JLabel(key + "");
                nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
                nameLabel.setFont(new Font("楷体", Font.PLAIN, 10));
                nameLabel.setForeground(Color.LIGHT_GRAY);
                nameLabel.setBounds(60, 0, 40, 50);
                foodPanel.add(nameLabel);

                //菜式价格
                JLabel priceLabel = new JLabel(jdbcImpl.getFoodPrice(key) + "人民币");
                priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
                priceLabel.setFont(new Font("楷体", Font.PLAIN, 10));
                priceLabel.setForeground(Color.LIGHT_GRAY);
                priceLabel.setBounds(120, 0, 80, 50);
                foodPanel.add(priceLabel);


                //数量选择器
                Spinner spinner = new Spinner();
                spinner.setBounds(200, 5, 40, 40);
                spinner.setValue(chosenFoodHashMap.get(key));
                spinner.setLabelText("数量:");
                foodPanel.add(spinner);
                spinner.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        Spinner thisPinner = (Spinner) e.getSource();
                        if ((Integer) thisPinner.getValue() == -1) {
                            thisPinner.setValue(0);
                        }
                        if ((Integer) thisPinner.getValue() == 0) {
                            chosenFoodHashMap.remove(key);
                            CartDialog.this.remove(scrollPanel);
                            scrollPanelProcess(chosenFoodHashMap);
                        } else {
                            chosenFoodHashMap.put(key, (Integer) thisPinner.getValue());
                        }
                    }
                });


                //删除菜品
                JLabel deleteLabel = new JLabel("╳");
                deleteLabel.setHorizontalAlignment(SwingConstants.CENTER);
                deleteLabel.setVerticalAlignment(SwingConstants.CENTER);
                deleteLabel.setForeground(Color.GRAY);
                deleteLabel.setBounds(260, 10, 30, 30);
                deleteLabel.setOpaque(true);
                deleteLabel.setBackground(new Color(20, 28, 33));
                deleteLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        super.mouseClicked(e);
                        chosenFoodHashMap.remove(key);
                        CartDialog.this.remove(scrollPanel);
                        scrollPanelProcess(chosenFoodHashMap);
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        super.mouseEntered(e);
                        CartDialog.this.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
                        deleteLabel.setBackground(new Color(32, 44, 54));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        super.mouseExited(e);
                        CartDialog.this.setCursor(Cursor.getPredefinedCursor(DEFAULT_CURSOR));
                        deleteLabel.setBackground(new Color(20, 28, 34));
                    }
                });
                foodPanel.add(deleteLabel);


                //元面板触动变色
                foodPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        super.mouseEntered(e);
                        foodPanel.setBackground(new Color(39, 53, 64));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        super.mouseExited(e);
                        foodPanel.setBackground(new Color(26, 36, 43));
                    }
                });
                contentPanelForScroll.add(foodPanel);
            }
        }
        scrollPanel.setViewportView(contentPanelForScroll);
    }

    private void southPanelProcess(HashMap<String, Integer> chosenFoodHashMap, String username) {
        JPanel southPanel = new JPanel();
        southPanel.setBounds(0, 450, 300, 150);
        southPanel.setBackground(new Color(20, 30, 34));

        double total = 0;
        for (String key : chosenFoodHashMap.keySet()) {
            total += jdbcImpl.getFoodPrice(key) * chosenFoodHashMap.get(key);
        }

        //总价标签
        JLabel totalLabel = new JLabel("总价: " + total);
        totalLabel.setFont(new Font("宋体", Font.PLAIN, 20));
        totalLabel.setBounds(0, 0, 200, 150);
        totalLabel.setBackground(new Color(14, 21, 24));
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalLabel.setVerticalAlignment(SwingConstants.CENTER);
        totalLabel.setOpaque(true);
        southPanel.add(totalLabel);

        //订单确认标签
        JLabel confirmLabel = new JLabel("确认订单");
        confirmLabel.setFont(new Font("宋体", Font.PLAIN, 20));
        confirmLabel.setBounds(200, 0, 100, 150);
        confirmLabel.setBackground(new Color(14, 21, 24));
        confirmLabel.setHorizontalAlignment(SwingConstants.CENTER);
        confirmLabel.setOpaque(true);
        southPanel.add(confirmLabel);
        confirmLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mousePressed(e);
                //订单确认处理
                SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                if (!chosenFoodHashMap.isEmpty()) {
                    for (String key : chosenFoodHashMap.keySet()) {
                        Order newOrder = new Order();
                        newOrder.setFoodId(jdbcImpl.getFoodId(key));
                        try {
                            newOrder.setOrderTime(new java.sql.Date(timeFormat.parse(timeFormat.format(Calendar.getInstance().getTime())).getTime()));
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                        newOrder.setQuantity(chosenFoodHashMap.get(key));
                        newOrder.setUsername(username);
                        jdbcImpl.addOrder(newOrder);
                        chosenFoodHashMap.clear();

                        JPanel contentPanelOfCartDialog = (JPanel) CartDialog.this.getContentPane();
                        contentPanelOfCartDialog.remove(2);
                        scrollPanelProcess(chosenFoodHashMap);
                    }
                } else {
                    JOptionPane.showMessageDialog(CartDialog.this, "购物车为空", null, JOptionPane.WARNING_MESSAGE);
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                confirmLabel.setBackground(new Color(26, 40, 45));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                confirmLabel.setBackground(new Color(14, 21, 24));
            }
        });


        this.add(southPanel);
    }


    // 为了实现窗口拖拽
    class MoveListener extends MouseAdapter {
        private Point pressedPoint;
        private Rectangle frameBounds;

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            this.frameBounds = CartDialog.this.getBounds();
            this.pressedPoint = e.getPoint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            moveJFrame(e);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            super.mouseDragged(e);
            moveJFrame(e);
        }

        private void moveJFrame(MouseEvent event) {
            Point endPoint = event.getPoint();

            int xDiff = endPoint.x - pressedPoint.x;
            int yDiff = endPoint.y - pressedPoint.y;
            frameBounds.x += xDiff;
            frameBounds.y += yDiff;
            CartDialog.this.setBounds(frameBounds);
        }
    }
}
