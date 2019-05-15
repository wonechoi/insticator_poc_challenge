package com.challenge.demo.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.demo.dto.QuestionDTO;
import com.challenge.demo.dto.QuestionResultDTO;
import com.challenge.demo.service.WidgetService;

@RestController
@RequestMapping("/widgets")
public class WidgetController {

	@Autowired
	WidgetService widgetService;
	
	@GetMapping(path="/{siteUUID}/{userUUID}", 
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public QuestionDTO getSites(@PathVariable(value = "siteUUID") String siteUUID, @PathVariable(value = "userUUID") String userUUID) {
		
		return  widgetService.getNextQuestion(UUID.fromString(siteUUID), UUID.fromString(userUUID));
		

	}
	
	@PostMapping(path="/{siteUUID}/{userUUID}", 
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<QuestionResultDTO> saveResult(@PathVariable(value = "siteUUID") String siteUUID, @PathVariable(value = "userUUID") String userUUID,
			@RequestBody QuestionResultDTO questionResultDto) {

		return widgetService.saveQuestionResult(UUID.fromString(siteUUID), UUID.fromString(userUUID), questionResultDto);
	
	}
}
