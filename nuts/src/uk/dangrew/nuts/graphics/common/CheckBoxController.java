package uk.dangrew.nuts.graphics.common;

import javafx.beans.property.BooleanProperty;

public interface CheckBoxController< TypeT > {

   public BooleanProperty propertyFor( TypeT object );
   
}//End Interface

