package leetcode.kSum;

import java.util.*;

/*
https://leetcode-cn.com/problems/two-sum-iv-input-is-a-bst/
 */
public class TwoSumIV {

    /*
        中序遍历（有序）+双指针（ArrayList，可以随机访问）
            1、对树进行中序遍历，并将访问到的数值添加到List中
            利用BST中序遍历结果按升序排列的特性，得到有序数组
            2、在得到有序的List（数组）的基础上，采用双指针法判断是否存在两数之和为 k 的两个元素

            时间复杂度：O(n)，其中n是树中节点的数量，本方法需要遍历整棵树
            空间复杂度：O(n)，list中存储n个节点的值
     */
    public boolean findTarget(TreeNode root, int k) {
        //1、
        List < Integer > list = new ArrayList();
        inorder(root, list);

        //2、
        int l = 0, r = list.size() - 1;
        while (l < r) {
            int sum = list.get(l) + list.get(r);
            if (sum == k)
                return true;
            if (sum < k)
                l++;
            else
                r--;
        }
        return false;
    }
    //将二叉搜索树中序序列存储到List中
    //  二叉搜索树的性质一：中序遍历序列是有序的
    //  二叉搜索树的性质二：树中没有重复元素
    public void inorder(TreeNode root, List < Integer > list) {
        if (root == null)
            return;
        inorder(root.left, list);
        list.add(root.val);
        inorder(root.right, list);
    }


    /*
        先序遍历+HashSet：
            遍历整棵树
            对于访问到的每个节点 p，在set中检查是否存在 k - p，如果存在则返回true；否则将 p 加入到set中
            当遍历完整棵树，都没有找到一对节点和为 k，那么树种不存在两个和为 k 的节点

            时间复杂度：O(n)，其中n是树中节点的数量，最坏情况下需要遍历整棵树
            空间复杂度：O(n)，最坏情况下需要存储n个节点的值
     */

    /*
        层序遍历+HashSet：
            使用广度优先搜索遍历二叉树
            1、将根节点插入queue中
            2、当queue不为空
                从队列首部删除一个节点 p ，检查set中是否存在 k - p （因为二叉搜索树中不存在重复元素，如果存在重复元素，就应该使用HashMap记录频次）
                如果存在，则返回true
                如果不存在，则将 p 加入set中，同时将 p 的左右孩子节点加入queue
            3、循环结束，queue为空，返回false

            时间复杂度：O(n)，其中n是树中节点的数量，最坏情况下需要遍历整棵树
            空间复杂度：O(n)，最坏情况下需要存储n个节点的值
     */
    public boolean findTarget1_my(TreeNode root, int k) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        Set<Integer> set = new HashSet<>();
        while (!queue.isEmpty()) {
            TreeNode node = queue.remove();
            int a = node.val;
            int complement = k - a;
            if (set.contains(complement)) {
                return true;
            }
            set.add(a);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
            //System.out.println(1);
        }

        return false;
    }
    //
    public boolean findTarget1(TreeNode root, int k) {
        Set < Integer > set = new HashSet();
        Queue < TreeNode > queue = new LinkedList();
        queue.add(root);
        while (!queue.isEmpty()) {
            if (queue.peek() != null) {
                TreeNode node = queue.remove();
                if (set.contains(k - node.val))
                    return true;
                set.add(node.val);
                queue.add(node.right);  //不管是否为null，直接加入
                queue.add(node.left);
            } else
                queue.remove();
        }
        return false;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) {
        val = x;
    }
}
