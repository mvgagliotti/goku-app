package com.goku.gokubackend.domain;

import java.util.Optional;

public interface Entity {
    Optional<String> getId();
    default boolean isNew() {
        return getId().isEmpty();
    }
    default boolean hasBusinessKey() { return false; }
}
