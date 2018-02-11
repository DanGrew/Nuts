package uk.dangrew.nuts.graphics.selection;

import java.util.List;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;

public interface SelectionPaneManager {

   public void layoutTiles( List< Food > foods );

   public void setSelected( FoodPortion portion, boolean selected );
   
   public SelectionPane selectionPane();
   
}//End Interface

