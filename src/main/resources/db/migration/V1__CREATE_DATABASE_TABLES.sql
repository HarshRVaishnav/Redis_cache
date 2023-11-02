create table if not exists customer_tbl (
	customer_number integer not null auto_increment,
	address_line1 varchar(50),
	address_line2 varchar(50),
	city varchar(255),
	country varchar(255),
	customer_first_name varchar(25),
	customer_last_name varchar(25),
	phone varchar(255),
	postal_code integer,
	state varchar(255),
	primary key (customer_number)
);


create table if not exists order_tbl (
	order_number integer not null auto_increment,
	comments varchar(500),
	customer_number integer,
	deleted bit not null,
	order_date date,
	status ENUM('CREATED', 'ORDERED', 'CANCELED', 'PENDING', 'SHIPPED', 'DELIVERED'),
	shipped_date date,
	fk_customer_number integer,
	primary key (order_number)
);

create table if not exists order_details_tbl (
	id integer not null, order_number integer,
	price_each double precision,
	product_code integer not null,
	quantity_ordered integer,
	fk_order_number integer,
	fk_product_code integer,
	primary key (id)
);

create table if not exists product_tbl (
	product_code integer not null auto_increment,
	price double precision not null,
	product_description varchar(100),
	product_name varchar(50),
	quantity_in_stock integer,
	primary key (product_code)
);