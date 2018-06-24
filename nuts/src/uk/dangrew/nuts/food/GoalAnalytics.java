/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.food;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

/**
 * {@link GoalAnalytics} provides {@link FoodAnalytics} with an associated {@link Goal}.
 */
public class GoalAnalytics extends FoodAnalytics {

   private final ObjectProperty< Goal > goal;
   
   /**
    * Constructs a new {@link GoalAnalytics}.
    */
   public GoalAnalytics() {
      this.goal = new SimpleObjectProperty<>();
   }//End Constructor
   
   /**
    * Access to the {@link Goal} applied.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Goal > goal() {
      return goal;
   }//End Method
   
   /**
    * Access to the calorie ratio for the {@link Goal}.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > caloriesRatioProperty() {
      return nutrition().of( NutritionalUnit.Calories );
   }//End Method
   
   /**
    * Access to the calorie ratio for the {@link Goal}, value.
    * @return the {@link ObjectProperty}.
    */
   public Double caloriesRatio() {
      return caloriesRatioProperty().get();
   }//End Method
   
}//End Class
