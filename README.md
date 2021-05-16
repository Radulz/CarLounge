![JaCoCoCarLounge](https://user-images.githubusercontent.com/81571989/118413192-b1dfe980-b6a6-11eb-8e7d-e6b384587715.png)
# CarLounge-CarRental_App
CarLounge - our app is designed as a solution both to people who are seeking a private means of transport for a relatively short period of time and to those who are willing to put their vehicles at other people`s disposal.
* [Java 15 or 16](https://www.oracle.com/java/technologies/javase-downloads.html)
* [JavaFX](https://openjfx.io/openjfx-docs/) (as GUI)
* [Gradle](https://gradle.org/) (as build tool)
* [Nitrite Java](https://www.dizitart.org/nitrite-database.html) (as Database)

## Prerequisites
To be able to install and run this project, please make sure you have installed Java 11 or higher. Otherwise, the setup will not work!
To check your java version, please run `java -version` in the command line.

To install a newer version of Java, you can go to [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html) or [OpenJDK](https://jdk.java.net/).

It would be good if you also installed Gradle to your system. To check if you have Gradle installed run `gradle -version`.

If you need to install Gradle, please refer to this [official Gradle docs](https://docs.gradle.org/current/userguide/installation.html).

Make sure you install JavaFX SDK on your machine, using the instructions provided in the [Official Documentation](https://openjfx.io/openjfx-docs/#install-javafx). Make sure to export the `PATH_TO_FX` environment variable, or to replace it in every command you will find in this documentation from now on, with the `path/to/javafx-sdk-15.0.1/lib`.

_Note: you can download version 15 of the javafx-sdk, by replacing in the download link for version 16 the `16` with `15`._

## Setup & Run
To set up and run the project locally on your machine, please follow the next steps.

### Clone the repository
Clone the repository using:
```git
git clone https://github.com/fis2021/CarLounge-CarRental_App.git
```

### Verify that the project Builds locally
Open a command line session and `cd CarLounge-CarRental_App`. (if necessary)
If you have installed all the prerequisites, you should be able to run any of the following commands:
```
gradle clean build
```
If you prefer to run using the wrappers, you could also build the project using 
```
./gradlew clean build (for Linux or MacOS)
or 
gradlew.bat clean build (for Windows)
```

### Open in IntelliJ IDEA
To open the project in IntelliJ idea, you have to import it as a Gradle project. 
After you import it, in order to be able to run it, you need to set up your IDE according to the [official documentation](https://openjfx.io/openjfx-docs/). Please read the section for `Non-Modular Projects from IDE`.
If you managed to follow all the steps from the tutorial, you should also be able to start the application by pressing the run key to the left of the Main class.

### Run the project with Gradle
The project has already been setup for Gradle according to the above link.
To start and run the project use one of the following commands:
* `gradle run` or `./gradlew run` (to start the `run` task of the `org.openjfx.javafxplugin` plugin)

To understand better how to set up a project using JavaFX 11+ and [Gradle](https://openjfx.io/openjfx-docs/#gradle), please check the [official OpenJFX documentation](https://openjfx.io/).

You should see an application starting, with the first interface being the LogIn one.

Upon clicking the "SIGN UP" button, you will be asked whether you want to register as a Client or as a Provider (which also splits into Individual/Legal Person).

You can try registering as any of the aforementioned.

However, if you try to register a user with the same email/identification number/tax registration number again, you will see an error message.

**Make sure to close the CarLounge-CarRental_App before trying to access the database, because the database only accepts one connection at a time!**


### Technical Details

#### Encrypting Passwords
Encrypting the passwords is done via the following 2 Java functions.

    public static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    public static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }
```

#### Nitrite Java
Nitrite Java was used in the [ClientService.java](https://github.com/fis2021/CarLounge-CarRental_App/blob/main/src/main/java/org/CarLounge/fis/services/ClientService.java) (and all the other User-Type files), where we initialized a database, and a _Repository_ of User objects:
```java
    private static ObjectRepository<User> userRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("registration-example.db").toFile())
                .openOrCreate("test", "test");

        userRepository = database.getRepository(User.class);
    }
```

This Repository was further used to add new Clients (and Providers in their respective files), by using the **insert** method:
```java
    public static void addClient(String email, String password, String fName, String lName, String bDate, String confirmPassword, String cnp) throws UsernameAlreadyExistsException, PasswordDoesNotContainTheRequiredCharacters, EmailFieldIsEmpty, LastNameFieldIsEmpty, PasswordFieldIsEmpty, BirthDateFieldIsEmpty, FirstNameFieldIsEmpty, MinimumAgeIsRequired, PasswordsDoesNotMatch, TextIsNotAValidEmail, ConfirmPasswordFieldIsEmpty, BirthDateIsNotADate, CnpIsMissing, CnpIsNotValid, CnpAlreadyExists {
        checkFields(email, password, fName, lName, bDate, confirmPassword, cnp);
        checkUserDoesNotAlreadyExist(email);
        Client c= new Client(email, encodePassword(email, password), fName, lName, bDate, cnp);
        ClientRepository.insert(c);
    }
```
and to find all users, by using the find method:
```java
    public static void checkUserDoesNotAlreadyExist(String email) throws UsernameAlreadyExistsException {
        for (Client client : ClientRepository.find()) {
            if (Objects.equals(email, client.getEmail()))
                throw new UsernameAlreadyExistsException(email);
        }
        for (Provider provider : LegalPersonProviderService.getProviderRepository().find()) {
            if (Objects.equals(email, provider.getEmail()))
                throw new UsernameAlreadyExistsException(email);
        }
    }
```

## Resources
To understand and learn more about **JavaFX**, you can take a look at some of the following links:
* [Introduction to FXML](https://openjfx.io/javadoc/16/javafx.fxml/javafx/fxml/doc-files/introduction_to_fxml.html)
* [Getting Started with JavaFX](https://openjfx.io/openjfx-docs/)
* [JavaFX Tutorial](https://code.makery.ch/library/javafx-tutorial/)
* [JavaFX Java GUI Design Tutorials](https://www.youtube.com/playlist?list=PL6gx4Cwl9DGBzfXLWLSYVy8EbTdpGbUIG)

To better understand how to use **Nitrite Java**, use the following links:
* [Nitrite Java Github Repository](https://github.com/nitrite/nitrite-java) 
* [Nitrite Java Project Page](https://www.dizitart.org/nitrite-database.html)
* [Nitrite Java Documentation Page](https://www.dizitart.org/nitrite-database/)
* [Nitrite Java: Filters](https://www.dizitart.org/nitrite-database/#filter)
* [Nitrite: How to Create an Embedded Database for Java and Android](https://dzone.com/articles/nitrite-how-to-create-an-embedded-database-for-jav)
* [Nitrite: An Embedded NoSQL Database for Java and Android](https://medium.com/@anidotnet/nitrite-an-embedded-nosql-database-for-java-and-android-318bf48c7758)
