package ru.VYurkin.ClientForJavaAPISensor.method;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import ru.VYurkin.ClientForJavaAPISensor.model.Measurement;
import ru.VYurkin.ClientForJavaAPISensor.model.Measurements;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GET {

     private static List<Double> temperature;
     private static final RestTemplate restTemplate = new RestTemplate();
     private static final String url = "http://localhost:8070/measurements";
     private static final String ANSI_RED = "\u001B[31m";
     private static final String ANSI_RESET = "\u001B[0m";

    public static void Graphic(){

try {
        Measurements response = restTemplate.getForObject(url, Measurements.class);

        if(response==null|response.getMeasurements()==null) {temperature = Collections.emptyList();}
        else {temperature = response.getMeasurements().stream().map(Measurement::getValue).collect(Collectors.toList());}

        double[] timeData = IntStream.range(0,temperature.size()).mapToDouble(Int->Int).toArray();
        double[] tempData = temperature.stream().mapToDouble(temp-> temp).toArray();

        XYChart chart = QuickChart.getChart("Temperature", "t", "T", "T(t)", timeData, tempData);

        new SwingWrapper(chart).displayChart();
     }

catch (ResourceAccessException e){
        System.out.println(ANSI_RED+e.getMessage()+ ANSI_RESET);
     }

    }

}
