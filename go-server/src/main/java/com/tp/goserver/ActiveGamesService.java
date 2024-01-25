package com.tp.goserver;

import java.util.HashMap;

import org.springframework.stereotype.Service;

@Service
public class ActiveGamesService {
    private HashMap<Integer, GoGame> games = new HashMap<>();

    public void createGame(int id, GoGame game) {
        games.put(id, game);
    }

    public void removeGame(int id) {
        games.remove(id);
    }
}
