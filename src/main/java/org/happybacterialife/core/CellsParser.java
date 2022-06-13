package org.happybacterialife.core;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

/**
 * Allow you to parse a file and initialize a grid/retrieve the number of iterations from it
 * 
 * @author dom
 *
 */
public class CellsParser {

	private boolean [][] cellsGrid;
	private int nbIterations; 
	
	public void parse (File initializationFile) throws Exception {
		if ( initializationFile == null ||
				!initializationFile.exists() ||
				initializationFile.isDirectory() ) {
			throw new Exception ( "You have to use an existing file" );
		}
		
		try {
			List<String> lines = FileUtils.readLines(initializationFile);
		
			if (lines.size() == 0) {
				throw new Exception ("the initialization file have to contains, at least, the informations of the grid (x, y, iteration count)");
			}
			
			lines.stream().forEach( line -> {
				if (cellsGrid == null) {
					//This is the first line, so, we have to instantiate the grid and to initialize the number of iteration(s)
					if (!Pattern.matches("[0-9]+ [0-9]+ [0-9]+", line)) {
						throw new RuntimeException ( "Wrong file format ( the first line have to contain three integers ( x, y, iteration count ) separated by one space )" );
					}

					List<Integer> gridInfos = Stream.of(line.split(" ")).map(item -> Integer.valueOf(item)).collect(Collectors.toList());
					
					cellsGrid = new boolean[ gridInfos.get(0) ][ gridInfos.get(1) ];
					nbIterations = gridInfos.get( 2 );
				} else {
					//We have to check the format and coherence regarding the size of the grid
					if (!Pattern.matches("[0-9]+ [0-9]+", line)) {
						throw new RuntimeException ( "Wrong file format ( a coordinate line have to contain two integers ( x, y ) separated by one space )" );
					}

					List<Integer> aliveCellInfos = Stream.of(line.split(" ")).map(item -> Integer.valueOf(item)).collect(Collectors.toList());
					if (aliveCellInfos.get(0) >= cellsGrid.length) {
						throw new RuntimeException ( "Wrong file format ( the first coordinate (x) have to be less than the x axis length of the grid )" );
					}
					
					if (aliveCellInfos.get(1) >= cellsGrid[0].length) {
						throw new RuntimeException ( "Wrong file format ( the second coordinate (y) have to be less than the y axis length of the grid )" );
					}
					
					cellsGrid[ aliveCellInfos.get(0) ][ aliveCellInfos.get(1) ] = true;
				}
			});
		} catch ( IOException ioe ) {
			throw new Exception ( "An error has occured during file reading", ioe );
		}
	}

	public boolean [][] getCellsGrid() {
		return cellsGrid;
	}

	public int getNbIterations() {
		return nbIterations;
	}
	
}
