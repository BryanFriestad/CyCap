package com.cycapservers.player_reporting;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerReportRepository extends CrudRepository<PlayerReport, String> {
	
}
