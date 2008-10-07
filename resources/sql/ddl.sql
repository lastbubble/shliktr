-- VERSION = $Id$
create table entry (
    id integer not null auto_increment,
    games_lost integer,
    games_won integer,
    lost integer,
    score integer,
    tiebreaker integer not null default 0,
    player_id integer not null,
    week_id integer not null,
    primary key (id)
);

create table game (
    id integer not null auto_increment,
    away_score integer not null default 0,
    home_score integer not null default 0,
    played_on datetime not null,
    away_team_id integer not null,
    home_team_id integer not null,
    week_id integer not null,
    primary key (id)
);

create table pick (
    id integer not null auto_increment,
    ranking integer,
    winner varchar(4),
    game_id integer not null,
    entry_id integer not null,
    primary key (id)
);

create table player (
    id integer not null auto_increment,
    active bit,
    email varchar(255),
    name varchar(32) not null unique,
    password varchar(32),
    username varchar(16) not null unique,
    primary key (id)
);

create table team (
    id integer not null auto_increment,
    abbr varchar(3) not null unique,
    location varchar(16) not null,
    name varchar(16) not null unique,
    primary key (id)
);

create table week (
    id integer not null,
    tiebreaker varchar(255),
    tiebreaker_answer integer,
    primary key (id)
);

alter table entry 
    add index FK5C308725CEA074F (player_id), 
    add constraint FK5C308725CEA074F 
    foreign key (player_id) 
    references player (id);

alter table entry 
    add index FK5C308729E5453AF (week_id), 
    add constraint FK5C308729E5453AF 
    foreign key (week_id) 
    references week (id);

alter table game 
    add index FK304BF2BD921BAF (home_team_id), 
    add constraint FK304BF2BD921BAF 
    foreign key (home_team_id) 
    references team (id);

alter table game 
    add index FK304BF250C38ABE (away_team_id), 
    add constraint FK304BF250C38ABE 
    foreign key (away_team_id) 
    references team (id);

alter table game 
    add index FK304BF29E5453AF (week_id), 
    add constraint FK304BF29E5453AF 
    foreign key (week_id) 
    references week (id);

alter table pick 
    add index FK3480218D7D869 (entry_id), 
    add constraint FK3480218D7D869 
    foreign key (entry_id) 
    references entry (id);

alter table pick 
    add index FK3480214983EAEF (game_id), 
    add constraint FK3480214983EAEF 
    foreign key (game_id) 
    references game (id);
