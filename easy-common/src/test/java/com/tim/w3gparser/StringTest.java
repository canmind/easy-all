package com.tim.w3gparser;

public class StringTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s1 = new String("kvill");
		System.out.println(s1 == s1.intern());
		// char[] test={'k','v','i','l','l'};
		// String s1=new String(test);
		// System.out.println(s1==s1.intern());
	}

}
