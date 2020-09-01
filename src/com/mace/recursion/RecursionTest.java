package com.mace.recursion;

/**
 * 递归问题 测试
 * 必须要有递归结束的条件，否则内存溢出。
 * @author zhangxuhui
 * @email zxh_1633@163.com
 * @create 2020-09-01 9:39
 */
public class RecursionTest {
    public static void main(String[] args) {
        System.out.println(factorial(3));
    }

    /**
     * 阶乘问题
     * f(2)*3 -> f(1)*2*3 ->1*2*3
     */
    public static int factorial(int n) {
        if (n == 1) {
            return 1;
        } else {
            return factorial(n - 1) * n;
        }
    }
}
