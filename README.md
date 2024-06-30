# kfinance
코틀린으로 구현하는 금융 관련 라이브러리

## Stock
### dart
[Open DART](https://opendart.fss.or.kr/)에서 기업 공시 정보를 가져오기 위한 클래스

- [ ] `OpenDartReader` : 기업 공시 정보를 가져오기 위한 클래스
    - [ ] <i>TBD</i>

### quant
주식 데이터를 분석하기 위한 클래스

- [ ] rsi
- [ ] macd
- [ ] envelope
- [ ] bollinger
- [ ] stochastic

### yahoo
[Yahoo Finance](https://finance.yahoo.com/)에서 주식 데이터를 가져오기 위한 클래스

- [x] `YahooDataReader` : 특정 주식의 데이터를 가져오기 위한 클래스
    - [x] 특정 주식의 주가(수정주가)를 리스트 및 테이블 형태로 조회한다
    - [x] 특정 주식의 종가, 시가, 고가, 저가, 거래량, 수정주가를 리스트 및 테이블 형태로 조회한다
    - [x] 특정 주식의 배당 정보를 리스트 및 테이블 형태로 조회한다

## Util
### calculator
금융 계산을 위한 클래스

- [x] `InterestCalculator` : 단리/복리 계산을 위한 클래스
    - [x] 단리 계산을 수행한다
    - [x] 월복리 계산을 수행한다

### calendar
영업일 및 공휴일을 확인하기 위한 클래스

- [x] `BusinessCalendar` : 영업일 및 공휴일을 확인하기 위한 클래스
  - [x] 영업일 및 공휴일 검색을 지원하는 나라인지 여부를 제공한다
  - [x] 특정 날짜에 대해 휴일 및 공휴일 여부를 반환한다
  - [x] 특정 나라의 특정 연도의 영업일 및 공휴일 리스트를 반환한다
  - [x] 특정 나라의 특정 월의 영업일 및 공휴일 리스트를 반환한다
  - [x] 특정 나라의 특정 날짜의 범위 내의 영업일 및 공휴일을 반환한다
