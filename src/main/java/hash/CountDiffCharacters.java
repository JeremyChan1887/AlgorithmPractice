package hash;

/*
计算字符串中含有的不同字符的个数。字符在ACSII码范围内(0~127)，不在范围内的不作统计。多个相同的字符只计算一次

输入
abaca
输出
3

关于ASCII码：
1）计算机中所有数据的存储、运算都要使用二进制数表示，因为计算机使用高电平和低电平分别表示1和0。对于字母、数字等字符在计算机中
也需要存储成二进制数，而这需要编码来实现。
目前常用的文字编码标准由ASCII（西文编码方案）；GB2312、GBK等（汉字字符编码方案）；Unicode（全球字符编码的国际标准）
2） ASCII码的大小规则及对应范围：数字（48-57） < 大写字母（65-90） < 小写字母（97-122）
标准ASCII的最高位为奇偶校验位（奇偶校验是在代码传送过程中用于检验是否出现错误的一种方法，有奇校验、偶校验两种。奇校验规定正确的
代码1个字节中的个数必须是奇数，若非奇数则在b7添加1），低7位才是编码位，其范围为000 0000 - 111 1111(0-127)（2^7=128）
扩展ASCII码：后128个
ASCII码使用7位、8位二进制数表示128或256种可能的字符
 */

import java.util.Scanner;
import java.util.HashSet;
public class CountDiffCharacters {

    /*
        思路：
            1、新建一个ASCII码范围大小的计数数组，遍历字符串，对每个不同字符的个数进行计数
            2、遍历计数数组，统计出现次数不为0的字符的个数
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            // 1、
            String input = sc.nextLine();
            int[] count = new int[128];
            for (int i=0; i<input.length(); i++) {
                count[input.charAt(i)] ++;
            }

            // 2、
            int num = 0;
            for (int i=0; i<128; i++) {
                if (count[i] != 0) {
                    num ++;
                }
            }
            System.out.println(num);
        }
    }

    /*
        思路：
            HashSet的元素不可重复性：将字符串的所有字符存进HashSet中，等遍历结束HashSet就是所有不同的字符
            注意：如果输入字符串包含的字符不只是0~127，则会得到错误的答案
     */
    public static void main1(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String input = sc.nextLine();
            HashSet<Character> set = new HashSet<>();
            for (int i=0; i<input.length(); i++) {
                set.add(input.charAt(i));
            }
            System.out.println(set.size());
        }
    }
}