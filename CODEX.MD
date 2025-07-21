**** Generic Instructions
- When defining variables, use camel case names (eg., myVariable)
- Do not declare variables using var keyword
- Provide Java docs to complex methods. Explain how the method works with a simple example
- Follow the variable naming convention as per the remaining codebase
  (eg., CustomerRegistration customerRegistration, Optional<CustomerRegistration> customerRegistrationOpt, etc.)
- Make sure to keep the code as modular as possible. This is done by using Helper methods, utils, etc,
  which could be reused

**** To be followed while commiting
https://github.com/angular/angular/blob/main/contributing-docs/commit-message-guidelines.md

**** To be followed while writing logs (@Slf4j)
- Inside the log use this format: "[<ClassName> : <methodName>] : <Short log message>"
- The Short log message can include variables, request, objects, etc. which can help debug the log

**** To be followed in a Repository Layer
- In the repository layer, use try catch block, with proper logs

**** To be followed while writing Request/Response/Dto classes
- If the Request or Response are to be used in Controller layer, add it to the Model Directory and then use it in Controller class. These requests and responses can enforce validations (NotNull, NotBlank, etc.,)
- If the Request or Response classes are to be used in the Service layer (Core Layer), add suffix ServiceRequest/ ServiceResponse to them (eg., myRequest in Controller, and myServiceRequest in Service layer)
- When myRequest in Controller layer goes to myServiceRequest in Service layer, use MapStruct's Mapper classes to convert them
- No validation annotations will be present in ServiceRequest. ie., @NotNull, @NotBlank, etc will not be present
- No Serialization will be present in ServiceRequest
- Everything else will be the same, the attributes, the attribute names, order, etc. in ServiceRequest
- If there are multiple requests/response/dto if with same attributes, create BaseRequest/Response/Dto and extend the other objects from that BaseObject

**** To be followed while writing Mapper Classes
- Use MappStruct's Mapper to write mapper to convert a parameter or a list of parameters to a single response class
- The paramter(s) and response class will be mentioned
- In case there are conflicting sources for a target attribute, then use Mapping annotation to provide a correct source
- In case a target attribute is not present in the parameter object(s), then use qualifiedByName annotation, and whose Named method's logic would be provided
- In case a Named method seems to be generic, then create a BaseMapper interface containing the generic Named methods, and this interface would be extended by the original Mapper class 

**** To be followed in Test classes
- Make sure the latest JUNIT version is followed
- Tests should be both extensive and intensive
- Make sure to use parameterized tests so that each code piece is tested on different scenarios (min 3-4 scenarios or data objects at least)
- Keep each test methods as modular as possible
- Make sure that the Objects (Request, Response, Dao, etc.) are created in separate util classes
- These can be used either as a request when calling the API (using mockmvc) or be used to mock external (api) calls or be used to assert the Expected responses and actual Responses. They can even be used it assert against the data that got inserted in the mongo container (when using test-containers in a fully fledged integration test)
- Make sure that exception thrown cases are also tested
- Make sure the method names are followed as per the Code base (camelCase)
- Make sure that ID variables (such as userId, etc.) use UUID.randomUUID.toString();
- If any variables can be taken outside the method and be reused that should be done (by defining it as private static final)

**** To be followed in a logic flow
- In case a specific class in not present, create that class in the respective directory and package
- In case a method is not present, create that method in its respective class
- If a methods's logic component is getting too long, try to refactor them into logical sub-modules

**** To be followed while writing Controller class
- Annotate the Methods with suitable actions (GET, POST, PUT, DELETE)
- name the controller methods according to the business logic that it helps in
- Make sure to have the endpoint defined according to the business logic