-- ----------- Table for validation queries from the connection pool. -----------

CREATE TABLE PingTable (foo CHAR(1));

-- ------------------------------ CREATE TABLES ---------------------------------

-- ----------------------------- User Profile -----------------------------------

CREATE TABLE UserProfile (userID VARCHAR(20) NOT NULL, password VARCHAR(11) NOT NULL,
    firstName VARCHAR(20) NOT NULL, surname VARCHAR(40) NOT NULL, email VARCHAR(40) NOT NULL,
    localeID VARCHAR(8), version BIGINT DEFAULT 0,
    CONSTRAINT UserProfilePK PRIMARY KEY (userID));

-- --------------------------------- WMS ----------------------------------------

CREATE SEQUENCE WMSSeq;

CREATE TABLE WMS (WMSID BIGINT NOT NULL, name VARCHAR(20) NOT NULL, description VARCHAR(50),
	URL VARCHAR(100) NOT NULL, userID VARCHAR(20) NOT NULL, version BIGINT DEFAULT 0,
	CONSTRAINT WMSPK PRIMARY KEY (WMSID),
    CONSTRAINT WMSUserProfileFK FOREIGN KEY (userID)
    	REFERENCES UserProfile (userID) ON DELETE CASCADE);

-- ---------------------------- InterestedUsersWMS --------------------------------

CREATE TABLE InterestedUsersWMS (userID VARCHAR(20) NOT NULL,
	WMSID BIGINT NOT NULL,
	CONSTRAINT InterestedUsersWMSPK PRIMARY KEY (userID, WMSID),
	CONSTRAINT InterestedUsersWMSUserProfileFK FOREIGN KEY (userID)
		REFERENCES UserProfile (userID),
	CONSTRAINT InterestedUsersWMSWMSFK FOREIGN KEY (WMSID)
		REFERENCES WMS (WMSID));

-- -------------------------------- Layer ---------------------------------------

CREATE SEQUENCE LayerSeq;

CREATE TABLE Layer (layerID BIGINT NOT NULL, title VARCHAR(200), name VARCHAR(200) NOT NULL, 
	styleTitle VARCHAR(200), styleName VARCHAR(200),
    minLatitude DOUBLE PRECISION, minLongitude DOUBLE PRECISION, 
    maxLatitude DOUBLE PRECISION, maxLongitude DOUBLE PRECISION,
	WMSID BIGINT NOT NULL, version BIGINT DEFAULT 0,
    CONSTRAINT LayerPK PRIMARY KEY (layerID),
    CONSTRAINT LayerWMSFK FOREIGN KEY (WMSID)
    	REFERENCES WMS (WMSID) ON DELETE CASCADE);

-- --------------------------------- Tag ----------------------------------------

CREATE TABLE Tag (tag VARCHAR(20) NOT NULL, version BIGINT DEFAULT 0,
	CONSTRAINT TagPK PRIMARY KEY (tag));
    
-- --------------------------------- Map ----------------------------------------

CREATE SEQUENCE MapSeq;

CREATE TABLE Map (mapID BIGINT NOT NULL, name VARCHAR(20) NOT NULL, description VARCHAR(50),
    minLatitude DOUBLE PRECISION NOT NULL, minLongitude DOUBLE PRECISION NOT NULL, 
    maxLatitude DOUBLE PRECISION NOT NULL, maxLongitude DOUBLE PRECISION NOT NULL, 
    userID VARCHAR(20) NOT NULL, version BIGINT DEFAULT 0,
    CONSTRAINT MapPK PRIMARY KEY (mapID),
    CONSTRAINT MapUserProfileFK FOREIGN KEY (userID)
    	REFERENCES UserProfile (userID) ON DELETE CASCADE);

-- --------------------------------- MapTag ----------------------------------------

CREATE TABLE MapTag (tag VARCHAR(20) NOT NULL, mapID BIGINT NOT NULL,
	CONSTRAINT MapTagPK PRIMARY KEY (tag, mapID),
	CONSTRAINT MapTagTagFK FOREIGN KEY (tag)
    	REFERENCES Tag (tag) ON DELETE CASCADE,
	CONSTRAINT MapTagMapFK FOREIGN KEY (mapID)
    	REFERENCES Map (mapID) ON DELETE CASCADE);

-- --------------------------------- LayerMap -------------------------------------

CREATE TABLE LayerMap (layerID BIGINT NOT NULL, mapID BIGINT NOT NULL,
	CONSTRAINT LayerMapPK PRIMARY KEY (layerID, mapID),
	CONSTRAINT LayerMapLayerFK FOREIGN KEY (layerID)
		REFERENCES Layer (layerID),
	CONSTRAINT LayerMapMapFK FOREIGN KEY (mapID)
		REFERENCES Map (mapID));

-- ---------------------------- InterestedUsersMap --------------------------------

CREATE TABLE InterestedUsersMap (userID VARCHAR(20) NOT NULL,
	mapID BIGINT NOT NULL,
	CONSTRAINT InterestedUsersMapPK PRIMARY KEY (userID, mapID),
	CONSTRAINT InterestedUsersMapUserProfileFK FOREIGN KEY (userID)
		REFERENCES UserProfile (userID),
	CONSTRAINT InterestedUsersMapMapFK FOREIGN KEY (mapID)
		REFERENCES Map (mapID));
    	
-- --------------------------------- POI ----------------------------------------

CREATE SEQUENCE POISeq;

CREATE TABLE POI (POIID BIGINT NOT NULL, name VARCHAR(20) NOT NULL, description VARCHAR(50),
	latitude DOUBLE PRECISION NOT NULL, longitude DOUBLE PRECISION NOT NULL, HTML VARCHAR(1000) NOT NULL,
	userID VARCHAR(20) NOT NULL, version BIGINT DEFAULT 0,
	CONSTRAINT POIPK PRIMARY KEY (POIID),
	CONSTRAINT POIUserProfileFK FOREIGN KEY (userID)
    	REFERENCES UserProfile (userID) ON DELETE CASCADE);

-- --------------------------------- POITag ----------------------------------------

CREATE TABLE POITag (tag VARCHAR(20) NOT NULL, POIID BIGINT NOT NULL,
	CONSTRAINT POITagPK PRIMARY KEY (tag, POIID),
	CONSTRAINT POITagTagFK FOREIGN KEY (tag)
    	REFERENCES Tag (tag) ON DELETE CASCADE,
	CONSTRAINT POITagPOIFK FOREIGN KEY (POIID)
    	REFERENCES POI (POIID) ON DELETE CASCADE);
	
-- ---------------------------- InterestedUsersPOI --------------------------------

CREATE TABLE InterestedUsersPOI (userID VARCHAR(20) NOT NULL,
	POIID BIGINT NOT NULL,
	CONSTRAINT InterestedUsersPOIPK PRIMARY KEY (userID, POIID),
	CONSTRAINT InterestedUsersPOIUserProfileFK FOREIGN KEY (userID)
		REFERENCES UserProfile (userID),
	CONSTRAINT InterestedUsersPOIPOIFK FOREIGN KEY (POIID)
		REFERENCES POI (POIID));
		
-- ------------------------------ INSERT ROWS -----------------------------------

INSERT INTO UserProfile VALUES('admin', 'pexzg3FUZAk', 'MapViewer', 'administrator',
	'admin@mapviewer.com', 'es,ES', '0');
