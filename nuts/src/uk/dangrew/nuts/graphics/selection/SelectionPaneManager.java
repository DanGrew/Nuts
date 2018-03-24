package uk.dangrew.nuts.graphics.selection;

import java.util.List;

import javafx.scene.Node;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;

public interface SelectionPaneManager {

   public void layoutTiles( List< Food > foods );

   public void setSelected( FoodPortion portion, boolean selected );
   
   public Node selectionPane();
   
}//End Interface

