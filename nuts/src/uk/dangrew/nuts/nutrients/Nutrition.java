package uk.dangrew.nuts.nutrients;

import java.util.EnumMap;
import java.util.stream.Stream;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import uk.dangrew.nuts.food.Food;

public class Nutrition {

   private final EnumMap< NutritionalUnit, ObjectProperty< Double > > nutrition;
   
   public Nutrition() {
      this.nutrition = new EnumMap<>( NutritionalUnit.class );
      Stream.of( NutritionalUnit.values() )
         .forEach( n -> nutrition.put( n, new SimpleObjectProperty<>( 0.0 ) ) );
   }//End Constructor
   
   public static OptionalNutrition of( Food food ) {
      if ( food == null ) {
         return new OptionalNutrition();
      }
      
      return new OptionalNutrition( food.nutrition() );
   }//End Method
   
   public void setMacroNutrients( double c, double f, double p ) {
      of( NutritionalUnit.Carbohydrate ).set( c );
      of( NutritionalUnit.Fat ).set( f );
      of( NutritionalUnit.Protein ).set( p );
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
