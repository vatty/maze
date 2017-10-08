package mazeGenerator;

import java.util.*;

import maze.Maze;
import maze.Cell;

public class GrowingTreeGenerator implements MazeGenerator {
	// Growing tree maze generator. As it is very general, here we implement as "usually pick the most recent cell, but occasionally pick a random cell"
	
	double threshold = 0.1;//Chances of picking a random cell
	
	boolean visited[][];//keeps track of all the unvisited cells
	ArrayList<Cell>notInMaze;//list of all the cells which are not in maze
	
	/**
	 * Removes wall between two cells
	 * @param current
	 * @param neighbour
	 * @param index
	 * @param n_index
	 */
	protected void removeWallBetween(Cell current, Cell neighbour, int index,int n_index) {
		if(current.wall[index]!=null) 
			current.wall[index].present=false;
		if(neighbour.wall[n_index]!=null)
			neighbour.wall[n_index].present=false;
	}
	
	/**
	 * Picks a random cell from the list of cells
	 * @param cells
	 * @return
	 */
	private Cell chooseRandomCell(ArrayList<Cell>cells) {
		if(cells.size()==0) return null;
		int rand=getRandomInt(cells.size());
		return cells.get(rand);
		
	}
	
	/**
	 * Returns a random integer less than limit
	 * @param limit
	 * @return
	 */
	public int getRandomInt(int limit) {
		return (int) (System.currentTimeMillis() % limit);
	}
	
	/**
	 * Generates maze using growingtree generator algorithm
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
        
        ArrayList<Cell>cells =  new ArrayList<Cell>();
        
        if(type==Maze.HEX) {
        	visited=new boolean[sizeR][sizeC+(sizeR+1)/2];//Initialize all the cells as unvisited
        	notInMaze= new ArrayList<Cell>();
        	for(int i=0;i<sizeR;i++) {
            	for(int j = (i + 1) / 2; j < sizeC + (i + 1) / 2; j++) {//Add all the cell to not in maze list
            		notInMaze.add(map[i][j]);
            	}
            }
        }
        else {
        	visited=new boolean[sizeR][sizeC];//Initialize all the cells as unvisited
        	notInMaze= new ArrayList<Cell>();
            for(int i=0;i<sizeR;i++) {
            	for(int j=0;j<sizeC;j++) {//Add all the cell to not in maze list
            		notInMaze.add(map[i][j]);
            	}
            }
        }
        
        int count = 1;//Initialize total number of cells that are visited to find the solution
        
        
        Cell randomCell=new Cell();
        randomCell=chooseRandomCell(notInMaze);//choose a cell randomnly from not in maze cells list
        cells.add(randomCell);
        visited[randomCell.r][randomCell.c]=true;//,ark the random cell as visited
        
        while(!cells.isEmpty()) {//run till all the cells are visited
        	if((double)count*threshold<1.00) {// Make a decision whether to choos a random cell or newest one based on threshold
        		randomCell=cells.get(cells.size()-1);//get newest cell
        	}
        	else {
        		randomCell=chooseRandomCell(cells);//choose random cell
        		count=1;
        	}
        	count++;
        	int flag=1;
        	for(int i=0;i<Maze.NUM_DIR;i++) {//for all the neighbours of the cell
        		if(randomCell.neigh[i]!=null && visited[randomCell.neigh[i].r][randomCell.neigh[i].c]==false) {
        			removeWallBetween(randomCell,randomCell.neigh[i],i,Maze.oppoDir[i]);
        			cells.add(randomCell.neigh[i]);
        			visited[randomCell.neigh[i].r][randomCell.neigh[i].c]=true;
        			flag=0;
        			break;
        		}
        	}
        	if(flag==1) {
        		cells.remove(randomCell);// remove processed cell from the list of cells
        	}
        	
        }

	}

}
