package com.challenge.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.demo.Entities.QuestionAnswer;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {
}
