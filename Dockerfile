# Imagem com Java 22 para rodar o JAR
FROM eclipse-temurin:22-jre

WORKDIR /app

# Copia o JAR gerado (ajusta o nome para o teu)
COPY api_padrao-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "app.jar"]
