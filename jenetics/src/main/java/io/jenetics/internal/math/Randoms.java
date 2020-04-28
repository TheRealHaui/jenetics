/*
 * Java Genetic Algorithm Library (@__identifier__@).
 * Copyright (c) @__year__@ Franz Wilhelmstötter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author:
 *    Franz Wilhelmstötter (franz.wilhelmstoetter@gmail.com)
 */
package io.jenetics.internal.math;

import static java.lang.Double.compare;
import static java.lang.Double.doubleToLongBits;
import static java.lang.Double.longBitsToDouble;
import static java.lang.Float.compare;
import static java.lang.Float.floatToIntBits;
import static java.lang.Float.intBitsToFloat;
import static java.lang.Math.abs;
import static java.lang.String.format;
import static io.jenetics.internal.util.Requires.probability;

import java.util.Random;
import java.util.stream.IntStream;

import io.jenetics.util.IntRange;

/**
 * Some random helper functions.
 *
 * @author <a href="mailto:franz.wilhelmstoetter@gmail.com">Franz Wilhelmstötter</a>
 * @since 1.4
 * @version 5.2
 */
public final class Randoms {
	private Randoms() {}

	public static byte nextByte(final Random random) {
		return (byte) nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE + 1, random);
	}

	public static char nextChar(final Random random) {
		char c = '\0';
		do {
			c = (char)nextInt(
				Character.MIN_VALUE,
				Character.MAX_VALUE + 1,
				random
			);
		} while (!Character.isLetterOrDigit(c));

		return c;
	}

	public static short nextShort(final Random random) {
		return (short) nextInt(Short.MIN_VALUE, Short.MAX_VALUE + 1, random);
	}

	/**
	 * Returns a pseudo-random, uniformly distributed int value between origin
	 * (included) and bound (excluded).
	 *
	 * @param origin the origin (inclusive) of each random value
	 * @param bound the bound (exclusive) of each random value
	 * @param random the random engine to use for calculating the random
	 *        int value
	 * @return a random integer greater than or equal to {@code min} and
	 *         less than or equal to {@code max}
	 * @throws IllegalArgumentException if {@code origin >= bound}
	 */
	public static int nextInt(
		final int origin,
		final int bound,
		final Random random
	) {
		if (origin >= bound) {
			throw new IllegalArgumentException(format(
				"origin >= bound: %d >= %d", origin, bound
			));
		}

		final int value;
		int n = bound - origin;
		if (n > 0) {
			value = random.nextInt(n) + origin;
		} else {
			int r;
			do {
				r = random.nextInt();
			} while (r < origin || r >= bound);
			value = r;
		}

		return value;
	}

	/**
	 * Returns a pseudo-random, uniformly distributed int value between origin
	 * (included) and bound (excluded).
	 *
	 * @param range the allowed integer range
	 * @param random the random engine to use for calculating the random
	 *        int value
	 * @return a random integer greater than or equal to {@code min} and
	 *         less than or equal to {@code max}
	 * @throws IllegalArgumentException if {@code range.getMin() >= range.getMax()}
	 */
	public static int nextInt(final IntRange range, final Random random) {
		return range.size() == 1
			? range.min()
			: nextInt(range.min(), range.max(), random);
	}

	/**
	 * Returns a pseudo-random, uniformly distributed double value between
	 * min (inclusively) and max (exclusively).
	 *
	 * @param min lower bound for generated float value (inclusively)
	 * @param max upper bound for generated float value (exclusively)
	 * @param random the random engine used for creating the random number.
	 * @return a random float greater than or equal to {@code min} and less
	 *         than to {@code max}
	 * @throws NullPointerException if the given {@code random}
	 *         engine is {@code null}.
	 */
	public static float nextFloat(
		final float min,
		final float max,
		final Random random
	) {
		if (compare(min, max) >= 0) {
			throw new IllegalArgumentException(format(
				"min >= max: %f >= %f.", min, max
			));
		}

		float value = random.nextFloat()*(max - min) + min;
		if (compare(value, max) >= 0) {
			value = intBitsToFloat(floatToIntBits(max) - 1);
		}

		return value;
	}

	/**
	 * Returns a pseudo-random, uniformly distributed double value between
	 * min (inclusively) and max (exclusively).
	 *
	 * @param min lower bound for generated double value (inclusively)
	 * @param max upper bound for generated double value (exclusively)
	 * @param random the random engine used for creating the random number.
	 * @return a random double greater than or equal to {@code min} and less
	 *         than to {@code max}
	 * @throws NullPointerException if the given {@code random}
	 *         engine is {@code null}.
	 */
	public static double nextDouble(
		final double min,
		final double max,
		final Random random
	) {
		if (compare(min, max) >= 0) {
			throw new IllegalArgumentException(format(
				"min >= max: %f >= %f.", min, max
			));
		}

		double value = random.nextDouble()*(max - min) + min;
		if (compare(value, max) >= 0) {
			value = longBitsToDouble(doubleToLongBits(max) - 1);
		}

		return value;
	}

	public static String nextASCIIString(final int length, final Random random) {
		final char[] chars = new char[length];
		for (int i = 0; i < length; ++i) {
			chars[i] = (char)nextInt(32, 127, random);
		}

		return new String(chars);
	}

	public static String nextASCIIString(final Random random) {
		return nextASCIIString(nextInt(5, 20, random), random);
	}

	/*
	 * Conversion methods used by the 'Random' engine from the JDK.
	 */

	public static float toFloat(final int a) {
		return (a >>> 8)/(float)(1 << 24);
	}

	public static float toFloat(final long a) {
		return (int)(a >>> 40)/(float)(1 << 24);
	}

	public static double toDouble(final long a) {
		return (((a >>> 38) << 27) + ((int)a >>> 5))/(double)(1L << 53);
	}

	public static double toDouble(final int a, final int b) {
		return (((long)(a >>> 6) << 27) + (b >>> 5))/(double)(1L << 53);
	}


	/*
	 * Conversion methods used by the Apache Commons BitStreamGenerator.
	 */

	public static float toFloat2(final int a) {
		return (a >>> 9)*0x1.0p-23f;
	}

	public static float toFloat2(final long a) {
		return (int)(a >>> 41)*0x1.0p-23f;
	}

	public static double toDouble2(final long a) {
		return (a & 0xFFFFFFFFFFFFFL)*0x1.0p-52d;
	}

	public static double toDouble2(final int a, final int b) {
		return (((long)(a >>> 6) << 26) | (b >>> 6))*0x1.0p-52d;
	}

	/**
	 * Create an {@code IntStream} which creates random indexes within the
	 * given range and the index probability.
	 *
	 * @since 3.0
	 *
	 * @param random the random engine used for calculating the random
	 *        indexes
	 * @param start the start index (inclusively)
	 * @param end the end index (exclusively)
	 * @param p the index selection probability
	 * @return an new random index stream
	 * @throws IllegalArgumentException if {@code p} is not a
	 *         valid probability.
	 */
	public static IntStream indexes(
		final Random random,
		final int start,
		final int end,
		final double p
	) {
		probability(p);
		final int P = Probabilities.toInt(p);

		return equals(p, 0, 1E-20)
			? IntStream.empty()
			: equals(p, 1, 1E-20)
				? IntStream.range(start, end)
				: IntStream.range(start, end)
					.filter(i -> random.nextInt() < P);
	}

	private static boolean
	equals(final double a, final double b, final double delta) {
		return abs(a - b) <= delta;
	}

	/**
	 * Create an {@code IntStream} which creates random indexes within the
	 * given range and the index probability.
	 *
	 * @since 3.0
	 *
	 * @param random the random engine used for calculating the random
	 *        indexes
	 * @param n the end index (exclusively). The start index is zero.
	 * @param p the index selection probability
	 * @return an new random index stream
	 * @throws IllegalArgumentException if {@code p} is not a
	 *         valid probability.
	 * @throws NullPointerException if the given {@code random}
	 *         engine is {@code null}.
	 */
	public static IntStream indexes(
		final Random random,
		final int n,
		final double p
	) {
		return indexes(random, 0, n, p);
	}

	/**
	 * Create a new <em>seed</em> byte array of the given length.
	 *
	 * @see #seed(byte[])
	 * @see #seed()
	 *
	 * @param length the length of the returned byte array.
	 * @return a new <em>seed</em> byte array of the given length
	 * @throws NegativeArraySizeException if the given length is smaller
	 *         than zero.
	 */
	public static byte[] seedBytes(final int length) {
		return seed(new byte[length]);
	}

	/**
	 * Fills the given byte array with random bytes, created by successive
	 * calls of the {@link #seed()} method.
	 *
	 * @see #seed()
	 *
	 * @param seed the byte array seed to fill with random bytes.
	 * @return the given byte array, for method chaining.
	 * @throws NullPointerException if the {@code seed} array is
	 *         {@code null}.
	 */
	public static byte[] seed(final byte[] seed) {
		for (int i = 0, len = seed.length; i < len;) {
			int n = Math.min(len - i, Long.SIZE/Byte.SIZE);

			for (long x = seed(); n-- > 0; x >>= Byte.SIZE) {
				seed[i++] = (byte)x;
			}
		}

		return seed;
	}

	/**
	 * Calculating a 64 bit seed value which can be used for initializing
	 * PRNGs. This method uses a combination of {@code System.nanoTime()}
	 * and {@code new Object().hashCode()} calls to create a reasonable safe
	 * seed value:
	 * <p>
	 * <pre>{@code
	 * public static long seed() {
	 *     return seed(System.nanoTime());
	 * }
	 * }</pre>
	 * <p>
	 * This method passes all of the statistical tests of the
	 * <a href="http://www.phy.duke.edu/~rgb/General/dieharder.php">
	 * dieharder</a> test suite&mdash;executed on a linux machine with
	 * JDK version 1.7. <em>Since there is no prove that this will the case
	 * for every Java version and OS, it is recommended to only use this
	 * method for seeding other PRNGs.</em>
	 *
	 * @see #seed(long)
	 *
	 * @return the random seed value.
	 */
	public static long seed() {
		return seed(System.nanoTime());
	}

	/**
	 * Uses the given {@code base} value to create a reasonable safe seed
	 * value. This is done by combining it with values of
	 * {@code new Object().hashCode()}:
	 * <p>
	 * <pre>{@code
	 * public static long seed(final long base) {
	 *     final long objectHashSeed = ((long)(new Object().hashCode()) << 32) |
	 *                                         new Object().hashCode();
	 *     long seed = base^objectHashSeed;
	 *     seed ^= seed << 17;
	 *     seed ^= seed >>> 31;
	 *     seed ^= seed << 8;
	 *     return seed;
	 * }
	 * }</pre>
	 *
	 * @param base the base value of the seed to create
	 * @return the created seed value.
	 */
	public static long seed(final long base) {
		return mix(base, ObjectSeed.seed());
	}

	private static long mix(final long a, final long b) {
		long c = a^b;
		c ^= c << 17;
		c ^= c >>> 31;
		c ^= c << 8;
		return c;
	}

	private static final class ObjectSeed {
		private static long seed() {
			return (long)new ObjectSeed().hashCode() << 32 |
				new ObjectSeed().hashCode();
		}
	}

}
