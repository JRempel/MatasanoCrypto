package set1;

import java.util.BitSet;

import set1.BaseConvert.ReturnType;

public class FixedXOR {

	public static String fixedXOR(String one, String two) throws Exception {
		if (one.length() != two.length())
			throw new Exception("InputLengthMismatch");
		else {
			// Get base64 encoding of each string
			BitSetAndIndex bitset1 = BaseConvert.hexToBase64(one, ReturnType.BITSETANDINDEX);
			BitSetAndIndex bitset2 = BaseConvert.hexToBase64(two, ReturnType.BITSETANDINDEX);
			// Xor result into a new BitSet
			BitSet xor = new BitSet();
			for (int i = 0; i <= Math.max(bitset1.getIndex(), bitset2.getIndex()); i++) {
				xor.set(i, bitset1.getBitset().get(i) ^ bitset2.getBitset().get(i));
			}
			// Get hex encoding of the new bitset
			String temp, hex = "";
			int remainder;
			int base10 = 0;
			for (int i = 0; i < Math.max(bitset1.getIndex(), bitset2.getIndex()); i++) {
				base10 |= (xor.get(i) ? 1 : 0);
				if ((i + 1) % 8 == 0) {
					temp = "";
					while (base10 != 0L) {
						remainder = base10 % 16;
						temp = BaseConvert.hexOrder[remainder] + temp;
						base10 /= 16;
					}
					hex += temp;
				}
				base10 <<= 1;
			}
			return hex;
		}
	}
}
