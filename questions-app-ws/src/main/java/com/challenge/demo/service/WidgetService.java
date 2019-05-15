package com.challenge.demo.service;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.challenge.demo.dto.QuestionDTO;
import com.challenge.demo.dto.QuestionResultDTO;

public interface WidgetService {

	// serve up a unique question which a user has not gotten to users
	public QuestionDTO getNextQuestion(UUID siteUUID, UUID userUUID);

	// store a user response
	public ResponseEntity<QuestionResultDTO> saveQuestionResult(UUID siteUUID, UUID userUUID,
			QuestionResultDTO questionResultDto);
}
