

package controller;

import controller.dto.GameStateDto;
import controller.dto.TurnActionDto;
import model.GameStateService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Oleksandr Husiev on 8/12/2018.
 */
public class GameStateControllerImpl implements GameStateController {

    @Autowired
    private GameStateService gameStateService;


    public GameStateDto getGameState() {
        GameStateDto gameStateDto = gameStateService.getGameState();
        return gameStateDto;
    }

    public void processGameTurn(TurnActionDto turnActionDto) {
        if (turnActionDto.getPlayerId() != gameStateService.getGameState().getActivePlayer()) {
            throw new IllegalArgumentException();
        }
        gameStateService.processTurn(turnActionDto);
    }
}
