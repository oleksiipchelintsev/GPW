package com.gpw.recruitment.logic.impl;

import com.gpw.recruitment.common.MazeBoard;
import com.gpw.recruitment.exception.MazeCustomSolverServiceException;
import com.gpw.recruitment.model.CellType;
import com.gpw.recruitment.model.MazeCell;
import com.gpw.recruitment.util.MazeBoardUtil;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BFSMazeSolverTest {

    @Mock
    private MazeBoard mazeBoard;
    @Mock
    private MazeBoardUtil mazeBoardUtil;

    BFSMethodSolver classToBeTested;

    @BeforeEach
    public void setUp() {
        classToBeTested = new BFSMethodSolver(mazeBoard, mazeBoardUtil);
    }

    @Test
    void whenRouteNotFound_ShouldThrowException() {

        assertThrows(MazeCustomSolverServiceException.class, () ->
                classToBeTested.solveAndFindRoute());
    }

    @Test
    void whenRouteIsFound_shouldBackTrack() throws MazeCustomSolverServiceException {
        when(mazeBoard.getStartCell()).thenReturn(mock(MazeCell.class));
        when(mazeBoardUtil.checkCellLocationValidity(mazeBoard.getStartCell())).thenReturn(true);
        when(mazeBoardUtil.hasItBeenAlreadyVisited(mazeBoard.getStartCell())).thenReturn(false);
        when(mazeBoardUtil.findCellType(mazeBoard.getStartCell())).thenReturn(CellType.FINISH);
        List<MazeCell> mazeCellList = new ArrayList<>();
        when(mazeBoardUtil.findBackTrackedPath(mazeBoard.getStartCell())).thenReturn(mazeCellList);

        Assertions.assertEquals(mazeCellList, classToBeTested.solveAndFindRoute());
    }

}
