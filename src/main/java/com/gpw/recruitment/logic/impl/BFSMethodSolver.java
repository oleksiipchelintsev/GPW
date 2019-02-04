package com.gpw.recruitment.logic.impl;

import com.gpw.recruitment.common.MazeBoard;
import com.gpw.recruitment.exception.MazeCustomSolverServiceException;
import com.gpw.recruitment.logic.MazeSolver;
import com.gpw.recruitment.model.CellType;
import com.gpw.recruitment.model.MazeCell;
import com.gpw.recruitment.model.Movement;
import com.gpw.recruitment.model.VisitStatus;
import com.gpw.recruitment.util.MazeBoardUtil;
import com.gpw.recruitment.util.UtilMath;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class BFSMethodSolver implements MazeSolver {
    private MazeBoardUtil mazeBoardUtil;
    private MazeBoard mazeBoard;

    public BFSMethodSolver(final MazeBoard mazeBoard, final MazeBoardUtil mazeBoardUtil) {
        this.mazeBoard = mazeBoard;
        this.mazeBoardUtil = mazeBoardUtil;
    }


    @Override
    public LinkedList<MazeCell> solveAndFindRoute() throws MazeCustomSolverServiceException {
        LinkedList<LinkedList<MazeCell>> paths = new LinkedList<>();
        final MazeCell startPoint = mazeBoard.getStartCell();
        LinkedList<MazeCell> cellTobeVisited = new LinkedList<>();
        //Starting the journey
        cellTobeVisited.add(startPoint);
        while (!cellTobeVisited.isEmpty()) {
            final MazeCell currentCell = cellTobeVisited.remove();

            if (!mazeBoardUtil.checkCellLocationValidity(currentCell)) {
                continue;
            }
            if (mazeBoardUtil.hasItBeenAlreadyVisited(currentCell)) { //if the vertex is not visited in current path
                continue;
            } else {
            }
            CellType currentCellType = mazeBoardUtil.findCellType(currentCell);
            if (currentCellType == CellType.WALL) {
                this.mazeBoard.markCellVisitStatus(currentCell.cellCoordinate(), VisitStatus.VISITED);
                continue;
            }

            if (currentCellType == CellType.FINISH) {
                LinkedList<MazeCell> cellList = new LinkedList<>();
                cellList.addAll(mazeBoardUtil.findBackTrackedPath(currentCell));
                paths.add(cellList);

                for (int i = 0; i < cellTobeVisited.size(); ) {
                    if ((cellTobeVisited.get(i).parentCell().equals(currentCell.parentCell()))) {
                        cellTobeVisited.remove(i);
                    } else {
                        i++;
                    }
                }
                mazeBoard.markCellVisitStatus(currentCell.parentCell().cellCoordinate(), VisitStatus.NOT_VISITED);
                continue;
            }
            // Check possible routes from current location
            for (Movement movement : Movement.values()) {
                MazeCell nextCell = mazeBoardUtil.findLocationAfterMovement(currentCell, movement);
                cellTobeVisited.add(nextCell);
                mazeBoard.markCellVisitStatus(currentCell.cellCoordinate(), VisitStatus.VISITED);
            }

        }
        taskOneInfo(paths);
        if (paths.size() > 0) {
            return paths.get(0);        //return fasters one
        }


        throw new MazeCustomSolverServiceException("Can not find a route from start to finish");
    }

    @Override
    public String printMazePath(final List<MazeCell> path) {
        CellType[][] clonedMazeBoard = Arrays.stream(this.mazeBoard.getMazeCell()).map(cellTypes -> Arrays.copyOf(cellTypes, cellTypes.length)).
                toArray(CellType[][]::new);     //Alex I do not understand it????

        for (MazeCell mazeCell : path) {
            CellType cellType = this.mazeBoardUtil.findCellType(mazeCell);
            if (cellType == CellType.START || cellType == CellType.FINISH) {
                continue;
            }
            clonedMazeBoard[mazeCell.cellCoordinate().x()][mazeCell.cellCoordinate().y()] = CellType.ROUTE;
            // System.out.println(/*"Path coordinates are: "*/ "("+  mazeCell.cellCoordinate().x() +";"+ mazeCell.cellCoordinate().y()+")");
        }
        StringJoiner displayText = new StringJoiner("");
        for (int rowCount = 0; rowCount < this.mazeBoard.getWidth(); rowCount++) {
            for (int colCount = 0; colCount < this.mazeBoard.getHeight(); colCount++) {
                CellType cellType = clonedMazeBoard[rowCount][colCount];
                switch (cellType) {
                    case START:
                        displayText.add(CellType.START.getCharacterType() + "");
                        break;
                    case FINISH:
                        displayText.add(CellType.FINISH.getCharacterType() + "");
                        break;
                    case EMPTY:
                        displayText.add(CellType.EMPTY.getCharacterType() + "");
                        break;
                    case ROUTE:
                        displayText.add(CellType.ROUTE.getCharacterType() + "");
                        break;
                    case WALL:
                        displayText.add(CellType.WALL.getCharacterType() + "");
                        break;
                    default:
                        break;
                }
            }
            displayText.add("\r\n");

        }
        return displayText.toString();
    }

    @Override
    public String printFullMaze() {
        CellType[][] clonedMazeBoard = Arrays.stream(this.mazeBoard.getMazeCell()).map(cellTypes -> Arrays.copyOf(cellTypes, cellTypes.length)).
                toArray(CellType[][]::new);     //Alex I do not understand it????

        StringJoiner displayText = new StringJoiner("");
        for (int rowCount = 0; rowCount < this.mazeBoard.getWidth(); rowCount++) {
            for (int colCount = 0; colCount < this.mazeBoard.getHeight(); colCount++) {
                CellType cellType = clonedMazeBoard[rowCount][colCount];
                switch (cellType) {
                    case START:
                        displayText.add(CellType.START.getCharacterType() + "");
                        break;
                    case FINISH:
                        displayText.add(CellType.FINISH.getCharacterType() + "");
                        break;
                    case EMPTY:
                        displayText.add(CellType.EMPTY.getCharacterType() + "");
                        break;
                    case ROUTE:
                        displayText.add(CellType.ROUTE.getCharacterType() + "");
                        break;
                    case WALL:
                        displayText.add(CellType.WALL.getCharacterType() + "");
                        break;
                    default:
                        break;
                }
            }
            displayText.add("\r\n");

        }
        return displayText.toString();
    }


    /**
     * Prints path with the smallest amount of corners
     *
     * @param paths - all finded paths in Maze
     *              If we have all rotes we can answer for the question how many
     *              corners do we have
     *              @see UtilMath#getCorners
     */

    public void taskOneInfo(LinkedList<LinkedList<MazeCell>> paths) {
        UtilMath utilMath = new UtilMath();
        HashMap<LinkedList<MazeCell>, Integer> pathCorners = new HashMap<>();
        for (LinkedList<MazeCell> currPath : paths) {
            pathCorners.put(currPath, utilMath.getCorners(currPath, mazeBoard));
        }

        Map<LinkedList<MazeCell>, Integer> sortedByCorners = pathCorners.entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                        LinkedHashMap::new));

        List<MazeCell> shortestPath = (List<MazeCell>) sortedByCorners.keySet().toArray()[0];
        int corners = sortedByCorners.get(shortestPath);

        System.out.println("Answer for our question (Task 1): ");
        System.out.println("Corners: " + corners);
        System.out.println(printMazePath((List<MazeCell>) sortedByCorners.keySet().toArray()[0]));
    }
}
