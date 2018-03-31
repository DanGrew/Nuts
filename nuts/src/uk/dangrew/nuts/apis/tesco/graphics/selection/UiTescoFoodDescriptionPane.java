package uk.dangrew.nuts.apis.tesco.graphics.selection;

import java.util.Collection;
import java.util.Collections;

import javafx.scene.layout.BorderPane;
import uk.dangrew.nuts.apis.tesco.model.nuts.TescoFoodDescription;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.selection.FoodSelectionManager;
import uk.dangrew.nuts.graphics.selection.FoodSelectionPaneManager;
import uk.dangrew.nuts.graphics.selection.UiFoodSelectionPane;
import uk.dangrew.nuts.graphics.selection.UiFoodSelector;

public class UiTescoFoodDescriptionPane extends BorderPane implements UiFoodSelector< FoodPortion > {

   private final UiFoodSelector< FoodPortion > selectionController;
   private final FoodSelectionPaneManager selectionPaneManager;
   private final FoodSelectionManager selectionManager;
   private final TescoFoodItemGenerator tescoFoodItemGenerator;
   
   public UiTescoFoodDescriptionPane( UiFoodSelector< FoodPortion > selectionController ) {
      this( 
               selectionController, 
               new FoodSelectionManager(), 
               new UiFoodSelectionPane( 1, 5 ),
               new TescoFoodItemGenerator()
      );
   }//End Constructor
   
   UiTescoFoodDescriptionPane( 
            UiFoodSelector< FoodPortion > selectionController, 
            FoodSelectionManager selectionManager, 
            UiFoodSelectionPane selectionPane,
            TescoFoodItemGenerator tescoFoodItemGenerator
   ) {
      this.selectionController = selectionController;
      this.selectionPaneManager = new FoodSelectionPaneManager( this, selectionPane );
      this.selectionManager = selectionManager;
      this.tescoFoodItemGenerator = tescoFoodItemGenerator;
      this.setCenter( selectionPaneManager.selectionPane() );
   }//End Constructor

   public void clearOptions() {
      selectionPaneManager.layoutTiles( Collections.emptyList() );
   }//End Method

   public void showOptions( TescoFoodDescription description ) {
      resetSelection();
      selectionPaneManager.layoutTiles( tescoFoodItemGenerator.generateFoodItemsFor( description ) );
   }//End Method

   @Override public boolean isSelected( FoodPortion food ) {
      return selectionManager.isSelected( food );
   }//End Method

   @Override public void deselect( FoodPortion food ) {
      selectionManager.deselect( food );
      selectionPaneManager.setSelected( food, false );
      selectionController.deselect( food );
   }//End Method

   @Override public void select( FoodPortion food ) {
      this.resetSelection();
      this.selectionManager.select( food );
      this.selectionPaneManager.setSelected( food, true );
      this.selectionController.select( food );
   }//End Method
   
   private void resetSelection(){
      Collection< FoodPortion > selection = selectionManager.getAndClearSelection();
      selection.forEach( this::deselect );
   }//End Method
   
}//End Class
