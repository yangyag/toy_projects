# Toy Project

## - Docker 명령어 예시
### 0. 프로그램 빌드
./gradlew bootJar

### 1. Docker Hub에 로그인
docker login

### 2. 이미지 빌드
docker build -t toy-app-image:v1.0.1 .

### 3. 이미지 태그 설정
docker tag toy-app-image:v1.0.1 yangyag2/toy-app-image:v1.0.1

### 4. 이미지 푸시
docker push yangyag2/toy-app-image:v1.0.1

### 5. docker-compose.yml
파일에서 버전 수정

### 6. Docker 재기동
docker-compose up -d --build