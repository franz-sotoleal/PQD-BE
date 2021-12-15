package com.pqd.application.usecase.product;

import com.pqd.application.domain.jenkins.JenkinsInfo;
import com.pqd.application.domain.jira.JiraInfo;
import com.pqd.application.domain.product.Product;
import com.pqd.application.domain.sonarqube.SonarqubeInfo;
import com.pqd.application.usecase.AbstractResponse;
import com.pqd.application.usecase.UseCase;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Optional;

@RequiredArgsConstructor
@UseCase
public class SaveProduct {

    private final ProductGateway productGateway;

    private final GenerateToken generateToken;

    public Response execute(Request request) {
        checkIfToolInfoExists(request);
        String token = generateToken.execute().getToken();
        Product product = Product.builder()
                                 .token(token)
                                 .name(request.getName())
                                 .sonarqubeInfo(request.getSonarqubeInfo())
                                 .jiraInfo(request.getJiraInfo())
                                 .jenkinsInfo(request.getJenkinsInfo())
                                 .build();

        return Response.of(productGateway.save(product));
    }


    @Value(staticConstructor = "of")
    @EqualsAndHashCode(callSuper = false)
    public static class Response extends AbstractResponse {
        Product product;
    }

    @Value(staticConstructor = "of")
    public static class Request {
        String name;

        Optional<SonarqubeInfo> sonarqubeInfo;

        Optional<JiraInfo> jiraInfo;

        Optional<JenkinsInfo> jenkinsInfo;
    }

    private void checkIfToolInfoExists(Request request) {
        if (request.getJiraInfo().isEmpty() && request.getSonarqubeInfo().isEmpty() && request.getJenkinsInfo().isEmpty()) {
            throw new SaveProductException("At least one tool need to be specified");
        }
    }

    public static class SaveProductException extends RuntimeException {
        public SaveProductException(String message) {
            super(message);
        }
    }
}
