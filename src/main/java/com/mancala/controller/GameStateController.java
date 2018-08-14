

package com.mancala.controller;

import com.mancala.model.dto.GameStateDto;
import com.mancala.model.dto.TurnActionDto;

import java.io.Serializable;

/**
 * Created by Oleksandr Husiev on 8/12/2018.
 */
public interface GameStateController extends Serializable {



        GameStateDto getGameState();

        void processGameTurn(TurnActionDto turnActionDto);

        void restartGame();
}
