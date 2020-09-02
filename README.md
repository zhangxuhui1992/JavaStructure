数据结构和算法

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

<img src="https://i.bmp.ovh/imgs/2020/08/0eddf06a73f91027.png" style="zoom:80%;" />

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

### 2、数组简单模拟队列

  <img src="https://i.bmp.ovh/imgs/2020/08/802b36762f7019ec.png" style="zoom:80%;" /> 

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

~~~txt
D:\SystemForJava\java_1.8\bin\java.exe
s(show): 显示队列
e(exit): 退出程序
a(add): 添加数据到队列
g(get): 从队列取出数据
h(head): 查看队列头的数据
s
队列为空，无数据读取...
s(show): 显示队列
e(exit): 退出程序
a(add): 添加数据到队列
g(get): 从队列取出数据
h(head): 查看队列头的数据
a
输出一个数
20
s(show): 显示队列
e(exit): 退出程序
a(add): 添加数据到队列
g(get): 从队列取出数据
h(head): 查看队列头的数据
s
20
0
0
s(show): 显示队列
e(exit): 退出程序
a(add): 添加数据到队列
g(get): 从队列取出数据
h(head): 查看队列头的数据
a
输出一个数
30
s(show): 显示队列
e(exit): 退出程序
a(add): 添加数据到队列
g(get): 从队列取出数据
h(head): 查看队列头的数据
a
输出一个数
40
s(show): 显示队列
e(exit): 退出程序
a(add): 添加数据到队列
g(get): 从队列取出数据
h(head): 查看队列头的数据
s
20
30
40
s(show): 显示队列
e(exit): 退出程序
a(add): 添加数据到队列
g(get): 从队列取出数据
h(head): 查看队列头的数据
a
输出一个数
50
队列已满无法添加数据...
s(show): 显示队列
e(exit): 退出程序
a(add): 添加数据到队列
g(get): 从队列取出数据
h(head): 查看队列头的数据
g
取出的数据是20
s(show): 显示队列
e(exit): 退出程序
a(add): 添加数据到队列
g(get): 从队列取出数据
h(head): 查看队列头的数据
g
取出的数据是30
s(show): 显示队列
e(exit): 退出程序
a(add): 添加数据到队列
g(get): 从队列取出数据
h(head): 查看队列头的数据
g
取出的数据是40
s(show): 显示队列
e(exit): 退出程序
a(add): 添加数据到队列
g(get): 从队列取出数据
h(head): 查看队列头的数据
g
队列为空，无数据读取...
s(show): 显示队列
e(exit): 退出程序
a(add): 添加数据到队列
g(get): 从队列取出数据
h(head): 查看队列头的数据
h
队列为空，无数据读取...
s(show): 显示队列
e(exit): 退出程序
a(add): 添加数据到队列
g(get): 从队列取出数据
h(head): 查看队列头的数据

h
队列为空，无数据读取...
s(show): 显示队列
e(exit): 退出程序
a(add): 添加数据到队列
g(get): 从队列取出数据
h(head): 查看队列头的数据
h
队列为空，无数据读取...
s(show): 显示队列
e(exit): 退出程序
a(add): 添加数据到队列
g(get): 从队列取出数据
h(head): 查看队列头的数据
e
程序退出~~

Process finished with exit code 0
~~~

### 3、数组模拟环形队列

 <img src="https://i.bmp.ovh/imgs/2020/08/6ae7f7eca0913cfc.png" style="zoom:80%;" /> 

~~~java
package com.mace.queue;

import java.util.Scanner;

/**
 * @author zhangxuhui
 * @email zxh_1633@163.com
 * @create 2020-08-06 11:05
 */
public class CircleArrayQueue {
    public static void main(String[] args) {
        //测试一把 [] [] [] []
        //创建一个队列
        //说明设置4, 其队列的有效数据最大是3
        CircleQueue queue = new CircleQueue(4);
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
 * 数组模拟环形队列
 * front队列头指针 默认为0，指向当前元素
 * rear队列尾指针 默认为0,指向当前元素的后移位置
 */
class CircleQueue{
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

    public CircleQueue(int maxSize){
        this.maxSize = maxSize;
        arr = new int [maxSize];
    }


    /**
     * 判断队列是否已满
     */
    public boolean isFull(){
        return (rear + 1) % maxSize == front;
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
        //直接将数据加入
        arr[rear] = num;
        //将 rear 后移, 这里必须考虑取模
        rear = (rear + 1) % maxSize;

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
        int temp = arr[front];
        front = (front + 1) % maxSize;
        return temp;
    }

    /**
     * 显示队列中的所有数据
     */
    public void show(){
        if(isEmpty()){
            System.out.println("队列为空，无数据读取...");
            return;
        }
        /**
         * 从front头元素开始，取队列元素的个数
         */
        for (int i = front; i < front + getLength() ; i++) {
            System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]);
        }
    }

    /**
     * 获取队列的头数据
     */
    public int getHead(){
        if(isEmpty()){
            throw  new RuntimeException("队列为空，无数据读取...");
        }
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

    /**
     * 获取队列有效元素的个数
     */
    public int getLength(){
        return (rear + maxSize - front) % maxSize;
    }
}

~~~

## 四、链表

### 1、概念

​	链表是有序列表，它在内存中存储如下：

​	 <img src="https://i.bmp.ovh/imgs/2020/08/b49287473df48478.png" style="zoom:50%;" /> 

- 链表是以节点的方式来存储，是链式存储。
- 每一个节点包含data域，next域：指向下一个节点
- 链表的各个节点不一定是连续存储
- 链表分为带头节点的链表和没有头节点的链表，根据实际的需求来确定

带头节点的单链表逻辑图如下：<img src="https://i.bmp.ovh/imgs/2020/08/3495703a26e29189.png" style="zoom:50%;" /> 

### 2、单链表简单实现

​	添加节点时直接添加至节点的尾部

~~~ java
package com.mace.linkedlist;

/**
 * 简单链表实现
 * @author zhangxuhui
 * @email zxh_1633@163.com
 * @create 2020-08-06 15:38
 */
public class SingleLinkedListDemo {
    public static void main(String[] args) {
        HearNode h1 = new HearNode(1, "宋江", "及时雨");
        HearNode h2 = new HearNode(2, "吴用", "智多星");
        HearNode h3 = new HearNode(3, "林冲", "豹子头");

        SingleLinkedList list = new SingleLinkedList();
        list.add(h1);
        list.add(h2);
        list.add(h3);
        list.show();
    }
}

/**
 * 单链表 有头节点
 */
class SingleLinkedList{
    /**
     * 头节点
     */
    private HearNode head = new HearNode(-1, "", "");

