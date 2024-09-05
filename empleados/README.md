# Java MVC Project with DAO and CRUD

## Table of Contents

- [Introduction](#introduction)
- [Architecture](#architecture)
- [DAO Pattern](#dao-pattern)
- [Application Properties](#application-properties)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Configuration](#configuration)
- [Example of Use](#example-of-use)
- [Conclusion](#conclusion)

## Introduction

This project is a web application developed in Java that follows the Model-View-Controller (MVC) design pattern and uses the Data Access Object (DAO) pattern. The application uses Servlets and JSP to render the views and implements a CRUD (Create, Read, Update, Delete) for data management.

## Architecture

The application is divided into the following layers:

*   **Model**: Represents the business logic and data of the application.
*   **View**: Handles rendering the user interface and displaying data.
*   **Controller**: Receives user requests and coordinates business logic and views.

## DAO Pattern

The DAO pattern is used to encapsulate data access logic and provide a layer of abstraction between the application and the database.

## Application Properties

The application properties are centralized in two files:

*   **application.properties**: Stores general application properties.
*   **database.properties**: Stores database properties.

Both files are loaded through a configuration file for each property.

## Features

The application implements a CRUD for data management, which includes:

*   **Create**: Allows creating new records in the database.
*   **Read**: Allows reading existing records in the database.
*   **Update**: Allows updating existing records in the database.
*   **Delete**: Allows deleting existing records in the database.

## Technologies Used

*   **Java**: Programming language used to develop the application.
*   **MVC**: Design pattern used to structure the application.
*   **DAO**: Data access pattern used to encapsulate data access logic.
*   **Servlets**: Used to receive and process user requests.
*   **JSP**: Used to render the user interface and display data.
*   **JavaScript**: Used to manage user interface functions, such as:
    + Keeping the cursor in the search bar.
    + Rendering the list of results in real-time while typing in the search bar.
    + Managing the confirmation modal for deleting an item.
*   **Lombok**: Used to reduce boilerplate code and improve code readability.
    + Annotations such as `@Data`, `@Getter`, and `@Setter` are used to automatically generate getters and setters for classes.
    + The `@NoArgsConstructor` and `@AllArgsConstructor` annotations are used to generate constructors.
*   **JUnit**: Used for unit testing and integration testing.
    + Test cases are written to verify the functionality of individual components and the entire application.
    + Mocking is used to isolate dependencies and test complex scenarios.

## Configuration

To configure the application, you need to edit the **application.properties** and **database.properties** files and load them through a configuration file for each property.

## Example of Use

To use the application, simply access the application URL and use the user interface to interact with the application.

## Conclusion

This application is an example of how to use the MVC and DAO patterns to develop a Java web application that implements a CRUD for data management. The application is easy to configure and use, and can be customized to adapt to specific user needs. By using Lombok and JUnit, we can improve code readability and ensure that the application is stable and reliable.