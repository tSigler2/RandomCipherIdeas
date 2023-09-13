package AES_Based_Cipher;
import java.util.Scanner;
import java.util.Random;

public class mainCipher {
	final static Scanner console = new Scanner(System.in);
	
	public static void main(String[] args) {
		String s = console.nextLine();
		
		System.out.println(AES_Style(s));
	}
	
	private static String conversionToBits(String s) {
		StringBuilder r  = new StringBuilder();
		
		char[] c = s.toCharArray();
		s = "";
		
		for(char x: c) {
			r.append(String.format("%8s", Integer.toBinaryString(x)).replaceAll(" ", "0"));
		}
		
		return r.toString();
	}
	
	private static String AES_Style(String s){
		s = conversionToBits(s);
		System.out.println(s);
		char[][][] encipherArrays = new char[4][4][(int)Math.ceil(s.length()/16)];
		
		int count = 0;
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				for(int k = 0; k < encipherArrays[0][0].length; k++) {
					encipherArrays[i][j][k] = s.charAt(count);
					count++;
				}
			}
		}
		
		String key = randomKeyGenerator(2*(int)Math.ceil(s.length()/16));
		System.out.println(key);
		
		int num1, num2, arrCount = 0;
		char rem;
		
		for(int i = 0; i < key.length(); i+= 2) {
			num1 = getNum(key.charAt(i));
			num2 = getNum(key.charAt(i+1));
			
			for(int j = 0; j < 4; j++) {
				rem = encipherArrays[num1][j][arrCount];
				encipherArrays[num1][j][arrCount] = encipherArrays[num2][j][arrCount];
				encipherArrays[num1][j][arrCount] = rem;
			}
			arrCount++;
			
			encipherArrays = bitRotation(encipherArrays);
		}
		
		String retAux = "";
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				for(int k = 0; k < encipherArrays[0][0].length; k++) {
					retAux += encipherArrays[i][j][k];
				}
			}
		}
		String ret = "";
		for(int i = 0; i < retAux.length(); i += 8) {
			ret += (char)Integer.parseInt(retAux.substring(i, i+8), 2);
		}
		
		return ret;
	}
	
	private static int getNum(char c) {
		switch(c) {
			case '0':
				return 0;
			case '1':
				return 1;
			case '2':
				return 2;
			case '3':
				return 3;
		}
		return 0;
	}
	
	private static char[][][] bitRotation(char[][][] aes){
		char rem;
		
		for(int i = 0; i < aes[0][0].length; i++) {
			rem = aes[3][3][i];
			
			for(int j = 3; j > 0; j--) {
				aes[3][j][i] = aes[3][j-1][i];
			}
			
			rem = aes[0][3][i];
			
			for(int j = 3; j > 0; j--) {
				aes[0][j][i] = aes[0][j-1][i];
			}
			
			aes[0][0][i] = rem;
			aes[3][0][i] = rem;
			
			rem = aes[2][0][i];
			aes[2][0][i] = aes[2][2][i];
			aes[2][2][i] = rem;
			
			rem = aes[2][1][i];
			aes[2][1][i] = aes[2][3][i];
			aes[2][3][i] = rem;
			
			rem = aes[1][0][i];
			
			aes[1][0][i] = aes[1][1][i];
			aes[1][1][i] = aes[1][2][i];
			aes[1][2][i] = aes[1][3][i];
			aes[1][3][i] = rem;
		}
		
		return aes;
	}
	
	private static String randomKeyGenerator(int length) {
		Random r = new Random();
		String k = "";
		int val;
		
		for(int i = 0; i < length; i++) {
			val = r.nextInt(4);
			
			switch(val) {
				case 0:
					k += '0';
				case 1:
					k += '1';
				case 2:
					k += '2';
				case 3:
					k += '3';
			}
		}
		
		k = k.substring(0, length);
		
		return k;
	}
}
