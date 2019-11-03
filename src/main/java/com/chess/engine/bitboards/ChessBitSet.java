package com.chess.engine.bitboards;
import java.util.BitSet;

public final class ChessBitSet extends BitSet {

	ChessBitSet() {
		super(64);
	}

	ChessBitSet(final ChessBitSet bitSet) {
		super(64);
		or(bitSet);
	}

	void shift(final int shiftValue) {
		final int length = length();
		if (shiftValue > 0) {
			if (length + shiftValue < 64) {
				for (int bitIndex = length; bitIndex >= 0; bitIndex--) {
					set(bitIndex + shiftValue, get(bitIndex));
				}
				clear(0, shiftValue);
			} else {
				clear(shiftValue, length + shiftValue);
			}
		} else if (shiftValue < 0) {
			if (length < -shiftValue) {
				clear();
			} else {
				for (int bitIndex = -shiftValue; bitIndex < length(); bitIndex++) {
					set(bitIndex + shiftValue, get(bitIndex));
				}
				clear(length + shiftValue, length);
			}
		}

	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size(); i++) {
			final boolean bit_is_set = get(i);
			if (bit_is_set) {
				sb.append(" 1 ");
			} else {
				sb.append(" . ");
			}
			if ((i + 1) % 8 == 0) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}

}