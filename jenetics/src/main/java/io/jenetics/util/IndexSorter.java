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
package io.jenetics.util;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.function.ToIntFunction;

/**
 * An {@code IndexSorter} doesn't touch the original array type, instead
 * an index lookup array is returned which allows to access the array in
 * an sorted order. The arrays are sorted in ascending order.
 *
 * <pre>{@code
 * final IndexSorter<double[]> sorter = IndexSorter.of(
 *     a -> a.length,
 *     (a, i, j) -> Double.compare(a[i], a[j])
 * );
 *
 * final double[] array = new Random().doubles(100).toArray();
 * final int[] indexes = sorter.sort(array);
 *
 * // 'Classical' array sort.
 * final double[] sorted = array.clone();
 * Arrays.sort(sorted);
 *
 * // Iterating the array in ascending order.
 * for (int i = 0; i < array.length; ++i) {
 *     assert sorted[i] == array[indexes[i]];
 * }
 * }</pre>
 *
 * @author <a href="mailto:franz.wilhelmstoetter@gmail.com">Franz Wilhelmstötter</a>
 * @version !__version__!
 * @since !__version__!
 */
public interface IndexSorter {

	/**
	 * The comparator used for comparing two array elements at the specified
	 * indexes.
	 * <pre>{@code
	 * final Comp<double[]> comparator =
	 *     (a, i, j) -> Double.compare(a[i], a[j]);
	 * }</pre>
	 * The example above shows how to create a comparator for {@code double[]}
	 * arrays.
	 *
	 * @param <T> the array type, e.g. {@code int[]}, {@code double[]} or
	 *            {@code Seq<String>}
	 *
	 * @author <a href="mailto:franz.wilhelmstoetter@gmail.com">Franz Wilhelmstötter</a>
	 * @version !__version__!
	 * @since !__version__!
	 */
	@FunctionalInterface
	interface Comp<T> {

		public static final Comp<int[]> INT =
			(a, i, j) -> Integer.compare(a[i], a[j]);

		public static final Comp<double[]> DOUBLE =
			(a, i, j) -> Double.compare(a[i], a[j]);

		/**
		 * Compares the two array elements, specified by its indices, for order.
		 * Returns a negative integer, zero, or a positive integer as the first
		 * argument is less than, equal to, or greater than the second.
		 *
		 * @see java.util.Comparator#compare(Object, Object)
		 *
		 * @param array the array where the two comparing elements are fetched
		 * @param i the index of the first array element
		 * @param j the index of the second array element
		 * @return a negative integer, zero, or a positive integer as the first
		 *         argument is less than, equal to, or greater than the second.
		 * @throws NullPointerException if an argument is null and this
		 *         comparator does not permit null arguments
		 */
		public int compare(final T array, final int i, final int j);

		/**
		 * Returns a comparator that imposes the reverse ordering of this
		 * comparator.
		 *
		 * @return a comparator that imposes the reverse ordering of this
		 *         comparator.
		 */
		public default Comp<T> reversed() {
			return (a, i, j) -> compare(a, j, i);
		}

	}


	/**
	 * General array sort algorithm.
	 *
	 * @param array the array which is sorted
	 * @param length the array length
	 * @param comp the array element comparator
	 * @param <T> the array type
	 * @return the sorted index array
	 */
	public <T> int[] sort(
		final T array,
		final int length,
		final Comp<? super T> comp
	);

	/**
	 * Sorting the given {@code array} by changing the given {@code indexes}.
	 * The order of the original {@code array} stays unchanged.
	 *
	 * @param array the array to sort
	 * @return the index lookup array - &forall; i &isin; [0, N): index[i] = i
	 * @throws NullPointerException if one of the array is {@code null}
	 */
	public default int[] sort(final int[] array) {
		return sort(array, array.length, (a, i, j) -> Integer.compare(a[i], a[j]));
	}



	public default <T> int[] sort(
		final Seq<? extends T> array,
		final Comparator<? super T> comparator
	) {
		return sort(
			array,
			array.size(),
			(a, i, j) -> comparator.compare(a.get(i), a.get(j))
		);
	}

	public default int[] sort(final double[] array) {
		return sort(array, array.length, (a, i, j) -> Double.compare(a[i], a[j]));
	}


	/* *************************************************************************
	 * Static helper methods.
	 * ************************************************************************/

//	/**
//	 * Create a new index sorter with the given {@code length} function and
//	 * array element {@code comparator}.
//	 *
//	 * <pre>{@code
//	 * final IndexSorter<int[]> sorter = of(
//	 *     a -> a.length,
//	 *     (a, i, j) -> Integer.compare(a[i], a[j])
//	 * );
//	 * }</pre>
//	 *
//	 * @param length the array length function
//	 * @param comparator the array element index comparator
//	 * @param <T> the array type
//	 * @return a index sorter with the given parameter
//	 * @throws NullPointerException if one of the arguments is {@code null}
//	 */
//	public static <T> IndexSorter<T> of(
//		final ToIntFunction<? super T> length,
//		final Comp<? super T> comparator
//	) {
//		requireNonNull(length);
//		requireNonNull(comparator);
//
//		return a -> null;//sort(a, length.applyAsInt(a), comparator);
//	}
//
////	/**
////	 * Return an index sorter for object arrays of type {@code T}.
////	 *
////	 * @param comparator the array element comparator
////	 * @param <T> the element type
////	 * @return an index sorter for object arrays of type {@code T}
////	 */
////	public static <T> IndexSorter<T[]>
////	ofArray(final Comparator<? super T> comparator) {
////		return of(a -> a.length, (a, i, j) -> comparator.compare(a[i], a[j]));
////	}
////
////	public static <T> IndexSorter<Seq<T>>
////	ofSeq(final Comparator<? super T> comparator) {
////		return of(Seq::length, (a, i, j) -> comparator.compare(a.get(i), a.get(j)));
////	}

}
