package com.pqd.adapters.web.dummy;

import com.pqd.application.usecase.dummy.GetDummyResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/dummy")
@RequiredArgsConstructor
public class DummyController {

    GetDummyResult getDummyResult;

    @GetMapping("/response")
    String getDummyResponse() {
        return "Dummy controller responds";
    }
}
