package org.happybacterialife.core;

/**
 * Allow you to compute some iterations from an initial grid
 * 
 * @author dom
 *
 */
public class CellsReactor {

	private boolean [][] cellsGrid;
	private int iteration;
	
	public CellsReactor (boolean [][] cellsGrid) {
		this.cellsGrid = cellsGrid;
	}
	
	public void iterate (int nbIterations) throws Exception {
		if (cellsGrid == null) {
			throw new Exception ( "A grid must be defined" );
		}
		
		System.out.println("Initial state : \n" + this);
		
		internalIterate(nbIterations);
	}
	
	private void internalIterate(int nbIterations) throws Exception {
		if (nbIterations != 0) {
			iteration++;
			
			boolean [][] nextCellsGrid = new boolean [cellsGrid.length][cellsGrid[0].length];
			
			for(int x = 0; x < cellsGrid.length; x++) {
				for(int y = 0; y < cellsGrid[x].length; y++) {
					int aliveCount = countAliveNeighbours(x, y);
					
					if (cellsGrid[x][y]) {
						if (aliveCount < 2 ||
								aliveCount > 3) {
							nextCellsGrid[x][y] = false;
						} else {
							nextCellsGrid[x][y] = true;
						}
					} else {
						if (aliveCount == 3) {
							nextCellsGrid[x][y] = true;
						} else {
							nextCellsGrid[x][y] = false;
						}
					}
				}
			}
			
			cellsGrid = nextCellsGrid;
			
			System.out.println( "State after iteration " + iteration + " : \n" + this );
			
			internalIterate(--nbIterations);
		}
	}
	
	private int countAliveNeighbours(int x, int y) {
		int nbNeightbours = 0;
		
		if (x > 0) {
			//We check inside the previous line
			if (cellsGrid[x-1][y]) {
				nbNeightbours++;
			}
			
			if (y > 0 &&
					cellsGrid[x-1][y-1]) {
				nbNeightbours++;
			}
			
			if (y < cellsGrid[x-1].length - 1 &&
					cellsGrid[x-1][y+1]) {
				nbNeightbours++;
			}
		}
		
		//We check in the current line
		if (y > 0 &&
				cellsGrid[x][y-1]) {
			nbNeightbours++;
		}
		
		if (y < cellsGrid[x].length - 1 &&
				cellsGrid[x][y+1]) {
			nbNeightbours++;
		}
		
		if (x < cellsGrid.length - 1) {
			//We check inside the next line
			if (cellsGrid[x+1][y]) {
				nbNeightbours++;
			}
			
			if (y > 0 &&
					cellsGrid[x+1][y-1]) {
				nbNeightbours++;
			}
			
			if (y < cellsGrid[x+1].length - 1 &&
					cellsGrid[x+1][y+1]) {
				nbNeightbours++;
			}
		}
		
		return nbNeightbours;
	}
	
	@Override
	public String toString () {
		StringBuilder builder = new StringBuilder();
		
		//in the grid, x correspond to the first dimension of the array and y the second
		//So, we have to iterate on y before x
		for(int y = 0;y < cellsGrid[0].length;y++) {
			if (y != 0) {
				builder.append("\n\r");
			}
			
			for(int x = 0;x < cellsGrid.length; x++) {
				if( cellsGrid[x][y] ) {
					builder.append( "#" );
				} else {
					builder.append( "." );
				}
			}
		}
		
		return builder.toString();
	}

	public boolean [][] getCellsGrid() {
		return cellsGrid;
	}

}
