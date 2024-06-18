package io.hhplus.tdd;

import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.service.UserPointService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
public class UserPointServiceTest {

    @Autowired
    UserPointService userPointService;

    @Test
    void 포인트_충전(){
        //given
        long id = 1L;
        long point = 34L;
        long updateMillis = 300L;
        UserPoint userPoint = new UserPoint(id, point, updateMillis);

        //when
        UserPoint userPoints = userPointService.chargeUserPoint(userPoint);

        //then
        assertThat(userPoints).isEqualTo(new UserPoint(id, point,updateMillis));

    }
}
