package com.challenge.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.challenge.demo.Entities.Site;

import java.util.UUID;

public interface SiteRepository extends JpaRepository<Site, Long> {

	@Query(value = "SELECT s.* FROM Site s WHERE s.site_uuid = ?1", nativeQuery = true)
	Site findByUuid(UUID siteUUID);

}