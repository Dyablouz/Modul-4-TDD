Reflection 1
While implementing the Edit and Delete features, I applied some Clean Code principles which are :

- Giving proper names that represent what does the variable and function made with their purpose.

- I also apply the single responsibility principle where each file focus on their job based on their name, for example the ProductRepository file solely focus on the data manipulation, the Product file in model is used to store the product model.

Secure coding practices that I attempted are the Secure HTTP methods where I used @PostMapping for Delete and Edit rather than @GetMapping. It will ensure that the state-changing operation cannot be triggered by Cross-Site Request Forgery as easy as GET request. I also used random UUID for product ID to make it more secure rather than sequential integer (1,2,3,4,5,6,7.....).

Reflection 2

Writing unit test give me a little bit of confidence in my code that it will works well. I don't think there is a fixed number of unit test that we need to make to verify our program, the focus we need to do is to ensure that every cases are already covered up. 100% Code coverage does not mean that we don't have any bugs or error but it only means that we tested every single line of our code. It does not directly means that our code does not have bugs.

By creating a new Java class similar to the prior functional test will reduce the code quality because the code will have "Code Duplication". By this problem, if in the future I need to do maintenance, I might need to change a lot of exact same line several times manually in every single file. Thus, to improve the cleanliness of the code, I should gather the common logic of the code into a new class and make it as the baseline to the other file that need it.

Reflection module 2



Issue that I fixed  reported by SonarCloud: 
- "Add at least one assertion to this test case". This occurred because the main() method was called inside a test without verifying any output. To fix this, I call an assertDoesNotThrow(() -> EshopApplication.main(new String[] {})); so that it satisfies SonarCloud requirement that every test must have an assertion.
- SonarCloud flagged an empty setUp() method in ProductRepositoryTest. So I simply just deleted it.
- Resolved a "Group dependencies by their destination" code smell by reordering the dependencies in build.gradle.kts so that all testImplementation lines were grouped together.
- SonarCloud warned to "Remove this field injection and use constructor injection instead" in both ProductController and ProductServiceImpl. I fixed this by removing the @Autowired from the fields, making the fields final, and generating constructors to inject the dependencies.
- I removed the throws Exception from multiple test methods in CreateProductFunctionalTest and HomePageFunctionalTest. SonarCloud flagged these because the methods did not actually throw any checked exceptions.



The current implementation already meets the definition of both Continuous Integration (CI) and Continuous Deployment (CD). For CI, the GitHub actions workflow (ci.yml and sonarcloud.yml) automatically build the project, run unit test and perform code quality analysis every time a new code it push or a pull request is opened. This will make sure the code is validated and functionable. For CD, the system is fully automated through the Koyeb's pull-based integration where every validated code is merged to main branch, the PaaS automatically detects the change, build new Docker image and deploy the application.

Reflection module 3

In this project, I applied the SOLID principles primarily within the Car feature, specifically across the controller, service, and repository layers:

For the Single Responsibility Principle (SRP), I ensured each class has only one main reason to change. For instance, CarController solely handles HTTP requests, CarServiceImpl manages the business logic, and InMemoryCarRepository focuses entirely on data storage operations.

I applied the Open/Closed Principle (OCP) so that behavior can be extended without modifying existing logic. By having CarServiceImpl depend on the CarRepository interface, I can easily introduce a PostgresCarRepository in the future without altering the service layer.

To meet the Liskov Substitution Principle (LSP), I made sure subtypes can safely replace base types. InMemoryCarRepository can be used wherever a CarRepository is expected without breaking the application's behavior.

For the Interface Segregation Principle (ISP), I split the car service contracts into smaller, focused interfaces like CarCreator, CarReader, CarUpdater, and CarDeleter. This way, CarController relies on these specific interfaces rather than a single bloated dependency.

Lastly, for the Dependency Inversion Principle (DIP), I ensured high-level modules depend on abstractions. CarController depends on service abstractions, and CarServiceImpl relies on the CarRepository abstraction rather than concrete repository classes.

Applying these SOLID principles gives several distinct advantages to the project:

It improves maintainability because responsibilities are separated. If I need to change how car data is persisted, I don't have to touch the CarController at all.

The code becomes much easier to extend. Adding a database-backed repository just means creating a new implementation of CarRepository.

Testability is significantly higher. With abstractions in place, I can easily mock interfaces like CarReader or CarCreator in my CarControllerTest without needing to set up concrete classes.

It lowers coupling. CarServiceImpl doesn't need to know if the data is stored in memory or a database; it just trusts the contract. This makes refactoring much safer since clear boundaries prevent unexpected side effects across layers.

On the other hand, not applying SOLID principles can lead to several disadvantages:

It creates "God classes" with mixed responsibilities. For example, when the car controller logic was still bundled inside ProductController, changes to cars and products were heavily entangled, making the code fragile and hard to change safely.

The project becomes highly coupled to concrete classes. If CarServiceImpl depended directly on a specific repository class, migrating to a new database would force me to rewrite the service logic.

Testing becomes a lot harder. Without interfaces, unit tests are forced to rely on real implementations, making the test setup complex and brittle.

Reflection module 4

This TDD flow is useful for me, Wrting the failed test first made the requirements clearer. It also reduced ambiguity before implementation thus made me implement and verify each step quicker.

Fast :  Yes, unit test with in-memory data makes it quick.
Independent: Yes, the results are deterministic in current setup.
Repeatable: Yes, results are deterministic in current setup.
Self-validating: Yes, assertions are explicit
Timely: Yes, test are added after implementation step.
