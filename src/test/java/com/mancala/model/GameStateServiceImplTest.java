package com.mancala.model;


import com.mancala.model.dto.TurnActionDto;
import com.mancala.model.entity.GameStateEntity;
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
@SpringBootTest(classes= GameStateServiceImpl.class)
public class GameStateServiceImplTest {


    @Autowired
    private GameStateServiceImpl gameStateService;

    @Test
    public void processTurnPositive() {
        int playerId = 0;
        int pitId = 1;
        TurnActionDto turnActionDto = TurnActionDto.builder().playerId(playerId).pitId(pitId).build();
        gameStateService.processTurn(turnActionDto);

        GameStateEntity gameStateEntity = gameStateService.getGameState();
        Assert.assertEquals(1, gameStateEntity.getActivePlayer());
        Assert.assertEquals(0, gameStateService.getSmallPitValue(playerId, pitId));
    }

    @Test
    public void processTurnExtraTurn() {
        int playerId = 0;
        int pitId = 0;
        TurnActionDto turnActionDto = TurnActionDto.builder().playerId(playerId).pitId(pitId).build();
        gameStateService.processTurn(turnActionDto);

        GameStateEntity gameStateEntity = gameStateService.getGameState();
        Assert.assertEquals(playerId, gameStateEntity.getActivePlayer());
        Assert.assertEquals(0, gameStateService.getSmallPitValue(playerId, pitId));
    }

}