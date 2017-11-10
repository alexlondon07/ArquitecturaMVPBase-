package io.github.alexlondon07.arquitecturamvpbase.repository;

import io.github.alexlondon07.arquitecturamvpbase.helper.ServicesFactory;
import io.github.alexlondon07.arquitecturamvpbase.helper.TypeDecryption;
import io.github.alexlondon07.arquitecturamvpbase.model.BreakfastMenu;
import io.github.alexlondon07.arquitecturamvpbase.service.IServices;

/**
 * Created by alexlondon07 on 11/10/17.
 */

public class BreakfastMenuRepository implements IBreakfastMenuRepository {

    private IServices services;

    public BreakfastMenuRepository() {
        ServicesFactory servicesFactory = new ServicesFactory(TypeDecryption.XML);
        services = (IServices) servicesFactory.getInstance(IServices.class);
    }

    @Override
    public BreakfastMenu getBreakfastMenu() {
        return services.getBreakfastMenu();
    }
}
