package set1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class SingleByteXor {

	// Frequencies referenced from
	// http://fitaly.com/board/domper3/posts/136.html
	@SuppressWarnings("serial")
	public static final HashMap<Character, Double> chars = new HashMap<Character, Double>() {
		{
			put(' ', 17.1662);
			put('e', 8.5771);
			put('t', 6.37);
			put('o', 5.7701);
			put('a', 5.188);
			put('n', 4.9701);
			put('i', 4.9019);
			put('s', 4.3686);
			put('r', 4.2586);
			put('l', 3.175);
			put('h', 2.7444);
			put('d', 2.5071);
			put('c', 2.1129);
			put('u', 2.0999);
			put('m', 1.6437);
			put('g', 1.5597);
			put('p', 1.5482);
			put('.', 1.5124);
			put('-', 1.3734);
			put('f', 1.3725);
			put('w', 1.3034);
			put('y', 1.133);
			put('b', 1.0195);
			put('v', 0.8462);
			put(',', 0.7384);
			put('k', 0.6753);
			put('•', 0.641);
			put('0', 0.5516);
			put('1', 0.4594);
			put(':', 0.4354);
			put('S', 0.4003);
			put('C', 0.3906);
			put('M', 0.3529);
			put('2', 0.3322);
			put('T', 0.3322);
			put('I', 0.3211);
			put('D', 0.3151);
			put('A', 0.3132);
			put('E', 0.2673);
			put('P', 0.2614);
			put('W', 0.2527);
			put('R', 0.2519);
			put('\'', 0.2447);
			put('"', 0.2442);
			put('H', 0.2321);
			put(')', 0.2233);
			put('(', 0.2178);
			put('B', 0.2163);
			put('N', 0.2085);
			put('x', 0.195);
			put('L', 0.1884);
			put('G', 0.1876);
			put('3', 0.1847);
			put('O', 0.1842);
			put('J', 0.1726);
			put('5', 0.1663);
			put('/', 0.1549);
			put('?', 0.1474);
			put('F', 0.1416);
			put('4', 0.1348);
			put('>', 0.1242);
			put('<', 0.1225);
			put(';', 0.1214);
			put('_', 0.1159);
			put('6', 0.1153);
			put('8', 0.1054);
			put('7', 0.103);
			put('9', 0.1024);
			put('V', 0.0892);
			put('j', 0.0867);
			put('U', 0.0814);
			put('q', 0.0747);
			put('K', 0.0687);
			put('*', 0.0628);
			put('z', 0.0596);
			put('$', 0.0561);
			put('X', 0.0343);
			put('Q', 0.0316);
			put('Y', 0.0304);
			put('=', 0.0227);
			put('&', 0.0226);
			put('+', 0.0215);
			put('#', 0.0179);
			put('%', 0.016);
			put(']', 0.0088);
			put('[', 0.0086);
			put('Z', 0.0076);
			put('@', 0.0073);
			put('!', 0.0072);
			put('{', 0.0026);
			put('}', 0.0026);
			put('\\', 0.0016);
			put('·', 0.001);
			put('`', 0.0009);
			put('|', 0.0007);
			put('^', 0.0003);
			put('~', 0.0003);
			put('ƒ', 0.0);
			put('ß', 0.0);
			put('â', 0.0);
			put('å', 0.0);
			put('æ', 0.0);
			put('í', 0.0);
		}
	};

	public static String SingleByteXor(String hex) throws Exception {
		String output, xor;
		BigInteger one, two, three;
		StringBuilder sb = new StringBuilder();
		boolean isPrintable;
		ArrayList<String> possible = new ArrayList<String>();

		// Iterate through possible XOR'd characters
		for (Integer i = 0; i < 256; i++) {

			// Reset Variables
			output = "";
			one = two = three = BigInteger.ZERO;
			sb.setLength(0);
			isPrintable = true;

			// Create a hex string with the possible XOR'd value which is the
			// same length as the input
			xor = Integer.toHexString(i);
			if (i < 16)
				xor = "0" + xor;
			while (xor.length() != hex.length()) {
				xor = xor + xor.substring(0, 2);
			}

			// XOR the two values as integers
			one = new BigInteger(hex, 16);
			two = new BigInteger(xor, 16);
			three = one.xor(two);
			output = three.toString(16);

			// Get the output ASCII string
			for (int j = 0; j + 2 < output.length(); j += 2) {
				String str = output.substring(j, j + 2);
				sb.append((char) Integer.parseInt(str, 16));
			}
			output = sb.toString();

			// check to make sure output is printable
			for (int j = 0; j < output.length(); j++) {
				char check = output.charAt(j);
				if (!chars.containsKey(check))
					isPrintable = false;
			}

			// Add all possibilities to an ArrayList
			if (isPrintable)
				possible.add(output);
		}

		// Throw error if there are no possible results
		if (possible.size() == 0)
			throw new Exception("NoPrintableResult");

		Double value = 0.0, tempValue;
		output = "";

		// Score possible strings based on character frequency
		for (String s : possible) {
			tempValue = getWeight(s);
			if (tempValue > value) {
				output = s;
				value = tempValue;
			}
		}

		// Return the most likely string
		return output;
	}
	
	public static double getWeight(String input) {
		double weight = 0.0;
		for (int i = 0; i < input.length(); i++) {
			weight += chars.get(input.charAt(i));
		}
		return weight;
	}
}
