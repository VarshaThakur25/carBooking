package com.varshathakur.cabbooking.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Cab {
  String id;
  Driver driver;

  @Setter Trip currentTrip;
  @Setter Location currentLocation;
  @Setter Boolean isAvailable;

  public Cab(String id, Driver driver, Location currentLocation) {
    this.id = id;
    this.driver = driver;
    this.currentLocation = currentLocation;
    this.isAvailable = true;
  }

  @Override
  public String toString() {
    return "Cab{" +
        "id='" + id + '\'' +
        ", driverName='" + driver.driverName + '\'' +
        ", currentLocation=" + currentLocation +
        ", isAvailable=" + isAvailable +
        '}';
  }
}
