package mazeSolver;

import java.util.ArrayList;
import java.util.Stack;

import maze.Cell;
import maze.Maze;

/**
 * Implements WallFollowerSolver
 */
public class WallFollowerSolver implements MazeSolver {
	ArrayList<Cell>correctPath;//list of cells which lies on the correct path
	int visited[][];//keeps track of the visited cells
	Cell start,end,current;
	Integer length,direction;
	
	/**
	 * Solves a maze using Wall follower algorithm
	 */
	@Override
	public void solveMaze(Maze maze) {
		correctPath = new ArrayList<Cell>();
		
		if(maze.type!=Maze.HEX) {//Initialize all cells as unvisited
			visited = new int[maze.sizeR][maze.sizeC];
		}
		else {//Initialize all cells as unvisited
			visited = new int[maze.sizeR][maze.sizeC+(maze.sizeR+1)/2];
		}
		
		start=maze.entrance;
		end=maze.exit;
		current=start;//Initialize solving from the start cell
		visited[current.r][current.c]=1;//Mark current cell as visited
		correctPath.add(current);//Push current cell into the correct path list
	    length=1;//Initialize the length of path as 1
	    direction=0;//Initialize the direction as zeor
		while(length<=maze.sizeR*maze.sizeC + 2) {
			if(current==end) break;//if control reaches at the end of maze terminate the loop
			ArrayList<Cell>ways = new ArrayList<Cell>();
			ArrayList<Integer>directions = new ArrayList<Integer>();
			int direction=0;
			for(int i=0;i<Maze.NUM_DIR;i++) {//visit all thei neighbours of current
				Cell next = current.neigh[i];
				if(next!=null && current.wall[i].present==false) {//if neighbour is not null and wall is not present
					ways.add(next);//add neighbour to the list of ways
					//If neighbour is on the NORTH set direction of propagation to NORTH as well
					if((current.r+ Maze.deltaR[Maze.NORTH])<maze.sizeR && (current.c+Maze.deltaC[maze.NORTH])<maze.sizeC && (current.r+ Maze.deltaR[Maze.NORTH])>=0 && (current.c+Maze.deltaC[maze.NORTH])>=0 && maze.map[current.r+ Maze.deltaR[Maze.NORTH]][current.c+Maze.deltaC[maze.NORTH]]==next) {
				    	directions.add(Maze.NORTH);
				    }
					//If neighbour is on the NORTHEAST set direction of propagation to NORTHEAST as well
					else if((current.r+ Maze.deltaR[Maze.NORTHEAST])<maze.sizeR && (current.c+Maze.deltaC[maze.NORTHEAST])<maze.sizeC && (current.r+ Maze.deltaR[Maze.NORTHEAST])>=0 && (current.c+Maze.deltaC[maze.NORTHEAST])>=0 && maze.map[current.r+ Maze.deltaR[Maze.NORTHEAST]][current.c+Maze.deltaC[maze.NORTHEAST]]==next) {
				    	directions.add(Maze.NORTHEAST);
				    }
					//If neighbour is on the EAST set direction of propagation to EAST as well
					else if((current.r+ Maze.deltaR[Maze.EAST])<maze.sizeR && (current.c+Maze.deltaC[maze.EAST])<maze.sizeC && (current.r+ Maze.deltaR[Maze.EAST])>=0 && (current.c+Maze.deltaC[maze.EAST])>=0 && maze.map[current.r+ Maze.deltaR[Maze.EAST]][current.c+Maze.deltaC[maze.EAST]]==next) {
				    	directions.add(Maze.EAST);
				    }
					//If neighbour is on the SOUTHEAST set direction of propagation to SOUTHEAST as well
					else if((current.r+ Maze.deltaR[Maze.SOUTHEAST])<maze.sizeR && (current.c+Maze.deltaC[maze.SOUTHEAST])<maze.sizeC && (current.r+ Maze.deltaR[Maze.SOUTHEAST])>=0 && (current.c+Maze.deltaC[maze.SOUTHEAST])>=0 && maze.map[current.r+ Maze.deltaR[Maze.SOUTHEAST]][current.c+Maze.deltaC[maze.SOUTHEAST]]==next) {
				    	directions.add(Maze.SOUTHEAST);
				    }
					//If neighbour is on the SOUTH set direction of propagation to SOUTH as well
					else if((current.r+ Maze.deltaR[Maze.SOUTH])<maze.sizeR && (current.c+Maze.deltaC[maze.SOUTH])<maze.sizeC && (current.r+ Maze.deltaR[Maze.SOUTH])>=0 && (current.c+Maze.deltaC[maze.SOUTH])>=0 && maze.map[current.r+ Maze.deltaR[Maze.SOUTH]][current.c+Maze.deltaC[maze.SOUTH]]==next) {
				    	directions.add(Maze.SOUTH);
				    }
					//If neighbour is on the SOUTHWEST set direction of propagation to SOUTHWEST as well
					else if((current.r+ Maze.deltaR[Maze.SOUTHWEST])<maze.sizeR && (current.c+Maze.deltaC[maze.SOUTHWEST])<maze.sizeC && (current.r+ Maze.deltaR[Maze.SOUTHWEST])>=0 && (current.c+Maze.deltaC[maze.SOUTHWEST])>=0 && maze.map[current.r+ Maze.deltaR[Maze.SOUTHWEST]][current.c+Maze.deltaC[maze.SOUTHWEST]]==next) {
				    	directions.add(Maze.SOUTHWEST);
				    }
					//If neighbour is on the WEST set direction of propagation to WEST as well
					else if((current.r+ Maze.deltaR[Maze.WEST])<maze.sizeR && (current.c+Maze.deltaC[maze.WEST])<maze.sizeC && (current.r+ Maze.deltaR[Maze.WEST])>=0 && (current.c+Maze.deltaC[maze.WEST])>=0 && maze.map[current.r+ Maze.deltaR[Maze.WEST]][current.c+Maze.deltaC[maze.WEST]]==next) {
				    	directions.add(Maze.WEST);
				    }
					//If neighbour is on the NORTHWEST set direction of propagation to NORTHWEST as well
					else if((current.r+ Maze.deltaR[Maze.NORTHWEST])<maze.sizeR && (current.c+Maze.deltaC[maze.NORTHWEST])<maze.sizeC && (current.r+ Maze.deltaR[Maze.NORTHWEST])>=0 && (current.c+Maze.deltaC[maze.NORTHWEST])>=0 && maze.map[current.r+ Maze.deltaR[Maze.NORTHWEST]][current.c+Maze.deltaC[maze.NORTHWEST]]==next) {
				    	directions.add(Maze.NORTHWEST);
				    }
				}
			}
			Cell path=new Cell();
			//If there is only one possible way to go 
			if(ways.size()==1) {
				path = ways.get(0);//move to that way
				direction=directions.get(0);//set direction as your movement
				
			}
			else if(ways.size()==2) {//If there are two possible ways
				if(visited[ways.get(0).r][ways.get(0).c]>0 && visited[ways.get(1).r][ways.get(1).c]>0) {//If both the cells are already visited
					if(directions.get(0)==direction) path=ways.get(0);//Follow the previous direction
					else path=ways.get(1);
				}
				else {
					path= ways.get(0);
					if(visited[path.r][path.c]!=0)//Follow the unvisited path
						path=ways.get(1);
				}
			}
			else {//If more than two options are there
				for(int k=0;k<ways.size();k++) {//follow the unvisited path
					if(visited[ways.get(k).r][ways.get(k).c]==0) {
						path=ways.get(k);
						direction=directions.get(k);
						break;
					}
				}
			}
		    
			visited[path.r][path.c]++;//mark the cell as visited
			current=path;//proceed to the next path cell
			ways.clear();//clear possible ways
			directions.clear();//clear their directions
			length++;//increase length of visited cells
		}
		
		if(maze.type==Maze.HEX) {
			for(int i=0;i<maze.sizeR;i++) {//Those cells which are visited only once are on the correct path
				for(int j = (i + 1) / 2; j < maze.sizeC + (i + 1) / 2; j++) {
					if(visited[i][j]==1) correctPath.add(maze.map[i][j]);//push those cells to correct path list
				}
			}
		}
		else {
			for(int i=0;i<maze.sizeR;i++) {//Those cells which are visited only once are on the correct path
				for(int j=0;j<maze.sizeC;j++) {
					if(visited[i][j]==1) correctPath.add(maze.map[i][j]);//push those cells to correct path list
				}
			}
		}
		
		for(int i=0;i<correctPath.size();i++) {//Draw the correct path
			maze.drawFtPrt(correctPath.get(i));
		}
		// TODO Auto-generated method stub
        
	} // end of solveMaze()
    
    
	@Override
	public boolean isSolved() {
		return current==end;
		// TODO Auto-generated method stub
		//return false;
	} // end if isSolved()
    
    
	@Override
	public int cellsExplored() {
		return length;
		// TODO Auto-generated method stub
		//return 0;
	} // end of cellsExplored()

} // end of class WallFollowerSolver
