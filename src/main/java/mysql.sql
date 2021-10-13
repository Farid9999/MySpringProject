
create DATABASE USERS
 USE USERS


create table APP_USER
(
    USER_ID           BIGINT not null,
    USER_NAME         VARCHAR(36) not null,
    ENCRYTED_PASSWORD VARCHAR(128) not null,
    ENABLED           BIT not null
) ;
--
alter table APP_USER
    add constraint APP_USER_PK primary key (USER_ID);

alter table APP_USER
    add constraint APP_USER_UK unique (USER_NAME);


-- Create table
create table APP_ROLE
(
    ROLE_ID   BIGINT not null,
    ROLE_NAME VARCHAR(30) not null
) ;
--
alter table APP_ROLE
    add constraint APP_ROLE_PK primary key (ROLE_ID);

alter table APP_ROLE
    add constraint APP_ROLE_UK unique (ROLE_NAME);


-- Create table
create table USER_ROLE
(
    ID      BIGINT not null,
    USER_ID BIGINT not null,
    ROLE_ID BIGINT not null
);
--
alter table USER_ROLE
    add constraint USER_ROLE_PK primary key (ID);

alter table USER_ROLE
    add constraint USER_ROLE_UK unique (USER_ID, ROLE_ID);

alter table USER_ROLE
    add constraint USER_ROLE_FK1 foreign key (USER_ID)
        references APP_USER (USER_ID)  on delete cascade;

alter table USER_ROLE
    add constraint USER_ROLE_FK2 foreign key (ROLE_ID)
        references APP_ROLE (ROLE_ID);


-- Used by Spring Remember Me API.
CREATE TABLE Persistent_Logins (

                                   username varchar(64) not null,
                                   series varchar(64) not null,
                                   token varchar(64) not null,
                                   last_used timestamp not null,
                                   PRIMARY KEY (series)

);


insert into app_role (ROLE_ID, ROLE_NAME)
values (1, 'ROLE_SuperAdmin');

insert into app_role (ROLE_ID, ROLE_NAME)
values (2, 'ROLE_Admin');

insert into app_role (ROLE_ID, ROLE_NAME)
values (3, 'ROLE_User');

---


--------------------------------------

insert into App_User (USER_ID, USER_NAME, ENCRYTED_PASSWORD, ENABLED)
values (1, 'Farid', '$2a$10$VBccj9CZMEYcJudY8QxFGel37NLErH73sUTSFLUeziS9f1YITNWPC', 1);



insert into user_role (ID, USER_ID, ROLE_ID)
values (1, 1, 1);
