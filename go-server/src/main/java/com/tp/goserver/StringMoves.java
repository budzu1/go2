package com.tp.goserver;

import java.util.ArrayList;

public final class StringMoves {

    public static String appendMoveToString(String existingMoves, int xn, int yn) {
        if (existingMoves == null || existingMoves.isEmpty()) {
            return xn + "," + yn + ";";
        } else {
            return existingMoves + xn + "," + yn + ";";
        }
    }

    public static ArrayList<Move> stringToMoveList(String movesString) {
        ArrayList<Move> movesList = new ArrayList<>();

        if (movesString != null && !movesString.isEmpty()) {
            String[] movesArray = movesString.split(";");

            for (String moveStr : movesArray) {
                if (!moveStr.isEmpty()) {
                    String[] coordinates = moveStr.split(",");
                    if (coordinates.length == 2) {
                        int x = Integer.parseInt(coordinates[0]);
                        int y = Integer.parseInt(coordinates[1]);
                        movesList.add(new Move(x, y));
                    }
                }
            }
        }

        return movesList;
    }

}
