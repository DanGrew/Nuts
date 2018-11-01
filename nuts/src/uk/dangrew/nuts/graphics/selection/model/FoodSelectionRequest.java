package uk.dangrew.nuts.graphics.selection.model;

import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.meal.FoodHolder;
import uk.dangrew.nuts.template.Template;

public class FoodSelectionRequest {
   
   private final FoodHolder meal;
   private final Goal goal;
   
   public FoodSelectionRequest( Template template ) {
      this( template, template.goalAnalytics().goal().get() );
   }//End Constructor
   
   public FoodSelectionRequest( FoodHolder meal ) {
      this( meal, null );
   }//End Constructor

   public FoodSelectionRequest( FoodHolder meal, Goal goal ) {
      this.meal = meal;
      this.goal = goal;
   }//End Constructor

   public FoodHolder meal() {
      return meal;
   }//End Method

   public Goal goal() {
      return goal;
   }//End Method

}//End Class
