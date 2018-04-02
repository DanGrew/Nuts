package uk.dangrew.nuts.goal.proportion;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.GoalAnalytics;
import uk.dangrew.nuts.nutrients.MacroNutrient;

public class ProportionGoalCalculator {

   
   private final ChangeListener< Double > updater;
   
   private FoodProperties properties;
   private GoalAnalytics analytics;
   
   private ProportionGoal proportionGoal;
   
   public ProportionGoalCalculator() {
      this.updater = ( s, o, n ) -> updateRatios();
   }//End Constructor
   
   public void associate( FoodProperties properties, GoalAnalytics analytics ) {
      if ( this.properties != null || this.analytics != null ) {
         throw new IllegalStateException( "Already associated." );
      }
      
      this.properties = properties;
      this.properties.carbohydrates().addListener( updater );
      this.properties.fats().addListener( updater );
      this.properties.protein().addListener( updater );
      this.properties.calories().addListener( updater );
      this.analytics = analytics;
      this.analytics.proportionGoal().addListener( ( s, o, n ) -> setGoal( n ) );
   }//End Method
   
   private void setGoal( ProportionGoal goal ) {
      if ( this.proportionGoal != null ) {
         for ( MacroNutrient macro : MacroNutrient.values() ) {
            this.proportionGoal.properties().nutritionFor( macro ).removeListener( updater );
         }
         this.proportionGoal.properties().calories().removeListener( updater );
      }
      
      this.proportionGoal = goal;
      if ( this.proportionGoal == null ) {
         for ( MacroNutrient macro : MacroNutrient.values() ) {
            analytics.nutrientRatioFor( macro ).set( 0.0 );
         }
         return;
      } else {
         for ( MacroNutrient macro : MacroNutrient.values() ) {
            properties.nutritionFor( macro ).addListener( updater );
            this.proportionGoal.properties().nutritionFor( macro ).addListener( updater );
         }
         this.proportionGoal.properties().calories().addListener( updater );
         updateRatios();
      }
   }//End Method
   
   private void updateRatios(){
      if ( this.proportionGoal == null ) {
         return;
      }
      
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
               proportionGoal.configuration().fatsProportionType(), 
               proportionGoal.configuration().fatsTargetValue(), 
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
