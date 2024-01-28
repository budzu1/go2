package com.tp.goserver;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActiveGamesService {

    private HashMap<Long, GoGame> games = new HashMap<>();

    public synchronized void createGame(Long id, Game game) {
        games.put(id, new GoGame(id, game));
    }

    public synchronized void removeGame(Long id) {
        games.remove(id);
    }

    public synchronized void startGame(Long id, Game game) {
        games.get(id).start(game);
    }

    public synchronized void makeMove(Long id, int row, int col, String login) {
        games.get(id).addMove(row, col, login);
    }

    public synchronized ArrayList<ArrayList<Integer>> getArray(Long id, String login) {
        return games.get(id).getBoard().prepareToSend();
    }

    public synchronized boolean ifCanChange(Long id) {
        return games.get(id).ifCanChange();
    }
}
