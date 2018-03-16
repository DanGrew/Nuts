package uk.dangrew.nuts.graphics.selection;

import java.util.Collection;

import javafx.stage.Stage;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodItemStore;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.Meal;

public class FoodSelectionApplier implements FoodSelectionWindowStageControls {

   private final Stage stage;
   private final FoodItemStore foodItems;
   
   private Meal focus;
   
   public FoodSelectionApplier( Stage stage, FoodItemStore foodItems ) {
      this.stage = stage;
      this.foodItems = foodItems;
   }//End Constructor

   @Override public void apply( Collection< FoodPortion > selected ) {
      if ( focus != null ) {
         selected.forEach( this::appendToPortions );
      }
      cancel();
   }//End Method
   
   private void appendToPortions( FoodPortion portion ) {
      if ( !( portion.food().get() instanceof FoodItem ) ) {
         return;
      }
      
      FoodItem foodItem = ( FoodItem ) portion.food().get();
      FoodItem databaseItem = foodItems.objectList().stream()
               .filter( f -> f.properties().nameProperty().get().equals( foodItem.properties().nameProperty().get() ) )
               .findFirst().orElse( null );
      if ( databaseItem == null ) {
         foodItems.store( foodItem );
         databaseItem = foodItem;
      }
      
      focus.portions().add( new FoodPortion( databaseItem, portion.portion().get() ) );
   }//End Method

   @Override public void cancel() {
      stage.hide();
   }//End Method

   public void focus( Meal focus ) {
      this.focus = focus;
   }//End Method

}//End Class
