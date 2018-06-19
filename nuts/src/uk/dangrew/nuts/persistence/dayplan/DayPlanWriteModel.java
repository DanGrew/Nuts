/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.dayplan;

import java.util.stream.Collectors;

import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.day.DayPlanStore;
import uk.dangrew.nuts.persistence.template.TemplateWriteModel;

/**
 * {@link DayPlanWriteModel} is responsible for handling the hooks for the {@link uk.dangrew.jupa.json.parse.JsonParser}
 * when writing {@link DayPlan}s.
 */
class DayPlanWriteModel extends TemplateWriteModel< DayPlan > {
   
   /**
    * Constructs a new {@link DayPlanWriteModel}.
    * @param dayPlans the {@link FoodStore} providing the {@link DayPlan}s.
    */
   DayPlanWriteModel( DayPlanStore dayPlans ) {
      super( dayPlans );
   }//End Constructor
   
   @Override protected Integer getNumberOfMeals( String key ) {
      startWritingMeals( key );
      return buffer().size();
   }//End Method
   
   @Override protected void startWritingMeals( String key ) {
      buffer().clear();
      buffer().addAll( meals().objectList().stream()
               .filter( d -> d.date() != null )
               .collect( Collectors.toList() ) 
      );
      buffer().sort( ( a, b ) -> a.date().compareTo( b.date() ) );
   }//End Method
   
   String getDateString( String key ) {
      if ( currentFood().date() == null ) {
         return null;
      }
      return currentFood().date().toString();
   }//End Method
   
   Double getConsumedCalories( String key ) {
      return currentFood().consumedCalories().get();
   }//End Method
   
   Double getAllowedCalories( String key ) {
      return currentFood().allowedCalories().get();
   }//End Method
   
   Double getCalorieBalance( String key ) {
      return currentFood().calorieBalance().get();
   }//End Method
   
   Boolean isBalanceReset( String key ) {
      return currentFood().isBalanceReset().get();
   }//End Method
   
   Boolean isConsumed( String key ) {
      return currentFood().consumed().contains( currentFoodPortion() );
   }//End Method

}//End Class
