package io.github.alexlondon07.arquitecturamvpbase.presenter;

import android.util.Log;

import java.util.ArrayList;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.helper.Database;
import io.github.alexlondon07.arquitecturamvpbase.model.BreakfastMenu;
import io.github.alexlondon07.arquitecturamvpbase.model.Food;
import io.github.alexlondon07.arquitecturamvpbase.model.Note;
import io.github.alexlondon07.arquitecturamvpbase.model.Product;
import io.github.alexlondon07.arquitecturamvpbase.repository.BreakfastMenuRepository;
import io.github.alexlondon07.arquitecturamvpbase.repository.NoteRepository;
import io.github.alexlondon07.arquitecturamvpbase.repository.ProductRepository;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.IProductView;
import retrofit.RetrofitError;


/**
 * Created by alexlondon07 on 9/16/17.
 */

public class ProductPresenter extends BasePresenter<IProductView> {

    private ProductRepository productRepository;
    private NoteRepository noteRepository;
    private BreakfastMenuRepository breakfastMenuRepository;
    private final static String TAG ="ProductPresenter";

    public ProductPresenter() {
        productRepository = new ProductRepository();
        noteRepository = new NoteRepository();
        breakfastMenuRepository = new BreakfastMenuRepository();
    }

    public void getProductsPresenter() {
        if(getValidateInternet().isConnected()){
            createThreadProduct();
        }else{
            //getView().showAlertDialogInternet(R.string.error, R.string.validate_internet);
            createThreadProductLocal();
        }
    }

    private void createThreadProductLocal() {
        getView().showProgress(R.string.loading_message);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getProductListLocal();
            }
        });
        thread.start();
    }

    private void createThreadProduct() {
        getView().showProgress(R.string.loading_message);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getProductList();
            }
        });
        thread.start();
    }

    private void getProductListLocal() {
        try {
            ArrayList<Product> productArrayList = Database.productDao.fetchAllProducts();
            getView().showProductList(productArrayList);
        }catch (Exception ex) {
            Log.w("ErrorGetProductList", ex.getMessage());
            getView().showAlertError(R.string.error, ex.getMessage());
        }finally {
            getView().hidePorgress();
        }
    }

    public void getProductList(){
        try {

            //getNoteXML();

            getBreakfastXML();

            ArrayList<Product> productArrayList = productRepository.getProductList();
            getView().showProductList(productArrayList);


        }catch (RetrofitError retrofitError){
            getView().showAlertError(R.string.error, R.string.validate_internet);
        }finally {
            getView().hidePorgress();
        }
    }

    private void getBreakfastXML() {
        //Mostrar información XML de Menú  https://www.w3schools.com/xml/simple.xml
        BreakfastMenu breakfastMenu =breakfastMenuRepository.getBreakfastMenu();

        Log.i(TAG, "BreakfastMenu Food");

        for (Food food: breakfastMenu.getFoodArrayList()) {
            Log.i(TAG, "Name:" + food.getName() + " Precio: " + food.getPrice() + " Descripción : " + food.getDescription() + " Calorias: " + food.getCalories());
        }
    }

    private void getNoteXML() {

        //Mostrando información XML en Log desde  https://www.w3schools.com/xml/note.xml
        Note note = noteRepository.getNote();

        Log.i("Note To: ", note.getTo());
        Log.i("Note Body: ", note.getBody());
        Log.i("Note From: ", note.getFrom());
        Log.i("Note Heading: ", note.getHeading());
    }
}
