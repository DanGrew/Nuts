package uk.dangrew.nuts.goal.proportion;

import javafx.beans.property.ObjectProperty;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.GoalAnalytics;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.goal.GoalCalculator;
import uk.dangrew.nuts.goal.GoalTypes;

public class ProportionGoalCalculator implements GoalCalculator {

   private FoodProperties properties;
   private GoalAnalytics analytics;
   private ProportionGoal proportionGoal;
   
   @Override public void calculate( 
            FoodProperties properties, 
            GoalAnalytics analytics, 
            Goal goal 
   ) {
      if ( goal.type() != GoalTypes.Proportion ) {
         return;
      }
      this.properties = properties;
      this.analytics = analytics;
      this.proportionGoal = ( ProportionGoal ) goal;
      calculate();
   }//End Method
   
   private void calculate(){
      double carbWeight = properties.carbohydrates().get();
      double fatWeight = properties.fats().get();
      double proteinWeight = properties.protein().get();
      
      double carbCalories = carbWeight * 4;
      double fatCalories = fatWeight * 9;
      double proteinCalories = proteinWeight * 4;
      
      double totalCalories = carbCalories + fatCalories + proteinCalories;
      double totalWeight = carbWeight + fatWeight + proteinWeight;
      
      
      calculateAndSet( 
               proportionGoal.configuration().carbohydrateProportionType(), 
               proportionGoal.configuration().carbohydrateTargetValue(), 
               totalCalories, 
               totalWeight, 
               carbCalories, 
               carbWeight, 
               analytics.carbohydratesRatioProperty() 
      );
      calculateAndSet( 
               proportionGoal.configuration().fatProportionType(), 
               proportionGoal.configuration().fatTargetValue(), 
               totalCalories, 
               totalWeight, 
               fatCalories, 
               fatWeight, 
               analytics.fatsRatioProperty() 
      );
      calculateAndSet( 
               proportionGoal.configuration().proteinProportionType(), 
               proportionGoal.configuration().proteinTargetValue(), 
               totalCalories, 
               totalWeight, 
               proteinCalories, 
               proteinWeight, 
               analytics.proteinRatioProperty() 
      );
   }//End Method
   
   private void calculateAndSet(
            ObjectProperty< ProportionType > type,
            ObjectProperty< Double > value, 
            double totalCalories,
            double totalWeight,
            double macroCalories,
            double macroWeight,
            ObjectProperty< Double > toSet
   ){
      if ( type.get() == null ) {
         toSet.set( 0.0 );
         return;
      }
      double progress = type.get().function().calculateProgress( 
            totalCalories, 
            totalWeight, 
            macroCalories, 
            macroWeight, 
            value.get() 
      );
      toSet.set( progress );
   }//End Method
   
}//End Class
