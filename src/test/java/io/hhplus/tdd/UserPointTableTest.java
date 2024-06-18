package io.hhplus.tdd;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.UserPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
public class UserPointTableTest {

    private UserPointTable userPointTable;

    @BeforeEach
    public void setUp(){
        userPointTable = new UserPointTable();
    }

    @Test
    public void 사용자_포인트_처음_충전(){
        //given
        long id = 1L;
        long amount = 30L;

        //when
        final UserPoint response = userPointTable.insertOrUpdate(id, amount);

        //then
        assertThat(response.id()).isEqualTo(id);
        assertThat(response.point()).isEqualTo(amount);
    }

    /*
        TC_기존사용자_포인트_추가_충전
        1. 사용자가 처음 포인트 충전
        2. 기존 사용자 추가로 포인트 충전 -> 기존 포인트 + 추가 포인트가 저장되어야 함
        3. 기존 사용자의 포인트 조회 -> 기존 포인트 + 추가 포인트가 저장되었는지 검증
     */
    @Test
    public void 기존사용자_포인트_추가_충전(){
        //given
        long id = 1L;
        long addPoint = 40L;
        사용자_포인트_처음_충전();

        final UserPoint response = userPointTable.selectById(id);
        long amount = response.point() + addPoint;

        //when
        final UserPoint addResponse = userPointTable.insertOrUpdate(id, amount);

        //then
        assertThat(addResponse.id()).isEqualTo(id);
        assertThat(addResponse.point()).isEqualTo(amount);
    }
}
