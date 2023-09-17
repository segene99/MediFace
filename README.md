# MediFace
Deepface를 활용한 안면인식 진료등록 서비스

# Setting
### 백엔드
1. JDK 17.0.8
2. mysql  Ver 8.1.0 
3. hibernate 6.2.3 Final
4. thymeleaf
5. JPA
6. 포트번호 5001
7. Software Architecture: 3 Layered, MVC

### DB
CREATE DATABASE facecheck;
create table patients (
  seq INT auto_increment primary key,
  name varchar(255) not null,
  phone longtext(255) not null,
  photo varchar(255) not null,
  created timestamp default current_timestamp
);



