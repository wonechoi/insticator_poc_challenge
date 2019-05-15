package com.challenge.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.challenge.demo.Entities.Question;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, Long> {

	@Query(value = "SELECT q.* FROM questions q WHERE q.site_id = ?1", nativeQuery = true)
	List<Question> findSiteQuestions(Long siteId);

	@Query(value = "SELECT * FROM "
			 + "(SELECT Q.* FROM SITE S, QUESTION Q, QUESTION_RESULT R "
			 + " WHERE S.SITE_UUID = ?1 AND S.SITE_ID = Q.SITE_ID AND S.SITE_UUID = R.SITE_UUID "
			 + "   AND Q.QUESTION_ID NOT IN (SELECT QUESTION_ID FROM QUESTION_RESULT WHERE R.USER_UUID = ?2 AND R.LOOPED != 1)) "
			 + "ORDER BY RAND() LIMIT 1", nativeQuery = true)
	Question findNextQuestion(UUID siteUUID, UUID userUUID);
}