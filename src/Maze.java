import java.io.BufferedReader;
import java.io.FileReader;

public class Maze implements Comparable<Maze> {

	//All of the Maze's variables
	private int[][] mazeArray;
	private String fileName;
	private String mazeName;
	private int height;
	private int width;
	private int posX;
	private int posY;
	private int initialX;
	private int initialY;
	private int endX;
	private int endY;
	private final static int N = 1, E = 2, S = 3, W = 4;
	
	//Maze constructor
	public Maze(int[][] currentMaze, int posX, int posY, int initialX, int initialY, int endX, int endY, String mazeName) {
		
		
		this.mazeName = mazeName;
		if (currentMaze == null) {
			initialMaze();
		} else {
			this.width = currentMaze.length;
			this.height = currentMaze[0].length;
			this.mazeArray = currentMaze;
			this.posX = posX;
			this.posY = posY;
			this.initialX = initialX;
			this.initialY = initialY;
			this.endX = endX;
			this.endY = endY;
		}
	}

	//If the maze is the first one it is created
	public void initialMaze() {
		
		String[] currentChar;
		String currentLine;
		int lineCounter = 0;
		//finds the file within the directory's
		fileName = "./Gentrack/" + mazeName + ".txt";
		
		try {
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			
			while (reader.ready()) {
				currentLine = reader.readLine();
				currentChar = currentLine.split(" ");	
				
				if(lineCounter == 0) {
					width = Integer.parseInt(currentChar[0]);
					height = Integer.parseInt(currentChar[1]);
					mazeArray = new int[width][height];
				} else if (lineCounter == 1) {
					initialX = posX = Integer.parseInt(currentChar[0]);
					initialY = posY = Integer.parseInt(currentChar[1]);
				} else if (lineCounter == 2) {
					endX = Integer.parseInt(currentChar[0]);
					endY = Integer.parseInt(currentChar[1]);
				} else {
					for(int i = 0; i < currentChar.length; i++) {
						mazeArray[i][(lineCounter-3)] = Integer.parseInt(currentChar[i]);
					}
				}
				lineCounter++;
			}			
		} catch (Exception e) {
			System.err.println("File could not be read");
			e.printStackTrace();
		}
	}
	
		public Maze move(int x) {
			int newPosX;
			int newPosY;
			int newLocation;
			   	 
			//fill a new 2d char with the letters that are in the old one
			int[][] newMazeArray = new int[width][height];
			for (int j = 0; j < height; ++j) {
				for (int i = 0; i < width; ++i) {
					newMazeArray[i][j] = mazeArray[i][j];
				}
			}
			 
			//the switch which takes and input from numbers 1 to 4
		switch (x) {
		case N:
	   		//if the agent is at a left most position it will not be able to move left
	   		newLocation = mazeArray[posX][posY - 1];
	   		if (newLocation == 1 || newLocation == 2) {
	   			return null;
	   		}
	   		newPosX = posX;
	   		newPosY = posY - 1;
	   		break;
	   		 
	   	case E:
	   		newLocation = mazeArray[posX + 1][posY];
	   		if (newLocation == 1 || newLocation == 2) {
	   			return null;
	   		}
	   		newPosX = posX + 1;
	   		newPosY = posY;
	   		break;
	   		 
	   	case S:
	   		newLocation = mazeArray[posX][posY + 1];
	   		if (newLocation == 1 || newLocation == 2) {
	   			return null;
	   		}
	   		newPosX = posX;
	   		newPosY = posY + 1;
	   		break;
	   		
	   	case W:
	   		newLocation = mazeArray[posX - 1][posY];
	   		if (newLocation == 1 || newLocation == 2) {
	   			return null;
	   		}
	   		newPosX = posX - 1;
	   		newPosY = posY;
	   		break;
	   	default:
	   		//if anything other than a number from 1 to 4 is passed it will error
	   		System.err.println("No Move Was Made");
	   		return null;
	   	}
		
	   	//positions that have been visited are given a placeholder
	   	newMazeArray[posX][posY] = 2;
	   	
	   	return new Maze(newMazeArray, newPosX, newPosY, initialX, initialY, endX, endY, mazeName);
	}
		
	//used for debugging 
	public void printMaze() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				System.out.print(mazeArray[j][i]);
			}
			System.out.println();
		}
	}
    
    public Boolean checkExit() {
    	if (posX == endX && posY == endY) {
    		return true;
    	} 
    		return false;
    }
    
    //converts all of the int's into their respective characters
    public void printFinishedMaze() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				int number = mazeArray[j][i];
				String changeStr;
				if(number == 1) {
					changeStr = "#";
				} else if (number == 2) {
					changeStr = "X";
				} else {
					changeStr = " ";
				}
				if(j == endX && i == endY) {
					changeStr = "E";
				}
				if(j == initialX && i == initialY) {
					changeStr = "S";
				}
				System.out.print(changeStr);
			}
			System.out.println();
		}
	}
    
    //the distance from the current position to the end to use as a heuristic
    public int score() {
    	int yDistance = Math.abs(endX - posX);
    	int xDistance = Math.abs(endY - posY);
    	return xDistance + yDistance;
    }

    //compares the current state with another one
    @Override
    public int compareTo(Maze m) {
   	 	return this.score() - m.score();
    }
}
