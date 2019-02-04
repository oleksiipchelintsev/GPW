package com.gpw.recruitment.service;

import com.gpw.recruitment.common.MazeBoard;
import com.gpw.recruitment.exception.MazeCustomSolverServiceException;
import com.gpw.recruitment.logic.MazeSolver;
import com.gpw.recruitment.logic.impl.BFSMethodSolver;
import com.gpw.recruitment.model.CellPosition;
import com.gpw.recruitment.model.CellType;
import com.gpw.recruitment.model.MazeCell;
import com.gpw.recruitment.model.VisitStatus;
import com.gpw.recruitment.util.MazeBoardUtil;
import com.gpw.recruitment.util.StringAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MazeSolverCLService {

    private static String NEW_LINE_REG_EXP = "[\\r]?\\n";
    @Autowired
    private MazeBoard mazeBoard;

    public List<String> listMazeInputs(final String directoryPath) {
        List<String> fileNames = new ArrayList<>();
        File folder = new File(directoryPath);
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                fileNames.add(file.getAbsolutePath());
            }
        }
        return fileNames;
    }

    public String processFile(String filePath) {
        Path path = null;
        try {
            path = Paths.get(new File(filePath).toURI());
            Stream<String> lines = Files.lines(path);
            String data = lines.collect(Collectors.joining("\n"));
            lines.close();
            String inputTxt = data.trim();
            StringAdapter stringAdapter = new StringAdapter(inputTxt);
            buildMazeBoard(stringAdapter.toString());

            MazeSolver mazeSolver = new BFSMethodSolver(this.mazeBoard, new MazeBoardUtil(this.mazeBoard));
            try {
                LinkedList<MazeCell> finalPath = mazeSolver.solveAndFindRoute();
                System.out.println("Fasters rote is this one: ");
                return mazeSolver.printMazePath(finalPath);
            } catch (MazeCustomSolverServiceException e) {
                return e.getLocalizedMessage();
            }
        } catch (IOException e) {
            return "Error" + e.getLocalizedMessage();
        }
    }


    public String processBinaryFile(String filePath) {
        Path path = null;
        Stream<String> linesOut = null;
        String data = "";
        try {
            path = Paths.get(new File(filePath).toURI());
            Stream<String> lines = Files.lines(path);
            List<String> listOfStrings = lines.collect(Collectors.toList());
            List<String> listOfNeededStrings = listOfStrings.subList(1, listOfStrings.size());

            System.out.println(listOfStrings);
            System.out.println(listOfNeededStrings);
            System.out.println();
            data = listOfNeededStrings.stream().
                    map(cur -> Integer.parseInt(cur, 2) + "")
                    .collect(Collectors.joining("\n"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    private void buildMazeBoard(final String inputMaze) {
        String[] mazeRows = inputMaze.split(NEW_LINE_REG_EXP);
        int noOfRows = mazeRows.length;
        int noOfColumns = mazeRows[0].length();

        this.mazeBoard.init(noOfRows,noOfColumns);
        for (int rowCount = 0; rowCount < noOfRows; rowCount++) {
            for (int colCount = 0; colCount < noOfColumns; colCount++) {

                CellType cellType = CellType.getByCharacter(mazeRows[rowCount].charAt(colCount));
                CellPosition cellCoordinate = new CellPosition(rowCount, colCount);     //Alex Mistake was here
                this.mazeBoard.markCellType(cellCoordinate, cellType);
                switch (cellType) {
                    case START:
                        this.mazeBoard.setStartCell(new MazeCell(null, cellCoordinate));
                        break;
                    case FINISH:
                        this.mazeBoard.setEndCell(new MazeCell(null, cellCoordinate));
                        break;
                    default:
                        break;
                }
                this.mazeBoard.markCellVisitStatus(cellCoordinate, VisitStatus.NOT_VISITED);
            }
        }
    }
}