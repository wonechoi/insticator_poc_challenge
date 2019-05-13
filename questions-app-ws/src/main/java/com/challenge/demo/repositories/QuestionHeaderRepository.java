package com.challenge.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.demo.Entities.QuestionHeader;

public interface QuestionHeaderRepository extends JpaRepository<QuestionHeader, Long>{

}
