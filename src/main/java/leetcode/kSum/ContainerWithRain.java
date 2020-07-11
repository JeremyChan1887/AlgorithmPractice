package leetcode.kSum;

/*
接雨水：
https://leetcode-cn.com/problems/trapping-rain-water/


 */
public class ContainerWithRain {

    //3、双指针
    /*
            思路：
                方法2从左、从右分开计算，我们想办法一次遍历完成
                动态编程中只要right_max[i] > left_max[i]，积水高度将由left_max决定。可以认为如果一端有更高的条形快（如右端），
                积水的高度将依赖于当前方向的高度（从左到右）。而当另一侧（右侧）的高度不是最高的，则从相反方向开始遍历（从右到左）
                使用两个指针交替进行的方式，实现一次遍历

            初始化left指针为0，right指针为size-1
            while left < right, do:
                if height[left] < height[right]
                    if height[left] >= left_max，更新left_max
                    else, 累加left_max - height[left]到ans
                    left ++
                else
                    if height[right] >= right_max，更新right_max
                    else，累加right_max-height[right]到ans
                    right --

            时间复杂度：O(n)  单次遍历的时间
            空间复杂度：O(1)  left、right、left_max、right_max需要常数的存储空间
     */
    public int trap(int[] height) {
        int left = 0, right = height.length - 1;
        int ans = 0;
        int left_max = 0, right_max = 0;
        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= left_max)  {
                    left_max = height[left];
                } else {
                    ans += (left_max - height[left]);
                }
                left ++;
            } else {
                if (height[right] >= right_max) {
                    right_max = height[right];
                } else {
                    ans += (right_max - height[right]);
                }
                right --;
            }
        }
        return ans;
    }

    //2、动态规划
    /*
            思路：
                在暴力法中，仅仅为了找到最大值每次都要向左和向右扫描一次，可以提前存储这个值
                申请left_max数组，向左遍历存各个元素左边最大高度
                申请right_max数组，向右遍历存各个元素右边最大高度
                从左到右遍历height数组，累加 min(max_left[i], max_right[i])-height[i] 到ans上

            时间复杂度：O(n)，存储最大高度数组，需要遍历两次，每次O(n)
            空间复杂度：O(n)
     */

    //1、暴力法
    /*
            思路：
                对于数组中的每个元素，直接计算它两边的最大高度（两个最大高度有可能是本身），然后用两者的较小值减去本身高度，
                就得到它可以存的水量，将所有的相加就得到总共可以存的水量
                第一个元素、最后一个元素不管高度是0、非0，都因为左边或右边没有柱子而不能接水
                因此，只需要从第二个元素开始遍历到倒数第二个元素

                基于木桶效应的常识，能存多少水取决于较短的短板
                初始化ans = 0
                从左到右扫描数组：
                    初始化max_left = 0 和max_right = 0 （当前元素的两个指标）
                    从当前元素向左扫描并更新：
                        max_left = max(max_left, height[j])
                    从当前元素向右扫描并更新：
                        max_right = max(max_right, height[j])
                    将min(max_left, max_right) - height[i]累加到ans

            时间复杂度：O(n)，数组中的每个元素都需要向左、向右扫描
            空间复杂度：O(1)

     */
    public int trap1(int[] height) {
        int ans = 0;
        for (int i=1; i<height.length-1; i++) {
            System.out.println(height[i]);

            int max_left = 0, max_right = 0;
            for (int j=i; j>=0; j--) {
                max_left = Math.max(max_left, height[j]);   //有可能是当前元素本身高度
            }
            for (int j=i; j<height.length; j++) {
                max_right = Math.max(max_right, height[j]); //有可能是当前元素本身高度
            }
            ans += Math.min(max_left, max_right) - height[i];

            System.out.println(height[i]+" "+max_left+" "+max_right+" "+ans);
        }
        return ans;
    }


}
