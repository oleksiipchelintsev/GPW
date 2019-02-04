package com.gpw.recruitment.util;

import com.gpw.recruitment.common.MazeBoard;
import com.gpw.recruitment.model.CellPosition;
import com.gpw.recruitment.model.CellType;
import com.gpw.recruitment.model.MazeCell;
import com.gpw.recruitment.model.Movement;
import com.gpw.recruitment.model.VisitStatus;

import java.util.ArrayList;
import java.util.List;

public class MazeBoardUtil {

    private MazeBoard mazeBoard;

    public MazeBoardUtil(final MazeBoard mazeBoard) {
        this.mazeBoard = mazeBoard;
    }

    public boolean checkCellLocationValidity(final MazeCell cell) {
        if (cell.cellCoordinate().x() > this.mazeBoard.getWidth() || cell.cellCoordinate().x() < 0) {
            return false;
        }
        return cell.cellCoordinate().y() <= this.mazeBoard.getHeight() && cell.cellCoordinate().y() >= 0;
    }

    public boolean hasItBeenAlreadyVisited(final MazeCell cell) {
        return this.mazeBoard.getVisitStatus()[cell.cellCoordinate().x()][cell.cellCoordinate().y()] == VisitStatus.VISITED;
    }

    public CellType findCellType(final MazeCell cell) {
        return this.mazeBoard.getMazeCell()[cell.cellCoordinate().x()][cell.cellCoordinate().y()];
    }

    public List<MazeCell> findBackTrackedPath(final MazeCell cell) {
        List<MazeCell> backTrackedPath = new ArrayList<>();
        MazeCell currentCell = cell;
        while (currentCell.parentCell() != null) {
            backTrackedPath.add(currentCell);
            currentCell = currentCell.parentCell();
        }
        return backTrackedPath;
    }

    public MazeCell findLocationAfterMovement(MazeCell currentCell, Movement movement) {
        CellPosition coordinate = currentCell.cellCoordinate();
        CellPosition newCoordinate = new CellPosition(coordinate.x(), coordinate.y());
        switch (movement) {
            case NORTH:
                newCoordinate = new CellPosition(coordinate.x(), coordinate.y() - 1);
                break;
            case SOUTH:
                newCoordinate = new CellPosition(coordinate.x(), coordinate.y() + 1);
                break;
            case WEST:
                newCoordinate = new CellPosition(coordinate.x() - 1, coordinate.y());
                break;
            case EAST:
                newCoordinate = new CellPosition(coordinate.x() + 1, coordinate.y());
                break;
            default:
                break;
        }
        return new MazeCell(currentCell, newCoordinate);
    }
}

