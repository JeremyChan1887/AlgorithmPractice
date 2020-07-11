package leetcode.kSum;

/*
https://leetcode-cn.com/problems/container-with-most-water/
 */
public class ContainerWithMaxWater {


    /*
        双指针：
            分析：
                [1, 8, 6, 2, 5, 4, 8, 3, 7]，初始时左右指针在数组的两段，可以容纳的水为min(1, 7) * 8 = 8
                这时如果移动右指针，由于宽度变小，不管新的右指针的高度大于、小于左指针的高度，其面积都会小于上一次的面积
                如果移动左指针（也就是高度较低的这个指针），则当新的左指针高度变高时，有可能弥补宽度缩小，使面积变大
                总之，
                    容纳水量是由 两个指针指向数字的较小值 * 指针之间的距离 决定的
                    如果移动数字较大的那个指针，那么 [两个指针指向的数字的较小值] 不会增加，而 [指针之间的距离] 会较小，因此乘积会减小
                    这道题的难点在于如何移动指针：
                        1) 相同情况下两边距离越远越好；
                        2) 区域受限于较短边

     */
    public int maxArea(int[] height) {
        int curMaxArea = Integer.MIN_VALUE;
        int left = 0, right = height.length-1;
        while (left < right) {
            int width = right - left;
            int area = (height[left] < height[right]) ? height[left]*width : height[right]*width;

            if (area > curMaxArea) {
                curMaxArea = area;
            }

            if (height[left] < height[right]) {
                left ++;
            } else if (height[left] > height[right]) {
                right --;
            } else {    //容器的两个边界相等时，可以直接跳过两个边界。因为，不管移动哪一个，剩余的那一个与其他高度的组合一定不能超过两个边界的面积
                left ++;
                right --;
            }
        }
        return curMaxArea;
    }
    //
    public int maxArea2(int[] height) {
        int i = 0, j = height.length - 1, res = 0;
        while(i < j){
            res = height[i] < height[j] ?
                    Math.max(res, (j - i) * height[i++]):
                    Math.max(res, (j - i) * height[j--]);
        }
        return res;
    }
    //
    public int maxArea1(int[] height) {
        int l = 0, r = height.length - 1;
        int ans = 0;
        while (l < r) {
            int area = Math.min(height[l], height[r]) * (r - l);
            ans = Math.max(ans, area);
            if (height[l] <= height[r]) {
                ++l;
            }
            else {
                --r;
            }
        }
        return ans;
    }
}
