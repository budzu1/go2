package com.tp.goserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    @Autowired
    private GameService gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createGameShouldReturnNewGame() {
        String creator = "Player1";
        Game game = new Game();
        game.setCreator(creator);

        when(gameRepository.save(any(Game.class))).thenReturn(game);

        Game createdGame = gameService.createGame(creator, 9);

        assertNotNull(createdGame);
        assertEquals(creator, createdGame.getCreator());
    }

    @Test
    void joinGameShouldUpdateGameStatus() {
        Long gameId = 1L;
        String opponent = "Player2";
        Game game = new Game();
        game.setId(gameId);
        game.setStatus("waiting");

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(gameRepository.save(any(Game.class))).thenReturn(game);

        Game updatedGame = gameService.joinGame(gameId, opponent);

        assertEquals("in progress", updatedGame.getStatus());
        assertEquals(opponent, updatedGame.getOpponent());
    }

    @Test
    void joinGameWithInvalidIdShouldThrowException() {
        Long gameId = 1L;
        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            gameService.joinGame(gameId, "Player2");
        });
    }

    @Test
    void gameShouldBeSaved() {
        String creator = "Player1";
        Game game = new Game();
        game.setCreator(creator);
        game.setStatus("waiting");

        when(gameRepository.save(any(Game.class))).thenReturn(game);

        Game createdGame = gameService.createGame(creator, 9);

        assertNotNull(createdGame);
        assertEquals(creator, createdGame.getCreator());
        System.out.println(createdGame.getStatus());
        assertEquals("waiting", createdGame.getStatus());
    }

    @Test
    void gameStatusShouldBeInProgress() {
        Long gameId = 1L;
        String opponent = "Player2";
        Game game = new Game();
        game.setId(gameId);
        game.setStatus("waiting");

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(gameRepository.save(any(Game.class))).thenReturn(game);

        Game updatedGame = gameService.joinGame(gameId, opponent);

        assertEquals("in progress", updatedGame.getStatus());
        assertEquals(opponent, updatedGame.getOpponent());
    }

    @Test
    void botShouldBeAddedToGame() {
        Long gameId = 1L;
        Game game = new Game();
        game.setId(gameId);
        game.setStatus("waiting");

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(gameRepository.save(any(Game.class))).thenReturn(game);

        Game updatedGame = gameService.addBot(gameId);

        assertEquals("Bot", updatedGame.getOpponent());
        assertTrue(updatedGame.getBot());
        assertEquals("in progress", updatedGame.getStatus());
    }

    @Test
    public void testNewUserLogin() {
        when(userRepository.findById("newUser")).thenReturn(Optional.empty());
        String response = gameService.loginUser("newUser");
        assertEquals("User logged in successfully.", response);
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testExistingUserLogin() {
        User existingUser = new User("existingUser", false);
        when(userRepository.findById("existingUser")).thenReturn(Optional.of(existingUser));
        String response = gameService.loginUser("existingUser");
        assertEquals("User logged in successfully.", response);
        verify(userRepository).save(existingUser);
    }

    @Test
    public void testAlreadyLoggedInUser() {
        User loggedInUser = new User("loggedInUser", true);
        when(userRepository.findById("loggedInUser")).thenReturn(Optional.of(loggedInUser));
        String response = gameService.loginUser("loggedInUser");
        assertEquals("User already logged in. Please use another login.", response);
    }

}
