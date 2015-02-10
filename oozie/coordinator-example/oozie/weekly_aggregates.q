INSERT INTO TABLE weekly_aggregate_table
	SELECT	unix_timestamp(),
			AVG(a) AS average_a, SUM(a) AS sum_a, 
			AVG(b) AS average_b, SUM(b) AS sum_b,
			AVG(c) AS average_c, SUM(c) AS sum_c,
			AVG(d) AS average_d, SUM(d) AS sum_d
	FROM daily_data_table;
