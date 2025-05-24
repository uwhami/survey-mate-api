# survey-mate-api
다정 &amp; 지은이의 설문조사 프로젝트의 벡엔드

Front-End : 
https://github.com/jein6316/survey-mate-web

&nbsp;

## Survey Mate API 설명

Spring Boot 기반 설문조사 플랫폼 백엔드 프로젝트입니다.  
회원가입/로그인부터 그룹 생성, 설문 응답까지 전반적인 기능을 RESTful하게 제공합니다.  
JWT 인증, 공통 응답 처리, AOP 활용 등 실무 수준의 백엔드 구조를 적용하였습니다.


## 주요 기술 스택

- Java 17, Spring Boot 3, Spring Security, JPA
- MariaDB, QueryDSL, JWT, Recoil (프론트 연동)
- AWS EC2 배포, Nginx 설정


## 주요 구현 기능

### API 아키텍처 & 인증
- RESTful API 설계 및 구현
- JWT 기반 로그인 인증 및 권한 분기 처리
- ResponseBodyAdvice를 활용한 글로벌 응답 포맷 통일
- 커스텀 예외 처리

### 데이터 모델링 & 공통 구조 설계
- JPA 기반 Entity 설계 및 MariaDB 연동
- BaseEntity에 공통 필드 상속
- 로그인 사용자 정보 자동 주입을 위한 추상 클래스(MemInfoAware) 구조 설계 및 AOP 처리 적용
- 파일 업로드 및 접근 URL 반환 기능
- 파일 경로, 멤버 Role/Status, 소셜 로그인 타입 등 ENUM 처리로 타입 안정성 확보와 DB 의존도 최소화, 코드 가독성 향상
- 공통코드 클래스(CodeGenerator)를 생성하여 회원가입, 설문조사 작성 시 코드를 생성하여 관리

### 운영/배포
- AWS EC2 인스턴스 생성 및 Nginx 설정
- 도메인 연결 및 외부 접근 가능 상태 유지
- application.yml 분리 및 운영 환경 대응


### 도메인 주소 연결
> https://djsurveymate.duckdns.org

&nbsp;

## 추가 예정 기능
- 그룹 초대 및 가입 신청/승인
- 그룹 매니저 권한 요청/승인 흐름
- 설문 응답 임시 저장 및 기한 지정 기능
- 관리자용 설문/그룹 통계 화면 및 관리 대시보드
- 그룹 내 설문 알림 기능

