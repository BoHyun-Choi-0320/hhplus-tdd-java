package io.hhplus.tdd.repository;

import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointHistoryRepository {
    PointHistory insert(long userId, long amount, TransactionType type, long updateMillis);

    List<PointHistory> selectAllUserId(long userId);
}
