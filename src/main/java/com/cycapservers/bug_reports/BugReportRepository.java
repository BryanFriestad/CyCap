package com.cycapservers.bug_reports;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugReportRepository extends CrudRepository<BugReport, String> {
	
}
