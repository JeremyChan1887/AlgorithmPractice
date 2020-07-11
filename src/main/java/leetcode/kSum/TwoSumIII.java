package leetcode.kSum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*
https://leetcode-cn.com/problems/two-sum-iii-data-structure-design/
 */

/*
    哈希法：
        1、初始化一个哈希表
        2、在add操作中：在哈希表中添加每个数字及其频次之间的映射关系        O(1)
        3、在find操作中：遍历哈希表，对于每个键值number，计算其complement = value - number        O(n)，n是哈希表中键值对的数量
            如果number与complement不同，检查哈希表中是否存在value - number（complement）
            如果number与complement相同，检查哈希表中是否存在2个以上的number

        小结：哈希表可以提供快速的查找和插入操作

 */
class TwoSumIII {
    private HashMap<Integer, Integer> num_counts;

    /** Initialize your data structure here. */
    public TwoSumIII() {
        this.num_counts = new HashMap<Integer, Integer>();
    }

    /** Add the number to an internal data structure.. */
    public void add(int number) {
        if (this.num_counts.containsKey(number))
            this.num_counts.replace(number, this.num_counts.get(number) + 1);
        else
            this.num_counts.put(number, 1);
    }

    /** Find if there exists any pair of numbers which sum is equal to the value. */
    public boolean find(int value) {
        for (Map.Entry<Integer, Integer> entry : this.num_counts.entrySet()) {
            int complement = value - entry.getKey();
            if (complement != entry.getKey()) {
                if (this.num_counts.containsKey(complement))
                    return true;
            } else {
                if (entry.getValue() > 1)
                    return true;
            }
        }
        return false;
    }
}

/*
    双指针法：
        利用ArrayList的可扩容+随机访问+可使用Collections的排序方法
        排序可以在插入数据之后，也可以在查找数据之前。由于插入比较频繁，应该将排序操作放在查找数据之前，从而减少时间消耗(排序的时机)

        利用isSorted标志，可以在连续多次执行find操作时，避免重复排序

        add(number)：O(1) (尽管扩容应该也可以均摊到其他不用扩容的操作)
        find(value)：O(nlogn) 这是最坏的情况下，因为我们需要对列表进行排序和遍历整个列表，这需要O(nlogn)和O(n)的时间。
 */
class TwoSumIII_1 {

    private ArrayList<Integer> list;
    private boolean isSorted;

    /** Initialize your data structure here. */
    public TwoSumIII_1() {
        list = new ArrayList<>();
        isSorted = false;
    }

    /** Add the number to an internal data structure.. */
    public void add(int number) {
        list.add(number);
        isSorted = false;
    }

    /** Find if there exists any pair of numbers which sum is equal to the value. */
    public boolean find(int value) {
        if (isSorted == false) {
            Collections.sort(list);
            isSorted = true;    //在多次连续find时可以避免重复排序
        }
        int i = 0, j = list.size() - 1;
        while (i < j) {
            int sum = list.get(i) + list.get(j);
            if (sum < value) {
                i ++;
            } else if (sum > value) {
                j --;
            } else {
                return true;
            }
        }
        return false;
    }
}

/**
 * Your TwoSum object will be instantiated and called as such:
 * TwoSum obj = new TwoSum();
 * obj.add(number);
 * boolean param_2 = obj.find(value);
 */
