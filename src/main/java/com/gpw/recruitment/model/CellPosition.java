package com.gpw.recruitment.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Objects;

@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
public class CellPosition {
    private final int x;
    private final int y;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CellPosition)) return false;
        CellPosition that = (CellPosition) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y);
    }
}
