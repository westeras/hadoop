set hive.exec.dynamic.partition.mode=nonstrict;

INSERT INTO TABLE managed_landing
	PARTITION (date)
	SELECT *, to_date(from_unixtime(unix_timestamp())) FROM external_staging_temporary;
