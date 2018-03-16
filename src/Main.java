public class Main {

	private static String mazeName = "large_input"
			;
	public static void main(String[] args) {
		
		//Takes the maze file's name as an input 
		if(args.length > 0) {
			mazeName = args[0];
		}
		//Creates a search object to go though the maze
		Search distance = new Search();
	   	distance.distanceSearch(mazeName);
	   	//System.out.println("Time Taken: " + distance.timeTaken + "ms");
		

	}
	
}
