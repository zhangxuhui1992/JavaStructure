package com.mace.stack;

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
        String press = "3 4 + 5 * 6 -";

        List<String> list = getListString(press);

        System.out.println("运算结果="+execute(list));
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
