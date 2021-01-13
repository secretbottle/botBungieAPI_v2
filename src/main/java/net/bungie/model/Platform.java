package net.bungie.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Platform {
    STEAM("Steam"),
    PSN ("PSN"),
    XBOX("XBOX"),
    STADIA("Stadia");

    private final String value;
}
