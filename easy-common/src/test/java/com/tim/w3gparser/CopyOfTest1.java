package com.tim.w3gparser;

public class CopyOfTest1 {
	public static void main(String ss[]) {
		System.out.println(get(new int[] { 1, -1, 2, -3, 4, -5, 6, -7 }));
	}

	public static int get(int[] data) {
		int[] len = new int[data.length];// 记录最长信息
		for (int i = 0; i < len.length; i++) {
			len[i] = 1;
		}
		for (int i = 0; i < data.length; i++) {
			for (int j = i - 1; j >= 0; j--) {
				if (data[i] > data[j] && len[i] < len[j] + 1) {
					len[i] = len[j] + 1;
				}
			}
		}
		int max = -1;
		for (int i = 0; i < len.length; i++) {
			if (max < len[i]) {
				max = len[i];
			}
		}
		return max;
	}
}
