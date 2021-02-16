package brookelovesummer;

import java.util.HashSet;

public class OperateString2 {

    public HashSet<Character> numbers = new HashSet<>();
    public HashSet<Character> operator = new HashSet<>();
    public boolean flag = true;
    public int help = 0;
    public int lpra = 0;
    public int rpra = 0;

    public OperateString2() {

        char pi = '\u03C0';
        for(int j=0;j<=9;j++)
            numbers.add((char)('0'+j));
        numbers.add('e');
        numbers.add('E');
        numbers.add(pi);
        numbers.add('.');
        numbers.add('$');//'-'

        operator.add('+');
        operator.add('-');
        operator.add('(');
        operator.add(')');
        operator.add('^');
        operator.add('*');
        operator.add('/');
        operator.add('√');//genhao
        operator.add('%');
        operator.add('!');
    }


    /**
     * Calculate rules(有括号先算括号里面，先乘方（开方），再乘除，最后加减！)
     * 采用递归的方式进行逐级拆解计算式，以括号为基准，配上字符串匹配函数str_fun01(x) && str_fun02(x,y)
     * @param text
     * @return
     */
    public String calculate(String text) {

        flag = true;

        int points = 0;
        for (int i = 0; i < text.length(); ++i) {
            if (text.charAt(i) == '.') ++points;
            if (points > 1) return "错误";
            // 表达式错误：多个小数点！
        }
        if(bracounts(text,'(') != bracounts(text,')')) {
            // 表达式错误：括号不成对！
            return "错误";
        }

        String result = operate(text);
        if (result == null || !flag) return "错误";

        if(Math.abs(Double.parseDouble(result))<1E-13) result="0";
        if(Math.abs(Double.parseDouble(result))>1E15) {
            if(result.contains("-"))
                return"-∞";
            else
                return "∞";
        }

        if(!result.contains("E")) {
            if(result.contains(".") && result.length() - result.indexOf(".") > 5)
                result = result.substring(0, result.indexOf(".")+6);
        }

        return result;
    }

    /**
     * 递归调用实现去括号
     * @param text
     * @return
     */
    private String operate(String text) {
        //[((() () )  )]  || [() () ()] ||[((())) ()]

        if (text == null || text.isEmpty()) return null;
        if (!text.contains("(") && !text.contains(")")) {
            do {
                text = calculator(text);
                if (text == null) return null;
            } while (!isnumber(text));
            return text;
        }

        int[] bracketsindex = getbracketsindex(text);
        String brooke = text.substring(bracketsindex[0]+1, bracketsindex[1]);
        brooke = operate(brooke);//递归去除级联型括号
        if (brooke == null) return null;

        String funname = getfunname(text,bracketsindex[0]);
        text = text.replace(text.substring(help,bracketsindex[1]+1),doubletostring(str_fun01(funname,Double.parseDouble(brooke))));
        if (text.contains("("))
            text = operate(text);//递归去除并联型括号
        if (text == null) return null;

        while(!isnumber(text)) {
            text = calculator(text);
            if (text == null) return null;
        }

        return text;
    }

    /**
     * 判断字符串是否为纯数字
     * @param text
     * @return
     */
    private boolean isnumber(String text) {

        text = operatenage(text);
        char[] s = text.toCharArray();
        for(int i=0;i<s.length;i++) {
            if(!numbers.contains(s[i]))
                return false;
        }
        return true;
    }


    /**
     * 获取当前操作函数
     * @param text
     * @param lbraindex
     * @return
     */
    private String getfunname(String text, int lbraindex) {

        text = operatenage(text);
        char[] s = text.toCharArray();
        int i = lbraindex - 1;

        while(i>=0 && !operator.contains(s[i--]));
        if(i>=0)i++;
        help = i+1;
        if(help == lbraindex)return "(";
        return text.substring(i+1, lbraindex);
    }


