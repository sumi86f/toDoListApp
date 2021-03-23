# Todo App

**Prerequisites:** [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

* [Getting Started](#getting-started)
* [Rest endpoints](#rest-endpoints)
* [Accessing URL](#accessing-url)

## Getting Started

To install the  application, run the following commands:

```bash
git clone https://github.com/sumi86f/toDoListApp.git
cd toDoListApp
```

This will get a copy of the project installed locally. To install all of its dependencies and start each app, follow the instructions below.

To build and run:
 
```bash
	 ./gradlew clean build
    ./gradlew bootRun
```
## Rest endpoints

Exposed endpoints:

  To Add a Todo

```bash
	POST : localhost:8081/todo/add
	Request body: {
				"name":"ref_name",
				"dueDate": "2021-03-25",
				"description":"ref_description",
				"status":"pending"
			}
```

   To fetch all Todo's 

```bash
	GET : localhost:8081/todo/all
```

  To fetch all pending Todo's 

```bash
	GET : localhost:8081/todo/pending
```

   To fetch all completed Todo's 

```bash
	GET : localhost:8081/todo/completed
```

  To delete a Todo by id 

```bash
	DELETE : localhost:8081/todo/delete/{id}
```
  To update a Todo

```bash
	PUT : localhost:8081/todo/update
	Request body: {
				"name":"updated_name",
				"dueDate": "2021-03-25",
				"description":"updated_description",
				"status":"pending"
			}
```

## Accessing URL

Url for accessing the application

```bash
	http://localhost:8081
```