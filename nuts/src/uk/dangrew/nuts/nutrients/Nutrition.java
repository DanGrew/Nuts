package uk.dangrew.nuts.nutrients;

import java.util.EnumMap;
import java.util.stream.Stream;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodProperties;

public class Nutrition {

   private final EnumMap< NutritionalUnit, ObjectProperty< Double > > nutrition;
   
   public Nutrition() {
      this.nutrition = new EnumMap<>( NutritionalUnit.class );
      Stream.of( NutritionalUnit.values() )
         .forEach( n -> nutrition.put( n, new SimpleObjectProperty<>( 0.0 ) ) );
   }//End Constructor
   
   public static OptionalNutrition of( FoodProperties properties ) {
      if ( properties == null ) {
         return new OptionalNutrition();
      }
      
      return new OptionalNutrition( properties.nutrition() );
   }//End Method
   
   public static OptionalNutrition of( Food food ) {
      if ( food == null ) {
         return new OptionalNutrition();
      }
      
      return of( food.properties() );
   }//End Method
   
   public ObjectProperty< Double > of( NutritionalUnit nutritionalUnit ) {
      return nutrition.get( nutritionalUnit );
   }//End Method

   public void listen( ChangeListener< ? super Double > listener ) {
      nutrition.values().forEach( p -> p.addListener( listener ) );
   }//End Method
   
   public void stopListening( ChangeListener< ? super Double > listener ) {
      nutrition.values().forEach( p -> p.removeListener( listener ) );
   }//End Method
}//End Class
