package com.tp.goserver;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.util.ArrayList;

import org.hibernate.mapping.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String creator; // Player who created the game
    private String opponent; // Opponent player or bot
    private boolean bot; // True if the opponent is a bot
    private String status; // Status of the game (e.g., "waiting", "in progress", "finished")
    private int size;
    private String winner;

    @Transient
    private ArrayList<Move> moves = new ArrayList<>();

    private String movesJson;

    public ArrayList<Move> getMoves() {
        return moves;
    }

    private void updateMovesJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.movesJson = objectMapper.writeValueAsString(moves);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Handle this exception appropriately
        }
    }

    public void setMoves(ArrayList<Move> moves) {
        this.moves = moves;
        updateMovesJson();
    }

    public void addMove(Move move) {
        this.moves.add(move);
        updateMovesJson();
    }

    public String getMovesJson() {
        return movesJson;
    }

    public void loadMovesFromJson() {
        if (this.movesJson != null && !this.movesJson.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                this.moves = objectMapper.readValue(this.movesJson, new TypeReference<ArrayList<Move>>() {
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public boolean getBot() {
        return bot;
    }

    public void setBot(boolean isBot) {
        this.bot = isBot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Game() {

    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
