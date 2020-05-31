package com.minseon.restApi.repository;

import com.minseon.restApi.entity.Quiz;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz,Long> {
    List<Quiz> findAll();
    Slice<Quiz> findAllBy(Pageable pageable);
}
