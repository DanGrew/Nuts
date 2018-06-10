package uk.dangrew.nuts.nutrients;

import java.util.Optional;

import javafx.beans.value.ChangeListener;

public class OptionalNutrition {

   private final Optional< Nutrition > nutrition;
   
   public OptionalNutrition() {
      this( null );
   }//End Constructor
   
   public OptionalNutrition( Nutrition nutritionValue ) {
      this.nutrition = Optional.ofNullable( nutritionValue );
   }//End Constructor
   
   public Nutrition get() {
      return nutrition.orElse( null );
   }//End Method
   
   public boolean listen( ChangeListener< ? super Double > listener ) {
      nutrition.ifPresent( n -> n.listen( listener ) );
      return nutrition.isPresent();
   }//End Method
   
   public boolean stopListening( ChangeListener< ? super Double > listener ) {
      nutrition.ifPresent( n -> n.stopListening( listener ) );
      return nutrition.isPresent();
   }//End Method
   
}//End Class
