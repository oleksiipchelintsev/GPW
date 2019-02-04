package com.gpw.recruitment.util;


import com.gpw.recruitment.common.MazeBoard;
import com.gpw.recruitment.model.CellType;
import com.gpw.recruitment.model.MazeCell;

import java.util.ArrayList;
import java.util.List;

public class UtilMath {

    /**
     * Returns number of corners in current path applied to mazeBoard
     * It is my own approach for solwing problem based on vectors
     * understanding
     * (<a href="https://en.wikipedia.org/wiki/Dot_product#Geometric_definition</a>)
     * Basicaly if multiplication of two vectors equals 0, we have deal with corner
     *
     * @param path      our finded path
     * @param mazeBoard our Maze (Matrix) for getting type of current cell in path
     * @return number of corners in the path
     */
    public int getCorners(List<MazeCell> path, MazeBoard mazeBoard) {
        List<MazeCell> mazeCellList = new ArrayList<>();
        for (int i = 0; i < path.size(); i++) {                             //Create more points for calculating vectors
            CellType cellType = findCellType(path.get(i), mazeBoard);
            if (cellType == CellType.START || cellType == CellType.FINISH) {
                continue;
            }

            if (i > 1 && i < path.size() - 1) {
                mazeCellList.add(path.get(i));
                mazeCellList.add(path.get(i));
            } else {
                mazeCellList.add(path.get(i));
            }
        }

        List<LinearVector> vectorList = new ArrayList<>();
        try {

            for (int i = 0; i < mazeCellList.size(); i += 2) {              //Create all vectors based on points
                LinearVector linearVector = new LinearVector();
                linearVector.a = mazeCellList.get(i + 1).cellCoordinate().x() - mazeCellList.get(i).cellCoordinate().x();
                linearVector.b = mazeCellList.get(i + 1).cellCoordinate().y() - mazeCellList.get(i).cellCoordinate().y();
                vectorList.add(linearVector);
            }

        } catch (IndexOutOfBoundsException ex) {

        }

        int corners=0;
        for(int i=0; i<vectorList.size()-1; i++){
            if((vectorList.get(i).a*vectorList.get(i+1).a + vectorList.get(i).b*vectorList.get(i+1).b) == 0){
                corners++;
            }
        }
        return corners;
    }

    public CellType findCellType(final MazeCell cell, MazeBoard mazeBoard) {
        return mazeBoard.getMazeCell()[cell.cellCoordinate().x()][cell.cellCoordinate().y()];
    }

    class LinearVector {                //Temporary data structure for calculating vector
        int a;
        int b;

        @Override
        public String toString() {
            return "LinearVector{" +
                    "a=" + a +
                    ", b=" + b +
                    '}';
        }
    }
}

