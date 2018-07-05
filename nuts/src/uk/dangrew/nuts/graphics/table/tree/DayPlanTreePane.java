package uk.dangrew.nuts.graphics.table.tree;

import javafx.scene.layout.BorderPane;
import uk.dangrew.nuts.graphics.meal.MealControls;

public class DayPlanTreePane extends BorderPane {

   private final DayPlanTreeTable table;
   private final DayPlanTreeTableController controller;
   
   public DayPlanTreePane() {
      setCenter( table = new DayPlanTreeTable() );
      controller = new DayPlanTreeTableController();
      controller.associate( table );
      MealControls controls = new MealControls( controller );
      setRight( controls );
   }//End Constructor
   
   public DayPlanTreeTableController controller(){
      return controller;
   }//End Method
   
}//End Constructor
