package com.mace.tree;

/**
 * 需求: 给你一个数组 {1,2,3,4,5,6,7}，要求以二叉树前序遍历的方式进行遍历。 前序遍历的结果应当为
 * 1,2,4,5,3,6,7
 * @author zhangxuhui
 * @email zxh_1633@163.com
 * @create 2020-09-07 9:58
 */
public class ArrayBinaryTreeDemo {
    public static void main(String[] args) {
        int [] arr = {1,2,3,4,5,6,7};
        ArrayBinaryTree tree = new ArrayBinaryTree(arr);
        /**
         * 输出1245367
         */
        tree.preOrder(0);
    }
}

class ArrayBinaryTree{
    private int [] arr;

    public ArrayBinaryTree(int[] arr) {
        this.arr = arr;
    }

    /**
     * 完成顺序二叉树的前序遍历
     * @param index 数组下标 起始索引
     */
    public void preOrder(int index){
        if(arr == null || arr.length == 0){
            System.out.println("数组为空，无法遍历输出");
        }

        System.out.println(arr[index]);

        if((2*index+1) < arr.length){
            preOrder(2*index+1);
        }

        if((2*index+2) < arr.length){
            preOrder(2*index +2);
        }
    }
}
