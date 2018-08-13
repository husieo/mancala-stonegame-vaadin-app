package model;


import controller.dto.GameStateDto;
import controller.dto.TurnActionDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Oleksandr Husiev on 8/13/2018.
 */
//@RunWith(SpringRunner.class)
public class GameStateServiceImplTest {

    private GameStateService gameStateService;

    @Before
    public void setUp() {
        gameStateService = new GameStateServiceImpl();
        gameStateService.initGame();
    }

    @Test
    public void processTurnPositive() {
        int playerId = 0;
        int pitId = 1;
        TurnActionDto turnActionDto = TurnActionDto.builder().playerId(playerId).pitId(pitId).build();
        gameStateService.processTurn(turnActionDto);

        GameStateDto gameStateDto = gameStateService.getGameState();
        Assert.assertEquals(1, gameStateDto.getActivePlayer());
        Assert.assertEquals(0, gameStateDto.getSmallPitValue(playerId, pitId));
    }

    @Test
    public void processTurnExtraTurn() {
        int playerId = 0;
        int pitId = 0;
        TurnActionDto turnActionDto = TurnActionDto.builder().playerId(playerId).pitId(pitId).build();
        gameStateService.processTurn(turnActionDto);

        GameStateDto gameStateDto = gameStateService.getGameState();
        Assert.assertEquals(playerId, gameStateDto.getActivePlayer());
        Assert.assertEquals(0, gameStateDto.getSmallPitValue(playerId, pitId));
    }

    @Test
    public void testCaptureStones(){
        int playerId = 0;
        int pitId = 0;
//        gameStateService.getGameState().updateSmallPitValue(playerId, pitId, );
    }
}