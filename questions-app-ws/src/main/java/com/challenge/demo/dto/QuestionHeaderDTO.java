package com.challenge.demo.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.challenge.demo.Entities.Question;
import com.challenge.demo.Entities.QuestionHeader;

public class QuestionHeaderDTO {

	private Long id;

	private Long questionId;

	private String header;

	private Date createdAt;

	private Date updatedAt;
	
	public static QuestionHeader transform(final QuestionHeaderDTO newQHDto, final Question question) {
		final QuestionHeader newQh = new QuestionHeader();
		newQh.setHeader(newQHDto.getHeader());
		newQh.setQuestion(question);

		return newQh;
	}

	public static QuestionHeaderDTO build(final QuestionHeader save) {
		final QuestionHeaderDTO newQhDto = new QuestionHeaderDTO();

		newQhDto.setId(save.getId());
		newQhDto.setHeader(save.getHeader());
		newQhDto.setCreatedAt(save.getCreatedAt());
		newQhDto.setUpdatedAt(save.getUpdatedAt());
		newQhDto.setQuestionId(save.getQuestion().getQuestionId());

		return newQhDto;
	}

	public static List<QuestionHeaderDTO> build(final List<QuestionHeader> headers) {
		final List<QuestionHeaderDTO> ret = new ArrayList<>();
		for (QuestionHeader qh : headers) {
			ret.add(build(qh));
		}
		return ret;
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

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
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
