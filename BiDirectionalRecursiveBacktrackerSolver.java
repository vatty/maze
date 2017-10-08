package mazeSolver;

import java.util.ArrayList;
import java.util.Stack;

import maze.Maze;
import maze.Cell;
/**
 * Implements the BiDirectional recursive backtracking maze solving algorithm.
 */
public class BiDirectionalRecursiveBacktrackerSolver implements MazeSolver {

	ArrayList<Cell>wasHere;//list of cells which are visited
	Stack<Cell>correctPath;//list of cells which lies on the correct path
	boolean visited[][];//keeps track of the visited cells
	Cell start,end,current;
	
	/**
	 * Solves a maze using backtracking
	 */
	@Override
	public void solveMaze(Maze maze) {
		wasHere = new ArrayList<Cell>();
		correctPath = new Stack<Cell>();
		
		if(maze.type!=Maze.HEX) {//Initialize all cells as unvisited
			visited = new boolean[maze.sizeR][maze.sizeC];
		}
		else {//Initialize all cells as unvisited
            visited = new boolean[maze.sizeR][maze.sizeC+(maze.sizeR+1)/2];
		}
		
		start=maze.entrance;
		end=maze.exit;
		current=start;//Initialize solving from the start cell
		visited[current.r][current.c]=true;//Mark current cell as visited
		correctPath.push(current);//Push current cell into the correct path stack
		wasHere.add(current);//Insert in the list of visited cells
		
		while(correctPath.peek()!=end) {//Until we didn't reach the end
			if(correctPath.empty()) break;//break the loop if correct path stack becomes empty
			current=correctPath.peek();//Take the topmost cell
			int flag=1;
			for(int i=0;i<Maze.NUM_DIR;i++) {//process all its neighbours
				if(current.neigh[i]!=null && visited[current.neigh[i].r][current.neigh[i].c]==false && current.wall[i].present==false) {//select the eligible one
					correctPath.push(current.neigh[i]);//Push the eligible cell to correct path
					visited[current.neigh[i].r][current.neigh[i].c]=true;//Mark it as visited
					wasHere.add(current.neigh[i]);//Include in visited list
					flag=0;
					//break;
				}
			}
			if(flag==1)//If no eligible cell is found
			correctPath.pop();//Pop out the cell since it is not on correct path
		}
		for(int i=0;i<correctPath.size();i++)//Draw the correct path
		maze.drawFtPrt(correctPath.elementAt(i));
		// TODO Auto-generated method stub

	} // end of solveMaze()


	/**
	 * Returns whether maze is solved or not
	 */
	@Override
	public boolean isSolved() {
		if(correctPath.peek()==end) 			
			return true;
		// TODO Auto-generated method stub
		return false;
	} // end if isSolved()


	/**
	 * Returns total number of cells that are explored
	 */
	@Override
	public int cellsExplored() {
		return wasHere.size();
		// TODO Auto-generated method stub
	} // end of cellsExplored()

} // end of class BiDirectionalRecursiveBackTrackerSolver
