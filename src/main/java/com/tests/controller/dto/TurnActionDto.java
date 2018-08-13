

package com.tests.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Oleksandr Husiev on 8/12/2018.
 */
@Getter
@Setter
@Builder
public class TurnActionDto {
    private int playerId;
    private int pitId;
}
