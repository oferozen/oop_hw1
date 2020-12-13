package homework1;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A GeoFeature represents a route from one location to another along a
 * single geographic feature. GeoFeatures are immutable.
 * <p>
 * GeoFeature abstracts over a sequence of GeoSegments, all of which have
 * the same name, thus providing a representation for nonlinear or nonatomic
 * geographic features. As an example, a GeoFeature might represent the
 * course of a winding river, or travel along a road through intersections
 * but remaining on the same road.
 * <p>
 * GeoFeatures are immutable. New GeoFeatures can be constructed by adding
 * a segment to the end of a GeoFeature. An added segment must be properly
 * oriented; that is, its p1 field must correspond to the end of the original
 * GeoFeature, and its p2 field corresponds to the end of the new GeoFeature,
 * and the name of the GeoSegment being added must match the name of the
 * existing GeoFeature.
 * <p>
 * Because a GeoFeature is not necessarily straight, its length - the
 * distance traveled by following the path from start to end - is not
 * necessarily the same as the distance along a straight line between
 * its endpoints.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   start : GeoPoint       // location of the start of the geographic feature
 *   end : GeoPoint         // location of the end of the geographic feature
 *   startHeading : angle   // direction of travel at the start of the geographic feature, in degrees
 *   endHeading : angle     // direction of travel at the end of the geographic feature, in degrees
 *   geoSegments : sequence    // a sequence of segments that make up this geographic feature
 *   name : String          // name of geographic feature
 *   length : real          // total length of the geographic feature, in kilometers
 * </pre>
 **/
public class GeoFeature {
    
    // Implementation hint:
    // When asked to return an Iterator, consider using the iterator() method
    // in the List interface. Two nice classes that implement the List
    // interface are ArrayList and LinkedList. If comparing two Lists for
    // equality is needed, consider using the equals() method of List. More
    // info can be found at:
    //   http://docs.oracle.com/javase/8/docs/api/java/util/List.html
    
	/*
     * Abstraction function:
     *     Geofeature map a list of segments into a path composed of a series of the following linear lines:
     *     (segments[0].p1, segments[0].p2), (segments[1].p1, segments[1].p2), ...,  (segments.last().p1, segments.last().p2).
     *      
     * Representation invariant:
     *     segments.size() != 0 &&
     *     foreach i in 0..(segments.size()-2) => segments[i].p2 == segments[i+1].p1 
     *  
     */
    
	private final ArrayList<GeoSegment> segments = new ArrayList<GeoSegment>();
	private final double length;

    /**
     * Checks if this's status is in line with the representation invariant. 
     * If this is not the case then the running of the program will stop since the checking operation is done 
     * using the "assert" function.
     * @effect if assert is enabled, program stops running in case the current status of this
     * 		   does not fulfill the representation invariant
     **/
  	private void checkRep() {
  		
  		assert(this.segments.size() != 0);
  		
  		var iter = this.segments.iterator();
  		GeoSegment current = iter.next();
  		while (iter.hasNext()) {
  			var next = iter.next();
  			assert(current.getP2().equals(next.getP1()));
  			current = next;
  		}
  		
  	}
	
    /**
     * Constructs a new GeoFeature.
     * @requires gs != null
     * @effects Constructs a new GeoFeature, r, such that
     *          r.name = gs.name &&
     *          r.startHeading = gs.heading &&
     *          r.endHeading = gs.heading &&
     *          r.start = gs.p1 &&
     *          r.end = gs.p2
     **/
	public GeoFeature(GeoSegment gs) {
		this.segments.add(gs);
		this.length = gs.getLength();
	    checkRep();
	}

