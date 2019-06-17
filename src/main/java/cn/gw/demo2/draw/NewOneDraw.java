package cn.gw.demo2.draw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NewOneDraw {

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.setSize(300, 300);//里面的宽和高任选数据
        jFrame.setTitle("gw");//标题
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//表示能够叉掉
        jFrame.setLocationRelativeTo(null);//表示画的东西是相对窗体的，这样就是画在窗体上了
        jFrame.setLayout(new FlowLayout());//称为流式布局，这样我们在画图板上设按钮的时候就可以自动从左到右从上到下的排列，而不会凌乱

        JButton jButton = new JButton("按");
        jFrame.add(jButton);

        //窗体可视化
        jFrame.setVisible(true);
        //获取窗体上的画笔对象
        Graphics graphics = jFrame.getGraphics();
        DrawListener drawListener = new DrawListener();
        jButton.addActionListener(drawListener);
        drawListener.setG(graphics);//这是在监听器中写的方法

    }

    static class DrawListener implements MouseListener, ActionListener {
        int x1=1, x2=200, y1=3, y2=250;
        private Graphics g;

        //这就是在DrawFrame中出现的setG方法，就是把MyFrame中的g传给监听器中的g，在监听器中实现功能
        public void setG(Graphics arg) {
            g = arg;
        }

        String shapeType = "";

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("按钮");
            //获取动作的事件动作命令
            String action = e.getActionCommand();
            shapeType = action;
            g.setColor(Color.YELLOW);
            g.drawLine(x1, y1, x2, y2);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("点击");
        }

        //鼠标按下
        @Override
        public void mousePressed(MouseEvent e) {
            System.out.println("按下");
            x1 = e.getX();
            y1 = e.getY();
        }

        //鼠标释放
        @Override
        public void mouseReleased(MouseEvent e) {
            System.out.println("释放");
            x2 = e.getX();
            y2 = e.getY();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            System.out.println("进入");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            System.out.println("离开");
        }
    }
}
