package test.Unit;

//import com.zeroc.Ice.current;
import helper.PreferenceWorkerPrx;
import helper.User;
import helper.ContextManagerWorker;
import main.ContextManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.BeforeClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.zeroc.Ice.Current;

import support.LocationDetails;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static main.ContextManager.locationWorker;
import static org.junit.jupiter.api.Assertions.*;

public class ContextManagerTest {
	
private ContextManagerWorker WorkerI;
static PreferenceWorkerPrx preferenceWorker;
private List<LocationDetails> list;
	
@BeforeClass
public static void setUpClass() {
    ContextManager.communicator = com.zeroc.Ice.Util.initialize();
    ContextManager.cityInfo = ContextManager.readCityInfo();
    ContextManager.iniPreferenceWorker();
    ContextManager.iniLocationMapper();
    ContextManager.iniWeatherAlarmWorker();
    ContextManager.runWeatherAlarm();
    ContextManager.setupContextManagerWorker();

}
@BeforeEach
void setUp() {
	WorkerI = new ContextManager.ContextManagerWorkerI();
    //SetupTest.setupService();
	}

@AfterEach 
void afterEach() {
	ContextManager.communicator.shutdown();
}

@ParameterizedTest
@ValueSource(strings = {"Jack,20"})
@DisplayName("addUserTempThreshold Test")
void addUserTempThresholdTest(String data) {
	String user = data.split(",")[0];
	int tempThresHold = Integer.parseInt(data.split(",")[1]);
	WorkerI.addUser(user, null);
	System.out.print(user);
	if (!user.equals("Jack")) {
		assertEquals(tempThresHold, ContextManager.users.get(user).tempThreshholds);
		} 
}


//readCityInfo()
@Test
@DisplayName("readCityInfo Test case")
public void readCityInfoTest() {
    List<LocationDetails> cityList = ContextManager.readCityInfo();
    String[] names = {"Vivo City Shopping Centre", "Crescent Mall", "Dam Sen Parklands", "Ho Chi Minh City, Downtown"};
    String[] locations = {"A", "B", "C", "D"};
    String[] cityInformations = {
    "Vivo City Shopping Centre is a major regional shopping centre in the southern suburb of Ho Chi Minh City, Vietnam. It is the second largest shopping centre in the southern suburbs of Ho Chi Minh City, by gross area, and contains the only H&M store in that region.",
    "Crescent Mall Shopping Centre is located 10km South of the Ho Chi Minh City central business district(CBD) and includes Banana Republic, Baskin Robins, CGV Cinema, Bobapop and over 130 specialty stores.",
    "The Dam Sen Parklands area was created as part of the rejuvenation of the industrial upgrade undertaken for World Expo 1988. The Parklands area is spacious with plenty of green and spaces for all ages. A big lake promenade stretches the area of Dam Sen Parklands.",
    "The Ho Chi Minh City central business district (CBD), or 'the City' is located on a central point in district One. The point, known at its tip as Central Point, slopes upward to the north-west where 'the city' is bounded by parkland and the inner city suburb of District 3, District 4 and District 5."};
    for (int i = 0; i < names.length; i++) {
        assertEquals(names[i], cityList.get(i).getName());
        assertEquals(locations[i], cityList.get(i).getLocation());
        assertEquals(cityInformations[i], cityList.get(i).getInfo());
    }
}
//getLocationsByService(String service)
@ParameterizedTest
@ValueSource(strings = {"shops", "cinema", "bowling", "market", "SHOPS", "CINEMA", "", "asdfg"})
@DisplayName("GetLocationsByService Test")
public void GetLocationsByServiceTest() {
ContextManager.cityInfo = ContextManager.readCityInfo();
}




//resetClock(String username)
@ParameterizedTest
@ValueSource(strings = {"Jack,50"})
@DisplayName("resetClock Test cases")
void resetClockTest(String data) {
String name = data.split(",")[0];
System.out.println(name);
int currentClock = Integer.parseInt(data.split(",")[1]);
ContextManager.users.get(name).clock = currentClock;
int resetCurrentClock = ContextManager.users.get(name).clock;
int newClock = ContextManager.users.get(name).clock;
ContextManager.resetClock(name);
assertEquals(0, resetCurrentClock);
	}

//tickClock(String username)


//checkAPOReached(User user)
@ParameterizedTest
@ValueSource(strings = {"1,30,30", "1,30,2"})
@DisplayName("checkAPOReached Test")
void checkAPOReachedTest(String data) {
	User user = new User();
	int medicalCondition = Integer.parseInt(data.split(",")[0]);
	int AQI = Integer.parseInt(data.split(",")[1]);
	int clock = Integer.parseInt(data.split(",")[2]);
	int baseTime;
	if(AQI > 0 && AQI <= 50) {
		baseTime = 30;
	} else if (AQI <= 100) {
		baseTime = 15;
	} else if (AQI <= 100) {
		baseTime = 10;
	} else {
		baseTime = 5;
	}
	int apoThreshold =  medicalCondition * baseTime;
	user.apoThreshhold = apoThreshold;
	user.clock = clock;
	System.out.println(apoThreshold);
	System.out.println(ContextManager.checkapoReached(user));
	assertEquals(apoThreshold, ContextManager.checkapoReached(user));
}
//checkTempReached(User user)
@ParameterizedTest
@ValueSource(strings = {"10,20", "40,25", "20,20", "-10,20"})
@DisplayName("checkTempReached Test")
void checkTempReachedTest(String data) {
	User user = new User();
	int currentTemp = Integer.parseInt(data.split(",")[0]);
	user.sensorData.temperature = currentTemp;
	user.tempThreshholds = new int[data.split(",").length - 1];
	System.out.println(data.split(",")[0]);
	boolean expected = false;
	for (int i = 0; i < user.tempThreshholds.length; i++) {
		user.tempThreshholds[i] = Integer.parseInt(data.split(",")[i + 1]);
		if (currentTemp >= user.tempThreshholds[i]) {
			expected = true;
		}
	}
	System.out.println(expected);
	assertEquals(expected, ContextManager.checkTempReached(user));
	}


//calculateAPOThreshold(User user)
@ParameterizedTest
@ValueSource(strings = {"1,0", "1,25", "1,50", "1,51", "1,75", "1,100", "1,101", "1,125", "1,150", "1,151", "1,175", "1,200",
		                "2,0", "2,25", "2,50", "2,51", "2,75", "2,100", "2,101", "2,125", "2,150", "2,151", "2,175", "2,200",
		                "3,0", "3,25", "3,50", "3,51", "3,75", "3,100", "3,101", "3,125", "3,150", "3,151", "3,175", "3,200",
		                "1,-1", "1,201", "2,-1", "2,201", "3,-1", "3,201"
})
@DisplayName("calculateAPOThreshold Test")
void calculateAPOThreshold(String data) {
	User user = new User();
	user.medicalConditionType = Integer.parseInt(data.split(",")[0]);
	user.sensorData.aqi = Integer.parseInt(data.split(",")[1]);
	int medicalCondition = user.medicalConditionType;
	int AQI = user.sensorData.aqi;
	int baseTime;
	int expected = 0;
	if(AQI >= 1 && AQI <= 50) {
		baseTime = 30;
		expected = medicalCondition * baseTime;
	} else if (AQI >= 51 && AQI <= 100) {
		baseTime = 15;
		expected = medicalCondition * baseTime;
	} else if (AQI >= 101 && AQI <= 150) {
		baseTime = 10;
		expected = medicalCondition * baseTime;
	} else {
		baseTime = 5;
		expected = medicalCondition * baseTime;
	}
	if (AQI <= 0 || AQI > 200 || medicalCondition == 0) {
		expected = 0;
	}
	
	System.out.println(ContextManager.calculateapoThreshhold(user));
//	System.out.println(ContextManager.checkapoReached(user));
	assertEquals(expected, ContextManager.calculateapoThreshhold(user));
}
//addUser(String username, Current current)
@ParameterizedTest
@ValueSource(strings = {" Jack,2", "David,3",})
@DisplayName("addUser Test")
void addUserTest(String data) {
	String user  = data.split(",")[0];
	String medicalType = data.split(",")[1];
	int getMedicalType = ContextManager.users.get(user).medicalConditionType;
	WorkerI.addUser(user, new Current());
	assertEquals(medicalType, getMedicalType);
} 


//deleteUser(String username, Current current)
@ParameterizedTest
@ValueSource(strings = {" Jack", "David"})
@DisplayName("deleteUser Test")
void deleteUserTest(String data) {
	String user  = data.split(",")[0];
	boolean addUser = WorkerI.addUser(user, new Current());
    int initialsSize;
    boolean deleteUser = WorkerI.deleteUser(user, new Current());
    if(user.equals("Jack") || user.equals("David")) {
    	 WorkerI.addUser(user, new Current());
    	 initialsSize = ContextManager.users.size();
    	 WorkerI.deleteUser(user, new Current());
    	 assertEquals(initialsSize - 1, ContextManager.users.size());
    }
    else {
    	ContextManager.users.put(user, new User());
    	initialsSize = ContextManager.users.size();
    	assertEquals(initialsSize, ContextManager.users.size());
    }
}


//searchInfo(String item, Current current) 
@ParameterizedTest
@ValueSource(strings = {"Jack,A", "Jack,B", "Jack,C", "Jack,D"})
@DisplayName("searchItems Test")

void searchInfoTest(String data) {
	String info  = data.split(",")[0];
	String expected = data.split(",")[1];
	String location = data.split(",")[1];
String actual = WorkerI.addUser(info, new Current());
ContextManager.cityInfo = ContextManager.readCityInfo();
if(actual == null) {
	actual = "null";
	}
assertEquals(expected, actual);
	}




//searchItems(String username, Current current)
@ParameterizedTest
@ValueSource(strings = {"Jack,A", "Jack,B", "Jack,C", "Jack,D"})
@DisplayName("searchItems Test")

void searchItemsTest(String data) {
	String user  = data.split(",")[0];
	String location = data.split(",")[1];
	WorkerI.addUser(user, new Current());
	ContextManager.users.get(user).sensorData.location = location;
	String locations = Array.toString(WorkerI.searchItems(user, new Current()));
    String expected = "";
    switch (data.split((","))[1]){
    case "A":
    	expected = "";
    	break;
    case "B":
    	expected = "";
    	break;
    case "C":
    	expected = "";
    	break;
    case "D":
    	expected = "";
    	break;
    	}
assertEquals(expected, locations);
	}
}




















