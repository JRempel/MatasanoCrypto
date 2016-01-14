package set1;

import java.util.BitSet;

import set1.BaseConvert.ReturnType;

public class FixedXOR {
	public static void main(String[] args) throws Exception {
		System.out.println(fixedXOR("1c0111001f010100061a024b53535009181c", "686974207468652062756c6c277320657965"));
	}
	
	public static String fixedXOR(String one, String two) throws Exception {
		if (one.length() != two.length())
			throw new Exception("InputLengthMismatch");
		else {
			BitSetAndIndex test = BaseConvert.hexToBase64(one, ReturnType.BITSETANDINDEX);
			System.out.println(test.getIndex());
			return "";
		}
	}
}
