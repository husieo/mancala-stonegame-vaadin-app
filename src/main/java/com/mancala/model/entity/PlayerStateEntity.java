

package com.mancala.model.entity;

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
public class PlayerStateEntity {
    Integer playerId;
    List<Integer> pitList;
    Integer scorePit;

    public PlayerStateEntity(PlayerStateEntity other){
        pitList = new ArrayList<>();
        pitList.addAll(other.getPitList());
        playerId = other.getPlayerId();
        scorePit = other.scorePit;
    }
}
