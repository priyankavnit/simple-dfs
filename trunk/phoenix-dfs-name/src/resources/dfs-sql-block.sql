CREATE TABLE {tableName} (
	id varchar(20) NOT NULL,
	nodeId varchar(20) NOT NULL,
	fileId varchar(20) NOT NULL,
	status varchar(10) NOT NULL,
	blockNo int(11) NOT NULL,
	copyNo int(11) NOT NULL,
	capacity int(11) NOT NULL,
	size int(11) NOT NULL,
	PRIMARY KEY (id) 
);

CREATE INDEX INX_FILE ON {tableName} (fileId);