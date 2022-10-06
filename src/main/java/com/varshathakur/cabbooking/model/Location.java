package com.varshathakur.cabbooking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import static java.lang.Math.*;

@ToString
@Getter
@AllArgsConstructor
public class Location {
  private Double x;
  private Double y;

  public Double distance(Location location2) {
    return abs(this.x-location2.x) + abs(this.y-location2.y);
  }
}
