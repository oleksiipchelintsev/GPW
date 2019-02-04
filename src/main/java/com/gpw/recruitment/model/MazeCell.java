package com.gpw.recruitment.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Objects;

@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
public class MazeCell {
    private final MazeCell parentCell;
    private final CellPosition cellCoordinate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MazeCell)) return false;
        MazeCell mazeCell = (MazeCell) o;
        return Objects.equals(cellCoordinate, mazeCell.cellCoordinate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(parentCell, cellCoordinate);
    }
}

