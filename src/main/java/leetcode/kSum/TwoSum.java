package leetcode.kSum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
https://leetcode-cn.com/problems/two-sum/
 */

public class TwoSum {

    /*
        双指针法：
            1、排序    O(nlogn)
            2、首位指针往前遍历，看看两个指针所对应的元素相加是否是target  O(n)
                如果 两者之和 < target，left ++；
                如果 两者之和 > target，right --;
                如果 两者之和 = target，返回结果

            注意：
                这里需要返回的是两个元素的原始下标，而排序之后会打乱下标，所以还需要暂存下标信息
     */
    public int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return null;
        }

        String str = "";                    //暂存下标信息1：在出现负数的用例下不能通过
        for (int i=0; i<nums.length; i++) {
            str += nums[i];
        }
        int[] arr = new int[nums.length];   //暂存下标信息2：
        for (int i=0; i<arr.length; i++) {
            arr[i] = nums[i];
        }
        Arrays.sort(nums);

        int[] result = new int[2];
        int left = 0, right = nums.length - 1;
        while (left < right) {
            if (nums[left] + nums[right] < target) {
                left ++;
            } else if (nums[left] + nums[right] > target) {
                right --;
            } else {
                /*
                if (nums[left] != nums[right]) {
                    result[0] = str.indexOf(nums[left] + '0');
                    result[1] = str.indexOf(nums[right] + '0');
                } else {
                    int index = str.indexOf(nums[left] + '0');
                    result[0] = index;
                    str = str.substring(index + 1);
                    result[1] = str.indexOf(nums[right] + '0') + (index + 1);
                }
                */
                int k = 0;
                for (int i=0; i<arr.length; i++) {
                    if (arr[i] == nums[left] || arr[i] == nums[right]) {
                        result[k++] = i;
                        if (k == 2) {
                            break;
                        }
                    }
                }
                break;
            }
        }

        return result;
    }

    /*
        思路：
            在迭代数组将元素插入到哈希表中的同时，检查表中是否已经存在当前元素所对应的互补元素。
            如果它存在，那说明找到了对应解，立即将其返回
     */
    public int[] twoSum3(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    /*
        思路：
            1、第一次遍历数组，将每个数字及其索引的映射关系存储到哈希表中
                利用HashMap，可以将查找时间从O(n)降低到O(1)
                注意，哈希表的快速查找时间只是近似恒定的，之所以是“近似”，是因为一旦出现冲突，查找时间可能退化到O(n)。
                    不过，只要仔细挑选哈希函数，在哈希表中进行查找的时间可以均摊为O(1)
                注意，第一次遍历中，如果出现重复元素，则存储出现最晚的元素的下标。因为，题目有“每种输入只会对应一个答案”的限制，
                    两个重复出现的数字只能互相作为互补数，不能单独作为其他数字的互补数。
                    （两遍遍历）考虑{2,2,3,4,5} target == 4这种情况，我们的map中只有【2,1】的键值对，而没有【2,0】的键值对。但是我们第二次遍历的时候，遍历的并不是哈希表，而是原数组。例如遍历到下标为0的数组元素时，从map中得到的值为1,1不等于0，所以就找到了。 总之，不管map中存的是【2,0】还是【2,1】，我们遍历原始数组的时候，总会找到一个和map中值不一样的下标，从而凑成一对。
                    （两遍遍历）没有{2,2,3,4,5} target == 5的情况，如果有，则丟掉key=2，value=1的鍵值對，返回結果是【0，2】
            2、第二次遍历数组，检查当前访问元素所对应的目标元素（target - nums[i]）是否存在哈希表中。
                注意，该目标元素不能是nums[i]本身
        时间复杂度：O(n)； 空间复杂度：O(n)  取决于哈希表中存储的元素个数（<= n）
        空间换时间：采用哈希算法来确认数组中是否存在目标元素，如果存在，则获得其索引
        小结：
            1) 保持数组中每个元素与其索引映射关系最好的结构是哈希表
            2) HashMap底层是通过数组+链表来实现的，链表用来保存冲突的key。containsKey方法首先通过hash函数得到key在数组的索引，这是O(1)的查找。
            如果发现有key冲突，才会开始查找链表（一般只要设计好hash函数，发生冲突的概率是不高的）
            3) 面试官经常会问HashMap为什么时间复杂度是O(1)？containsKey方法看似O(n^2)，实际上里面的循环不一定进入，只有当冲突的时候才进入，而且也可以均摊
            散列存储结构的查找是根据hashCode值快速定位，key为基本类型或String类型，均已重写hashCode方法，所以定位很快
            循环只是解决了hash冲突的查找问题
            4) HashMap用来做这个总感觉怪怪的，看似高效的解决了问题，但是map本身put时resize消耗的时间和空间都没计算在内
            -> 创建map的时候指定数组大小，应该可以节省一部分时间
            -> JVM应该会进行优化
     */
    public int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement) && map.get(complement) != i) {
                return new int[] { i, map.get(complement) };
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    /*
        思路：
            暴力法：遍历每个元素 x，并查找是否存在一个值与 target - x 相等的目标元素
            时间复杂度：O(n^2)
                对于每个元素，需要遍历数组的其他元素来寻找它对应的目标元素，这个查找过程需要耗费O(n)时间
                n个元素查找n次，每次需要O(n)时间，因此时间复杂度是O(n^2)
            空间复杂福：O(1)
     */
    public int[] twoSum1(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] == target - nums[i]) {
                    return new int[] { i, j };
                }
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }

}
