package com.pqd.adapters.web.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.io.Serializable;

@Value
@AllArgsConstructor
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    String jwttoken;
}
