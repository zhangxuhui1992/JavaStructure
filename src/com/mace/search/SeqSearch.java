package com.mace.search;

/**
 * 线性查找
 * @author zhangxuhui
 * @email zxh_1633@163.com
 * @create 2020-09-03 15:25
 */
public class SeqSearch {
    public static void main(String[] args) {
        int [] arr = {1, 9, 11, -1, 34, 89};

        int index = seqSearch(arr,9);

        if(index == -1){
            System.out.println("未找到。。。");
        }else{
            System.out.println("查找的值对应的索引为："+index);
        }
    }

    public static int seqSearch(int[] arr, int val) {
        /**
         * 线性查找，从头到尾遍历
         */
        for(int i = 0 ; i < arr.length;i++){
            if(arr[i] == val){
                return i;
            }
        }
        return -1;
    }
}
