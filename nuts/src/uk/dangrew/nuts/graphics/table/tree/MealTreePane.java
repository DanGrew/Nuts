package uk.dangrew.nuts.graphics.table.tree;

import javafx.scene.layout.BorderPane;
import uk.dangrew.nuts.graphics.meal.MealControls;
import uk.dangrew.nuts.graphics.meal.MealTableController;

public class MealTreePane extends BorderPane {

   private final DayPlanTreeTable table;
   private final MealTreeTableControllerImpl controller;
   
   public MealTreePane() {
      setCenter( table = new DayPlanTreeTable() );
      controller = new MealTreeTableControllerImpl();
      controller.associate( table );
      MealControls controls = new MealControls( controller );
      setRight( controls );
   }//End Constructor
   
   public MealTableController controller(){
      return controller;
   }//End Method
   
}//End Constructor
