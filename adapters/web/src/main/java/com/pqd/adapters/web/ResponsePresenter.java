package com.pqd.adapters.web;

import com.pqd.application.usecase.AbstractResponse;
import org.springframework.http.ResponseEntity;

public interface ResponsePresenter {

    void present(AbstractResponse response);

    ResponseEntity<?> getViewModel();

}
