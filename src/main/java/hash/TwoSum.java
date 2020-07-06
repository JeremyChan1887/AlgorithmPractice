package hash;

import java.util.ArrayList;
import java.util.HashMap;

/*
给出一个整数数组，请在数组中找出两个加起来等于目标值的数，
返回这两个数字的下标（index1，index2），需要满足 index1 小于index2.。注意：下标是从1开始的
假设给出的数组中只存在唯一解
例如：
给出的数组为 {2, 7, 11, 15},目标值为9
输出 ndex1=1, index2=2

注意：
这道题不能使用排序+双指针的方法，因为排序过后元素原先的下标信息就丢失了

输入：
[3,2,4],6
输出：
[2,3]
 */
public class TwoSum {

    /*
        思路：
            边遍历边存“已遍历的元素所需要的互补元素<->已遍历的元素的第一次出现的下标”
            （1）、（2）可以逆过来，也就是存“已遍历的元素<->已遍历的元素第一次出现的下标”
     */
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])){  //（1）
                return new int[]{map.get(nums[i])+1,i+1};
            }
            map.put(target - nums[i],i);    //（2）
        }
        return null;
    }

    /*
        思路：
            采用哈希算法
     */
    public int[] twoSum1 (int[] numbers, int target) {
        // write code here
        //LinkedHashMap<Integer, Integer> map = new LinkedHashMap<Integer, Integer>();
        //应该记录重复元素的所有下标
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
        for (int i=0; i<numbers.length; i++) {
            if (!map.containsKey(numbers[i])) {
                ArrayList<Integer> item = new ArrayList<Integer>();
                item.add(i + 1);
                map.put(numbers[i], item);
            } else {
                map.get(numbers[i]).add(i + 1);
            }
        }

        int[] result = new int[2];
        for (int i=0; i<numbers.length; i++) {
            if (map.get(numbers[i]).size() > 1) {
                map.get(numbers[i]).remove(0);
            } else {
                map.remove(numbers[i]); ////[3,2,4],6   6 = 3 + 3   IndexOutOfBound
            }
            if (map.containsKey(target - numbers[i])) {
                result[0] = i + 1;
                result[1] = map.get(target - numbers[i]).get(0);
            }
        }
        return result;
    }
    //
    //83%的用例
    public int[] twoSum_error (int[] numbers, int target) {
        // write code here
        //LinkedHashMap<Integer, Integer> map = new LinkedHashMap<Integer, Integer>();
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();    //[0,4,3,0],0  期待：[1,4]  输出：[0,0]
        for (int i=0; i<numbers.length; i++) {
            if (!map.containsKey(numbers[i])) {
                map.put(numbers[i], i+1);
            }
        }

        int[] result = new int[2];
        for (int i=0; i<numbers.length; i++) {
            map.remove(numbers[i]); //[3,2,4],6   6 = 3 + 3
            if (map.containsKey(target - numbers[i])) {
                result[0] = i + 1;
                result[1] = map.get(target - numbers[i]);
            }
        }
        return result;
    }
}