    /**
     * 添加节点 头节点不变，遍历时使用临时变量记录最后的节点
     */

    public void add(HearNode node){
        HearNode temp = head;
        while(true){
            /**
             * 如果当前节点的下一个节点为null,则temp为最后一个节点
             * 退出循环
             */
            if(temp.getNext() == null){
                break;
            }else{
                //否则将temp后移，继续循环。
                temp = temp.getNext();
            }
        }
        /**
         * 当循环退出时，temp为链表的最后节点
         */
        temp.setNext(node);
    }

    /**
     * 显示链表
     */
    public void show(){
        if(head.getNext() != null){
            HearNode temp = head.getNext();
            while (true){
                if(temp!= null){
                    System.out.println(temp);
                }else{
                    return;
                }
                temp = temp.getNext();
            }
        }else{
            System.out.println("链表没有数据...");
        }
    }


}


class HearNode{
    /**
     * 编号
     */
    private int no;
    /**
     * 姓名
     */
    private String name;
    /**
     * 外号
     */
    private String nickName;
    /**
     * 下一节点
     */
    private HearNode next;


    public HearNode(int no,String name,String nickName){
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }



    @Override
    public String toString() {
        return "HearNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public HearNode getNext() {
        return next;
    }

    public void setNext(HearNode next) {
        this.next = next;
    }
}

~~~

安照英雄的排名进行添加

~~~ java
/**
     * 根据英雄的id排名进行添加
     * 如果id已存在则不能添加
     */
    public void addById(HearNode node){
        /**
         * 临时指针记录当前位置
         */
        HearNode temp = head;

        /**
         * id是否已存在的标志位
         */
        boolean flag = false;

        while(true){
            /**
             * 已经到链表的末尾
             */
            if(temp.getNext() == null){
                break;
            }
            /**
             * 找到合适的添加位置
             */
            if(temp.getNext().getNo() > node.getNo()){
                break;
            }else if(temp.getNext().getNo() == node.getNo()){
                flag = true;
                break;
            }
            /**
             * 如果以上都没有找到则指针后移
             */
            temp = temp.getNext();
        }

        if(flag){
            System.out.println("此英雄排名已存在 ID ="+ node.getNo());
        }else{
            node.setNext(temp.getNext());
            temp.setNext(node);
        }
    }
~~~

传入node节点，根据id，更新节点信息。

~~~ java
/**
     * 根据节点id,更新节点信息
     */
    public void update(HearNode node){

        if(head.getNext() == null){
            System.out.println("链表为空。。。");
            return;
        }

        HearNode temp = head;
        /**
         * 找到对应节点的标志位
         */
        boolean flag = false;

        while (true){
            if(temp.getNext() == null){
                break;
            }
            if(temp.getNo() == node.getNo()){
                flag = true;
                break;
            }
            temp = temp.getNext();
        }
        if(flag){
            temp.setName(node.getName());
            temp.setNickName(node.getNickName());
        }else{
            System.out.println("没有找到对应的更新对象，不能修改。");
        }
    }
~~~

根据节点id，删除节点。

~~~ java
 /**
     * 根据节点id，删除对应节点。
     */

