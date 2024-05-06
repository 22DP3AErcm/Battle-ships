package org.openjfx;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ResolutionService {
    private static DoubleProperty resolutionProperty = new SimpleDoubleProperty();

    // Getter and setter for the resolution property
    public static DoubleProperty resolutionProperty() {
        return resolutionProperty;
    }
}
