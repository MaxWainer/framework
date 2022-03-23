# Condtributing to project

To contribute into project, you can simply fork it, and after editing, create pull request (Marking required!).

Some projects require unit-testings, use `test` part for that, if it require in-game experience, provide example of using it and in-game video where showed functionality

### Example marking
- Class
  ```java
  /**
  * Some description
  *
  * @author <Github Name> (<Github profile link>)
  */
  class SomeClass {
  
  }
  ```
- Method
  ```java
  /**
  * Some description
  *
  * @author <Github Name> (<Github profile link>)
  */
  void someMethod() {
  
  }
  ```
- Modifications
  ```java
  // [<Github Name> (<Github profile link>)] Start
  final String someString = "String!";
  final Result someResult = someMethod(someString);
  // [<Github Name> (<Github profile link>)] End
  ```

### Code style rules
- Everything should be formatted using [google-java-format](https://plugins.jetbrains.com/plugin/8527-google-java-format)
- Everything which is not modified should be marked as `final` (In constructors, local variables, fields and etc.)
- Everything which is shouldn't be null, should be marked with `org.jetbrains.annotation.NotNull`
- All collections/maps and etc. if it's not modifiable, should be marked with `org.jetbrains.annotation.Unmodifiable` (And guarded by `Collections#unmodifiable<Map/List/Set>`)
- Every new module with specific feature can have UML diagram (Optional, but if it's something really big, it's pretty required)
