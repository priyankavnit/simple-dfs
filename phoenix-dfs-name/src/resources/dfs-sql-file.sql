CREATE TABLE {tableName} (
	id VARCHAR(20) NOT NULL,
	parentId VARCHAR(20) NOT NULL,
	name VARCHAR(200) NOT NULL,
	status VARCHAR(10) NOT NULL,
	blockNum INT NOT NULL,
	minCopyNum INT NOT NULL,
	maxCopyNum INT NOT NULL,
	nowCopyNum INT NOT NULL,
	type VARCHAR(200) NOT NULL,
	version INT NOT NULL,
	PRIMARY KEY (id)
);

CREATE INDEX PID_INX ON {tableName} (parentId);

CREATE UNIQUE INDEX PID_NAME_UNI ON {tableName} (parentId,name);

