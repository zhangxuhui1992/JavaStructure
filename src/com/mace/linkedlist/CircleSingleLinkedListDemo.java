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