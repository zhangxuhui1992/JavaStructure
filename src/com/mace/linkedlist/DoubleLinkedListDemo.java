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




