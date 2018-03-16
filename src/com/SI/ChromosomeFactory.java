package com.SI;

public class ChromosomeFactory {

    public Chromosome[] chromosomes;


    public ChromosomeFactory(int populationSize){
        chromosomes = new Chromosome[populationSize];
    }

    public Chromosome getChromosome(int index) {
        return chromosomes[index];
    }

    public void addChromosome(int index, Chromosome chromosome){
        chromosomes[index] = chromosome;
    }


    public Chromosome getBest(int[][] distMatrix, int[][] flowMatrix){
        Chromosome best = chromosomes[0];
        for(int i=1; i<chromosomes.length; i++){
            if(best.calculateCost(distMatrix, flowMatrix) > chromosomes[i].calculateCost(distMatrix, flowMatrix)){
                best = chromosomes[i];
            }
        }
        return best;
    }

    public int getAvg(int[][] distMatrix, int[][] flowMatrix){
        int sum = 0;
        for(int i=0; i<chromosomes.length; i++){
            sum += chromosomes[i].calculateCost(distMatrix,flowMatrix);
        }
        return sum / chromosomes.length;
    }


    public int getWorst(int[][] distMatrix, int[][] flowMatrix){
        Chromosome worst = chromosomes[0];
        for(int i=0; i<chromosomes.length; i++){
            if(worst.calculateCost(distMatrix, flowMatrix) < chromosomes[i].calculateCost(distMatrix,flowMatrix)){
                worst = chromosomes[i];
            }
        }
        return worst.calculateCost(distMatrix,flowMatrix);
    }


}
