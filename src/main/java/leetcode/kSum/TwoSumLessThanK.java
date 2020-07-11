package leetcode.kSum;

import java.util.Arrays;

/*
https://leetcode-cn.com/problems/two-sum-less-than-k/

传统的TwoSum是要你找到等于target的配对，而如果是要找到大于/小于target的配对呢？这时候就不能使用哈希表的方法了，
哈希表的方法比较适合处理等于的情况
 */
public class TwoSumLessThanK {

    /*
        排序+双指针法：
            1、排序
            2、双指针遍历
            1) 如果当前左右指针指向的元素之和大于或者等于target，移动右指针
            2) 如果当前左右指针指向的元素之和小于target，
                判断它是不是大于maxSum，如果是，则更新maxSum；否则，不更新maxSum（拓展：这里可以记录配对数量：固定左指针，后指针及其左边的区间都符合，只要加上这个区间的数量即可（可能需要去重））
                移动左指针
     */
    public int twoSumLessThanK(int[] A, int K) {
        if (A == null || A.length == 0) {
            return -1;
        }

        Arrays.sort(A);

        int l = 0, r = A.length - 1;
        int result = Integer.MIN_VALUE;

        while (l < r) {
            if (A[l] + A[r] >= K) {
                r--;
            } else {
                result = Math.max(result, A[l] + A[r]);
                l++;
            }
        }

        return result == Integer.MIN_VALUE ? -1 : result;
    }

    /*
        排序+二分法：
            1、排序
            2、遍历每个元素number
            1) 计算complement = K - number，然后二分查找数组中最后一个小于complement的元素的下标index
            如果返回的下标是-1，说明数组中没有小于complement的元素； 否则，返回的是最后一个小于complement的元素的下标
            2) 如果返回的下标小于当前元素，说明之前已经遍历过，直接跳过； 否则，获取当前最小的maxSum = A[i] + A[index]
            3) 最终遍历结束得到的maxSum即是答案
     */
    public int twoSumLessThanK1(int[] A, int K) {
        Arrays.sort(A);

        int maxSum = -1;
        for (int i=0; i<A.length; i++) {
            int complement = K - A[i];

            int index = binarySearch(A, complement);
            if (index != -1 && index != i && index > i) {
                int curSum =  A[i] + A[index];
                if (maxSum < curSum) {
                    maxSum = curSum;
                }
            }
        }
        return maxSum;
    }
    // 二分查找，找到有序数组中，最后一个小于num的元素的下标
    private static int binarySearch(int[] A, int num) {
        int left = 0, right = A.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (A[mid] >= num) {
                right = mid - 1;
            } else if (A[mid] < num) {
                if (mid == A.length-1 || (mid != A.length-1 && A[mid+1] >= num) ) { //两种返回的情况
                    return mid;
                }
                left = mid + 1; //除了以上两种情况，其他情况尽管A[mid] < num，但却不是最后一个小于num的元素
            }
        }
        return -1;
    }
}
