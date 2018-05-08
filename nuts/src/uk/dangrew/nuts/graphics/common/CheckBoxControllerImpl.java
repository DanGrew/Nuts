package uk.dangrew.nuts.graphics.common;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import uk.dangrew.nuts.system.Concept;

public abstract class CheckBoxControllerImpl< TypeT extends Concept > implements CheckBoxController< TypeT >{

   private final Map< TypeT, BooleanProperty > conceptProperties;
   private final Map< BooleanProperty, TypeT > propertyConcepts;
   private final ChangeListener< Boolean > booleanBinding;
   
   public CheckBoxControllerImpl() {
      this.conceptProperties = new HashMap<>();
      this.propertyConcepts = new HashMap<>();
      this.booleanBinding = this::apply;
   }//End Constructor
   
   protected abstract void apply( ObservableValue< ? extends Boolean > property, boolean o, boolean included );
   
   protected TypeT conceptFor( ObservableValue< ? extends Boolean > property ) {
      return propertyConcepts.get( property );
   }//End Method
   
   @Override public BooleanProperty propertyFor( TypeT food ) {
      if ( !conceptProperties.containsKey( food ) ) {
         BooleanProperty property = new SimpleBooleanProperty(false );
         conceptProperties.put( food, property );
         propertyConcepts.put( property, food );
         property.addListener( booleanBinding );
      }
      return conceptProperties.get( food );
   }//End Method
   
}//End Class
