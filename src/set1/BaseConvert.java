package set1;

import java.util.BitSet;

// NOTE: Does not pad the result

public class BaseConvert {
	private static final Character[] base64Order = new Character[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/', };

	private static final Character[] hexOrder = new Character[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
			'b', 'c', 'd', 'e', 'f' };

	public static String hexToBase64(String hex) throws Exception {
		hex = hex.toLowerCase();
		// check for a non-hex string
		if (hex.length() % 2 != 0 || hex.contains("[^a-f0-9]"))
			throw new Exception("InputNotHex");
		else {
			int char1, char2;
			Integer base10;
			int index = 0;
			String base64 = "";
			BitSet bits = new BitSet();
			// convert a pair of chars to a binary sequence through their
			// decimal value
			for (int i = 0; i < hex.length(); i += 2) {
				// get actual value from ASCII table
				char1 = hex.charAt(i);
				char2 = hex.charAt(i + 1);
				if (char1 > 57)
					char1 -= 39;
				if (char2 > 57)
					char2 -= 39;
				char1 -= 48;
				char2 -= 48;
				// merge characters to a single integer value
				char1 <<= 4;
				base10 = (char1 | char2);
				
				// loop that adds to the BitSet reads right-to-left, so reverse
				// the bits
				base10 = Integer.reverse(base10 << 24) & 0xff;
				
				// append binary values to the BitSet
				// and account for when base10 is 0
				if (base10 == 0L) {
					index += 8;
				} else {
					while (base10 != 0L) {
						if (base10 % 2 != 0)
							bits.set(index);
						index++;
						base10 >>= 1;
					}
					// account for trailing 0s
					while (index % 8 != 0) {
						index++;
					}
				}
			}
			// read 6 bits to integer value for base64-value lookup
			base10 = 0;
			for (int i = 0; i < index; i++) {
				base10 |= (bits.get(i) ? 1 : 0);
				if ((i + 1) % 6 == 0) {
					base64 += base64Order[base10];
					base10 = 0;
				}
				base10 <<= 1;
			}
			return base64;
		}
	}

	public static String base64ToHex(String base64) throws Exception {
		if (base64.length() % 4 != 0 || base64.contains("[^a-zA-Z0-9\\+/]"))
			throw new Exception("InputNotBase64");
		else {
			int charValue = 0;
			int index = 0;
			String hex = "";
			BitSet bits = new BitSet();
			for (int i = 0; i < base64.length(); i++) {
				charValue = base64.charAt(i);
				// get actual value from ASCII table
				if (charValue >= 'A' && charValue <= 'Z')
					charValue -= 'A';
				else if (charValue >= 'a' && charValue <= 'z')
					charValue -= 71;
				else if (charValue >= '0' && charValue <= '9')
					charValue += 4;
				else if (charValue == '+')
					charValue += 19;
				else if (charValue == '/')
					charValue += 16;
				/// loop that adds to the BitSet reads right-to-left, so reverse
				// the bits and then shift
				charValue = Integer.reverse(charValue << 24) & 0xff;
				charValue >>= 2;
				// append binary values to the BitSet
				while (charValue != 0L) {
					if (charValue % 2 != 0) {
						bits.set(index);
					}
					index++;
					charValue >>= 1;
				}
				// account for trailing 0s
				while (index % 6 != 0) {
					index++;
				}
			}
			// read 8-bit integer value for hex-value lookup
			String temp;
			int remainder;
			for (int i = 0; i < index; i++) {
				charValue = (charValue | (bits.get(i) ? 1 : 0));
				if ((i + 1) % 8 == 0) {
					temp = "";
					while (charValue != 0L) {
						remainder = charValue % 16;	
						temp = hexOrder[remainder] + temp;
						charValue /= 16;
					}
					hex += temp;
				}
				charValue <<= 1;
			}
			return hex;
		}
	}
}
