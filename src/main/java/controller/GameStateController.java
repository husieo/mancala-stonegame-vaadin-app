

package controller;

import controller.dto.GameStateDto;
import controller.dto.TurnActionDto;

import java.io.Serializable;

/**
 * Created by Oleksandr Husiev on 8/12/2018.
 */
public interface GameStateController extends Serializable {



        GameStateDto getGameState();

        void processGameTurn(TurnActionDto turnActionDto);

        void restartGame();
}
