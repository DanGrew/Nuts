package uk.dangrew.nuts.apis.tesco.graphics.selection;

import java.util.ArrayList;
import java.util.List;

import uk.dangrew.nuts.apis.tesco.api.Tesco;
import uk.dangrew.nuts.apis.tesco.model.nuts.TescoFoodDescription;
import uk.dangrew.nuts.graphics.selection.view.UiFoodSelector;

public class UiTescoDescriptionController implements UiFoodSelector< TescoFoodDescription > {

   private final UiTescoFoodDescriptionPane options;
   private final Tesco tesco;
   
   private TescoFoodDescriptionSelectionPaneManager selectionPane;
   private TescoFoodDescription selected;
   
   public UiTescoDescriptionController( UiTescoFoodDescriptionPane options ) {
      this( new Tesco(), options );
   }//End Constructor
   
   UiTescoDescriptionController( Tesco tesco, UiTescoFoodDescriptionPane options ) {
      this.options = options;
      this.tesco = tesco;
   }//End Constructor
   
   public void controlSelection( TescoFoodDescriptionSelectionPaneManager pane ) {
      this.selectionPane = pane;
      fireLayoutChanges( new ArrayList<>() );
   }//End Method
   
   private void fireLayoutChanges( List< TescoFoodDescription > descriptions ){
      selectionPane.layoutTiles( descriptions );
   }//End Method

   @Override public boolean isSelected( TescoFoodDescription food ) {
      return food == selected;
   }//End Method

   @Override public void deselect( TescoFoodDescription food ) {
      if ( selected == null ) {
         return;
      } else if ( selected == food ) {
         selectionPane.setSelected( food, false );
         selected = null;
         options.clearOptions();
      }
   }//End Method

   @Override public void select( TescoFoodDescription food ) {
      deselect( selected );
      this.selected = food;
      this.selectionPane.setSelected( food, true );
      this.tesco.downloadProductDetail( food );
      this.options.showOptions( food );
   }//End Method
   
   public void search( String criteria ) {
      fireLayoutChanges( tesco.search( criteria ) );
   }//End Method

}//End Class
