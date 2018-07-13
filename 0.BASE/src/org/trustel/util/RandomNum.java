package org.trustel.util;

import java.util.Random;

/**
 * 用于生成无重复随机数
 * @author ZouJing
 *
 */
public class RandomNum {
	public static int[] random1() {
		Random r = new Random();
		int temp1, temp2;
		int send[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21 };
		int len = send.length;
		int returnValue[] = new int[22];
		for (int i = 0; i < 22; i++) {
			temp1 = Math.abs(r.nextInt()) % len;
			returnValue[i] = send[temp1];
			temp2 = send[temp1];
			send[temp1] = send[len - 1];
			send[len - 1] = temp2;
			len--;
		}
		return returnValue;
	}
	public static int[] random2() {
		int send[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21 };
		int temp1, temp2, temp3;
		Random r = new Random();
		for (int i = 0; i < send.length; i++){// 随机交换send.length次
			temp1 = Math.abs(r.nextInt()) % (send.length - 1); // 随机产生一个位置
			temp2 = Math.abs(r.nextInt()) % (send.length - 1); // 随机产生另一个位置
			if (temp1 != temp2) {
				temp3 = send[temp1];
				send[temp1] = send[temp2];
				send[temp2] = temp3;
			}
		}
		return send;
	}

}
