# Creating an cinema management system

### 1. Introduction
The proposed JAVA application is a complete systemic approach on a management system to be used from cinema owners for better management of movies, employees, clients, tickets and more.

For the **movies**, we save the actors, the directors, a brief summary, the url with the trailers (can be more than one), the production date, the director (can be more than one) and its genre (social drama, comedy, action, horror, etc). A movie can have more than one genre designation. We also keep track of any awards for the film (award name, award date, description as well as whether it was only nominated for the award or if it won).

For the **directors**, we keep information about the name and surname of each contributor, a short biography, the url with a photo of it. The actors can be directors and/or actors. In addition, we keep track of the awards they have received (award name, award date, description and whether they were only nominated for the award or if they won it).

The **cinema** has a set of halls and for each we know its capacity, whether it is air conditioned or not, the floor on which it is located and its name (unique). **Tickets** refer to a specific movie in a room, at a specific location and start and end time. Tickets are divided into simple and 3D which are charged 15% more than ordinary. **Seats** have a specific unique number in a room and a description of where the seat is on the vertical axis ("low", "middle", or "high") on the horizontal axis ("center", "right wing", "Left wing”), as well as whether or not it is in the corridor. The numbering of the positions starts from the top left and ends at the bottom right.

**Employees** work in shifts. Each shift employs 3 cashiers, 4 employees and 2 cleaners and a shift manager. Each employee can work only one shift per day (morning or afternoon) and the system keeps track of the days and shifts he/she works, his/her degree, a short CV, years of service in a similar position and the date of recruitment. Per month, the salary of each employee is based on the shifts he/she worked (10 hours per shift) and his hourly cost.

Every **customer** has an electronic card in the system. The cinema provides cards with a unique code which customers use every time they visit it. A card can correspond to more than one customer (e.g. members of a family but each member will have their own card in the system) but each customer must be associated with only one card. Each card in each transaction accumulates a number of bonus points (10% of the cost of tickets) and when they exceed 200 the customer wins 2 free tickets. The customer has a unique password and the system stores data about the movies he/she has watched, the card he is associated with, as well as personal information such as sheet, date of birth, marital status. Finally, a customer at the end of the movie rates it with 1 to 5 stars.

The following assumptions exist in this approach:
* Each movie is shown in only one room. Respectively, each room shows only one film.
* The first release date of a movie does not correspond to the movie release date in the cinema. That is, the cinema can show films that were released many years ago..
* Each customer of the cinema has his own card in the system however he can use any card. Points are charged to the cardholder.
* There are only two shift zones. Morning (8 am - 6 pm) and afternoon (6:01 pm - 4 am)
* A movie can share same actors/producers/directors with other movies.
* In each employee, we save the hours he works per shift and how much he is paid each hour (depending on his specialty).
* For each card, we store in a variable how many tickets he is entitled to and if the customer received them.

## 2. Building the database (MySQL)

Use the `create.sql` file to create the following tables:

* `movie` table:
Each film will have its own title, synopsis and release date.
* `trailer_url` table:
Because a movie can have more than one trailer url’s, we define them in a separate table that stores the url value and the movie to which it corresponds.
* `category` table:
Because a movie can belong to more than one category, we create a separate table that stores a category and the movie to which it belongs.
* `room` table:
Each room has its own name (rname separate for each room) and we keep in variables the total seats, the available vacancies, the floor on which it is located, if it has air condition which movie is shown in that room and who is the responsible employee(rusher).
* `ticket` table:
Each ticket has its start time (and the movie to which it corresponds), the end time, its location, whether it is plain or 3D (sep variable), its price, for which room it is, and which cashier issued it (printer).
* `custsees` relationship:
It has as primary key who watches the movie (watcher) and which movie watches (wmovie). It also saves how much he rated the movie and when he saw it (wdate - linked to the ticket start_time).
* `customer` table:
Customers are separated from each other by their code (code) and no name is kept for customers in the system. We also save gender, birthday and marital status.
* `cartel` table:
Each customer has his own card in the system with primary key the owner_code that shows in the password of each customer.
* `card` table:
Every customer who tickets to the system uses a card that is linked to its owner via a code (own_code -> code), and each card has its own id, a set of points, the code of the person who used it (without necessarily being the owner), how many tickets the cardholder is entitled to and whether he/she got the tickets he/she is entitled to.
* `mawarded` relationship:
This relationship connects awards with films. The awarded gets a value of 1 if the film was awarded a prize and 0 if it was not awarded. It also saves the award date, which film was awarded (aw_movie showing the title of the film) and aw_award showing the name of the award the film won.
* `award` table:
The award won (either by a film or by a film actor). For each award we keep the name of the award and its description.
* `factors` table:
Film actors. For each one we keep his first and last name, a CV, the address of his photo and the id that distinguishes him as a factor.
* `star` table:
Refers to the actors and for each one an id that distinguishes him/her.
* `director` table:
Refers to the directors and for each one an id that distinguishes him/her.
* `mhas` relationship:
It means that a film has an actor, ie an actor or a director. It keeps the name of the movie and the id of the actor that it shows in the primary keys of the respective entities.
* `fawarded` relationship:
It means that someone was nominated for an award. The variable fawarded gets a value of 1 if the factor won the prize and 0 if it was simply nominated. It also saves the award date which actor was awarded (indicating the actor's id - actor or director -), and which award he won which shows the name of the award.
* `seats` table:
It is stored in which room the position is located, what number corresponds to it, in which order it is located. The two variables vertical_num and horizontal_num due to SET only get predefined arguments that show us where in the room the position is. Finally, the cashier who issued the ticket is saved.
* `worker` table:
Each employee is separated in the system by its wid. It also saves the date of hiring (hdate), how many years of work he has, his CV, what degree he has, how many hours he works per shift (hours), how much he is paid for each hour worked (kostos_h), his salary and his status (the his post).
* `wworks` relationship:
Indicates which employee works in which shift. It has two variables that point to the employee id and the shift id.
* `shift` table:
Each shift has its own id. More specifically:
    * Morning shift: From 8 in the morning until 6 at noon.
    * Evening shift: From 6 and 1 minute at noon until 4 in the evening.
    
    Whether the shift is evening or morning we keep it in the variable time_of_day and finally for each shift we keep the date that was executed.

Use the `insert.sql` file to insert the test records that fill in the information for all tables. More specifically, the inserts consist of:
* 8 movies
* 8 rooms [each room plays only one movie (3D or simple)]
* 6 awards in total (films or actors)
* 9 actors (actors and directors)
* 10 customers (each with his own card in the system)
* 20 employees (6 cashiers, 4 cleaners, 2 shift managers, 8 passengers)
* 36 tickets (3D or just)
* Many seats (closed or free)
* 26 shifts (2 per day for 13 days)
* 10 cards (one for each customer)
* 10 cards (one per customer - some have been used more than once)
* 3 directors
* 6 actors
* 5 categories of tannins
* Relationships that correspond to the numbers of the entities 

## 3. Tools needed

* Programming Language : `Java`
* IDE Version: `Eclipse 4.4`
* Java Connector: `Java Database Connectivity (JDBC)`
* JDB Version: `MySQL Connector/J 5.1.39`
* SQL Version : `MySQL RDBMS (XAMPP)`

# 4. Deploying the Java Application

The steps to deploy the website should be the following:

1. Creating the database 
You may login in any MySQL database management panel and create a new database. You may use the `baseis` name for your database. Make sure you select the UTF-8 encoding (collation utf8_general_ci). The `MyWindow.java` file includes the following lines for a connection to the MySQL database:

        String dbname = "baseis", username = "root", password = "";

        int port = 3306;
    You may update this according to your localhost setup and credentials.
2. Populating the database
Select your database (e.g. `baseis`) and import the `create.sql` file and `insert.sql`, which includes the creation of the tables and the data population.
3. Running the `MyWindow.java` file
This is the main file which includes the all back-end information handling for the application to launch.
4. That's it! You have successfully deployed the system...

# 5. Using the Application

When we launch the application (`MyWindow.java` file), a message is initially displayed as to whether or not we were able to connect to the database. In case for any reason we are unable to connect, we display an error message to the user and terminate the program, otherwise we display a connection success message.

![enter image description here](https://i.ibb.co/qxS6m9t/2.png)

Image 1: Successful connection to database

![enter image description here](https://i.ibb.co/FzrHbGB/1.jpg)

Image 2: Error while connecting to database

If we manage and connect to the database, the main window of the application opens and we are shown the available options of the a) client, b) the shift manager and c) the cashier respectively.

![enter image description here](https://i.ibb.co/bNCy5Gj/3.png)

Image 3: The Main Application Window

## 5.1. The Customer tab
If we want to simulate the operation of the customer, then we press the button **CUSTOMER** and the application opens a new customer window. Then, we are able to see customer-specific information, so we ask for the customer's code to find the information he wants. If a customer code that is in our database is given (code field in the table customer), we display the requested information using a JTextArea.

![enter image description here](https://i.ibb.co/Sw9s164/4.png)

Image 4: Providing the Client Code for access to Customer data

![enter image description here](https://i.ibb.co/C6Z65Q5/5.png)

Image 5: Customer personal information

![enter image description here](https://i.ibb.co/QJS2qpP/h.jpg)

Image 6: Error if the Client Code is not correct

## 5.2. The Shift Manager tab
To simulate the shift manager, select the **SHIFT MANAGER** button in the main application window. After the button is pressed, a new window opens with the options that the shift manager can make using one JComboBox. Some indicative queries the Shift Manager can run are depicted below:

![enter image description here](https://i.ibb.co/4SGLbnz/1.png)

Image 7: Which movies did the male clients watch in a certain period of time (defined by the manager) and rated them 4 or 5 and what was the category of these movies?

![enter image description here](https://i.ibb.co/MfhDJLL/2.png)

Image 8: What is the movie (or movies) with the most tickets in a given time period (defined by the manager)

![enter image description here](https://i.ibb.co/TWZxGXf/4.png)

Image 9: What kind of movies do female customers aged 20-30 choose to watch (in descending order)?

## 5.3. The Cashier tab
If we want to simulate the operation of the cashier, then we press the **CASHIER** button, which opens a new window for the cashier. Once the window opens, the application shows the cashier all the possible choices he can make by using a JComboBox. The available features are the following:
1. Show movie screenings per day and the available seats
2. Validate Ticket
3. Free Tickets feature
4. Manage Client Personal Information
5. Overall Clients charge fee

![enter image description here](https://i.ibb.co/K53wZpX/13123123.jpg)

Image 10: Available features for the Cashier

Let's select the 1st option **'1. Show movie screenings per day and the available seats'**.

![enter image description here](https://i.ibb.co/KbLHF3R/1.png)

Image 11: Enter datetime for the movie screening

![enter image description here](https://i.ibb.co/b1XXmW6/3.png)

Image 12: Results of the query

Let's select the 2nd option **'2. Validate Ticket'**. With each cash-in of a ticket, the customer's points should increase by 10% on the value of the ticket (10 for simple, 11.5 for 3D) and as soon as it reaches 200 or above, the customer wins 2 free tickets!

![enter image description here](https://i.ibb.co/GxyPJXH/4.png)

Image 13: Enter the number of tickets for validation

![enter image description here](https://i.ibb.co/Lp8tndN/5.png)

Image 14: Enter the card number for the issued ticket

![enter image description here](https://i.ibb.co/ctcWzV4/7.png)

Image 15: Enter the ticket details

![enter image description here](https://i.ibb.co/rwg56F1/8.png)

Image 16: Check if client should be awarded with free tickets or not


Let's select the 3rd option **'Free Tickets feature'** and let's suppose that the customer intends to cash-in his free tickets.

![enter image description here](https://i.ibb.co/6yr6Vzc/1.png)

Image 17: Enter the card number for the free issued ticket

![enter image description here](https://i.ibb.co/qyhZSHN/3.png)

Image 18: Confirmation on the cashing of the tickets

![enter image description here](https://i.ibb.co/FhM0WHc/4.jpg)

Image 19: Select the movie to cash in the tickets

Let's select the 4th option **'4. Manage Client Personal Information'**. In option 4,we are able to manage the customer's personal information. Therefore, we ask the customer for his number (code field in the customer table) and display 5 fields as JTextFields with the user details. For example:

![enter image description here](https://i.ibb.co/bWBXVD3/1.png)

Image 20: Customer Information

![enter image description here](https://i.ibb.co/mXBgGwF/2.png)

Image 21: Update Field

![enter image description here](https://i.ibb.co/7RBppTR/3.png)

Image 22: Successful message upon completion















