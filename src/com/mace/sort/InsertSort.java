package com.mace.sort;

import java.util.Arrays;

/**
 * 插入排序
 *  把第一个元素认为为有序列表，剩余元素认为为无序列表
 *  取出无序列表中的元素，添加到有序列表的合适位置
 *
 *  当前元素和它前面的元素进行比较，如果按升序，当前元素小于
 *  它前面的元素，则进行位置交换，继续向前比较，直到找到当前元素
 *  大于它前面的元素的位置，即为正确位置。
 *
 *  耗时：1511.0毫秒
 * @author zhangxuhui
 * @email zxh_1633@163.com
 * @create 2020-09-02 14:30
 */
public class InsertSort {
    public static void main(String[] args) {
        //int [] arr = {101, 34, 119, 1, -1, 89};

        int [] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] =(int)(Math.random()*80000);
        }

        double start = System.currentTimeMillis();

        insertSort2(arr);

        double end = System.currentTimeMillis();
        System.out.println("耗时："+(end-start)+"毫秒");

        //System.out.println(Arrays.toString(arr));
    }

    private static void insertSort(int[] arr) {
        for(int i = 1 ; i < arr.length ; i++){

            int insertVal = arr[i];
            /**
             * 即 arr[1]的前面这个数的下标
             *  insertIndex >= 0 保证在给insertVal找插入位置，不越界
             *  insertVal < arr[insertIndex] 待插入的数，还没有找到插入位置
             *  就需要将 arr[insertIndex] 后移
             *  insertVal待插入的值
             *  有序列表中的比较的值
             *
             */
            int insertIndex = i - 1 ;
            while (insertIndex >= 0 && insertVal < arr[insertIndex]){
                arr[insertIndex+1] = arr[insertIndex];
                insertIndex--;
            }
            arr[insertIndex + 1] = insertVal;
        }
    }

    private static void insertSort2(int[] arr) {
        for(int i = 1 ; i < arr.length;i++){
            int j = i;
            while (j>0){
                if(arr[j] < arr[j-1]){
                    int temp = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = temp;
                    j--;
                }else{
                    break;
                }
            }
        }
    }
}
