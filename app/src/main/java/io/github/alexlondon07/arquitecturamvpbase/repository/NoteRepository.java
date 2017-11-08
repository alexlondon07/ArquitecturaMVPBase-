package io.github.alexlondon07.arquitecturamvpbase.repository;

import io.github.alexlondon07.arquitecturamvpbase.helper.ServicesFactory;
import io.github.alexlondon07.arquitecturamvpbase.helper.TypeDecryption;
import io.github.alexlondon07.arquitecturamvpbase.model.Note;
import io.github.alexlondon07.arquitecturamvpbase.service.IServices;

/**
 * Created by alexlondon07 on 11/7/17.
 */

public class NoteRepository implements  INoteRepository{

    private IServices services;

    public NoteRepository() {
        ServicesFactory servicesFactory = new ServicesFactory(TypeDecryption.XML);
        services = (IServices) servicesFactory.getInstance(IServices.class);
    }

    @Override
    public Note getNote() {
        Note note = services.getNote();
        return note;
    }
}
