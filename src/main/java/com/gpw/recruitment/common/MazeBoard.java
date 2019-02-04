package com.gpw.recruitment.common;

import com.gpw.recruitment.model.CellPosition;
import com.gpw.recruitment.model.CellType;
import com.gpw.recruitment.model.MazeCell;
import com.gpw.recruitment.model.VisitStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class MazeBoard {

    @Getter
    private CellType[][] mazeCell;
    @Getter
    private VisitStatus[][] visitStatus;
    @Getter @Setter
    private MazeCell startCell;
    @Getter @Setter
    private MazeCell endCell;
    @Getter
    int width;
    @Getter int height;

    /**
     *
     * @param width  Maximum x value
     * @param height Maximum y value
     */
    public void init(final int width, final int height){
        this.height = height;
        this.width = width;
        this.mazeCell = new CellType[width][height];
        this.visitStatus = new VisitStatus[width][height];
    }

    public void markCellVisitStatus(final CellPosition coordinate, final VisitStatus visitStatus){
        this.visitStatus[coordinate.x()][coordinate.y()] = visitStatus;
    }

    public void markCellType(final CellPosition cellCoordinate, final CellType cellType){
        this.mazeCell[cellCoordinate.x()][cellCoordinate.y()] = cellType;
    }
}