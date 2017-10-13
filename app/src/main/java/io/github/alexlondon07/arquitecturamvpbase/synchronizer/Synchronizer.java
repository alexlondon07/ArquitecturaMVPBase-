package io.github.alexlondon07.arquitecturamvpbase.synchronizer;

import android.util.Log;

import java.util.ArrayList;

import io.github.alexlondon07.arquitecturamvpbase.helper.Database;
import io.github.alexlondon07.arquitecturamvpbase.model.Product;
import io.github.alexlondon07.arquitecturamvpbase.repository.ProductRepository;
import io.github.alexlondon07.arquitecturamvpbase.repository.RepositoryError;
import retrofit.RetrofitError;

import static android.R.id.list;

/**
 * Created by alexlondon07 on 10/12/17.
 */

public class Synchronizer {

    private static final String TAG = "Synchronizer";
    public static Synchronizer instance = null;
    private ProductRepository productRepository = new ProductRepository();

    public static Synchronizer getInstance(){
        if(instance == null){
            instance = new Synchronizer();
        }
        return  instance;
    }

    public void executeSyncLocalToServer(boolean isConnected) {
        //TODO
        if(isConnected){
            ArrayList<Product> productArrayList = Database.productDao.fetchAllProducts();
            for(Product obj:productArrayList){
                if(obj.getSync().equals("0")){
                    //Sync the product
                    productRepository.saveProduct(obj);

                    //Update when finished the Sync
                    obj.setSync("1");
                    Database.productDao.updateProduct(obj.getId(), obj);
                }
            }
        }

        Log.i(TAG, "Network changed: " + isConnected);
    }
}
