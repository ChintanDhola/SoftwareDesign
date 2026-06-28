# Use a supported Java 17 environment
FROM eclipse-temurin:17-alpine

# Set the working directory
WORKDIR /app

# code into a folder named exercise_5 to match your Java package
COPY . /app/exercise_5

# Compile all Java files inside the package
RUN javac exercise_5/*.java

# Run the program using the full package name.
ENTRYPOINT ["java", "exercise_5.Main"]