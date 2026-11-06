-- Step 1: Temporarily disable constraint safety checks
SET FOREIGN_KEY_CHECKS = 0;

-- Step 2: Clean wipe existing relational database views
DROP TABLE IF EXISTS `payments`;
DROP TABLE IF EXISTS `bill_payments`;
DROP TABLE IF EXISTS `invoices`;
DROP TABLE IF EXISTS `bills`;
DROP TABLE IF EXISTS `sales_order_items`;
DROP TABLE IF EXISTS `sales_orders`;
DROP TABLE IF EXISTS `purchase_order_items`;
DROP TABLE IF EXISTS `purchase_orders`;
DROP TABLE IF EXISTS `sales_opportunities`;
DROP TABLE IF EXISTS `leads`;
DROP TABLE IF EXISTS `customers`;
DROP TABLE IF EXISTS `vendors`;
DROP TABLE IF EXISTS `inventory_transactions`;
DROP TABLE IF EXISTS `inventory_stocks`;
DROP TABLE IF EXISTS `production_routing`;
DROP TABLE IF EXISTS `production_orders`;
DROP TABLE IF EXISTS `bom_items`;
DROP TABLE IF EXISTS `bills_of_materials`;
DROP TABLE IF EXISTS `products`;
DROP TABLE IF EXISTS `product_categories`;
DROP TABLE IF EXISTS `work_centers`;
DROP TABLE IF EXISTS `warehouses`;
DROP TABLE IF EXISTS `journal_items`;
DROP TABLE IF EXISTS `journal_entries`;
DROP TABLE IF EXISTS `journals`;
DROP TABLE IF EXISTS `chart_of_accounts`;
DROP TABLE IF EXISTS `payrolls`;
DROP TABLE IF EXISTS `attendance`;
DROP TABLE IF EXISTS `employees`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `departments`;
DROP TABLE IF EXISTS `branches`;
DROP TABLE IF EXISTS `companies`;

-- Step 3: Recreate all structural layout tables with auto-increment keys
CREATE TABLE `companies` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `tax_number` varchar(50) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL
);

CREATE TABLE `branches` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `company_id` INT NOT NULL,
  `name` varchar(100) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` varchar(50) NOT NULL
);

CREATE TABLE `departments` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `branch_id` INT NOT NULL,
  `name` varchar(100) NOT NULL
);

CREATE TABLE `users` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `department_id` INT NOT NULL,
  `username` varchar(50) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `email` varchar(100) NOT NULL,
  `role` varchar(50) NOT NULL
);

CREATE TABLE `employees` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `hire_date` date NOT NULL,
  `salary` decimal(12,2) NOT NULL
);

CREATE TABLE `attendance` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `employee_id` INT NOT NULL,
  `work_date` date NOT NULL,
  `check_in` timestamp NOT NULL,
  `check_out` timestamp NOT NULL
);

CREATE TABLE `payrolls` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `employee_id` INT NOT NULL,
  `period_start` date NOT NULL,
  `period_end` date NOT NULL,
  `gross_salary` decimal(12,2) NOT NULL,
  `net_salary` decimal(12,2) NOT NULL
);

CREATE TABLE `chart_of_accounts` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `name` varchar(100) NOT NULL,
  `type` varchar(50) NOT NULL
);

CREATE TABLE `journals` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `code` varchar(10) NOT NULL
);

CREATE TABLE `journal_entries` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `journal_id` INT NOT NULL,
  `entry_date` date NOT NULL,
  `description` varchar(255) NOT NULL,
  `reference` varchar(100) NOT NULL
);

CREATE TABLE `journal_items` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `journal_entry_id` INT NOT NULL,
  `account_id` INT NOT NULL,
  `debit` decimal(12,2) NOT NULL,
  `credit` decimal(12,2) NOT NULL
);

CREATE TABLE `warehouses` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `branch_id` INT NOT NULL,
  `name` varchar(100) NOT NULL,
  `location` varchar(255) NOT NULL
);

CREATE TABLE `product_categories` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` varchar(255) NOT NULL
);

CREATE TABLE `products` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `category_id` INT NOT NULL,
  `name` varchar(150) NOT NULL,
  `sku` varchar(50) NOT NULL,
  `barcode` varchar(50) NOT NULL,
  `price` decimal(12,2) NOT NULL,
  `cost` decimal(12,2) NOT NULL
);

