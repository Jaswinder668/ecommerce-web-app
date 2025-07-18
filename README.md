# 🛍️ E-Commerce Web App

This is a full-stack E-Commerce Web Application built using **Spring Boot**, **Spring MVC**, **Hibernate**, and **MySQL**. It includes both user and admin features for managing products, cart, orders, and checkout functionality.

---

## 📌 Features

### 👤 User Features:
- User Registration and Login
- View All Products
- Add Products to Cart
- Place Orders
- View Order History

### 🛠️ Admin Features:
- Admin Dashboard
- Add / Edit / Delete Products
- View All Users
- Manage All Orders
- Update Order Status

---

## 🧰 Tech Stack

| Layer       | Technology Used                   |
|------------|------------------------------------|
| Frontend   | HTML, CSS, JavaScript, Thymeleaf   |
| Backend    | Java, Spring Boot, Spring MVC, Spring Data JPA |
| Database   | MySQL                              |
| Tools      | Maven, Git, GitHub, Postman        |

---

## 🚀 How to Run the Project Locally

1. **Clone the repository:**

```bash
git clone https://github.com/Jaswinder668/ecommerce-web-app.git
Open the project in your favorite IDE (e.g., IntelliJ IDEA, Eclipse).

Create a MySQL database:

sql
Copy
Edit
CREATE DATABASE ecommerce_app;
Update application.properties with your MySQL credentials:

properties
Copy
Edit
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_app
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
Run the project:

bash
Copy
Edit
mvn spring-boot:run
Visit in browser:

arduino
Copy
Edit
http://localhost:8080
📁 Project Structure
css
Copy
Edit
ecommerce-web-app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/in/ecommerce/
│   │   │       ├── controller/
│   │   │       ├── entity/
│   │   │       ├── repository/
│   │   │       └── service/
│   │   └── resources/
│   │       ├── templates/
│   │       ├── static/
│   │       └── application.properties
├── pom.xml
└── README.md
📸 Screenshots
(Add your app screenshots here — e.g., homepage, product list, cart page, admin panel, etc.)

🙋‍♂️ Author
Jaswinder Singh
Java Full Stack Developer
📧 jaswinder668@gmail.com
🔗 GitHub

📃 License
This project is for educational purposes only. All rights reserved © Jaswinder Singh.

yaml
Copy
Edit

---

If you want, I can also:
- Add badges (build, license, GitHub stars)
- Write a `LICENSE` file
- Create screenshots section with example placeholders

Let me know what to add next!

