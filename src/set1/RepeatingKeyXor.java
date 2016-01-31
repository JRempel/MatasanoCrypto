package set1;

public class RepeatingKeyXor {

	// Take in a String and Key, return a String which is the original String
	// XOR'd with the key
	public static String RepeatingKeyXor(String key, String text) {
		byte[] plainText = text.getBytes();
		byte[] xorKey = key.getBytes();
		byte[] encrypted = new byte[plainText.length];

		// XOR the bytes of the key with bytes of the input text repeatedly
		for (int i = 0; i < plainText.length; i++) {
			encrypted[i] = (byte) (plainText[i] ^ xorKey[(i % xorKey.length)]);
		}

		return bytesToHex(encrypted);
	}

	// Convert a byte array to a hex string
	public static String bytesToHex(byte[] bytes) {
		StringBuilder string = new StringBuilder();
		for (byte b : bytes) {
			string.append(String.format("%02x", b));
		}
		return string.toString();
	}

}
