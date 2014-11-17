--SELECT AVG(a) FROM 
--	(SELECT PERCENTILE(a, 0.2) AS p1, PERCENTILE(a, 0.8) AS p2
--		FROM test) percentiles JOIN test
--	WHERE a > percentiles.p1 AND a < percentiles.p2

SELECT AVG(a) FROM test
	WHERE a > (SELECT PERCENTILE(a, 0.2) FROM test) AND a < (SELECT PERCENTILE(a, 0.8) FROM test);
