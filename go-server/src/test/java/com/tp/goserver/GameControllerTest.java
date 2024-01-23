package com.tp.goserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.when;

@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Test
    void createGameEndpointShouldReturnGame() throws Exception {
        String creator = "Player1";
        Game game = new Game();
        game.setCreator(creator);

        given(gameService.createGame(anyString())).willReturn(game);

        mockMvc.perform(post("/game/create").param("creator", creator))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.creator").value(creator));
    }

    @Test
    void gameShouldBeCreated() throws Exception {
        String creator = "Player1";
        Game game = new Game();
        game.setCreator(creator);

        given(gameService.createGame(anyString())).willReturn(game);

        mockMvc.perform(post("/game/create")
                .param("creator", creator))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.creator").value(creator));
    }

    @Test
    void gameStatusShouldBeUpdated() throws Exception {
        Long gameId = 1L;
        String opponent = "Player2";
        Game game = new Game();
        game.setId(gameId);
        game.setOpponent(opponent);
        game.setStatus("in progress");

        given(gameService.joinGame(anyLong(), anyString())).willReturn(game);

        mockMvc.perform(post("/game/join")
                .param("gameId", String.valueOf(gameId))
                .param("opponent", opponent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.opponent").value(opponent))
                .andExpect(jsonPath("$.status").value("in progress"));
    }

    @Test
    void botShouldBeAddedToGame() throws Exception {
        Long gameId = 1L;
        Game game = new Game();
        game.setId(gameId);
        game.setOpponent("Bot");
        game.setBot(true);
        game.setStatus("in progress");

        given(gameService.addBot(anyLong())).willReturn(game);

        mockMvc.perform(post("/game/addBot")
                .param("gameId", String.valueOf(gameId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.opponent").value("Bot"))
                .andExpect(jsonPath("$.bot").value(true))
                .andExpect(jsonPath("$.status").value("in progress"));
    }

    @Test
    public void testLogin() throws Exception {
        // Mock the behavior of the gameService when loginUser is called
        when(gameService.loginUser("testUser")).thenReturn("User logged in successfully.");

        // Perform the test
        mockMvc.perform(post("/game/login").param("username", "testUser"))
                .andExpect(status().isOk())
                .andExpect(content().string("User logged in successfully."));
    }

}
