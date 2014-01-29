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

import static org.jenetics.TestUtils.newFloat64GenePopulation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javolution.xml.XMLSerializable;

import org.jscience.mathematics.number.Float64;
import org.testng.Assert;
import org.testng.annotations.Test;

import org.jenetics.util.Function;
import org.jenetics.util.IO;
import org.jenetics.util.Serialize;
import org.jenetics.util.lists;


/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version <em>$Date$</em>
 */
public class PopulationTest {

	private static final class Continous
		implements Function<Genotype<Float64Gene>, Float64>,
					Serializable
	{
		private static final long serialVersionUID = 1L;
		@Override
		public Float64 apply(Genotype<Float64Gene> genotype) {
			return genotype.getChromosome().getGene().getAllele();
		}
	}

	private static final Function<Genotype<Float64Gene>, Float64> _cf = new Continous();
	private static Phenotype<Float64Gene, Float64> pt(double value) {
		return Phenotype.valueOf(Genotype.valueOf(
			new Float64Chromosome(Float64Gene.valueOf(value, 0, 10))
		), _cf, 0).evaluate();
	}

	@Test
	public void sort() {
		final Population<Float64Gene, Float64> population = new Population<>();
		for (int i = 0; i < 100; ++i) {
			population.add(pt(Math.random()*9.0));
		}

		population.sort();
		for (int i = 0; i < population.size() - 1; ++i) {
			Float64 first = _cf.apply(population.get(i).getGenotype());
			Float64 second = _cf.apply(population.get(i + 1).getGenotype());
			Assert.assertTrue(first.compareTo(second) >= 0);
		}

		lists.shuffle(population);
		population.sortWith(Optimize.MAXIMUM.<Float64>descending());
		for (int i = 0; i < population.size() - 1; ++i) {
			Float64 first = _cf.apply(population.get(i).getGenotype());
			Float64 second = _cf.apply(population.get(i + 1).getGenotype());
			Assert.assertTrue(first.compareTo(second) >= 0, first + "<" + second);
		}

		lists.shuffle(population);
		population.sortWith(Optimize.MINIMUM.<Float64>descending());
		for (int i = 0; i < population.size() - 1; ++i) {
			Float64 first = _cf.apply(population.get(i).getGenotype());
			Float64 second = _cf.apply(population.get(i + 1).getGenotype());
			Assert.assertTrue(first.compareTo(second) <= 0, first + ">" + second);
		}
	}

	@Test
	public void phenotypeXMLSerialization()
		throws IOException
	{
		final Population<Float64Gene, Float64> population = new Population<>();
		for (int i = 0; i < 1000; ++i) {
			population.add(pt(Math.random()*9.0));
		}

		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		IO.xml.write(population, out);

		final byte[] data = out.toByteArray();

		final ByteArrayInputStream in = new ByteArrayInputStream(data);

		@SuppressWarnings("unchecked")
		final Population<Float64Gene, Float64>
		copy = (Population<Float64Gene, Float64>)IO.xml.read(XMLSerializable.class, in);

		for (int i = 1; i < population.size(); ++i) {
			Assert.assertSame(
				copy.get(i - 1).getFitnessFunction(),
				copy.get(i).getFitnessFunction()
			);
			Assert.assertSame(
				copy.get(i - 1).getFitnessScaler(),
				copy.get(i).getFitnessScaler()
			);
		}
	}

	@Test
	public void xmlSerialization() throws IOException {
		final Population<Float64Gene, Float64> population = newFloat64GenePopulation(23, 34, 123);
		Serialize.xml.test(population);
	}

	@Test
	public void objectSerialization() throws IOException {
		final Population<Float64Gene, Float64> population = newFloat64GenePopulation(23, 34, 123);
		Serialize.object.test(population);
	}

}





