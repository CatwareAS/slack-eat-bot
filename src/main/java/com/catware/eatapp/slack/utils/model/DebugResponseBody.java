package com.catware.eatapp.slack.utils.model;

import java.util.Objects;

public class DebugResponseBody {

    private String responseType;
    private String text;

    public DebugResponseBody() {
    }

    public DebugResponseBody(String responseType, String text) {
        this.responseType = responseType;
        this.text = text;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DebugResponseBody that = (DebugResponseBody) o;
        return Objects.equals(responseType, that.responseType) &&
                Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseType, text);
    }

    @Override
    public String toString() {
        return "DebugResponseBody{" +
                "responseType='" + responseType + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
