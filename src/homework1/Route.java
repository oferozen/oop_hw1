package homework1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A Route is a path that traverses arbitrary GeoSegments, regardless
 * of their names.
 * <p>
 * Routes are immutable. New Routes can be constructed by adding a segment 
 * to the end of a Route. An added segment must be properly oriented; that 
 * is, its p1 field must correspond to the end of the original Route, and
 * its p2 field corresponds to the end of the new Route.
 * <p>
 * Because a Route is not necessarily straight, its length - the distance
 * traveled by following the path from start to end - is not necessarily
 * the same as the distance along a straight line between its endpoints.
 * <p>
 * Lastly, a Route may be viewed as a sequence of geographical features,
 * using the <tt>getGeoFeatures()</tt> method which returns an Iterator of
 * GeoFeature objects.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   start : GeoPoint            // location of the start of the route
 *   end : GeoPoint              // location of the end of the route
 *   startHeading : angle        // direction of travel at the start of the route, in degrees
 *   endHeading : angle          // direction of travel at the end of the route, in degrees
 *   geoFeatures : sequence      // a sequence of geographic features that make up this Route
 *   geoSegments : sequence      // a sequence of segments that make up this Route
 *   length : real               // total length of the route, in kilometers
 *   endingGeoSegment : GeoSegment  // last GeoSegment of the route
 * </pre>
 **/
public class Route {

	private final List<GeoSegment> segments = new ArrayList<GeoSegment>();
	private final double length;

	/*
     * Abstraction function:
     *     map a list of segments into a line composed from list of geo-features, where each geo-feature is composed from the
     *     longest consecutive segments with the same name.
     *      
     * Representation invariant:
     *     segments.size() != 0 &&
     *     foreach i in 0..(segments.size()-2) => segments[i].p2 == segments[i+1].p1 
     *  
     */
    /**
     * Checks if this's status is in line with the representation invariant. 
     * If this is not the case then the running of the program will stop since the checking operation is done 
     * using the "assert" function.
     * @effect if assert is enabled, program stops running in case the current status of this
     * 		   does not fulfill the representation invariant
     **/
  	private void checkRep() {
  		
  		assert(segments.size() != 0);
  		
  		var iter = segments.iterator();
  		GeoSegment current = iter.next();
  		while (iter.hasNext()) {
  			var next = iter.next();
  			assert(current.getP2().equals(next.getP1()));
  			current = next;
  		}
  		
  	}

  	/**
  	 * Constructs a new Route.
     * @requires gs != null
     * @effects Constructs a new Route, r, such that
     *	        r.startHeading = gs.heading &&
     *          r.endHeading = gs.heading &&
     *          r.start = gs.p1 &&
     *          r.end = gs.p2
     **/
  	public Route(GeoSegment gs) {
  		this.segments.add(gs);
  		this.length = gs.getLength();
  		checkRep();
  	}

  	/**
  	 * Constructs a new Route.
     * @effects Constructs a new Route.
     *          Methods are invalid until class exit point;
     **/
  	private Route(List<GeoSegment> geoSegments) {
  		this.segments.addAll(geoSegments);
		
  		double length = 0;
		
		var iter = geoSegments.iterator();
		while (iter.hasNext()) {
			length += iter.next().getLength();
		}
		this.length = length;
		checkRep();
  	}
  	
	/**
     * Returns the first GeoSegment in the list
     * @return first GeoSegment in the list
     */
	private GeoSegment firstGeoSegment() {
		return this.segments.get(0);
	}

	/**
     * Returns the last GeoSegment in the list
     * @return last GeoSegment in the list
     */
	private GeoSegment lastGeoSegment() {
		return this.segments.get(this.segments.size() - 1);
	}

    /**
     * Returns location of the start of the route.
     * @return location of the start of the route.
     **/
  	public GeoPoint getStart() {
  		checkRep();
  		return firstGeoSegment().getP1();
  	}


  	/**
  	 * Returns location of the end of the route.
     * @return location of the end of the route.
     **/
  	public GeoPoint getEnd() {
  		checkRep();
  		return lastGeoSegment().getP2();
  	}


  	/**
  	 * Returns direction of travel at the start of the route, in degrees.
  	 * the start of the route is the first segment of the route with a positive length
   	 * @return direction (in compass heading) of travel at the start of the
   	 *         route, in degrees if this.length > 0.
   	 *         0 returned if this.length = 0
   	 **/
  	public double getStartHeading() {
  		checkRep();
  		// if the length of this route is zero then heading is 0 degrees according to the convention we chose
  		if(this.length == 0) {
  			return 0;
  		}
  		// route's length is not zero; find the first segment with a length longer than zero
  		Iterator<GeoSegment> iter = this.segments.iterator();
  		double heading = 0;
  		while(iter.hasNext()) {
  			heading = iter.next().getHeading();
  			if (heading > 0) {
  				break;
  			}
  		}
  		checkRep();
  		return heading;
  	}


