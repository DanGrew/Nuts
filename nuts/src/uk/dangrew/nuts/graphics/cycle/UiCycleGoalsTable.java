/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */package uk.dangrew.nuts.graphics.cycle;

import uk.dangrew.nuts.goal.DerivedCalorieGoal;
import uk.dangrew.nuts.graphics.table.ConceptTable;

public class UiCycleGoalsTable extends ConceptTable< DerivedCalorieGoal > {

   public UiCycleGoalsTable() {
      this( new UiCycleGoalsTableController() );
   }//End Constructor
   
   public UiCycleGoalsTable( UiCycleGoalsTableController controller ) {
      super( new UiCycleGoalsTableColumns(), controller );
   }//End Constructor
   
   @Override public UiCycleGoalsTableController controller() {
      return ( UiCycleGoalsTableController ) super.controller();
   }//End Method
   
}//End Class
