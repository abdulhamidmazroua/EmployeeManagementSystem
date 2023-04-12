create table employee(
    employee_id varchar(5),
    username varchar(20),
    password varchar(20),
    first_name varchar(20),
    last_name varchar(20),
    gender varchar(10),
    phone varchar(20),
    position varchar(20),
    image varchar(30),
    date date,
    primary key(employee_id)
);


insert into employee values ('E0000', 'admin', 'admin', 'hameed', 'mazroua', 'male', '01125688743', 'vice president', 'none', '2021-12-17');
INSERT INTO employee VALUES ('E0001', 'user1', 'pass1', 'John', 'Doe', 'Male', '123456789', 'Manager', 'image1.png', '2022-01-01');
INSERT INTO employee VALUES ('E0002', 'user2', 'pass2', 'Jane', 'Doe', 'Female', '987654321', 'Assistant Manager', 'image2.png', '2022-01-02');
INSERT INTO employee VALUES ('E0003', 'user3', 'pass3', 'Bob', 'Smith', 'Male', '555555555', 'Team Leader', 'image3.png', '2022-01-03');
INSERT INTO employee VALUES ('E0004', 'user4', 'pass4', 'Alice', 'Johnson', 'Female', '111111111', 'Software Engineer', 'image4.png', '2022-01-04');
INSERT INTO employee VALUES ('E0005', 'user5', 'pass5', 'David', 'Lee', 'Male', '222222222', 'Accountant', 'image5.png', '2022-01-05');
INSERT INTO employee VALUES ('E0006', 'user6', 'pass6', 'Mary', 'Brown', 'Female', '333333333', 'Marketing Coordinator', 'image6.png', '2022-01-06');
INSERT INTO employee VALUES ('E0007', 'user7', 'pass7', 'Kevin', 'Garcia', 'Male', '444444444', 'Sales Representative', 'image7.png', '2022-01-07');
INSERT INTO employee VALUES ('E0008', 'user8', 'pass8', 'Emily', 'Lopez', 'Female', '777777777', 'Human Resources Coordinator', 'image8.png', '2022-01-08');
INSERT INTO employee VALUES ('E0009', 'user9', 'pass9', 'Alex', 'Martinez', 'Male', '888888888', 'Business Analyst', 'image9.png', '2022-01-09');
INSERT INTO employee VALUES ('E0010', 'user10', 'pass10', 'Olivia', 'Nguyen', 'Female', '999999999', 'IT Technician', 'image10.png', '2022-01-10');
INSERT INTO employee VALUES ('E0011', 'user11', 'pass11', 'Daniel', 'Kim', 'Male', '444444555', 'Finance Manager', 'image11.png', '2022-01-11');
INSERT INTO employee VALUES ('E0012', 'user12', 'pass12', 'Sophia', 'Chen', 'Female', '333333222', 'Graphic Designer', 'image12.png', '2022-01-12');
INSERT INTO employee VALUES ('E0013', 'user13', 'pass13', 'Ryan', 'Wang', 'Male', '111222333', 'Business Development Manager', 'image13.png', '2022-01-13');
INSERT INTO employee VALUES ('E0014', 'user14', 'pass14', 'Mia', 'Zhang', 'Female', '444555666', 'Customer Service Representative', 'image14.png', '2022-01-14');
INSERT INTO employee VALUES ('E0015', 'user15', 'pass15', 'Joshua', 'Li', 'Male', '888777666', 'Operations Manager', 'image15.png', '2022-01-15');


create table emp_info(
    id numeric,
    employee_id varchar(5),
    first_name varchar(20),
    last_name varchar(20),
    position varchar(20),
    salary numeric,
    date date,
    primary key (id),
    foreign key (employee_id) references employee(employee_id)
);

