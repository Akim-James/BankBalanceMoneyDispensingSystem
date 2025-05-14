package com.discoverybank.bbds.web.model;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record Result(Boolean success, Integer statusCode, String statusReason) implements Serializable {
}
