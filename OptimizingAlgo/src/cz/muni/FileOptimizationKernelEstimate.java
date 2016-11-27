package cz.muni;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class FileOptimizationKernelEstimate {

    public static void main(String[] args) {
        String csvFile = "/Users/User/Documents/Skola/Bakina/ThroughputConst/dataCeleThroughputConst.csv";
        String line;
        Map<Integer, Double> fullDataValues = new LinkedHashMap<>();
        Integer counter =1;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            line = br.readLine();
            while (line != null) {

                // use comma as separator
                String[] values = line.split(";");
                Double nextValue = Double.valueOf(values[1]);
                fullDataValues.put(counter++, nextValue);

                line = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        String resultFile = "/Users/User/Documents/Skola/Bakina/ThroughputConst/kernelEstimation30.csv";
        try (FileWriter writer = new FileWriter(resultFile)) {

            KernelEstimator kernelEstimator = new KernelEstimator();
            Map<Integer, List<Double>> estimatedValues = kernelEstimator.kernelEstimate(fullDataValues, 30);
            int index = 0;
            for (Map.Entry<Integer, List<Double>> entry : estimatedValues.entrySet()) {
                StringBuilder sb = new StringBuilder();
                if (entry.getValue() != null) {
                    sb.append(entry.getKey()).append(';').append(entry.getValue().get(0));
                    if( entry.getValue().size() > 1){
                        sb.append(';').append(entry.getValue().get(1)).append(';').append(entry.getValue().get(2));
                    }
                    sb.append("\n");
                    writer.append(sb.toString());
                }
                index++;
                //pre priklady
                //if(index == 100){
                    //break;
                //}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
