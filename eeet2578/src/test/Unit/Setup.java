package test.Unit;

import com.zeroc.IceStorm.AlreadySubscribed;
import com.zeroc.IceStorm.BadQoS;
import com.zeroc.IceStorm.InvalidSubscriber;
import helper.ContextManagerWorker;
import main.AllSensors;
import main.ContextManager;
import helper.*;

public class Setup {
    public static AllSensors allSensors;
    static Boolean isAlreadySetup = false;
    static ContextManagerWorker WorkerI;

    static void setupService() {

        if (Setup.isAlreadySetup) { 
            return;
        }

        ContextManager.communicator = com.zeroc.Ice.Util.initialize();
        ContextManager.cityInfo = ContextManager.readCityInfo();
        ContextManager.iniPreferenceWorker();
        ContextManager.iniLocationMapper();
        ContextManager.iniWeatherAlarmWorker();
        ContextManager.runWeatherAlarm();
        ContextManager.setupContextManagerWorker();
        WorkerI = new ContextManager.ContextManagerWorkerI();

        allSensors = new AllSensors("Jack");
        allSensors.run();

        WorkerI.addUser("Jack", null);
        WorkerI.addUser("David", null);

        Setup.isAlreadySetup = true;
    }
}