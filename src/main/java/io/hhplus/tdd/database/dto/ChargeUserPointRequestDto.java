package io.hhplus.tdd.database.dto;

import io.hhplus.tdd.point.UserPoint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChargeUserPointRequestDto {

    private long amount;

    @Builder
    public ChargeUserPointRequestDto(long amount){
        this.amount = amount;
    }
}
