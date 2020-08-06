# 数据结构和算法

## 一、线性结构和非线性结构

### 1、线性结构

- 线性结构作为最常用的数据结构，其特点是数据元素之间存在一对一的线性关系，有两种不同的存储结构，即顺序结构和链式存储结构，
- 顺序存储的线性表为顺序表，顺序表中的存储元素是连续的。
- 链式存储的线性表称为链表，链表中存储的元素不是连续的，元素节点中存放数据元素以及相邻元素的地址信息。
- 线性结构常见有：数组、队列、链表、栈、

### 2、非线性结构

- 非线性结构包括：二维数组、多维数组、广义表、树结构、图结构

## 二、稀疏数组

### 1、概念

​	当一个数组中大部分元素为0，或者为同一个值的数组时，可以使用稀疏数组来保存该数组。

​	稀疏数组的处理方式：

- 记录数组一共有几行几列，有多少个不同的值。
- 把具有不同值的元素的行列及值记录在一个小规模的数组中，从而缩小程序的规模。

### 2、实例

![](https://i.bmp.ovh/imgs/2020/08/0eddf06a73f91027.png)

Java代码实现

~~~java
package com.mace.sparsearr;
import java.io.*;
/**
 * 稀疏数组
 * @author zhangxuhui
 * @email zxh_1633@163.com
 * @create 2020-08-05 10:21
 */
public class SparseArr {
    public static void main(String[] args) throws IOException, InterruptedException {
        /**
         * 构建原始二维数组：11*11
         * 0-没有棋子 1-黑子 2-蓝子
         */
        int [][] arr = new int [11][11];

        //初始化默认数据
        arr[1][2] = 1;
        arr[2][3] = 2;

        //输出
        System.out.println("初始化原始二维数组:");
        for(int [] data : arr){
            for(int num : data){
                System.out.printf("%d\t",num);
            }
            System.out.println();
        }

        /**
         * 原始数组转稀疏数组
         * 1.遍历原始数组，得到有效数据的个数num
         * 2.构建稀疏数组 new int[num+1][3]
         * 3.补充数据
         */

        int count = 0 ;
        //记录行
        int index = arr.length ;
        //记录列
        int innerIndex = 0 ;
        for(int i = 0 ; i < index;i++){
            innerIndex = arr[i].length;
            for(int k = 0 ; k < innerIndex;k++){
                if(arr[i][k] != 0){
                    count++;
                }
            }
        }

        //创建稀疏数组
        int [][] sparseArr = new int[count+1][3];

        //输出稀疏数组：
        System.out.println("输出无数据稀疏数组：");
        for(int [] data : sparseArr){
            for(int num : data){
                System.out.printf("%d\t",num);
            }
            System.out.println();
        }

        //第一行的值为 原始数组的行 原始数组的列 有效数据的个数
        sparseArr[0][0] = index;
        sparseArr[0][1] = innerIndex;
        sparseArr[0][2] = count;

        //遍历原始数组，将有效数据添加至稀疏数组
        int sparseIndex = 0;
        for(int i = 0 ; i < index;i++){
            for(int k = 0 ; k < innerIndex;k++){
                if(arr[i][k] != 0){
                    sparseIndex++;
                    sparseArr[sparseIndex][0] = i;
                    sparseArr[sparseIndex][1] = k;
                    sparseArr[sparseIndex][2] = arr[i][k];
                }
            }
        }

        //输出稀疏数组：
        System.out.println("输出有数据稀疏数组：");
        for(int [] data : sparseArr){
            for(int num : data){
                System.out.printf("%d\t",num);
            }
            System.out.println();
        }


        //写出带文件，每一行写出
        System.out.println("数组正在写出到文件...");
        Thread.sleep(1000);
        System.out.println("写出成功...");
        writeToTxTFile(sparseArr);


        //从文件中读取数据
        Thread.sleep(1000);
        System.out.println("开始读取磁盘存储的数组...");
        int [][] result = readFiletoArr();
        Thread.sleep(1000);
        System.out.println("读取成功...");


        //输出稀疏数组：
        System.out.println("输出从磁盘读取的稀疏数组：");
        for(int [] data : result){
            for(int num : data){
                System.out.printf("%d\t",num);
            }
            System.out.println();
        }

        /**
         * 将稀疏数组转换为原来的初始数组
         */

        int [][] data = new int[result[0][0]][result[0][1]];
        for(int i = 1 ; i < result.length;i++){
            data[result[i][0]][result[i][1]] = result[i][2];
        }
        
        //输出
        System.out.println("输出转化后数组:");
        for(int [] da : data){
            for(int num : da){
                System.out.printf("%d\t",num);
            }
            System.out.println();
        }

    }


    /**
     * 数组写出到文件
     * @param arr
     */
    public static void writeToTxTFile(int [][] arr) throws IOException {
       File txt = new File("D:\\arr.txt");
       if(!txt.exists()){
           txt.createNewFile();
       }

       String str = "";
       for(int [] data : arr){
           for(int num : data){
               str += num+" ";
           }
           str += "\n";
       }

        FileWriter writer = new FileWriter(txt);
        writer.write(str);
        writer.flush();
        writer.close();
    }

    /**
     * 从文件中读取数据
     * @return
     */
    public static int[][] readFiletoArr() throws IOException {
        File txt = new File("D:\\arr.txt");
        FileReader reader = new FileReader(txt);
        BufferedReader br = new BufferedReader(reader);
        String[] s = br.readLine().split(" ");
        int [][] result = new int [Integer.parseInt(s[2])+1][Integer.parseInt(s[2])+1];
        result[0] = str2intArr(s);

        int index = 1 ;
        String str;
        while((str = br.readLine()) != null){
            result[index] = str2intArr(str.split(" "));
            index++;
        }
        return result;
    }

    public static int[] str2intArr(String [] arr){

        int [] intArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            intArr[i] = Integer.parseInt(arr[i]);
        }
        return intArr;
    }
}

