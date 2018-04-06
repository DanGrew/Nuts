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
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.goal.calorie.CalorieGoal;
import uk.dangrew.nuts.goal.proportion.ProportionGoal;

/**
 * {@link GoalCalculationView} provides a pane of settable, and autocalculating, properties to configure
 * for setting the user's {@link Goal}.
 */
public class GoalCalculationView extends TitledPane {
   
   private final CalorieGoalView calorieView;
   private final ProportionGoalView proportionView;
   
   public GoalCalculationView() {
      this( new CalorieGoalView(), new ProportionGoalView() );
   }//End Constructor
   
   GoalCalculationView( CalorieGoalView calorieView, ProportionGoalView proportionView ) {
      super.setText( "Selected Goal" );
      this.calorieView = calorieView;
      this.proportionView = proportionView;
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
