package leetcode.kSum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
https://leetcode-cn.com/problems/3sum/
 */

public class ThreeSum {


    /*
        双指针思路：
            1、排序(两个作用：双指针；排除重复的组合)
            2、确定第一个数，将问题转换成两数之和

            关于去重：
                暴力法：
                    暴力法需要使用三重循环枚举三元组，得到O(N^3)个满足题目要求的三元组，时间复杂度至少为O(N^3)
                    此外，它还需要使用哈希表进行去重操作，得到不包含重复三元组的最终答案，消耗大量空间
                双指针法：将暴力法的O(n^3)降到了O(n^2)
                    [不重复]的本质是什么？
                    1) 第二重循环枚举到的元素不小于当前第一重循环枚举到的元素；
                    2) 第三重循环枚举到的元素不小于当前第二重循环枚举到的元素
                    这样一来，我们枚举到的三元组(a,b,c)就满足a<=b<=c，保证了只有(a,b,c)这个顺序会被枚举到，而(b,a,c)、(c,b,a)等等就不会，
                    从而减少了重复。（可以通过将数组中的元素从小到大进行排序来实现）
                    //
                    上面只能减少重复（通过排序实现），而由于a、b、c均可能有多个，所以还是会有重复三元组。有两种方法来避免这种重复，如下所示
                    //
                    第一种重复是，同样的三个数不同组合顺序引起的重复；第二种重复是，三元组中的三个数各自可能出现多次
     */
    // 去重方法二
    public List<List<Integer>> threeSum(int[] nums) {// 总时间复杂度：O(n^2)
        List<List<Integer>> ans = new ArrayList<>();
        if (nums == null || nums.length <= 2) return ans;

        Arrays.sort(nums); // O(nlogn)

        for (int i = 0; i < nums.length - 2; i++) { // O(n^2)
            if (nums[i] > 0) break; // 第一个数大于 0，后面的数都比它大，肯定不成立了
            if (i > 0 && nums[i] == nums[i - 1]) continue; // 去掉重复情况
            int target = -nums[i];
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                if (nums[left] + nums[right] == target) {
                    ans.add(new ArrayList<>(Arrays.asList(nums[i], nums[left], nums[right])));

                    // 现在要增加 left，减小 right，但是不能重复，比如: [-2, -1, -1, -1, 3, 3, 3], i = 0, left = 1, right = 6, [-2, -1, 3] 的答案加入后，需要排除重复的 -1 和 3
                    left++; right--; // 首先无论如何先要进行加减操作
                    while (left < right && nums[left] == nums[left - 1]) left++;
                    while (left < right && nums[right] == nums[right + 1]) right--;
                } else if (nums[left] + nums[right] < target) {
                    left++;
                } else {  // nums[left] + nums[right] > target
                    right--;
                }
            }
        }
        return ans;
    }
    //
    // 去重方法一
    // 超过时间限制，LeetCode可能在时间限制变严格了，之前是可以通过的。因为是List的contains方法遍历的原因
    public List<List<Integer>> threeSum1(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();

        if (nums == null || nums.length < 3) {
            return list;    //非法输入1
        }

        //nums.length >= 3
        Arrays.sort(nums);
        if (nums[0] > 0) {
            return list;    //非法输入2
        }
        for (int i=0; i<nums.length-2; i++) {   //先确定第一个数，再从剩余的范围中找到符合的两个数，相当于两数之和
            int left = i+1, right = nums.length-1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum > 0) {
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    List<Integer> temp = new ArrayList<>();
                    temp.add(nums[i]);
                    temp.add(nums[left]);
                    temp.add(nums[right]);
                    if (!list.contains(temp)) { //不加入重复的组合
                        list.add(temp);
                    }
                    left++;
                    right--;
                }
            }
        }

        return list;
    }

}
