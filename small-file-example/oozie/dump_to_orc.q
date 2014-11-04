INSERT INTO TABLE managed_landing
	PARTITION (date=to_date(from_unixtime(unix_timestamp())))
	SELECT * FROM external_staging_temporary;
