package homework1;

/**
 * A GeoPoint is a point on the earth. GeoPoints are immutable.
 * <p>
 * North latitudes and east longitudes are represented by positive numbers.
 * South latitudes and west longitudes are represented by negative numbers.
 * <p>
 * The code may assume that the represented points are nearby the Technion.
 * <p>
 * <b>Implementation direction</b>:<br>
 * The Ziv square is at approximately 32 deg. 46 min. 59 sec. N
 * latitude and 35 deg. 0 min. 52 sec. E longitude. There are 60 minutes
 * per degree, and 60 seconds per minute. So, in decimal, these correspond
 * to 32.783098 North latitude and 35.014528 East longitude. The 
 * constructor takes integers in millionths of degrees. To create a new
 * GeoPoint located in the the Ziv square, use:
 * <tt>GeoPoint zivCrossroad = new GeoPoint(32783098,35014528);</tt>
 * <p>
 * Near the Technion, there are approximately 110.901 kilometers per degree
 * of latitude and 93.681 kilometers per degree of longitude. An
 * implementation may use these values when determining distances and
 * headings.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   latitude :  real        // latitude measured in degrees
 *   longitude : real        // longitude measured in degrees
 * </pre>
 **/
public class GeoPoint {

    /** Minimum value the latitude field can have in this class. **/
    public static final int MIN_LATITUDE  =  -90 * 1000000;
        
    /** Maximum value the latitude field can have in this class. **/
    public static final int MAX_LATITUDE  =   90 * 1000000;
        
    /** Minimum value the longitude field can have in this class. **/
    public static final int MIN_LONGITUDE = -180 * 1000000;
        
    /** Maximum value the longitude field can have in this class. **/
    public static final int MAX_LONGITUDE =  180 * 1000000;

    /**
     * Approximation used to determine distances and headings using a
     * "flat earth" simplification.
     */
    public static final double KM_PER_DEGREE_LATITUDE = 110.901;

    /**
     * Approximation used to determine distances and headings using a
     * "flat earth" simplification.
     */
    public static final double KM_PER_DEGREE_LONGITUDE = 93.681;
    
    // Implementation hint:
    // Doubles and floating point math can cause some problems. The exact
    // value of a double can not be guaranteed except within some epsilon.
    // Because of this, using doubles for the equals() and hashCode()
    // methods can have erroneous results. Do not use floats or doubles for
    // any computations in hashCode(), equals(), or where any other time 
    // exact values are required. (Exact values are not required for length 
    // and distance computations). Because of this, you should consider 
    // using ints for your internal representation of GeoPoint. 

    
    // TODO : change to _variable
    private final int latitude;
    private final int longitude;
    
    /*
     * Abstraction function:
     *   Geographic decimal coordinate (decimalLatitude, decimalLongitude) =>
     *   Geographic degrees coordinate ((latitude degrees, latitude minutes, latitude seconds),
     *                                  (longitude degrees, longitude minutes, longitude seconds)) 
     * Where:
     *   latitude degrees = bottom (decimalLatitude / 1000000)
     *   latitude minutes = bottom (60 * ((decimalLatitude / 1000000) - latitude degrees))
     *   latitude seconds = bottom (3600 * ((decimalLatitude / 1000000) - (latitude degrees)) - (60 * latitude minutes))
     *   longitude degrees = bottom (decimalLongitude / 1000000)
     *   longitude minutes = bottom (60 * ((decimalLongitude / 1000000) - longitude degrees))
     *   longitude seconds = bottom (3600 * ((decimalLongitude / 1000000) - (longitude degrees)) - (60 * longitude minutes))
     *
     * Representation invariant:
     *   decimalLatitude >= MIN_LATITUDE && decimalLatitude <= MAX_LATITUDE &&
     *   decimalLongitute > MIN_LONGITUDE && decimalLongitute <= MAX_LONGITUDE
     *  
     */
    
    /**
     * Checks if this's status (latitude and longitude private fields of this) is in line with the representation invariant. 
     * If this is not the case then the running of the program will stop since the checking operation is done 
     * using the "assert" function.
     * @effect if assert is enabled, program stops running in case the current status of this
     * 		   does not fulfill the representation invariant
     **/
  	private void checkRep() {
  		assert(this.latitude <= 90*1000000);
  		assert(this.latitude >= -90*1000000);
  		assert(this.longitude <= 180*1000000);
  		assert(this.longitude > -180*1000000);
  	}
    
