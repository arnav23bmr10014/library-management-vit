# library-management-vit
Console Based Library Management System

The Library Management System is a Java-based console application designed to simulate the core administrative operations of a physical library. It provides a centralized interface for managing library assets (Books and DVDs), registering patrons, and handling circulation transactions (borrowing and returning items). This project serves as a practical demonstration of strict Object-Oriented Programming (OOP) principles, utilizing a monolithic single-file structure with static nested classes to demonstrate modularity within a constrained environment.

Features

Multi-Type Inventory Management: Support for adding and tracking different types of media:

Books: Tracked by Title and Author.

DVDs: Tracked by Title and Duration.

Patron Management: Functionality to register library users with unique IDs.

Circulation Control:

Check-Out: Assign items to specific patrons, changing the item's status to "Borrowed."

Return: Process item returns, freeing them up for the next user.

Smart Validation: Prevents invalid operations (e.g., trying to borrow an already borrowed book).

System Logging: Provides clear feedback messages ([INFO], [SUCCESS], [ERROR]) for every user action.

Technologies/Tools Used

Programming Language: Java SE (Standard Edition) - JDK 17 or higher recommended.

Interface: Command Line Interface (CLI).

Tools: Standard Java Compiler (javac).

Libraries: Standard Java Libraries (java.util.* for Lists and Scanners).

Steps to Install & Run the Project

Prerequisites: Ensure you have the Java Development Kit (JDK) installed on your machine.

Verify installation by running java -version in your terminal.

Download/Create the File:

Create a new file named LibrarySystem.java.

Paste the source code provided in the project documentation into this file.

Compile the Code:
Open your terminal or command prompt, navigate to the folder containing the file, and run:

javac LibrarySystem.java


Run the Application:
After successful compilation, run the program using:

java LibrarySystem


Instructions for Testing

Once the application is running, use the numbered menu to test the features:

Test Adding an Item:

Select Option 3 (Add Item).

Choose Type 1 for Book. Enter Title: "Dune", Author: "Frank Herbert".

Check Option 1 (List Inventory) to verify it appears.

Test Registering a Patron:

Select Option 4 (Register Patron).

Enter Name: "John Doe".

Test Checkout (Happy Path):

Select Option 5 (Checkout Item).

Enter the ID of the Book you added (likely 1 or 2).

Enter the ID of the Patron (likely 1 or 2).

Verify the success message.

Test Validation (Error Path):

Try to Checkout the same book again.

Verify the system displays an [ERROR] message stating the item is already checked out.





