package com.mace.sort;

import java.util.Arrays;

/**
 * 选择排序
 * 假设没轮的第一个元素为最小或最大值，与后面的每一个元素进行比较
 * 根据大小进行交换，交换数组长度-1轮，最后一个元素不用比较。
 * 80000数据 排序 耗时:2690.0毫秒
 *
 * @author zhangxuhui
 * @email zxh_1633@163.com
 * @create 2020-09-02 11:21
 */
public class SelectSort {
    public static void main(String[] args) {
        //int [] arr = new int[]{101, 34, 119, 1, -1, 90, 123};
        int[] arr = new int[80000];

        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 80000);
        }

        double strat = System.currentTimeMillis();

        selectSort(arr);

        double end = System.currentTimeMillis();

        System.out.println("耗时:" + (end - strat) + "毫秒");

    }

    private static void selectSort(int[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {

            /**
             * 假设最小值和其索引
             */
            int miniIndex = i;
            int mini = arr[i];

            for (int k = i + 1; k < arr.length; k++) {
                if (arr[k] > mini) {
                    miniIndex = k;
                    mini = arr[k];
                }
            }
            if (miniIndex != i) {
                arr[miniIndex] = arr[i];
                arr[i] = mini;

            }
        }

        /**
         * 比较耗时
         */
       /* for (int i = 0; i < arr.length - 1; i++) {
            for (int k = i + 1; k < arr.length; k++) {
                if (arr[i] > arr[k]) {
                    int temp = arr[k];
                    arr[k] = arr[i];
                    arr[i] = temp;
                }
            }
        }*/
    }
}
