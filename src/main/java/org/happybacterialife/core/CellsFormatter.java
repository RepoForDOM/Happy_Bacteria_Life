package org.happybacterialife.core;

import java.io.File;

import org.apache.commons.io.FileUtils;

/**
 * Allow you to format a grid into differents formats
 * 
 * @author dom
 *
 */
public class CellsFormatter {

	/**
	 * Format the grid as a file
	 * 
	 * @param cellsGrid
	 * @param targetDirectory
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static void formatAsFile (boolean [][] cellsGrid, File targetFile) throws Exception {
		if (targetFile == null ||
				targetFile.exists()) {
			throw new Exception ( "Wrong destination file path" );
		}
		
		StringBuilder builder = new StringBuilder ();
		
		if (cellsGrid.length > 0 &&
				cellsGrid[0].length > 0) {
			builder.append(cellsGrid.length + " " + cellsGrid[0].length + "\n");
		
			for(int x = 0;x < cellsGrid.length;x++) {
				for(int y = 0;y < cellsGrid[x].length; y++) {
					if(cellsGrid[x][y]) {
						builder.append(x + " " + y + "\n");
					}
				}
			}
		}
		
		try {
			FileUtils.write(targetFile, builder);
		} catch ( Exception e ) {
			throw new Exception ( "An error has occured during writing of target file", e );
		}
	}
	
}
