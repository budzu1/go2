package com.tp.goserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username) {
        String loginMessage = gameService.loginUser(username);
        return new ResponseEntity<>(loginMessage, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Game> createGame(@RequestParam String creator) {
        Game newGame = gameService.createGame(creator);
        Long id = newGame.getId();
        return new ResponseEntity<>(newGame, HttpStatus.CREATED);
    }

    @PostMapping("/join")
    public ResponseEntity<Game> joinGame(@RequestParam Long gameId, @RequestParam String opponent) {
        Game updatedGame = gameService.joinGame(gameId, opponent);
        return new ResponseEntity<>(updatedGame, HttpStatus.OK);
    }

    @PostMapping("/addBot")
    public ResponseEntity<Game> addBotToGame(@RequestParam Long gameId) {
        Game updatedGame = gameService.addBot(gameId);
        return new ResponseEntity<>(updatedGame, HttpStatus.OK);
    }

    @PostMapping("/makeMove")
    public ResponseEntity<Boolean> makeMove(@RequestParam int x, @RequestParam int y, @RequestParam String login,
            @RequestParam String gameId) {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
