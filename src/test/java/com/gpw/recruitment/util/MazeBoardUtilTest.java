package com.gpw.recruitment.util;

import com.gpw.recruitment.common.MazeBoard;
import com.gpw.recruitment.model.CellPosition;
import com.gpw.recruitment.model.CellType;
import com.gpw.recruitment.model.MazeCell;
import com.gpw.recruitment.model.Movement;
import com.gpw.recruitment.model.VisitStatus;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class MazeBoardUtilTest {

    MazeBoardUtil classTobeTested;
    @Mock
    private MazeBoard mazeBoard;

    @BeforeEach
    void setUp(){
        classTobeTested = new MazeBoardUtil(mazeBoard);
    }

    @Test
    public void checkWidthBoundaryConditions() {
        when(mazeBoard.getWidth()).thenReturn(4);
        MazeCell mazeCell = mock(MazeCell.class);
        CellPosition cellCoordinate = mock(CellPosition.class);
        when(mazeCell.cellCoordinate()).thenReturn(cellCoordinate);

        when(cellCoordinate.x()).thenReturn(-1);
        assertFalse(classTobeTested.checkCellLocationValidity(mazeCell));

        when(cellCoordinate.x()).thenReturn(8);
        assertFalse(classTobeTested.checkCellLocationValidity(mazeCell));
    }

    @Test
    void checkHeightBoundaryConditions() {
        when(mazeBoard.getWidth()).thenReturn(4);
        when(mazeBoard.getHeight()).thenReturn(5);
        MazeCell mazeCell = mock(MazeCell.class);
        CellPosition cellCoordinate = mock(CellPosition.class);
        when(cellCoordinate.x()).thenReturn(3);
        when(mazeCell.cellCoordinate()).thenReturn(cellCoordinate);

        when(cellCoordinate.y()).thenReturn(-1);
        assertFalse(classTobeTested.checkCellLocationValidity(mazeCell));

        when(cellCoordinate.y()).thenReturn(8);
        assertFalse(classTobeTested.checkCellLocationValidity(mazeCell));

        when(cellCoordinate.y()).thenReturn(0);
        assertTrue(classTobeTested.checkCellLocationValidity(mazeCell));
    }

    @Test
    void hasItBeenAlreadyVisited() {
        VisitStatus[][] visitStatusArray = new VisitStatus[1][1];
        visitStatusArray[0][0] = VisitStatus.VISITED;
        when(mazeBoard.getVisitStatus()).thenReturn(visitStatusArray);
        MazeCell mazeCell = mock(MazeCell.class);
        CellPosition cellCoordinate = mock(CellPosition.class);
        when(cellCoordinate.x()).thenReturn(0);
        when(cellCoordinate.y()).thenReturn(0);
        when(mazeCell.cellCoordinate()).thenReturn(cellCoordinate);

        assertTrue(classTobeTested.hasItBeenAlreadyVisited(mazeCell));

        visitStatusArray[0][0] = VisitStatus.NOT_VISITED;
        assertFalse(classTobeTested.hasItBeenAlreadyVisited(mazeCell));
    }

    @Test
    void findCellType() {
        CellType[][] cells = new CellType[2][2];
        cells[0][0] = CellType.START;
        cells[0][1] = CellType.WALL;
        cells[1][0] = CellType.FINISH;
        cells[1][1] = CellType.EMPTY;
        when(mazeBoard.getMazeCell()).thenReturn(cells);
        MazeCell mazeCell = mock(MazeCell.class);
        CellPosition cellCoordinate = mock(CellPosition.class);
        when(cellCoordinate.x()).thenReturn(0);
        when(cellCoordinate.y()).thenReturn(0);
        when(mazeCell.cellCoordinate()).thenReturn(cellCoordinate);

        Assertions.assertEquals(CellType.START, classTobeTested.findCellType(mazeCell));

        when(cellCoordinate.x()).thenReturn(0);
        when(cellCoordinate.y()).thenReturn(1);
        Assertions.assertEquals(CellType.WALL, classTobeTested.findCellType(mazeCell));

        when(cellCoordinate.x()).thenReturn(1);
        when(cellCoordinate.y()).thenReturn(0);
        Assertions.assertEquals(CellType.FINISH, classTobeTested.findCellType(mazeCell));

        when(cellCoordinate.x()).thenReturn(1);
        when(cellCoordinate.y()).thenReturn(1);
        Assertions.assertEquals(CellType.EMPTY, classTobeTested.findCellType(mazeCell));
    }

    @Test
    void findBackTrackedPath() {
        MazeCell mazeCell = mock(MazeCell.class);
        MazeCell parentCell = mock(MazeCell.class);
        MazeCell grandParentCell = mock(MazeCell.class);
        when(mazeCell.parentCell()).thenReturn(parentCell);
        when(parentCell.parentCell()).thenReturn(grandParentCell);
        when(grandParentCell.parentCell()).thenReturn(null);
        List<MazeCell> mazeCells = classTobeTested.findBackTrackedPath(mazeCell);
        Assertions.assertEquals(2, mazeCells.size());
    }

    @Test
    void findLocationAfterMovement() {
        MazeCell mazeCell = mock(MazeCell.class);
        CellPosition cellCoordinate = mock(CellPosition.class);
        when(cellCoordinate.x()).thenReturn(1);
        when(cellCoordinate.y()).thenReturn(1);
        when(mazeCell.cellCoordinate()).thenReturn(cellCoordinate);

        MazeCell movedCell = classTobeTested.findLocationAfterMovement(mazeCell, Movement.NORTH);
        Assertions.assertEquals(mazeCell, movedCell.parentCell());
        Assertions.assertEquals(1, movedCell.cellCoordinate().x());
        Assertions.assertEquals(0, movedCell.cellCoordinate().y());

        movedCell = classTobeTested.findLocationAfterMovement(mazeCell, Movement.SOUTH);
        Assertions.assertEquals(1, movedCell.cellCoordinate().x());
        Assertions.assertEquals(2, movedCell.cellCoordinate().y());

        movedCell = classTobeTested.findLocationAfterMovement(mazeCell, Movement.WEST);
        Assertions.assertEquals(0, movedCell.cellCoordinate().x());
        Assertions.assertEquals(1, movedCell.cellCoordinate().y());

        movedCell = classTobeTested.findLocationAfterMovement(mazeCell, Movement.EAST);
        Assertions.assertEquals(2, movedCell.cellCoordinate().x());
        Assertions.assertEquals(1, movedCell.cellCoordinate().y());
    }
}
