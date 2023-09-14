# MediFace
[AI-X] 팀 All-In의 토이 프로젝트

# Setting
### 자바
1. JDK 17.0.8
2. mysql  Ver 8.1.0 
3. hibernate 6.2.3 Final
4. thymeleaf
5. JPA
6. 포트번호 8081

### DB
CREATE DATABASE facecheck;
create table patients (
  seq INT auto_increment primary key,
  name varchar(255) not null,
  phone varchar(255) not null,
  photo varchar(255) not null,
  created timestamp default current_timestamp
);


# 백엔드
- Software Architecture: 5 Layered, MVC

# 참고
software architecture: https://sac4686.tistory.com/15

<br/><br/><br/>

#### 개발일지(segene)
2023.09.08 MVC 5 Layered 구조세팅 
<br/>
2023.09.11 환자등록/삭제/조회 기능구현
<br/>
2023.09.14 Face Attribute Analysis 기능구현. 웹서버와 분석기능을 수행하는 model서버를 분리 후, JSON으로 데이터 전송/반환
