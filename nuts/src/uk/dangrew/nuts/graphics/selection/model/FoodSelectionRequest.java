package uk.dangrew.nuts.graphics.selection.model;

import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.template.Template;

public class FoodSelectionRequest {
   
   private final Meal meal;
   private final Goal goal;
   
   public FoodSelectionRequest( Template template ) {
      this( template, template.goalAnalytics().goal().get() );
   }//End Constructor
   
   public FoodSelectionRequest( Meal meal ) {
      this( meal, null );
   }//End Constructor

   public FoodSelectionRequest( Meal meal, Goal goal ) {
      this.meal = meal;
      this.goal = goal;
   }//End Constructor

   public Meal meal() {
      return meal;
   }//End Method

   public Goal goal() {
      return goal;
   }//End Method

}//End Class
