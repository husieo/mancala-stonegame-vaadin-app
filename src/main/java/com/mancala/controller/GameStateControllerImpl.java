

package com.mancala.controller;

import com.mancala.model.dto.GameStateDto;
import com.mancala.model.entity.GameStateEntity;
import com.mancala.model.dto.TurnActionDto;
import com.mancala.model.service.GameLogicService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Oleksandr Husiev on 8/12/2018.
 */
public class GameStateControllerImpl implements GameStateController {


    private static final long serialVersionUID = -6242854424423314906L;

    @Autowired
    private GameLogicService gameLogicService;


    public GameStateDto getGameState() {
        return gameLogicService.getGameState();
    }

    public void processGameTurn(TurnActionDto turnActionDto) {
        if (turnActionDto.getPlayerId() != gameLogicService.getGameState().getActivePlayer()) {
            throw new IllegalArgumentException();
        }
        gameLogicService.processTurn(turnActionDto);
    }

    @Override
    public void restartGame() {
        gameLogicService.initGame();
    }
}
