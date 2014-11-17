SELECT AVG(a) FROM (SELECT PERCENTILE(a, array(0.1, 0.9)) AS q FROM test) src JOIN test
	WHERE a > src.q[0] AND a < src.q[1];
