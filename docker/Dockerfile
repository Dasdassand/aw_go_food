# Используем официальный образ JDK для сборки
FROM openjdk:17-jdk-slim as build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы сборщика и метаданные проекта
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Копируем исходный код
COPY src src

# Сборка проекта
RUN ./gradlew build --no-daemon

# Запускаем новый этап для создания финального образа
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный jar-файл из предыдущего этапа
COPY --from=build /app/build/libs/*.jar app.jar

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]

# Открываем порт, который слушает приложение (измените при необходимости)
EXPOSE 8080
