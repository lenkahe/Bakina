package cz.muni;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class FileOptimizationKernelEstimate {

    public static void main(String[] args) {
        String csvFile = "/Users/User/Documents/Skola/Bakina/Memory/dataCeleMemory.csv";
        String line;
        String cvsSplitBy = ";";
        Map<Integer, Double> fullDataValues = new LinkedHashMap<>();
        Map<String, Double> result = new LinkedHashMap<>();
        Integer counter =0;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            line = br.readLine();
            while (line != null) {

                // use comma as separator
                String[] values = line.split(cvsSplitBy);
                Double nextValue = Double.valueOf(values[1]);
                result.put(values[0], nextValue);
                fullDataValues.put(counter++, nextValue);

                line = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        String resultFile = "/Users/User/Documents/Skola/Bakina/Jadrove odhady/kernelEstimationMemory8.csv";
        try (FileWriter writer = new FileWriter(resultFile)) {

            KernelEstimator kernelEstimator = new KernelEstimator();
            Map<Integer, Double> estimatedValues = kernelEstimator.kernelEstimate(fullDataValues, 8.0);
            int index = 0;
            for (Map.Entry<String, Double> entry : result.entrySet()) {
                result.replace(entry.getKey(), estimatedValues.get(index));
                StringBuilder sb = new StringBuilder();
                if (entry.getValue() != null) {
                    sb.append(entry.getKey()).append(';').append(entry.getValue());
                    sb.append("\n");
                    writer.append(sb.toString());
                }
                index++;
                if(index == 100){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
