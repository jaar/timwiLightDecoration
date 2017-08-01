package com.jarhan.timwi.lightDecoration;

/**
 * Class representing the coordinate of a square of ligths
 * 
 * @author jarhan
 *
 */
public class Coordinate {

    /**
     * the start line index of the square
     */
    private int startingLineIndex;

    /**
     * the start column index of the square
     */
    private int startingColumnIndex;

    /**
     * the end line index of the square
     */
    private int endingLineIndex;
    
    /**
     * the end column index of the square
     */
    private int endingColumnIndex;

    /**
     * @return the startingLineIndex
     */
    public int getStartingLineIndex() {
        return startingLineIndex;
    }

    /**
     * @param startingLineIndex the startingLineIndex to set
     */
    public void setStartingLineIndex(int startingLineIndex) {
        this.startingLineIndex = startingLineIndex;
    }

    /**
     * @return the startingColumnIndex
     */
    public int getStartingColumnIndex() {
        return startingColumnIndex;
    }

    /**
     * @param startingColumnIndex the startingColumnIndex to set
     */
    public void setStartingColumnIndex(int startingColumnIndex) {
        this.startingColumnIndex = startingColumnIndex;
    }

    /**
     * @return the endingLineIndex
     */
    public int getEndingLineIndex() {
        return endingLineIndex;
    }

    /**
     * @param endingLineIndex the endingLineIndex to set
     */
    public void setEndingLineIndex(int endingLineIndex) {
        this.endingLineIndex = endingLineIndex;
    }

    /**
     * @return the endingColumnIndex
     */
    public int getEndingColumnIndex() {
        return endingColumnIndex;
    }

    /**
     * @param endingColumnIndex the endingColumnIndex to set
     */
    public void setEndingColumnIndex(int endingColumnIndex) {
        this.endingColumnIndex = endingColumnIndex;
    }

}
