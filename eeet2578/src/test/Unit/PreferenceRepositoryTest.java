package test.Unit;

import static org.junit.jupiter.api.Assertions.*;
import com.zeroc.Ice.Current;
import main.PreferenceRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import helper.PreferenceRequest;
import support.Preference;

class PreferenceRepositoryTest {
	
    private static PreferenceRepository.PreferenceWorkerI worker;
 
    private String username;
    private static Current current;
    private PreferenceRequest request;
    private boolean bool;
    private List<Integer> tempList = new ArrayList<>();
    
	
	@BeforeAll 
	static void beforeAllClass() {
		PreferenceRepository.preferences = PreferenceRepository.readPreference();
		worker = new PreferenceRepository.PreferenceWorkerI();

	}
	
	//readPreference()Test:
	//Positive-test(correct input): the method should return an already existing username after calling that username 
	@Test
	void readPreference_CorrectUserName_PositiveTest() {
		request = new PreferenceRequest();
		request.username = "David";
        for (Preference preference : PreferenceRepository.preferences) {
            if (preference.getName().equals(request.username)) {
                bool = true;
            }
        }
        assertTrue(bool);
	}
	
	//Negative-test(wrong input): the method should not return a non-existing username after calling that username
	@Test
    void readPreference_IncorrectUserName_NegativeTest() {
        request = new PreferenceRequest();
        request.username = "avid";
        bool = false;
        for (Preference preference : PreferenceRepository.preferences) {
            if (preference.getName().equals(request.username)) {
                bool = true;
            }
        }
        assertFalse(bool);
    }
	
	//Negative-test(wrong username): the method should not return a non-existing username after calling that username
	@Test
    void readPreference_WrongUserName_NegativeTest() {
        request = new PreferenceRequest();
        request.username = "Joe";
        bool = false;
        for (Preference preference : PreferenceRepository.preferences) {
            if (preference.getName().equals(request.username)) {
                bool = true;
            }
        }
        assertFalse(bool);
    }
	
	                                                                                                                                                                
	//Negative-test(null username): the method should not return a non-existing username after calling null username
	@Test
    void readPreference_BlankInput_NegativeTest() {
        request = new PreferenceRequest();
        request.username = null;
        bool = false;
        for (Preference preference : PreferenceRepository.preferences) {
            if (preference.getName().equals(request.username)) {
                bool = true;
            }
        }
        assertFalse(bool);
    }
	

    //Negative-test(space username): the method should not return a username after calling space username
    @Test
    void readPreference_SpaceUsername_NegativeTest() {
        request = new PreferenceRequest();
        request.username = " ";
        bool = false;
        for (Preference preference : PreferenceRepository.preferences) {
            if (preference.getName().equals(request.username)) {
                bool = true;
            }
        }
        assertFalse(bool);
    }
    
    
    
    
    
    //getSuggestionTemp(String name, Integer tempThreshhold)
    //Positive-test(correct suggestion): the method should return correct suggestion when the user is David and temperature threshold is 16
    @Test
    void getSuggestionTemp_CorrectSugsgestion_PositiveTest() {
        assertEquals("pool",PreferenceRepository.getSuggestionTemp("David", 16));
    }
    
    //Negative-test(correct suggestion): the method should return correct suggestion when the user is David and temperature threshold is 16
    @Test
    void getSuggestionTemp_WrongTempThreshold_NegativeTest() {
        assertEquals("pool",PreferenceRepository.getSuggestionTemp("David", 18));
    }


    //Negative-test(execpted results is not equal to actual result): the method should not return an incorrect suggestion when the username and temperature threshold is correct
    @Test
    void getSuggestionTemp_WrongSuggestion_NegativeTest() {
        assertNotEquals("shops",PreferenceRepository.getSuggestionTemp("David", 16));
    }
    
    
    
    
    
    //getSuggestionAPO(String name)
    //Positive-test(correct username): the method should return a correct suggestion for the user  
    @Test
    void getSuggestionAPO_CorrectSugsgestion_PositiveTest() {
        assertEquals("cinema",PreferenceRepository.getSuggestionAPO("David"));
    }
    
    //Negative-test(Incorrect username): the method should return a null location because of an invalid user
    @Test
    void getSuggestionAPO_WrongUsername_NegativeTest() {
    	assertEquals(null,PreferenceRepository.getSuggestionAPO("Joe"));
    	
    }

