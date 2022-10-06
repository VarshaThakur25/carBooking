package com.varshathakur.cabbooking.controllers;

import com.varshathakur.cabbooking.database.RidersManager;
import com.varshathakur.cabbooking.database.TripsManager;
import java.util.List;

import com.varshathakur.cabbooking.model.Cab;
import com.varshathakur.cabbooking.model.Location;
import com.varshathakur.cabbooking.model.Rider;
import com.varshathakur.cabbooking.model.Trip;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RidersController {
  private RidersManager ridersManager;
  private TripsManager tripsManager;

  public RidersController(RidersManager ridersManager, TripsManager tripsManager) {
    this.ridersManager = ridersManager;
    this.tripsManager = tripsManager;
  }

  @RequestMapping(value = "/register/rider", method = RequestMethod.POST)
  public ResponseEntity registerRider(final String riderId, final String riderName, final Integer age, final String sex) {
    ridersManager.createRider(new Rider(riderId, riderName, age, sex));
    return ResponseEntity.ok("");
  }

  @RequestMapping(value = "/findRide", method = RequestMethod.POST)
  public List<Cab> findRide(
          final String riderId,
          final Double sourceX,
          final Double sourceY,
          final Double destX,
          final Double destY) {

    List<Cab> closeByAvailableCabs = tripsManager.findTrip(
            ridersManager.getRider(riderId),
            new Location(sourceX, sourceY),
            new Location(destX, destY));
    return closeByAvailableCabs;
  }

  @RequestMapping(value = "/bookRide", method = RequestMethod.POST)
  public ResponseEntity bookRide(
          final String riderId,
          final String cabID,
          final Double destX,
          final Double destY) {

    tripsManager.createTrip(
            ridersManager.getRider(riderId),tripsManager.getCabsManager().getCab(cabID), new Location(destX, destY));

    return ResponseEntity.ok("");
  }


  @RequestMapping(value = "/fetchHistory", method = RequestMethod.GET)
  public ResponseEntity fetchHistory(final String riderId) {
    List<Trip> trips = tripsManager.tripHistory(ridersManager.getRider(riderId));
    return ResponseEntity.ok(trips);
  }
}
