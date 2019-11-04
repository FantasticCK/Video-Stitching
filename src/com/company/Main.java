package com.company;

import java.util.Arrays;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
//        new Solution().videoStitching(new int[][]{{0, 1}, {6, 8}, {0, 2}, {5, 6}, {0, 4}, {0, 3}, {6, 7}, {1, 3}, {4, 7}, {1, 4}, {2, 5}, {2, 6}, {3, 4}, {4, 5}, {5, 7}, {6, 9}}, 9);
        new Solution().videoStitching(new int[][]{{0, 2}, {4, 8}}, 5);
    }
}

// DP
class Solution {
    public int videoStitching(int[][] clips, int T) {
        Arrays.sort(clips, (o1, o2) -> {
            if (o1[0] != o2[0]) {
                return o1[0] - o2[0];
            } else {
                return o1[1] - o2[1];
            }
        });
        if (clips[0][0] != 0)
            return -1;

        int n = clips.length;
        int[][] dp = new int[T + 1][n];
        for (int i = 0; i < T + 1; i++) {
            for (int j = 0; j < n; j++) {
                int st = clips[j][0], ed = clips[j][1];

                if (j == 0) {
                    dp[i][j] = (i >= st && i <= ed) ? 1 : -1;
                    continue;
                }

                if (i < st || i > ed) {
                    dp[i][j] = dp[i][j - 1];
                    continue;
                }

                if (dp[i][j - 1] > 0 || i == st) {
                    dp[i][j] = dp[i][j - 1];
                    continue;
                }
                dp[i][j] = dp[st][j] == -1 ? -1 : (st == 0 ? 1 : dp[st][j] + 1);
            }
        }

        return dp[T][n - 1];
    }
}


// Greedy

class Solution {
    public int videoStitching(int[][] clips, int T) {
        Arrays.sort(clips, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return a[0]-b[0];
            }
        });
        int count = 0;
        int curend = 0;
        int laststart = -1;

        for(int i = 0; i < clips.length; ) {
            if(clips[i][0] > curend) {
                return -1;
            }
            int maxend = curend;
            while(i < clips.length && clips[i][0] <= curend) { // while one clip's start is before or equal to current end
                maxend = Math.max(maxend, clips[i][1]); // find out the one with the max possible end
                i++;
            }
            count++;
            curend = maxend;
            if(curend >= T) {
                return count;
            }
        }
        return -1;
    }
}