    /**
     * Constructs a new GeoFeature.
     * @requires gs != null && gs.size() != 0
     * @effects Constructs a new GeoFeature, r, such that
     *          r.name = gs.name &&
     *          r.startHeading = gs.heading &&
     *          r.endHeading = gs.heading &&
     *          r.start = gs.p1 &&
     *          r.end = gs.p2
     **/
	private GeoFeature(ArrayList<GeoSegment> gs) {
		this.segments.addAll(gs);
		
		double length = 0;
		
		var iter = gs.iterator();
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
     * Returns name of geographic feature.
     * @return name of geographic feature
     */
    public String getName() {
    	checkRep();
        return firstGeoSegment().getName();
    }


    /**
     * Returns location of the start of the geographic feature.
     * @return location of the start of the geographic feature.
     */
    public GeoPoint getStart() {
    	checkRep();
   	  	return firstGeoSegment().getP1();
    }


    /**
     * Returns location of the end of the geographic feature.
     * @return location of the end of the geographic feature.
     */
    public GeoPoint getEnd() {
    	checkRep();
   	  	return lastGeoSegment().getP2();
    }


    /**
     * Returns direction of travel at the start of the geographic feature.
     * the start of the geoFeature is the first segment of the geo Feature with a positive length
     * @return direction (in standard heading) of travel at the start of the
     *         geographic feature, in degrees if this.length > 0.
     *         0 returned if this.length = 0
     */
    public double getStartHeading() {
    	checkRep();
  		// if the length of this geo feature is zero then heading is 0 degrees according to the convention we chose
  		if(this.length == 0) {
  			return 0;
  		}
  		// length of this is not zero; find the first segment with a length longer than zero
  		Iterator<GeoSegment> iter = this.segments.iterator();
  		GeoSegment currentGeoSegment = null;
  		while(iter.hasNext()) {
  			currentGeoSegment = iter.next();
  			if (currentGeoSegment.getLength() > 0) {
  				break;
  			}
  		}
  		checkRep();
  		return currentGeoSegment.getHeading();
    }


    /**
     * Returns direction of travel at the end of the geographic feature.
     * the end of the geoFeature is the last segment of the geo Feature with a positive length
     * @return direction (in standard heading) of travel at the end of the
     *         geographic feature, in degrees if this.length > 0.
     *         0 returned if this.length = 0
     */
    public double getEndHeading() {
    	checkRep();
    	return lastGeoSegment().getHeading();
    }


    /**
     * Returns total length of the geographic feature, in kilometers.
     * @return total length of the geographic feature, in kilometers.
     *         NOTE: this is NOT as-the-crow-flies, but rather the total
     *         distance required to traverse the geographic feature. These
     *         values are not necessarily equal.
     */
    public double getLength() {
    	checkRep();
        return this.length;
    }


    /**
      * Creates a new GeoFeature that is equal to this GeoFeature with gs
      * appended to its end.
      * @requires gs != null && gs.p1 = this.end && gs.name = this.name.
      * @return a new GeoFeature r such that
      *         r.end = gs.p2 &&
      *         r.endHeading = gs.heading &&
      *           r.length = this.length + gs.length
      **/
    public GeoFeature addSegment(GeoSegment gs) {
    	checkRep();
    	var segmentList = new ArrayList<GeoSegment>(this.segments);
    	segmentList.add(gs);
    	checkRep();
    	return new GeoFeature(segmentList);
    }


    /**
     * Returns an Iterator of GeoSegment objects. The concatenation of the
     * GeoSegments, in order, is equivalent to this GeoFeature. All the
     * GeoSegments have the same name.
     * @return an Iterator of GeoSegments such that
     * <pre>
     *      this.start        = a[0].p1 &&
     *      this.startHeading = a[0].heading &&
     *      this.end          = a[a.length - 1].p2 &&
     *      this.endHeading   = a[a.length - 1].heading &&
     *      this.length       = sum(0 <= i < a.length) . a[i].length &&
     *      for all integers i
     *          (0 <= i < a.length-1 => (a[i].name == a[i+1].name &&
     *                                   a[i].p2d  == a[i+1].p1))
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoSegment
     */
    public Iterator<GeoSegment> getGeoSegments() {
    	checkRep();
        return this.segments.iterator();
    }


    /**
     * Compares the argument with this GeoFeature for equality.
     * @return o != null && (o instanceof GeoFeature) &&
     *         (o.geoSegments and this.geoSegments contain
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
	    
	    ArrayList<GeoSegment> gsList = ((GeoFeature) o).segments;
	    
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
        
	    for (int i = 0; i <= this.segments.size(); i++) {
	    	result = String.format("%s,%s", result, this.segments.get(i)); // get(i) ?
	    }
	    checkRep();
    	return result;
    }
}
