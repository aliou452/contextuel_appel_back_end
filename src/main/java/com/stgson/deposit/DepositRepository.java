package com.stgson.deposit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DepositRepository extends JpaRepository<Deposit, Long> {

    List<Deposit> findByDistId(Long id);

    @Query("SELECT d FROM Deposit d WHERE d.dist.id=:id AND d.doneAt BETWEEN :startDate AND :endDate")
    List<Deposit> getTransBetweenDates(@Param("id")Long id, @Param("startDate") LocalDateTime startDate, @Param("endDate")LocalDateTime endDate);
}