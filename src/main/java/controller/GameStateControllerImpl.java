

package controller;

import controller.dto.GameStateDto;
import controller.dto.TurnActionDto;
import model.GameStateService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

/**
 * Created by Oleksandr Husiev on 8/12/2018.
 */
public class GameStateControllerImpl implements GameStateController {

    private static int PITS_NUMBER = 6;

    @Autowired
    private GameStateService gameStateService;


    public GameStateDto getGameState() {
        GameStateDto gameStateDto = gameStateService.getGameState();
        for (int i = 0; i < gameStateDto.getPlayerStateDtoList().size(); i++) {
            if (i % 2 == 0) {
                Collections.reverse(gameStateDto.getPlayerStateDtoList().get(i).getPitList());
            }
        }
        return gameStateService.getGameState();
    }

    public void processGameTurn(TurnActionDto turnActionDto) {
        if (turnActionDto.getPlayerId() != gameStateService.getGameState().getActivePlayer()) {
            throw new IllegalArgumentException();
        }
        if (turnActionDto.getPlayerId() % 2 == 0) {
            turnActionDto.setPitId(PITS_NUMBER - turnActionDto.getPitId() - 1);
        }
        gameStateService.processTurn(turnActionDto);
    }
}
