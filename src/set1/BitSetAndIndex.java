package set1;

import java.util.BitSet;

public class BitSetAndIndex {
	private BitSet bits;
	private int index;

	public BitSetAndIndex(BitSet bits, int index) {
		this.bits = bits;
		this.index = index;
	}

	public BitSet getBitset() {
		return bits;
	}
	
	public int getIndex() {
		return index;
	}
}
