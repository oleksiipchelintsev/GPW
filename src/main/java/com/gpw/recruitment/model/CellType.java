package com.gpw.recruitment.model;

import java.util.Arrays;

public enum CellType {
    EMPTY(' '), WALL('#'), START('S'), FINISH('F'), ROUTE('.');

    CellType(final char characterType) {
        this.characterType = characterType;
    }

    private char characterType;

    public char getCharacterType() {
        return characterType;
    }

    public static CellType getByCharacter(char shortCut) {
        return Arrays.stream(CellType.values()).filter(v -> v.characterType == shortCut).findAny().orElse(null);
    }
}
