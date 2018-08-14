

package com.mancala.controller;

import com.mancala.model.entity.GameStateEntity;
import com.mancala.model.dto.TurnActionDto;
import com.mancala.model.service.GameStateService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Oleksandr Husiev on 8/12/2018.
 */
public class GameStateControllerImpl implements GameStateController {


    private static final long serialVersionUID = -6242854424423314906L;

    @Autowired
    private GameStateService gameStateService;


    public GameStateEntity getGameState() {
        return gameStateService.getGameState();
    }

    public void processGameTurn(TurnActionDto turnActionDto) {
        if (turnActionDto.getPlayerId() != gameStateService.getGameState().getActivePlayer()) {
            throw new IllegalArgumentException();
        }
        gameStateService.processTurn(turnActionDto);
    }

    @Override
    public void restartGame() {
        gameStateService.initGame();
    }
}
