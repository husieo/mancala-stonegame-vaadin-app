

package controller;

import controller.dto.GameStateDto;
import controller.dto.TurnActionDto;

/**
 * Created by Oleksandr Husiev on 8/12/2018.
 */
public interface GameStateController {



        GameStateDto getGameState();

        void processGameTurn(TurnActionDto turnActionDto);
}
