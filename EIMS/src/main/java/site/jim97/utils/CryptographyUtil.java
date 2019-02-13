package site.jim97.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

public class CryptographyUtil {

	private final static String SALT="jimblog";
	
	public static String md5(String str){
		return new Md5Hash(str,SALT).toString();
	}
	
	public static void main(String[] args) {
		String password="c4ca4238a0b923820dcc509a6f75849b";
		
		System.out.println("MD5 lock:"+CryptographyUtil.md5(password));
	}
}
