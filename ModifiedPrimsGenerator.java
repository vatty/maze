package mazeGenerator;

import java.util.ArrayList;
import maze.Cell;
import maze.Maze;

public class ModifiedPrimsGenerator implements MazeGenerator {
	
	boolean inMaze[][];//keeps track of cells which are inside maze
	
	/**
	 * Removes wall between two cells
	 * @param current
	 * @param neighbour
	 * @param index
	 * @param n_index
	 */
	protected void removeWallBetween(Cell current, Cell neighbour, int index,int n_index) {
		if(current.wall[index]!=null) //if wall is present
			current.wall[index].present=false;//remove wall
		if(neighbour.wall[n_index]!=null) //if wall is present
			neighbour.wall[n_index].present=false;//remove wall
	}
	
	/**
	 * Returns a random integer
	 * @param limit
	 * @return
	 */
	public int getRandomInt(int limit) {
		return (int) (System.currentTimeMillis() % limit);//return random int
	}
	
	/**
	 * Return a random frontier cell
	 * @param frontier
	 * @return
	 */
	public Cell chooseRandomUnvisitedFrontier(ArrayList<Cell>frontier) {
		int n = frontier.size();
		if(n==0) return null;
		int rand = getRandomInt(n);//get random int
		return frontier.get(rand);
	}


	/**
	 * Generates a maze generated by prim's algorithm
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
        ArrayList<Cell> notInMaze = new ArrayList<Cell>();//keeps track of cells not present in the maze
        
        
        
        ArrayList<Cell>frontier = new ArrayList<Cell>();//stores all the frontier cells
        Cell start,finish,current;
        start=entrance;
        finish=exit;
        current=start;
        
        if(type==Maze.HEX) {// add all the cells which are not in maze to the list
        	for(int i=0;i<sizeR;i++) {
            	for(int j = (i + 1) / 2; j < sizeC + (i + 1) / 2; j++) {
            		notInMaze.add(map[i][j]);
            	}
            }
        	
            inMaze = new boolean[sizeR][sizeC+(sizeR+1)/2];
        	
        }
        else {// add all the cells which are not in maze to the list
            for(int i=0;i<sizeR;i++) {
            	for(int j = 0; j < sizeC ; j++) {
            		notInMaze.add(map[i][j]);
            	}
            }
            inMaze = new boolean[sizeR][sizeC];
        	
        }
    	inMaze[current.r][current.c]=true;
        notInMaze.remove(current);
        
        for(int i=0;i<Maze.NUM_DIR;i++) {
        	if(current.neigh[i]!=null && inMaze[current.neigh[i].r][current.neigh[i].c]==false)
        		frontier.add(current.neigh[i]);
        }        
        
        if(current.tunnelTo!=null) {
        	frontier.add(current.tunnelTo);
        }

        while(!notInMaze.isEmpty()) {//run till every cell is inside maze
        	
        	if(frontier.size()==0) break;
        	
			Cell unVisitedFrontier = chooseRandomUnvisitedFrontier(frontier);
			frontier.remove(unVisitedFrontier);

			if(unVisitedFrontier!=null) {//run loop until unvisited frontiers are present
				for(int i=0;i<Maze.NUM_DIR;i++) {
					if(unVisitedFrontier.neigh[i]!=null && inMaze[unVisitedFrontier.neigh[i].r][unVisitedFrontier.neigh[i].c]==true) {
						removeWallBetween(unVisitedFrontier,unVisitedFrontier.neigh[i],i,Maze.oppoDir[i]);
						inMaze[unVisitedFrontier.r][unVisitedFrontier.c]=true;
						notInMaze.remove(unVisitedFrontier);
			
						for(int j=0;j<Maze.NUM_DIR;j++) {
							if( unVisitedFrontier.neigh[j]!=null && frontier.contains(unVisitedFrontier.neigh[j])==false && inMaze[unVisitedFrontier.neigh[j].r][unVisitedFrontier.neigh[j].c]==false ) {
								frontier.add(unVisitedFrontier.neigh[j]);
							}
						}
						break;

					}
				}
			}
        }
        		// TODO Auto-generated method stub

	} // end of generateMaze()

} // end of class ModifiedPrimsGenerator
