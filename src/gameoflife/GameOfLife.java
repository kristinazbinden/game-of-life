/**
 *  Description: Game of Life - Algorithmic game, without user input, featuring a
 *  visual representation of boolean elements in an array.
 *  Author: Kristina Zbinden
 *  Due Date: 11/21/21
 *  Pledged: All work contained here is my own and was created without collaboration.
 *
 */
package gameoflife;

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class GameOfLife extends GraphicsProgram {

    /**
     * Number of villagers
     */
    private static final int NUM_VILLAGERS = 10;
    /**
     * Delay of array representation being printed by milliseconds
     */
    private static final int DELAY = 100;
    /**
     * Width of the game display (all coordinates are in pixels)
     */
    private static final int WIDTH = 800;
    /**
     * Height of the game display
     */
    private static final int HEIGHT = 800;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String[] sizeArgs = {"width=" + WIDTH, "height=" + HEIGHT};
        new GameOfLife().start(sizeArgs);
    }

    //Fill houses with villagers, all of which are dead except for the middle house
    public void calcVillagers(boolean[] villagers) {
        int i;
        int middleHouse = NUM_VILLAGERS / 2;

        for (i = 0; i < NUM_VILLAGERS; ++i) {
            if (i == middleHouse) {
                villagers[i] = true;
            } else {
                villagers[i] = false;
            }
        }
    }

    //Update array elements upon each call
    public void runGeneration(boolean[] villagers) {
        //Create mirror array of villagers[] in order to circularly compare adjacent elements
        boolean[] checkVillagers = new boolean[villagers.length + 2];
        checkVillagers[0] = villagers[villagers.length - 1];
        checkVillagers[checkVillagers.length - 1] = villagers[0];
        for (int i = 1; i < villagers.length; ++i) {
            checkVillagers[i] = villagers[i - 1];
        }

        //Run through villagers[]
        for (int i = 0; i < villagers.length; ++i) {
            //Check mirror elements of villagers[] in checkVillagers[]
            boolean curr = checkVillagers[i + 1];
            boolean left = checkVillagers[i];
            boolean right = checkVillagers[i + 2];

            //Algorithm to check and update villagers[]
            if (!curr && ((left && !right) || (!left && right))) {
                villagers[i] = true;
            } else if (curr && ((left && right) || (!left && !right))) {
                villagers[i] = true;
            } else {
                villagers[i] = false;
            }

        }
    }

    //Draw a row of houses representing live and dead villagers, with the yCoord updating upon each runGeneration method call
    public void drawHouses(boolean[] villagers, int yCoord) {
        int houseLength = WIDTH / NUM_VILLAGERS;

        for (int i = 0; i < NUM_VILLAGERS; i++) {
            int x = i * (houseLength);
            int y = WIDTH / NUM_VILLAGERS * yCoord;

            //GRect arguements: x, y, width, height
            GRect house = new GRect(x, y, houseLength, houseLength);
            //Grey objects represent dead villagers, and yellow represent live
            if (villagers[i] == false) {
                house.setFillColor(Color.GRAY);
            } else {
                house.setFillColor(Color.YELLOW);
            }

            house.setColor(Color.YELLOW);
            house.setFilled(true);
            add(house);
            pause(DELAY);

        }

    }

    /**
     * Called by the system once the app is started.
     */
    @Override
    public void run() {
        boolean[] villagers = new boolean[NUM_VILLAGERS];
        //Fill array with villagers
        calcVillagers(villagers);
        //Call method to print representation of each generation to screen then advance to next generation
        for (int i = 0; i < NUM_VILLAGERS; ++i) {
            drawHouses(villagers, i);
            runGeneration(villagers);
        }
    }
}
