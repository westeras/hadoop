DROP TABLE IF EXISTS daily_data_table;
DROP TABLE IF EXISTS daily_data_table;

CREATE EXTERNAL TABLE daily_data_table (
	a			INT,
	b			INT,
	c			INT,
	d			INT)

	LOCATION "coordinator-example/landing/";

CREATE TABLE weekly_aggregate_table (
	date		BIGINT,
	average_a	DOUBLE,
	sum_a		INT,
	average_b	DOUBLE,
	sum_b		INT,
	average_c	DOUBLE,
	sum_c		INT,
	average_d	DOUBLE,
	sum_d		INT);
