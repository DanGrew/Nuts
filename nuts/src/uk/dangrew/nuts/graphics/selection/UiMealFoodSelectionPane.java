package uk.dangrew.nuts.graphics.selection;

import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.graphics.meal.MealTableWithControls;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

public class UiMealFoodSelectionPane extends GridPane {

   private final MealTableWithControls table;
   
   public UiMealFoodSelectionPane( Meal meal, Database database ) {
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForRowPercentages( this, 20, 80 );
      styling.configureConstraintsForEvenColumns( this, 1 );
      
      UiFoodSelectionController controller = new UiFoodSelectionController( meal );
      
      add( table = new MealTableWithControls( "Meal", database ), 0, 0 );
      add( new UiFoodSelectionTabs( database, controller ), 0, 1 );
      
      table.table().controller().showMeal( meal );
   }//End Constructor
   
}//End Class
