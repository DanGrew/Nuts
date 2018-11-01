package uk.dangrew.nuts.graphics.table.tree;

import javafx.scene.layout.BorderPane;
import uk.dangrew.nuts.graphics.meal.MealControls;
import uk.dangrew.nuts.graphics.meal.ShareControls;
import uk.dangrew.nuts.graphics.recipe.integration.RecipeGeneratorControls;
import uk.dangrew.nuts.graphics.table.BasicConceptControls;
import uk.dangrew.nuts.graphics.table.controls.TableControls;
import uk.dangrew.nuts.store.Database;

public class DayPlanTreePane extends BorderPane {

   private final DayPlanTreeTable table;
   private final TableControls controls;
   private final DayPlanTreeTableController controller;
   
   public DayPlanTreePane( Database database ) {
      this.setCenter( table = new DayPlanTreeTable( database ) );
      this.controller = new DayPlanTreeTableController( database );
      this.controller.associate( table );
      this.setRight( controls = new TableControls( 
               new BasicConceptControls( controller ), 
               new MealControls( controller ),
               new ShareControls( controller ),
               new RecipeGeneratorControls( controller )
      ) );
   }//End Constructor
   
   public DayPlanTreeTableController controller(){
      return controller;
   }//End Method
   
   DayPlanTreeTable table(){
      return table;
   }//End Method
   
   TableControls controls(){
      return controls;
   }//End Method
   
}//End Constructor