    public void deleteById(int id){
        HearNode temp = head;
        /**
         * 用来标识是否找到
         */
        boolean flag = false;

        while(true){
            if(temp.getNext() == null){
                System.out.println("对应节点未找到...");
                break;
            }

            if(temp.getNext().getNo() == id){
                flag = true;
                break;
            }

            temp = temp.getNext();
        }

        if(flag){
            temp.setNext(temp.getNext().getNext());
        }

    }
~~~

### 3、练习：单链表中有效节点的个数

~~~ java
	/**
     * 获取单链表的有效节点数量
     * 不包含头节点
     */
    public static int getLength(HearNode node){
        if(node.getNext() == null){
            return 0;
        }

        int length = 0 ;

        HearNode temp = node.getNext();

        while(temp != null){
            length++;
            temp = temp.getNext();
        }

        return length;
    }
~~~

### 4、查找单链表中的倒数第k个节点

~~~ java
   /**
     * 查找单链表中的倒数第k个节点
     *
     * 参数 头节点 倒数k
     */
    public static HearNode getHearNodeBeyK(HearNode head,int k){

        if(head.getNext() == null){
            return null;
        }

        int size = getLength(head);

        if(k <= 0 || k > size){
            return null;
        }

        HearNode temp = head.getNext();

        for(int i = 0 ; i < size - k ;i++){
            temp = temp.getNext();
        }
        return temp;
    }
~~~

### 5、单链表的反转

思路：

- 新建头节点
- 遍历原始链表，让每一个节点添加到新链表的头节点的后面
- 将原始节点的头节点指向新的链表

~~~ java
  /**
     * 单链表的反转
     */
    public static void reverList(HearNode head){
        if(head.getNext() == null || head.getNext().getNext() == null){
            System.out.println("链表为空或只有一个节点无法反转");
        }

        /**
         * 新建头节点
         */
        HearNode newHead = new HearNode(0, "", "");
        /**
         * 当前节点的指针
         */
        HearNode temp = head.getNext();

        /**
         * 记录当前节点的下一个节点
         */
        HearNode next = null;

        /**
         * 遍历
         */
        while (temp != null){
            /**
             * next记录当前节点的下一个节点
             */
            next = temp.getNext();
            /**
             * 新链表当前节点的next指向新头链表的next
             */
            temp.setNext(newHead.getNext());
            /**
             * 新链表的头节点指向当前节点
             */
            newHead.setNext(temp);

            /**
             * 指针后移
             */
            temp = next;
        }

        head.setNext(newHead.getNext());

    }
~~~

### 6、单链表的逆序打印

思路：

- 使用栈的先进后出特点

~~~ java
	/**
     * 链表的逆序打印，没有改变原来链表的结构
     */
    public static void reversePrint(HearNode head){
        if(head.getNext() == null || head.getNext().getNext() == null){
            System.out.println("链表为空或只有一个节点，无法打印...");
            return;
        }

        HearNode temp = head.getNext();
        Stack<HearNode> stack = new Stack<>();
        while(temp != null){
            stack.push(temp);
            temp = temp.getNext();
        }

        while (stack.size() > 0){
            System.out.println(stack.pop());
        }
    }
~~~

### 7、双向链表

<img src="https://ftp.bmp.ovh/imgs/2020/08/9c58f7f066505252.png" style="zoom:80%;" /> 

- 遍历和单链表一样，只是可以向两个方向遍历。
- 添加-找到链表的最后节点temp.next = node,node.pre = temp
- 修改，找到对应的节点，修改相应的值即可。
- 删除，找到要删除的节点，temp.pre.next=temp.next,temp.next.pre=temp.pre。

~~~java
package com.mace.linkedlist;

/**
 * @author zhangxuhui
 * @email zxh_1633@163.com
 * @create 2020-08-11 15:10
 */
public class DoubleLinkedListDemo {
    public static void main(String[] args) {
        System.out.println("双向链表测试");

        HearNode2 h1 = new HearNode2(1, "宋江", "及时雨");
        HearNode2 h3 = new HearNode2(3, "吴用", "智多星");
        HearNode2 h8 = new HearNode2(8, "林冲", "豹子头");

        DoubleLinkedList list = new DoubleLinkedList();

        list.add(h1);
        list.add(h3);
        list.add(h8);

        list.show();
        System.out.println("----------------------------------------------------");
        list.update(new HearNode2(3, "小吴","星星"));
        list.show();
        System.out.println("----------------------------------------------------");
        list.deleteById(3);
        list.show();
        list.addById(new HearNode2(3, "鲁智深", "花和尚"));
        System.out.println("----------------------------------------------------");
        list.show();
    }
}

class DoubleLinkedList{
    /**
     * 头节点
     */
    private HearNode2 head = new HearNode2(-1, "", "");

    public HearNode2 getHead() {
        return head;
    }

    public void setHead(HearNode2 head) {
        this.head = head;
    }

    /**
     * 显示链表
     */
    public void show(){
        if(head.getNext() != null){
            HearNode2 temp = head.getNext();
            while (true){
                if(temp!= null){
                    System.out.println(temp);
                }else{
                    return;
                }
                temp = temp.getNext();
            }
        }else{
            System.out.println("链表没有数据...");
        }
    }

    /**
     * 依次往节点的末尾添加
     * 添加节点 头节点不变，遍历时使用临时变量记录最后的节点
     */

    public void add(HearNode2 node){
        HearNode2 temp = head;
        while(true){
            /**
             * 如果当前节点的下一个节点为null,则temp为最后一个节点
             * 退出循环
             */
            if(temp.getNext() == null){
                break;
            }else{
                //否则将temp后移，继续循环。
                temp = temp.getNext();
            }
        }
        /**
         * 当循环退出时，temp为链表的最后节点
         * 双向指向
         */
        temp.setNext(node);
        node.setPre(temp);
    }

    /**
     * 根据节点id,更新节点信息
     * 和单向链表一样
     */
    public void update(HearNode2 node){

        if(head.getNext() == null){
            System.out.println("链表为空。。。");
            return;
        }

        HearNode2 temp = head;
        /**
         * 找到对应节点的标志位
         */
        boolean flag = false;

        while (true){
            if(temp.getNext() == null){
                break;
            }
            if(temp.getNo() == node.getNo()){
                flag = true;
                break;
            }
            temp = temp.getNext();
        }
        if(flag){
            temp.setName(node.getName());
            temp.setNickName(node.getNickName());
        }else{
            System.out.println("没有找到对应的更新对象，不能修改。");
        }
    }


    /**
     * 根据节点id，删除对应节点。
     */

    public void deleteById(int id){

        if(head.getNext() == null){
            System.out.println("链表为空...");
            return;
        }

        /**
         * 用来标识是否找到
         */
        boolean flag = false;
        HearNode2 temp = head.getNext();

        while(true){
            if(temp == null){
                System.out.println("对应节点未找到...");
                break;
            }

            if(temp.getNo() == id){
                flag = true;
                break;
            }

            temp = temp.getNext();
        }

        if(flag){
            temp.getPre().setNext(temp.getNext());
            if(temp.getNext() != null){
                temp.getNext().setPre(temp.getPre());
            }
        }
    }


