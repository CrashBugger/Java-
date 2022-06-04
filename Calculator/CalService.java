package code;

import java.util.List;

public class CalService {
    private boolean isSecondNum;
    private String lastOp;
    private String firstNum;
    private String secondNum;
    private double store;
    private String numString = "1234567890.";
    private String opString = "+-*/";

    /**
     * 中转方法
     */
    String callMethod(String cmd, String text) {
        if (cmd.equals("C")) {
            return clearAll();
        } else if (cmd.equals("CE")) {
            return clearAll();
        } else if (cmd.equals("Back")) {
            return backSpace(text);
        } else if (numString.contains(cmd)) {
            return catNum(cmd, text);
        } else if (opString.contains(cmd)) {
            return setOp(cmd, text);
        } else if (cmd.equals("=")) {
            return cal(text, false);
        } else if (cmd.equals("1/x")) {
            return setReciprocal(text);
        } else if (cmd.equals("%")) {
            return cal(text, true);
        } else {
            return mCmd(cmd, text);
        }
    }

    String cal(String text, boolean isPercent) {
        //初始化第二个操作数
        double secondResult = secondNum == null ? Double.valueOf(text) : Double.valueOf(secondNum).doubleValue();
        //如果除数为0,不处理
        if (secondResult == 0 && this.lastOp.equals("/")) {
            return "0";
        }
        //如果有%操作,第二个操作数等于两数相乘除以100
        if (isPercent) {
            secondResult = MyMath.multiply(Double.valueOf(firstNum), MyMath.divide(secondResult, 100));
        }
        //四则运算,返回结果给第一个操作数
        if (this.lastOp.equals("+")) {
            firstNum = String.valueOf(MyMath.add(Double.valueOf(firstNum), secondResult));
        } else if (this.lastOp.equals("-")) {
            firstNum = String.valueOf(MyMath.subtract(Double.valueOf(firstNum), secondResult));
        } else if (this.lastOp.equals("*")) {
            firstNum = String.valueOf(MyMath.multiply(Double.valueOf(firstNum), secondResult));
        } else if (this.lastOp.equals("/")) {
            firstNum = String.valueOf(MyMath.divide(Double.valueOf(firstNum), secondResult));
        }
        //第二个操作数重新赋值
        secondNum = secondNum == null ? text : secondNum;
        //isSecond置为true
        this.isSecondNum = true;
        return firstNum;
    }

    String setReciprocal(String text) {
        if (text.equals("0")) {
            //text为0,不求
            return text;
        } else {
            //isSecondNum置为false
            this.isSecondNum = true;
            return String.valueOf(MyMath.divide(1, Double.valueOf(text)));
        }
    }

    String sqrt(String text) {
        this.isSecondNum = true;
        return String.valueOf(Math.sqrt(Double.valueOf(text)));
    }

    String setOp(String cmd, String text) {
        //设置为上次的操作
        this.lastOp = cmd;
        this.firstNum = text;
        //第二个操作数赋值为空
        this.secondNum = null;
        //isSecondNum置为true
        this.isSecondNum = true;
        //返回空值
        return null;
    }

    String setNegative(String text) {
        return null;
    }

    String catNum(String cmd, String text) {
        String result = cmd;
        //如果目前的text不等于0
        if (!text.equals("0")) {
            if (isSecondNum) {
                isSecondNum = false;
            } else {
                //返回目前的text加上新单击的数字
                result = text + cmd;
            }
        }
        //如果有.开头,则在前面补0
        if (result.indexOf(".") == 0) {
            result = "0" + text;
        }
        return result;
    }

    String backSpace(String text) {
        return text.equals("0") || text.equals("") ? "0" : text.substring(0, text.length() - 1);
    }

    String mCmd(String cmd, String text) {
        if (cmd.equals("M+")) {
            //累加操作
            store = MyMath.add(store, Double.valueOf(text));
        } else if (cmd.equals("MC")) {
            //清除操作
            store = 0;
        } else if (cmd.equals("MR")) {
            //读出store的zhi
            isSecondNum = true;
            return String.valueOf(store);
        } else if (cmd.equals("MS")) {
            //计算结果保存
            store = Double.valueOf(text).doubleValue();
        }
        return null;
    }

    String clearAll() {
        this.firstNum = "0";
        this.secondNum = null;
        return this.firstNum;
    }

    String clear(String text) {
        return null;
    }

    public int getStore() {
        return 0;
    }
}
