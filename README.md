# spring-data-flow-example
This is a repository for Spring Data Flow examples

Example streams:

stream create test --definition "http  --spring.cloud.stream.bindings.output.contentType='application/x-spring-tuple' | car-fact-processor | log" --deploy--spring.cloud.stream.bindings.output.contentType='application/json'

http --port=10101 --spring.cloud.stream.bindings.output.contentType='application/json' | car-fact-processor  --spring.cloud.stream.bindings.input.contentType='application/x-java-object;type=com.github.wkennedy.dto.Car' | log

http --port=10101 --spring.cloud.stream.bindings.output.contentType='application/json' | car-fact-processor  --spring.cloud.stream.bindings.input.contentType='application/x-java-object;type=com.github.wkennedy.dto.Car' | jdbc --spring.datasource.url=jdbc:mysql://127.0.0.1:3306/galaxy_schema?user=user --spring.datasource.password=pass --spring.datasource.driver-class-name=org.mariadb.jdbc.Driver --jdbc.table-name=car_fact --jdbc.columns=engine,make

curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -H "Postman-Token: 54f725ef-aa53-8f50-63a4-feeb5a5b5a5c" -d '{
	"engine":"SR20",
	"make":"Nissan"
}' "http://localhost:10101"