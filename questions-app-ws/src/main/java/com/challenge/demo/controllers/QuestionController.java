package com.challenge.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.demo.Entities.Question;
import com.challenge.demo.Entities.QuestionAnswer;
import com.challenge.demo.Entities.QuestionHeader;
import com.challenge.demo.dto.QuestionAnswerDTO;
import com.challenge.demo.dto.QuestionDTO;
import com.challenge.demo.dto.QuestionHeaderDTO;
import com.challenge.demo.repositories.QuestionAnswerRepository;
import com.challenge.demo.repositories.QuestionHeaderRepository;
import com.challenge.demo.repositories.QuestionRepository;
import com.challenge.demo.repositories.SiteRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	SiteRepository siteRepository;
	
	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	QuestionAnswerRepository qaRepository;

	@Autowired
	QuestionHeaderRepository qhRepository;
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionDTO incomingQuestion) {
		return siteRepository
				.findById(incomingQuestion.getSiteId())
				.map(site -> {
					final Question newQ = QuestionDTO.createQuestion(incomingQuestion, site);
					return new ResponseEntity<>(QuestionDTO.build(questionRepository.save(newQ)), HttpStatus.CREATED);
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@Transactional
	@GetMapping()
	public ResponseEntity<List<QuestionDTO>> getQuestions() {
		return Optional
				.ofNullable(questionRepository.findAll())
				.map(questions -> ResponseEntity.ok(QuestionDTO.build(questions)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<QuestionDTO> updateQuestion(@RequestBody Question incomingQuestion, @PathVariable(value = "id") Long questionId) {

		return questionRepository
				.findById(questionId)
				.map(question -> {
					question.setQuestion(incomingQuestion.getQuestion());
					question.setSite(incomingQuestion.getSite());
					return new ResponseEntity<>(QuestionDTO.build(questionRepository.save(question)), HttpStatus.OK);
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	/*
	 * Change
	 * - delete related Question Headers, Answers, Results together.
	 */
	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<QuestionDTO> deleteQuestion(@PathVariable(value = "id") Long questionId) {
		return questionRepository
				.findById(questionId)
				.map(question -> {
					qaRepository.deleteByQuestionId(questionId);
					qhRepository.deleteByQuestionId(questionId);
					questionRepository.deleteById(questionId);
					return ResponseEntity.ok(QuestionDTO.build(question));
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/{id}")
	public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable(value = "id") Long questionId) {
		return questionRepository
				.findById(questionId)
				.map(question -> ResponseEntity.ok(QuestionDTO.build(question)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	/*
	 * Add
	 * - Create new QuestionHeader
	 * - Create Get headers with question id 
	 * - Delete QuestionHeaders with header id
	 */
	@PostMapping("/{id}/headers")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<QuestionHeaderDTO> createQuestionHeaders(@PathVariable(value = "id") Long questionId,
																   @RequestBody QuestionHeaderDTO newQHDto) {
		return questionRepository
				.findById(questionId)
				.map(question -> {
					final QuestionHeader newQh = QuestionHeaderDTO.transform(newQHDto, question);
					return new ResponseEntity<>(QuestionHeaderDTO.build(qhRepository.save(newQh)), HttpStatus.CREATED);
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/{id}/headers")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<QuestionHeaderDTO>> getQuestionHeaders(@PathVariable(value = "id") Long questionId) {
		return questionRepository
				.findById(questionId)
				.map(question -> ResponseEntity.ok(QuestionHeaderDTO.build(question.getHeaders())))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}/headers")
	public ResponseEntity<QuestionHeaderDTO> deleteQuestionHeader(@PathVariable(value = "id") Long questionHeaderId) {
		return qhRepository
				.findById(questionHeaderId)
				.map(questionHeader -> {
					qhRepository.deleteById(questionHeaderId);
					return ResponseEntity.ok(QuestionHeaderDTO.build(questionHeader));
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PostMapping("/{id}/answers")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<QuestionAnswerDTO> createQuestionAnswers(@PathVariable(value = "id") Long questionId,
																   @RequestBody QuestionAnswerDTO newQADto) {
		return questionRepository
				.findById(questionId)
				.map(question -> {
					final QuestionAnswer newQa = QuestionAnswerDTO.transform(newQADto, question);
					return new ResponseEntity<>(QuestionAnswerDTO.build(qaRepository.save(newQa)), HttpStatus.CREATED);
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/{id}/answers")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<QuestionAnswerDTO>> getQuestionAnswers(@PathVariable(value = "id") Long questionId) {
		return questionRepository
				.findById(questionId)
				.map(question -> ResponseEntity.ok(QuestionAnswerDTO.build(question.getAnswers())))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	/*
	 * Add 
	 * - Delete Answers with answer id
	 */
	@DeleteMapping("/{id}/answers")
	public ResponseEntity<QuestionAnswerDTO> deleteQuestionAnswer(@PathVariable(value = "id") Long questionAnswerId) {
		return qaRepository
				.findById(questionAnswerId)
				.map(questionAnswer -> {
					qaRepository.deleteById(questionAnswerId);
					return ResponseEntity.ok(QuestionAnswerDTO.build(questionAnswer));
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
}