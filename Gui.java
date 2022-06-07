package Code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Gui extends JFrame {
    private JTextField houZhui;
    private JTextField zhongZhui;
    private int ADD = 1;//运算符优先级
    private int SUB = 1;
    private int MUL = 2;
    private int DIV = 2;

    /**
     * 初始化界面
     */
    public void init() {
        //设置样式
        houZhui = new JTextField();
        zhongZhui = new JTextField();
        this.setSize(500, 200);
        this.setLayout(new FlowLayout());
        JLabel houZhuiLabel = new JLabel("后缀表达式结果:");
        JLabel zhongZhuiLabel = new JLabel("请输入中缀表达式:");
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.setLayout(new GridLayout(2, 1));
        panel.add(zhongZhuiLabel);
        panel.add(zhongZhui);
        panel.add(houZhuiLabel);
        panel.add(houZhui);
        JButton button = new JButton("计算结果");
        button.setPreferredSize(new Dimension(80, 50));
        panel.setPreferredSize(new Dimension(300, 100));
        this.add(panel);
        this.add(button);
        //加入监听
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String input = zhongZhui.getText();
                if (input == null || input.equals("")) {
                    return;
                }
                houZhui.setText("");
                String isValid = isValid(input);
                if (isValid != null) {
                    JOptionPane.showMessageDialog(null, isValid);
                    return;
                }
                String result = getResult(input);//拿到最终结果
                JOptionPane.showMessageDialog(null, "结果为" + result);
            }
        });
        this.setVisible(true);
    }

    /**
     * 判断运算式是否合法
     *
     * @return 返回提示信息
     */
    private String isValid(String str) {
        {
            int temp = 0;//括号是否闭合判断
            Stack<Integer> stack = new Stack<>();//栈保存括号位置
            if (str.charAt(0) == '/' || str.charAt(0) == '*')
                return "首位运算符号错误";
            if ((str.charAt(str.length() - 1) < '0' || str.charAt(str.length() - 1) > '9') && str.charAt(str.length() - 1) != ')') {
                System.out.println(str.charAt(str.length() - 1));
                return "末位运算符非法";
            }
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '(') {
                    if (i == 0 && (str.charAt(i + 1) == '*' || str.charAt(i + 1) == '/'))
                        return "第" + (i + 2) + "位运算符非法";
                    else if (str.charAt(i - 1) >= '0' && str.charAt(i - 1) <= '9')
                        return "第" + (i) + "位运算符非法";
                    temp++;
                    stack.push(i);
                } else if (str.charAt(i) == ')') {
                    if (isSiZe(str, i - 1))
                        return "第" + (i) + "位运算符非法";
                    else if ((i + 1 < str.length()) && (str.charAt(i + 1) >= '0' && str.charAt(i + 1) <= '9'))
                        return "第" + (i + 2) + "位运算符非法";
                    temp--;
                    if (temp < 0) {
                        return "第" + (i + 1) + "位括号未闭合";
                    }
                    stack.pop();
                }
                if (str.charAt(i) == '/') {
                    if (str.charAt(i + 1) == '0') {
                        return "除0错误";
                    }
                }
                if (isSiZe(str, i) && isSiZe(str, i + 1)) {
                    return "第" + (i + 1) + "位运算符错误";
                }

            }
            if (temp > 0) {
                return "第" + (stack.pop() + 1) + "位括号未闭合";
            }
            return null;
        }
    }

    /**
     * 判断string的i位是否是四则符号
     */
    public boolean isSiZe(String str, int i) {
        return str.charAt(i) == '+' || str.charAt(i) == '*' || str.charAt(i) == '-' || str.charAt(i) == '/';
    }

    /**
     * 参数是中缀表达式
     *
     * @return 返回一个list，list中存放的是String类型的内容
     */
    public List<String> toInfixExpressionList(String s) {
        //定义一个List，存放中缀表达式对应的内容
        List<String> ls = new ArrayList<String>();
        int i = 0;  //这是一个指针，用于遍历中缀表达式字符串
        String str;  //对多位数进行拼接
        char c;  //每遍历到一个字符，就放入到c中
        do {
            //如果c是一个非数字，就需要加入到ls中
            if ((c = s.charAt(i)) < '0' || (c = s.charAt(i)) > '9') {
                ls.add("" + c);
                i++;
            } else {  //如果是一个数，需要考虑多位数的问题
                str = "";  //先将str置成""
                while (i < s.length() && (c = s.charAt(i)) >= '0' && (c = s.charAt(i)) <= '9') {
                    str += c;  //拼接
                    i++;
                }
                ls.add(str);
            }
        }
        while (i < s.length());
        return ls;  //返回转换成了list的字符串ls
    }

    /**
     * 中缀的list数组
     *
     * @return 将转换为list的中缀表达式变为后缀表达式的list
     */

    public List<String> parseSuffixExpressionList(List<String> ls) {
        //定义两个栈
        Stack<String> s1 = new Stack<String>();  //符号栈
        //说明：因为s2这个栈，在整个转换过程中，没有pop操作，而且后面还需要逆序输出

        List<String> s2 = new ArrayList<String>();  //存储中间结果的List2

        //遍历ls
        for (String item : ls) {
            //如果是一个数，加入s2
            if (item.matches("\\d+")) {  //使用正则表达式来匹配，看匹配的是否是一个数
                s2.add(item);
            } else if (item.equals("(")) {
                s1.push(item);
            } else if (item.equals(")")) {
                //如果是右括号")"，则依次弹出s1栈顶的运算符，并压入s2，直到遇到左括号为止，此时将这一对括号丢弃
                while (!s1.peek().equals("(")) {
                    s2.add(s1.pop());
                }
                s1.pop();  // ！！！ 将 ( 弹出s1栈，消除小括号
            } else {
                //当item的优先级小于等于s1栈顶运算符的优先级，将s1栈顶的运算符弹出并压入到s2中，接下来与s1中新的栈顶运算符作比较
                while (s1.size() != 0 && getValue(s1.peek()) >= getValue(item)) {
                    s2.add(s1.pop());
                }
                //还需要将item压入栈中
                s1.push(item);
            }
        }
        //将s1中剩余的运算符依次弹出并加入到s2中
        while (s1.size() != 0) {
            s2.add(s1.pop());
        }
        return s2;  //因为是存放到List中，因此按顺序输出的结果就是对应的后缀表达式的对应的List
    }

    //写一个方法，返回对应的优先级数字
    public int getValue(String operation) {
        int result = 0;
        switch (operation) {
            case "+":
                result = ADD;
                break;
            case "-":
                result = SUB;
                break;
            case "*":
                result = MUL;
                break;
            case "/":
                result = DIV;
                break;
            default:
                result = 0;
                break;
        }
        return result;
    }

    /**
     * 根据后缀表达式计算
     */
    public int calculate(List<String> ls) {
        //创建一个栈，只需要一个栈即可
        Stack<String> stack = new Stack<String>();
        //遍历ls
        for (String item : ls) {
            //这里使用正则表达式来取出整数
            if (item.matches("\\d+")) {  //匹配的是多位数
                //入栈
                stack.push(item);
            } else {
                //pop出两个数，并运算，再入栈
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int res = 0;
                if (item.equals("+")) {
                    res = num1 + num2;
                } else if (item.equals("-")) {
                    res = num1 - num2;
                } else if (item.equals("*")) {
                    res = num1 * num2;
                } else if (item.equals("/")) {
                    res = num1 / num2;
                } else {
                    throw new RuntimeException("运算符有误");
                }
                //把res入栈
                stack.push("" + res);   //stack是存字符串的，但是res是一个整数，可以用整数加上一个空字符串，来把整数变成字符串
            }
        }
        //最后留在stack中的数据是运算结果
        return Integer.parseInt(stack.pop());
    }

    public String getResult(String str) {
        List<String> ls = toInfixExpressionList(str);//拿到中缀的数组
        List<String> suffix = parseSuffixExpressionList(ls);//拿到后缀数组
        String output = "";
        for (String s : suffix) {
            output += " " + s;
        }
        houZhui.setText(output);
        int result = calculate(suffix);
        return "" + result;
    }

    public static void main(String[] args) {
        Gui gui = new Gui();
        gui.init();
    }
}


