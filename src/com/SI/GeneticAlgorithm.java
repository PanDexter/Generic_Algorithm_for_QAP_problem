package com.SI;

import java.io.FileNotFoundException;
import java.util.Random;

public class GeneticAlgorithm {

    public static final String PATH = "had20.dat.txt";
    public static final int POP_SIZE = 300;
    public static final int TOUR = 100;
    public static final double PX = 0.7;
    public static final double PM = 0.01;
    public static final int GEN = 100;
    public static final int MAX_ITERATION = 1000;


    private int [][] distanceMatrix;
    private int [][] flowMatrix;
    private int size;
    private int counter;
    private Matrix matrix;
    private Random random;

    CSV csv = new CSV("random_v5");


    public GeneticAlgorithm() throws FileNotFoundException {
        matrix = new Matrix();
        flowMatrix = new int[0][0];
        distanceMatrix = new int[0][0];
        counter =1;
        random = new Random();
    }


    private void readMatrix(){
        matrix.readFromFile(PATH);
        flowMatrix = matrix.getFlowMatrix();
        distanceMatrix = matrix.getDistanceMatrix();
        size = matrix.getN();
    }

    public ChromosomeFactory initializePopulation(){
        ChromosomeFactory population;
        population = new ChromosomeFactory(POP_SIZE);

        for(int i=0; i<POP_SIZE; i++){

            Chromosome chromosome = new Chromosome(size);
            population.addChromosome(i, chromosome);
        }

        return population;
    }


    public ChromosomeFactory algorithm(ChromosomeFactory input) throws FileNotFoundException {


        ChromosomeFactory output;
        output = initializePopulation();
        Chromosome best =  input.getBest(distanceMatrix, flowMatrix);
        int score = best.calculateCost(distanceMatrix, flowMatrix);
        System.out.println(score);
        System.out.println(best.data);
        int avg = input.getAvg(distanceMatrix,flowMatrix);
        int worst = input.getWorst(distanceMatrix, flowMatrix);
        output.addChromosome(0, best);

        csv.appendToFile(Integer.toString(counter));
        csv.nextColumn();
        csv.appendToFile(Integer.toString(score));
        csv.nextColumn();
        csv.appendToFile(Integer.toString(avg));
        csv.nextColumn();
        csv.appendToFile(Integer.toString(worst));
        csv.nextLine();

        for (int i = 1; i < POP_SIZE; i++) {

            Chromosome newChromosome = tournament(input);

            if (random.nextInt(1)< PX) {
                Chromosome parent1 = newChromosome;
                Chromosome parent2 = input.getChromosome(random.nextInt(POP_SIZE));
                newChromosome = crossover(parent1, parent2);
            }

            newChromosome.repair();
            newChromosome = mutate(newChromosome);
            output.addChromosome(i, newChromosome);
        }

        while (counter < GEN)
        {
            counter++;
            algorithm(output);
        }
        csv.saveFile();
        return output;

    }


    public Chromosome mutate(Chromosome input){
        Chromosome chromosome = input;

        for(int i=0; i<size; i++) {
            if (random.nextInt(1) < PM) {
                int firstIndex = random.nextInt(size);
                int secondIndex = random.nextInt(size);

                int tempGene = chromosome.getGene(firstIndex);

                chromosome.setGene(firstIndex, chromosome.getGene(secondIndex));
                chromosome.setGene(secondIndex, tempGene);
            }
        }
        return chromosome;
    }


    public Chromosome crossover(Chromosome first, Chromosome second){
        Chromosome child;
        child = new Chromosome(size);

        int startPos = random.nextInt(size);

        for(int i=0; i<startPos; i++){
            child.setGene(i, first.getGene(i));
        }
        for(int i=startPos; i<size; i++){
            child.setGene(i, second.getGene(i));
        }

        return child;

    }


    public Chromosome tournament(ChromosomeFactory population) {
        Chromosome best = null;

        for (int i = 0; i < TOUR; i++) {
            Chromosome drawn = population.getChromosome(random.nextInt(POP_SIZE));
            if (best == null || drawn.calculateCost(distanceMatrix, flowMatrix) > best.calculateCost(distanceMatrix, flowMatrix)) {
                best = drawn;
            }

        }
        return best;
    }

    public int getGenerationCost(ChromosomeFactory population){
        int totalCost = 0;

        for(Chromosome chromosome : population.chromosomes){
                totalCost += chromosome.calculateCost(distanceMatrix,flowMatrix);
        }

        return totalCost;
    }

    public Chromosome roulette(ChromosomeFactory population) {
        int totalCost;
        Random random = new Random();

        totalCost = getGenerationCost(population);
        int rand = random.nextInt(totalCost);
        int partialSum = 0;

        for(int x = POP_SIZE-1; x>=0; x--){
            partialSum += population.getChromosome(x).calculateCost(distanceMatrix,flowMatrix);
            if(partialSum >= rand){
                return  population.getChromosome(x);
            }
        }
        return null;

    }


    public void randomSearch(ChromosomeFactory population){

        Random random = new Random();
        Chromosome best = population.getChromosome(random.nextInt(POP_SIZE));
        for(int i=0; i<MAX_ITERATION; i++){
            Chromosome current = population.getChromosome(random.nextInt(POP_SIZE));
            if(current.calculateCost(distanceMatrix,flowMatrix) < best.calculateCost(distanceMatrix,flowMatrix)){
                best = current;
            }

            int score = best.calculateCost(distanceMatrix, flowMatrix);
            System.out.println("Current best result = " + score);
            csv.appendToFile(Integer.toString(i));
            csv.nextColumn();
            csv.appendToFile(Integer.toString(score));
            csv.nextLine();

        }
        csv.saveFile();
    }


    public static void main(String[] args) throws FileNotFoundException {

        GeneticAlgorithm ga = new GeneticAlgorithm();
        ga.readMatrix();
        ChromosomeFactory inputPopulation = ga.initializePopulation();
        ga.algorithm(inputPopulation);


      //ga.randomSearch(inputPopulation);

    }
}
