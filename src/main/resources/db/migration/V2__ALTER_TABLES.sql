alter table order_tbl add constraint
	FK584rqmqn15bf6cydc9umr4hti
	foreign key (fk_customer_number)
	references customer_tbl
(customer_number);

alter table order_details_tbl add constraint
	FK7pbee4g4jv72rtbecr4mrq0y2
	foreign key (fk_order_number)
	references order_tbl
(order_number);

alter table order_details_tbl add constraint
	FK6nqimtkqv4m3w789p4nh9qro
	foreign key (fk_product_code)
	references product_tbl
(product_code);