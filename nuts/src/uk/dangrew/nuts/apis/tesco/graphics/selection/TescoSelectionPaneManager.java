package uk.dangrew.nuts.apis.tesco.graphics.selection;

import java.util.List;

import uk.dangrew.nuts.apis.tesco.model.TescoFoodDescription;
import uk.dangrew.nuts.graphics.selection.SelectionPane;

public interface TescoSelectionPaneManager {

   public void layoutTiles( List< TescoFoodDescription > foods );

   public void setSelected( TescoFoodDescription portion, boolean selected );
   
   public SelectionPane selectionPane();
   
}//End Interface

