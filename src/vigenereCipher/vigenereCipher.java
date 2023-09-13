package vigenereCipher;
import java.util.Scanner;

public class vigenereCipher {
	
	static Scanner s = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.print("Input Plain Text to be encrypted: ");
		String plain = s.nextLine();
		System.out.print("Input Key: ");
		int keyGen = s.nextInt();
		for(int i = 0; i < 50; i++) {
			String key = keyGenerator(keyGen);
			System.out.println("Key: " + key);
			
			String out = vigenereCipher(plain, key);
			
			printCypher(out);
			out = decryptVigenere(out, key);
			printDecrypt(out);
			System.out.println();
			System.out.println();
		}
	}
	
	public static String vigenereCipher(String s, String k) {
		//Time Complexity: O(n)
		//Space Complexity: O(n)
		
		int plainChar;
		int key;
		String enciphered = "";
		int l = 0;
		
		for(int i = 0; i < s.length(); i++) {
			plainChar = (int)s.charAt(i);
			key = (int)k.charAt(l);
			l++;
			
			if(l == k.length()) {			
				l = 0;
			}
			
			plainChar += key;
			
			if(plainChar > 126) {
				plainChar -= 95;
			}
			
			
			enciphered += (char)plainChar;
		}
		
		return enciphered;
	}
	
	public static String decryptVigenere(String e, String k) {
		//Time Complexity: O(n)
		//Space Complexity: O(n)
		
		String d = "";
		int c, key, l = 0;
		
		for(int i = 0; i < e.length(); i++) {
			c = (int)e.charAt(i);
			key = k.charAt(l);
			l++;
			
			if(l == k.length()) {
				l = 0;
			}
			
			c -= key;
			
			if(c < 32) {
				c += 95;
			}
			
			d += (char)c;
		}
		
		return d;
	}
	
	public static String keyGenerator(int l) {
		//Time Complexity: O(n)
		//Space Complexity: O(n)
		
		String k = "";
		char n;
		
		for(int i = 0; i < l; i++) {
			n = (char)((Math.random() * 128) + 32);
			if(n > 126) n -= 95;
			k += n;
		}
		
		return k;
	}
	
	public static void printCypher(String s) {
		//Time Complexity: O(n)
		//Space Complexity: O(1)
		
		System.out.print("Encrypted Text: ");
		for(int i = 0; i < s.length(); i++) {
			System.out.print(s.charAt(i));
		}
	}
	
	public static void printDecrypt(String s) {
		//Time Complexity: O(n)
		//Space Complexity: O(1)
		
		System.out.println();
		System.out.print("Decrypted Text: ");
		for(int i = 0; i < s.length(); i++) {
			System.out.print(s.charAt(i));
		}
	}
}
