package io.hhplus.tdd.service;

import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.repository.UserPointRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPointService {

    private final UserPointRepository userPointRepository;

    public UserPoint chargeUserPoint(UserPoint userPoint){
        userPointRepository.save(userPoint);
    }
}
