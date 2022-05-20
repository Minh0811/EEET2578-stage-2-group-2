package test.Unit;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;

import org.junit.Test;

import main.LocationServer;

public class LocationServerTest {

	//Test case 1
	@Test
	public void readConfigTestwithA() {
		LocationServer objectTest = new LocationServer();
		LinkedHashMap<String, String> table = new LinkedHashMap<>();
		table = LocationServer.readConfig();
		assertEquals("Indoor", table.get("A"));
	}
	
	//Test case 2
		@Test
		public void readConfigTestwithB() {
			LocationServer objectTest = new LocationServer();
			LinkedHashMap<String, String> table = new LinkedHashMap<>();
			table = objectTest.readConfig();
			assertEquals("Indoor", table.get("B"));
		}
	
	//Test case 3
		@Test
		public void readConfigTestwithC() {
			LocationServer objectTest = new LocationServer();
			LinkedHashMap<String, String> table = new LinkedHashMap<>();
			table = objectTest.readConfig();
			assertEquals("Outdoor", table.get("C"));
		}		
	
	//Test case 4	
		
		@Test
		public void readConfigTestwithD() {
			LocationServer objectTest = new LocationServer();
			LinkedHashMap<String, String> table = new LinkedHashMap<>();
			table = objectTest.readConfig();
			assertEquals("Outdoor", table.get("D"));
		}	
		
	//Test case 5
		@Test
		public void readConfigTestCheckRespondingTime() {
			LocationServer objectTest = new LocationServer();
			long startingPoint = System.currentTimeMillis();
			objectTest.readConfig();
			long endingPoint = System.currentTimeMillis();
			assertTrue((endingPoint - startingPoint) < 0.001);
		}	
		
	//Test case 6
	@Test
	public void readConfigTestwithlowercasea() {
		LocationServer objectTest = new LocationServer();
		LinkedHashMap<String, String> table = new LinkedHashMap<>();
		table = objectTest.readConfig();
		assertEquals("Indoor", table.get("a"));
	}
	
	//Test case 7
		@Test
		public void readConfigTestwithlowercaseb() {
			LocationServer objectTest = new LocationServer();
			LinkedHashMap<String, String> table = new LinkedHashMap<>();
			table = objectTest.readConfig();
			assertEquals("Indoor", table.get("b"));
		}
	
	//Test case 8
		@Test
		public void readConfigTestwithlowercasec() {
			LocationServer objectTest = new LocationServer();
			LinkedHashMap<String, String> table = new LinkedHashMap<>();
			table = objectTest.readConfig();
			assertEquals("Outdoor", table.get("c"));
		}		
	
	//Test case 9
		
		@Test
		public void readConfigTestwithlowercased() {
			LocationServer objectTest = new LocationServer();
			LinkedHashMap<String, String> table = new LinkedHashMap<>();
			table = objectTest.readConfig();
			assertEquals("Outdoor", table.get("d"));
		}	
		
	//Test case 10
	
			@Test
			public void readConfigTestwithNull() {
				LocationServer objectTest = new LocationServer();
				LinkedHashMap<String, String> table = new LinkedHashMap<>();
				table = objectTest.readConfig();
				assertEquals("Outdoor", table.get("Null"));
			}
			
	//Test case 11
	
	@Test
	public void readConfigTestwithSpaces() {
		LocationServer objectTest = new LocationServer();
		LinkedHashMap<String, String> table = new LinkedHashMap<>();
		table = objectTest.readConfig();
		assertEquals("Outdoor", table.get("C "));
	}	
		

	//Test case 12
	
		@Test
		public void readConfigTestwithSQL() {
			LocationServer objectTest = new LocationServer();
			LinkedHashMap<String, String> table = new LinkedHashMap<>();
			table = objectTest.readConfig();
			assertEquals("Outdoor", table.get("*"));
		}
		

	//Test case 13
	
		@Test
		public void locationMappingTestwithValidEntry() { //either A or B or C or D
			LocationServer objectTest = new LocationServer();
			LinkedHashMap<String, String> table = new LinkedHashMap<>();
			table = objectTest.readConfig();
			assertEquals("Outdoor", table.get("C"));
		}
	
	//Test case 14
	
		@Test
		public void locationMappingTestwithInvalidEntry() { // E
			LocationServer objectTest = new LocationServer();
			LinkedHashMap<String, String> table = new LinkedHashMap<>();
			table = objectTest.readConfig();
			assertEquals("Outdoor", table.get("E"));
		}
				
}
