package homework1;

public class GeoFeatureTest {


	private static final double tolerance = 0.01;
   
  	private GeoFeature buildFeature(GeoFeature gf, int start, int end) {
  		
  		assert(start >= 0 && start < ExampleGeoSegments.segments.length );
  		assert(end >= 0 && end < ExampleGeoSegments.segments.length );

  		int delta = start < end ? 1 : -1;
  		
  		if (gf == null) {
  			gf = new GeoFeature(ExampleGeoSegments.segments[start]);
  		} else {
  			gf = gf.addSegment(delta > 0 ? ExampleGeoSegments.segments[start] : ExampleGeoSegments.segments[start].reverse());
  		}
  		
  		if (start == end) {
  			return gf;
  		}
  		
  		do {
  			start += delta;
  			gf = gf.addSegment(delta > 0 ? ExampleGeoSegments.segments[start] : ExampleGeoSegments.segments[start].reverse());
  		} while(start != end);
  		
  		return gf;
  	}
  	
 	
  	
  	boolean same(double x, double y) {
  		return ((y >= x-tolerance) && (y <= x+tolerance));
  	}
  	
  	
	public void show(String str) {
		System.out.println();
		System.out.println("***** " + str + " *****");
	}
		
		
	public void show(String str, boolean ok) {
		if (ok)
			System.out.print("v ");
		else
			System.out.print("x ");
		System.out.println(str);	
	}
  	
	// Get the current line number in Java
	public static String line() {
		return String.format("%d", Thread.currentThread().getStackTrace()[2].getLineNumber());
	}

  	
  	public void test() {
  		
  		GeoFeature gf1, gf1_copy, gf2, gf3;
  		gf1 = gf1_copy = gf2 = gf3 = null;
  		
  		gf1 = buildFeature(null, 0, 6);
  		gf1_copy = buildFeature(null, 0, 6);
  		gf2 = buildFeature(null, 6, 0);
  		
		show(line(), gf1.equals(gf1));
		show(line(), !gf1.equals(null));
		show(line(), gf1.equals(buildFeature(null, 0, 6)));
		show(line(), gf1.equals(gf1_copy));

		gf3 = gf1.addSegment(ExampleGeoSegments.segments[7]);
		
		show(line(), !gf1.equals(gf3));
		
		gf1 = gf1.addSegment(ExampleGeoSegments.segments[7]);

		show(line(), gf1.equals(gf3));
		
		gf1 = gf1.addSegment(ExampleGeoSegments.segments[8]);
		
		show(line(), !gf1.equals(gf3));
		
		
  		gf1 = buildFeature(null, ExampleGeoSegments.segments.length - 1, 0);
  		gf2 = buildFeature(null, ExampleGeoSegments.segments.length - 1, 1);
  		
		show(line(), !gf1.equals(gf2));
		gf2 = buildFeature(gf2, 0, 0);
		show(line(), gf1.equals(gf2));				
		
  	}


	public static void main(String[] args) {
		GeoFeatureTest test = new GeoFeatureTest();
		test.test();
	}
	
}