~~~

~~~txt
D:\SystemForJava\java_1.8\bin\java.exe
初始化原始二维数组:
0	0	0	0	0	0	0	0	0	0	0	
0	0	1	0	0	0	0	0	0	0	0	
0	0	0	2	0	0	0	0	0	0	0	
0	0	0	0	0	0	0	0	0	0	0	
0	0	0	0	0	0	0	0	0	0	0	
0	0	0	0	0	0	0	0	0	0	0	
0	0	0	0	0	0	0	0	0	0	0	
0	0	0	0	0	0	0	0	0	0	0	
0	0	0	0	0	0	0	0	0	0	0	
0	0	0	0	0	0	0	0	0	0	0	
0	0	0	0	0	0	0	0	0	0	0	
输出无数据稀疏数组：
0	0	0	
0	0	0	
0	0	0	
输出有数据稀疏数组：
11	11	2	
1	2	1	
2	3	2	
数组正在写出到文件...
写出成功...
开始读取磁盘存储的数组...
读取成功...
输出从磁盘读取的稀疏数组：
11	11	2	
1	2	1	
2	3	2	
输出转化后数组:
0	0	0	0	0	0	0	0	0	0	0	
0	0	1	0	0	0	0	0	0	0	0	
0	0	0	2	0	0	0	0	0	0	0	
0	0	0	0	0	0	0	0	0	0	0	
0	0	0	0	0	0	0	0	0	0	0	
0	0	0	0	0	0	0	0	0	0	0	
0	0	0	0	0	0	0	0	0	0	0	
0	0	0	0	0	0	0	0	0	0	0	
0	0	0	0	0	0	0	0	0	0	0	
0	0	0	0	0	0	0	0	0	0	0	
0	0	0	0	0	0	0	0	0	0	0	

Process finished with exit code 0
~~~

## 三、队列

### 1、概念

- 队列是一个有序列表，可以用数组或是链表来实现。
- 遵循先进先出的原则，即：先存入队列中的数据，要先取出，后存入的后取出。

### 2、数组模拟队列

  ![](https://i.bmp.ovh/imgs/2020/08/802b36762f7019ec.png) 

~~~ java
package com.mace.queue;

import java.util.Scanner;

/**
 * @author zhangxuhui
 * @email zxh_1633@163.com
 * @create 2020-08-06 9:45
 */
public class ArrayQueue {
    public static void main(String[] args) {
        //测试一把
        //创建一个队列
        Queue queue = new Queue(3);
        char key = ' ';
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        //输出一个菜单
        while(loop) {
            System.out.println("s(show): 显示队列");
            System.out.println("e(exit): 退出程序");
            System.out.println("a(add): 添加数据到队列");
            System.out.println("g(get): 从队列取出数据");
            System.out.println("h(head): 查看队列头的数据");
            key = scanner.next().charAt(0);
            switch (key) {
                case 's':
                    queue.show();
                    break;
                case 'a':
                    System.out.println("输出一个数");
                    int value = scanner.nextInt();
                    queue.add(value);
                    break;
                case 'g':
                    try {
                        int res = queue.get();
                        System.out.printf("取出的数据是%d\n", res);
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        int res = queue.getHead();
                        System.out.printf("队列头的数据是%d\n", res);
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }

        System.out.println("程序退出~~");
    }
}


/**
 * 使用数组模拟队列 queue实体
 * 当队列中已保存了数据时，头指针在头元素的前一位置
 * 尾指针指向队列中的尾元素
 */
class Queue{
    /**
     * 数组的最大容量
     */
    private int maxSize;
    /**
     * 队列头指针
     */
    private int front;
    /**
     * 队列尾指针
     */
    private int rear;
    /**
     *  存放数据
     */
    private int [] arr;


    public Queue(int maxSixze){
        this.maxSize = maxSixze;
        /**
         * 没有数据时，指向头元素的前一位置
         */
        this.front = -1;
        /**
         * 没有数据时，同front
         */
        this.rear = -1;
        /**
         * 初始化数组
         */
        arr = new int[maxSixze];
    }


    /**
     * 判断队列是否已满
     */
    public boolean isFull(){
        return maxSize-1 == rear;
    }

    /**
     * 判断队列是否为空
     */
    public boolean isEmpty(){
        return front == rear;
    }

    /**
     * 添加数据到队列
     */
    public void add(int num){
        if(isFull()){
            System.out.println("队列已满无法添加数据...");
            return;
        }

        /**
         * 尾指针后移
         */
        rear++;
        arr[rear] = num;
    }

    /**
     * 获取队列中的数据
     */
    public int get(){
        if(isEmpty()){
            throw  new RuntimeException("队列为空，无数据读取...");
        }

        /**
         * 头指针后移
         */
        front++;
        return arr[front];

    }

    /**
     * 显示队列中的所有数据
     */
    public void show(){
        if(isEmpty()){
            System.out.println("队列为空，无数据读取...");
            return;
        }
        for(int num : arr){
            System.out.println(num);
        }
    }

    /**
     * 获取队列的头数据
     */
    public int getHead(){
        if(isEmpty()){
            throw  new RuntimeException("队列为空，无数据读取...");
        }
        front++;
        return arr[front];
    }

    /**
     * 获取队列尾数据
     */
    public int getLast(){
        if(isEmpty()){
            throw  new RuntimeException("队列为空，无数据读取...");
        }
        return arr[rear];
    }
}

~~~
