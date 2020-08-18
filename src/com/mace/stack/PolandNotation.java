package com.mace.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 逆波兰表达式计算
 * @author zhangxuhui
 * @email zxh_1633@163.com
 * @create 2020-08-18 15:24
 */
public class PolandNotation {
    public static void main(String[] args) {
        /**
         * 先给出逆波兰表达式
         * (3+4)×5-6  -> 3 4 + 5 × 6 -
         */
        //String press = "3 4 + 5 * 6 -";

        //List<String> list = getListString(press);

        //System.out.println("运算结果="+EXECUTE(LIST));


        /**
         * 中缀表达式转后缀表达式 1+((2+3)×4)-5 => 转成 1 2 3 + 4 × + 5 –
         * 1.先将中缀表达式转为list
         * 2.转换
         */
        String expression = "1+((2+3)*4)-5";

        List<String> list = infixStringList(expression);

        System.out.println("转换后的list = "+list);

        System.out.println(execute(parseSuffixExpreesionList(list)));


    }

    //即 ArrayList [1,+,(,(,2,+,3,),*,4,),-,5]  =》 ArrayList [1,2,3,+,4,*,+,5,–]
    //方法：将得到的中缀表达式对应的List => 后缀表达式对应的List
    public static List<String> parseSuffixExpreesionList(List<String> ls) {
        //定义两个栈
        Stack<String> s1 = new Stack<String>(); // 符号栈
        //说明：因为s2 这个栈，在整个转换过程中，没有pop操作，而且后面我们还需要逆序输出
        //因此比较麻烦，这里我们就不用 Stack<String> 直接使用 List<String> s2
        //Stack<String> s2 = new Stack<String>(); // 储存中间结果的栈s2
        List<String> s2 = new ArrayList<String>(); // 储存中间结果的Lists2

        //遍历ls
        for(String item: ls) {
            //如果是一个数，加入s2
            if(item.matches("\\d+")) {
                s2.add(item);
            } else if (item.equals("(")) {
                s1.push(item);
            } else if (item.equals(")")) {
                //如果是右括号“)”，则依次弹出s1栈顶的运算符，并压入s2，直到遇到左括号为止，此时将这一对括号丢弃
                while(!s1.peek().equals("(")) {
                    s2.add(s1.pop());
                }
                s1.pop();//!!! 将 ( 弹出 s1栈， 消除小括号
            } else {
                //当item的优先级小于等于s1栈顶运算符, 将s1栈顶的运算符弹出并加入到s2中，再次转到(4.1)与s1中新的栈顶运算符相比较
                //问题：我们缺少一个比较优先级高低的方法
                while(s1.size() != 0 && Operation.getValue(s1.peek()) >= Operation.getValue(item) ) {
                    s2.add(s1.pop());
                }
                //还需要将item压入栈
                s1.push(item);
            }
        }

        //将s1中剩余的运算符依次弹出并加入s2
        while(s1.size() != 0) {
            s2.add(s1.pop());
        }

        return s2; //注意因为是存放到List, 因此按顺序输出就是对应的后缀表达式对应的List

    }

    /**
     * 中缀表达式转list
     */
    public static List<String> infixStringList(String pression){
        List<String> list = new ArrayList<>();

        /**
         * 遍历字符串指针
         */
        int index = 0 ;

        String str;

        char c;

        do{
            /**
             * c 是运算符
             */
            if((c = pression.charAt(index)) < 48 || (c = pression.charAt(index)) > 57){
                list.add(String.valueOf(c));
                index++;
            }else{
                /**
                 *  '0'[48]->'9'[57]
                 */
                str = "";
                while (index < pression.length() && (c = pression.charAt(index)) >= 48 && (c = pression.charAt(index)) <=57){
                    str += c;
                    index++;
                }
                list.add(str);
            }
        }while (index < pression.length());

        return list;
    }


    /**
     * 将表达式存入list,便于读取
     */
    public static List<String> getListString(String press){
        return Arrays.asList(press.split(" "));
    }


    /**
     * 完成逆波兰表达式的计算
     */
    public static int execute(List<String> list){
        Stack<String> stack = new Stack<>();

        for(String str:list){
            /**
             * 匹配数字
             */
            if(str.matches("\\d+")){
                stack.push(str);
            }else{

                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int result = 0 ;

                if("+".equals(str)){
                    result = num1 + num2;
                }else if("-".equals(str)){
                    result = num1 - num2;
                }else if("*".equals(str)){
                    result = num1 * num2;
                }else if("/".equals(str)){
                    result = num1 / num2;
                }else{
                    throw new RuntimeException("操作符不合法");
                }

                stack.push(String.valueOf(result));
            }
        }
        return Integer.parseInt(stack.pop());
    }
}


//编写一个类 Operation 可以返回一个运算符 对应的优先级
class Operation {
    private static int ADD = 1;
    private static int SUB = 1;
    private static int MUL = 2;
    private static int DIV = 2;

    //写一个方法，返回对应的优先级数字
    public static int getValue(String operation) {
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
                System.out.println("不存在该运算符" + operation);
                break;
        }
        return result;
    }

}

