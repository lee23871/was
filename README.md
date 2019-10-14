### WAS 구현

1. Host Header 분석 기능
    - 같은 서버에서 Host 별로 다른 데이터 제공 기능.

2. 서버 설정 기능
    - server.setting 파일로 서버의 설정 관리
    - Port 지정
    - Host 별 ROOT Directory 및 403, 404, 500 오류 HTML 파일 지정

3. 403, 404, 500 오류 처리
    - 위의 오류 발생시 설정된 HTML 파일 반환 기능
    
4. 403 보안 체크
    - 상위 Directory 접근 시, 또는 .exe 파일 접근시 403 오류 발생
    
5. logback 설정
    - logback.xml 에 로그 형식 설정
    - logs/service.log 파일에 생성되고 하루 단위로 Rolling 함.
    - 오류 발생시 StackTrace 로깅

6. SimpleServlet 구현
    - SimpleServlet 을 구현하는 Hello 와 service.Hello 구현
    - ServletMapping 에서 URI 를 해당 클래스로 Mapping 하는 정보를 구현
    - 정보가 String 으로 되어 있어, 설정 파일로 위의 내용을 관리하도록 추후 수정이 가능하도록 구현

7. 시각 정보를 출력하는 SimpleServlet 구련
    - SimpleServlet 을 구현하는 service.TimeServlet 을 구현
    - 해당 구현체는 localhost/service.Time 로 사용이 가능함
 
8. JUnit 테스트 작성
    - RequestProcessorTest 에 Integration 테스트를 작성
    - 케이스 별로 403, 404, 파일 접근, Hello Servlet 구현체 접근 등의 케이스를 작성
    - 다른 Host 로 호출시의 케이스 작성
    - 이외의 추가적으로 한 Component 의 Unit 테스트를 작성