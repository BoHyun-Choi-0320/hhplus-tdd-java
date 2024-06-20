package io.hhplus.tdd.service;

import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.repository.PointHistoryRepository;
import io.hhplus.tdd.repository.UserPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserPointService {

    private final UserPointRepository userPointRepository;
    private final PointHistoryRepository pointHistoryRepository;

    public UserPoint userPoint(long id){
        return userPointRepository.selectById(id);
    }

    public UserPoint userPointCharge(long id, long amount){
        // 기존 정보 가져오기
        // 만약 기존 정보가 없다면 기본값 반환
        UserPoint userPoint = Optional.ofNullable(userPoint(id)).orElse(UserPoint.empty(id));
        // 최종 포인트 = 기존 포인트 + 충전 포인트
        long finalPoint = userPoint.point() + amount;

        // 포인트 업데이트
        UserPoint updateUserPoint = userPointRepository.insertOrUpdate(id, finalPoint);

        // 포인트 충전 기록 저장
        pointHistoryRepository.insert(id, amount, TransactionType.CHARGE, System.currentTimeMillis());

        return updateUserPoint;
    }

    public List<PointHistory> selectAllUserId(long id){
        // 특정 사용자의 충전/이용 내역 조회
        List<PointHistory> userPointHistories = pointHistoryRepository.selectAllUserId(id);

        // 만약 포인트 기록이 없으면 예외처리
        if(userPointHistories.isEmpty()){
            throw new RuntimeException("User Point History not found.");
        }
        return userPointHistories;

    }

    public UserPoint userPointUse(long id, long amount){
        // 기존 포인트 조회
        // 포인트가 없을 경우, 0 반환
        long existPoint = Optional.ofNullable(userPointRepository.selectById(id)).map(UserPoint::point).orElseThrow(()-> new RuntimeException("not enough points to use."));

        // 기존 포인트보다 사용 포인트가 많을 경우, 예외 처리
        if(amount > existPoint){
            throw new RuntimeException("not enough points to use. ");
        }

        // 최종 업데이트 포인트
        long finalPoint = existPoint - amount;

        // 사용자 포인트 업데이트
        UserPoint updateUserPoint = userPointRepository.insertOrUpdate(id, finalPoint);

        // 포인트 사용 기록
        pointHistoryRepository.insert(id, amount, TransactionType.USE, System.currentTimeMillis());

        return updateUserPoint;
    }
}
