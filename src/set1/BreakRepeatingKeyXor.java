package set1;

public class BreakRepeatingKeyXor {
	public static void main(String[] args) throws Exception {
		System.out.println(findHammingDistance("this is a test", "wokka wokka!!!"));
	}

	public static int findHammingDistance(String one, String two) throws Exception {
		if (one.length() != two.length())
			throw new Exception("DifferingStringSize");

		int distance = 0;
		byte[] oneBytes = one.getBytes();
		byte[] twoBytes = two.getBytes();
		byte[] difference = new byte[oneBytes.length];

		// XOR the bits to find the unique bits
		for (int i = 0; i < oneBytes.length; i++) {
			difference[i] = (byte) (oneBytes[i] ^ twoBytes[i]);
		}

		// find the number of unique bits
		int curr;
		for (int i = 0; i < difference.length; i++) {
			curr = difference[i];
			// Integer.bitCount uses bit-shifting and 2's C to determine the
			// number of set bits; faster than if (curr % 2 == 1)... etc.
			distance += Integer.bitCount(curr);
		}
		return distance;
	}
}
