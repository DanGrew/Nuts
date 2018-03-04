package uk.dangrew.nuts.apis.tesco.graphics.selection;

import java.util.List;

import uk.dangrew.nuts.apis.tesco.item.TescoFoodReference;
import uk.dangrew.nuts.graphics.selection.SelectionPane;

public interface TescoSelectionPaneManager {

   public void layoutTiles( List< TescoFoodReference > foods );

   public void setSelected( TescoFoodReference portion, boolean selected );
   
   public SelectionPane selectionPane();
   
}//End Interface

