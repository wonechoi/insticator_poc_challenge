package com.challenge.demo.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.challenge.demo.Entities.Question;
import com.challenge.demo.Entities.QuestionAnswer;
import com.challenge.demo.Entities.QuestionResult;
import com.challenge.demo.Entities.Site;
import com.challenge.demo.dto.QuestionDTO;
import com.challenge.demo.dto.QuestionResultDTO;
import com.challenge.demo.repositories.QuestionAnswerRepository;
import com.challenge.demo.repositories.QuestionRepository;
import com.challenge.demo.repositories.QuestionResultRepository;
import com.challenge.demo.repositories.SiteRepository;
import com.challenge.demo.service.WidgetService;

@Service
public class WidgetServiceImpl implements WidgetService {

	@Autowired
	SiteRepository siteRepository;
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	QuestionAnswerRepository qaRepository;
	
	@Autowired
	QuestionResultRepository qrRepository;
	
	@Override
	public QuestionDTO getNextQuestion(UUID siteUUID, UUID userUUID) {
		
		Question question = questionRepository.findNextQuestion(siteUUID, userUUID);
		
		if(question == null) {
			qrRepository.setLoopedTrue(siteUUID, userUUID);
			question = questionRepository.findNextQuestion(siteUUID, userUUID);
		}
		
		QuestionDTO questionDto = QuestionDTO.build(question);
		
		return questionDto;
	}

	@Override
	public ResponseEntity<QuestionResultDTO> saveQuestionResult(UUID siteUUID, UUID userUUID,
			QuestionResultDTO questionResultDto) {

		Site site = siteRepository.findByUuid(siteUUID);
		Optional<Question> question = questionRepository.findById(questionResultDto.getQuestionId());
		Optional<QuestionAnswer> questionAnswer = qaRepository.findById(questionResultDto.getAnswerId());

		QuestionResult questionResult = null;
		
		if(site != null && question.isPresent()	&& questionAnswer.isPresent()) {
			questionResult = QuestionResultDTO.transform(questionResultDto, site, question.get(), questionAnswer.get(), userUUID);
			return new ResponseEntity<>(QuestionResultDTO.build(qrRepository.save(questionResult)), HttpStatus.CREATED);
		}
		
		return ResponseEntity.notFound().build();
	}

	
	
}
