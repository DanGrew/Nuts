/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.goal;

import javafx.scene.control.TitledPane;
import uk.dangrew.nuts.goal.CalorieGoal;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.goal.proportion.ProportionGoal;

/**
 * {@link GoalCalculationView} provides a pane of settable, and autocalculating, properties to configure
 * for setting the user's {@link Goal}.
 */
public class GoalCalculationView extends TitledPane {
   
   private final CalorieGoalView calorieView;
   private final ProportionGoalView proportionView;
   
   /**
    * Constructs a new {@link GoalCalculationView}.
    */
   public GoalCalculationView() {
      super.setText( "Selected Goal" );
      this.calorieView = new CalorieGoalView();
      this.proportionView = new ProportionGoalView();
   }//End Constructor
   
   /**
    * Method to show the {@link Goal} in the view.
    * This will decouple the view from the previous.
    * @param goal the {@link Goal} to show.
    */
   public void show( Goal goal ) {
      calorieView.detach();
      proportionView.detach();
      switch ( goal.type() ) {
         case Calorie:
            setContent( calorieView );
            calorieView.show( ( CalorieGoal )goal );
            break;
         case Proportion:
            setContent( proportionView );
            proportionView.show( ( ProportionGoal )goal );
            break;
      }
      
   }//End Method 
   
}//End Class
