package cz.muni;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Zvuk {

    public static void main(String[] args) {
       // String csvFile = "/Users/User/Documents/Skola/Bakina/ThroughputConst/zvuk15,3.csv";
        //String csvFile = "/Users/User/Documents/Skola/Bakina/Throughput/kernelEstimation30.csv";
        String csvFile = "/Users/User/Documents/Skola/Bakina/Throughput/zvukNovy6.csv";
        String line;
        String cvsSplitBy = ";";
        List<Double> window = new ArrayList<>();
        List<Double> fullDataValues = new ArrayList<>();
        List<Double> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            line = br.readLine();
            while (line != null) {

                Double sum = 0d;

                // use comma as separator
                String[] values = line.split(cvsSplitBy);
                Double nextValue = Double.valueOf(values[1]);
                fullDataValues.add(nextValue);

                line = br.readLine();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

      /*  for (int i = 0; i < fullDataValues.size(); i++){
            if (i  % 2 != 1 ){
                if (i + 1 < fullDataValues.size()) {
                    result.add((fullDataValues.get(i) + fullDataValues.get(i + 1)) / 2);
                }else {
                    break;
                }
            }
        }*/

        for (int i = 0; i < fullDataValues.size(); i++){
            if (i  % 3 != 0 ){
                if (i + 1 < fullDataValues.size()) {
                    result.add((fullDataValues.get(i) + fullDataValues.get(i + 1)) / 2);
                }else {
                    break;
                }
            }
        }



        String resultFile = "/Users/User/Documents/Skola/Bakina/Throughput/zvukNovy7.csv";

        //Integer valueCounter = 0;
        try (FileWriter writer = new FileWriter(resultFile)) {
            Double counter = 1d;

          /*  for (int i = 1; i <= 1; i++) {
                StringBuilder sb = new StringBuilder();
                sb.append(counter).append(';').append(fullDataValues.get(i - 1));
                sb.append("\n");
                writer.append(sb.toString());
                counter++;
            }*/

            for (Double entry : result) {
                StringBuilder sb = new StringBuilder();
                    sb.append(counter).append(';').append(entry);
                    sb.append("\n");
                    writer.append(sb.toString());
                counter = counter + 16;

            }
          /*  for (int i = fullDataValues.size() - (fullDataValues.size()%5) ; i < fullDataValues.size(); i++) {
                StringBuilder sb = new StringBuilder();
                sb.append(counter).append(';').append(fullDataValues.get(i));
                sb.append("\n");
                writer.append(sb.toString());
                counter++;
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(result.size());
    }

}
