package uk.dangrew.nuts.apis.tesco.graphics.selection;

import java.util.List;

import uk.dangrew.nuts.apis.tesco.item.TescoFoodItem;
import uk.dangrew.nuts.graphics.selection.SelectionPane;

public interface TescoSelectionPaneManager {

   public void layoutTiles( List< TescoFoodItem > foods );

   public void setSelected( TescoFoodItem portion, boolean selected );
   
   public SelectionPane selectionPane();
   
}//End Interface

