package leetcode.kSum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
https://leetcode-cn.com/problems/4sum/submissions/
 */
public class FourSum {

    /*
        双指针法思路：
             使用四个指针(a<b<c<d)。固定最小的a和b在左边，c=b+1,d=_size-1 移动两个指针包夹求解。
             保存使得nums[a]+nums[b]+nums[c]+nums[d]==target的解。偏大时d左移，偏小时c右移。c和d相
             遇时，表示以当前的a和b为最小值的解已经全部求得。b++,进入下一轮循环b循环，当b循环结束后。
             a++，进入下一轮a循环。
             即(a在最外层循环，里面嵌套b循环，再嵌套双指针c,d包夹求解)。

     */
    //采用另一种方式处理重复四元组，从而在时间上做出优化
    public List<List<Integer>> fourSum(int[] nums,int target){
        /*定义一个返回值*/
        List<List<Integer>> result=new ArrayList<>();
        /*当数组为null或元素小于4个时，直接返回*/
        if(nums==null||nums.length<4){
            return result;
        }
        /*对数组进行从小到大排序*/
        Arrays.sort(nums);
        /*数组长度*/
        int length=nums.length;
        /*定义4个指针k，i，j，h  k从0开始遍历，i从k+1开始遍历，留下j和h，j指向i+1，h指向数组最大值*/
        for(int k=0;k<length-3;k++){
            /*当k的值与前面的值相等时忽略*/
            if(k>0&&nums[k]==nums[k-1]){            //1、去重（第一个数）
                continue;
            }
            /*获取当前最小值，如果最小值比目标值大，说明后面越来越大的值根本没戏*/
            int min1=nums[k]+nums[k+1]+nums[k+2]+nums[k+3];     //优化：提前退出
            if(min1>target){
                break;
            }
            /*获取当前最大值，如果最大值比目标值小，说明后面越来越小的值根本没戏，忽略*/
            int max1=nums[k]+nums[length-1]+nums[length-2]+nums[length-3];      //优化：提前退出
            if(max1<target){
                continue;
            }
            /*第二层循环i，初始值指向k+1*/
            for(int i=k+1;i<length-2;i++){
                /*当i的值与前面的值相等时忽略*/
                if(i>k+1&&nums[i]==nums[i-1]){      //2、去重（第二个数）
                    continue;
                }
                /*定义指针j指向i+1*/
                int j=i+1;
                /*定义指针h指向数组末尾*/
                int h=length-1;
                /*获取当前最小值，如果最小值比目标值大，说明后面越来越大的值根本没戏，忽略*/
                int min=nums[k]+nums[i]+nums[j]+nums[j+1];      //优化：提前退出
                if(min>target){
                    continue;
                }
                /*获取当前最大值，如果最大值比目标值小，说明后面越来越小的值根本没戏，忽略*/
                int max=nums[k]+nums[i]+nums[h]+nums[h-1];      //优化：提前退出
                if(max<target){
                    continue;
                }
                /*开始j指针和h指针的表演，计算当前和，如果等于目标值，j++并去重，h--并去重，当当前和大于目标值时h--，当当前和小于目标值时j++*/
                while (j<h){
                    int curr=nums[k]+nums[i]+nums[j]+nums[h];
                    if(curr==target){
                        result.add(Arrays.asList(nums[k],nums[i],nums[j],nums[h]));
                        j++;
                        while(j<h&&nums[j]==nums[j-1]){         //3、去重（第三个数）
                            j++;
                        }
                        h--;
                        while(j<h&&i<h&&nums[h]==nums[h+1]){        //4、去重（第四个数）
                            h--;
                        }
                    }else if(curr>target){
                        h--;
                    }else {
                        j++;
                    }
                }
            }
        }
        return result;
    }
    //
    public List<List<Integer>> fourSum1(int[] nums, int target) {

        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);

        for (int i = 0; i<nums.length-3; i++) {     //确定第一个数
            for (int j = i+1; j<nums.length-2; j++) {   //确定第二个数
                int m = j+1, n = nums.length - 1;   //采用双指针法找到合适的第三个数、第四个数（转换成两数之和的问题）
                while (m < n) {
                    int sum = nums[i] + nums[j] + nums[m] + nums[n];
                    if (sum < target) {
                        m++;
                    } else if (sum > target) {
                        n--;
                    } else {
                        List<Integer> quadruplet = new ArrayList<>();
                        quadruplet.add(nums[i]);
                        quadruplet.add(nums[j]);
                        quadruplet.add(nums[m]);
                        quadruplet.add(nums[n]);
                        if (!list.contains(quadruplet)) {   //利用排序 + 集合的contains方法去重
                            list.add(quadruplet);
                        }
                        m++;
                        n--;
                    }
                }
            }
        }

        return list;
    }

}
