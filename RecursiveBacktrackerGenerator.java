package mazeGenerator;

import maze.Cell;

import java.util.ArrayList;
import java.util.Stack;

import maze.Maze;


public class RecursiveBacktrackerGenerator implements MazeGenerator {
	private Stack<Cell> stack; // Will be used for backtracking
	boolean visited[][];       // Will be used to keep track of the visited records
	
	/**
	 * Returns a random unvisited Neighbour and sets the value of index
	 * @param curr , cell whose random neighbour would be returned
	 * @param index , index of the random nighbour
	 * @return
	 */
	public Cell chooseRandomUnvisitedNeighbour(Cell curr,Index index) {
		ArrayList<Integer> a = new ArrayList<Integer>();
		for(int i=0;i<Maze.NUM_DIR;i++) {// all the neighbours are stored in arraylist a
			if(curr.neigh[i]!=null && visited[curr.neigh[i].r][curr.neigh[i].c]==false) {
				a.add(i);
			}			
		}
		if(a.size()>0) { // if arraylist is greater than 0 then find a random int less than size of a
			int rand = getRandomInt(a.size()); //find random int
			index.i=a.get(rand);  // assign it to index object
			return curr.neigh[a.get(rand)]; //return Cell of random index
		}
		return null; // return null incase of no neighbours of curr
	}
	
	/**
	 * Generates Random integer
	 * @param limit
	 * @return
	 */
	public int getRandomInt(int limit) { 
		return (int) (System.currentTimeMillis() % limit);// return random int
	}
	
	/**
	 * Removes wall between two cells
	 * @param current
	 * @param neighbour
	 * @param index
	 * @param n_index
	 */
	protected void removeWallBetween(Cell current, Cell neighbour, int index,int n_index) {
		if(current.wall[index]!=null)  // if wall is not null
			current.wall[index].present=false;// remove the wall
		if(neighbour.wall[n_index]!=null) // if wall is not null
			neighbour.wall[n_index].present=false; // remove the wall
	}
	
	/**
	 * Generates random maze using recursion
	 */
	@Override
	public void generateMaze(Maze maze) {
		int sizeR=maze.sizeR;
		int sizeC=maze.sizeC;
		Cell map[][]=maze.map;
		Cell entrance= maze.entrance;
		Cell exit = maze.exit;
		int type= maze.type;
		int sizeTunnel    = maze.sizeTunnel;
        boolean isVisu = maze.isVisu;
        ArrayList<Cell> unVisitedCells = new ArrayList<Cell>(); // List of unvisited integers
        
        if(type==Maze.HEX) {// add all unvisited cell to the list
        	for(int i=0;i<sizeR;i++) {
            	for(int j = (i + 1) / 2; j < sizeC + (i + 1) / 2; j++) {
            		unVisitedCells.add(map[i][j]);
            	}
            }
            visited = new boolean[sizeR][sizeC+(sizeR+1)/2];//set false for all the unvisited cells
        }
        else {// add all unvisited cell to the list
        	for(int i=0;i<sizeR;i++) {
            	for(int j=0;j<sizeC;j++) {
            		unVisitedCells.add(map[i][j]);
            	}
            }
            visited = new boolean[sizeR][sizeC];//set false for all the unvisited cells
        }
        
        
        stack = new Stack<Cell>();//initialize stack
        Cell start,finish,current;
        start=entrance;
        finish=exit;
        current=start;// start from entrance of maze
                  
    	visited[current.r][current.c]=true;// set the first cell as visited

        start=current;
        Index index = new Index(-1);//initialize index
        
        while(!unVisitedCells.isEmpty()) {//run loop till there are unvisited cells left
        	
        	if(unVisitedCells.size()==1)// if only one cell is left
        		finish=unVisitedCells.get(0);//assign finish to the last cell
			Cell unvisited = chooseRandomUnvisitedNeighbour(current,index);
			
			if(unvisited != null) {//if random unvisited cell is not null
				stack.push(current);
				removeWallBetween(current, unvisited,index.i,Maze.oppoDir[index.i]);
				current = unvisited;
				visited[current.r][current.c]=true;
				unVisitedCells.remove(current);
			}
			else if(!stack.isEmpty()) {//if stack becomes empty
				current = stack.pop();
			}
			else {//for any other condition
				current = unVisitedCells.remove(getRandomInt(unVisitedCells.size()));
				if(current!=null) 
					visited[current.r][current.c]=true;
				}
			
		}
		// TODO Auto-generated method stub

	} // end of generateMaze()

} // end of class RecursiveBacktrackerGenerator
