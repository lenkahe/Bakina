package cz.muni;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.lang.Math.abs;

public class FileOptimization {

    public static void main(String[] args) {
        String csvFile = "/Users/User/Documents/Skola/Bakina/Memory/kernelEstimationMemory30.csv";
        String line;
        String cvsSplitBy = ";";
        int windowCounter = 0;
        List<Double> window = new ArrayList<>();
        List<Double> fullDataValues = new ArrayList<>();
        Map<String, Double> result = new LinkedHashMap<>();
        Double average;
        Double variance;
        Double varianceBefore;
        String lastTime = "";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            line = br.readLine();
            while (line != null) {

                Double sum = 0d;

                // use comma as separator
                String[] values = line.split(cvsSplitBy);
                Double nextValue = Double.valueOf(values[1]);
                fullDataValues.add(nextValue);


                //initialization
                if (result.isEmpty()){
                    result.put(values[0],nextValue);
                    window.add(windowCounter, nextValue);
                    windowCounter = windowCounter >= 9 ? 0 : ++windowCounter;
                    line = br.readLine();
                    lastTime = values[0];
                    continue;
                }
                if((line = br.readLine()) == null ){
                    result.put(values[0],nextValue);
                    continue;
                }

                if(window.size() > windowCounter){
                    window.remove(windowCounter);
                }
                for (Double value : window) {
                    sum = sum + value;
                }

                average = sum/window.size();
                sum = 0.0;
                for (Double value : window) {
                    sum = sum + Math.pow(average - value, 2);
                }
                varianceBefore =sum /window.size();
                window.add(windowCounter, nextValue);
                windowCounter = windowCounter >= 9 ? 0 : ++windowCounter;
                sum = 0.0;
                for (Double value : window) {
                    sum = sum + value;
                }

                average = sum/window.size();
                sum = 0.0;
                for (Double value : window) {
                    sum = sum + Math.pow(average - value, 2);
                }
                variance =sum /(window.size()-1);
                Double standartDeviation = Math.sqrt(variance);
                if (Math.abs(average - nextValue) > standartDeviation + 20){
                ///if(Math.abs(Math.sqrt(variance) - Math.sqrt(varianceBefore)) > 5){
                //if(abs(average - nextValue) > 50){
                    //result.put(lastTime, window.get(windowCounter != 0 ? windowCounter - 1 : 9));
                    result.put(values[0], nextValue);
                } else{
                    result.put(values[0], 0d);
                }
               /* if(window.size() > windowCounter){
                    window.remove(windowCounter);
                }
                window.add(windowCounter, nextValue);
                windowCounter = windowCounter >= 9 ? 0 : ++windowCounter;*/
                lastTime = values[0];
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        String resultFile = "/Users/User/Documents/Skola/Bakina/Memory/reducedFile.csv";

        Integer valueCounter = 0;
        try (FileWriter writer = new FileWriter(resultFile)) {


            for (Map.Entry<String, Double> entry : result.entrySet()) {
                StringBuilder sb = new StringBuilder();
                if(entry.getValue() != 0d) {
                    sb.append(entry.getKey()).append(';').append(entry.getValue());
                    sb.append("\n");
                    writer.append(sb.toString());
                }
                if (entry.getValue() != 0d){
                    valueCounter++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        CountingForRco countingForRco = new CountingForRco();
        countingForRco.countigZeroValue(result);

        Map<String, Integer> resultMap = countingForRco.rocValues(fullDataValues, new ArrayList<>(result.values()));
        for (Map.Entry<String,Integer> entry : resultMap.entrySet()){
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        Integer TP = resultMap.get("truePositive");
        Integer TN = resultMap.get("trueNegative");
        Integer FP = resultMap.get("falsePositive");
        Integer FN = resultMap.get("falseNegative");

        System.out.println("Sensitivita = TP/(TP+FN): " + TP * 1.0/(TP+FN));
        System.out.println("Specificnost =  FP/(TN+FP): " + FP * 1.0/(TN+FP));

        System.out.println(valueCounter);
    }

}
