# 기본 이미지로 OpenJDK 11 사용
FROM openjdk:11

# 작업 디렉토리 생성 및 설정
RUN mkdir -p /app
WORKDIR /app

# 빌드된 JAR 파일을 컨테이너에 복사
COPY target/YM-0.0.1-SNAPSHOT.jar /app/YM-0.0.1-SNAPSHOT.jar

# 환경 변수 설정 (옵션)
ENV PORT 8085

# 8085 포트 개방 (Spring Boot 기본 포트)
EXPOSE 8085

# 어플리케이션 실행 명령어 설정
CMD ["java", "-jar", "/app/YM-0.0.1-SNAPSHOT.jar"]