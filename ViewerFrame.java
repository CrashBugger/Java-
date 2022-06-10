package viewframe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewerFrame extends JFrame {

    private int width = 700;
    private int height = 700;
    private MyCanvas canvas;//画布
    private ActionListener action;//监听对象
    private Service service;//业务处理类
    //xy坐标
    private int X = width / 2 - 100;
    private int Y = height / 2 - 100;
    private double recWidth = 200;//矩形的宽或者圆形的半径
    private double recHeight = 100;//矩形的高
    //颜色状态
    private Color color = Color.red;
    //各种形状初始值
    private int shape = 1;//当前形状


    void init() {
        service = new Service();
        //监听到按钮操作后转交到service执行
        this.action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                service.menuDo(ViewerFrame.this, actionEvent.getActionCommand());
            }
        };
        //设置标题
        this.setTitle("画图程序");
        this.setPreferredSize(new Dimension(width, height));
        //工具栏,放置各种操作的按钮
        JPanel toolBar = createToolPanel();
        this.add(toolBar, BorderLayout.NORTH);
        //滑动栏，其内是画板
        canvas = new MyCanvas(this);
        //设置大小
        canvas.setPreferredSize(new Dimension(800, 800));
        this.add(new JScrollPane(canvas), BorderLayout.CENTER);
        //设置可见并指定空间塞满
        this.pack();
        this.setVisible(true);
    }

    /**
     * @return 创建按钮菜单
     */
    JPanel createToolPanel() {
        //创建一个JPanel
        JPanel panel = new JPanel();
        //创建一个标题为工具的工具栏
        JToolBar toolBar = new JToolBar("工具");
        //不可拖动
        toolBar.setFloatable(false);
        //布局方式
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        //工具数组
        String[] toolArr = {"矩形", "圆形",
                "扇形", "红色", "绿色", "蓝色", "变大", "变小", "左移", "右移", "上移", "下移"};
        int count = 0;
        for (int i = 0; i < toolArr.length; i++) {
            JButton button = new JButton(toolArr[i]);
            button.addActionListener(this.action);
            //将button加到工具栏中
            toolBar.add(button);
            count++;
            //每三个隔一个空格
            if (count % 3 == 0 && i != toolArr.length - 1) {
                toolBar.addSeparator(new Dimension(10, 10));
            }
        }
        panel.add(toolBar);
        //返回
        return panel;
    }

    /**
     * 业务处理类,处理各种按钮操作
     */
    class Service {//形状选择范围
        private final int RECTANGLE = 1;
        private final int CIRCLE = 2;
        private final int SECTOR = 3;

        /**
         * @param frame 当前窗体
         * @param cmd   按钮具体信息
         */
        public void menuDo(ViewerFrame frame, String cmd) {
            //判断是哪个操作
            if (cmd.equals("矩形")) {
                frame.shape = RECTANGLE;
            } else if (cmd.equals("圆形")) {
                frame.shape = CIRCLE;
            } else if (cmd.equals("扇形")) {
                frame.shape = SECTOR;
            } else if (cmd.equals("红色")) {
                frame.color = Color.red;
            } else if (cmd.equals("绿色")) {
                frame.color = Color.green;
            } else if (cmd.equals("蓝色")) {
                frame.color = Color.blue;
            } else if (cmd.equals("变小")) {
                frame.recHeight *= 0.75;
                frame.recWidth *= 0.75;
            } else if (cmd.equals("变大")) {
                frame.recWidth *= 1.25;
                frame.recHeight *= 1.25;
            } else if (cmd.equals("左移")) {
                frame.X -= 10;
            } else if (cmd.equals("右移")) {
                frame.X += 10;
            } else if (cmd.equals("上移")) {
                frame.Y -= 10;
            } else if (cmd.equals("下移")) {
                frame.Y += 10;
            }
            //更新画布
            frame.canvas.update(canvas.getGraphics());
        }
    }

    /**
     * 重写Canvas类，实现自己的paint逻辑
     */
    private class MyCanvas extends Canvas {

        //形状选择范围
        private final int RECTANGLE = 1;
        private final int CIRCLE = 2;
        private final int SECTOR = 3;
        private ViewerFrame frame;//窗口对象，需要用到其中的属性

        public MyCanvas(ViewerFrame frame) {
            this.frame = frame;
        }

        @Override
        public void paint(Graphics g) {
            rePaint(this.frame);
        }

        private void rePaint(ViewerFrame frame) {
            Graphics graphics = frame.canvas.getGraphics();
            graphics.setColor(frame.color);
            switch (frame.shape) {
                //根据当前状态画图
                case RECTANGLE:
                    graphics.fillRect(frame.X, frame.Y, (int) frame.recWidth, (int) frame.recHeight);
                    break;
                case CIRCLE:
                    graphics.fillOval(frame.X, frame.Y, (int) frame.recWidth, (int) frame.recWidth);
                    break;
                case SECTOR:
                    graphics.fillArc(frame.X, frame.Y, (int) frame.recHeight, (int) frame.recHeight, 0, 60);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        new ViewerFrame().init();
    }
}