    /**
     * 获取当前表达式的最外层括号对的索引
     * @param text
     * @return
     */
    private int[] getbracketsindex(String text) {

        // find a pair brackets
        int lindex = text.indexOf("(");
        int num = 1 , rindex = lindex;
        char[] s = text.toCharArray();
        while(num !=0 && rindex++ < s.length) {
            if(s[rindex] == '(')
                num++;
            if(s[rindex] == ')')
                num--;
        }

        return new int[] {lindex,rindex} ;
    }


    /**
     * operate text with no brackets
     * @param text
     * @return
     */
    private String calculator(String text) {

        text = stradjust(text);

        if(isnumber(text)) {
            text = text.replace('$', '-');
            return text;
        }

        boolean calculated = false;
        char[] s = text.toCharArray();

        if(text.contains("^")||text.contains("√")) {
            for (int i=0;i<s.length;i++)
                if(s[i] == '^' || s[i] == '√') {
                    text = gettext(text,s[i],i);
                    if (text == null) return null;
                    i=0;
                    s = text.toCharArray();
                    calculated = true;
                }
        }
        if(text.contains("!")||text.contains("%")) {
            for (int i=0;i<s.length;i++)
                if(s[i] == '!' || s[i] == '%') {
                    text = gettext(text,s[i],i);
                    if (text == null) return null;
                    i=0;
                    s = text.toCharArray();
                    calculated = true;
                }
        }
        if(text.contains("*")||text.contains("/")) {
            for (int i=0;i<s.length;i++)
                if(s[i] == '*' || s[i] == '/') {
                    text = gettext(text,s[i],i);
                    if (text == null) return null;
                    i=0;
                    s = text.toCharArray();
                    calculated = true;
                }
        }
        if(text.contains("+")||operatenage(text).contains("-")) {
            for (int i=0;i<s.length;i++)
                if(s[i] == '+' || s[i] == '-') {
                    text = gettext(text,s[i],i);
                    if (text == null) return null;
                    i=0;
                    s = text.toCharArray();
                    calculated = true;
                }
        }
        if (!calculated) return null;
        return text;
    }

    /**
     * 将表达式中的负'-'用'$'代替（只要不是运算符的符号均可）
     * @param text
     * @return
     */
    private String operatenage(String text) {

        if(!text.contains("-"))
            return text;
        text = text.replace("--", "+");
        char[] s = text.toCharArray();
        if(s[0] == '-') s[0] = '$';
        for(int i=1;i<s.length;i++) {
            if(s[i] == '-' && (s[i-1]=='E' || !numbers.contains(s[i-1])))
                s[i] = '$';
        }
        text = new String(s);
        return text;
    }

    /**
     * 根据当前确定的运算符及其索引，计算当前运算符连接的表达式的值
     * @param text
     * @param ch
     * @param index
     * @return
     */
    private String gettext(String text ,char ch ,int index) {

        text = operatenage(text);
        double[] dob = getparams(text,index);
        if (dob == null) return null;

        if(ch == '√')
            text = text.replace(text.substring(index,rpra),""+doubletostring(str_fun01(""+ch,dob[1])));
        else if(ch == '%' || ch == '!')
            text = text.replace(text.substring(lpra, index+1),""+doubletostring(str_fun01(""+ch,dob[0])));
        else {
            text = text.replace(text.substring(lpra, rpra) ,""+doubletostring(str_fun02(""+ch,dob[0],dob[1])));
        }
        return text;
    }

    /**
     * 调整当前字符串中包含的特殊字符，例如'e','pi'等
     * @param text
     * @return
     */
    private String stradjust(String text) {

        if(text.contains("\u03C0")) {
            text = text.replace("\u03C0", String.valueOf(Math.PI));
            return stradjust(text);
        }
        if(text.contains("e")) {
            text = text.replace("e", String.valueOf(Math.E));
            return stradjust(text);
        }
        return operatenage(text);
    }


