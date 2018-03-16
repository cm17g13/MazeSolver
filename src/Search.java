import java.util.ArrayList;
import java.util.PriorityQueue;
 
public class Search {
    
    Long start; //Stores the start time of the start of the search
    Long end; //sores the end time
    long timeTaken; //the total time taken
    
    public boolean distanceSearch(String mazeName) {
 
		start = System.currentTimeMillis();
		//uses a priority queue, that accepts the compare between states
		//o that it can order the moves and pick the bests
		PriorityQueue<Maze> fringe = new PriorityQueue<Maze>();
		fringe.add(new Maze(null, 0,0,0,0,0,0, mazeName));
		Maze current;
		while(true) {
	   		//if all possible moves are exhausted then there is no solution
	   		if(fringe.isEmpty()) {
	   			System.err.println("There is no possible solution");
	   			return false;
	   		}
	   		//takes the  best move and makes it the current move
	   		current = fringe.poll();
	   		//if the current state is the solution then the maze is solved
	   		if (current.checkExit()) {
	   			System.out.println("Solution found");
	   			end = System.currentTimeMillis();
	   			timeTaken = end - start;
	   			current.printFinishedMaze();
	   			return (true);
	   		}
	   		 
	   		ArrayList<Maze> children = expand(current);
	   		for(int i = 0; i < children.size(); i++) {
	   			//Adds the children to the PriorityQueue
	   			//for them to be compared and the best move to be taken
	   			fringe.add(children.get(i));
	   		}
		}
	}
	//expands all of the possible moves from a Maze position and adds them to an arrayList
	public ArrayList<Maze> expand(Maze m) {
		ArrayList<Maze> children = new ArrayList<Maze>();
		for (int i = 1; i <= 4; ++i) {
			Maze newMaze = m.move(i);
			//checks if the move is possible
			if (newMaze != null) {
				children.add(newMaze);
			}
		}
		return children;
	
	}  
}