    /**
     * 根据英雄的id排名进行添加
     * 如果id已存在则不能添加
     */
    public void addById(HearNode2 node){
        /**
         * 临时指针记录当前位置
         */
        HearNode2 temp = head;

        /**
         * id是否已存在的标志位
         */
        boolean flag = false;

        while(true){
            /**
             * 已经到链表的末尾
             */
            if(temp.getNext() == null){
                break;
            }
            /**
             * 找到合适的添加位置
             */
            if(temp.getNext().getNo() > node.getNo()){
                break;
            }else if(temp.getNext().getNo() == node.getNo()){
                flag = true;
                break;
            }
            /**
             * 如果以上都没有找到则指针后移
             */
            temp = temp.getNext();
        }

        if(flag){
            System.out.println("此英雄排名已存在 ID ="+ node.getNo());
        }else{
            HearNode2 next = temp.getNext();
            temp.setNext(node);
            node.setPre(temp);
            node.setNext(next);
            next.setPre(node);
        }
    }

}


class HearNode2{
    /**
     * 编号
     */
    private int no;
    /**
     * 姓名
     */
    private String name;
    /**
     * 外号
     */
    private String nickName;
    /**
     * 下一节点
     */
    private HearNode2 next;

    /**
     * 前一个节点
     */
    private HearNode2 pre;

    public HearNode2(int no,String name,String nickName){
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }



    @Override
    public String toString() {
        return "HearNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public HearNode2 getNext() {
        return next;
    }

    public void setNext(HearNode2 next) {
        this.next = next;
    }

    public HearNode2 getPre() {
        return pre;
    }

    public void setPre(HearNode2 pre) {
        this.pre = pre;
    }
}

~~~

### 8、单向环形链表

​	Josephu(约瑟夫、约瑟夫环) 问题
​	Josephu 问题为：设编号为 1，2，… n 的 n 个人围坐一圈，约定编号为 k（1<=k<=n）的人从 1 开始报数，数
到 m 的那个人出列，它的下一位又从 1 开始报数，数到 m 的那个人又出列，依次类推，直到所有人出列为止，由
此产生一个出队编号的序列。

​	提示：用一个不带头结点的循环链表来处理 Josephu 问题：先构成一个有 n 个结点的单循环链表，然后由 k 结点起从 1 开始计数，计到 m 时，对应结点从链表中删除，然后再从被删除结点的下一个结点又从 1 开始计数，直
到最后一个结点从链表中删除算法结束。

问题分析：

​					<img src="https://ftp.bmp.ovh/imgs/2020/08/b03bca2ef734391c.png" style="zoom:50%;" />	      

 代码思路：<img src="https://ftp.bmp.ovh/imgs/2020/08/f30494cd6313459b.png" style="zoom:50%;" /> 

出圈思路：<img src="https://ftp.bmp.ovh/imgs/2020/08/f495a6caaa7dd2d6.png" style="zoom:50%;" />

 ~~~java
package com.mace.linkedlist;

/**
 * @author zhangxuhui
 * @email zxh_1633@163.com
 * @create 2020-08-12 10:39
 */
public class CircleSingleLinkedListDemo {
    public static void main(String[] args) {
        CircleSingleLinkedList list = new CircleSingleLinkedList();
        list.add(5);
        list.show();
        System.out.println("--------------------------------------");
        list.count(1,2,5);
    }
}


class CircleSingleLinkedList{
    /**
     * 头节点
     */
    private Boy first = null;

    public Boy getFirst() {
        return first;
    }

    public void setFirst(Boy first) {
        this.first = first;
    }

    /**
     * 添加节点
     */
    public void add(int num){
        if(num < 1){
            System.out.println("num值不正确");
            return;
        }

        Boy cur = null;

        for(int i = 1 ; i <= num ; i ++){
            Boy boy = new Boy(i);
            if(i == 1){
                first = boy;
                first.setNext(first);
                cur = first;
            }else{
                cur.setNext(boy);
                boy.setNext(first);
                cur = boy;
            }
        }
    }


    /**
     * 遍历当前的环形链表
     */
    public void show(){
        if(first == null){
            System.out.println("链表为空，无法遍历");
            return;
        }

        Boy temp = first;

        while (true){
            System.out.println(temp);

            if(temp.getNext() == first){
                return;
            }

            temp = temp.getNext();
        }
    }

    /**
     * 出圈代码实现
     * @param startNo 开始的编号
     * @param count 数多少下
     * @param nums  总共有多少个boy
     */
    public void count(int startNo,int count,int nums){
        /**
         * 先对数据进行校验
         */
        if(first == null || startNo < 1 || startNo > nums){
            System.out.println("输入参数有误，请重新输入...");
            return;
        }

        Boy helper = first;

        while (true){
            if (helper.getNext() == first){
                break;
            }
            helper = helper.getNext();
        }

        for(int i = 0 ; i < startNo -1 ; i++){
            first = first.getNext();
            helper = helper.getNext();
        }

        while (true){
            if(helper == first){
                break;
            }
            for(int k = 0 ; k< count -1 ;k++){
                first = first.getNext();
                helper = helper.getNext();
            }

            System.out.println(first);

            first = first.getNext();
            helper.setNext(first);
        }
        System.out.println("圈中最后剩"+first);
    }

}

/**
 * 孩子实体
 */
class Boy{

    private int no;
    private Boy next;

    public Boy(int no){
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Boy{" +
                "no=" + no +
                '}';
    }
}
 ~~~

## 七、栈

### 1、栈的介绍

- 栈是一个 先入后出(FILO-First In Last Out) 的有序列表
- 栈(stack) 是限制线性表中元素的插入和删除只能在线性表的同一端进行的一种特殊线性表。 允许插入和删除的一端，为 变化的一端，称为栈顶(Top) ，另一端为 固定的一端，称为栈底(Bottom)。
- 根据栈的定义可知 ， 最先放入栈中元素在栈底 ， 最后放入的元素在栈顶 ， 而删除元素刚好相反 ， 最后放入的元素最先删除，最先放入的元素最后删除。

