package main;

import org.junit.Test;

public class ListManageTest {

	@Test
	public void testEmpty() {
		int[] arr = null;
		assert (ListManage.listManage(arr) == -1);
	}

	@Test
	public void testNoMiss() {
		int[] arr = new int[100];
		for (int i = 0; i < arr.length; i++)
			arr[i] = i + 1;

		assert (ListManage.listManage(arr) == -1);
	}

	@Test
	public void testMiss() {
		int missingValue = 1;

		int[] arr = new int[99];
		for (int i = 0, j = 0; i < arr.length; i++, j++)
			if (i != missingValue)
				arr[i] = j + 1;

		assert (ListManage.listManage(arr) == missingValue);
	}
}