    //Negative-test(Incorrect suggestion): the method should not return a different location cinema for the user "David"
    @Test
    void getSuggestionAPO_WrongSuggestion_NegativeTest() {
        assertNotEquals("pool",PreferenceRepository.getSuggestionAPO("David"));
    }

    
    
    
    
    
    //getSuggestionWeather(String name, Integer weather)
    //Positive-test(correct username and type of weather is 0): the method should return a correct location suggestion 
    @Test
    void getSuggestionWeather_CorrectSuggestion_PositiveTest() {
        assertEquals("shops",PreferenceRepository.getSuggestionWeather("David",0));
    }

    //Negative-test(Incorrect username): the method should return a null location because of an invalid type of weather
    @Test
    void getSuggestionWeather_InvalidWeatherType_NegativeTest() {
        assertEquals(null,PreferenceRepository.getSuggestionWeather("David",5));
    }
    
    //Negative-test(Incorrect location): the method should not return a different location from shops for the user "David"
    @Test
    void getSuggestionWeather_WrongSuggestion_NegativeTest() {
        assertNotEquals("cinema",PreferenceRepository.getSuggestionWeather("David",0));
    }
    
    
  //Positive-test(Correct medical condition type): the method should return correct medical condition type
    @Test
    void getUserInfo_CorrectMedicalConditionType_PositiveTest() {
    	username ="David";
        assertEquals(3,worker.getUserInfo(username,current).medicalConditionType);
    }

  //Negative-test(Incorrect medical condition type): the method should not return incorrect medical condition type
    @Test
    void getUserInfo_WrongMedicalConditionType_NegativeTest1(){
        username ="David";
        assertNotEquals(-1,worker.getUserInfo(username,current).medicalConditionType);
    }

  //Negative-test(Incorrect medical condition type): the method should not return incorrect medical condition type
    @Test
    void getUserInfo_WrongMedicalConditionType_NegativeTest2(){
        username ="David";
        assertNotEquals(4,worker.getUserInfo(username,current).medicalConditionType);
    }

  //Positive-test(Correct APO threshold): the method should return correct APO threshold
    @Test
    void getUserInfo_CorrectAPOThreshold_PositiveTest() {
        username ="David";
        assertEquals(0,worker.getUserInfo(username,current).apoThreshhold);
    }

    //Negative-test(Incorrect APO threshold): the method should not return incorrect APO threshold
    void getUserInfo_WrongAPOThreshold_NegativeTest() {
        username ="David";
        assertNotEquals(30,worker.getUserInfo("David",current).apoThreshhold);
    }

    //Positive-test(Correct weather type): the method should return correct weather type 
    @Test
    void getUserInfo_CorrectWeatherType_PositiveTest() {
        username ="David";
        assertEquals(0,worker.getUserInfo(username,current).weather);
    }
    
    //Negative-test(Incorrect weather type): the method should not return incorrect weather type
    @Test
    void getUserInfo_WrongWeatherType_NegativeTest1() {
        username ="David";
        assertNotEquals(5,worker.getUserInfo(username,current).weather);
    }
    
    //Negative-test(Incorrect weather type): the method should not return incorrect weather type
    @Test
    void getUserInfo_WrongWeatherType_NegativeTest2() {
        username ="David";
        assertNotEquals(-1,worker.getUserInfo(username,current).weather);
    }
    
    //Positive-test(Correct Temperature): the method should return correct temperature
    @Test
    void getUserInfo_CorrectTemperature_PositiveTest() {
        username = "Jack";
        tempList.add(20);
        tempList.add(30);
        assertEquals(tempList.toString(),Arrays.toString(worker.getUserInfo(username,current).tempThreshholds));
    }

    //Negative-test(Incorrect temperature): the method should not return incorrect temperature
    @Test
    void getUserInfo_WrongTemperature_NegativeTest() {
        username = "Jack";
        tempList.add(25);
        assertNotEquals(tempList.toString(),Arrays.toString(worker.getUserInfo(username,current).tempThreshholds));
    }

    
 
    
    
    
    
    //getPreference(PreferenceRequest request, Current current)
    //Positive-test(Correct reference of APO): the method should return correct reference 
    @Test
    void getPreference_CorrectPreferenceForAPO_PositiveTest() {
        request = new PreferenceRequest();
        request.username = "Jack";
        request.weather = 0;
        request.value ="APO";
        assertEquals("bowling",worker.getPreference(request, current));
    }

    //Negative-test(Incorrect reference of APO): the method should not return correct reference
    @Test
    void getPreference_WrongPreferenceForAPO_NeagtiveTest() {
        request = new PreferenceRequest();
        request.username = "Jack";
        request.weather = 0;
        request.value ="APO";
        assertNotEquals("shops",worker.getPreference(request, current));
    }

