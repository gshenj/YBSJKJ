/**
 * Created by jim on 2015/7/30.
 */
-- Database: kisweb

-- DROP DATABASE kisweb;

CREATE DATABASE kisweb
  WITH OWNER = kisweb
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Chinese (Simplified)_People''s Republic of China.936'
       LC_CTYPE = 'Chinese (Simplified)_People''s Republic of China.936'
       CONNECTION LIMIT = -1;
GRANT ALL ON DATABASE kisweb TO kisweb;
GRANT ALL ON DATABASE kisweb TO public;

ALTER DEFAULT PRIVILEGES
    GRANT INSERT, SELECT, UPDATE, DELETE, TRUNCATE, REFERENCES, TRIGGER ON TABLES
    TO public;

ALTER DEFAULT PRIVILEGES
    GRANT INSERT, SELECT, UPDATE, DELETE, TRUNCATE, REFERENCES, TRIGGER ON TABLES
    TO postgres;

-- Sequence: seq_kis

-- DROP SEQUENCE seq_kis;

CREATE SEQUENCE seq_kis
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 313
  CACHE 1;
ALTER TABLE seq_kis
  OWNER TO postgres;


-- Sequence: seq_order_number

-- DROP SEQUENCE seq_order_number;

CREATE SEQUENCE seq_order_number
  INCREMENT 1
  MINVALUE 100001
  MAXVALUE 999999
  START 100002
  CACHE 1
  CYCLE;
ALTER TABLE seq_order_number
  OWNER TO kisweb;


-- Table: city

-- DROP TABLE city;

