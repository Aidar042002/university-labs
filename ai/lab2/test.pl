%Название
game(minecraft).
game(cyberpunk_2077).
game(the_witcher_3).
game(fortnite).
game(apex_legends).
game(overwatch).
game(hollow_knight).
game(stalker).
game(stardew_valley).
game(tetris).
game(super_mario).
game(battlefield).
game(the_last_of_us).
game(dark_souls).
game(fifa24).
game(god_of_war).
game(skyrim).
game(red_dead_detemption).
game(dota_2).
game(league_of_legends).

%Жанры
genre(action).
genre(adventure).
genre(role_playing).
genre(shooter).
genre(strategy).
genre(puzzle).
genre(sports).
genre(platformer).
genre(survival).
genre(simulation).

%Имя и жанр
has_genre(minecraft, survival).
has_genre(cyberpunk_2077, role_playing).
has_genre(the_witcher_3, role_playing).
has_genre(fortnite, shooter).
has_genre(apex_legends, shooter).
has_genre(overwatch, shooter).
has_genre(hollow_knight, platformer).
has_genre(stalker, shooter).
has_genre(stardew_valley, simulation).
has_genre(tetris, puzzle).
has_genre(super_mario, platformer).
has_genre(battlefield, shooter).
has_genre(the_last_of_us, action).
has_genre(dark_souls, action).
has_genre(fifa_24, sports).
has_genre(god_of_war, action).
has_genre(skyrim, role_playing).
has_genre(red_dead_redemption, adventure).
has_genre(dota_2, strategy).
has_genre(league_of_legends, strategy).

%Дата релиза
release_year(minecraft, 2011).
release_year(cyberpunk_2077, 2020).
release_year(the_witcher_3, 2015).
release_year(fortnite, 2017).
release_year(apex_legends, 2019).
release_year(overwatch, 2016).
release_year(hollow_knight, 2017).
release_year(stalker, 2007).
release_year(stardew_valley, 2016).
release_year(tetris, 1984).
release_year(super_mario, 1985).
release_year(battlefield, 2002).
release_year(the_last_of_us, 2013).
release_year(dark_souls, 2011).
release_year(fifa_24, 2023).
release_year(god_of_war, 2018).
release_year(skyrim, 2011).
release_year(red_dead_redemption, 2010).
release_year(dota_2, 2013).
release_year(league_of_legends, 2009).

%Разработчик
developer(minecraft, mojang).
developer(cyberpunk_2077, cd_projekt_red).
developer(the_witcher_3, cd_projekt_red).
developer(fortnite, epic_games).
developer(apex_legends, respawn_entertainment).
developer(overwatch, blizzard_entertainment).
developer(hollow_knight, team_cherry).
developer(stalker, gsc_game_world).
developer(stardew_valley, concerned_ape).
developer(tetris, alexey_pajitnov).
developer(super_mario, nintendo).
developer(battlefield, dice).
developer(the_last_of_us, naughty_dog).
developer(dark_souls, fromsoftware).
developer(fifa_24, ea_sports).
developer(god_of_war, santa_monica_studio).
developer(skyrim, bethesda_game_studios).
developer(red_dead_redemption, rockstar_games).
developer(dota_2, valve).
developer(league_of_legends, riot_games).

% Игра выпущена до 2000 года
classic_game(Game) :- release_year(Game, Year), Year < 2000.

% Игра разработана студией у которой больше одного проекта
multiple_games_developer(Developer) :-
    developer(Game1, Developer),
    developer(Game2, Developer),
    Game1 \= Game2.

% Игра в жанре, который имеет больше двух игр
popular_genre(Game) :-
    has_genre(Game, Genre),
    has_genre(AnotherGame, Genre),
    has_genre(YetAnotherGame, Genre),
    Game \= AnotherGame,
    AnotherGame \= YetAnotherGame,
    YetAnotherGame \= Game.

% Игра разработана определенной студией и выпущена после 2010 года
recent_development(Developer, Game) :-
    developer(Game, Developer),
    release_year(Game, Year),
    Year > 2010.

% Игра доступна в жанре и разработана определенной студией
genre_developer(Game, Genre, Developer) :-
    has_genre(Game, Genre),
    developer(Game, Developer).

% Игры от разработчиков которые в конретный год выпустили несколько игр
same_year_developer(Game) :-
    developer(Game, Developer),
    release_year(Game, Year),
    developer(OtherGame, Developer),
    release_year(OtherGame, Year),
    Game \= OtherGame.
