# spring-data-flow-example
This is a repository for Spring Data Flow examples

Example streams:

stream create test --definition "http --spring.cloud.stream.bindings.output.contentType='application/json' --spring.cloud.stream.bindings.output.contentType='application/x-spring-tuple' | car-fact-processor | log" --deploy