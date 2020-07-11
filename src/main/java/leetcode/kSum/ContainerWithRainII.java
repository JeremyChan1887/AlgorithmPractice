package leetcode.kSum;

import java.util.PriorityQueue;

/*
接雨水II：
https://leetcode-cn.com/problems/trapping-rain-water-ii/

 */
public class ContainerWithRainII {

    /*
        思路：
            接雨水I中维护了左右两个最高的墙，这里维护的是周围一个圈，用堆来维护周围这一圈中的最小元素。
            为什么是维护最小的元素而不是最大的元素呢？因为木桶效应啊。
            当这个最小的元素从堆里弹出来，和它四个方向的元素去比较大小，看看能不能往里面灌水
                如果弹出来的高度比它周围的大，就可以往较矮的里面灌水了
                    灌水后要把下一个柱子放进去的时候，放的高度要取两者较大的，也就是灌水后的高度（原先的高度+灌的水的高度），不是它原先矮的高度了
                如果不能灌水，继续走
            [
                [1, 4, 3, 1, 3, 2],
                [3, 2, 1, 3, 2, 4],
                [2, 3, 3, 2, 3, 1]
            ]
            首先将第一圈都放进去，然后开始从堆中弹出，
            第一圈，最小值是1（遍历时候标记为访问过），1（坐标为[0,3]）从堆中弹出来。它下方的3没有被访问过，尝试灌水，发现不能灌水，3入堆
            继续弹，比如此时弹出3（坐标为[1,0]），它可以向右边的2（坐标为[1,1]）灌水，统计起来，同时往堆中插入2这个位置。注意，此时不能插入原先的高度2，而应该是灌水后的高度3

            核心思想就是先确定木桶的外围，找到外围的最短板子后对其周围能填水的地方填水，然后更新木桶外围

            从矩阵的最外围往里面遍历，让圈不断缩小
            1、为了防止重复遍历，用visited数组记录；
            2、其次要用小顶堆（以高度为判断基准，可以保证高度较小的先出队）存最外面一圈，然后不断从里面取出最矮的块，将块的四周加入堆中
                为什么要让高度较小的先出队？（关键点） 木桶效应

     */
    public int trapRainWater(int[][] heights) {
        if (heights == null || heights.length == 0) return 0;   //heights.length < 3

        int n = heights.length;
        int m = heights[0].length;
        // 用一个vis数组来标记这个位置有没有被访问过
        boolean[][] vis = new boolean[n][m];
        // 优先队列中存放三元组 [x,y,h] 坐标和高度
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[2] - o2[2]);

        // 先把最外一圈放进去
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i == 0 || i == n - 1 || j == 0 || j == m - 1) {
                    pq.offer(new int[]{i, j, heights[i][j]});
                    vis[i][j] = true;
                }
            }
        }


        int res = 0;
        // 方向数组，把dx和dy压缩成一维来做（非常巧妙）
        int[] dirs = {-1, 0, 1, 0, -1};
        while (!pq.isEmpty()) {
            int[] poll = pq.poll();
            // 看一下周围四个方向，没访问过的话能不能往里灌水
            for (int k = 0; k < 4; k++) {
                int nx = poll[0] + dirs[k];
                int ny = poll[1] + dirs[k + 1];
                // 如果位置合法且没访问过
                if (nx >= 0 && nx < n && ny >= 0 && ny < m && !vis[nx][ny]) {
                    // 如果外围这一圈中最小的比当前这个还高，那就说明能往里面灌水啊
                    if (poll[2] > heights[nx][ny]) {
                        res += poll[2] - heights[nx][ny];
                    }
                    // 如果灌水高度得是你灌水后的高度了，如果没灌水也要取高的
                    pq.offer(new int[]{nx, ny, Math.max(heights[nx][ny], poll[2])});
                    vis[nx][ny] = true;
                }
            }
        }
        return res;
    }

}
