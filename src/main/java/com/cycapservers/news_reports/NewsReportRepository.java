package com.cycapservers.news_reports;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsReportRepository extends CrudRepository<NewsReport, String> {
	
}
