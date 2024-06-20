# 항해플러스 1주차 과제

## [요구 사항]

- PATCH  `/point/{id}/charge` : 포인트를 충전한다.
- PATCH `/point/{id}/use` : 포인트를 사용한다.
- GET `/point/{id}` : 포인트를 조회한다.
- GET `/point/{id}/histories` : 포인트 내역을 조회한다.
- 잔고가 부족할 경우, 포인트 사용은 실패하여야 합니다.
- 동시에 여러 건의 포인트 충전, 이용 요청이 들어올 경우 순차적으로 처리되어야 합니다. (동시성)

## [STEP 1]

- 테스트케이스 작성 및 작성 이유 주석 작성 여부
- 프로젝트 내의 주석에 필요한 기능의 작성 여부
- 단위 테스트 여부

## [STEP 2]

- 동시성 제어 및 관련 통합 테스트 작성 여부
- 잔고 부족시, 포인트 사용 실패 처

## [TC_목록]

- [X] 포인트 충전
  - [X] 기존 포인트가 없을 경우, 포인트 충전
  - [X] 기존 포인트가 있을 경우, 기존 포인트 + 충전 포인트 => 포인트 충전


- [ ] 포인트 사용 
  - [X] 사용 포인트가 기존 포인트보다 많을 경우, 사용 불가 처리
  - [ ] 사용 포인트가 기존 포인트보다 적을 경우, 사용 가능 -> 최종 포인트 업데이트


- [X] 포인트 조회
  - [X]  기존 포인트가 없을 경우, 기본 값 반환


-  [ ] 포인트 내역 조회
  - [X] 포인트 내역이 없을 경우, 포인트 내역 없음 예외처리
  - [ ] 포인트 내역이 존재할 경우, 포인트 내역 반환
