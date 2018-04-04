/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.goal.proportion;

import java.util.ArrayList;
import java.util.List;

import uk.dangrew.nuts.goal.proportion.ProportionGoal;
import uk.dangrew.nuts.goal.proportion.ProportionGoalStore;
import uk.dangrew.nuts.goal.proportion.ProportionType;

class ProportionGoalWriteModel {
   
   private final ProportionGoalStore goals;
   private final List< ProportionGoal > buffer;
   private ProportionGoal current; 
   
   ProportionGoalWriteModel( ProportionGoalStore goals ) {
      this.goals = goals;
      this.buffer = new ArrayList<>();
   }//End Constructor
   
   Integer getNumberOfGoals( String key ){
      return goals.objectList().size();
   }//End Method
   
   void startWritingGoals() {
      buffer.clear();
      buffer.addAll( goals.objectList() );
   }//End Method
   
   void startWritingGoal() {
      if ( buffer.isEmpty() ) {
         return;
      }
      this.current = buffer.remove( 0 );
   }//End Method
   
   String getId() {
      return current.properties().id();
   }//End Method
   
   String getName() {
      return current.properties().nameProperty().get();
   }//End Method
   
   ProportionType getCarbohydrateProportionType() {
      return current.configuration().carbohydrateProportionType().get();
   }//End Method
   
   Double getCarbohydrateProportionValue() {
      return current.configuration().carbohydrateTargetValue().get();
   }//End Method
   
   ProportionType getFatProportionType() {
      return current.configuration().fatProportionType().get();
   }//End Method
   
   Double getFatProportionValue() {
      return current.configuration().fatTargetValue().get();
   }//End Method
   
   ProportionType getProteinProportionType() {
      return current.configuration().proteinProportionType().get();
   }//End Method
   
   Double getProteinProportionValue() {
      return current.configuration().proteinTargetValue().get();
   }//End Method

}//End Class
