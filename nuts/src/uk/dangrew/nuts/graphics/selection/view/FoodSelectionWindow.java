package uk.dangrew.nuts.graphics.selection.view;

import com.sun.javafx.application.PlatformImpl;

import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionApplier;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionEvent;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

public class FoodSelectionWindow {
   
   private final Stage stage;
   private final FoodSelectionApplier selectionApplier;
   private final UiMealFoodSelectionPane selectionPane;
   
   public FoodSelectionWindow( Database database ) {
      this.stage = new Stage();
      this.selectionApplier = new FoodSelectionApplier( stage, database.foodItems() );
      this.stage.setScene( new Scene( selectionPane = new UiMealFoodSelectionPane( database, selectionApplier ) ) );
      this.stage.setAlwaysOnTop( true );
      this.stage.hide();
      
      new FoodSelectionEvent().register( e -> show( e.getValue().meal() ) );
   }//End Constructor
   
   public void show( Meal meal ){
      selectionApplier.focus( meal );
      selectionPane.selectForMeal( meal );
      PlatformImpl.runAndWait( stage::showAndWait );
   }//End Method
   
   public void show( Template template ){
      selectionApplier.focus( template );
      selectionPane.selectForTemplate( template );
      
      if ( !stage.isShowing() ) {
         PlatformImpl.runAndWait( stage::showAndWait );
      }
   }//End Method
   
}//End Class
