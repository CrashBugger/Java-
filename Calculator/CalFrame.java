package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class CalFrame extends JFrame {
    private static final int PRE_WIDTH = 100;
    private static final int PRE_HEIGHT = 100;
    private String[] nOp = {"7", "8", "9", "/", "sqrt", "4", "5", "6", "*", "%", "1", "2", "3", "-", "1/x", "0",
            "+/-", ".", "+", "="};
    private String[] mOp = {"MC", "MR", "MS", "M+"};
    private String[] rOp = {"Back", "CE", "C"};
    private CalService service;
    private JTextField textField;
    private JButton button;

    private void initialize() {
        service = new CalService();
        textField = new JTextField();
        button = new JButton();
        this.setResizable(false);
        this.setTitle("Calculator");
        this.setSize(600, 500);
        //计算输入框
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 1));
        panel.add(getTextField(), BorderLayout.NORTH);
        panel.setPreferredSize(new Dimension(PRE_WIDTH, PRE_HEIGHT));
        //增加存储操作键
        JButton[] mButton = getMButton();
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(5, 1, 0, 5));
        for (JButton b : mButton) {
            panel1.add(b);
        }
        //结果操作键
        JButton[] rButton = getRButton();
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1, 3, 3, 3));
        for (JButton b : rButton) {
            panel2.add(b);
        }
        //按钮
        JButton[] nButton = getNButton();
        JPanel panel22 = new JPanel();
        panel22.setLayout(new GridLayout(4, 5, 3, 5));
        for (JButton b : nButton) {
            panel22.add(b);
        }
        //添加面板
        panel.add(panel1, BorderLayout.WEST);
        panel.add(panel2, BorderLayout.SOUTH);
        panel.add(panel22, BorderLayout.CENTER);
        this.add(panel);
        this.setVisible(true);
    }

    public ActionListener getActionListener() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String cmd = actionEvent.getActionCommand();
                String result = null;
                try {
                    //计算结果
                    result = service.callMethod(cmd, textField.getText());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                //处理button标记
                if (cmd.contains("MC")) {
                    button.setText("");
                } else if (cmd.indexOf("M") == 0 && service.getStore() > 0) {
                    button.setText("M");
                }
                //处理计算结果
                if (result != null) {
                    textField.setText(result);
                }
            }
        };
        return actionListener;
    }

    JTextField getTextField() {
        return this.textField;
    }

    /**
     * @return 操作按键(右侧的)
     */
    JButton[] getMButton() {
        JButton[] result = new JButton[mOp.length + 1];
        result[0] = button;
        for (int i = 0; i < this.mOp.length; i++) {
            JButton b = new JButton(mOp[i]);
            b.addActionListener(getActionListener());
            b.setForeground(Color.RED);
            result[i + 1] = b;
        }
        return result;
    }

    /**
     * @return 操作按键(上面的)
     */
    JButton[] getRButton() {
        JButton[] result = new JButton[rOp.length];
        for (int i = 0; i < this.rOp.length; i++) {
            JButton b = new JButton(this.rOp[i]);
            b.addActionListener(getActionListener());
            b.setForeground(Color.RED);
            result[i] = b;
        }
        return result;
    }

    /**
     * @return 运算按键
     */
    JButton[] getNButton() {
        //这个数组保存需要设置为红色的操作符
        String[] redButton = {"/", "*", "-", "+", "="};
        JButton[] result = new JButton[nOp.length];
        for (int i = 0; i < nOp.length; i++) {
            //新建按钮
            JButton b = new JButton(nOp[i]);
            //添加事件
            b.addActionListener(getActionListener());
            //对redButton排序才可以使用binarysearch
            Arrays.sort(redButton);
            if (Arrays.binarySearch(redButton, nOp[i]) >= 0) {
                b.setForeground(Color.red);
            } else {
                b.setForeground(Color.blue);
            }
            result[i] = b;
        }
        return result;
    }

    public static void main(String[] args) {
        CalFrame calFrame = new CalFrame();
        calFrame.initialize();
    }
}
