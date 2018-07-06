package uk.dangrew.nuts.graphics.table.tree;

import javafx.scene.layout.BorderPane;
import uk.dangrew.nuts.day.DayPlanController;
import uk.dangrew.nuts.graphics.meal.MealControls;

public class DayPlanTreePane extends BorderPane {

   private final DayPlanTreeTable table;
   private final MealControls controls;
   private final DayPlanTreeTableController controller;
   
   public DayPlanTreePane( DayPlanController dayPlanController ) {
      this.setCenter( table = new DayPlanTreeTable( dayPlanController ) );
      this.controller = new DayPlanTreeTableController();
      this.controller.associate( table );
      this.setRight( controls = new MealControls( controller ) );
   }//End Constructor
   
   public DayPlanTreeTableController controller(){
      return controller;
   }//End Method
   
   DayPlanTreeTable table(){
      return table;
   }//End Method
   
   MealControls controls(){
      return controls;
   }//End Method
   
}//End Constructor
