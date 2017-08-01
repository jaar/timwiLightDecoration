package com.jarhan.timwi.lightDecoration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Application entry point
 *
 */
public class App {

    /**
     * logger
     */
    private static final Logger LOGGER = LogManager.getLogger(App.class);

    /**
     * size of the square of lights
     */
    private static int SQUARE_LENGTH = 1000;

    
    /**
     * Constants for reading the file
     */
    private static String TOGGLE_INSTRUCTION = "toggle";
    private static String TURN_ON_INSTRUCTION = "turn on";
    private static String TURN_OFF_INSTRUCTION = "turn off";
    private static String THROUGH_INSTRUCTION = " through ";
    private static String COORDINATE_SEPARATOR = ",";

    /**
     * enum for the instruction
     * @author jarhan
     *
     */
    private static enum Instruction {
        TURN_ON, TURN_OFF, TOGGLE;
    }

    /**
     * entry point
     * @param args aruments, args[0] = path to the file of instructions
     */
    public static void main(String[] args) {

        // the array that contains the states of the lights
        boolean lights[][] = new boolean[SQUARE_LENGTH][SQUARE_LENGTH];
        Instruction instruction = null;
        if(args.length != 1){
            LOGGER.error("you must specify a file of instructions as first argument");
            System.exit(-1);
        }
        File instructionFile = new File(args[0]);
        try (BufferedReader reader = new BufferedReader(new FileReader(instructionFile))){
            String line = null;
            int lineNumber = 0;
            // read the line
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String coordinates = null;
                if (StringUtils.startsWith(line, TOGGLE_INSTRUCTION)) {
                    instruction = Instruction.TOGGLE;
                    coordinates = StringUtils.removeStart(line, TOGGLE_INSTRUCTION);
                } else if (StringUtils.startsWith(line, TURN_ON_INSTRUCTION)) {
                    instruction = Instruction.TURN_ON;
                    coordinates = StringUtils.removeStart(line, TURN_ON_INSTRUCTION);
                } else if (StringUtils.startsWith(line, TURN_OFF_INSTRUCTION)) {
                    instruction = Instruction.TURN_OFF;
                    coordinates = StringUtils.removeStart(line, TURN_OFF_INSTRUCTION);
                } else {
                    // unknown instruction, logging and proceeding to next line
                    LOGGER.warn("unknown instruction on line " + lineNumber + " -- " + line);
                    continue;
                }

                Coordinate coordinate = null;
                try {
                    coordinate = createCoordinate(coordinates);
                } catch (Exception e) {
                    LOGGER.warn("error on line " +  lineNumber + " -- " + line, e);
                    continue;
                }
                // TODO handle case when startingIndex > endingIndex

                // switching the lights
                for (int i = coordinate.getStartingLineIndex(); i <= coordinate.getEndingLineIndex(); i++) {
                    for (int j = coordinate.getStartingColumnIndex(); j <= coordinate.getEndingColumnIndex(); j++) {
                        lights[i][j] = switchLights(instruction, lights[i][j]);
                    }
                }

            }

            // counting the lights
            int litLights = 0;
            for (int i = 0; i < SQUARE_LENGTH; i++) {
                for (int j = 0; j < SQUARE_LENGTH; j++) {
                    if(lights[i][j]){
                        litLights++;
                    }
                }
            }
            
            System.out.println("There are " + litLights + " lights lit");

        } catch (IOException e) {
            LOGGER.error("error reading the file", e);
        }
    }

    /**
     * Change the value of the boolean depending on the instruction
     * 
     * @param instruction
     *            the instruction
     * @param oldValue
     *            the old value of the boolean
     * @return the new value of the boolean
     */
    private static boolean switchLights(Instruction instruction, boolean oldValue) {
        boolean returnValue = false;
        switch (instruction) {
        case TURN_ON:
            returnValue = true;
            break;
        case TURN_OFF:
            returnValue = false;
            break;
        case TOGGLE:
            returnValue = !oldValue;
            break;
        default:
            break;
        }
        return returnValue;
    }

    /**
     * Create a {@link Coordinate} from the string of coordinates
     * 
     * @param coordinates
     *            the string of coordinates
     * @return the coordinates as {@link Coordinate} object
     */
    private static Coordinate createCoordinate(String coordinates) throws Exception {
        Coordinate coordinate = new Coordinate();
        String[] coordinatesArray = StringUtils.splitByWholeSeparator(coordinates, THROUGH_INSTRUCTION);
        // test if we have starting and ending coordinates
        testCoordinatesArray(coordinatesArray);

        String[] startingCoordinates = StringUtils.split(StringUtils.strip(coordinatesArray[0]), COORDINATE_SEPARATOR);
        // test if we have column and line coordinates
        testCoordinatesArray(startingCoordinates);
        coordinate.setStartingColumnIndex(Integer.valueOf(startingCoordinates[0]));
        coordinate.setStartingLineIndex(Integer.valueOf(startingCoordinates[1]));

        String[] endingCoordinates = StringUtils.split(StringUtils.strip(coordinatesArray[1]), COORDINATE_SEPARATOR);
        // test if we have column and line coordinates
        testCoordinatesArray(endingCoordinates);
        coordinate.setEndingColumnIndex(Integer.valueOf(endingCoordinates[0]));
        coordinate.setEndingLineIndex(Integer.valueOf(endingCoordinates[1]));

        return coordinate;
    }

    /**
     * test if the array is not null and has a size of 2.<br>
     * Throws en exception if not
     * 
     * @param coordinatesArray
     *            the array to test
     * @throws Exception
     *             the exception
     */
    private static void testCoordinatesArray(String[] coordinatesArray) throws Exception {
        if (coordinatesArray == null || coordinatesArray.length != 2) {
            throw new Exception("error when parsing coordinates");
        }
    }
}
