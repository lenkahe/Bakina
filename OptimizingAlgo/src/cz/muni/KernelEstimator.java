package cz.muni;


import java.util.*;

public class KernelEstimator {

    private Double max = 0.0;
    private Double min = 10000.0;

    public Map<Integer, List<Double>> kernelEstimate(Map<Integer, Double> timeSeries, Integer smoothingWindow) {

        Map<Integer, List<Double>> result = new LinkedHashMap<>();
        List<Map.Entry<Integer, Double>> subMap;
        List<Map.Entry<Integer, Double>> entryList = new ArrayList<>(timeSeries.entrySet());
        int counter = 0;
        while (true) {

            subMap = entryList.subList(counter, counter + 2*smoothingWindow < timeSeries.size() ? counter + 2*smoothingWindow : timeSeries.size());

            if (counter == 0) {
                for (int j = 0; j <= smoothingWindow; j = j+1) {
                //for (Map.Entry<Integer, Double> value : subMap) {
                    kernelEstimateForX(result, subMap.subList(counter, j + smoothingWindow), subMap.get(j), smoothingWindow);
                }
            } else  if(counter + 2*smoothingWindow > timeSeries.size()){
                for (int j = smoothingWindow; j < subMap.size(); j = j+1){
                    kernelEstimateForX(result, subMap.subList(j - smoothingWindow, subMap.size()), subMap.get(j), smoothingWindow);
                }
                break;
            } else{
                kernelEstimateForX(result, subMap, subMap.get(smoothingWindow), smoothingWindow);
            }

            //counter = counter + 30;
            counter = counter + 1;
        }
        return result;
    }

    private void kernelEstimateForX(Map<Integer, List<Double>> result, List<Map.Entry<Integer, Double>> subMap, Map.Entry<Integer, Double> value, Integer smoothingWindow){

        Double denominator = 0.0;
        Double numerator = 0.0;
        Double smoothingWindowDouble = smoothingWindow.doubleValue();
        //Map.Entry<Integer, Double> value = subMap.get(i);
        for (Map.Entry<Integer, Double> subValue : subMap) {
            denominator = denominator + (( 1 -(Math.pow((value.getKey() - subValue.getKey()) / smoothingWindowDouble, 2))) * subValue.getValue());
            numerator = numerator + (1 - Math.pow((value.getKey() - subValue.getKey()) / smoothingWindowDouble, 2));
        }
        if (result.keySet().contains(value.getKey())) {
            Double average =  (denominator / numerator + result.get(value.getKey()).get(0))/2;
            result.get(value.getKey()).set(0, average);
        } else {
            List<Double> resultValue = new ArrayList<>();
            resultValue.add(denominator / numerator);

            max = value.getValue() > max ? value.getValue() : max;
            min = value.getValue() < min ? value.getValue() : min;
            if (value.getKey() % 100 == 0 ){
                resultValue.add(max);
                resultValue.add(min);
                max = 0.0;
                min = 10000.0;
            }
            result.put(value.getKey(), resultValue);
        }

    }
}