CREATE TABLE city
(
  id integer NOT NULL,
  name character varying,
  province integer,
  CONSTRAINT pk_city PRIMARY KEY (id),
  CONSTRAINT uk_city_name UNIQUE (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE city
  OWNER TO kisweb;
GRANT ALL ON TABLE city TO public;
GRANT ALL ON TABLE city TO kisweb;
COMMENT ON TABLE city
  IS '城市';

  -- Table: company

-- DROP TABLE company;

CREATE TABLE company
(
  id integer NOT NULL,
  name character varying,
  memo character varying,
  address character varying,
  tel_number character varying,
  mobile_number character varying,
  fax character varying,
  CONSTRAINT pk_company PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE company
  OWNER TO kisweb;
COMMENT ON TABLE company
  IS '公司信息
如：苏州琴惠塑业有限公司';


-- Table: customer

-- DROP TABLE customer;

CREATE TABLE customer
(
  id integer NOT NULL,
  name character varying NOT NULL,
  tel_number character varying, -- seperate with ';' if has more then one tel number.
  mobile_number character varying, -- seperate with ';' if has more then one mobile number.
  address character varying,
  sale_bill_info character varying, -- 开票信息
  city integer,
  principal character varying, -- 负责人
  company integer, -- 对应公司是哪个
  CONSTRAINT pk_customer PRIMARY KEY (id),
  CONSTRAINT fk_customer_city FOREIGN KEY (city)
      REFERENCES city (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_customer_company FOREIGN KEY (company)
      REFERENCES company (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_customer_name UNIQUE (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE customer
  OWNER TO kisweb;
GRANT ALL ON TABLE customer TO public;
GRANT ALL ON TABLE customer TO kisweb;
COMMENT ON COLUMN customer.tel_number IS 'seperate with '';'' if has more then one tel number.';
COMMENT ON COLUMN customer.mobile_number IS 'seperate with '';'' if has more then one mobile number.';
COMMENT ON COLUMN customer.sale_bill_info IS '开票信息';
COMMENT ON COLUMN customer.principal IS '负责人';
COMMENT ON COLUMN customer.company IS '对应公司是哪个';


-- Index: fki_customer_city

-- DROP INDEX fki_customer_city;

CREATE INDEX fki_customer_city
  ON customer
  USING btree
  (city);

-- Index: fki_customer_company

-- DROP INDEX fki_customer_company;

CREATE INDEX fki_customer_company
  ON customer
  USING btree
  (company);

-- Table: kis_role

-- DROP TABLE kis_role;

CREATE TABLE kis_role
(
  id integer NOT NULL,
  name character varying,
  description character varying,
  CONSTRAINT pk_kis_role PRIMARY KEY (id),
  CONSTRAINT uk_kis_role_name UNIQUE (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE kis_role
  OWNER TO kisweb;
GRANT ALL ON TABLE kis_role TO public;
GRANT ALL ON TABLE kis_role TO kisweb;


-- Table: kis_user

-- DROP TABLE kis_user;

CREATE TABLE kis_user
(
  id integer NOT NULL,
  name character varying NOT NULL,
  passwd character varying NOT NULL,
  update_time timestamp without time zone,
  role integer,
  show_name character varying,
  CONSTRAINT pk_kis_user PRIMARY KEY (id),
  CONSTRAINT fk_kis_user_role FOREIGN KEY (role)
      REFERENCES kis_role (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT uk_kis_user_name UNIQUE (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE kis_user
  OWNER TO kisweb;
GRANT ALL ON TABLE kis_user TO public;
GRANT ALL ON TABLE kis_user TO kisweb;


-- Table: product_category

-- DROP TABLE product_category;

CREATE TABLE product_category
(
  id integer NOT NULL,
  name character varying NOT NULL,
  description character varying,
  CONSTRAINT pk_product_category PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE product_category
  OWNER TO kisweb;
GRANT ALL ON TABLE product_category TO public;
GRANT ALL ON TABLE product_category TO kisweb;
COMMENT ON TABLE product_category
  IS '产品类别：聚丙烯，ABS等';


-- Table: product_units

-- DROP TABLE product_units;

CREATE TABLE product_units
(
  id integer NOT NULL,
  cn_name character varying NOT NULL,
  en_name character varying NOT NULL,
  description character varying,
  CONSTRAINT pk_product_units PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE product_units
  OWNER TO kisweb;
GRANT ALL ON TABLE product_units TO public;
GRANT ALL ON TABLE product_units TO kisweb;



-- Table: product_modal

-- DROP TABLE product_modal;

CREATE TABLE product_modal
(
  id integer NOT NULL,
  name character varying NOT NULL,
  description character varying,
  category integer NOT NULL DEFAULT 1, -- 产品分类
  customer integer NOT NULL, -- product belong to which customer!
  units integer, -- 计量单位
  suggest_unit_price numeric(10,2),
  CONSTRAINT pk_product PRIMARY KEY (id),
  CONSTRAINT fk_product_modal_category FOREIGN KEY (category)
      REFERENCES product_category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_product_modal_customer FOREIGN KEY (customer)
      REFERENCES customer (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_product_modal_units FOREIGN KEY (units)
      REFERENCES product_units (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_product_name UNIQUE (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE product_modal
  OWNER TO kisweb;
GRANT ALL ON TABLE product_modal TO public;
GRANT ALL ON TABLE product_modal TO kisweb;
COMMENT ON TABLE product_modal
  IS '产品型号';
COMMENT ON COLUMN product_modal.category IS '产品分类';
COMMENT ON COLUMN product_modal.customer IS 'product belong to which customer!';
COMMENT ON COLUMN product_modal.units IS '计量单位';


-- Index: fki_product_category

-- DROP INDEX fki_product_category;

CREATE INDEX fki_product_category
  ON product_modal
  USING btree
  (category);

-- Index: fki_product_modal_customer

-- DROP INDEX fki_product_modal_customer;

CREATE INDEX fki_product_modal_customer
  ON product_modal
  USING btree
  (customer);

-- Index: fki_product_modal_units

-- DROP INDEX fki_product_modal_units;

CREATE INDEX fki_product_modal_units
  ON product_modal
  USING btree
  (units);


-- Table: sale_order

-- DROP TABLE sale_order;

CREATE TABLE sale_order
(
  id integer NOT NULL,
  date_text character varying, -- 开单日期
  total_sum numeric, -- 订单总金额
  create_user integer, -- 订单制单人
  create_datetime timestamp without time zone, -- 创建时间
  comment character varying, -- 订单备注
  order_number character varying, -- 订单号码
  customer integer, -- 客户id
  content_thumbnail character varying,
  customer_name character varying, -- 冗余属性
  customer_address character varying, -- 冗余属性
  customer_principal character varying, -- 冗余属性
  customer_contact_number character varying, -- 冗余属性
  create_user_name character varying, -- 冗余属性
  flag integer DEFAULT 0, -- 1: 已作废...
  company_name character varying,
  company_address character varying,
  company_contact_number character varying,
  company_fax character varying,
  CONSTRAINT pk_sale_order PRIMARY KEY (id),
  CONSTRAINT fk_sale_order_create_user FOREIGN KEY (create_user)
  REFERENCES kis_user (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_sale_order_customer FOREIGN KEY (customer)
  REFERENCES customer (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_order_number UNIQUE (order_number)
)
WITH (
OIDS=FALSE
);
ALTER TABLE sale_order
OWNER TO kisweb;
COMMENT ON COLUMN sale_order.date_text IS '开单日期';
COMMENT ON COLUMN sale_order.total_sum IS '订单总金额';
COMMENT ON COLUMN sale_order.create_user IS '订单制单人';
COMMENT ON COLUMN sale_order.create_datetime IS '创建时间';
COMMENT ON COLUMN sale_order.comment IS '订单备注';
COMMENT ON COLUMN sale_order.order_number IS '订单号码';
COMMENT ON COLUMN sale_order.customer IS '客户id';
COMMENT ON COLUMN sale_order.customer_name IS '冗余属性';
COMMENT ON COLUMN sale_order.customer_address IS '冗余属性';
COMMENT ON COLUMN sale_order.customer_principal IS '冗余属性';
COMMENT ON COLUMN sale_order.customer_contact_number IS '冗余属性';
COMMENT ON COLUMN sale_order.create_user_name IS '冗余属性';
COMMENT ON COLUMN sale_order.flag IS '1: 已作废
0: 未作废，默认值';


-- Index: fki_sale_order_create_user

-- DROP INDEX fki_sale_order_create_user;

CREATE INDEX fki_sale_order_create_user
ON sale_order
USING btree
(create_user);

-- Index: fki_sale_order_customer

-- DROP INDEX fki_sale_order_customer;

CREATE INDEX fki_sale_order_customer
ON sale_order
USING btree
(customer);




-- Table: sale_order_item

-- DROP TABLE sale_order_item;

CREATE TABLE sale_order_item
(
  id integer NOT NULL,
  product_modal integer,
  quantity numeric NOT NULL, -- xiao shou mou chan pin de shu liang!
  unit_price numeric,
  sum numeric, -- xiao shou jin e
  memo character varying,
  serial_number integer, -- 订单项序号
  sale_order integer, -- 订单项对应哪个订单
  product_modal_text character varying, -- 型号打印的文字，冗余
  product_category_text character varying, -- 产品分类文字，冗余
  product_units_text character varying,
  CONSTRAINT pk_sale_order_item PRIMARY KEY (id),
  CONSTRAINT fk_product_modal_item FOREIGN KEY (product_modal)
      REFERENCES product_modal (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_sale_order_item FOREIGN KEY (sale_order)
      REFERENCES sale_order (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE sale_order_item
  OWNER TO kisweb;
GRANT ALL ON TABLE sale_order_item TO public;
GRANT ALL ON TABLE sale_order_item TO kisweb;
COMMENT ON COLUMN sale_order_item.quantity IS 'xiao shou mou chan pin de shu liang!';
COMMENT ON COLUMN sale_order_item.sum IS 'xiao shou jin e';
COMMENT ON COLUMN sale_order_item.serial_number IS '订单项序号';
COMMENT ON COLUMN sale_order_item.sale_order IS '订单项对应哪个订单';
COMMENT ON COLUMN sale_order_item.product_modal_text IS '型号打印的文字，冗余';
COMMENT ON COLUMN sale_order_item.product_category_text IS '产品分类文字，冗余';


-- Index: fki_product_modal_assigned_item

-- DROP INDEX fki_product_modal_assigned_item;

CREATE INDEX fki_product_modal_assigned_item
  ON sale_order_item
  USING btree
  (product_modal);

-- Index: fki_sale_order_assigned_item

-- DROP INDEX fki_sale_order_assigned_item;

CREATE INDEX fki_sale_order_assigned_item
  ON sale_order_item
  USING btree
  (sale_order);

-- Table: sale_order_number

-- DROP TABLE sale_order_number;

CREATE TABLE sale_order_number
(
  order_number integer,
  id integer
)
WITH (
  OIDS=FALSE
);
ALTER TABLE sale_order_number
  OWNER TO kisweb;
