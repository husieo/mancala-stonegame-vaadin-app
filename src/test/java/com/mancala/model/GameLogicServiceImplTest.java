package com.mancala.model;


import com.mancala.model.dto.GameStateDto;
import com.mancala.model.dto.TurnActionDto;
import com.mancala.model.service.GameLogicServiceImpl;
import com.mancala.model.service.GameStateService;
import com.mancala.model.service.GameStateServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Oleksandr Husiev on 8/13/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes= {GameLogicServiceImpl.class, GameStateServiceImpl.class})
public class GameLogicServiceImplTest {


    @Autowired
    private GameLogicServiceImpl gameLogicService;

    @Autowired
    private GameStateServiceImpl gameStateService;

    /**
     * Check if the turn simple scenario is finished with the expected result
     */
    @Test
    public void processTurnPositive() {
        int playerId = 0;
        int pitId = 1;
        TurnActionDto turnActionDto = TurnActionDto.builder().playerId(playerId).pitId(pitId).build();
        gameLogicService.processTurn(turnActionDto);

        GameStateDto gameStateDto = gameStateService.getGameState();
        Assert.assertEquals(1, gameStateDto.getActivePlayer());
        Assert.assertEquals(0, gameStateService.getSmallPitValue(playerId, pitId));
    }


    /**
     * Check if the player is given extra turn if the last stone ended in his score pit
     */
    @Test
    public void processTurnExtraTurn() {
        int playerId = 0;
        int pitId = 0;
        TurnActionDto turnActionDto = TurnActionDto.builder().playerId(playerId).pitId(pitId).build();
        gameLogicService.processTurn(turnActionDto);

        GameStateDto gameStateDto = gameStateService.getGameState();
        Assert.assertEquals(playerId, gameStateDto.getActivePlayer());
        Assert.assertEquals(0, gameStateService.getSmallPitValue(playerId, pitId));
    }

}