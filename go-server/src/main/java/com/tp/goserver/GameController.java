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
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PostMapping("/join")
    public ResponseEntity<Integer> joinGame(@RequestParam Long gameId, @RequestParam String opponent) {
        Game updatedGame = gameService.joinGame(gameId, opponent);
        return new ResponseEntity<>(updatedGame.getSize(), HttpStatus.OK);
    }

    @PostMapping("/addBot")
    public ResponseEntity<Game> addBotToGame(@RequestParam Long gameId) {
        gameService.addBot(gameId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/makeMove")
    public ResponseEntity<Boolean> makeMove(@RequestParam int x, @RequestParam int y, @RequestParam String login) {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