    /**
     * 获取当前操作符的左右参数
     * @param text
     * @param ch_index
     * @return
     */
    private double[] getparams(String text ,int ch_index) {

        double lparam = 0,rparam = 0;
        char[] s = text.toCharArray();
        int i = ch_index-1;//So important too

        // find left parameter
        if(s[ch_index] != '√') {
            while(i >= 0 && numbers.contains(s[i])) {
                i--;
            }
            String temp = text.substring(i+1,ch_index).replace('$', '-');
            if (temp.isEmpty()) return null;
            lparam = Double.parseDouble(temp);
            lpra = i+1;
        }

        // find right parameter
        if(s[ch_index] != '%' && s[ch_index] != '!') {
            i = ch_index+1;
            while(i<s.length && numbers.contains(s[i])) {
                i++;
            }
            String temp = text.substring(ch_index+1,i).replace('$', '-');
            if (temp.isEmpty()) return null;
            rparam = Double.parseDouble(temp);
            rpra = i;
        }

        return new double[] {lparam, rparam};
    }

    /**
     * 改进的double转String，对于取整后和整数相当的double数据，返回取证后的数据
     * @param dob
     * @return
     */
    private String doubletostring(double dob) {

        if(dob == (int)dob)
            return ""+(int)dob;
        else
            return ""+dob;
    }

    /**
     * 统计text中bra字符的个数，本例中用于计算括号的个数
     * @param text
     * @param bra
     * @return
     */
    private int bracounts(String text ,char bra) {

        char[] s = text.toCharArray();
        int count = 0;
        for(int i=0;i<s.length;i++) {
            if(s[i] == bra)
                count++;
        }
        return count;
    }

    /**
     * 单目操作函数str_fun01
     * @param fun
     * @param x
     * @return
     */
    private double str_fun01(String fun,double x) {

        double param = 0;
        switch(fun) {
            case "$":
                param = x*(-1); break;
            case "%":
                param = x*0.01; break;
            case "(":
                param = x; break;
            case "!" :{
                if(x<0) {
                    // 错误：阶乘函数参数只能为非负整数！
                    flag = false;
                }else if(x > 16) {
                    // 错误：结果超过整形数范围！
                    flag = false;
                }
                else
                    param = (double) factorial((int) x);
                break;
            }
            case "√":{//genhao
                if (x < 0) {
                    // 错误：负数不能开方！
                    flag = false;
                }
                else
                    param = Math.pow(x, 0.5);
                break;
            }
            case "ln":{
                if (x <= 0) {
                    // 错误：lg 或 ln 真数只能为正数！
                    flag = false;
                }
                else
                    param = Math.log(x);
                break;
            }
            case "lg":{
                if (x <= 0) {
                    // 错误：lg 或 ln 真数只能为正数！
                    flag = false;
                }
                else
                    param = Math.log10(x);
                break;
            }
            case "sin":
                param = Math.sin(x);break;
            case "cos":
                param = Math.cos(x);break;
            case "tan":
                param = Math.tan(x);break;
            case "arcsin":
                param = Math.asin(x);break;
            case "arccos":
                param = Math.acos(x);break;
            case "arctan":
                param = Math.atan(x);break;
            default:
                flag = false; break;
        }
        return param;
    }

    /**
     * 双目操作函数str_fun02
     * @param fun
     * @param x
     * @param y
     * @return
     */
    private double str_fun02(String fun,double x,double y) {

        double param = 0;
        switch(fun) {
            case "^":{
                if (x < 0  && (int)y != y) {
                    // 错误：负数只能求整数次幂！
                    flag = false;
                } else if(x == 0 && y<0) {
                    // 错误：零没有负数次幂！
                    flag = false;
                }
                else
                    param = Math.pow(x, y);
                break;
            }
            case "+":
                param = x+y;break;
            case "-":
                param = x-y;break;
            case "*":
                param = x*y;break;
            case "/":{
                if (y == 0) {
                    // 错误：零不能作为除数！
                    flag = false;
                }
                else
                    param = x/y;
                break;

            }
            default: flag = false; break;
        }
        return param;
    }

    /**
     * 递归法求整数n的阶乘
     * @param n
     * @return
     */
    private int factorial(int n) {

        if(n == 0)
            return 1;
        return n*factorial(n-1);
    }
}

