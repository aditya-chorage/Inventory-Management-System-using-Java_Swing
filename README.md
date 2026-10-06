Inventory Management System
📌 Project Overview
The Inventory Management System is a Java-based desktop application developed to simplify and organize the management of products in an inventory. The project provides a graphical user interface (GUI) through which users can add, view, update, delete, and search product records.
The application is developed using Java Swing and follows basic Object-Oriented Programming (OOP) concepts. Product information is represented using a Product class, while an ArrayList is used to store multiple product objects during program execution.
The system is designed as a simple academic project that demonstrates how Java can be used to develop a functional desktop-based management application.
🎯 Objectives
The main objectives of the project are:
- To develop a simple and user-friendly inventory management application.
- To maintain product information in an organized manner.
- To allow users to add new products to the inventory.
- To provide options for updating existing product information.
- To allow users to delete unwanted product records.
- To search for products using Product ID or Product Name.
- To identify products having low stock.
- To calculate the total quantity of products available.
- To calculate the total value of the inventory.
- To implement input validation and exception handling.
- To demonstrate Java OOP concepts and GUI programming.
✨ Features
1. Add Product
Users can add a new product by entering:
- Product ID
- Product Name
- Category
- Price
- Quantity
The system validates the entered information before adding the product.
2. View Products
All products are displayed in a JTable with the following information:
Field	Description
ID	Unique product identification number
Product Name	Name of the product
Category	Product category
Price	Price per unit
Quantity	Available stock
Status	Stock availability status


3. Update Product
Users can select an existing product and modify its:
- Product Name
- Category
- Price
- Quantity
This allows inventory information to be kept up to date.
4. Delete Product
A selected product can be removed from the inventory. The application asks for confirmation before deleting the record.
5. Search Product
The search feature allows users to find products using:
- Product ID
- Product Name
This makes it easier to locate a particular product when the inventory contains multiple records.
6. Low Stock Detection
The system automatically identifies products whose quantity is less than 5 units.
