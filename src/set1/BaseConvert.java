package set1;

import java.util.BitSet;

// NOTE: Does not pad the result

public class BaseConvert {
	public static final Character[] base64Order = new Character[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/', };

	public static final Character[] hexOrder = new Character[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
			'b', 'c', 'd', 'e', 'f' };

	public static int getHexValue(char value) {
		if (value > 57)
			value -= 39;
		value -= 48;
		return value;
	}

	public static int getBase64Value(char value) {
		if (value >= 'A' && value <= 'Z')
			value -= 65;
		else if (value >= 'a' && value <= 'z')
			value -= 71;
		else if (value >= '0' && value <= '9')
			value += 4;
		else if (value == '+')
			value += 19;
		else if (value == '/')
			value += 16;
		return value;
	}

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
				char1 = getHexValue(hex.charAt(i));
				char2 = getHexValue(hex.charAt(i + 1));

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
			int base10 = 0;
			int index = 0;
			String hex = "";
			BitSet bits = new BitSet();
			for (int i = 0; i < base64.length(); i++) {
				// get actual value from ASCII table
				base10 = getBase64Value(base64.charAt(i));

				/// loop that adds to the BitSet reads right-to-left, so reverse
				// the bits and then shift
				base10 = Integer.reverse(base10 << 24) & 0xff;
				base10 >>= 2;
				// append binary values to the BitSet
				if (base10 == 0) {
					index += 6;
				} else {
					while (base10 != 0L) {
						if (base10 % 2 != 0) {
							bits.set(index);
						}
						index++;
						base10 >>= 1;
					}
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
				base10 |= (bits.get(i) ? 1 : 0);
				if ((i + 1) % 8 == 0) {
					temp = "";
					while (base10 != 0L) {
						remainder = base10 % 16;
						temp = hexOrder[remainder] + temp;
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