  	/**
  	 * Returns direction of travel at the end of the route, in degrees.
  	 * the start of the route is the last segment of the route with a positive length
     * @return direction (in compass heading) of travel at the end of the
     *         route, in degrees if this.length > 0.
   	 *         0 returned if this.length = 0
     **/
  	public double getEndHeading() {
  		checkRep();
  		// if the length of this route is zero then heading is 0 degrees according to the convention we chose
  		if(this.length == 0) {
  			return 0;
  		}
  		// route's length is not zero; find the last segment with a length longer than zero
  		Iterator<GeoSegment> iter = this.segments.iterator();
  		GeoSegment currentGeoSegment = null;
  		double heading = 0;
  		while(iter.hasNext()) {
  			currentGeoSegment = iter.next();
  			if (currentGeoSegment.getLength() > 0) {
  				heading = currentGeoSegment.getHeading();
  			}
  		}
  		checkRep();
  		return heading;
  	}


  	/**
  	 * Returns total length of the route.
     * @return total length of the route, in kilometers.  NOTE: this is NOT
     *         as-the-crow-flies, but rather the total distance required to
     *         traverse the route. These values are not necessarily equal.
   	 **/
  	public double getLength() {
  		checkRep();
  		return this.length;
  	}


  	/**
     * Creates a new route that is equal to this route with gs appended to
     * its end.
   	 * @requires gs != null && gs.p1 == this.end
     * @return a new Route r such that
     *         r.end = gs.p2 &&
     *         r.endHeading = gs.heading &&
     *         r.length = this.length + gs.length
     **/
  	public Route addSegment(GeoSegment gs) {
  		checkRep();
  		var geoSegments = new ArrayList<>(this.segments);
  		geoSegments.add(gs);
  		checkRep();
  		return new Route(geoSegments);
  	}


    /**
     * Returns an Iterator of GeoFeature objects. The concatenation
     * of the GeoFeatures, in order, is equivalent to this route. No two
     * consecutive GeoFeature objects have the same name.
     * @return an Iterator of GeoFeatures such that
     * <pre>
     *      this.start        = a[0].start &&
     *      this.startHeading = a[0].startHeading &&
     *      this.end          = a[a.length - 1].end &&
     *      this.endHeading   = a[a.length - 1].endHeading &&
     *      this.length       = sum(0 <= i < a.length) . a[i].length &&
     *      for all integers i
     *          (0 <= i < a.length - 1 => (a[i].name != a[i+1].name &&
     *                                     a[i].end  == a[i+1].start))
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoFeature
     **/
  	public Iterator<GeoFeature> getGeoFeatures() {
  		checkRep();
  		List<GeoFeature> geoFeatures = new ArrayList<GeoFeature>();
  		Iterator<GeoSegment> iter = this.segments.iterator();
  		
  		var currentGeoFeature = new GeoFeature(iter.next());
  		
  		while(iter.hasNext()) {
  			var currentGeoSegment = iter.next();
  			
  			// If current segment has same name as current feature, add it to the feature
  			if (currentGeoFeature.getName() == currentGeoSegment.getName()) {
  				currentGeoFeature = currentGeoFeature.addSegment(currentGeoSegment);
  			
  			// If current segment doesn't have the same name as the current feature,
  			// the feature has ended. Add it to the features list and construct an new feature
  			// with the current segment as it's starting segment
  			} else {
  				geoFeatures.add(currentGeoFeature);
  				currentGeoFeature = new GeoFeature(currentGeoSegment);
  			}
  		}
  		geoFeatures.add(currentGeoFeature);
  		checkRep();
  		return geoFeatures.iterator();
  	}


  	/**
     * Returns an Iterator of GeoSegment objects. The concatenation of the
     * GeoSegments, in order, is equivalent to this route.
     * @return an Iterator of GeoSegments such that
     * <pre>
     *      this.start        = a[0].p1 &&
     *      this.startHeading = a[0].heading &&
     *      this.end          = a[a.length - 1].p2 &&
     *      this.endHeading   = a[a.length - 1].heading &&
     *      this.length       = sum (0 <= i < a.length) . a[i].length
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoSegment
     **/
  	public Iterator<GeoSegment> getGeoSegments() {
  		checkRep();
  		return this.segments.iterator();
  	}


  	/**
     * Compares the specified Object with this Route for equality.
     * @return true iff (o instanceof Route) &&
     *         (o.geoFeatures and this.geoFeatures contain
     *          the same elements in the same order).
     **/
  	public boolean equals(Object o) {
  		
  		checkRep();
	    // self check
	    if (this == o)
	        return true;
	    
	    if (o == null) {
	    	return false;
	    }
	    
	    // type check and cast
	    if (getClass() != o.getClass()) {
	    	checkRep();
	    	return false;
	    }
	    
	    List<GeoSegment> gsList = ((Route) o).segments;
	    
	    // Compare size
	    if (this.segments.size() != gsList.size()) {
	    	checkRep();
	    	return false;
	    }
	    
	    // Compare elements
	    for (int i = 0; i < this.segments.size(); i++) {
	    	if (!this.segments.get(i).equals(gsList.get(i))) {
	    		checkRep();
	    		return false;
	    	}
	    }
	    
	    checkRep();
	    return true;
  	}


    /**
     * Returns a hash code for this.
     * @return a hash code for this.
     **/
  	public int hashCode() {
  		checkRep();

    	return 1;
  	}


    /**
     * Returns a string representation of this.
     * @return a string representation of this.
     **/
  	public String toString() {
  		checkRep();
        String result = this.segments.get(0).toString();
        
	    for (int i = 0; i < this.segments.size(); i++) {
	    	result = String.format("%s,%s", result, this.segments.get(i));
	    }
	    checkRep();
    	return result;
  	}
}
