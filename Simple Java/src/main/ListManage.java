package main;

public class ListManage {

	public static int listManage(int[] arr) {
		if (arr == null || arr.length <= 0)
			return -1;

		java.util.Arrays.sort(arr);
		int i = 0;
		for (i = 0; i < arr.length; i++)
			if (arr[i] != i)
				break;
		if (arr[i] != i)
			return i;
		else
			return -1;
	}
}
