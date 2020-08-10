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
        System.out.println("----------------------------------------");
        reverList(list.getHead());
        list.show();
        //list.update(new HearNode(2, "小吴", "星星"));



        //list.addById(h1);
        //list.addById(h3);
        //list.addById(h2);

        //list.show();

        //list.deleteById(2);
       // System.out.println("----------------------------------------");
        //list.show();

        //System.out.println("----------------------------------------");

        //System.out.println(getLength(list.getHead()));

        //System.out.println(getHearNodeBeyK(list.getHead(),2));
        //list.show();
        //System.out.println("----------------------------------------");

    }

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
             * 当前节点的next指向新头链表的next
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



    /**
     * 获取单链表的有效节点数量 参数 - 头节点
     * 不包含头节点
     */
    public static int getLength(HearNode head){
        if(head.getNext() == null){
            return 0;
        }

        int length = 0 ;

        HearNode temp = head.getNext();

        while(temp != null){
            length++;
            temp = temp.getNext();
        }

        return length;
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
     * 依次往节点的末尾添加
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


    public HearNode getHead() {
        return head;
    }

    public void setHead(HearNode head) {
        this.head = head;
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
