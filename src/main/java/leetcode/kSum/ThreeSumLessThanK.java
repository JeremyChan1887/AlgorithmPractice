package leetcode.kSum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
https://leetcode-cn.com/problems/3sum-smaller/

这道问题如果采用穷举：
枚举所有的三元组(i, j, k)，其中i < j < k，并逐个判断它们是否满足题目中的条件
时间复杂度：O(n^3)。因为三元组(i, j, k)的个数总共为n! / ( 3! * (n-3)! ) = n * (n-1) * (n-2) / 6
空间复杂度：O(1)
极可能不满足时间限制
 */

public class ThreeSumLessThanK {
    public static int[] stringToIntegerArray(String input) {
        input = input.trim();
        input = input.substring(1, input.length() - 1);
        if (input.length() == 0) {
            return new int[0];
        }

        String[] parts = input.split(",");
        int[] output = new int[parts.length];
        for(int index = 0; index < parts.length; index++) {
            String part = parts[index].trim();
            output[index] = Integer.parseInt(part);
        }
        return output;
    }
    /*
        输入：
        [-2,0,1,3]
        2
        输出：
        2
     */
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = in.readLine()) != null) {
            int[] nums = stringToIntegerArray(line);
            line = in.readLine();
            int target = Integer.parseInt(line);

            int ret = new ThreeSumLessThanK().threeSumSmaller(nums, target);

            String out = String.valueOf(ret);

            System.out.print(out);
        }
    }




    /*
        二分法：

        时间复杂度：O(n^2 * logn)。枚举 i 和 j 各需要O(n)的时间复杂度，二分查找 k 需要O(logn)的时间复杂度
        空间复杂度：O(1)

     */
    public int threeSumSmaller(int[] nums, int target) {
        Arrays.sort(nums);
//        System.out.println(Arrays.toString(nums));

        int sum = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            sum += twoSumSmaller(nums, i + 1, target - nums[i]);
        }
        return sum;
    }
    private int twoSumSmaller(int[] nums, int startIndex, int target) {
        int sum = 0;
        for (int i = startIndex; i < nums.length - 1; i++) {
            //官方答案：
            // int j = binarySearch(nums, i, target - nums[i]);    //这里用i，因此可能返回的是i，不过i-i=0，所以是没问题的
            //
            //无法通过答案
            int j = binarySearch(nums, i+1, target - nums[i]);    //这里用i，因此可能返回的是i，不过i-i=0，所以是没问题的
            //这里如果用的是i+1，则出错
            /*
            请问一下, 这个二分查找法中, 为什么 twoSumSmaller 里面, int j = binarySearch(nums, i, target - nums[i]); 这里的 startIndex 不应该是 i + 1吗? 为什么是 i?

            i + 1 的目的是定一，然后处理剩下的 2 个。
            binarySearch 实际上就是在处理剩余的 2 个，所以不能 + 1
             */
            System.out.println(i +" "+ j);
            sum += j - i;
        }
        return sum;
    }
    //找到有序数组[startIndex, len-1]区间内，最后一个小于target的元素
    private int binarySearch(int[] nums, int startIndex, int target) {

        int left = startIndex;
        int right = nums.length - 1;

        //如果要使用i + 1的话
        //left=3, right=3, target=3, nums[left] = nums[right] = 3不小于target，却因为不满足while循环的条件，而直接返回left=3
        if (left == right) {
            return startIndex - 1;
        }

        while (left < right) {
            int mid = (left + right + 1) / 2;   //left=2, right=3, mid=3
            if (nums[mid] < target) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }



    /*
        双指针法：
            1、先解决一个简化版的问题，两数之和小于K
                1) 排序
                2) left、right指针分别指向第一个、最后一个元素
                    如果两个元素之和大于等于K，则说明right不可能出现在二元组中，right--（因为left指针只向右移动，其元素只会越来越大。要想减小两者之和，只能向左移动right指针）
                    如果两个元素之和小于K，说明[left + 1, right]区间的元素与left元素之和均小于K。比如left=0, right=3，则有right-left=3组二元组满足要求，count+=3, left++
            2、再在外部套上一层循环，变成三数之和小于K

        时间复杂度：O(n^2)
        空间复杂度：O(1)
     */
    public int threeSumSmaller2(int[] nums, int target) {
        Arrays.sort(nums);

        int count = 0;
        for (int i=0; i<nums.length-2; i++) {
            int j = i + 1, k = nums.length - 1;
            if (j >= k) {   //多余的判断，下面的while循环已经做出了判断了
                continue;
            }

            while (j < k) {
                if (nums[i] + nums[j] + nums[k] >= target) {
                    k --;
                } else {
                    System.out.println(i +" "+ j +" "+ k);
                    //count ++;
                    count += k - j;
                    j ++;
                }
                // while (nums[j] == nums[j-1]) {
                //     j ++;
                // }
                // while (nums[k] == nums[k+1]) {
                //     k --;
                // }
            }
        }
        return count;
    }
    //
    public int threeSumSmaller1(int[] nums, int target) {
        Arrays.sort(nums);

        int sum = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            sum += twoSumSmaller1(nums, i + 1, target - nums[i]);
        }
        return sum;
    }
    private int twoSumSmaller1(int[] nums, int startIndex, int target) {
        int sum = 0;
        int left = startIndex;
        int right = nums.length - 1;
        while (left < right) {
            if (nums[left] + nums[right] < target) {
                sum += right - left;
                left++;
            } else {
                right--;
            }
        }
        return sum;
    }

}
