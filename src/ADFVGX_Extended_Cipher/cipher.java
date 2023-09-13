package ADFVGX_Extended_Cipher;
import java.util.*;

public class cipher {
	public static void main(String[] args) {
		Scanner c = new Scanner(System.in);
		
		HashMap<Character, String> table = generateTable();
		HashMap<String, Character> reverseTable = tableSwap(table);
		String key, plain = "";
		
		System.out.print("Input Mode Wanted(0: Encrypt, 1: Decrypt): ");
		int k = c.nextInt();
		System.out.println();
		
		if(k == 0) {
			while(plain == "") {
				System.out.print("String to be Encrypted: ");
				plain = c.nextLine();
			}
		}
		
		else if(k == 1) {
			System.out.print("String to be Decrypted: ");
			plain = c.nextLine();
		}
		
		while(true) {
			System.out.print("Key: ");
			key = c.nextLine();
			if(checkInputKey(key)) {
				break;
			}
			System.out.println("No Repeat Characters Allowed");
		}
		c.close();
		
		if(k == 0) {
			String e = encrypt(plain, table, key);
			System.out.println();
			System.out.println("Encrypted String: " + e);
			System.out.println();
		}
		
		else if(k == 1) {
			String d = decrypt(plain, key, reverseTable);
			System.out.println("Decrypted String: " + d);
		}
	}
	
	static HashMap<Character, String> generateTable(){
		//Time Complexity: O(n)
		//Space Complexity: O(n)
		
		HashMap<Character, String> table = new HashMap<Character, String>();
		String CypherPiece = "AA"; int change; int secondary;
		
		for(int i = 32; i < 127; i++) {
			table.put((char)i, CypherPiece);
			
			change = (int)CypherPiece.charAt(1);
			change++;
			
			if(change > 74) {
				change = 65;
				secondary = (int)CypherPiece.charAt(0);
				secondary++;
				CypherPiece = "" + (char) secondary + (char) change;
			}
			else {
				CypherPiece =  "" + CypherPiece.charAt(0) + (char)change;
			}
		}
		
		return table;
	}
	
	static boolean checkInputKey(String key) {
		HashSet<Character> hs = new HashSet<Character>();
		
		for(int i = 0; i < key.length(); i++) {
			if(hs.contains(key.charAt(i))) {
				return false;
			}
			
			hs.add(key.charAt(i));
		}
		
		return true;
	}
	
	static HashMap<String, Character> tableSwap(HashMap<Character, String> origTable) {
		HashMap<String, Character> SwapTable = new HashMap<String, Character>();
		
		for(char e:origTable.keySet()) {
			SwapTable.put(origTable.get(e), e);
		}
		
		return SwapTable;
	}
	
	static String encrypt(String plain, HashMap<Character, String> table, String key) {
		//Time Complexity: O(n^2)
		//Space Complexity: O(n)
		
		String cypher = ""; //Return String
		
		for(int i = 0; i < plain.length(); i++) { //Using HashMap to return the plaintext as letter pairs
			cypher += table.get(plain.charAt(i));
		}
		
		HashMap<Character, String> returnMap = new HashMap<Character, String>(); //New hashmap for buckets for letter swapping
		
		for(int i = 0; i < key.length(); i++) {
			plain = "";
			
			for(int j = i; j < cypher.length(); j += key.length()) {
				plain += cypher.charAt(j);//Filtering letters into buckets using mathematical principles
			}
			
			if(!returnMap.containsKey(key.charAt(i))) { //Checking if letter key already exist for sorting
				String a = "";
				returnMap.put(key.charAt(i), a);
			}
		}
		
		String a;
		cypher = "";
		
		for(int i = 32; i < 127;i++) { //Go through list in ascii table order and add strings to output string from hash map
			if(returnMap.containsKey((char) i)) {
				a = returnMap.get((char) i);
				while(a.length() != 0) {
					cypher += a + " ";
				}
			}
		}
		
		return cypher;
	}
	
	static String decrypt(String e, String k, HashMap<String, Character> reverseTable) {
		//Time Complexity: O(n^2)
		//Space Complexity: O(n)
		
		String cut = "";
		int longestSubString = 0;
		char[] A = k.toCharArray();
		
		HashMap<Character, List<String>> sorter = new HashMap<Character, List<String>>();
		List<String> l;
		
		A = quickSort(A, 0, A.length-1);
		
		int i = 0, b = 0;
		
		for(int j = 0; j < A.length; j++) {
			i = b + 1;
			
			while(cut == "") {
				if(e.charAt(i) == ' ' || e.charAt(i) == '\0') {
					cut = e.substring(b, i);
					b = i+1;
				}
				else {
					i++;
				}
			}
			
			if(!sorter.containsKey(A[j])) {
				l = new ArrayList<String>();
				l.add(cut);
				sorter.put(A[j], l);
			}
			else {
				l = sorter.get(A[j]);
				l.add(cut);
				sorter.put(A[j], l);
			}
			
			if(cut.length() > longestSubString) {
				longestSubString = cut.length();
			}
			
			cut = "";
		}
		
		String r = "";
		String set = "";
		String out = "";
		List<String> ordering = new ArrayList<String>();
		
		for(int j = 0; j < k.length(); j++) {
			l = sorter.get(k.charAt(j));
			
			while(!l.isEmpty()) {
				ordering.add(l.get(0));
				l.remove(0);
			}
		}
		
		for(int j = 0; j < longestSubString; j++) {
			for(int m = 0; m < ordering.size(); m++) {
				if(j < ordering.get(m).length()) {
					r += (char) ordering.get(m).charAt(j);
				}
			}
		}
		
		for(int j = 0; j + 1 < r.length(); j += 2) {
			set = "" + (char) r.charAt(j) + (char) r.charAt(j+1);
			out += "" + reverseTable.get(set);
			set = "";
		}
		
		return out;
	}
	
	static char[] quickSort(char[] A, int l, int h) {
		if(l >= h) {
			return A;
		}
		int p = partition(A, l, h);
		quickSort(A, l, p-1);
		quickSort(A, p+1, h);
		
		return A;
	}
	static int partition(char[] A, int l, int h) {
		int pivot = A[h];
		int i = l - 1;
		int swap;
		
		for(int j = l; j <= h-1; j++) {
			if(A[j] < pivot) {
				i++;
				swap = A[i];
				A[i] = A[j];
				A[j] = (char) swap;
			}
		}
		
		swap = A[i+1];
		A[i+1] = A[h];
		A[h] = (char) swap;
		
		return (i+1);
	}
}