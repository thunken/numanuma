package com.thunken.numanuma;

import java.util.BitSet;
import java.util.EnumSet;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Implementations of {@link Collector} that implement various useful reduction operations for enumerated types, such as
 * accumulating elements into an {@link EnumSet} and other bit vectors.
 *
 * @see EnumSets
 */
public final class EnumCollectors {

	private EnumCollectors() {
		/* NO OP */
	}

	/**
	 * Returns a {@code Collector} that accumulates the input elements into a new {@code int}-based bit flag.
	 *
	 * @param <E>
	 *            The type of the elements to collect.
	 * @return A {@code Collector} which collects all the input elements into a new {@code int}-based bit flag.
	 */
	public static <E extends Enum<E>> Collector<E, ?, Integer> toBitFlag() {
		return Collectors.reducing(0, Enum::ordinal, (bitFlag, ordinal) -> (bitFlag | 1 << ordinal));
	}

	/**
	 * Returns a {@code Collector} that accumulates the input elements into a new {@code BitSet}.
	 *
	 * @param <E>
	 *            The type of the elements to collect.
	 * @return A {@code Collector} which collects all the input elements into a {@code BitSet}.
	 */
	public static <E extends Enum<E>> Collector<E, ?, BitSet> toBitSet() {
		return Collector.of(BitSet::new, (bitSet, element) -> bitSet.set(element.ordinal()), (bitSet1, bitSet2) -> {
			bitSet1.or(bitSet2);
			return bitSet1;
		});
	}

	/**
	 * Returns a {@code Collector} that accumulates the input elements into a new {@code EnumSet}.
	 *
	 * @param <E>
	 *            The type of the elements to collect.
	 * @param elementType
	 *            The class object of the element type for the specified {@link Enum}.
	 * @return A {@code Collector} which collects all the input elements into a new {@code EnumSet}.
	 * @throws NullPointerException
	 *             If {@code elementType} is null.
	 */
	public static <E extends Enum<E>> Collector<E, ?, EnumSet<E>> toEnumSet(final Class<E> elementType) {
		Objects.requireNonNull(elementType, "elementType is null");
		return Collectors.toCollection(() -> EnumSet.noneOf(elementType));
	}

}
