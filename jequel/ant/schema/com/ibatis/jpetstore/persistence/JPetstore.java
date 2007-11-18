package com.ibatis.jpetstore.persistence;

import de.jexp.jequel.table.Field;
import de.jexp.jequel.table.BaseTable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author de.jexp.jequel.generator.JavaFileGenerationProcessor
 * @since Sa Nov 17 09:55:48 CET 2007
 * Generated from: -- database product: HSQL Database Engine 1.8.0
-- driver: org.hsqldb.jdbcDriver - HSQL Database Engine Driver 1.8.0
-- connection: jdbc:hsqldb:data/test
-- schema pattern: nullnull 
 */

@SuppressWarnings({"NonConstantFieldWithUpperCaseName"})
public abstract class JPetstore {
public final static class INVENTORY extends BaseTable<INVENTORY> {
/** PK: INVENTORYFK: ITEM */
    public final Field ITEMID = foreignKey(JPetstore.ITEM.class,"ITEMID").primaryKey();
    public final Field QTY = integer();
    { initFields(); }
}

public final static INVENTORY INVENTORY = new INVENTORY();
public final static class SIGNON extends BaseTable<SIGNON> {
/** PK: SIGNON */
    public final Field USERNAME = string().primaryKey();
    public final Field PASSWORD = string();
    { initFields(); }
}

public final static SIGNON SIGNON = new SIGNON();
public final static class LINEITEM extends BaseTable<LINEITEM> {
/** PK: LINEITEMFK: ORDERS */
    public final Field ORDERID = foreignKey(JPetstore.ORDERS.class,"ORDERID").primaryKey();
/** PK: LINEITEM */
    public final Field LINENUM = integer().primaryKey();
    public final Field ITEMID = string();
    public final Field QUANTITY = integer();
    public final Field UNITPRICE = numeric();
    { initFields(); }
}

public final static LINEITEM LINEITEM = new LINEITEM();
public final static class ORDERSTATUS extends BaseTable<ORDERSTATUS> {
/** PK: ORDERSTATUSFK: ORDERS */
    public final Field ORDERID = foreignKey(JPetstore.ORDERS.class,"ORDERID").primaryKey();
/** PK: ORDERSTATUS */
    public final Field LINENUM = integer().primaryKey();
    public final Field TIMESTAMP = date();
    public final Field STATUS = string();
    { initFields(); }
}

public final static ORDERSTATUS ORDERSTATUS = new ORDERSTATUS();
public final static class SEQUENCE extends BaseTable<SEQUENCE> {
/** PK: SEQUENCE */
    public final Field NAME = string().primaryKey();
    public final Field NEXTID = integer();
    { initFields(); }
}

public final static SEQUENCE SEQUENCE = new SEQUENCE();
public final static class ORDERS extends BaseTable<ORDERS> {
/** PK: ORDERS */
    public final Field ORDERID = integer().primaryKey();
/** FK: ACCOUNT */
    public final Field USERID = foreignKey(JPetstore.ACCOUNT.class,"USERID");
    public final Field ORDERDATE = date();
    public final Field SHIPCITY = string();
    public final Field SHIPSTATE = string();
    public final Field SHIPZIP = string();
    public final Field SHIPCOUNTRY = string();
    public final Field BILLCITY = string();
    public final Field BILLSTATE = string();
    public final Field BILLZIP = string();
    public final Field BILLCOUNTRY = string();
    public final Field COURIER = string();
    public final Field TOTALPRICE = numeric();
    public final Field BILLTOFIRSTNAME = string();
    public final Field BILLTOLASTNAME = string();
    public final Field SHIPTOFIRSTNAME = string();
    public final Field SHIPTOLASTNAME = string();
    public final Field CREDITCARD = string();
    public final Field EXPRDATE = string();
    public final Field CARDTYPE = string();
    public final Field LOCALE = string();
    { initFields(); }
}

public final static ORDERS ORDERS = new ORDERS();
public final static class BANNERDATA extends BaseTable<BANNERDATA> {
/** PK: BANNERDATA */
    public final Field FAVCATEGORY = string().primaryKey();
    public final Field BANNERNAME = string();
    { initFields(); }
}

public final static BANNERDATA BANNERDATA = new BANNERDATA();
public final static class PRODUCT extends BaseTable<PRODUCT> {
/** PK: PRODUCT */
    public final Field PRODUCTID = string().primaryKey();
/** FK: CATEGORY */
    public final Field CATEGORY = foreignKey(JPetstore.CATEGORY.class,"CATID");
    public final Field NAME = string();
    public final Field DESCN = string();
    { initFields(); }
}

public final static PRODUCT PRODUCT = new PRODUCT();
public final static class ACCOUNT extends BaseTable<ACCOUNT> {
/** PK: ACCOUNT */
    public final Field USERID = string().primaryKey();
    public final Field EMAIL = string();
    public final Field FIRSTNAME = string();
    public final Field LASTNAME = string();
    public final Field STATUS = string();
    public final Field CITY = string();
    public final Field STATE = string();
    public final Field ZIP = string();
    public final Field COUNTRY = string();
    public final Field PHONE = string();
    { initFields(); }
}

public final static ACCOUNT ACCOUNT = new ACCOUNT();
public final static class PROFILE extends BaseTable<PROFILE> {
/** PK: PROFILEFK: ACCOUNT */
    public final Field USERID = foreignKey(JPetstore.ACCOUNT.class,"USERID").primaryKey();
    public final Field LANGPREF = string();
    public final Field FAVCATEGORY = string();
    public final Field MYLISTOPT = integer();
    public final Field BANNEROPT = integer();
    { initFields(); }
}

public final static PROFILE PROFILE = new PROFILE();
public final static class CATEGORY extends BaseTable<CATEGORY> {
/** PK: CATEGORY */
    public final Field CATID = string().primaryKey();
    public final Field NAME = string();
    public final Field DESCN = string();
    { initFields(); }
}

public final static CATEGORY CATEGORY = new CATEGORY();
public final static class ITEM extends BaseTable<ITEM> {
/** PK: ITEM */
    public final Field ITEMID = string().primaryKey();
/** FK: PRODUCT */
    public final Field PRODUCTID = foreignKey(JPetstore.PRODUCT.class,"PRODUCTID");
    public final Field LISTPRICE = numeric();
    public final Field UNITCOST = numeric();
/** FK: SUPPLIER */
    public final Field SUPPLIER = foreignKey(JPetstore.SUPPLIER.class,"SUPPID");
    public final Field STATUS = string();
    { initFields(); }
}

public final static ITEM ITEM = new ITEM();
public final static class SUPPLIER extends BaseTable<SUPPLIER> {
/** PK: SUPPLIER */
    public final Field SUPPID = integer().primaryKey();
    public final Field NAME = string();
    public final Field STATUS = string();
    public final Field CITY = string();
    public final Field STATE = string();
    public final Field ZIP = string();
    public final Field PHONE = string();
    { initFields(); }
}

public final static SUPPLIER SUPPLIER = new SUPPLIER();
}
