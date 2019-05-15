package com.challenge.demo.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;


import com.challenge.demo.Entities.Question;
import com.challenge.demo.Entities.QuestionType;
import com.challenge.demo.Entities.Site;

public class QuestionDTO {

	private Long questionId;

	private Long siteId;

	private String question;

	private QuestionType type;
	
	private List<QuestionHeaderDTO> headers = new ArrayList<>();

	private List<QuestionAnswerDTO> answers = new ArrayList<>();
	
	private Date createdAt;

	private Date updatedAt;

	public static QuestionDTO build(Question question) {
		
		
		ModelMapper modelMapper = new ModelMapper();
		Hibernate.initialize(question.getHeaders());
		final QuestionDTO obj = modelMapper.map(question, QuestionDTO.class);	
		
		return obj;
	}

	public static List<QuestionDTO> build(List<Question> questions) {
		final List<QuestionDTO> ret = new ArrayList<>();

		for (Question question : questions) {
			ret.add(build(question));
		}

		return ret;
	}

	public static Question createQuestion(final QuestionDTO incomingQuestion, final Site site) {
		final Question newQ = new Question();
		newQ.setSite(site);
		newQ.setQuestion(incomingQuestion.getQuestion());
		newQ.setType(incomingQuestion.getType());
		return newQ;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(final Long siteId) {
		this.siteId = siteId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(final String question) {
		this.question = question;
	}
	
	public QuestionType getType() {
		return type;
	}

	public void setType(QuestionType type) {
		this.type = type;
	}

	public List<QuestionHeaderDTO> getHeaders() {
		return headers;
	}

	public void setHeaders(List<QuestionHeaderDTO> headers) {
		this.headers = headers;
	}

	public List<QuestionAnswerDTO> getAnswers() {
		return answers;
	}

	public void setAnswers(List<QuestionAnswerDTO> answers) {
		this.answers = answers;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(final Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(final Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(final Long questionId) {
		this.questionId = questionId;
	}
}
