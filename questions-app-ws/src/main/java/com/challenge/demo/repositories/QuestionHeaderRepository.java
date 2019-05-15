package com.challenge.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.demo.Entities.QuestionHeader;

public interface QuestionHeaderRepository extends JpaRepository<QuestionHeader, Long>{

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM QUESTION_HEADER WHERE QUESTION_ID = :questionId", nativeQuery = true)
	void deleteByQuestionId(Long questionId);

}
