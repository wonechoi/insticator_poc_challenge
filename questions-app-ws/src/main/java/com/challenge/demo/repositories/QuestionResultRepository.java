package com.challenge.demo.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.demo.Entities.Question;
import com.challenge.demo.Entities.QuestionResult;

public interface QuestionResultRepository extends JpaRepository<QuestionResult, Long> {
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE QUESTION_RESULT SET LOOPED = 1 WHERE site_uuid = :siteUUID and user_uuid = :userUUID", nativeQuery = true)
	void setLoopedTrue(UUID siteUUID, UUID userUUID);


}
