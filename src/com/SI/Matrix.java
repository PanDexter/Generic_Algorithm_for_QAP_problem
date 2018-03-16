package com.SI;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Matrix {

    public int n;
    public int[][] flowMatrix;
    public int[][] distanceMatrix;


    public Matrix(){}

    public Matrix(int n, int[][] flowMatrix, int[][] distanceMatrix) {
        this.n = n;
        this.flowMatrix = flowMatrix;
        this.distanceMatrix = distanceMatrix;
    }

    void readFromFile(String path) {
        String line = "";
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader br = new BufferedReader(fileReader);

            n = Integer.parseInt(br.readLine().trim());
            br.readLine();
            flowMatrix = new int[n][n];
            readMatrice(br, flowMatrix);
            br.readLine();
            distanceMatrix = new int [n][n];
            readMatrice(br, distanceMatrix);


            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            br.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + path + "'");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void printMatrice(int matrix[][]) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    void readMatrice(BufferedReader source, int[][] destination) throws IOException {
        for (int i = 0; i < n; i++){

            String line = source.readLine();
            String[] s_row = line.trim().split("\\s+");

            int i_row[] = new int[s_row.length];

            for (int j = 0; j < i_row.length; j++) {
                i_row[j] = Integer.parseInt(s_row[j]);
            }

            destination[i] = i_row;
        }
    }

    public int getN() {
        return n;
    }

    public int[][] getFlowMatrix() {
        return flowMatrix;
    }

    public int[][] getDistanceMatrix() {
        return distanceMatrix;
    }


}
