package com.tests.model;


import com.tests.controller.dto.GameStateDto;
import com.tests.controller.dto.TurnActionDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Oleksandr Husiev on 8/13/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes= com.tests.model.GameStateServiceImpl.class)
public class GameStateServiceImplTest {


    @Autowired
    private GameStateService gameStateService;

    @Before
    public void setUp() {
//        gameStateService = new GameStateServiceImpl();
//        gameStateService.initGame();
    }

    @Test
    public void processTurnPositive() {
        int playerId = 0;
        int pitId = 1;
        TurnActionDto turnActionDto = TurnActionDto.builder().playerId(playerId).pitId(pitId).build();
        gameStateService.processTurn(turnActionDto);

        GameStateDto gameStateDto = gameStateService.getGameState();
        Assert.assertEquals(1, gameStateDto.getActivePlayer());
        Assert.assertEquals(0, gameStateDto.getSmallPitValue(playerId, pitId));
    }

    @Test
    public void processTurnExtraTurn() {
        int playerId = 0;
        int pitId = 0;
        TurnActionDto turnActionDto = TurnActionDto.builder().playerId(playerId).pitId(pitId).build();
        gameStateService.processTurn(turnActionDto);

        GameStateDto gameStateDto = gameStateService.getGameState();
        Assert.assertEquals(playerId, gameStateDto.getActivePlayer());
        Assert.assertEquals(0, gameStateDto.getSmallPitValue(playerId, pitId));
    }

    @Test
    public void testCaptureStones(){
        int playerId = 0;
        int pitId = 0;
//        gameStateService.getGameState().updateSmallPitValue(playerId, pitId, );
    }
}