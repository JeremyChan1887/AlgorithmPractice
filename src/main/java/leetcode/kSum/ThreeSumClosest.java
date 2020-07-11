package leetcode.kSum;

import java.util.Arrays;

/*
https://leetcode-cn.com/problems/3sum-closest/
 */
public class ThreeSumClosest {


    /*
        排序 + 双指针法：
            排序
            首先枚举第一个元素a（其下标为i），对于剩下的两个元素b和c，我们希望它们的和最接近target-a。
            其次在[i+1, n)区间内枚举b和c（之所以是i+1，是为了防止重复枚举）。已知两者可枚举范围+该范围数组元素有序，可以使用双指针
                左指针指向i+1，右指针指向n-1，计算a+b+c来更新答案ans：
                    如果a+b+c > target，将右指针向左移动一个位置
                    如果a+b+c < target，将左指针向右移动一个位置
                    如果a+b+c = target，毫无疑问是最接近的，直接返回
            最后在枚举结束后直接返回答案ans

            实际上，pb、pc就表示我们当前可以选择的数的范围，在每一次枚举的过程中，我们尝试边界上的两个元素，根据它们与target值的关系，
            选择[抛弃]左边界的元素还是右边界的元素，从而减少了枚举的范围。这种思路跟  11.盛最多水的容器  中的双指针解法是类似的

            小优化：
            1）本题也有一些可以减少运行时间（但不会减少时间复杂度）的小优化。
            当我们枚举到恰好等于 target 的 a+b+c 时，可以直接返回 target 作为答案，因为不会有再比这个更接近的值了。
            2）另一个优化与 15. 三数之和的官方题解 中提到的类似。
            当我们枚举 a, b, c 中任意元素并移动指针时，可以直接将其移动到下一个与这次枚举到的不相同的元素，减少枚举的次数。
     */
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int n = nums.length;
        int best = 10000000;

        // 枚举 a
        for (int i = 0; i < n; ++i) {
            // 保证和上一次枚举的元素不相等
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            // 使用双指针枚举 b 和 c
            int j = i + 1, k = n - 1;
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                // 如果和为 target 直接返回答案
                if (sum == target) {
                    return target;
                }
                // 根据差值的绝对值来更新答案
                if (Math.abs(sum - target) < Math.abs(best - target)) {
                    best = sum;
                }
                if (sum > target) {
                    // 如果和大于 target，移动 c 对应的指针
                    int k0 = k - 1;
                    // 移动到下一个不相等的元素
                    while (j < k0 && nums[k0] == nums[k]) {
                        --k0;
                    }
                    k = k0;
                } else {
                    // 如果和小于 target，移动 b 对应的指针
                    int j0 = j + 1;
                    // 移动到下一个不相等的元素
                    while (j0 < k && nums[j0] == nums[j]) {
                        ++j0;
                    }
                    j = j0;
                }
            }
        }
        return best;
    }
    //
    public int threeSumClosest1(int[] nums, int target) {
        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));

        int subSum = 10000; //记住当前与target距离最小的sum
        //int subSum = nums[0] + nums[1] + nums[2];
        for (int i=0; i<nums.length-2; i++) {
            int j = i+1, k = nums.length - 1;
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                if ( Math.abs(subSum - target) > Math.abs(sum - target) ) { //判断sum与target的距离是不是当前最小
                    subSum = sum;
                }
                if (sum > target) {
                    k --;
                } else if (sum < target) {
                    j ++;
                } else {
                    return sum;
                }
            }
        }
        return subSum;
    }

}
