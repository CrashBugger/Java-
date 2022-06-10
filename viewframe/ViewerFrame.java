package viewframe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewerFrame extends JFrame {
    private int width = 1000;
    private int height = 800;
    private ViewerService service;
    private JLabel label;

    void init() {
        service = new ViewerService();
        label = new JLabel();
        //设置标题
        this.setTitle("看图程序");
        this.setPreferredSize(new Dimension(width, height));
        //创建菜单
        createMenuBar();
        //工具栏
        JPanel toolBar = createToolPanel();
        this.add(toolBar, BorderLayout.NORTH);
        this.add(new JScrollPane(label), BorderLayout.CENTER);
        //可见
        this.pack();
        this.setVisible(true);
    }

    JLabel getLabel() {
        return this.label;
    }

    JPanel createToolPanel() {
        //创建一个JPanel
        JPanel panel = new JPanel();
        //创建一个标题为工具的工具栏
        JToolBar toolBar = new JToolBar("工具");
        //不可拖动
        toolBar.setFloatable(false);
        //布局方式
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        //工具数组
        String[] toolArr = {"viewframe.action.OpenAction", "viewframe.action.LastAction",
                "viewframe.action.NextAction", "viewframe.action.BigAction", "viewframe.action.SmallAction"};
        for (int i = 0; i < toolArr.length; i++) {
            //每一个按钮创建一个新的action
            ViewerAction action = new ViewerAction(new ImageIcon("1.png"),
                    toolArr[i], this);
//            以图标创建一个新的button
            JButton button = new JButton(action);

            button.setText(toolArr[i].substring(17));
            //将button加到工具栏中
            toolBar.add(button);
        }
        panel.add(toolBar);
        //返回
        return panel;
    }
//
//    private String getButtonName(String s) {
//        Pattern compile = Pattern.compile(".*?.(.*?)Action");
//        Matcher matcher = compile.matcher(s);
//        return matcher.group(0);
//    }

    void createMenuBar() {
        //加给菜单的事件监听器
        ActionListener menuListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //传入当前对象
                service.menuDo(ViewerFrame.this, actionEvent.getActionCommand());
            }
        };
        JMenuBar menuBar = new JMenuBar();
        String[] menuArr = {"文件(F)", "工具(T)", "帮助(H)"};
        String[][] menuItemArr = {{"打开(O)", "退出(X)"},
                {"放大(M)", "缩小(O)", "-", "上一个(X)", "下一个(P)"},
                {"帮助主题", "关于"}};
        //遍历创建菜单
        for (int i = 0; i < menuArr.length; i++) {
            //新建一个JMenu菜单
            JMenu menu = new JMenu(menuArr[i]);
            for (int j = 0; j < menuItemArr[i].length; j++) {
                if (menuItemArr[i][j].equals("-")) {
                    //设置菜单分割
                    menu.addSeparator();
                } else {
                    //新建一个JMenuItem菜单项
                    JMenuItem menuItem = new JMenuItem(menuItemArr[i][j]);
                    menuItem.addActionListener(menuListener);
                    //加入菜单项中
                    menu.add(menuItem);
                }
            }
            menuBar.add(menu);
        }
        //设置jMenuBar
        this.setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        new ViewerFrame().init();
    }
}
