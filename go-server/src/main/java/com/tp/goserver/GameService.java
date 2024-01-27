package com.tp.goserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActiveGamesService activeGames;

    public String loginUser(String username) {
        User user = userRepository.findById(username)
                .orElse(new User(username, false));

        if (user.isLoggedIn()) {
            return "User already logged in. Please use another login.";
        } else {
            user.setLoggedIn(true);
            userRepository.save(user);
            return "User logged in successfully.";
        }
    }

    public Game createGame(String creator, int size) {
        Game newGame = new Game();
        newGame.setSize(size);
        newGame.setCreator(creator);
        newGame.setStatus("waiting"); // Initial status is 'waiting' for an opponent
        newGame.setBot(false); // By default, the game is not with a bot

        return gameRepository.save(newGame);
    }

    public Game joinGame(Long gameId, String opponent) {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        if (!optionalGame.isPresent()) {
            throw new RuntimeException("Game not found");
        }

        Game game = optionalGame.get();
        game.setOpponent(opponent);
        game.setStatus("in progress");
        return gameRepository.save(game);
    }

    public Game addBot(Long gameId) {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        if (!optionalGame.isPresent()) {
            throw new RuntimeException("Game not found");
        }

        Game game = optionalGame.get();
        game.setOpponent("Bot");
        game.setBot(true);
        game.setStatus("in progress");
        return gameRepository.save(game);
    }
}
