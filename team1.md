# Team1 Meeting Log
- Team1 Member : 김영서, 박승호, 엄소은

## 목차

1. [10/28](#1028)
2. [11/5](#115)
3. [11/11](#1111)
4. [11/13](#1113)

---
## 회의록

### 10/28
- Before Meeting
    - Use Case 모두 읽어보기 
- During Meeting
    - 안과와 관련된 UC 2개 (UC 21, 22) 선정

### 11/5
- Before Meeting
    - DB 칼럼 뜯어보기
    - UC 별로 API 명세 작성
- During Meeting
    - 브랜치 관리 : 메인 개발은 feature/team1, 각 UC 별로 새로운 브랜치에 개발
    - API 명세 별로 만들어야 하는 기능 확정

    <img width="600" alt="image" src="https://github.com/ddoddii/ddoddii.github.io/assets/95014836/7757af4e-6e47-4346-baed-4af4e8401d42">

    - 구현해야 하는 기능 분담
        - Iter1 : 0~6 끝내기 (BE- 엄소은, 박승호, FE - 김영서, 박승호) 

    

### 11/11
- Before Meeting
    - 개발 관련 오류 발생한 것 가져오기
- During Meeting
    - iTrust2 에 이미 구현되어 있는 기능 / 구현해야 할 기능 세부적으로 나누기

    <img width="340" alt="image" src="https://github.com/ddoddii/ddoddii.github.io/assets/95014836/112cdc99-f283-4ceb-9e1e-b431395628ae">

- 개발 상황
    - [X] (FE) 안과 약속 조회 하는 페이지
    - [X] (FE) eyecheckup 체크 하는 박스
    - [X] (BE) OPH, OD 도 appointment 를 볼 수 있도록 권한 추가 부여

### 11/13
- Before Meeting
    - HCP, OD, OPH 권한 별로 안과 수술 신청 권한이 다른 것을 확인
- During Meeting
    - BE에서 설정한 권한 대로 FE 에서도 안보이게 맞추기 
- 개발 상황
    - [X] (FE) HCP 에서 view opth request 문구 없애기
    - [X] (FE) HCP 에서 일반 안과 약속 볼 수 있는 페이지 삭제 
    - [X]  (FE) 환자가 안과약속 신청할 때 OD, OPH 에게만 신청할 수 있도록 변경
- 브랜치 이슈
    - develop 말고 main 에 머지하는 이슈 -> revert 로 해결 


### 11/16
- Before Meeting
    - edit office visit page 구현
        - oph role을 가진 user는 자신이 기록한/만든 office visit의 명단을 볼 수 있다.(role에 대한 update 필요, 명단 가져오는 api는 있음 get; api/v1/officevisits/hcp)
        - edit an office visit 버튼을 통해 edit document office visit page로 이동할 수 있다. (라우팅 필요)
        - create a new office visit 버튼을 통해 document office visit page로 이동할 수 있다. (라우팅 필요)

    - edit document office visit page 구현
        - document랑 비슷하게 생긴 페이지, 정보가 전부 입력되어 있음(세부 정보 가져오는 api 있는 거 같은데 확인 필요 get; api/v1/officevisits/{id}
        - 업데이트 api는 있음: put; api/v1/officevisits/{id}
        - 버튼 수정: Update Office Visit

    - past office visit page 구현
        - 환자가 자신이 방문했던 office visit 명단을 type에 따라 label하여 볼 수 있다.(명단 가져오는 api 있음: get; api/v1/officevisits/myofficevisits)
        - 그중 surgery office visit 하나를 선택하여 view an ophthalmology surgery visit 페이지로 이동할 수 있다. (라우팅 필요)

    - view an ophthalmology surgery visit 페이지 구현
        - 해당 office visit의 날짜, 시력 정보, sphere, cylinder, axis, surgery type, notes를 볼 수 있다. 수정x(api 없음, 구현 필요)

- During Meeting
    - Eyecheckup 개발 상황 공유

- 개발 상황
    - [X] (BE) eyecheckup 관련 테이블 추가
    - [X] (BE) eyecheckup id 을 OfficeVisit 테이블에서 FK 로 참조하도록 추가 

### 11/20
- During Meeting 
    - FE, BE eyecheckup 관련 형식 맞추기 

    <img width="369" alt="image" src="https://github.com/ddoddii/ddoddii.github.io/assets/95014836/f68f0252-46a9-4b62-b5b9-6d3b693a1450">

    <img width="312" alt="KakaoTalk_Photo_2023-12-08-12-52-50" src="https://github.com/ddoddii/ddoddii.github.io/assets/95014836/34544a3b-bd66-46ab-967e-9c5be0668ddc">

- 개발 상황
    - [X] 프런트에서 eyecheckup 입력하면 DB 에 저장되는 것 확인
    - [X] edit office visit 시 수정된 정보가 DB 에 저장되는 것 확인
- 개발 이슈 
    - 이클립스에서 폴더명이 대문자만 빌드가 되어서 머지 시 폴더명을 바꿔서 버그가 생기는 이슈
    - GitKaraken 에서 commit 을 보면서 해결
    - 전체 회의를 통해 폴더명 통일

    <img width="600" alt="KakaoTalk_Photo_2023-12-08-12-56-15" src="https://github.com/ddoddii/ddoddii.github.io/assets/95014836/db8c17bd-528f-4f39-9f08-31845e058339">
