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
		@DisplayName("Abc")
		
		void validInput(String username) {
			allSensors = new AllSensors(username);
			assertEquals(username, allSensors.username);
		}
		
	}
}
