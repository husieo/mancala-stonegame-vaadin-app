package com.mancala.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Oleksandr Husiev on 8/14/2018.
 */
@Getter
@Setter
@Builder
public class PlayerStateDto {
    Integer playerId;
    List<Integer> pitList;
    Integer scorePit;

}
