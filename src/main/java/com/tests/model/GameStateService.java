

package com.tests.model;

import com.tests.controller.dto.GameStateDto;
import com.tests.controller.dto.TurnActionDto;

/**
 * Created by Oleksandr Husiev on 8/12/2018.
 */
public interface GameStateService {

    GameStateDto getGameState();
    void processTurn(TurnActionDto turnActionDto);
    void initGame();
}
