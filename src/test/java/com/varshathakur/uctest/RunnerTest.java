package com.varshathakur.uctest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.varshathakur.cabbooking.controllers.CabsController;
import com.varshathakur.cabbooking.controllers.RidersController;
import com.varshathakur.cabbooking.database.CabsManager;
import com.varshathakur.cabbooking.database.RidersManager;
import com.varshathakur.cabbooking.database.TripsManager;
import com.varshathakur.cabbooking.exceptions.NoCabsAvailableException;
import com.varshathakur.cabbooking.model.Cab;
import com.varshathakur.cabbooking.strategies.CabMatchingStrategy;
import com.varshathakur.cabbooking.strategies.DefaultPricingStrategy;
import com.varshathakur.cabbooking.strategies.PricingStrategy;
import com.varshathakur.cabbooking.strategies.DefaultCabMatchingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RunnerTest {
  CabsController cabsController;
  RidersController ridersController;

  @BeforeEach
  void setUp() {
    CabsManager cabsManager = new CabsManager();
    RidersManager ridersManager = new RidersManager();

    CabMatchingStrategy cabMatchingStrategy = new DefaultCabMatchingStrategy();
    PricingStrategy pricingStrategy = new DefaultPricingStrategy();

    TripsManager tripsManager = new TripsManager(cabsManager, ridersManager, cabMatchingStrategy, pricingStrategy);

    cabsController = new CabsController(cabsManager, tripsManager);
    ridersController = new RidersController(ridersManager, tripsManager);
  }

  @Test
  void testInput() {

    String r1 = "r1";
    ridersController.registerRider(r1, "Abhishek", 23, "M");
    String r2 = "r2";
    ridersController.registerRider(r2, "Rahul", 29, "M");
    String r3 = "r3";
    ridersController.registerRider(r3, "Nandini", 22, "F");

    String c1 = "c1";
    cabsController.regiserCab(c1, "driver1", "driver1", "M", 22, "Swift, KA-01-12345", 10.0, 1.0);
    String c2 = "c2";
    cabsController.regiserCab(c2, "driver2", "driver2", "M", 29, "Swift, KA-01-1", 11.0, 10.0);
    String c3 = "c3";
    cabsController.regiserCab(c3, "driver3", "driver3", "M", 24, "Swift, KA-01-12", 5.0, 3.0);

    assertThrows(NoCabsAvailableException.class, () -> {
      ridersController.findRide(r1, 0.0, 0.0, 20.0, 1.0);
    });


    List<Cab> closeByAvailableCabs = ridersController.findRide(r2, 10.0, 0.0, 15.0, 3.0);
    assertEquals(closeByAvailableCabs.size(),1);
    assertEquals(closeByAvailableCabs.get(0).getDriver().getDriverName(),"driver1");

    ridersController.bookRide(r2, c1,15.0, 3.0);


    assertThrows(NoCabsAvailableException.class, () -> {
      ridersController.findRide(r3, 15.0, 6.0, 20.0, 4.0);
    });

    List<Cab> closeByAvailableCabs2 = ridersController.findRide(r3, 11.0, 10.0, 15.0, 3.0);
    assertEquals(closeByAvailableCabs2.size(),1);
    assertEquals(closeByAvailableCabs2.get(0).getDriver().getDriverName(),"driver2");

    assertThrows(NoCabsAvailableException.class, () -> {
      ridersController.findRide(r1, 0.0, 0.0, 20.0, 1.0);
    });

    List<Cab> closeByAvailableCabs3 = ridersController.findRide(r1, 5.0, 4.0, 15.0, 3.0);
    assertEquals(closeByAvailableCabs3.size(),1);
    assertEquals(closeByAvailableCabs3.get(0).getDriver().getDriverName(),"driver3");
  }


}

