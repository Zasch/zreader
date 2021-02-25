create sequence entry_id_seq increment by 1;
create sequence author_id_seq increment by 1;
create sequence feed_id_seq increment by 1;
create sequence enclosure_id_seq increment by 1;
create sequence link_id_seq increment by 1;
create sequence category_id_seq increment by 1;
create sequence content_id_seq increment by 1;



create table AUTHOR
(
    ID BIGINT auto_increment
        primary key,
    EMAIL VARCHAR(255),
    NAME VARCHAR(255),
    URI VARCHAR(255)
);

create table CATEGORY
(
    ID BIGINT auto_increment
        primary key,
    NAME VARCHAR(255),
    TAXONOMY_URI VARCHAR(255)
);

create table CONTENT
(
    ID BIGINT auto_increment
        primary key,
    MODE VARCHAR(255),
    TYPE VARCHAR(255),
    VALUE TEXT
);

create table ENCLOSURE
(
    ID BIGINT auto_increment
        primary key,
    LENGTH BIGINT,
    TYPE VARCHAR(255),
    URL VARCHAR(255)
);

create table ENTRY
(
    ID BIGINT auto_increment
        primary key,
    AUTHOR VARCHAR(255),
    COMMENTS VARCHAR(255),
    LINK VARCHAR(255),
    PUBLISHED_DATE TIMESTAMP,
    TITLE VARCHAR(255),
    UPDATED_DATE TIMESTAMP,
    URI VARCHAR(255),
    DESCRIPTION_ID BIGINT,
    TITLE_EX_ID BIGINT,
    constraint FK54TYXJT8PBN04U3F4MT0BO21C
        foreign key (TITLE_EX_ID) references CONTENT (ID),
    constraint FKNST0DS0Y36OR61E8KP5F753IO
        foreign key (DESCRIPTION_ID) references CONTENT (ID)
);

create table ENTRY_AUTHORS
(
    ENTRY_ID BIGINT not null,
    AUTHORS_ID BIGINT not null
        constraint UK_J8RBENBKC55DRWRMR0DAP3ERU
            unique,
    constraint FK7B99EX1J6TCWI613DPRBVBT1B
        foreign key (ENTRY_ID) references ENTRY (ID),
    constraint FKRGQ7F72U532NFBRNY5UDC830B
        foreign key (AUTHORS_ID) references AUTHOR (ID)
);

create table ENTRY_CATEGORIES
(
    ENTRY_ID BIGINT not null,
    CATEGORIES_ID BIGINT not null
        constraint UK_FULXL660A6SPXLYLORYRP3R40
            unique,
    constraint FKA1KOGO82WS782DL5IIKADPQ5J
        foreign key (CATEGORIES_ID) references CATEGORY (ID),
    constraint FKMDBACDRQGT03SJOQ3GNR1G8RM
        foreign key (ENTRY_ID) references ENTRY (ID)
);

create table ENTRY_CONTENTS
(
    ENTRY_ID BIGINT not null,
    CONTENTS_ID BIGINT not null
        constraint UK_AT3V13FEBLVXWCL7QE42FWR16
            unique,
    constraint FKB2U5H32CSWO1JIY3GHMBLLV05
        foreign key (CONTENTS_ID) references CONTENT (ID),
    constraint FKRHKQSLOKMXNO693R8AR27PJ7C
        foreign key (ENTRY_ID) references ENTRY (ID)
);

create table ENTRY_CONTRIBUTORS
(
    ENTRY_ID BIGINT not null,
    CONTRIBUTORS_ID BIGINT not null
        constraint UK_PUAFL1IBURBOLUP5NHL7PQ08N
            unique,
    constraint FK9T6657C53M40VV3DCL71GOOE6
        foreign key (ENTRY_ID) references ENTRY (ID),
    constraint FKGJ1P9O6IBAOEDMU3REUFQHMCY
        foreign key (CONTRIBUTORS_ID) references AUTHOR (ID)
);

create table ENTRY_ENCLOSURES
(
    ENTRY_ID BIGINT not null,
    ENCLOSURES_ID BIGINT not null
        constraint UK_JC4MNDQFUOL2XWAAYY93AC9FK
            unique,
    constraint FKEM88U364UNEL2CT4B05U2L5HM
        foreign key (ENCLOSURES_ID) references ENCLOSURE (ID),
    constraint FKR9E945VJIP8STBIB22KWB5XTF
        foreign key (ENTRY_ID) references ENTRY (ID)
);

create table FEED
(
    ID BIGINT auto_increment
        primary key,
    TITLE VARCHAR(255),
    URL VARCHAR(255)
);

create table LINK
(
    ID BIGINT auto_increment
        primary key,
    HREF VARCHAR(255),
    HREFLANG VARCHAR(255),
    LENGTH BIGINT,
    REL VARCHAR(255),
    TITLE VARCHAR(255),
    TYPE VARCHAR(255)
);

create table ENTRY_LINKS
(
    ENTRY_ID BIGINT not null,
    LINKS_ID BIGINT not null
        constraint UK_MK1553ELHX57U6TQWLGSFHU3N
            unique,
    constraint FK2WH33OXBUW6C7YI0PKEA7U6P3
        foreign key (LINKS_ID) references LINK (ID),
    constraint FKDIUI66V8C2RFXJD1MECERL6WI
        foreign key (ENTRY_ID) references ENTRY (ID)
);

