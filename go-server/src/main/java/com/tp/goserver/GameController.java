package com.tp.goserver;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private ActiveGamesService activeGameService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username) {
        String loginMessage = gameService.loginUser(username);
        return new ResponseEntity<>(loginMessage, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createGame(@RequestParam String creator, @RequestParam int size) {
        Game newGame = gameService.createGame(creator, size);
        Long id = newGame.getId();
        System.out.println(id);
        activeGameService.createGame(id, newGame);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PostMapping("/join")
    public ResponseEntity<Integer> joinGame(@RequestParam Long gameId, @RequestParam String opponent) {
        System.out.println("join: " + opponent);
        Game updatedGame = gameService.joinGame(gameId, opponent);
        activeGameService.startGame(gameId, updatedGame);
        return new ResponseEntity<>(updatedGame.getSize(), HttpStatus.OK);
    }

    @PostMapping("/addBot")
    public ResponseEntity<Game> addBotToGame(@RequestParam Long gameId) {
        gameService.addBot(gameId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/makeMove")
    public ResponseEntity<Boolean> makeMove(@RequestParam int row, @RequestParam int col, @RequestParam String login,
            @RequestParam Long gameId) {

        activeGameService.makeMove(gameId, row - 1, col - 1, login);

        gameService.addMove(gameId, new Move(row - 1, col - 1));

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestParam String login,
            @RequestParam Long gameId) {

        ArrayToSend arrayToSend = new ArrayToSend(activeGameService.getArray(gameId, login));

        ObjectMapper objectMapper = new ObjectMapper();

        if (!activeGameService.ifCanChange(gameId)) {
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            String jsonString = objectMapper.writeValueAsString(arrayToSend);
            return new ResponseEntity<>(jsonString, HttpStatus.OK);
            // jsonString now contains the JSON representation of arrayToSend
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("error", HttpStatus.OK);
    }

    @PostMapping("/pass")
    public ResponseEntity<Boolean> pass(@RequestParam Long gameId, @RequestParam String login) {
        boolean temp = activeGameService.pass(gameId, login);
        return new ResponseEntity<>(temp, HttpStatus.OK);
    }

    @PostMapping("/giveUp")
    public ResponseEntity<Boolean> giveUp(@RequestParam Long gameId, @RequestParam String login) {
        boolean temp = activeGameService.giveUp(gameId, login);
        return new ResponseEntity<>(temp, HttpStatus.OK);
    }

    @PostMapping("/pContinue")
    public ResponseEntity<Boolean> pContinue(@RequestParam Long gameId, @RequestParam String login) {
        boolean temp = activeGameService.pContinue(gameId, login);
        return new ResponseEntity<>(temp, HttpStatus.OK);
    }

    @PostMapping("/getWinner")
    public ResponseEntity<String> getWinner(@RequestParam Long gameId) {
        String winner = activeGameService.getWinner(gameId);

        if (winner == null) {
            return new ResponseEntity<>("", HttpStatus.OK);
        } else {
            gameService.setWinner(gameId, winner);
            gameService.changeStatusFinish(gameId);
            return new ResponseEntity<>(winner, HttpStatus.OK);
        }
    }

}
