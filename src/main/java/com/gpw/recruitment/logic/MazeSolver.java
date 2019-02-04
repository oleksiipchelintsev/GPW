package com.gpw.recruitment.logic;

import com.gpw.recruitment.exception.MazeCustomSolverServiceException;
import com.gpw.recruitment.model.MazeCell;

import java.util.LinkedList;
import java.util.List;


public interface MazeSolver {
    LinkedList<MazeCell> solveAndFindRoute() throws MazeCustomSolverServiceException;

    String printMazePath(List<MazeCell> path);

    String printFullMaze();
}

