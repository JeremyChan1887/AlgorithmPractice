package hash;

import java.util.Scanner;
import java.util.LinkedHashSet;
import java.util.Iterator;

/*
提取不重复的整数
输入一个int型整数，按照从右向左的阅读顺序，返回一个不含重复数字的新的整数。

输入：
9876673
输出：
37689
 */
public class GetIntegerWithoutDuplicate {
    /*
        思路：
            从右到左遍历整数的每一位数字，如果该数字没出现过，就添加到结果整数中，并将其标记为已访问；如果已经出现过，则直接跳过
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int num = sc.nextInt();
            int[] flag = new int[10];
            int result = 0;
            while (num > 0) {
                if (flag[num % 10] == 0) {
                    flag[num % 10] ++;
                    result = result * 10 + (num % 10);
                }
                num /= 10;
            }
            System.out.println(result);
        }
    }

    /*
        思路：
            LinkedHashSet的不可重复性 & 添加有序性：
                1、从右到左遍历字符串，将每一个不同的数字按顺序添加到LinkedHashSet中
                2、从前到后遍历LinkedHashSet，组成数字
     */
    public static void main1(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        LinkedHashSet<Integer> set = new LinkedHashSet<>();
        for (int i=input.length()-1; i>=0; i--) {
            int num = Integer.valueOf(input.substring(i, i+1));
            set.add(num);
        }

        int result = 0;
        Iterator<Integer> itr = set.iterator();
        while (itr.hasNext()) {
            result = result * 10 + itr.next();
        }
        System.out.println(result);
    }
}