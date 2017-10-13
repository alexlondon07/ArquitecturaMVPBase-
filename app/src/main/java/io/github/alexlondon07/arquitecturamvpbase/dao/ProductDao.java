package io.github.alexlondon07.arquitecturamvpbase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import io.github.alexlondon07.arquitecturamvpbase.helper.Database;
import io.github.alexlondon07.arquitecturamvpbase.model.Product;
import io.github.alexlondon07.arquitecturamvpbase.providers.DbContentProvider;
import io.github.alexlondon07.arquitecturamvpbase.schemes.IProductScheme;

/**
 * Created by alexlondon07 on 9/30/17.
 */

public class ProductDao extends DbContentProvider implements IProductScheme, IProductDao {

    private Cursor cursor;
    private ContentValues initialValues;

    public ProductDao(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public ArrayList<Product> fetchAllProducts() {
        ArrayList<Product> productList =  new ArrayList<>();
        cursor = super.query(PRODUCT_TABLE, PRODUCT_COLUMNS,null, null, COLUMN_PRODUCT_NAME);
        if(cursor != null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                Product product = cursorToEntity(cursor);
                productList.add(product);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return productList;
    }

    public void setContentValue(Product product){
        initialValues = new ContentValues();
        initialValues.put(COLUMN_ID, product.getId());
        initialValues.put(COLUMN_PRODUCT_NAME, product.getName());
        initialValues.put(COLUMN_PRODUCT_DESCRIPTION, product.getDescription());
        initialValues.put(COLUMN_PRODUCT_QUANTITY, product.getQuantity());
        initialValues.put(COLUMN_PRODUCT_PRICE, product.getPrice());
        initialValues.put(COLUMN_PRODUCT_SYNC, product.getSync());
    }

    public ContentValues getContentValue(){
        return initialValues;
    }

    @Override
    public Boolean createProduct(Product product) {
        setContentValue(product);
        try {
            return super.insert(PRODUCT_TABLE, getContentValue()) >= 0;
            /*
                if(totalInsert == -1){
                    return true:
                }
                return false
            */
        }catch (SQLiteConstraintException e){
            Log.e("DBErrorCreateProduct", e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean deleteProduct(String id) {
        try {
            String selection = COLUMN_ID + " = ?";
            String[] selectionArgs = { id };
            return super.delete(PRODUCT_TABLE, selection, selectionArgs) > 0;
        }catch (SQLiteConstraintException ex){
            Log.e("DBErrorDeleteProduct", ex.getMessage());
            return false;
        }
    }

    @Override
    public Boolean updateProduct(String id, Product product) {
        setContentValue(product);
        try{
            String selection = COLUMN_ID + " = ?";
            String[] selectionArgs = { id };
            return super.update(PRODUCT_TABLE, getContentValue(), selection, selectionArgs) > 0;
        }catch (SQLiteConstraintException ex){
            Log.e("DBErrorUpdateProduct", ex.getMessage());
            return false;
        }
    }

    @Override
    protected Product cursorToEntity(Cursor cursor) {
        Product product = new Product();
        int nameIndex;

        if(cursor != null){
            if(cursor.getColumnIndex(COLUMN_ID) != -1){
                nameIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                product.setId(cursor.getString(nameIndex));
            }

            if(cursor.getColumnIndex(COLUMN_PRODUCT_NAME) != -1){
                nameIndex = cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME);
                product.setName(cursor.getString(nameIndex));
            }

            if(cursor.getColumnIndex(COLUMN_PRODUCT_DESCRIPTION) != -1){
                nameIndex = cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_DESCRIPTION);
                product.setDescription(cursor.getString(nameIndex));
            }

            if(cursor.getColumnIndex(COLUMN_PRODUCT_PRICE) != -1){
                nameIndex = cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_PRICE);
                product.setPrice(cursor.getString(nameIndex));
            }

            if(cursor.getColumnIndex(COLUMN_PRODUCT_QUANTITY) != -1){
                nameIndex = cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_QUANTITY);
                product.setQuantity(cursor.getString(nameIndex));
            }

            if(cursor.getColumnIndex(COLUMN_PRODUCT_SYNC) != -1){
                nameIndex = cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_SYNC);
                product.setSync(cursor.getString(nameIndex));
            }
        }
        return product;
    }
}
