package com.thunken.numanuma;

import java.util.BitSet;
import java.util.EnumSet;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Utility methods for working with {@link EnumSet} and other bit vectors.
 *
 * @see java.lang.Enum
 * @see java.util.EnumSet
 * @see java.util.BitSet
 */
public final class EnumSets {

	private EnumSets() {
		/* NO OP */
	}

	/**
	 * Creates an {@link EnumSet} with all the elements of this {@code Enum} that satisfy the given {@link Predicate}.
	 *
	 * @param <E>
	 *            The type of the elements in the {@link EnumSet}.
	 * @param elementType
	 *            The class object of the element type for the specified {@link Enum}.
	 * @param filter
	 *            The predicate used to filter elements of the specified {@link Enum}.
	 * @return An {@link EnumSet} with all the elements of this {@code Enum} that satisfy the given {@link Predicate}.
	 * @throws NullPointerException
	 *             If any of {@code elementType} or {@code filter} is null.
	 */
	public static <E extends Enum<E>> EnumSet<E> filter(final Class<E> elementType, final Predicate<E> filter) {
		Objects.requireNonNull(elementType, "elementType is null");
		Objects.requireNonNull(filter, "filter is null");
		final EnumSet<E> set = EnumSet.allOf(elementType);
		set.removeIf(filter.negate());
		return set;
	}

	/**
	 * Returns {@code true} if this set contains all elements of this type.
	 *
	 * @param <E>
	 *            The type of the elements in the {@link EnumSet}.
	 * @param set
	 *            The {@link EnumSet} to test.
	 * @return {@code true} if this set contains all elements of this type.
	 * @throws NullPointerException
	 *             If {@code set} is null.
	 */
	public static <E extends Enum<E>> boolean isFull(final EnumSet<E> set) {
		Objects.requireNonNull(set, "set is null");
		return EnumSet.complementOf(set).isEmpty();
	}

	/**
	 * Creates an {@link EnumSet} initialized from the specified {@code int}-based bit flag. For each set bit in the bit
	 * flag, the {@code Enum} constant with the corresponding {@link Enum#ordinal()} is added to the {@code EnumSet}.
	 *
	 * @param <E>
	 *            The type of the elements in the {@link EnumSet}.
	 * @param bitFlag
	 *            The {@code int}-based bit flag from which to initialize the {@link EnumSet}.
	 * @param elementType
	 *            The class object of the element type for the specified {@link Enum}.
	 * @return An {@link EnumSet} initialized from the specified {@code int}-based bit flag.
	 * @throws IllegalArgumentException
	 *             If the bit flag contains set bits with indices greater than the {@code Enum} constants' maximum
	 *             {@link Enum#ordinal()}.
	 * @throws NullPointerException
	 *             If {@code elementType} is null.
	 */
	public static <E extends Enum<E>> EnumSet<E> ofBitFlag(final int bitFlag, final Class<E> elementType) {
		if (bitFlag < 0) {
			throw new IllegalArgumentException("bitFlag cannot be negative");
		}
		Objects.requireNonNull(elementType, "elementType is null");
		final E[] enumConstants = elementType.getEnumConstants();
		final int cardinality = enumConstants.length;
		if (Integer.lowestOneBit(bitFlag) > cardinality) {
			throw new IllegalArgumentException(
					"the bit flag contains set bits with indices greater than the Enum constants' maximum ordinal");
		}
		final EnumSet<E> enumSet = EnumSet.noneOf(elementType);
		for (int i = 0; i < cardinality; i++) {
			if ((bitFlag & 1L << i) != 0) {
				enumSet.add(enumConstants[i]);
			}
		}
		return enumSet;
	}

	/**
	 * Creates an {@link EnumSet} initialized from the specified {@link BitSet}. For each set bit in the {@link BitSet},
	 * the {@code Enum} constant with the corresponding {@link Enum#ordinal()} is added to the {@code EnumSet}.
	 *
	 * @param <E>
	 *            The type of the elements in the {@link EnumSet}.
	 * @param bitSet
	 *            The {@link BitSet} from which to initialize the {@link EnumSet}.
	 * @param elementType
	 *            The class object of the element type for the specified {@link Enum}.
	 * @return An {@link EnumSet} initialized from the specified {@link BitSet}.
	 * @throws IllegalArgumentException
	 *             If the {@link BitSet} contains set bits with indices greater than the {@code Enum} constants' maximum
	 *             {@link Enum#ordinal()}.
	 * @throws NullPointerException
	 *             If {@code bitSet} or {@code elementType} is null.
	 */
	public static <E extends Enum<E>> EnumSet<E> ofBitSet(final BitSet bitSet, final Class<E> elementType) {
		Objects.requireNonNull(bitSet, "bitSet is null");
		Objects.requireNonNull(elementType, "elementType is null");
		final E[] enumConstants = elementType.getEnumConstants();
		if (bitSet.length() > enumConstants.length) {
			throw new IllegalArgumentException(
					"the BitSet contains set bits with indices greater than the Enum constants' maximum ordinal");
		}
		final EnumSet<E> enumSet = EnumSet.noneOf(elementType);
		for (int i = bitSet.nextSetBit(0); i >= 0; i = bitSet.nextSetBit(i + 1)) {
			enumSet.add(enumConstants[i]);
			if (i == Integer.MAX_VALUE) {
				break;
			}
		}
		return enumSet;
	}

	/**
	 * Creates an {@code int}-based bit flag initialized from the specified {@link EnumSet}. For each element in the
	 * {@link EnumSet}, the bit corresponding to the element's {@link Enum#ordinal()} is set.
	 *
	 * @param <E>
	 *            The type of the elements in the {@link EnumSet}.
	 * @param enumSet
	 *            The {@link EnumSet} from which to initialize the bit flag.
	 * @return An {@code int}-based bit flag initialized from the specified {@link EnumSet}.
	 * @throws NullPointerException
	 *             If {@code enumSet} is null.
	 */
	public static <E extends Enum<E>> int toBitFlag(final EnumSet<E> enumSet) {
		Objects.requireNonNull(enumSet, "enumSet is null");
		int bitFlag = 0;
		for (final E element : enumSet) {
			bitFlag |= 1L << element.ordinal();
		}
		return bitFlag;
	}

	/**
	 * Creates a {@link BitSet} initialized from the specified {@link EnumSet}. For each element in the {@link EnumSet},
	 * the bit corresponding to the element's {@link Enum#ordinal()} is set.
	 *
	 * @param <E>
	 *            The type of the elements in the {@link EnumSet}.
	 * @param enumSet
	 *            The {@link EnumSet} from which to initialize the {@link BitSet}.
	 * @return A {@link BitSet} initialized from the specified {@link EnumSet}.
	 * @throws NullPointerException
	 *             If {@code enumSet} is null.
	 */
	public static <E extends Enum<E>> BitSet toBitSet(final EnumSet<E> enumSet) {
		Objects.requireNonNull(enumSet, "enumSet is null");
		final BitSet bitSet = new BitSet();
		for (final E element : enumSet) {
			bitSet.set(element.ordinal());
		}
		return bitSet;
	}

}
