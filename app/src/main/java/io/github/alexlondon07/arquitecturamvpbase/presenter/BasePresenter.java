package io.github.alexlondon07.arquitecturamvpbase.presenter;

import android.os.Bundle;

import io.github.alexlondon07.arquitecturamvpbase.helper.IValidateInternet;
import io.github.alexlondon07.arquitecturamvpbase.views.IBaseView;

/**
 * Created by alexlondon07 on 9/16/17.
 */

public class BasePresenter <T extends IBaseView> {
   private T view;
   private IValidateInternet validateInternet;

   public BasePresenter() {
   }

   public void inject(T view, IValidateInternet validateInternet){
      this.view = view;
      this.validateInternet = validateInternet;
   }

   public T getView() {
      return view;
   }

   public IValidateInternet getValidateInternet() {
      return validateInternet;
   }

}
