package leetcode.kSum;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/*
https://leetcode-cn.com/problems/two-sum-bsts/submissions/
 */
public class TwoSumTwoBST {

    /*
        中序遍历 + 双指针法
     */
    public boolean twoSumBSTs(TreeNode root1, TreeNode root2, int target) {
        if (root1 == null || root2 == null) {
            return false;
        }

        // 1、中序遍历
        List<Integer> list1 = new ArrayList<>();
        inOrder(root1, list1);
        List<Integer> list2 = new ArrayList<>();
        inOrder(root2, list2);

        // 2、双指针法
        int len1 = list1.size(), len2 = list2.size();
        int i = 0, j = len2 - 1;
        while (i < len1 && j >= 0) {
            int add = list1.get(i) + list2.get(j);
            if (add > target) {
                j --;
            } else if (add < target) {
                i ++;
            } else {
                return true;
            }
        }
        return false;
    }
    /*
    private static void inOrder(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }

        inOrder(root.left, list);
        list.add(root.val);
        inOrder(root.right, list);
    }
    */



    /*
        中序遍历 + 枚举 + TreeSet的contains方法
     */
    public boolean twoSumBSTs2(TreeNode root1, TreeNode root2, int target) {
        if (root1 == null || root2 == null) {
            return false;
        }

        TreeSet<Integer> set1 = new TreeSet<>();
        inOrder(root1, set1);
        TreeSet<Integer> set2 = new TreeSet<>();
        inOrder(root2, set2);

        for (Integer num : set1) {
            if (set2.contains(target - num)) {
                return true;
            }
        }
        return false;
    }
    private static void inOrder(TreeNode root, TreeSet<Integer> set) {
        if (root == null) {
            return;
        }

        inOrder(root.left, set);
        set.add(root.val);
        inOrder(root.right, set);
    }


    /*
        中序遍历 + 枚举 + 二分查找
        思路：
            1、分别对两棵树进行中序遍历，将各自的数字存成有序的数组列表
            2、枚举其中一个列表，对于每个当前访问的数字，判断能否在另一个列表中二分查找到互补元素
            如果可以，返回true；如果枚举完都没有找到，返回false
     */
    public boolean twoSumBSTs1(TreeNode root1, TreeNode root2, int target) {
        // 1、
        List<Integer> list1 = new ArrayList<>();
        inOrder(root1, list1);
        List<Integer> list2 = new ArrayList<>();
        inOrder(root2, list2);

        // 小的优化，枚举较短的列表，二分查找较长的列表
        if (list1.size() > list2.size()) {
            List temp = list1;
            list1 = list2;
            list2 = temp;
        }

        // 2、
        for (int i=0; i<list1.size(); i++) {
            int complement = target - list1.get(i);
            if (binarySearch(list2, complement)) {
                return true;
            }
        }
        return false;
    }
    // 二分查找：判断有序的数组列表中，是否有target这个数字
    private static boolean binarySearch(List<Integer> list, int target) {
        int left = 0, right = list.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) < target) {
                left = mid + 1;
            } else if (list.get(mid) > target) {
                right = mid - 1;
            } else {
                return true;
            }
        }
        return false;
    }
    // 中序遍历：将BST的所有数字按中序存储到数组链表中（中序遍历序列是升序的）
    private static void inOrder(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }

        inOrder(root.left, list);
        list.add(root.val);
        inOrder(root.right, list);
    }

}
