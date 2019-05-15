package com.challenge.demo.dto;

import java.util.Date;
import java.util.UUID;

import com.challenge.demo.Entities.Question;
import com.challenge.demo.Entities.QuestionAnswer;
import com.challenge.demo.Entities.QuestionResult;
import com.challenge.demo.Entities.Site;

public class QuestionResultDTO {

	private Long id;

	private Long questionId;

	private Long answerId;

	private Date createdAt;

	private Date updatedAt;

	public static QuestionResult transform(final QuestionResultDTO qrDto, final Site site, final Question question, final QuestionAnswer qa, UUID userUUID) {
		final QuestionResult newQr = new QuestionResult();
		newQr.setSite(site);
		newQr.setUserUUID(userUUID);
		newQr.setQuestion(question);
		newQr.setAnwser(qa);
		
		return newQr;
	}
	
	public static QuestionResultDTO build(QuestionResult save) {
		final QuestionResultDTO newQrDto = new QuestionResultDTO();

		newQrDto.setId(save.getId());
		newQrDto.setQuestionId(save.getQuestion().getQuestionId());
		newQrDto.setAnswerId(save.getAnwser().getId());
		newQrDto.setCreatedAt(save.getCreatedAt());
		newQrDto.setUpdatedAt(save.getUpdatedAt());

		return newQrDto;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
