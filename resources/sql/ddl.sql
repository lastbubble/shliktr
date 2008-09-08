-- VERSION = $Id$
    create table game (
        id integer not null auto_increment,
        away_score integer default 0,
        home_score integer default 0,
        played_on datetime not null,
        away_team_id integer not null,
        home_team_id integer not null,
        week_id integer not null,
        primary key (id)
    );

    create table pick (
        id integer not null auto_increment,
        ranking integer not null,
        winner varchar(4),
        game_id integer not null,
        primary key (id)
    );

    create table picks (
        id integer not null auto_increment,
        tiebreaker integer not null,
        player_id integer not null,
        week_id integer not null,
        primary key (id)
    );

    create table picks_pick (
        picks_id integer not null,
        pick_id integer not null,
        unique (pick_id)
    );

    create table player (
        id integer not null auto_increment,
        active bit not null,
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

    alter table game 
        add index FK304BF2D931B1D1 (home_team_id), 
        add constraint FK304BF2D931B1D1 
        foreign key (home_team_id) 
        references team (id);

    alter table game 
        add index FK304BF26C6320E0 (away_team_id), 
        add constraint FK304BF26C6320E0 
        foreign key (away_team_id) 
        references team (id);

    alter table game 
        add index FK304BF2B9F3E9D1 (week_id), 
        add constraint FK304BF2B9F3E9D1 
        foreign key (week_id) 
        references week (id);

    alter table pick 
        add index FK34802165238111 (game_id), 
        add constraint FK34802165238111 
        foreign key (game_id) 
        references game (id);

    alter table picks 
        add index FK65B8472EFC9CF1 (player_id), 
        add constraint FK65B8472EFC9CF1 
        foreign key (player_id) 
        references player (id);

    alter table picks 
        add index FK65B8472B9F3E9D1 (week_id), 
        add constraint FK65B8472B9F3E9D1 
        foreign key (week_id) 
        references week (id);

    alter table picks_pick 
        add index FKC22EABCE4E5C5EB1 (pick_id), 
        add constraint FKC22EABCE4E5C5EB1 
        foreign key (pick_id) 
        references pick (id);

    alter table picks_pick 
        add index FKC22EABCE7D386AC3 (picks_id), 
        add constraint FKC22EABCE7D386AC3 
        foreign key (picks_id) 
        references picks (id);
