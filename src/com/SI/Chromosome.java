package com.SI;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Chromosome {



    public List<Integer> data;
    private int size;


    public Chromosome(int size) {
        this.size = size;
        data = IntStream.range(1, size+1).boxed().collect(Collectors.toList());
        Collections.shuffle(data);
    }

    public int getGene(int index){
        return data.get(index);
    }

    public void setGene(int index, int gene){
            if(data.size() <= index){
                data.add(index,gene);
            }
            else {
                data.set(index,gene);
            }
    }

    public void repair(){
            ArrayList<Integer> checked = new ArrayList<>();
            ArrayList<Integer> removed = new ArrayList<>();
            for(int i=1; i<data.size()+1; i++){
                removed.add(i);
            }
            for(int i=0; i<data.size(); i++){
                Integer current = data.get(i);
                if(!checked.contains(current)){
                    checked.add(current);
                    removed.remove(current);
                } else {
                    data.set(i,-1);
                }
            }
            while(data.contains(-1)){
                int index = data.indexOf(-1);
                data.set(index, removed.get(0));
                removed.remove(0);
            }
    }

    public int calculateCost(int[][] distanceMatrix, int[][] flowMatrix){
        int cost = 0;
        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++){
                cost += flowMatrix[i][j] * distanceMatrix[data.get(i)-1][data.get(j)-1];
            }
        }
        return cost;
    }


}
