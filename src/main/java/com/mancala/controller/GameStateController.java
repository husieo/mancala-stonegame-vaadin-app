

package com.mancala.controller;

import com.mancala.model.dto.TurnActionDto;
import com.mancala.model.entity.GameStateEntity;

import java.io.Serializable;

/**
 * Created by Oleksandr Husiev on 8/12/2018.
 */
public interface GameStateController extends Serializable {



        GameStateEntity getGameState();

        void processGameTurn(TurnActionDto turnActionDto);

        void restartGame();
}
