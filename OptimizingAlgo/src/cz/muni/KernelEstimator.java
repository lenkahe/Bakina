package cz.muni;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KernelEstimator {

    public Map<Integer, Double> kernelEstimate(Map<Integer, Double> timeSeries, Double smoothingWindow) {

        Map<Integer, Double> result = new HashMap<>();
        List<Map.Entry<Integer, Double>> subMap;
        List<Map.Entry<Integer, Double>> entryList = new ArrayList<>(timeSeries.entrySet());
        int counter = 0;
        while (counter < timeSeries.size()) {

            subMap = entryList.subList(counter, counter + 100 < timeSeries.size() ? counter + 100 : timeSeries.size());
            Double denominator = 0.0;
            Double numerator = 0.0;

            for (int i = 0; i < subMap.size(); i = i+5) {
            //for(Map.Entry<Integer, Double> value : subMap){
                Map.Entry<Integer, Double> value = subMap.get(i);
                for (Map.Entry<Integer, Double> subValue : subMap) {
                    denominator = denominator + (1 / smoothingWindow * ((1 / Math.sqrt(2 * Math.PI)) * Math.pow(Math.E, -(Math.pow((value.getKey() - subValue.getKey()) / smoothingWindow, 2) / 2)))) * subValue.getValue();
                    numerator = numerator + (1 / smoothingWindow * ((1 / Math.sqrt(2 * Math.PI)) * Math.pow(Math.E, -(Math.pow((value.getKey() - subValue.getKey()) / smoothingWindow, 2) / 2))));
                }
                if (result.keySet().contains(value.getKey())) {
                    result.put(value.getKey(), (denominator / numerator + result.get(value.getKey()))/2);
                } else {
                    result.put(value.getKey(), denominator / numerator);
                }
            }

            //counter = counter + 50;
            counter = counter + 20;
        }
        return result;
    }
}
