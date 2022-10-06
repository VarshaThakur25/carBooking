package com.varshathakur.cabbooking.strategies;

import com.varshathakur.cabbooking.model.Cab;
import com.varshathakur.cabbooking.model.Location;
import com.varshathakur.cabbooking.model.Rider;
import java.util.List;

public interface CabMatchingStrategy {

  Cab matchCabToRider(Rider rider, List<Cab> candidateCabs, Location fromPoint, Location toPoint);
}
