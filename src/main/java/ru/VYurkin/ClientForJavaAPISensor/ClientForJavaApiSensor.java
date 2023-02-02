package ru.VYurkin.ClientForJavaAPISensor;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import ru.VYurkin.ClientForJavaAPISensor.model.Measurement;
import ru.VYurkin.ClientForJavaAPISensor.model.Measurements;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ClientForJavaApiSensor {
    public static void main(String[] args) {

        final String name ="Sensor121";
        postRegistration(name);

        final int numberOfMeasurements = 1000;
        postAddMeasurements(numberOfMeasurements, name);

        List<Double> temperature = getTemperature();
        Graphic(temperature);
    }

    private static void postAddMeasurements(int numberOfMeasurements, String name) {
        Random random= new Random();
        for(int i=0;i<numberOfMeasurements;i++){
            double value =random.nextDouble(200)-100;
            boolean raining = random.nextBoolean();
            createMeasurements(value, raining, name);
        }
    }

    private static void createMeasurements(double value, boolean raining, String name) {
        Map<String,Object> jsonMeasurements = new HashMap<>();
        jsonMeasurements.put("value", value);
        jsonMeasurements.put("raining", raining);
        jsonMeasurements.put("sensor", Map.of("name", name));
        String url = "http://localhost:8070/measurements/add";
        convertAndSend(jsonMeasurements, url);
    }

    public static void postRegistration(String name){
        Map<String,Object> jsonSensor = new HashMap<>();
        jsonSensor.put("name",name);
        String url = "http://localhost:8070/sensors/registration";
        convertAndSend(jsonSensor, url);
    }
    public static void convertAndSend(Map<String,Object> jsonSensor,String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(jsonSensor, headers);
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_RESET = "\u001B[0m";

        try {
            restTemplate.postForObject(url, request, String.class);
                System.out.println("Сообщение отправлено");
        }catch (HttpClientErrorException | ResourceAccessException e){
            System.out.println(ANSI_RED+e.getMessage()+ ANSI_RESET);
        }
    }

    public static List<Double> getTemperature(){
        final RestTemplate restTemplate = new RestTemplate();
        final String url = "http://localhost:8070/measurements";
        Measurements response = restTemplate.getForObject(url, Measurements.class);

        if(response==null|response.getMeasurements()==null)
            return Collections.emptyList();

        return response.getMeasurements().stream().map(Measurement::getValue).collect(Collectors.toList());

    }
    public static void Graphic(List<Double> temperature){
        double[] timeData = IntStream.range(0,temperature.size()).mapToDouble(Int->Int).toArray();
        double[] tempData = temperature.stream().mapToDouble(temp-> temp).toArray();

        XYChart chart = QuickChart.getChart("Temperature", "t", "T", "T(t)", timeData, tempData);

        new SwingWrapper(chart).displayChart();
    }


    }


