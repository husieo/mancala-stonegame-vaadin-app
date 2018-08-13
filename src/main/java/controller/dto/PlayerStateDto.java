

package controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleksandr Husiev on 8/12/2018.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerStateDto {
    Integer playerId;
    List<Integer> pitList;
    Integer scorePit;

    public PlayerStateDto(PlayerStateDto other){
        pitList = new ArrayList<>();
        for(Integer el : other.getPitList()){
            pitList.add(new Integer(el));
        }
        playerId = other.getPlayerId();
        scorePit = other.scorePit;
    }
}
