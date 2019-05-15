package com.challenge.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.demo.Entities.QuestionAnswer;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QUESTION_ANSWER WHERE QUESTION_ID = :questionId", nativeQuery = true)
	void deleteByQuestionId(Long questionId);
}
