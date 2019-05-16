package com.challenge.demo.service.impl;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.challenge.demo.Entities.Question;
import com.challenge.demo.Entities.QuestionAnswer;
import com.challenge.demo.Entities.QuestionHeader;
import com.challenge.demo.Entities.QuestionResult;
import com.challenge.demo.Entities.QuestionType;
import com.challenge.demo.Entities.Site;
import com.challenge.demo.dto.QuestionDTO;
import com.challenge.demo.dto.QuestionResultDTO;
import com.challenge.demo.repositories.QuestionAnswerRepository;
import com.challenge.demo.repositories.QuestionRepository;
import com.challenge.demo.repositories.QuestionResultRepository;
import com.challenge.demo.repositories.SiteRepository;

class WidgetServiceImplTest {

	@InjectMocks
	WidgetServiceImpl widgetService;
	
	@Mock
	SiteRepository siteRepository;
	
	@Mock
	QuestionRepository questionRepository;
	
	@Mock
	QuestionAnswerRepository qaRepository;
	
	@Mock
	QuestionResultRepository qrRepository;
	
	Site site = new Site();
	Question question = new Question();
	QuestionAnswer qa = new QuestionAnswer();
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		question.setQuestionId(1L);
		question.setQuestion("Please tell us a bit about yourself");
		question.setSite(new Site());
		question.setType(QuestionType.valueOf("Matrix"));
		
		site.setUrl("www.bob.com");
		
		qa.setId(2L);
		qa.setQuestion(question);
		qa.setAnswer("Female 15 to 35");
		qa.setIsCorrectAnswer(true);
	}

	/*
	 * Test getNextQuestion() when data exists and is right value.
	 */
	@Test
	final void testGetNextQuestion() throws Exception {
		
		when(questionRepository.findNextQuestion(any(UUID.class), any(UUID.class))).thenReturn(question);
		
		ResponseEntity<QuestionDTO> questionDto = widgetService.getNextQuestion(UUID.fromString("cec216e3-6822-4a6d-a27d-935034802285"), 
				UUID.fromString("788e1866-667d-4349-b4f9-023e01fd5c36"));
		
		assertNotNull(questionDto);
		assertEquals("Please tell us a bit about yourself", questionDto.getBody().getQuestion());
		assertEquals(QuestionType.valueOf("Matrix"),questionDto.getBody().getType());
	}

	/*
	 * Test saveQuestionResult() when data exists and is right value.
	 */
	@Test
	final void testSaveQuestionResult() {
		
		Optional<Question> qo = Optional.of(question);
		Optional<QuestionAnswer> qao = Optional.of(qa);
		
		QuestionResultDTO qrDto = new QuestionResultDTO();
		qrDto.setAnswerId(2L);
		qrDto.setQuestionId(1L);
		
		QuestionResult qr = QuestionResultDTO.transform(qrDto, site, qo.get(), qao.get(), 
											UUID.fromString("788e1866-667d-4349-b4f9-023e01fd5c36"));
		
		when(siteRepository.findByUuid(any(UUID.class))).thenReturn(site);
		when(questionRepository.findById(any(Long.class))).thenReturn(qo);
		when(qaRepository.findById(any(Long.class))).thenReturn(qao);
		when(qrRepository.save(any(QuestionResult.class))).thenReturn(qr);
		

		
		QuestionResultDTO testResult = widgetService.saveQuestionResult(UUID.fromString("cec216e3-6822-4a6d-a27d-935034802285"), 
							UUID.fromString("788e1866-667d-4349-b4f9-023e01fd5c36"), qrDto).getBody();
		
		verify(qrRepository, times(1)).save(any(QuestionResult.class));
		
		assertNotNull(testResult);
		assertEquals(qrDto.getQuestionId(), testResult.getQuestionId());
		assertEquals(qrDto.getAnswerId(), testResult.getAnswerId());

	}

	/*
	 * Test saveQuestionResult() when data does not exist.
	 */
	@Test
	final void testSaveQuestionNullResult() {
		
		Optional<Question> qo = Optional.of(question);
		Optional<QuestionAnswer> qao = Optional.of(qa);
		
		QuestionResultDTO qrDto = new QuestionResultDTO();
		qrDto.setAnswerId(2L);
		qrDto.setQuestionId(1L);
		
		QuestionResult qr = QuestionResultDTO.transform(qrDto, site, qo.get(), qao.get(), 
											UUID.fromString("788e1866-667d-4349-b4f9-023e01fd5c36"));
		
		when(siteRepository.findByUuid(any(UUID.class))).thenReturn(null);
		when(questionRepository.findById(any(Long.class))).thenReturn(qo);
		when(qaRepository.findById(any(Long.class))).thenReturn(qao);
		when(qrRepository.save(any(QuestionResult.class))).thenReturn(qr);
		

		
		QuestionResultDTO testResult = widgetService.saveQuestionResult(UUID.fromString("cec216e3-6822-4a6d-a27d-935034802285"), 
							UUID.fromString("788e1866-667d-4349-b4f9-023e01fd5c36"), qrDto).getBody();
		
		verify(qrRepository, times(0)).save(any(QuestionResult.class));
		
		assertNull(testResult);
	}
	
	// Custom exceptions will be tested like below
	@Disabled
	@Test
	final void testGetNextQuestion_Exception() {
		assertThrows(Exception.class, 
				()->{
					widgetService.getNextQuestion(UUID.fromString("cec216e3-6822-4a6d-a27d-935034802285"), 
							UUID.fromString("788e1866-667d-4349-b4f9-023e01fd5c36"));
				}
				
				);
	}
}
