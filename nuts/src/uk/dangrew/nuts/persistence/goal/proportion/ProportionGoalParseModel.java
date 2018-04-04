/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.goal.proportion;

import uk.dangrew.nuts.goal.proportion.ProportionGoal;
import uk.dangrew.nuts.goal.proportion.ProportionGoalStore;
import uk.dangrew.nuts.goal.proportion.ProportionType;

class ProportionGoalParseModel {
   
   private final ProportionGoalStore goals;
   
   private String id;
   private String name;
   private ProportionType carbohydrateProportionType;
   private ProportionType fatProportionType;
   private ProportionType proteinProportionType;
   private Double carbohydrateProportionValue;
   private Double fatProportionValue;
   private Double proteinProportionValue;
   
   ProportionGoalParseModel( ProportionGoalStore goals ) {
      this.goals = goals;
   }//End Constructor
   
   /**
    * Triggered when starting a new {@link Goal}.
    * @param key the parsed key.
    */
   void startGoal( String key ) {
      id = null;
      name = null;
      carbohydrateProportionType = null;
      carbohydrateProportionValue = null;
      fatProportionType = null;
      fatProportionValue = null;
      proteinProportionType = null;
      proteinProportionValue = null;
   }//End Method
   
   /**
    * Triggered when all values of a {@link Goal} have been parsed.
    * @param key the parsed key.
    */
   void finishGoal( String key ) {
      ProportionGoal goal = goals.get( id );
      if ( goal == null ) {
         goal = goals.createConcept( id, name );
      }
      
      setGoalProperties( goal );
   }//End Method
   
   /**
    * Setter for the properties of a {@link Goal} from the parsed values.
    * @param goal the {@link Goal} to set on.
    */
   private void setGoalProperties( ProportionGoal goal ) {
      goal.properties().nameProperty().set( name );
      goal.configuration().carbohydrateProportionType().set( carbohydrateProportionType );
      goal.configuration().fatProportionType().set( fatProportionType );
      goal.configuration().proteinProportionType().set( proteinProportionType );
      goal.configuration().carbohydrateTargetValue().set( carbohydrateProportionValue );
      goal.configuration().fatTargetValue().set( fatProportionValue );
      goal.configuration().proteinTargetValue().set( proteinProportionValue );
   }//End Method

   void setId( String value ) {
      this.id = value;
   }//End Method
   
   void setName( String value ) {
      this.name = value;
   }//End Method
   
   void setCarbohydrateProportionType( ProportionType value ) {
      this.carbohydrateProportionType = value;
   }//End Method
   
   void setCarbohydrateProportionValue( Double value ) {
      this.carbohydrateProportionValue = value;
   }//End Method
   
   void setFatProportionType( ProportionType value ) {
      this.fatProportionType = value;
   }//End Method
   
   void setFatProportionValue( Double value ) {
      this.fatProportionValue = value;
   }//End Method
   
   void setProteinProportionType( ProportionType value ) {
      this.proteinProportionType = value;
   }//End Method
   
   void setProteinProportionValue( Double value ) {
      this.proteinProportionValue = value;
   }//End Method
   
}//End Class
