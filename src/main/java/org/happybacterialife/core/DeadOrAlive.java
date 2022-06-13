package org.happybacterialife.core;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class DeadOrAlive {
	
	public static void main ( String [] args ) {
		CommandLineParser parser = new DefaultParser(false);
		HelpFormatter helpFormatter = new HelpFormatter();
		
		Options options = new Options();
		
		Option option = new Option ("i", "input", true, "The path to the init file");
		option.setRequired(true);
		options.addOption(option);
		
		option = new Option("o", "output", true, "The destination file name");
		option.setRequired(false);
		options.addOption(option);
		
		try {
			CommandLine commandLine = parser.parse(options, args);
			
			CellsParser cellsParser = new CellsParser ();
			cellsParser.parse(new File (commandLine.getOptionValue("i")));
			
			CellsReactor cellsReactor = new CellsReactor (cellsParser.getCellsGrid());
			
			cellsReactor.iterate(cellsParser.getNbIterations());
			
			if (commandLine.getOptionValue("o") != null) {
				CellsFormatter.formatAsFile(cellsReactor.getCellsGrid(), new File (commandLine.getOptionValue("o")));
			}
		} catch ( ParseException pe ) {
			helpFormatter.printHelp("java -jar DeadOrAlive.jar -i input_file [-o output_file]",options);
		} catch ( Exception e ) {
			System.out.println(e);
		}
	}
	
}
