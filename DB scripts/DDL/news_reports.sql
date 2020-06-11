use db309sd1;
#drop table if exists news_reports;
create table news_reports(
	id int NOT NULL auto_increment,
	posting_dev varchar(255) NOT NULL,
    post_date date NOT NULL,
    hypertext varchar(2500) NOT NULL,
    primary key (id),
    foreign key (posting_dev) references account (UserID) on delete no action on update cascade
);

drop procedure if exists GetRecentNews;
DELIMITER //
create procedure GetRecentNews(in count int)
	BEGIN
	select * from news_reports
    order by posting_dev asc
    limit count;
	END //
DELIMITER ;