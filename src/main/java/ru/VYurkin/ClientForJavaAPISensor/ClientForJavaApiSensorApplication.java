package ru.VYurkin.ClientForJavaAPISensor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.VYurkin.ClientForJavaAPISensor.method.GET;
import ru.VYurkin.ClientForJavaAPISensor.method.POST;

@SpringBootApplication
public class ClientForJavaApiSensorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientForJavaApiSensorApplication.class, args);

		final String name ="Sensor121";

		POST sensor = new POST("http://localhost:8070/sensors/registration", name);

		sensor.postRegistration();


		final int numberOfMeasurements = 1000;

		POST measurements = new POST("http://localhost:8070/measurements/add", name);

		measurements.postAddMeasurements(numberOfMeasurements);


		System.setProperty("java.awt.headless", "false");

		GET.Graphic();

	}

}
