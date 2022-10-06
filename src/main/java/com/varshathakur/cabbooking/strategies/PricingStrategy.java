package com.varshathakur.cabbooking.strategies;

import com.varshathakur.cabbooking.model.Location;

public interface PricingStrategy {
  Double findPrice(Location fromPoint, Location toPoint);
}
