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
 *    Franz Wilhelmstötter (franz.wilhelmstoetter@gmx.at)
 */
package org.jenetics;

import static java.lang.String.format;
import static org.jenetics.util.math.subset;

import java.util.Random;

import org.jenetics.util.IndexStream;
import org.jenetics.util.RandomRegistry;

/**
 * <p>
 * An enhanced genetic algorithm (EGA) combine elements of existing solutions in
 * order to create a new solution, with some of the properties of each parent.
 * Recombination creates a new chromosome by combining parts of two (or more)
 * parent chromosomes. This combination of chromosomes can be made by selecting
 * one or more crossover points, splitting these chromosomes on the selected
 * points, and merge those portions of different chromosomes to form new ones.
 * </p>
 * <p>
 * The recombination probability <i>P)r)</i> determines the probability that a
 * given individual (genotype, not gene) of a population is selected for
 * recombination. The (<i>mean</i>) number of changed individuals depend on the
 * concrete implementation and can be vary from
 * <i>P(r)</i>&middot;<i>N<sub>G</sub></i> to
 * <i>P(r)</i>&middot;<i>N<sub>G</sub></i>&middot;<i>O<sub>R</sub></i>, where
 * <i>O<sub>R</sub></i> is the order of the recombination, which is the number
 * of individuals involved int the {@link #recombine} method.
 * </p>
 *
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @since 1.0
 * @version 1.0 &mdash; <em>$Date$</em>
 */
public abstract class Recombinator<G extends Gene<?, G>>
	extends AbstractAlterer<G>
{

	private final int _order;

	/**
	 * Constructs an alterer with a given recombination probability.
	 *
	 * @param probability The recombination probability.
	 * @throws IllegalArgumentException if the {@code probability} is not in the
	 *         valid range of {@code [0, 1]} or the given {@code order} is
	 *         smaller than two.
	 */
	protected Recombinator(final double probability, final int order) {
		super(probability);
		if (order < 2) {
			throw new IllegalArgumentException(format(
				"Order must be greater than one, but was %d.", order
			));
		}
		_order = order;
	}

	/**
	 * Return the number of individuals involved in the
	 * {@link #recombine(Population, int[], int)} step.
	 *
	 * @return the number of individuals involved in the recombination step.
	 */
	public int getOrder() {
		return _order;
	}

	@Override
	public final <C extends Comparable<? super C>> int alter(
		final Population<G, C> population, final int generation
	) {
		final Random random = RandomRegistry.getRandom();
		final int order = Math.min(_order, population.size());
		final IndexStream stream = IndexStream.Random(
			population.size(), _probability
		);

		int alterations = 0;
		for (int i = stream.next(); i != -1; i = stream.next()) {
			final int[] individuals = subset(population.size(), order, random);
			individuals[0] = i;

			alterations += recombine(population, individuals, generation);
		}

		return alterations;
	}

	/**
	 * Recombination template method.
	 *
	 * @param <C> the fitness result type
	 * @param population the population to recombine
	 * @param individuals the array with the indexes of the individuals which
	 *         are involved in the <i>recombination</i> step. The length of the
	 *         array is {@link #getOrder()}. The first individual is the
	 *         <i>primary</i> individual.
	 * @param generation the current generation.
	 * @return the number of genes that has been altered.
	 */
	protected abstract <C extends Comparable<? super C>> int recombine(
		final Population<G, C> population,
		final int[] individuals,
		final int generation
	);


}






