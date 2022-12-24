
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Knapsack_Problem {

	static final Double pc = 0.6; // probability of crossover
	static final Double pm = 0.1; // probability of Mutation
	static Random rand = new Random(); // instance of random class

	static Chromosome[] Population_initialization(Chromosome[] c, int itemNumber, int population_size) {
		for (int i = 0; i < population_size; i++) {
			c[i] = new Chromosome();
			for (int j = 0; j < itemNumber; j++) {
				c[i].genes.add(rand.nextBoolean());
			}
		}
		return c;

	}

	static Chromosome[] Calculate_fitness(Chromosome[] c, item[] t, int itemNumber, int maxWeight,
			int population_size) {
		// if the fitness
		// bigger than the
		// max weight the
		// chromosom will be
		// dicarded
		// (dicarded=true)

		/* neads an implementation */
		for (int i = 0; i < population_size; i++) {
			int total_C_c_fitness = 0, total_C_weight = 0;
			for (int j = 0; j < itemNumber; j++) {
				if (c[i].genes.get(j)) {
					total_C_weight += t[j].weight;
					total_C_c_fitness += t[j].value;
				}
			}
			if (total_C_weight > maxWeight || total_C_c_fitness == 0) {

				// c[i].discarded = true;
				c[i].fitness = 0;

			} else {
				c[i].fitness = total_C_c_fitness;
				c[i].discarded = false;

			}
		}
		return c;
	}

	static int Calculate_fitness_Number(Chromosome[] c, item[] t, int itemNumber, int maxWeight, int population_size) {
		int Fitness_of_all_population = 0;

		/* neads an implementation */
		for (int i = 0; i < population_size; i++) {
			int total_C_c_fitness = 0, total_C_weight = 0;
			for (int j = 0; j < itemNumber; j++) {
				if (c[i].genes.get(j)) {
					total_C_weight += t[j].weight;
					total_C_c_fitness += t[j].value;
				}
			}
			if (total_C_weight > maxWeight || total_C_c_fitness == 0) {

				// c[i].discarded = true;
				c[i].fitness = 0;

			} else {
				c[i].fitness = total_C_c_fitness;
				Fitness_of_all_population += total_C_c_fitness;
				c[i].discarded = false;
			}
		}

		return Fitness_of_all_population;
	}

	static Chromosome[] roulette(Chromosome[] c, int populationSize, int itemNumber) {
	int first_candidates_index = -1;
	int sec_candidates_index = -1;
	        first_candidates_index = SelectFirstCandidates(c, populationSize, itemNumber);
			sec_candidates_index=SelectSecondCandidates(c, populationSize, itemNumber,first_candidates_index);
			 System.out.println("selected numbers");
			System.out.println(first_candidates_index);
			System.out.println(sec_candidates_index);
			Chromosome[] c1 = new Chromosome[2];

			c[sec_candidates_index].selcted = true;

			c1 = crossOver(c, first_candidates_index, sec_candidates_index, itemNumber);
			c1 = mutation(c1, itemNumber);

			c = replace(c, c1, populationSize);
			return c;


	}
	static int SelectFirstCandidates(Chromosome[] c, int populationSize, int itemNumber) {

int first_candidates_index = -1;

Random random = new Random(); // instance of random class

int totalFitness = 0;
for (int i = 0; i < populationSize; i++) {
totalFitness += c[i].fitness;
}
int randomFitness = random.nextInt(totalFitness);

int cumulativeFitness = 0;
int preCumulative = 0;
for (int j = 0; j < populationSize; j++) {
cumulativeFitness += c[j].fitness;
if (cumulativeFitness >= randomFitness && first_candidates_index == -1 && !c[j].selcted
&& !c[j].discarded) {
first_candidates_index = j;
return first_candidates_index;

}

}

if (first_candidates_index == -1) {
System.out.println("roulette has choesen the same choromosome .. will re play it ");
first_candidates_index = SelectFirstCandidates(c, populationSize, itemNumber);
}

return first_candidates_index;
}

	static int SelectSecondCandidates(Chromosome[] c, int populationSize, int itemNumber, int first_candidates_index) {// will
																														// simulate
																														// the
																														// roultte
																														// wheel
		int sec_candidates_index = -1;

		Random random = new Random(); // instance of random class

		int totalFitness = 0;
		for (int i = 0; i < populationSize; i++) {
			totalFitness += c[i].fitness;
		}
		int randomFitness = random.nextInt(totalFitness);
	//	System.out.print("randomFitness: ");
	//	System.out.print(randomFitness);
		int cumulativeFitness = 0;
		int preCumulative = 0;
		for (int j = 0; j < populationSize; j++) {
			if (j != first_candidates_index) {
				cumulativeFitness += c[j].fitness;
				if (cumulativeFitness >= randomFitness && sec_candidates_index == -1 && !c[j].selcted
						&& !c[j].discarded) {
					sec_candidates_index = j;
					return sec_candidates_index;

				}
			}
		}
		if (first_candidates_index == -1 || sec_candidates_index  == -1 ||  first_candidates_index == sec_candidates_index) {
			System.out.println("roulette has choesen the same choromosome .. will re play it ");
			sec_candidates_index = SelectSecondCandidates(c, populationSize, itemNumber,first_candidates_index);
		}

		return sec_candidates_index;
	}

	static Chromosome[] crossOver(Chromosome[] c, int first_candidates_index, int sec_candidates_index,
			int itemNumber) {

		Double r2 = rand.nextDouble();
		// System.out.println("R2222222"+r2);
		if (r2 > pc) {
			return c;
		}
		int r1 = rand.nextInt(itemNumber - 1);
		Chromosome[] c1 = new Chromosome[2];
		c[first_candidates_index].selcted = false;
		c[sec_candidates_index].selcted = false;

		c1[0] = c[first_candidates_index];
		c1[1] = c[sec_candidates_index];

		// System.out.println("R11111111111_"+r1);
		for (int i = r1; i < itemNumber; i++) {
			Boolean tmp = c1[0].genes.get(i);
			c1[0].genes.set(i, c1[1].genes.get(i));
			c1[1].genes.set(i, tmp);
		}
		return c1;
	}

	static Chromosome[] mutation(Chromosome[] c, int itemNumber) {

		for (int i = 0; i < itemNumber; i++) {
			Double r3 = rand.nextDouble();

			if (r3 <= pm) {
				// System.out.println(r3+"r3");
				c[0].genes.set(i, !c[0].genes.get(i));
			}
		}

		for (int i = 0; i < itemNumber; i++) {
			Double r3 = rand.nextDouble();
			if (r3 <= pm) {
				// System.out.println(r3+"r3");
				c[1].genes.set(i, !c[1].genes.get(i));
			}
		}
		return c;
	}

	static Chromosome[] replace(Chromosome[] c, Chromosome[] c1, int populationSize) {
		Chromosome cMin = new Chromosome();
		Chromosome cTemp = new Chromosome();
		int index_min = 0;
		cMin = c[0];
		for (int i = 0; i < populationSize; i++) {
			cTemp = c[i];
			if (cMin.fitness > cTemp.fitness) {
				cMin = cTemp;
				index_min = i;
			}

		}
		c[index_min] = c1[0];
		// replace the second Chromosome
		index_min = 0;
		cMin = c[0];
		for (int i = 0; i < populationSize; i++) {
			cTemp = c[i];
			if (cMin.fitness > cTemp.fitness) {
				cMin = cTemp;
				index_min = i;
			}

		}
		c[index_min] = c1[1];

		return c;
	}

	public static void main(String[] args) throws IOException {
		File file = new File("C:/Users/User/Desktop/Fcai-4-1st term/Soft Computing/Codes/assignment1/input.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));

		int testcases = 0;
		int population_size = 10;
		// Declaring a string variable
		String st = br.readLine();
		// testcases = Integer.parseInt(st);
		testcases = 1;

		for (int j = 0; j < testcases; j++) {
			int itemNum = 0;
			int maxWieght = 0;
			int t_ind = 0;
			item[] t = new item[100];
			Chromosome[] c = new Chromosome[population_size];

			for (int i = 0; i > -1; i++) { // the algorithm for each test case
				if (i < (itemNum + 4)) {
					st = br.readLine();
				}
				if (i == 0 || i == 1) {
					continue;
				} else if (i == (2)) {
					maxWieght = Integer.parseInt(st);
				} else if (i == (3)) {
					itemNum = Integer.parseInt(st);

				} else if (i > 3 && i <= (itemNum + 3)) {
					int value = 0;
					int wieght = 0;
					int k = 0;
					int pow = 0;

					for (k = 0; k < 10; k++) {
						if (!(st.substring(k, k + 1)).matches(" ")) {

							pow = 10 * wieght;
							wieght = pow + Integer.parseInt(st.substring(k, k + 1));

						} else {

							for (int kk = (k + 1); kk < st.length(); kk++) {

								pow = 10 * value;
								value = pow + Integer.parseInt(st.substring(kk, kk + 1));
							}

							break;
						}

					}
					t[t_ind] = new item(wieght, value);
					t_ind++;
				} else {
					break;
				}
			}
			// now we filled out all items
			// we can perform a for loop here till we reach out the optimal sol
			int numberOfgenerations = 2;
			int fitness = 0;
			c = Population_initialization(c, itemNum, population_size);
			fitness = Calculate_fitness_Number(c, t, itemNum, maxWieght, population_size);
			System.out.println("Toatal Value ->before");
			System.out.println(fitness);

			for (int jj = 0; jj < numberOfgenerations; jj++) {
				c = Calculate_fitness(c, t, itemNum, maxWieght, population_size);

				for(int ii=0;ii<population_size;ii++){
					System.out.print("index:" );
					System.out.println(ii );
					System.out.print("fitness :" );
					System.out.println(c[ii].fitness );

					 
					 }


				c = roulette(c, population_size, itemNum);
			}
			fitness = Calculate_fitness_Number(c, t, itemNum, maxWieght, population_size);
			System.out.println("Toatal Value ->after");
			System.out.println(fitness);

			/*
			 * test
			 * 
			  for(int ii=0;ii<population_size;ii++){
			 for (int jj = 0; jj < itemNum; jj++) {
			  
			  System.out.print(c[ii].genes.get(jj) );
			  
			  }}
			 */

		}

	}
}