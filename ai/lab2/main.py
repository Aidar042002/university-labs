from pyswip import Prolog

class Main:
    def __init__(self, prolog_path):
        self.prolog = Prolog()
        try:
            self.prolog.consult(prolog_path)
            print("Файл Prolog загружен успешно.")
        except Exception as e:
            print(f"Произошла ошибка при загрузке файла Prolog: {e}")
            exit()

    def get_user_input(self, user_input):
        age = None
        genres = []
        developers = []

        tokens = user_input.split(";")
        if len(tokens) != 3:
            raise ValueError("Неверный формат ввода. Пожалуйста, используйте формат: 'Мне X лет; мне нравятся жанры: жанр1, жанр2; разработчики: разработчик1'.")

        age_part = tokens[0].strip().split(" ")
        if len(age_part) < 2:
            raise ValueError("Ошибка: неверный формат возраста.")
        
        try:
            age = int(age_part[1])
            if age < 0 or age > 100:
                raise ValueError("Возраст должен быть в диапазоне от 0 до 100.")
        except ValueError:
            raise ValueError("Ошибка: неверный формат возраста. Пожалуйста, введите число.")
        
        genres_part = tokens[1].strip().replace("мне нравятся жанры:", "").strip()
        if genres_part:
            genres = [genre.strip() for genre in genres_part.split(",") if genre.strip()]

        developers_part = tokens[2].strip().replace("разработчики:", "").strip()
        if developers_part:
            developers = [developer.strip() for developer in developers_part.split(",") if developer.strip()] 

        return age, genres, developers

    def get_recommendations(self, age, genres, developers):
        recommendations = []
        unsuitable_genres = ["shooter"] if age < 16 else []  

        if genres and not developers:
            for genre in genres:
                if genre not in unsuitable_genres:
                    query = f"has_genre(Game, '{genre}')"
                    for result in self.prolog.query(query):
                        recommendations.append(result['Game'])

        elif developers and not genres:
            for developer in developers:
                query = f"developer(Game, '{developer}')"
                for result in self.prolog.query(query):
                    recommendations.append(result['Game'])

        elif genres and developers:
            for genre in genres:
                if genre not in unsuitable_genres:
                    for developer in developers:
                        query = f"genre_developer(Game, '{genre}', '{developer}')"
                        for result in self.prolog.query(query):
                            recommendations.append(result['Game'])
        else:
            print("Не указаны жанры или разработчики. Пожалуйста, введите хотя бы одно значение.")
            return []

        return recommendations

if __name__ == "__main__":
    print("Старт рекомендательной системы...")
    prolog_path = "D:/univer/university-labs/ai/lab2/test.pl"
    system = Main(prolog_path)
    
    while True:
        user_input = input("Введите информацию о себе и своих предпочтениях (например: 'Мне 25 лет; мне нравятся жанры: sports; разработчики: ea_sports') или введите 'y' для выхода: ")
        
        if user_input.strip().lower() == 'y':  
            print("Выход из программы.")
            break
        
        if not user_input.strip():
            print("Ввод не может быть пустым. Попробуйте снова.")
            continue
        
        try:
            age, genres, developers = system.get_user_input(user_input)

            recommendations = system.get_recommendations(age, genres, developers)

            if recommendations:
                print(f"Рекомендованные игры для вас: {', '.join(recommendations)}")
            else:
                print("К сожалению, подходящих игр не найдено.")
        except Exception as e:
            print(f"Ошибка: {e}. Попробуйте снова или введите 'y' для выхода.")
            continue
