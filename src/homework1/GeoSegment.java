package homework1;

/**
 * A GeoSegment models a straight line segment on the earth. GeoSegments 
 * are immutable.
 * <p>
 * A compass heading is a nonnegative real number less than 360. In compass
 * headings, north = 0, east = 90, south = 180, and west = 270.
 * <p>
 * When used in a map, a GeoSegment might represent part of a street,
 * boundary, or other feature.
 * As an example usage, this map
 * <pre>
 *  Trumpeldor   a
 *  Avenue       |
 *               i--j--k  Hanita
 *               |
 *               z
 * </pre>
 * could be represented by the following GeoSegments:
 * ("Trumpeldor Avenue", a, i), ("Trumpeldor Avenue", z, i),
 * ("Hanita", i, j), and ("Hanita", j, k).
 * </p>
 * 
 * </p>
 * A name is given to all GeoSegment objects so that it is possible to
 * differentiate between two GeoSegment objects with identical
 * GeoPoint endpoints. Equality between GeoSegment objects requires
 * that the names be equal String objects and the end points be equal
 * GeoPoint objects.
 * </p>
 *
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   name : String       // name of the geographic feature identified
 *   p1 : GeoPoint       // first endpoint of the segment
 *   p2 : GeoPoint       // second endpoint of the segment
 *   length : real       // straight-line distance between p1 and p2, in kilometers
 *   heading : angle     // compass heading from p1 to p2, in degrees
 * </pre>
 **/
public class GeoSegment  {

        final private String name;
        final private GeoPoint p1;
        final private GeoPoint p2;
        final private double length;
        final private double heading;
        
        /*
         * Abstraction function:
         *   GeoSegment (p1,p2) represent a strait directed line on earth starting from p1 and ending on p2
         * 
         * Representation invariant:
         *   None
         */
        
        /**
         * Checks if this's status is in line with the representation invariant. 
         * If this is not the case then the running of the program will stop since the checking operation is done 
         * using the "assert" function.
         * @effect if assert is enabled, program stops running in case the current status of this
         * 		   does not fulfill the representation invariant
         **/
      	private void checkRep() {
      		return;
      	}
        
        
        /**
         * Constructs a new GeoSegment with the specified name and endpoints.
         * @requires name != null && p1 != null && p2 != null
         * @effects constructs a new GeoSegment with the specified name and endpoints.
         **/
        public GeoSegment(String name, GeoPoint p1, GeoPoint p2) {
            this.name = name;
            this.p1 = p1;
            this.p2 = p2;
            this.length = p1.distanceTo(p2);
            this.heading = p1.headingTo(p2);
            checkRep();
        }
        
        
        /**
         * Returns a new GeoSegment like this one, but with its endpoints reversed.
         * @return a new GeoSegment gs such that gs.name = this.name
         *         && gs.p1 = this.p2 && gs.p2 = this.p1
         **/
        public GeoSegment reverse() {
        	checkRep();
        	return new GeoSegment(this.name, this.p2, this.p1);
        }
        
        
        /**
         * Returns the name of this GeoSegment.
         * @return the name of this GeoSegment.
         */
        public String getName() {
        	checkRep();
            return this.name;
        }
        
        
        /**
         * Returns first endpoint of the segment.
         * @return first endpoint of the segment.
         */
        public GeoPoint getP1() {
        	checkRep();
            return this.p1;
        }
        
        
        /**
         * Returns second endpoint of the segment.
         * @return second endpoint of the segment.
         */
        public GeoPoint getP2() {
        	checkRep();
            return this.p2;
        }
        
        
        /**
         * Returns the length of the segment.
         * @return the length of the segment, using the flat-surface, near the
         *         Technion approximation.
         */
        public double getLength() {
            return length;
        }
        
        
        /**
         * Returns the compass heading from p1 to p2.
         * @requires this.length != 0
         * @return the compass heading from p1 to p2, in degrees, using the
         *         flat-surface, near the Technion approximation.
         **/
        public double getHeading() {
        	checkRep();
            return heading;
        }
        
        
        /**
         * Compares the specified Object with this GeoSegment for equality.
         * @return gs != null && (gs instanceof GeoSegment)
         *         && gs.name = this.name && gs.p1 = this.p1 && gs.p2 = this.p2
         **/
        public boolean equals(Object gs) {
        	checkRep();
        	
        	// self check
  	      	if (this == gs) {
  	      		return true;
  	      	}
  	      	
  	      	if (gs == null) {
  	      		return false;
  	      	}
  	      
  	      	// type check and cast
  	      	if (getClass() != gs.getClass()) {
  	      		checkRep();
  	      		return false;
  	      	}
  	      
  	      	// field comparison
  	      	GeoSegment other = (GeoSegment) gs;
  	      	
  	        checkRep();
  	      	return this.p1.equals(other.p1) && this.p2.equals(other.p2) && this.name == other.name;
        }
        
        
        /**
         * Returns a hash code value for this.
         * @return a hash code value for this.
         **/
        public int hashCode() {
        	checkRep();
        	//we use bit-wise XOR between the three components that make up the representation of a 
        	// geo-segment (using the built-in hash for the string field)
        	// this provides a viable hash since it is an actual function which means that the hash of a segment is well defined.
        	// in addition to that, it's not entirely unique and this could make it quite helpful in possible uses in hash tables.
        	return this.name.hashCode() ^ this.p1.hashCode() ^ this.p2.hashCode();
        }
        
        
        /**
         * Returns a string representation of this.
         * @return a string representation of this.
         **/
        public String toString() {
        	checkRep();
        	return String.format("%s, %s, %s", this.name, this.p1.toString(), this.p2.toString());
        }

}