CREATE TABLE `inventory_stocks` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `warehouse_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `quantity` decimal(12,4) NOT NULL
);

CREATE TABLE `inventory_transactions` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `warehouse_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `transaction_type` varchar(50) NOT NULL,
  `quantity` decimal(12,4) NOT NULL,
  `transaction_date` timestamp NOT NULL
);

CREATE TABLE `customers` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `address` varchar(255) NOT NULL
);

CREATE TABLE `vendors` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `contact_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `address` varchar(255) NOT NULL
);

CREATE TABLE `leads` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `customer_id` INT NOT NULL,
  `source` varchar(100) NOT NULL,
  `status` varchar(50) NOT NULL,
  `estimated_value` decimal(12,2) NOT NULL
);

CREATE TABLE `sales_opportunities` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `lead_id` INT NOT NULL,
  `title` varchar(150) NOT NULL,
  `stage` varchar(50) NOT NULL,
  `close_date` date NOT NULL
);

CREATE TABLE `sales_orders` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `customer_id` INT NOT NULL,
  `branch_id` INT NOT NULL,
  `order_date` date NOT NULL,
  `status` varchar(50) NOT NULL,
  `total_amount` decimal(12,2) NOT NULL
);

CREATE TABLE `sales_order_items` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `sales_order_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `quantity` decimal(12,4) NOT NULL,
  `unit_price` decimal(12,2) NOT NULL,
  `discount` decimal(5,2) NOT NULL
);

CREATE TABLE `purchase_orders` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `vendor_id` INT NOT NULL,
  `branch_id` INT NOT NULL,
  `order_date` date NOT NULL,
  `status` varchar(50) NOT NULL,
  `total_amount` decimal(12,2) NOT NULL
);

