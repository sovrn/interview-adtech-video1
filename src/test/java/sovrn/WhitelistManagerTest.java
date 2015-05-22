package sovrn;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WhitelistManagerTest extends TestCase {

    private WhitelistManager manager;

    public void setUp() throws Exception {
        super.setUp();
        manager = new WhitelistManager();

        //sample data set with 15,000 domains
        BufferedReader br = new BufferedReader(new FileReader("sample_data.csv"));
        String line;
        Map<String, Boolean> whitelistTest = new HashMap<String, Boolean>();
        while((line = br.readLine()) != null){
            String str[] = line.split(",");
            // Check and do not put in bad data (either field empty)
            if (str.length == 2 && str[0] != null && !str[0].isEmpty() && str[1] != null && !str[1].isEmpty()) {
                whitelistTest.put(str[0].toLowerCase(), Boolean.valueOf(str[1]));
            }
        }
        manager.setWhitelist(whitelistTest);
    }

    @Test //test for empty
    public void testIsOnWhitelist_Empty() {
        String domain = "";
        assertFalse(manager.isOnWhitelist(domain));
    }

    @Test //test for null
    public void testIsOnWhitelist_Null() {
        String domain = null;
        assertFalse(manager.isOnWhitelist(domain));
    }

    @Test //test that ZOO.COM returns true for ZOO.COM, TRUE
    public void testIsOnWhitelist_ZOOdotCOM_TRUE() {
        String domain = "ZOO.COM";
        assertTrue(manager.isOnWhitelist(domain));
    }

    @Test //test that zoo.com returns true for ZOO.COM, TRUE
    public void testIsOnWhitelist_ZOOdotCOM_TRUE_lowerCase() {
        String domain = "zoo.com";
        assertTrue(manager.isOnWhitelist(domain));
    }

    @Test //test that ABC.ZOO.COM returns true for ZOO.COM, TRUE
    public void testIsOnWhitelist_ABCdotZOOdotCOM_TRUE() {
        String domain = "ABC.ZOO.COM";
        assertTrue(manager.isOnWhitelist(domain));
    }

    @Test //test that ABC.DEF.ZOO.COM returns true for ZOO.COM, TRUE
    public void testIsOnWhitelist_ABCdotDEFdotZOOdotCOM_TRUE() {
        String domain = "ABC.DEF.ZOO.COM";
        assertTrue(manager.isOnWhitelist(domain));
    }

    @Test //test that ZOO.COM.ABC returns false for ZOO.COM, TRUE
    public void testIsOnWhitelist_ZOOdotCOMdotABC_TRUE() {
        String domain = "ZOO.COM.ABC";
        assertFalse(manager.isOnWhitelist(domain));
    }

    @Test //test that zoo.com.abc returns false for ZOO.COM, TRUE
    public void testIsOnWhitelist_ZOOdotCOMdotABC_TRUE_lowerCase() {
        String domain = "zoo.com.abc";
        assertFalse(manager.isOnWhitelist(domain));
    }

    @Test //test that ACB.COM returns true for ACB.COM, FALSE
    public void testIsOnWhitelist_ACBdotCOM_FALSE() {
        String domain = "ACB.COM";
        assertTrue(manager.isOnWhitelist(domain));
    }

    @Test //test that acb.com returns true for ACB.COM, FALSE
    public void testIsOnWhitelist_ACBdotCOM_FALSE_lowerCase() {
        String domain = "acb.com";
        assertTrue(manager.isOnWhitelist(domain));
    }

    @Test //test that XYZ.ACB.COM returns false for ACB.COM, FALSE
    public void testIsOnWhitelist_XYZdotACBdotCOM_FALSE() {
        String domain = "XYZ.ACB.COM";
        assertFalse(manager.isOnWhitelist(domain));
    }

    @Test //test that XACB.COM returns false for ACB.COM, FALSE
    public void testIsOnWhitelist_XACBdotCOM_FALSE() {
        String domain = "XACB.COM";
        assertFalse(manager.isOnWhitelist(domain));
    }

    @Test //test that ACB.COM.NET returns false for ACB.COM, FALSE
    public void testIsOnWhitelist_ACBdotCOMdotNET_FALSE() {
        String domain = "ACB.COM.NET";
        assertFalse(manager.isOnWhitelist(domain));
    }

    @Test //test that acb.com.net returns false for ACB.COM, FALSE
    public void testIsOnWhitelist_ACBdotCOMdotNET_FALSE_lowerCase() {
        String domain = "acb.com.net";
        assertFalse(manager.isOnWhitelist(domain));
    }

    @Test
    //I used this method to test different ways to iterate over the HashMap to find the fastest combination
    //I was surprised that the one I use most commonly (#3) was not the fastest way to iterate

    //    With 100,000 rows
    //    Using entrySet() in for-each loop : 22
    //    Using keySet() in for-each loop : 29
    //    Using entrySet() and iterator : 14
    //    Using keySet() and iterator : 21

    //    With 15,000 rows (interesting it does not scale directly)
    //    Using entrySet() in for-each loop : 13
    //    Using keySet() in for-each loop : 10
    //    Using entrySet() and iterator : 4
    //    Using keySet() and iterator : 6
    //    I ran it 10 times and #3 was fastest 7 times, #4 was fastest 1 times, and 2 ties so went with #7

    public void testLookUpMethodSpeed() {

        Map<String, Boolean> testMap = manager.getWhitelist();


        // #1 using entrySet in for-each loop
        long startTime = Calendar.getInstance().getTimeInMillis();
        for (Map.Entry<String, Boolean> entry : testMap.entrySet()) {
            entry.getKey();
            entry.getValue();
        }
        System.out.println("Using entrySet() in for-each loop : " + (Calendar.getInstance().getTimeInMillis() - startTime));


        // #2 using keySet() in for-each loop
        startTime = Calendar.getInstance().getTimeInMillis();
        for (String key : testMap.keySet()) {
            testMap.get(key);
        }
        System.out.println("Using keySet() in for-each loop : " + (Calendar.getInstance().getTimeInMillis() - startTime));


        // #3 using Iterator on entrySet() in while loop
        startTime = Calendar.getInstance().getTimeInMillis();
        Iterator<Map.Entry<String, Boolean>> itr1 = testMap.entrySet().iterator();
        while(itr1.hasNext())
        {
            Map.Entry<String, Boolean> entry = itr1.next();
            entry.getKey();
            entry.getValue();
        }
        System.out.println("Using entrySet() and iterator : " + (Calendar.getInstance().getTimeInMillis() - startTime));


        // #4 using Iterator on keySet() in while loop
        startTime = Calendar.getInstance().getTimeInMillis();
        Iterator<String> itr2 = testMap.keySet().iterator();
        while(itr2.hasNext())
        {
            String key = itr2.next();
            testMap.get(key);
        }
        System.out.println("Using keySet() and iterator : " + (Calendar.getInstance().getTimeInMillis() - startTime));
    }
}