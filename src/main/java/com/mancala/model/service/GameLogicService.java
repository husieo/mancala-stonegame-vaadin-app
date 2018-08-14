

package com.mancala.model.service;


import com.mancala.model.dto.GameStateDto;
import com.mancala.model.entity.GameStateEntity;
import com.mancala.model.dto.TurnActionDto;

/**
 * Created by Oleksandr Husiev on 8/12/2018.
 */
public interface GameLogicService {

    GameStateDto getGameState();
    void processTurn(TurnActionDto turnActionDto);
    void initGame();


}
