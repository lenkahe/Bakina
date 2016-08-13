package cz.muni;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import static java.lang.Math.abs;


public class CountingForRco {

    public void countigZeroValue(Map<String, Double> withZeros){
        Double valueToAdd = 0d;
        Integer countOfZeroValues = 0;
        Double lastValue = 0d;

        for(Map.Entry<String, Double> entry : withZeros.entrySet()){
            if (entry.getValue() == 0d && countOfZeroValues == 0){
                List<Double> values = new ArrayList<>(withZeros.values());
                Integer firstIndex = values.indexOf(0d);
                Double firstValue = values.get(firstIndex - 1);
                Double value = 0d;
                countOfZeroValues = 0;
                while (value == 0d){
                    countOfZeroValues++;
                    value = values.get(firstIndex + countOfZeroValues);
                }

                Double secondValue = value;

                valueToAdd = (secondValue - firstValue)/ (countOfZeroValues + 1);
            }

            if(countOfZeroValues != 0){
                entry.setValue(lastValue + valueToAdd);
                countOfZeroValues--;
            }

            lastValue = entry.getValue();
        }

    }

    public Map<String, Integer> rocValues(List<Double> fullData, List<Double> optimizeData){
        Integer falsePositive = 0;
        Integer truePositive = 0;
        Integer falseNegative = 0;
        Integer trueNegative = 0;

        for(int i = 1; i < fullData.size() - 1; i++){
            if (fullData.get(i).equals(optimizeData.get(i))){
                if(abs(fullData.get(i) - fullData.get(i - 1)) > 40.0){
                    trueNegative++;
                } else {
                    falseNegative++;
                }
            } else {
                if (abs(fullData.get(i) - optimizeData.get(i)) > 60.0){
                    falsePositive++;
                } else {
                    truePositive++;
                }
            }

        }

        Map<String, Integer> result = new HashMap<>();
        result.put("falseNegative",falseNegative);
        result.put("falsePositive", falsePositive);
        result.put("truePositive", truePositive);
        result.put("trueNegative", trueNegative);
        return result;


    }
}
