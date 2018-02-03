package uk.dangrew.nuts.graphics.selection;

import java.util.Collection;

import uk.dangrew.nuts.food.FoodPortion;

public interface FoodSelectionWindowStageControls {

   public void apply( Collection< FoodPortion > selected );
   
   public void cancel();
   
}//End Interface

