package com.thunken.numanuma;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

/**
 * Utility methods for working with enumerated types.
 *
 * @see Enum
 * @see EnumSets
 */
@UtilityClass
public class Enums {

	/**
	 * Returns the number of elements in the specified enumerated type.
	 *
	 * @param <E>
	 *            The type of the elements in the specified {@link Enum}.
	 * @param elementType
	 *            The class object of the element type for the specified {@link Enum}.
	 * @return The number of elements in the specified enumerated type.
	 * @throws NullPointerException
	 *             If {@code elementType} is null.
	 */
	public static <E extends Enum<E>> int cardinality(@NonNull final Class<E> elementType) {
		return elementType.getEnumConstants().length;
	}

	/**
	 * Returns a pseudorandom, uniformly distributed element from the specified enumerated type.
	 *
	 * @param <E>
	 *            The type of the elements in the specified {@link Enum}.
	 * @param elementType
	 *            The class object of the element type for the specified {@link Enum}.
	 * @return A random element from the specified enumerated type.
	 * @throws NullPointerException
	 *             If {@code elementType} is null.
	 * @see Enums#randomElement(Class, Random)
	 */
	public static <E extends Enum<E>> E randomElement(@NonNull final Class<E> elementType) {
		return randomElement(elementType, ThreadLocalRandom.current());
	}

	/**
	 * Returns a pseudorandom, uniformly distributed element from the specified enumerated type.
	 *
	 * @param <E>
	 *            The type of the elements in the specified {@link Enum}.
	 * @param elementType
	 *            The class object of the element type for the specified {@link Enum}.
	 * @param random
	 *            The source of randomness to use.
	 * @return A random element from the specified enumerated type.
	 * @throws NullPointerException
	 *             If {@code elementType} or {@code random} is null.
	 * @see Enums#randomElement(Class)
	 */
	public static <E extends Enum<E>> E randomElement(@NonNull final Class<E> elementType,
			@NonNull final Random random) {
		final E[] enumConstants = elementType.getEnumConstants();
		return enumConstants[random.nextInt(enumConstants.length)];
	}

	/**
	 * Returns a sequential {@link Stream} with all the elements of the specified enumerated type as its source.
	 *
	 * @param <E>
	 *            The type of the elements in the specified {@link Enum}.
	 * @param elementType
	 *            The class object of the element type for the specified {@link Enum}.
	 * @return A sequential {@link Stream} with all the elements of the specified enumerated type.
	 * @throws NullPointerException
	 *             If {@code elementType} is null.
	 */
	public static <E extends Enum<E>> Stream<E> stream(@NonNull final Class<E> elementType) {
		return Arrays.stream(elementType.getEnumConstants());
	}

	/**
	 * Returns the element of the specified enumerated type with the specified constant's name, converting from one type
	 * to the other. The name must match exactly an identifier used to declare a constant in the specified type.
	 * (Extraneous whitespace characters are not permitted.)
	 *
	 * @param <E1>
	 *            The type of the elements in the specified {@link Enum}.
	 * @param <E2>
	 *            The type of the specified constant.
	 * @param elementType
	 *            The class object of the element type for the specified {@link Enum}.
	 * @param element
	 *            The element to convert.
	 * @return An {@link Optional} describing the element with the specified name if the enumerated type has such a
	 *         constant, otherwise an empty {@code Optional}.
	 * @throws NullPointerException
	 *             If {@code elementType} or {@code element} is null.
	 * @see Enums#valueOf(Class, String)
	 */
	public static <E1 extends Enum<E1>, E2 extends Enum<E2>> Optional<E1> valueOf(@NonNull final Class<E1> elementType,
			@NonNull final E2 element) {
		return elementType.isInstance(element) ? Optional.of(elementType.cast(element))
				: valueOf(elementType, element.name());
	}

	/**
	 * Returns the element of the specified enumerated type with the specified name. The name must match exactly an
	 * identifier used to declare a constant in this type. (Extraneous whitespace characters are not permitted.)
	 *
	 * @param <E>
	 *            The type of the elements in the specified {@link Enum}.
	 * @param elementType
	 *            The class object of the element type for the specified {@link Enum}.
	 * @param name
	 *            The name of the constant to return.
	 * @return An {@link Optional} describing the element with the specified name if the enumerated type has such a
	 *         constant, otherwise an empty {@code Optional}.
	 * @throws NullPointerException
	 *             If {@code elementType} or {@code name} is null.
	 * @see Enum#valueOf(Class, String)
	 * @see Enums#valueOf(Class, Enum)
	 */
	public static <E extends Enum<E>> Optional<E> valueOf(@NonNull final Class<E> elementType,
			@NonNull final String name) {
		try {
			return Optional.of(Enum.valueOf(elementType, name));
		} catch (final IllegalArgumentException e) {
			return Optional.empty();
		}
	}

}
