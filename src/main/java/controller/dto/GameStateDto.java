

package controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Oleksandr Husiev on 8/12/2018.
 */
@Getter
@Setter
@Builder
public class GameStateDto {
    private int activePlayer;
    List<PlayerStateDto> playerStateDtoList;


    public int getSmallPitValue(int playerId, int pitNumber) {
        return playerStateDtoList.get(playerId).getPitList().get(pitNumber);
    }

    public void updateSmallPitValue(int playerId, int pitNumber, int value) {
        int prevValue = playerStateDtoList.get(playerId).getPitList().get(pitNumber);
        playerStateDtoList.get(playerId).getPitList().set(pitNumber, prevValue + value);
    }

    public void updateScorePitValue(int playerId, int value) {
        int prevValue = playerStateDtoList.get(playerId).getScorePit();
        playerStateDtoList.get(playerId).setScorePit(prevValue + value);
    }
}
