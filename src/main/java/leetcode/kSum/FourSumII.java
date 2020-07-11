package leetcode.kSum;

import java.util.HashMap;
import java.util.Map;

/*
https://leetcode-cn.com/problems/4sum-ii/

 */
public class FourSumII {
    /*
            哈希法：
                首先将所有A+B的结果均存进哈希表中，如果有重复则用频次
                接着，遍历所有-(C+D)的结果，并查询在哈希表中是否存在。如果存在，则说明A+B+C+D=0
     */
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        Map<Integer, Integer> map = new HashMap<>();
        int res = 0;
        for(int i = 0;i<A.length;i++){
            for(int j= 0;j<B.length;j++){
                int sumAB = A[i]+B[j];
                if(map.containsKey(sumAB)) map.put(sumAB,map.get(sumAB)+1);
                else map.put(sumAB,1);
            }
        }

        for(int i = 0;i<C.length;i++){
            for(int j = 0;j<D.length;j++){
                int sumCD = -(C[i]+D[j]);
                if(map.containsKey(sumCD)) res += map.get(sumCD);
            }
        }
        return res;
    }

}
