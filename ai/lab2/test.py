from owlready2 import *

class GameRecommendationSystem:
    def __init__(self, ontology_path):
        self.ontology_path = ontology_path
        self.load_ontology()

    def load_ontology(self):
        try:
            self.onto = get_ontology(f"file://{self.ontology_path}").load()
            if self.onto is None:
                raise ValueError("Ошибка: онтология не загружена.")
            print("Онтология загружена успешно.")
        except Exception as e:
            print(f"Произошла ошибка при загрузке онтологии: {e}")
            exit()

    def process_user_input(self, user_input):
        age = None
        genres = []
        developers = []

        tokens = user_input.split(";")
        if len(tokens) != 3:
            raise ValueError("Неверный формат ввода. Пожалуйста, используйте формат: 'Мне X лет; мне нравятся жанры: жанр1, жанр2; разработчики: разработчик1'.")

        age_part = tokens[0].strip().split(" ")
        if len(age_part) < 2:
            raise ValueError("Ошибка: неверный формат возраста.")
        age = int(age_part[1])
        
        genres_part = tokens[1].strip().replace("мне нравятся жанры:", "").strip()
        genres = [genre.strip() for genre in genres_part.split(",")]

        developers_part = tokens[2].strip().replace("разработчики:", "").strip()
        developers = [developer.strip() for developer in developers_part.split(",")]

        return age, genres, developers

    def get_recommendations(self, age, genres, developers):
        recommendations = []
        unsuitable_genres = ["action", "shooter"] if age < 16 else []

        for genre in genres:
            if genre not in unsuitable_genres:
                for game in self.onto.Game.instances():
                    has_genre = genre in [g.name for g in game.has_genre]

                    # Проверка разработчиков
                    game_developers = [d for d in game.developer] if hasattr(game, 'developer') else []
                    has_developer = not developers or any(dev in game_developers for dev in developers)

                    if has_genre and has_developer:
                        recommendations.append(game.name)
        return recommendations

    def run(self):
        while True:
            user_input = input("Введите информацию о себе и своих предпочтениях (например: 'Мне 15 лет; мне нравятся жанры: RPG, инди-игры; разработчики: nintendo'): ")
            
            if not user_input.strip():
                print("Ввод не может быть пустым. Попробуйте снова.")
                continue
            
            try:
                age, genres, developers = self.process_user_input(user_input)
                recommendations = self.get_recommendations(age, genres, developers)

                if recommendations:
                    print(f"Рекомендованные игры для вас: {', '.join(recommendations)}")
                else:
                    print("К сожалению, подходящих игр не найдено.")
            except Exception as e:
                print(f"Ошибка: {e}")
                continue  # Продолжаем цикл и запрашиваем ввод снова

            exit_choice = input("Хотите выйти? (y/n): ").strip().lower()
            if exit_choice == 'y':
                break

if __name__ == "__main__":
    print("Добро пожаловать в рекомендательную систему!")
    ontology_path = "D:/univer/university-labs/ai/lab2/ai1.2.owl"  # Укажите путь к вашему OWL-файлу
    system = GameRecommendationSystem(ontology_path)
    system.run()
    print("Выход")
    
