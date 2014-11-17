CREATE TABLE test (
	id	INT,
	a	INT,
	b	INT)

	ROW FORMAT DELIMITED
		FIELDS TERMINATED BY ','

	LOCATION '/user/root/testdat.csv';
