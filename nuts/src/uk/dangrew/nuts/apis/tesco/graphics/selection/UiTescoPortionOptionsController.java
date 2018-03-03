package uk.dangrew.nuts.apis.tesco.graphics.selection;

import uk.dangrew.nuts.apis.tesco.api.TescoFoodItemCache;
import uk.dangrew.nuts.apis.tesco.item.TescoFoodItem;

public class UiTescoPortionOptionsController implements UiTescoFoodSelector {

   private final UiTescoFoodPortionOptions options;
   private final TescoFoodItemCache itemsCache;
   
   private TescoSelectionPaneManager selectionPane;
   private TescoFoodItem selected;
   
   public UiTescoPortionOptionsController( UiTescoFoodPortionOptions options ) {
      this.options = options;
      this.itemsCache = new TescoFoodItemCache();
   }//End Constructor
   
   public void controlSelection( TescoSelectionPaneManager pane ) {
      this.selectionPane = pane;
      fireLayoutChanges();
   }//End Method
   
   private void fireLayoutChanges(){
      selectionPane.layoutTiles( itemsCache.getFoodItems() );
   }//End Method

   @Override public boolean isSelected( TescoFoodItem food ) {
      return food == selected;
   }//End Method

   @Override public void deselect( TescoFoodItem food ) {
      if ( selected == null ) {
         return;
      } else if ( selected == food ) {
         selectionPane.setSelected( food, false );
         selected = null;
         options.clearOptions();
      }
   }//End Method

   @Override public void select( TescoFoodItem food ) {
      deselect( selected );
      this.selected = food;
      this.selectionPane.setSelected( food, true );
      this.options.showOptions( itemsCache.getOptionsFor( food ) );
   }//End Method

}//End Class