    /**
     * Constructs GeoPoint from a latitude and longitude.
     * @requires the point given by (latitude, longitude) in millionths
     *           of a degree is valid such that:
     *           (MIN_LATITUDE <= latitude <= MAX_LATITUDE) and
     *           (MIN_LONGITUDE <= longitude <= MAX_LONGITUDE)
     * @effects constructs a GeoPoint from a latitude and longitude
     *          given in millionths of degrees.
     **/
    public GeoPoint(int latitude, int longitude) {
        this.latitude = latitude; 
        if(longitude == -180 * 1000000) {
        	this.longitude = -longitude;
        }else {
        	this.longitude = longitude;
        }
        checkRep();
    }

     
    /**
     * Returns the latitude of this.
     * @return the latitude of this in millionths of degrees.
     */
    public int getLatitude() {
    	checkRep();
        return this.latitude;
    }


    /**
     * Returns the longitude of this.
     * @return the latitude of this in millionths of degrees.
     */
    public int getLongitude() {
    	checkRep();
        return this.longitude;
    }


    /**
     * Computes the distance between GeoPoints.
     * @requires gp != null
     * @return the distance from this to gp, using the flat-surface, near
     *         the Technion approximation.
     **/
    public double distanceTo(GeoPoint gp) {
    	checkRep();
    	
    	// Calculating the distance of the latitude component of the two points
        double latitudeDeltaNormalized =  Math.abs(this.latitude - gp.latitude) / 1000000.0; 
        double latitdudeDistanceKm = latitudeDeltaNormalized * KM_PER_DEGREE_LATITUDE;
        
        // Calculating the distance of the longitude component of the two points
        double longtitudeDeltaNormalized =  Math.abs(this.longitude - gp.longitude) / 1000000.0;
        double longtitudeDistanceKm = longtitudeDeltaNormalized * KM_PER_DEGREE_LONGITUDE;
        
        // Calculating the distance between the two points
        double distance = Math.sqrt(Math.pow(latitdudeDistanceKm, 2) + Math.pow(longtitudeDistanceKm, 2));
        
        checkRep();
        return distance;
    }


    /**
     * Computes the compass heading between GeoPoints.
     * @requires gp != null && !this.equals(gp)
     * @return the compass heading h from this to gp, in degrees, using the
     *         flat-surface, near the Technion approximation, such that
     *         0 <= h < 360. In compass headings, north = 0, east = 90,
     *         south = 180, and west = 270.
     **/
    public double headingTo(GeoPoint gp) {
       //    Implementation hints:
       // 1. You may find the mehtod Math.atan2() useful when
       // implementing this method. More info can be found at:
       // http://docs.oracle.com/javase/8/docs/api/java/lang/Math.html
       //
       // 2. Keep in mind that in our coordinate system, north is 0
       // degrees and degrees increase in the clockwise direction. By
       // mathematical convention, "east" is 0 degrees, and degrees
       // increase in the counterclockwise direction. 
    	checkRep();
    	
        double latitudeDelta =  gp.latitude - this.latitude; 
        double longitudeDelta =  gp.longitude - this.longitude;
        
        // Calculating the angle in radians between the heading directing of a straight path 
        // that starts in point this and ends in poing gp and the positive direction of the X axis.
        double heading = - Math.atan2(latitudeDelta, longitudeDelta); 
        
        heading = 360 * heading / (2 * Math.PI); // convert radians to degrees
        
        // transforming the angle to one that is measured with the positive direction of the Y axis (north)
        heading = (heading + 360 + 90) % 360;  
        
        checkRep();
        return heading;
    }


    /**
     * Compares the specified Object with this GeoPoint for equality.
     * @return gp != null && (gp instanceof GeoPoint) &&
     *            gp.latitude = this.latitude && gp.longitude = this.longitude
     **/
    public boolean equals(Object gp) {
    	checkRep();
        // self check
        if (this == gp)
        	return true;
        
        if (gp == null) {
           return false;
          }
          
        // type check and cast
        if (getClass() != gp.getClass()) {
        	checkRep();
        	return false;
        }
          
        // field comparison
        GeoPoint other = (GeoPoint) gp;
        checkRep();
        return this.latitude == other.latitude && this.longitude == other.longitude;
    }


    /**
     * Returns a hash code value for this GeoPoint.
     * @return a hash code value for this GeoPoint.
     **/
    public int hashCode() {
    	checkRep();
    	// bit-wise XOR between the two numbers that make up the representation of a geo-point 
    	// provides a viable hash since it is an actual function which means that the hash of a point is well defined.
    	// in addition to that, it's not entirely unique and this could make it quite helpful in possible uses in hash tables. 
        return this.latitude ^ this.longitude;
    }


    /**
     * Returns a string representation of this GeoPoint.
     * @return a string representation of this GeoPoint.
     **/
    public String toString() {
    	checkRep();
        return String.format("%d,%d", this.latitude, this.longitude);
    }

}
