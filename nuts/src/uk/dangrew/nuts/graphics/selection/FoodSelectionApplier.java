package uk.dangrew.nuts.graphics.selection;

import java.util.Collection;

import javafx.stage.Stage;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.Meal;

public class FoodSelectionApplier implements FoodSelectionWindowStageControls {

   private final Stage stage;
   
   private Meal focus;
   
   public FoodSelectionApplier( Stage stage ) {
      this.stage = stage;
   }//End Constructor

   @Override public void apply( Collection< FoodPortion > selected ) {
      if ( focus != null ) {
         selected.forEach( p -> focus.portions().add( p.duplicate( "" ) ) );
      }
      cancel();
   }//End Method

   @Override public void cancel() {
      stage.hide();
   }//End Method

   public void focus( Meal focus ) {
      this.focus = focus;
   }//End Method

}//End Class