    //Positive-test(Correct reference of Temperature): the method should return correct reference 
    @Test
    void getPreference_CorrectPreferenceForTemperature_PositiveTest() {
        request = new PreferenceRequest();
        request.username = "Jack";
        request.weather = 0;
        request.value = "20";
        assertEquals("shops",worker.getPreference(request, current));
    }

    //Negative-test(Incorrect reference of temperature): the method should not return correct reference
    @Test
    void getPreference_WrongPreferenceForTemperature_NegativeTest() {
        request = new PreferenceRequest();
        request.username = "Jack";
        request.weather = 0;
        request.value = "20";
        assertNotEquals("cinema",worker.getPreference(request, current));
    }

    //Positive-test(Correct reference of weather): the method should return correct reference 
    @Test
    void getPreference_CorrectPreferenceForWeather_PositiveTest() {
        request = new PreferenceRequest();
        request.username = "Jack";
        request.weather = 1;
        request.value = "";
        assertEquals("cinema",worker.getPreference(request, current));
    }

    //Negative-test(Incorrect reference of weather): the method should not return correct reference
    @Test
    void getPreference_WrongPreferenceForWeather_NegativeTest() {
        request = new PreferenceRequest();
        request.username = "Jack";
        request.weather = 1;
        request.value = "";
        assertNotEquals("bowling",worker.getPreference(request, current));
    }

    //Positive-test(Correct reference of Context Manager): the method should return correct reference 
    @Test
    void getPreference_CorrectRequest_PositiveTest() {
        request = new PreferenceRequest();
        request.username = "Jack";
        request.weather = 0;
        request.value = "APO";
        bool = false;
        if (((worker.getUserInfo(request.username, current).weather) == request.weather && ((request.value == "" || request.value == "APO" || Arrays.toString(worker.getUserInfo(request.username,current).tempThreshholds).contains(request.value))))) {
            bool = true;
        }
        assertTrue(bool);
    }

    //Negative-test(Incorrect reference of Context Manager, Invalid AOP value): the method should not return correct reference
  //1
    @Test
    void getPreference_WrongRequest_NegativeTest1() {
        request = new PreferenceRequest();
        request.username = "David";
        request.weather = 0;
        request.value = "abc";
        bool = false;
        if (((worker.getUserInfo(request.username, current).weather) == request.weather && ((request.value == "" || request.value == "APO" || Arrays.toString(worker.getUserInfo(request.username,current).tempThreshholds).contains(request.value))))) {
            bool = true;
        }
        assertFalse(bool);
    }

    //Negative-test(Incorrect reference of Context Manager, Invalid space AOP value): the method should not return correct reference
    @Test
    void getPreference_WrongRequest_NegativeTest2() {
        request = new PreferenceRequest();
        request.username = "David";
        request.weather = 0;
        request.value = " ";
        bool = false;
        if (((worker.getUserInfo(request.username, current).weather) == request.weather && ((request.value == "" || request.value == "APO" || Arrays.toString(worker.getUserInfo(request.username,current).tempThreshholds).contains(request.value))))) {
            bool = true;
        }
        assertFalse(bool);
    }

    //Negative-test(Incorrect reference of Context Manager, Invalid weather value): the method should not return correct reference
    @Test
    void getPreference_WrongRequest_NegativeTest4() {
        request = new PreferenceRequest();
        request.username = "David";
        request.weather = 4;
        request.value = "*";
        bool = false;
        if (((worker.getUserInfo(request.username, current).weather) == request.weather && ((request.value == "" || request.value == "APO" || Arrays.toString(worker.getUserInfo(request.username,current).tempThreshholds).contains(request.value))))) {
            bool = true;
        }
        assertFalse(bool);
    }

    //Negative-test(Incorrect reference of Context Manager, Invalid temperature value): the method should not return correct reference
  //1
    @Test
    void getPreference_WrongRequest_NegativeTest5() {
        request = new PreferenceRequest();
        request.username = "Jack";
        request.weather = 0;
        request.value = "35";
        bool = false;
        if (((worker.getUserInfo(request.username, current).weather) == request.weather && ((request.value == "" || request.value == "APO" || Arrays.toString(worker.getUserInfo(request.username,current).tempThreshholds).contains(request.value))))) {
            bool = true;
        }
        assertFalse(bool);
    }
}
    

    
    
    
    
   