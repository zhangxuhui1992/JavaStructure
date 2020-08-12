package com.mace.stack;

import java.util.Scanner;

/**
 * @author zhangxuhui
 * @email zxh_1633@163.com
 * @create 2020-08-12 14:48
 */
public class ArrayStackDemo {
    public static void main(String[] args) {
        //测试一下ArrayStack 是否正确
        //先创建一个ArrayStack对象->表示栈
        ArrayStack stack = new ArrayStack(4);
        String key = "";
        boolean loop = true; //控制是否退出菜单
        Scanner scanner = new Scanner(System.in);

        while(loop) {
            System.out.println("show: 表示显示栈");
            System.out.println("exit: 退出程序");
            System.out.println("push: 表示添加数据到栈(入栈)");
            System.out.println("pop: 表示从栈取出数据(出栈)");
            System.out.println("请输入你的选择");
            key = scanner.next();
            switch (key) {
                case "show":
                    stack.show();
                    break;
                case "push":
                    System.out.println("请输入一个数");
                    int value = scanner.nextInt();
                    stack.push(value);
                    break;
                case "pop":
                    try {
                        int res = stack.pop();
                        System.out.printf("出栈的数据是 %d\n", res);
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.getMessage());
                    }
                    break;
                case "exit":
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }

        System.out.println("程序退出~~~");
    }
}

/**
 * 数组模拟栈
 */
class ArrayStack{
    private int maxSize;//栈的大小
    private int[] arr;//数组模拟栈
    private int top=-1;//top表示栈顶，默认为-1

    public ArrayStack(int maxSize){
        this.maxSize = maxSize;
        arr = new int[this.maxSize];
    }

    /**
     * 栈满
     */
    public boolean isFull(){
        return top == maxSize - 1 ;
    }

    /**
     * 栈空
     */
    public boolean isEmpty(){
        return top == -1;
    }

    /**
     * 压栈
     */
    public void push(int num){
        if(isFull()){
            System.out.println("栈满，无法添加");
            return;
        }

        top++;
        arr[top] = num;

    }

    /**
     * 出栈
     */
    public int pop(){
        if(isEmpty()){
            throw new RuntimeException("栈空，已没有数据");
        }

        int val = arr[top];
        top--;
        return val;
    }

    /**
     * 遍历
     */
    public void show(){
        if(isEmpty()){
            System.out.println("栈为空，无法遍历....");
            return;
        }

        for(int i = top;i>=0;i--){
            System.out.printf("索引为%d,值为%d \n",i,arr[i]);
        }
    }

}