 ![](https://ftp.bmp.ovh/imgs/2020/08/ed4bca9d03c8c394.png) 

### 2、栈的应用场景

- 子程序的调用：在跳往子程序前，会先将下个指令的地址存到堆栈中，直到子程序执行完后再将地址取出，以
  回到原来的程序中。
- 处理递归调用：和子程序的调用类似，只是除了储存下一个指令的地址外，也将参数、区域变量等数据存入堆
  栈中。
- 表达式的转换[中缀表达式转后缀表达式]与求值(实际解决)。
- 二叉树的遍历。
- 图形的深度优先(depth 一 first)搜索法。

### 3、数组模拟栈

<img src="https://ftp.bmp.ovh/imgs/2020/08/bcb21a92acd70476.png" style="zoom:50%;" /> 

~~~java
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

~~~

### 4、栈实现综合计算器(中缀表达式)

 <img src="https://ftp.bmp.ovh/imgs/2020/08/9bff7ca0e6a554b7.png" style="zoom:50%;" /> 

~~~ java
package com.mace.stack;

/**
 * @author zhangxuhui
 * @email zxh_1633@163.com
 * @create 2020-08-18 10:26
 */
public class Calculator {
    public static void main(String[] args) {
        String expression = "7*2*2-5+1-5+3-4";

        //创建两个栈，一个数栈、一个符号栈
        ArrayStack2 numStack = new ArrayStack2(10);
        ArrayStack2 operStack = new ArrayStack2(10);

        //定义需要的相关变量
        int index = 0;//用于扫描
        int num1 = 0;
        int num2 = 0;
        int oper = 0;
        int res = 0;
        char ch = ' '; //将每次扫描得到char保存到ch
        String keepNum = ""; //用于拼接 多位数


        while(true){
            //依次得到expression 的每一个字符
            ch = expression.substring(index, index+1).charAt(0);

            if(operStack.isOper(ch)){//如果是运算符
                //判断当前的符号栈是否为空
                if(!operStack.isEmpty()) {
                    //如果符号栈有操作符，就进行比较,如果当前的操作符的优先级小于或者等于栈中的操作符,就需要从数栈中pop出两个数,
                    //在从符号栈中pop出一个符号，进行运算，将得到结果，入数栈，然后将当前的操作符入符号栈
                    if(operStack.priority(ch) <= operStack.priority(operStack.peek())) {
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        oper = operStack.pop();
                        res = numStack.cal(num1, num2, oper);
                        //把运算的结果如数栈
                        numStack.push(res);
                        //然后将当前的操作符入符号栈
                        operStack.push(ch);
                    } else {
                        //如果当前的操作符的优先级大于栈中的操作符， 就直接入符号栈.
                        operStack.push(ch);
                    }
                }else {
                    //如果为空直接入符号栈..
                    operStack.push(ch); // 1 + 3
                }
            }else{//是数字
                //处理多位数
                keepNum += ch;

                //如果ch已经是expression的最后一位，就直接入栈
                if (index == expression.length() - 1) {
                    numStack.push(Integer.parseInt(keepNum));
                }else{

                    //判断下一个字符是不是数字，如果是数字，就继续扫描，如果是运算符，则入栈
                    //注意是看后一位，不是index++
                    if (operStack.isOper(expression.substring(index+1,index+2).charAt(0))) {
                        //如果后一位是运算符，则入栈 keepNum = "1" 或者 "123"
                        numStack.push(Integer.parseInt(keepNum));
                        //重要的!!!!!!, keepNum清空
                        keepNum = "";

                    }
                }
            }

            //让index + 1, 并判断是否扫描到expression最后.
            index++;
            if (index >= expression.length()) {
                break;
            }
        }

        //当表达式扫描完毕，就顺序的从 数栈和符号栈中pop出相应的数和符号，并运行.
        while(true) {
            //如果符号栈为空，则计算到最后的结果, 数栈中只有一个数字【结果】
            if(operStack.isEmpty()) {
                break;
            }
            num1 = numStack.pop();
            num2 = numStack.pop();
            oper = operStack.pop();
            res = numStack.cal(num1, num2, oper);
            numStack.push(res);//入栈
        }
        //将数栈的最后数，pop出，就是结果
        int res2 = numStack.pop();
        System.out.printf("表达式 %s = %d", expression, res2);

    }
}



/**
 * 数组模拟栈
 */
class ArrayStack2{
    private int maxSize;//栈的大小
    private int[] arr;//数组模拟栈
    private int top=-1;//top表示栈顶，默认为-1

    public ArrayStack2(int maxSize){
        this.maxSize = maxSize;
        arr = new int[this.maxSize];
    }

    //返回运算符的优先级，优先级是程序员来确定, 优先级使用数字表示
    //数字越大，则优先级就越高.
    public int priority(int oper) {
        if(oper == '*' || oper == '/'){
            return 1;
        } else if (oper == '+' || oper == '-') {
            return 0;
        } else {
            return -1; // 假定目前的表达式只有 +, - , * , /
        }
    }
    //判断是不是一个运算符
    public boolean isOper(char val) {
        return val == '+' || val == '-' || val == '*' || val == '/';
    }
    //计算方法
    public int cal(int num1, int num2, int oper) {
        int res = 0; // res 用于存放计算的结果
        switch (oper) {
            case '+':
                res = num1 + num2;
                break;
            case '-':
                res = num2 - num1;// 注意顺序
                break;
            case '*':
                res = num1 * num2;
                break;
            case '/':
                res = num2 / num1;
                break;
            default:
                break;
        }
        return res;
    }

    //增加一个方法，可以返回当前栈顶的值, 但是不是真正的pop
    public int peek(){
        return arr[top];
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
~~~

### 5、逆波兰计算器

要求完成如下任务:

- 输入一个逆波兰表达式(后缀表达式)，使用栈(Stack), 计算其结果
- 支持小括号和多位数整数，因为这里我们主要讲的是数据结构，因此计算器进行简化，只支持 对整数的计算。
- 思路分析

<img src="https://ftp.bmp.ovh/imgs/2020/08/7729d86de7a4f807.png" style="zoom:50%;" /> 

~~~ java
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

~~~

### 6、中缀表达式转后缀表达式

 ![](https://ftp.bmp.ovh/imgs/2020/08/3dc443bcfda110d7.png) 

~~~ java
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

~~~

## 八、递归

### 1、概念

​	递归就是方法自己调用自己,每次调用时 传入不同的变量.递归有助于编程者解决复杂的问题,同时可以让代码变得简洁。

<img src="https://ftp.bmp.ovh/imgs/2020/08/2219f60ed70b169b.png" style="zoom:50%;" /> 

### 2、递归能解决什么样的问题

- 各种数学问题如: 8 皇后问题 , 汉诺塔, 阶乘问题, 迷宫问题, 球和篮子的问题(google 编程大赛)
- 各种算法中也会使用到递归，比如快排，归并排序，二分查找，分治算法等。
- 将用栈解决的问题-->递归代码比较简洁

### 3、递归需要遵守的重要规则

- 执行一个方法时，就创建一个新的受保护的独立空间(栈空间)。
- 方法的局部变量是独立的，不会相互影响, 比如 n 变量。
- 如果方法中使用的是引用类型变量(比如数组)，就会共享该引用类型的数据。
- 递归 必须向退出递归的条件逼近，否则就是无限递归,出现 StackOverflowError。
- 当一个方法执行完毕，或者遇到 return，就会返回， 遵守谁调用，就将结果返回给谁，同时当方法执行完毕或
  者返回时，该方法也就执行完毕。

### 4、迷宫问题

  <img src="https://ftp.bmp.ovh/imgs/2020/09/9d2388fcfa2a529e.png" style="zoom:50%;" /> 

~~~java
package com.mace.recursion;
/**
 * 迷宫问题
 * @author zhangxuhui
 * @email zxh_1633@163.com
 * @create 2020-09-01 9:44
 */
public class MiGong {
    public static void main(String[] args) {
        /**
         * 创建二维数组，模拟皇宫
         * 1代表墙，填充数组
         */
        int [][] arr = new int[8][7];

        for(int i = 0 ;i<7;i++){
            arr[0][i] = 1;
            arr[7][i] = 1;
        }

        for(int i = 1 ; i < 7 ;i++){
            arr[i][0] = 1;
            arr[i][6] = 1;
        }

        arr[3][1] = 1;
        arr[3][2] = 1;

        /**
         * 迷宫初始化
         */
        System.out.println("迷宫初始化");
        ArrtoString(arr);
        setWay2(arr,1, 1);
        System.out.println("路径查找后");
        ArrtoString(arr);
    }

    /**
     * 二维数组打印
     */
    public static void ArrtoString(int [][] arr){
        for(int i = 0 ;i < arr.length;i++){
            for(int k = 0 ;k <arr[i].length;k++){
                System.out.print(arr[i][k]+"\t");
            }
            System.out.println();
        }
    }
    /**
     * 使用递归回溯来给小球找路
     * 说明
     * 	1. map 表示地图
     * 	2. i,j 表示从地图的哪个位置开始出发 (1,1)
     * 	3. 如果小球能到 map[6][5] 位置，则说明通路找到.
     * 	4. 约定： 当map[i][j] 为 0 表示该点没有走过 当为 1 表示墙  ； 2 表示通路可以走 ； 3 表示该点已经走过，但是走不通
     * 	5. 在走迷宫时，需要确定一个策略(方法) 下->右->上->左 , 如果该点走不通，再回溯
     *
     * @param map 地图
     * @param i 查找位置的坐标
     * @param j
     * @return 如果找到通路，就返回true, 否则返回false
     */
    public static boolean getWay(int [][] map,int i,int j){
        if(map[6][5] == 2) { // 通路已经找到ok
            return true;
        } else {
            if(map[i][j] == 0) { //如果当前这个点还没有走过
                //按照策略 下->右->上->左  走
                map[i][j] = 2; // 假定该点是可以走通.
                if(getWay(map, i+1, j)) {//向下走
                    return true;
                } else if (getWay(map, i, j+1)) { //向右走
                    return true;
                } else if (getWay(map, i-1, j)) { //向上
                    return true;
                } else if (getWay(map, i, j-1)){ // 向左走
                    return true;
                } else {
                    //说明该点是走不通，是死路
                    map[i][j] = 3;
                    return false;
                }
            } else { // 如果map[i][j] != 0 , 可能是 1， 2， 3
                return false;
            }
        }
    }
    //修改找路的策略，改成 上->右->下->左
    public static boolean setWay2(int[][] map, int i, int j) {
        if(map[6][5] == 2) { // 通路已经找到ok
            return true;
        } else {
            if(map[i][j] == 0) { //如果当前这个点还没有走过
                //按照策略 上->右->下->左
                map[i][j] = 2; // 假定该点是可以走通.
                if(setWay2(map, i-1, j)) {//向上走
                    return true;
                } else if (setWay2(map, i, j+1)) { //向右走
                    return true;
                } else if (setWay2(map, i+1, j)) { //向下
                    return true;
                } else if (setWay2(map, i, j-1)){ // 向左走
                    return true;
                } else {
                    //说明该点是走不通，是死路
                    map[i][j] = 3;
                    return false;
                }
            } else { // 如果map[i][j] != 0 , 可能是 1， 2， 3
                return false;
            }
        }
    }
}

~~~

### 5、八皇后问题

 <img src="https://ftp.bmp.ovh/imgs/2020/09/5828abcee3563ad8.png" style="zoom:50%;" /> 

​		八皇后问题，是一个古老而著名的问题，是回溯算法的典型案例。该问题是国际西洋棋棋手马克斯·贝瑟尔于
1848 年提出：在 8×8 格的国际象棋上摆放八个皇后，使其不能互相攻击，即： 任意两个皇后都不能处于同一行 
同一列或同一斜线上，问有多少种摆法(92)。

​		思路分析：

-  第一个皇后先放第一行第一列
-  第二个皇后放在第二行第一列、然后判断是否 OK， 如果不 OK，继续放在第二列、第三列、依次把所有列都
   放完，找到一个合适
-  继续第三个皇后，还是第一列、第二列……直到第 8 个皇后也能放在一个不冲突的位置，算是找到了一个正确解
-  当得到一个正确解时，在栈回退到上一个栈时，就会开始回溯，即将第一个皇后，放到第一列的所有正确解，
   全部得到.
-  然后回头继续第一个皇后放第二列，后面继续循环执行 1,2,3,4 的步骤

说明：理论上应该创建一个二维数组来表示棋盘，但是实际上可以通过算法，用一个一维数组即可解决问题. arr[8] ={0 , 4, 7, 5, 2, 6, 1, 3} //对应 arr 下标 表示第几行，即第几个皇后，arr[i] = val , val 表示第 i+1 个皇后，放在第 i+1行的第 val+1 列。

代码实现：

~~~ java
package com.mace.recursion;

import java.util.zip.DeflaterOutputStream;

/**
 * 八皇后问题
 * @author zhangxuhui
 * @email zxh_1633@163.com
 * @create 2020-09-01 14:23
 */
public class BaHuangHou {
    //定义一个max表示共有多少个皇后
    int max = 8;
    //定义数组array, 保存皇后放置位置的结果,比如 arr = {0 , 4, 7, 5, 2, 6, 1, 3}
    int[] array = new int[max];
    static int count = 0;
    static int judgeCount = 0;
    public static void main(String[] args) {
        //测试一把 ， 8皇后是否正确
        double start = System.currentTimeMillis();
        BaHuangHou queue8 = new BaHuangHou();
        queue8.check(0);
        System.out.printf("一共有%d解法", count);
        System.out.printf("一共判断冲突的次数%d次", judgeCount); // 1.5w
        double end = System.currentTimeMillis();
        System.out.println("耗时："+(end-start)+"mm");

    }



    //编写一个方法，放置第n个皇后
    //特别注意： check 是 每一次递归时，进入到check中都有  for(int i = 0; i < max; i++)，因此会有回溯
    private void check(int n) {
        if(n == max) {  //n = 8 , 其实8个皇后就既然放好
            print();
            return;
        }

        //依次放入皇后，并判断是否冲突
        for(int i = 0; i < max; i++) {
            //先把当前这个皇后 n , 放到该行的第1列
            array[n] = i;
            //判断当放置第n个皇后到i列时，是否冲突
            if(judge(n)) { // 不冲突
                //接着放n+1个皇后,即开始递归
                check(n+1); //
            }
            //如果冲突，就继续执行 array[n] = i; 即将第n个皇后，放置在本行得 后移的一个位置
        }
    }

    //查看当我们放置第n个皇后, 就去检测该皇后是否和前面已经摆放的皇后冲突
    /**
     *
     * @param n 表示第n个皇后
     * @return
     */
    private boolean judge(int n) {
        judgeCount++;
        for(int i = 0; i < n; i++) {
            // 说明
            //1. array[i] == array[n]  表示判断 第n个皇后是否和前面的n-1个皇后在同一列
            //2. Math.abs(n-i) == Math.abs(array[n] - array[i]) 表示判断第n个皇后是否和第i皇后是否在同一斜线
            // n = 1  放置第 2列 1 n = 1 array[1] = 1
            // Math.abs(1-0) == 1  Math.abs(array[n] - array[i]) = Math.abs(1-0) = 1
            //3. 判断是否在同一行, 没有必要，n 每次都在递增
            if(array[i] == array[n] || Math.abs(n-i) == Math.abs(array[n] - array[i]) ) {
                return false;
            }
        }
        return true;
    }

    //写一个方法，可以将皇后摆放的位置输出
    private void print() {
        count++;
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

}

~~~

##  九、排序

### 1、介绍

​	排序也称排序算法(SortAlgorithm)，排序是将 一组数据，依 指定的顺序进行 排列的过程。

### 2、排序的分类

- 内部排序:
  指将需要处理的所有数据都加载到 内部存储器( 内存)中进行排序。
- 外部排序法：
  数据量过大，无法全部加载到内存中，需要借助 外部存储( 文件等)进行排序。

<img src="https://ftp.bmp.ovh/imgs/2020/09/d385cd8e2d3d5882.png" style="zoom:50%;" /> 

### 3、算法的时间复杂度

度量一个程序(算法)执行时间的两种方法：

​		事后统计的方法：这种方法可行, 但是有两个问题：一是要想对设计的算法的运行性能进行评测，需要实际运行该程序；二是所得时间的统计量依赖于计算机的硬件、软件等环境因素, 这种方式，要在同一台计算机的相同状态下运行，才能比较那个算法速度更快。

​		事前估算的方法：通过分析某个算法的 时间复杂度来判断哪个算法更优.

时间频度：

​		一个算法花费的时间与算法中语句的执行次数成正比例，哪个算法中语句执行次数多，它花费时间就多。 一个算法中的语句执行次数称为语句频度或时间频度。记为 T(n)。

​		T(n) = 2n +20; //忽略常数项

​		T(n) = 2n^2+3n+10 //忽略低次项

​		T(n) = 5n^2+7n //忽略系数

算法的时间复杂度：

- 一般情况下， 算法中的基本操作语句的重复执行次数是问题规模 n 的某个函数，用 T(n)表示，若有某个辅
  助函数 f(n)，使得当 n 趋近于无穷大时，T(n) / f(n) 的极限值为不等于零的常数，则称 f(n)是 T(n)的同数量级函数。记作 T(n)= Ｏ( f(n) )，称Ｏ( f(n) ) 为算法的渐进时间复杂度，简称时间复杂度。

-  T(n) 不同，但时间复杂度可能相同。 如：T(n)=n²+7n+6 与 T(n)=3n²+2n+2 它们的 T(n) 不同，但时间复杂
  度相同，都为 O(n² )。

-  计算时间复杂度的方法：

  ​	用常数 1 代替运行时间中的所有加法常数 T(n)=n²+7n+6 => T(n)=n²+7n+1

  ​	修改后的运行次数函数中，只保留最高阶项 T(n)=n²+7n+1 => T(n) = n²

  ​	去除最高阶项的系数 T(n) = n² => T(n) = n² => O(n²)

  常见的时间复杂度

  - 常数阶 O(1)

  - 对数阶 O(log2n)

  - 线性阶 O(n)

  - 线性对数阶 O(nlog2n)

  - 平方阶 O(n^2)

  - 立方阶 O(n^3)

  - k 次方阶 O(n^k)

  - 指数阶 O(2^n)

     <img src="https://ftp.bmp.ovh/imgs/2020/09/2d05a5c5da28f179.png" style="zoom:50%;" /> 

说明：常见的算法时间复杂度由小到大依次为：Ο(1)＜Ο(log2n)＜Ο(n)＜Ο(nlog2n)＜Ο(n2)＜Ο(n3)＜ Ο(nk) ＜
Ο(2n) ，随着问题规模 n 的不断增大，上述时间复杂度不断增大，算法的执行效率越低

平均时间复杂度和最坏时间复杂度：

- 平均时间复杂度是指所有可能的输入实例均以等概率出现的情况下，该算法的运行时间。

- 最坏情况下的时间复杂度称最坏时间复杂度。 一般讨论的时间复杂度均是最坏情况下的时间复杂度。这样做的原因是：最坏情况下的时间复杂度是算法在任何输入实例上运行时间的界限，这就保证了算法的运行时间不会比最坏情况更长。

- 平均时间复杂度和最坏时间复杂度是否一致，和算法有关。

   <img src="https://ftp.bmp.ovh/imgs/2020/09/5787bfba28206185.png" style="zoom:50%;" /> 

### 4、算法的空间复杂度

- 类似于时间复杂度的讨论，一个算法的空间复杂度(Space Complexity)定义为该算法所耗费的存储空间，它也是问题规模 n 的函数。
- 空间复杂度(Space Complexity)是对一个算法在运行过程中临时占用存储空间大小的量度。有的算法需要占的
  临时工作单元数与解决问题的规模 n 有关，它随着 n 的增大而增大，当 n 较大时，将占用较多的存储单元，例如快速排序和 归并排序算法, 基数排序就属于这种情况。
- 在做算法分析时，主要讨论的是时间复杂度。 从用户使用体验上看 ， 更看重的程序执行的速度。一些缓存产品(redis, memcache)和算法(基数排序)。

### 5、冒泡排序

基本思想：

​	通过对待排序序列从前向后（从下标较小的元素开始）, 依次比较相邻元素的值，若发现逆序则交换，使值较大的元素逐渐从前移向后部，就象水底下的气泡一样逐渐向上冒。

优化：

​	因为排序的过程中，各元素不断接近自己的位置， 如果一趟比较下来没有进行过交换 ， 就说明序列有序，因此要在排序过程中设置一个标志 flag 判断元素是否进行过交换。从而减少不必要的比较。(这里说的优化，可以在冒泡排序写好后，在进行)。

​	 <img src="https://ftp.bmp.ovh/imgs/2020/09/929e3936ef87064e.png" style="zoom: 80%;" /> 

小结上面的图解过程:
(1) 一共进行 数组的大小-1 次 大的循环
(2)每一趟排序的次数在逐渐的减少
(3) 如果我们发现在某趟排序中，没有发生一次交换， 可以提前结束冒泡排序。这个就是优化

~~~ java
package com.mace.sort;

import java.util.Arrays;

/**
 * 冒泡排序：
 * 每相邻的两个元素之间进行比较交换，一轮下来，就得到最大或最小的元素
 * 比较数组长度-1轮，因为剩最后一个元素时，再不用比较。
 * 80000数据 排序耗时：12459.0毫秒
 * @author zhangxuhui
 * @email zxh_1633@163.com
 * @create 2020-09-02 10:00
 */
public class BubbleSort {
    public static void main(String[] args) {
        //int[] arr = new int[]{3, 9, -1, 10, 20};

        int [] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] =(int)(Math.random()*80000);
        }

        double start = System.currentTimeMillis();

        bubbleSort(arr);

        double end = System.currentTimeMillis();

        System.out.println("耗时："+(end-start)+"毫秒");


    }

    public static void bubbleSort(int [] arr){
        /**
         * 定义零时变量用于交换
         *  外层循环控制比较的轮数
         *  内层循环控制每两个元素之间的比较
         */
        int temp;

        /**
         * 定义标志位，记录是否进行交换
         */
        boolean falg = false;

        for (int i = 0; i < arr.length - 1; i++) {
            for (int k = 0; k < arr.length - 1 - i; k++) {
                if (arr[k] > arr[k + 1]) {
                    falg = true;
                    temp = arr[k + 1];
                    arr[k + 1] = arr[k];
                    arr[k] = temp;
                }
            }
            //System.out.println("第" + (i + 1) + "轮比较");
            //System.out.println(Arrays.toString(arr));

            /**
             * 如果进行了交换则将flag进行重置
             * 如果一轮后没有进行交换，则从数组已有序，结束循环即可。
             */
            if (falg) {
                falg = false;
            } else {
                break;
            }
        }
    }
}

~~~

