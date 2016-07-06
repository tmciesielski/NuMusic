/* PHASE 1 */
drop table APP.RAW_XM_GARBAGE;
drop table APP.RAW_XM_SONGS;
drop table APP.XM_SONGS;


create table APP.RAW_XM_SONGS (
ID integer not null generated always as identity (start with 1, increment by 1),
SOURCE varchar(255),
ARTIST varchar(255),
TITLE varchar(255),
SONG varchar(255),
DATE timestamp,
TWITTER_ID bigint,
unique (ID)
)


create table APP.RAW_XM_GARBAGE (
ID integer not null generated always as identity (start with 1, increment by 1),
SOURCE varchar(255),
ARTIST varchar(255),
TITLE varchar(255),
SONG varchar(255),
DATE timestamp,
TWITTER_ID bigint,
unique (ID)
)


create table APP.XM_SONGS (
ID integer not null generated always as identity (start with 1, increment by 1),
ARTIST varchar(255),
TITLE varchar(255),
SONG varchar(255),
NEWEST_DATE timestamp,
OLDEST_DATE timestamp,
PLAY_COUNT int,
unique (ID)
)


create table APP.YOUTUBE_LINKS (
ID integer not null generated always as identity (start with 1, increment by 1),
SONG_NAME varchar(255), 
YOUTUBE_LINK varchar(255),
YOUTUBE_TITLE varchar(255),
VIEW_COUNT integer,
NEWEST_DATE timestamp,
OLDEST_DATE timestamp,
PLAY_COUNT int,
unique (ID)
)

drop table APP.YOUTUBE_LINKS

delete FROM APP.YOUTUBE_LINKS WHERE ID > 1

select * from APP.YOUTUBE_LINKS ORDER BY SONG_NAME


select count(*) FROM APP.RAW_XM_SONGS
select * FROM APP.RAW_XM_GARBAGE
select TOP 10 PERCENT * FROM APP.XM_SONGS ORDER BY PLAY_COUNT DESC, NEWEST_DATE DESC
SELECT * FROM APP.XM_SONGS ORDER BY RANDOM() OFFSET 0 ROWS FETCH NEXT 200 ROWS ONLY