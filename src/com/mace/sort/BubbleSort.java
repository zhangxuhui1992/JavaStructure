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
