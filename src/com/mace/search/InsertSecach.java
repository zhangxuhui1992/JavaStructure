package com.mace.search;

import java.util.Arrays;

/**
 * 插值查找
 *  有序数组
 * @author zhangxuhui
 * @email zxh_1633@163.com
 * @create 2020-09-03 16:57
 */
public class InsertSecach {
    public static void main(String[] args) {
        int [] arr = new int[100];
        for(int i = 0 ; i < 100;i++){
            arr[i] = i + 1;
        }

        int index = insertSearch(arr, 0, arr.length - 1, 100);

        System.out.println(index);
    }

    /**
     * 插值查找
     * @param arr 查找的数组
     * @param left 开始索引
     * @param right 末尾索引
     * @param val 查找的值
     * @return
     */
    public static int insertSearch(int [] arr,int left ,int right,int val){
        if(left > right || val < arr[left] || val > arr[right]){
            return -1;
        }
        int mid = left + (right - left) * (val - arr[left]) / (arr[right] - arr[left]);
        int midVal = arr[mid];
        if (val > midVal) { // 说明应该向右边递归
            return insertSearch(arr, mid + 1, right, val);
        } else if (val < midVal) { // 说明向左递归查找
            return insertSearch(arr, left, mid - 1, val);
        } else {
            return mid;
        }
    }
}
