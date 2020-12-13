package homework1;

public class RouteTest {


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
   
  	private Route buildFeature(Route route, int start, int end) {
  		
  		assert(start >= 0 && start < segments.length );
  		assert(end >= 0 && end < segments.length );

  		int delta = start < end ? 1 : -1;
  		
  		if (route == null) {
  			route = new Route(delta > 0 ? segments[start] : segments[start].reverse());
  		} else {
  			route = route.addSegment(delta > 0 ? segments[start] : segments[start].reverse());
  		}
  		
  		if (start == end) {
  			return route;
  		}
  		
  		do {
  			start += delta;
  			route = route.addSegment(delta > 0 ? segments[start] : segments[start].reverse());
  		} while(start != end);
  		
  		return route;
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
  		
  		Route rout1, rout1_copy, rout2, rout3;
  		rout1 = rout1_copy = rout2 = rout3 = null;
  		
  		rout1 = buildFeature(null, 0, 6);
  		rout1_copy = buildFeature(null, 0, 6);
  		rout2 = buildFeature(null, 6, 0);
  		
  		var iter = rout1.getGeoSegments();
  		for (int i = 0; i <= 6; i++) {
  			var segment = iter.next();
  			show(line(),segment.equals(segments[i]));
  		}
  		
		show(line(), rout1.equals(rout1));
		show(line(), !rout1.equals(null));
		show(line(), rout1.equals(buildFeature(null, 0, 6)));
		show(line(), rout1.equals(rout1_copy));

		rout3 = rout1.addSegment(segments[7]);
		
		show(line(), !rout1.equals(rout3));
		
		rout1 = rout1.addSegment(segments[7]);

		show(line(), rout1.equals(rout3));
		
		rout1 = rout1.addSegment(segments[8]);
		
		show(line(), !rout1.equals(rout3));
		
		
  		rout1 = buildFeature(null, segments.length - 1, 0);
  		rout2 = buildFeature(null, segments.length - 1, 1);
  		
		show(line(), !rout1.equals(rout2));
		rout2 = buildFeature(rout2, 0, 0);
		show(line(), rout1.equals(rout2));			
  	}


	public static void main(String[] args) {
		RouteTest test = new RouteTest();
		test.test();
	}
	
}
