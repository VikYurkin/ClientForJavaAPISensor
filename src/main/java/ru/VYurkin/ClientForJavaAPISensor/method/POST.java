package ru.VYurkin.ClientForJavaAPISensor.method;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class POST {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final Random random= new Random();

    private final String ANSI_RED = "\u001B[31m";
    private final String ANSI_RESET = "\u001B[0m";

    private double value;

    private boolean raining;

    private final String name;

    private final String url;

    public POST(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public void postAddMeasurements(int numberOfMeasurements) {

        for(int i=0;i<numberOfMeasurements;i++){
            this.setValue(random.nextDouble(-100, 100));
            this.setRaining(random.nextBoolean());
            createMeasurements();
        }
    }

    private void createMeasurements() {
        Map<String,Object> jsonMeasurements = new HashMap<>();
        jsonMeasurements.put("value", this.getValue());
        jsonMeasurements.put("raining", this.isRaining());
        jsonMeasurements.put("sensor", Map.of("name", this.name));
        convertAndSend(jsonMeasurements);
    }

    public void postRegistration(){
        Map<String,Object> jsonSensor = new HashMap<>();
        jsonSensor.put("name",this.name);
        convertAndSend(jsonSensor);
    }
    private void convertAndSend(Map<String,Object> jsonSensor) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(jsonSensor, headers);
        try {
            restTemplate.postForObject(this.url, request, String.class);
            System.out.println("Сообщение отправлено");
        }catch (HttpClientErrorException | ResourceAccessException e){
            System.out.println(ANSI_RED+e.getMessage()+ ANSI_RESET);
        }
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

}
