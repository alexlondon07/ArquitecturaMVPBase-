package io.github.alexlondon07.arquitecturamvpbase.schemes;

/**
 * Created by alexlondon07 on 9/30/17.
 */

public interface IProductScheme {

    String PRODUCT_TABLE = "products";
    String COLUMN_ID = "_id";
    String COLUMN_PRODUCT_NAME = "product_name";
    String COLUMN_PRODUCT_DESCRIPTION = "product_description";
    String COLUMN_PRODUCT_QUANTITY = "product_quantity";
    String COLUMN_PRODUCT_PRICE = "product_price";
    String COLUMN_PRODUCT_SYNC = "product_sync";

    String PRODUCT_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + PRODUCT_TABLE + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
            + COLUMN_PRODUCT_DESCRIPTION + " TEXT, "
            + COLUMN_PRODUCT_QUANTITY + " TEXT, "
            + COLUMN_PRODUCT_PRICE + " TEXT, "
            + COLUMN_PRODUCT_SYNC + " TEXT "
            + ");";

     String PRODUCT_ALTER_CREATE_VERSION_1 = " ALTER TABLE "
            + PRODUCT_TABLE + " ADD COLUMN " + COLUMN_PRODUCT_SYNC + " TEXT ";


    String [] PRODUCT_COLUMNS = new String[]{
            COLUMN_ID, COLUMN_PRODUCT_NAME, COLUMN_PRODUCT_DESCRIPTION, COLUMN_PRODUCT_QUANTITY, COLUMN_PRODUCT_PRICE, COLUMN_PRODUCT_SYNC
    };


}
