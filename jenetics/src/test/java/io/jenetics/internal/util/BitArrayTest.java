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
package io.jenetics.internal.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmail.com">Franz Wilhelmstötter</a>
 */
public class BitArrayTest {

	private static final EquivalentValidator<byte[], BitArray> FROM_BYTES =
		EquivalentValidator.of(BitArray::new, (v, a) -> a.toByteArray());

	private static final EquivalentValidator<BigInteger, BitArray> FROM_BIGINT =
		EquivalentValidator.of(BitArray::of, (v, a) -> a.toBigInteger());

	private static final EquivalentValidator<String, BitArray> FROM_STRING =
		EquivalentValidator.of(BitArray::of, (v, a) -> a.toString());

	private static final EquivalentValidator<BitArray, byte[]> TO_BYTES =
		EquivalentValidator.of(
			BitArray::toByteArray,
			(a, v) -> new BitArray(v, 0, a.length())
		);

	private static final EquivalentValidator<BitArray, BigInteger> TO_BIGINT =
		EquivalentValidator.of(
			BitArray::toBigInteger,
			(a, v) -> BitArray.of(v, a.length())
		);

	private static final EquivalentValidator<BitArray, String> TO_STRING =
		EquivalentValidator.of(
			BitArray::toString,
			(a, v) -> BitArray.of(v, a.length())
		);

	@Test(dataProvider = "bytes")
	public void fromBytes(final byte[] value) {
		FROM_BYTES.verify(value);
	}

	@DataProvider
	public Object[][] bytes() {
		final Random random = new Random();
		final Supplier<byte[]> supplier = () -> {
			final int length = random.nextInt(100) + 1;
			final byte[] array = new byte[length];
			random.nextBytes(array);
			return array;
		};

		return Stream.generate(supplier)
			.limit(25)
			.map(value -> new Object[]{value})
			.toArray(Object[][]::new);
	}

	@Test(dataProvider = "bigIntegers")
	public void fromBigInteger(final BigInteger value) {
		FROM_BIGINT.verify(value);
	}

	@DataProvider
	public Object[][] bigIntegers() {
		final Random random = new Random();
		final Supplier<BigInteger> supplier = () ->
			BigInteger.probablePrime(random.nextInt(100) + 100, random);

		return Stream.generate(supplier)
			.limit(25)
			.map(value -> new Object[]{value})
			.toArray(Object[][]::new);
	}

	@Test(dataProvider = "strings")
	public void fromString(final String value) {
		FROM_STRING.verify(value);
	}

	@DataProvider
	public Object[][] strings() {
		final var random = new Random(1234);

		final List<Object[]> values = new ArrayList<>();
		for (int i = 0; i < 25; ++i) {
			final int length = random.nextInt(1000) + 1;
			final var string = IntStream.range(0, length)
				.mapToObj(__ -> random.nextBoolean() ? "1" : "0")
				.collect(Collectors.joining());

			values.add(new Object[]{string});
		}

		return values.toArray(new Object[0][]);
	}

	@Test(dataProvider = "arrays")
	public void toBytes(final BitArray ba) {
		TO_BYTES.verify(ba);
	}

	@Test(dataProvider = "arrays")
	public void toBigInteger(final BitArray ba) {
		TO_BIGINT.verify(ba);
	}

	@Test(dataProvider = "arrays")
	public void toString(final BitArray ba) {
		TO_STRING.verify(ba);
	}

	@DataProvider
	public Object[][] arrays() {
		final var random = new Random(1234);

		final List<Object[]> values = new ArrayList<>();
		for (int i = 0; i < 25; ++i) {
			final int length = random.nextInt(30) + 5;
			final int start = random.nextInt(23);
			final int end = length*Byte.SIZE - random.nextInt(25);

			final byte[] bits = new byte[length];
			random.nextBytes(bits);

			values.add(new Object[]{new BitArray(bits, start, end)});
		}
		values.add(new Object[]{BitArray.of("11111110101001100101101100100111101101011101")});

		return values.toArray(new Object[0][]);
	}


	@Test
	public void fromShortStringWithLength() {
		final var length = 30;
		final var value = "10010101";
		final var bits = BitArray.of(value, length);
		Assert.assertEquals(bits.length(), length);

		final var string = bits.toString();
		Assert.assertEquals(string.length(), length);
		Assert.assertEquals(string.substring(length - value.length()), value);
	}

	@Test
	public void fromLongStringWithLength() {
		final var length = 5;
		final var value = "11111111111111110010100";
		final var bits = BitArray.of(value, length);
		Assert.assertEquals(bits.length(), length);

		final var string = bits.toString();
		Assert.assertEquals(string.length(), length);
		Assert.assertEquals(string, value.substring(value.length() - length));
	}

	@Test
	public void fromBytesRange() {
		final var random = new Random();
		final var bytes = new byte[10];
		random.nextBytes(bytes);

		final var bits = BitArray.of(bytes, 6, 65);
		Assert.assertEquals(BitArray.of(bits.toByteArray(), bits.length()), bits);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void fromEmptyString() {
		BitArray.of("");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void withZeroLength() {
		BitArray.ofLength(0);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void withNegativeLength() {
		BitArray.ofLength(-10);
	}

}
