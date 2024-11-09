# java-convenience-store-precourse

# 📄기능 구현 목록

## 1. md파일 읽기

products.md, promotions.md 파일을 읽어 재고 수량, 프로모션 종류 파악하기✅

- 파일 읽기에 문제가 발생한 경우 프로그램 종료✅
    - [x] 값이 누락된 경우
    - [x] 값의 포맷이 잘못된 경우(ex. start_date가 날짜 형태가 아닌 경우)

### 1-1. promotions.md 파일 읽기✅

- 유효성 검사 사항
    - [x] 프로모션 이름이 null인 경우
    - [x] 같은 이름의 프로모션이 있는 경우

### 1-2. products.md 파일 읽기✅

- 유효성 검사 사항
    - [ ] 같은 물품에 프로모션이 두개인 경우

### 1-3. 전체 재고 관리하는 모델 생성✅

## 2. 사용자에게 구매 목록 입력 받기

### 2-1. 사용자에게 편의점 재고 출력하기✅

### 2-2. 구매 상품 및 수량 입력 받기

- 사용자의 입력 유효성 검사
    - [x] 사용자의 입력 형식 검사하기
    - [ ] 사용자가 입력한 상품 존재 유무 검사하기
    - [x] 구매 수량이 양수인지 확인
    - [ ] 사용자가 입력한 상품에 대한 재고 검사하기


- 사용자의 입력이 유효하지 않았을 경우 다시 입력 요청하기

## 3. 재고 파악 후 추가 정보 입력받기

### 3-1. 프로모션 재고 여부 판단하기

- 프로모션 상품일 경우 프로모션 재고를 초과할 시 안내
    * Y: 일부 수량에 대해 정가로 결제
    * N: 정가로 결제해야하는 수량만큼 제외한 후 결제를 진행
    * 이 외 : 다시 입력 받기

### 3-2. 프로모션 적용된 상품이 있다면 혜택 안내하기

- 프로모션 적용된 상품에 대해 (N + 1) 만큼 나누어 떨어지지 않을 경우 안내
    * Y: 증정 받을 수 있는 상품을 추가함
    * N: 증정 받을 수 있는 상품을 추가하지 안 함
    * 이 외: 다시 입력 받기

### 3-3. 멤버십 할인 적용 여부

- 결제 진행 전 항상 물어보기
    - Y: 멤버십 할인을 적용

    * N: 멤버십 할인을 적용 안 함
    * 이 외 : 다시 입력 받기

## 4. 사용자 지불 금액 계산하기 및 출력

추가정보 바탕으로 총 구매액, 행사할인 금액, 멤버십 할인 금액, 내실돈 계산

## 5. 재고 업데이트

## 6. 추가 구매 여부 입력받기

* 결제 이후 항상 추가 구매 여부를 입력 받음
    * Y: 재고가 업데이트된 상품 목록을 확인 후 추가로 구매를 진행
    * N: 구매를 종료
    * 이 외 : 다시 입력 받기

# 🗂️예상 도메인 설계

## 물품에 대한 모델

**상태**

- 물품 이름✅
- 가격✅
- 재고✅
- 프로모션 종류 (없다면 NULL)✅

**행동**

- ~~총 구매 개수 입력 시 제공해야할 프로모션 재고, 일반 재고 반환~~
- 재고 개수 반환✅
- 프로모션 증정 단위 반환✅

## 프로모션에 대한 모델 생성

**상태**

- 프로모션 이름✅
- 구매 개수✅
- 증정 개수 = 1✅
- 프로모션 시작 일시✅
- 프로모션 종료 일시✅

**행동**

- ~~총 구매 개수 입력시 증정해야하는 개수 반환~~ -> 프로모션 받을 수 있는 단위 반환✅

## 전체 재고에 관한 모델 생성

**상태**

- 일반 재고에 대한 객체 (List)✅
- 프로모션 재고에 대한 객체 (List)✅

**행동**

- 이름을 받아 재고 여부 반환✅
- 이름과 구매 개수를 받아 재고 여부 반환✅
- 이름과 구매 개수를 받아 정가 결제해야하는 개수를 반환✅
- 이름과 구매 개수를 받아 혜택을 받을 수 있는지 여부 반환✅
- ~~재고 정리~~

## 사용자 구매 목록 관련 모델 생성

**상태**

- 사용자의 구매 목록에 대한 객체 (Map)
    - 구매가 가능한 것에 대한 일관성을 가짐

**행동**

- 존재하는 물품인지 검사
- 재고 수량이 있는지 검사

## 결제 관련 모델 생성

**상태**

- 총 구매 금액
- 프로모션 할인 금액
- 멤버십 할인 금액
- 실 결제액
