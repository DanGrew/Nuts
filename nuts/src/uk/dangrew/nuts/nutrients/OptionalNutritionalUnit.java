package uk.dangrew.nuts.nutrients;

import java.util.Optional;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;

public class OptionalNutritionalUnit {

   private final Optional< ObjectProperty< Double > > property;
   
   public OptionalNutritionalUnit() {
      this( null );
   }//End Constructor
   
   public OptionalNutritionalUnit( ObjectProperty< Double > propertyValue ) {
      this.property = Optional.ofNullable( propertyValue );
   }//End Constructor
   
   public ObjectProperty< Double > property(){
      return property.isPresent() ? property.get() : null;
   }//End Method
   
   public boolean set( Double value ) {
      property.ifPresent( p -> p.set( value ) );
      return property.isPresent();
   }//End Method
   
   public Double get() {
      if ( property.isPresent() ) {
         return property.get().get();
      }
      return null;
   }//End Method
   
   public boolean listen( ChangeListener< ? super Double > listener ) {
      property.ifPresent( p -> p.addListener( listener ) );
      return property.isPresent();
   }//End Method
   
   public boolean stopListening( ChangeListener< ? super Double > listener ) {
      property.ifPresent( p -> p.removeListener( listener ) );
      return property.isPresent();
   }//End Method
   
}//End Class
