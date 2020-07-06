package hash;

import java.util.Scanner;
/*
写出一个程序，接受一个由字母和数字组成的字符串，和一个字符，然后输出输入字符串中含有该字符的个数。不区分大小写。

输入：
ABCDEF
A
输出：
1

字符串 & 字符：
1、字符串的大小写互转
str = str.toUpperCase()、str = str.toLowerCase()
2、字符串 -> 字符
char[] arr = str.toCharArray()、 char ch = str.charAt(index)
3、字符 -> 字符串
String str = String.valueOf(ch)
 */

public class CountCharacterOccurrenceTimes {

    /*
        思路：
            正则表达式：
                1、将输入字符串、目标字符变为统一的大写
                2、遍历字符串，利用正则表达式将字符串中，所有不为目标字符的字符都替换成空字符
                3、遍历完成之后，字符串只剩下目标字符，其长度就是目标字符的出现次数
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String input = sc.nextLine().toUpperCase();
            String c = sc.nextLine().toUpperCase();

            System.out.println(input.replaceAll("[^" + c + "]", "").length());
        }
    }

    /*
        思路：
            遍历字符串1，将字符串1每个字符转换成一个字符串，然后与只有一个字符的字符串2进行大小写无关的比较，如果相等，则count加一
     */
    public static void main3(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String all = sc.nextLine();
            String one = sc.nextLine();

            int count = 0;
            char[] arr = all.toCharArray();
            for (int i=0; i<arr.length; i++) {
                if (one.equalsIgnoreCase(String.valueOf(arr[i]))) {
                    count ++;
                }
            }
            System.out.println(count);
        }
    }

    /*
        思路：
            1、处理输入，将目标字符、字符串对应的字符大小写变为一致
            2、如果字符串存在目标字符，则count加一，并将第一次出现及其前面的所有字符都切掉
     */
    public static void main2(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String input = sc.nextLine().toUpperCase();
            char c = sc.nextLine().toUpperCase().toCharArray()[0];

            int count = 0;
            for (int i=0; i<input.length(); i++) {
                if (input.charAt(i) == c) {
                    count++;
                }
            }
            System.out.println(count);
        }
    }
    //
    public static void main1(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            // 1、处理输入 如果c是小写字母，则将整个字符串的字母转变为小写；如果是大写字母，则转变为大写；如果是数字，则不处理
            String input = sc.nextLine();
            char c = sc.nextLine().charAt(0);
            if (c >= 'A' && c <= 'Z') {
                input = input.toUpperCase();
            } else if (c >= 'a' && c <= 'z') {
                input = input.toLowerCase();
            }

            // 2、
            int count = 0;
            while (input.indexOf(c) != -1) {
                count ++;
                int index = input.indexOf(c);
                input = input.substring(index + 1); //如果i是最后一个字符，则返回一个空字符串
            }
            System.out.println(count);
        }
    }
}

