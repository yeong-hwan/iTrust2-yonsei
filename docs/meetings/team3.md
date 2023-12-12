## Team3 Meeting Log
- Team1 Member : 강민용, 임예원, 노규민

## 목차
1. 11/04
2. 11/08
3. 11/15
4. 11/18
5. 11/20
6. 11/28
7. 12/01

## UC Flow
![cell3_uc_flow](../../img/cell3_uc_flow.png)

### Meeting Title: Cell3 첫 미팅 및 구조 파악
- **Date:** 11/04
- **Participants:** 강민용, 임예원, 노규민
- **Agenda:**
  - 초기 환경 세팅 방법에 대한 논의
  - iTrust2 전체 구조에 대한 논의
  
- **Discussion Summary:**
  - aaplication.yml을 template에서 복사 및 추가하여 아래와 같이 수정
    ```
    spring:
    datasource:
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://localhost:3306/iTrust2_test?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&serverTimezone=UTC
        username: root
        password: root
        hikari:
        idleTimeout: 500
        connectionTimeout: 15000
    jpa:
        hibernate:
        ddl-auto: update
        dialect: org.hibernate.dialect.MySQL5Dialect
        show-sql: true

    server:
        port: 8080
        servlet:
            contextPath: /iTrust2
    ```
  - 전체구조 = Spring-boot + AngularJS (ver 1.x) + BootstrapCSS
    - frontend
        - framework = AngularJS (Angular와 다르며 ver 1.x를 의미)
        - 핵심적인 개념 = module + controller
        - 첫 접속시 마주하는 화면 → index
        - templates안에 경로에 따라서 폴더가 나눠져있음 (admin, er, hcp, patient, personnel)
        - layout에 head와 body fragment가 있음
            - head = title과 link를 인자로 받으며, header 정보에 필요한 것들로 구성
            - body = menu navigation + content + footer로 구성
    - Backend
        - front에서의 요청 처리를 위한 API들은 아래 경로에 모여있음
            - *iTrust2\src\main\java\edu\ncsu\csc\iTrust2\controllers\api*
        - front에서 get 요청을 보내면, response로 해당하는 value return

- **Action Items:**
  - UC15, UC23 (new UC)에 대해서 어떤 작업이 필요할지 Front, Back을 나눠서 기능 정리하기
  - 작성된 기능들을 토대로, 개발시 문제가 없게 API 문서 작성하기



### Meeting Title: UC15 필요한 기능에 대한 논의
- **Date:** 11/08
- **Participants:** 강민용, 임예원, 노규민
- **Agenda:**
  - User Register에 대한 개발 논의
  - 각 UC별 필요한 기능에 대한 논의
  
- **Discussion Summary:**
  - Register 과정을 모든 UC에서 필요로 하는 만큼 이를 빠르게 대응하기 위해 등록 먼저 개발
    - 첫 스타트로 괜찮은 기능개발로 생각됨 (feature/team3-register)
    - 기존 UC에서 참고할 만한 사항들 잘 추려서 진행
  - UC15에 대한 기능 논의 (front / back)
    1. 승인된 사용자 = HCP & ER
        - **프론트** HCP와 ER의 navigation bar에 응급 상황 의료 기록 검색을 위한 dropdown 추가
    
    2. 승인된 사용자가 환자의 Emergency Record 검색
        - **프론트** 해당 dropdown에 접속했을 때, 환자의 이름 (첫번째 또는 마지막) 또는 MID(의료식별번호)를 검색할 수 있는 창 제공 → 무엇으로 검색할지 선택할 수 있는 UI 제공
            - 검색 시, 적절한 데이터 형식과 일치하지 않는 경우 사용자는 표시되지 않고 경고를 보여줌
        - **프론트** 입력 후 검색을 누르면 아래에 리스트로 이름을 포함하는 환자들 보여주기
        - **벡엔드** 검색 시, API 요청을 통해 query를 만족하는 환자 리스트를 response로 전달
            - 전달 시 이름으로 검색하는지, MID로 검색하는지 함께 보냄
            - **(예원) 혹시 검색을 위한 API가 이미 구현되어 있는지 (또는 겹치는게 있는지) 확인**
        
    3. 구체적인 환자 기록 확인
        - **프론트** 나열된 환자를 클릭하면, 해당 환자의 응급 의료 기록 페이지를 보여줌
            - 필요한 정보 → database를 따로 만드는게 아니라 가져오는 것만
                - 이름, 연령, 출생일, 성별 및 혈액형
                - 60일 이내의 단기 진단에 대한 진단 코드 목록 (최신순)
                - 환자가 지난 90일 동안 받은 모든 처방전 목록 (최신순) → 굳이 안해도 좋을듯
            - 굳이 새로운 링크를 안하더라도 팝업 같이 보여주면 편할듯 싶음
        - **벡엔드** 인증된 사용자가 환자를 클릭하면 위의 필요한 정보를 response로 전달
            - 각각의 DB에서 위의 필요한 정보 긁어오기

- **Action Items:**
  - 작성된 기능들을 토대로, 병렬 개발 시 문제가 없게 API 문서 작성하기 (UC15)



### Meeting Title: UC23 필요한 기능에 대한 논의
- **Date:** 11/15
- **Participants:** 강민용, 임예원, 노규민
- **Agenda:**
  - User Register에 대한 개발 마무리 및 develop branch merge / 팀원 공유
  - UC 23 필요한 기능에 대한 논의
  
