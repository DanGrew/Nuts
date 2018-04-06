/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */package uk.dangrew.nuts.graphics.cycle;

import uk.dangrew.nuts.cycle.Cycle;
import uk.dangrew.nuts.cycle.CycleStore;
import uk.dangrew.nuts.goal.CalorieGoalStore;
import uk.dangrew.nuts.graphics.table.ConceptTable;

public class UiCycleTable extends ConceptTable< Cycle > {

   public UiCycleTable( CycleStore cycles, CalorieGoalStore goals ) {
      this( new UiCycleTableController( cycles, goals ) );
   }//End Constructor
   
   public UiCycleTable( UiCycleTableController controller ) {
      super( new UiCycleTableColumns(), controller );
   }//End Constructor
   
}//End Class
