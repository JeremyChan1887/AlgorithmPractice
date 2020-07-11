package leetcode.kSum;

/*
https://leetcode-cn.com/problems/two-sum-ii-input-array-is-sorted/
 */
public class TwoSumII {
    public int[] twoSum(int[] numbers, int target) {
        if (numbers == null) {
            return null;
        }

        int i = 0, j = numbers.length - 1;
        while (i < j) {
            int sum = numbers[i] + numbers[j];

            if (sum == target) {
                return new int[] {i + 1, j + 1};
            } else if (sum < target) {
                i++;
            } else {
                j--;
            }
        }

        return null;
        //return new int[] {-1, -1};
    }
}