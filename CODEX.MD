**** Generic Instructions
- When defining variables, use camel case names (eg., myVariable)
- Do not declare variables using var keyword
- Provide Java docs to complex methods. Explain how the method works with a simple example

**** To be followed while commiting
https://github.com/angular/angular/blob/main/contributing-docs/commit-message-guidelines.md

**** To be followed while writing logs (@Slf4j)
- Inside the log use this format: "[<ClassName> : <methodName>] : <Short log message>"
- The Short log message can include variables, request, objects, etc. which can help debug the log

**** To be followed in a Repository Layer
- In the repository layer, use try catch block, with proper logs
