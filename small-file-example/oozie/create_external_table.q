CREATE EXTERNAL TABLE external_staging_temporary (
	c1	INT,
	c2	INT,
	c3	INT,
	c4	INT,
	c5	INT)

	ROW FORMAT DELIMITED
		FIELDS TERMINATED BY ','

	LOCATION '/user/root/temporary/';
