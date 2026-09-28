package com.example.studyhub.data;

/** Common shape every entity exposes so one adapter/fragment can render any model. */
public interface Displayable {
    int getId();
    String getTitleText();
    String getBodyText();
    /** Optional third line; may be null/empty. */
    String getExtraText();
}
