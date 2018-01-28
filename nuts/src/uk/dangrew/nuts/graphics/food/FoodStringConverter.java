package uk.dangrew.nuts.graphics.food;

import javafx.collections.ObservableList;
import uk.dangrew.kode.javafx.spinner.StringExtractConverter;
import uk.dangrew.nuts.food.Food;

public class FoodStringConverter extends StringExtractConverter< Food >{
   
   public FoodStringConverter( ObservableList< Food > foods ) {
      super( 
               f -> f.properties().nameProperty().get(), 
               name -> foods.stream().filter( f -> f.properties().nameProperty().get().equals( name ) ).findFirst().get(), 
               null
      );
   }
   
}//End Class

