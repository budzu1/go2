package com.TP;

public final class GameSession {
    private static GameSession instance;
    private Long gameId;
    private String login;

    // Private constructor to prevent instantiation
    private GameSession() {
    }

    // Public method to get the instance
    public static synchronized GameSession getInstance() {
        if (instance == null) {
            instance = new GameSession();
        }
        return instance;
    }

    // Getters and setters for gameId and userId
    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getUserId() {
        return login;
    }

    public void setUserId(String login) {
        this.login = login;
    }
}