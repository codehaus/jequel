package de.jexp.jequel.ant.tables;

import de.jexp.jequel.table.Field;
import de.jexp.jequel.table.Table;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author de.jexp.jequel.generator.JavaSingleFileGenerationProcessor
 * @since So Okt 28 01:13:30 CEST 2007
 * Generated from: DB: HSQL Database Engine 1.8.0  
 */

public abstract class JPetstore {

public final static INVENTORY INVENTORY = new INVENTORY();

public final static SIGNON SIGNON = new SIGNON();

public final static LINEITEM LINEITEM = new LINEITEM();

public final static ORDERSTATUS ORDERSTATUS = new ORDERSTATUS();

public final static SEQUENCE SEQUENCE = new SEQUENCE();

public final static ORDERS ORDERS = new ORDERS();

public final static BANNERDATA BANNERDATA = new BANNERDATA();

public final static PRODUCT PRODUCT = new PRODUCT();

public final static ACCOUNT ACCOUNT = new ACCOUNT();

public final static PROFILE PROFILE = new PROFILE();

public final static CATEGORY CATEGORY = new CATEGORY();

public final static ITEM ITEM = new ITEM();

public final static SUPPLIER SUPPLIER = new SUPPLIER();

public final static INVENTORY INVENTORY = new INVENTORY();

public final static class INVENTORY extends Table<INVENTORY> {
/** PK: INVENTORY */
    public Field ITEMID = string().primaryKey();
    public Field QTY = integer();

    {
        initFields();
    }
}

public final static SIGNON SIGNON = new SIGNON();

public final static class SIGNON extends Table<SIGNON> {
/** PK: SIGNON */
    public Field USERNAME = string().primaryKey();
    public Field PASSWORD = string();

    {
        initFields();
    }
}

public final static LINEITEM LINEITEM = new LINEITEM();

public final static class LINEITEM extends Table<LINEITEM> {
/** PK: LINEITEM */
    public Field ORDERID = integer().primaryKey();
/** PK: LINEITEM */
    public Field LINENUM = integer().primaryKey();
    public Field ITEMID = string();
    public Field QUANTITY = integer();
    public Field UNITPRICE = numeric();

    {
        initFields();
    }
}

public final static ORDERSTATUS ORDERSTATUS = new ORDERSTATUS();

public final static class ORDERSTATUS extends Table<ORDERSTATUS> {
/** PK: ORDERSTATUS */
    public Field ORDERID = integer().primaryKey();
/** PK: ORDERSTATUS */
    public Field LINENUM = integer().primaryKey();
    public Field TIMESTAMP = date();
    public Field STATUS = string();

    {
        initFields();
    }
}

public final static SEQUENCE SEQUENCE = new SEQUENCE();

public final static class SEQUENCE extends Table<SEQUENCE> {
/** PK: SEQUENCE */
    public Field NAME = string().primaryKey();
    public Field NEXTID = integer();

    {
        initFields();
    }
}

public final static ORDERS ORDERS = new ORDERS();

public final static class ORDERS extends Table<ORDERS> {
/** PK: ORDERS */
    public Field ORDERID = integer().primaryKey();
    public Field USERID = string();
    public Field ORDERDATE = date();
    public Field SHIPCITY = string();
    public Field SHIPSTATE = string();
    public Field SHIPZIP = string();
    public Field SHIPCOUNTRY = string();
    public Field BILLCITY = string();
    public Field BILLSTATE = string();
    public Field BILLZIP = string();
    public Field BILLCOUNTRY = string();
    public Field COURIER = string();
    public Field TOTALPRICE = numeric();
    public Field BILLTOFIRSTNAME = string();
    public Field BILLTOLASTNAME = string();
    public Field SHIPTOFIRSTNAME = string();
    public Field SHIPTOLASTNAME = string();
    public Field CREDITCARD = string();
    public Field EXPRDATE = string();
    public Field CARDTYPE = string();
    public Field LOCALE = string();

    {
        initFields();
    }
}

public final static BANNERDATA BANNERDATA = new BANNERDATA();

public final static class BANNERDATA extends Table<BANNERDATA> {
/** PK: BANNERDATA */
    public Field FAVCATEGORY = string().primaryKey();
    public Field BANNERNAME = string();

    {
        initFields();
    }
}

public final static PRODUCT PRODUCT = new PRODUCT();

public final static class PRODUCT extends Table<PRODUCT> {
/** PK: PRODUCT */
    public Field PRODUCTID = string().primaryKey();
    public Field CATEGORY = string();
    public Field NAME = string();
    public Field DESCN = string();

    {
        initFields();
    }
}

public final static ACCOUNT ACCOUNT = new ACCOUNT();

public final static class ACCOUNT extends Table<ACCOUNT> {
/** PK: ACCOUNT */
    public Field USERID = string().primaryKey();
    public Field EMAIL = string();
    public Field FIRSTNAME = string();
    public Field LASTNAME = string();
    public Field STATUS = string();
    public Field CITY = string();
    public Field STATE = string();
    public Field ZIP = string();
    public Field COUNTRY = string();
    public Field PHONE = string();

    {
        initFields();
    }
}

public final static PROFILE PROFILE = new PROFILE();

public final static class PROFILE extends Table<PROFILE> {
/** PK: PROFILE */
    public Field USERID = string().primaryKey();
    public Field LANGPREF = string();
    public Field FAVCATEGORY = string();
    public Field MYLISTOPT = integer();
    public Field BANNEROPT = integer();

    {
        initFields();
    }
}

public final static CATEGORY CATEGORY = new CATEGORY();

public final static class CATEGORY extends Table<CATEGORY> {
/** PK: CATEGORY */
    public Field CATID = string().primaryKey();
    public Field NAME = string();
    public Field DESCN = string();

    {
        initFields();
    }
}

public final static ITEM ITEM = new ITEM();

public final static class ITEM extends Table<ITEM> {
/** PK: ITEM */
    public Field ITEMID = string().primaryKey();
    public Field PRODUCTID = string();
    public Field LISTPRICE = numeric();
    public Field UNITCOST = numeric();
    public Field SUPPLIER = integer();
    public Field STATUS = string();

    {
        initFields();
    }
}

public final static SUPPLIER SUPPLIER = new SUPPLIER();

public final static class SUPPLIER extends Table<SUPPLIER> {
/** PK: SUPPLIER */
    public Field SUPPID = integer().primaryKey();
    public Field NAME = string();
    public Field STATUS = string();
    public Field CITY = string();
    public Field STATE = string();
    public Field ZIP = string();
    public Field PHONE = string();

    {
        initFields();
    }
}
}
