package io.hhplus.tdd;

import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.repository.PointHistoryRepository;
import io.hhplus.tdd.repository.UserPointRepository;
import io.hhplus.tdd.service.UserPointService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

/*
    포인트 충전
    - 기존 포인트가 없을 경우, 기본값 + 충전 포인트 => 최종 포인트 업데이트
    - 기존 포인트가 있을 경우, 기존 포인트 + 충전 포인트 => 최종 포인트 업데이트

    포인트 사용
    - 기존 포인트가 없을 경우, 사용 불가 처리
    - 사용 포인트가 기존 포인트보다 많을 경우, 사용 불가 처리
    - 사용 포인트가 기존 포인트보다 적을 경우, 사용 가능 -> 최종 포인트 업데이트

    포인트 조회
    - 기존 포인트가 없을 경우, 기본 값 반환
    - 기존 포인트가 있을 경우, 기존 포인트 반환

    포인트 내역 조회
    - 포인트 내역이 없을 경우, 포인트 내역 없음 예외처리
    - 포인트 내역이 존재할 경우, 포인트 내역 반환
 */
@WebMvcTest(UserPointService.class)
public class UserPointServiceTest {

    @MockBean
    private UserPointRepository userPointRepository;

    @MockBean
    private PointHistoryRepository pointHistoryRepository;

    @Autowired
    private UserPointService userPointService;

    @Test
    void 포인트조회_기존포인트_없음_기본값_반환(){
        //given
        long id = 1L;
        UserPoint initUserPoint = UserPoint.empty(id);
        given(userPointRepository.selectById(anyLong())).willReturn(initUserPoint);

        //when
        UserPoint userPoint = userPointService.userPoint(id);

        //then
        assertThat(userPoint).isEqualTo(initUserPoint);
    }

    @Test
    void 기존포인트_없음_포인트_충전(){
        //given
        long id = 1L;
        long amount = 30L;
        long time = System.currentTimeMillis();

        UserPoint initUserPoint = new UserPoint(id, amount, time);
        given(userPointRepository.insertOrUpdate(anyLong(), anyLong())).willReturn(initUserPoint);

        //when
        UserPoint updateUserPoint = userPointService.userPointCharge(id, amount);
        System.out.println("updateUserPoint : "+updateUserPoint);

        //then
        assertThat(updateUserPoint).isNotNull();
        assertThat(updateUserPoint.id()).isEqualTo(id);
        assertThat(updateUserPoint.point()).isEqualTo(amount);
    }

    //기존 포인트가 있을 경우, 기존 포인트 + 충전 포인트 => 포인트 충전
    @Test
    void 기존포인트_있음_기존포인트_충전포인트_반환(){
        long id = 1L;
        long initAmount = 10L;
        long time = System.currentTimeMillis();
        long amount = 40L;

        UserPoint initUserPoint = new UserPoint(id, initAmount, time);
        UserPoint updateUserPoint = new UserPoint(id, initAmount+amount, time);

        given(userPointRepository.insertOrUpdate(anyLong(),anyLong())).willReturn(initUserPoint);
        given(userPointRepository.insertOrUpdate(anyLong(), anyLong())).willReturn(updateUserPoint);

        //when
        UserPoint result = userPointService.userPointCharge(id, amount);
        System.out.println("result : "+result);

        //then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(id);
        assertThat(result.point()).isEqualTo(initAmount + amount);
    }

    @Test
    void 기존포인트_보다_사용포인트가_많으면_사용불가(){
        //given
        long id = 1L;
        long amount = 30L;
        long usePoint = 50L;
        UserPoint userPoint = new UserPoint(id, amount, System.currentTimeMillis());

        given(userPointRepository.insertOrUpdate(anyLong(),anyLong())).willReturn(userPoint);

        // then
        assertThrows(RuntimeException.class, () -> userPointService.userPointUse(id, usePoint));
    }

    @Test
    void 기존포인트_보다_사용포인트가_적으면_사용가능(){
        //given
        long id = 1L;
        long amount = 30L;
        long usePoint = 10L;
        UserPoint userPoint = new UserPoint(id, amount, System.currentTimeMillis());

        when(userPointRepository.insertOrUpdate(anyLong(),anyLong()));

        // when
        UserPoint useUserPoint = userPointService.userPointUse(id, usePoint);
        System.out.println(useUserPoint);

        //then
        assertThat(useUserPoint).isNotNull();
        assertThat(useUserPoint.id()).isEqualTo(id);
        assertThat(useUserPoint.point()).isEqualTo(amount - usePoint);
    }

    @Test
    void 사용내역_없음_예외처리(){
        //given
        long id = 1L;
        long amount = 30L;
        long time = System.currentTimeMillis();
        UserPoint userPoint = new UserPoint(id, amount, time);
        given(userPointRepository.insertOrUpdate(anyLong(),anyLong())).willReturn(userPoint);

        // then
        assertThrows(RuntimeException.class, () -> userPointService.selectAllUserId(id));
    }



}
