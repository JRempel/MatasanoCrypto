package set1;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

public class DetectSingleXor {
	/*
	 * Takes in a file that contains hex-encoded lines, and returns the decoded
	 * line that has been XOR'd with a single byte. If there are multiple, it
	 * will return the highest weighted line, where weight is the sum of
	 * character frequencies in the line (According to the HashMap in
	 * SingleByteXor).
	 */
	public static String detectSingleXor(String FilePath) throws IOException {

		// Read file-lines into a list
		List<String> list = Files.readAllLines(new File(FilePath).toPath(), Charset.defaultCharset());
		String correct = "";
		double value = 0.0, resultWeight;
		String result;

		// Try SingleByteXor for each line, retain most-likely
		for (String s : list) {
			try {
				result = SingleByteXor.SingleByteXor(s);
				resultWeight = SingleByteXor.getWeight(result);
				if (resultWeight > value) {
					value = resultWeight;
					correct = result;
				}
			} catch (Exception e) {
			}
		}
		return correct;
	}
}
