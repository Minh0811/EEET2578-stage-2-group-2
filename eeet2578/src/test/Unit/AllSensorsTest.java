package test.Unit;


import main.AllSensors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;



import static org.junit.jupiter.api.Assertions.*;


 class AllSensorsTest {
	private AllSensors allSensors;
	
	class ParameterizedTests {
		

		@ParameterizedTest
		@ValueSource(strings = {"David", "Jack"})
		@DisplayName("ValidInput Test")
		void validInput(String username) { 
			allSensors = new AllSensors(username);
			assertEquals(username, allSensors.username);
			assertTrue(allSensors.signal);
			assertNotEquals(null, allSensors.communicator);
			assertNotEquals(null, allSensors.aqiSensor);
			assertNotEquals(null, allSensors.locationSensor);
			assertNotEquals(null, allSensors.temperatureSensor);
			assertNotEquals(null, allSensors.monitor);
		}
		
		@ParameterizedTest
		@ValueSource(strings = {"Joe", ""})
		@DisplayName("InvalidInput Test")
		void invalidInputTest(String data) {
			allSensors = new AllSensors(data);
			assertThrows(FileNotFoundException.class, () => new AllSensors(username));
		}
		
		@ParameterizedTest
		@ValueSource(strings = {"Jack,A,200,10"})
		@DisplayName("invaliInputTest Test")
		void invaliInputTest(String data) {
		String user = data.split(",")[0];
		String location = data.split(",")[1];
		int aqi = Integer.parseInt(data.split(",")[2]);; 
		int temp = Integer.parseInt(data.split(",")[3]);
		assertEquals(user, allSensors.getSensorData().username);
		assertEquals(location, allSensors.getSensorData().location);
		assertEquals(aqi, allSensors.getSensorData().aqi);
		assertEquals(temp, allSensors.getSensorData().temperature);
		
		}
	}
}
