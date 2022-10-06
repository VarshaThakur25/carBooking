package com.varshathakur.cabbooking.database;

import com.varshathakur.cabbooking.exceptions.NoCabsAvailableException;
import com.varshathakur.cabbooking.exceptions.TripNotFoundException;
import com.varshathakur.cabbooking.model.Cab;
import com.varshathakur.cabbooking.model.Location;
import com.varshathakur.cabbooking.model.Rider;
import com.varshathakur.cabbooking.model.Trip;
import com.varshathakur.cabbooking.strategies.CabMatchingStrategy;
import com.varshathakur.cabbooking.strategies.PricingStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NonNull;

public class TripsManager {

  public static final Double MAX_ALLOWED_TRIP_MATCHING_DISTANCE = 5.0;
  private Map<String, List<Trip>> trips = new HashMap<>();

  @Getter
  private CabsManager cabsManager;
  private RidersManager ridersManager;
  private CabMatchingStrategy cabMatchingStrategy;
  private PricingStrategy pricingStrategy;

  public TripsManager(
      CabsManager cabsManager,
      RidersManager ridersManager,
      CabMatchingStrategy cabMatchingStrategy,
      PricingStrategy pricingStrategy) {
    this.cabsManager = cabsManager;
    this.ridersManager = ridersManager;
    this.cabMatchingStrategy = cabMatchingStrategy;
    this.pricingStrategy = pricingStrategy;
  }

  public void createTrip(
          @NonNull final Rider rider,
          @NonNull final Cab cab,
          @NonNull final Location toPoint) {

    final Double price = pricingStrategy.findPrice(cab.getCurrentLocation(), toPoint);

    final Trip newTrip = new Trip(rider, cab, price, cab.getCurrentLocation(), toPoint);

    if (!trips.containsKey(rider.getId())) {
      trips.put(rider.getId(), new ArrayList<>());
    }
    trips.get(rider.getId()).add(newTrip);
    cab.setCurrentTrip(newTrip);
  }

  public List<Cab> findTrip(
          @NonNull final Rider rider,
          @NonNull final Location fromPoint,
          @NonNull final Location toPoint) {
    final List<Cab> closeByCabs =
            cabsManager.getCabs(fromPoint, MAX_ALLOWED_TRIP_MATCHING_DISTANCE);
    final List<Cab> closeByAvailableCabs =
            closeByCabs.stream()
                    .filter(cab -> cab.getCurrentTrip() == null)
                    .collect(Collectors.toList());

    if (closeByAvailableCabs == null || closeByAvailableCabs.isEmpty()) {
      throw new NoCabsAvailableException();
    }
    return closeByAvailableCabs;
  }

  public List<Trip> tripHistory(@NonNull final Rider rider) {
    return trips.get(rider.getId());
  }

  public void endTrip(@NonNull final Cab cab) {
    if (cab.getCurrentTrip() == null) {
      throw new TripNotFoundException();
    }

    cab.getCurrentTrip().endTrip();
    cab.setCurrentTrip(null);
  }
}
