package com.cycapservers.news_reports;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsReportRepository extends CrudRepository<NewsReport, String> {
	
	@Query(value = "call GetRecentNews(:count);", nativeQuery = true)
	List<NewsReport> GetRecentNews(@Param("count") int count);
}
