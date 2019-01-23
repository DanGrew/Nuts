package uk.dangrew.nuts.meal;

import javafx.collections.ObservableList;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;

public interface FoodHolder extends Food {

   @Override public FoodHolder duplicate();
   
   public ObservableList< FoodPortion > portions();

   public void swap( FoodPortion portion1, FoodPortion portion2 );

}//End Interface