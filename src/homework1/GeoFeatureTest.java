package homework1;

public class GeoFeatureTest {


	private static final double tolerance = 0.01;
	
	public static GeoSegment segments[] = {
			ExampleGeoSegments.segments[0],
			ExampleGeoSegments.segments[1],
			ExampleGeoSegments.segments[2],
			ExampleGeoSegments.segments[3],
			ExampleGeoSegments.segments[4],
			ExampleGeoSegments.segments[5],
			ExampleGeoSegments.segments[7],
			ExampleGeoSegments.segments[8],
			ExampleGeoSegments.segments[9],
			ExampleGeoSegments.segments[10],
			ExampleGeoSegments.segments[11],
			ExampleGeoSegments.segments[13],
			ExampleGeoSegments.segments[14],
			ExampleGeoSegments.segments[15],
	};
	
	GeoFeatureTest (){
		
	}
   
  	private GeoFeature buildFeature(GeoFeature gf, int start, int end) {
  		
  		assert(start >= 0 && start < segments.length );
  		assert(end >= 0 && end < segments.length );

  		int delta = start < end ? 1 : -1;
  		
  		if (gf == null) {
  			gf = new GeoFeature(delta > 0 ? segments[start] : segments[start].reverse());
  		} else {
  			gf = gf.addSegment(delta > 0 ? segments[start] : segments[start].reverse());
  		}
  		
  		if (start == end) {
  			return gf;
  		}
  		
  		do {
  			start += delta;
  			gf = gf.addSegment(delta > 0 ? segments[start] : segments[start].reverse());
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
  		
  		var iter = gf1.getGeoSegments();
  		for (int i = 0; i <= 6; i++) {
  			var segment = iter.next();
  			show(line(),segment.equals(segments[i]));
  		}
  		
		show(line(), gf1.equals(gf1));
		show(line(), !gf1.equals(null));
		show(line(), gf1.equals(buildFeature(null, 0, 6)));
		show(line(), gf1.equals(gf1_copy));

		gf3 = gf1.addSegment(segments[7]);
		
		show(line(), !gf1.equals(gf3));
		
		gf1 = gf1.addSegment(segments[7]);

		show(line(), gf1.equals(gf3));
		
		gf1 = gf1.addSegment(segments[8]);
		
		show(line(), !gf1.equals(gf3));
		
		
  		gf1 = buildFeature(null, segments.length - 1, 0);
  		gf2 = buildFeature(null, segments.length - 1, 1);
  		
		show(line(), !gf1.equals(gf2));
		gf2 = buildFeature(gf2, 0, 0);
		show(line(), gf1.equals(gf2));				
		
  	}


	public static void main(String[] args) {
		GeoFeatureTest test = new GeoFeatureTest();
		test.test();
	}
	
}
