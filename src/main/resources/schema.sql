create table PRODUCT(
  ID int not null,
  NAME varchar(100) not null,
  PRIMARY KEY ( ID )

);
create table OFFER(
  ID int not null,
  VALID_FROM TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRICE numeric (5,2),
  PRODUCT_ID  int not null,
  PRIMARY KEY ( ID ),
  CONSTRAINT fk_PRODUCT_ID_OFFER
  FOREIGN KEY (PRODUCT_ID)
  REFERENCES PRODUCT(ID)
);

create table SIZE(
  SIZE_ID INT not null,
  SIZE varchar(1) not null,
  AVAILABILITY boolean,
  LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRODUCT_ID int not null,
  PRIMARY KEY ( SIZE_ID ),
  CONSTRAINT fk_PRODUCT_ID_SIZE
  FOREIGN KEY (PRODUCT_ID)
  REFERENCES PRODUCT(ID)
 
);




