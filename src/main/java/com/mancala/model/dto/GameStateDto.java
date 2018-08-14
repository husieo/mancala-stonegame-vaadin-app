package com.mancala.model.dto;

import com.mancala.model.entity.PlayerStateEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Oleksandr Husiev on 8/14/2018.
 */
@Getter
@Setter
@Builder
public class GameStateDto {
    private int activePlayer;
    List<PlayerStateDto> playerStateDtoList;
    private boolean gameOver;
}