- **Discussion Summary:**
  - UC23에 대한 기능 논의 (front / back)
    1. 승인된 사용자 = HCP & 안과 의료 제공자 (Optometrist HCP) & 안과 의사 HCP (Ophthalmologist HCP)
    - **프론트** 위 3명의 사용자에 대해서 navigation bar에 환자에게 실험실 검사를 요청할 수 있는 dropdown 추가

    2. 실험실 검사 요청
        - **프론트** 환자 선택은 검색을 통해서 진행 (이름 or MID)
            - 특별한 지침을 지정하지말고, 요청받은 환자 모두 같은 형태의 검사를 받도록
        - **벡엔드** front에서 요청이 들어오면 아래의 작업을 진행
            - [실험실 검사 요청]에 대한 table을 DB에 추가
            - 누가(위 3 종류의 사용자) 요청했는지, 누구(환자)한테 요청했는지, 언제 요청했는지
        
    3. 알림
        - **벡엔드** 환자 또는 Lab Tech이 로그인 할 때마다, 실험실 검사에 대한 요청이 있는지 확인 (위에서 만든 table을 검색)
        - **프론트** 만약 있다면 간단한 팝업 보여주기
            - 보낸 사람, 언제 요청했는지
        
    4. Lab Tech이 실험실 검사 처리 및 시스템에 결과 기록
        - **프론트** 실험실 검사 결과 입력을 위한 간단한 형태의 Form + submit 버튼 제공
        - **벡엔드**
            - [실험실 검사 결과] table 추가
            - [실험실 검사 요청] table에서 해당 검사 요청을 제거하고, [실험실 검사 결과] table에 결과 입력
            - 실험실 검사가 무엇인가? (form 안에 넣을 친구들 - 안과기준)
                - 안과 검사 몇 가지 선정 → 시력 검사, 안압 검사, 시야 검사, 안내 초음파 검사 결과 등

    5. 환자, 개인 대리인, HCP가 실험실 검사 결과 확인
        - **프론트** 환자 및 개인 대리인의 경우 실험실 검사 결과 table에 본인이 있다면 결과 확인가능 (없으면 결과가 없다고 표시)
        - **벡엔드** front의 요청을 받아서 환자 및 개인 대리인의 실험실 검사 결과 확인 및 response로 전달
        - **[프론트, 벡엔드]** HCP의 경우 [실험실 결과 table]의 모든 결과를 list로 보여줌

- **Action Items:**
  - 작성된 기능들을 토대로, 병렬 개발 시 문제가 없게 API 문서 작성하기 (UC23)





### Meeting Title: UC15, UC23 API 문서 작성
- **Date:** 11/18
- **Participants:** 강민용, 임예원, 노규민
- **Agenda:**
  - 병렬적으로 개발을 빠르게 진행하기 위해 API documentation 진행


  
- **Discussion Summary:**
  - API documentation for UC15
        1. Search Emergency Health Records
            - **Endpoint:** `/api/emergency_health_records/search`
            - **Method:** `GET`
            - **Query Parameters:**
            - `searchType` (String): Type of search, set to "name".
            - `searchQuery` (String): The search query for the specified search type.

            - **Success Response:**
            - Status: 200 OK
            - Content:
                ```json
                {
                "patients": [
                    {
                    "firstName": "Siegwardof",
                    "lastName": "Catarina",
                    // ... other patient details
                    },
                    // ... other patients
                ]
                }
                ```


        2. View Patient's Emergency Health Records
            - **Endpoint:** `/api/emergency_health_records/search`
            - **Method:** `GET`
            - **Query Parameters:**
            - `searchType` (String): Type of search, set to "username".
            - `searchQuery` (String): The search query for the specified search type.

            - **Success Response:**
            - Status: 200 OK
            - Content:
                ```json
                {
                "patients": [
                    {
                    "firstName": "Siegwardof",
                    "lastName": "Catarina",
                    // ... other patient details
                    },
                    // ... other patients
                ]
                }
                ```

- **Action Items:**
  - 작성한 API 문서를 토대로 Front / Backend 개발 시작
  - Front = 강민용, API#1 = 임예원, API#2 = 노규민



### Meeting Title: UC15 개발 및 Test 완료
- **Date:** 11/20
- **Participants:** 강민용, 임예원, 노규민
- **Agenda:**
  - UC15 개발 및 Test 완료
  
- **Discussion Summary:**
  - 각 기능에 대해서 정상 작동하는 것 확인
  - Test Code 작성을 통해 DB 및 Backend 작업도 잘 되는 것 test 완료

- **Action Items:**
  - iter2 발표 자료 만들기
  - UC23 개발 계획 진행


### Meeting Title: UC23 개발 계획 수립 및 개발 시작
- **Date:** 11/28
- **Participants:** 강민용, 임예원, 노규민
- **Agenda:**
  - UC23 개발 및 Test 완료
  
- **Discussion Summary:**
  - API documentation을 기반으로 12월 초까지 개발 마무리하는 것이 목표
  - UC15와 마찬가지로 Front와 Backend 각각 개발하고 통합하는 걸로
  - Readme 정돈이 필요함! 진행상황과 회의록을 깔끔하게 볼 수 있게 정리하기

- **Action Items:**
  - 강민용 = UC23 frontend
  - 임예원 = UC23 backend API
  - 노규민 = iter3 발표 자료 만들기


### Meeting Title: UC23 개발 및 Test 작성 완료
- **Date:** 12/01
- **Participants:** 강민용, 임예원, 노규민
- **Agenda:**
  - UC23 개발 및 Test 완료
  
- **Discussion Summary:**
  - 각 기능에 대해서 정상 작동하는 것 확인
  - Test Code 작성을 통해 DB 및 Backend 작업도 잘 되는 것 test 완료
  - 각 API에 대해서 Test Coverage 80% 이상 달성 완료

- **Action Items:**
  - iter3 발표 자료 만들기