CREATE TABLE `purchase_order_items` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `purchase_order_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `quantity` decimal(12,4) NOT NULL,
  `unit_cost` decimal(12,2) NOT NULL
);

CREATE TABLE `bills` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `vendor_id` INT NOT NULL,
  `purchase_order_id` INT NOT NULL,
  `bill_date` date NOT NULL,
  `due_date` date NOT NULL,
  `total_amount` decimal(12,2) NOT NULL,
  `status` varchar(50) NOT NULL
);

CREATE TABLE `bill_payments` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `bill_id` INT NOT NULL,
  `account_id` INT NOT NULL,
  `payment_date` date NOT NULL,
  `amount` decimal(12,2) NOT NULL,
  `payment_method` varchar(50) NOT NULL
);

CREATE TABLE `invoices` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `customer_id` INT NOT NULL,
  `sales_order_id` INT NOT NULL,
  `invoice_date` date NOT NULL,
  `due_date` date NOT NULL,
  `total_amount` decimal(12,2) NOT NULL,
  `status` varchar(50) NOT NULL
);

CREATE TABLE `payments` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `invoice_id` INT NOT NULL,
  `account_id` INT NOT NULL,
  `payment_date` date NOT NULL,
  `amount` decimal(12,2) NOT NULL,
  `payment_method` varchar(50) NOT NULL
);

CREATE TABLE `bills_of_materials` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `product_id` INT NOT NULL,
  `quantity` decimal(12,4) NOT NULL
);

CREATE TABLE `bom_items` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `bom_id` INT NOT NULL,
  `component_product_id` INT NOT NULL,
  `quantity` decimal(12,4) NOT NULL
);

CREATE TABLE `production_orders` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `product_id` INT NOT NULL,
  `warehouse_id` INT NOT NULL,
  `quantity` decimal(12,4) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `status` varchar(50) NOT NULL
);

CREATE TABLE `work_centers` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `hourly_cost` decimal(12,2) NOT NULL
);

CREATE TABLE `production_routing` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `production_order_id` INT NOT NULL,
  `work_center_id` INT NOT NULL,
  `sequence` INT NOT NULL,
  `setup_time` decimal(8,2) NOT NULL,
  `run_time` decimal(8,2) NOT NULL
);

-- Step 4: Safely append structural foreign key relationships
ALTER TABLE `branches` ADD FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`);
ALTER TABLE `departments` ADD FOREIGN KEY (`branch_id`) REFERENCES `branches` (`id`);
ALTER TABLE `users` ADD FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`);
ALTER TABLE `employees` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
ALTER TABLE `attendance` ADD FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`);
ALTER TABLE `payrolls` ADD FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`);
ALTER TABLE `journal_entries` ADD FOREIGN KEY (`journal_id`) REFERENCES `journals` (`id`);
ALTER TABLE `journal_items` ADD FOREIGN KEY (`journal_entry_id`) REFERENCES `journal_entries` (`id`);
ALTER TABLE `journal_items` ADD FOREIGN KEY (`account_id`) REFERENCES `chart_of_accounts` (`id`);
ALTER TABLE `warehouses` ADD FOREIGN KEY (`branch_id`) REFERENCES `branches` (`id`);
ALTER TABLE `products` ADD FOREIGN KEY (`category_id`) REFERENCES `product_categories` (`id`);
ALTER TABLE `inventory_stocks` ADD FOREIGN KEY (`warehouse_id`) REFERENCES `warehouses` (`id`);
ALTER TABLE `inventory_stocks` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);
ALTER TABLE `inventory_transactions` ADD FOREIGN KEY (`warehouse_id`) REFERENCES `warehouses` (`id`);
ALTER TABLE `inventory_transactions` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);
ALTER TABLE `leads` ADD FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`);
ALTER TABLE sales_opportunities ADD FOREIGN KEY (lead_id) REFERENCES leads (id);
ALTER TABLE sales_orders ADD FOREIGN KEY (customer_id) REFERENCES customers (id);
ALTER TABLE sales_orders ADD FOREIGN KEY (branch_id) REFERENCES branches (id);
ALTER TABLE sales_order_items ADD FOREIGN KEY (sales_order_id) REFERENCES sales_orders (id);
ALTER TABLE sales_order_items ADD FOREIGN KEY (product_id) REFERENCES products (id);
ALTER TABLE purchase_orders ADD FOREIGN KEY (vendor_id) REFERENCES vendors (id);
ALTER TABLE purchase_orders ADD FOREIGN KEY (branch_id) REFERENCES branches (id);
ALTER TABLE purchase_order_items ADD FOREIGN KEY (purchase_order_id) REFERENCES purchase_orders (id);
ALTER TABLE purchase_order_items ADD FOREIGN KEY (product_id) REFERENCES products (id);
ALTER TABLE bills ADD FOREIGN KEY (vendor_id) REFERENCES vendors (id);
ALTER TABLE bills ADD FOREIGN KEY (purchase_order_id) REFERENCES purchase_orders (id);
ALTER TABLE bill_payments ADD FOREIGN KEY (bill_id) REFERENCES bills (id);
ALTER TABLE bill_payments ADD FOREIGN KEY (account_id) REFERENCES chart_of_accounts (id);
ALTER TABLE invoices ADD FOREIGN KEY (customer_id) REFERENCES customers (id);
ALTER TABLE invoices ADD FOREIGN KEY (sales_order_id) REFERENCES sales_orders (id);
ALTER TABLE payments ADD FOREIGN KEY (invoice_id) REFERENCES invoices (id);
ALTER TABLE payments ADD FOREIGN KEY (account_id) REFERENCES chart_of_accounts (id);
ALTER TABLE bills_of_materials ADD FOREIGN KEY (product_id) REFERENCES products (id);
ALTER TABLE bom_items ADD FOREIGN KEY (bom_id) REFERENCES bills_of_materials (id);
ALTER TABLE bom_items ADD FOREIGN KEY (component_product_id) REFERENCES products (id);
ALTER TABLE production_orders ADD FOREIGN KEY (product_id) REFERENCES products (id);
ALTER TABLE production_orders ADD FOREIGN KEY (warehouse_id) REFERENCES warehouses (id);
ALTER TABLE production_routing ADD FOREIGN KEY (production_order_id) REFERENCES production_orders (id);
ALTER TABLE production_routing ADD FOREIGN KEY (work_center_id) REFERENCES work_centers (id);
-- Step 5: Restore constraint safety verification rules
SET FOREIGN_KEY_CHECKS = 1;