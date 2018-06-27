/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.goal.proportion;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import uk.dangrew.nuts.goal.proportion.ProportionGoal;
import uk.dangrew.nuts.goal.proportion.ProportionGoalStore;
import uk.dangrew.nuts.goal.proportion.ProportionType;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

class ProportionGoalParseModel {
   
   private final ProportionGoalStore goals;
   private final Map< NutritionalUnit, Double > nutritionalUnitGoals;
   
   private String id;
   private String name;
   private ProportionType carbohydrateProportionType;
   private ProportionType fatProportionType;
   private ProportionType proteinProportionType;
   private ProportionType fiberProportionType;
   private Double carbohydrateProportionValue;
   private Double fatProportionValue;
   private Double proteinProportionValue;
   private Double fiberProportionValue;
   
   ProportionGoalParseModel( ProportionGoalStore goals ) {
      this.goals = goals;
      this.nutritionalUnitGoals = new EnumMap<>( NutritionalUnit.class );
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
      fiberProportionType = null;
      fiberProportionValue = null;
      nutritionalUnitGoals.clear();
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
      goal.configuration().fiberProportionType().set( fiberProportionType );
      goal.configuration().carbohydrateTargetValue().set( carbohydrateProportionValue );
      goal.configuration().fatTargetValue().set( fatProportionValue );
      goal.configuration().proteinTargetValue().set( proteinProportionValue );
      goal.configuration().fiberTargetValue().set( fiberProportionValue );
      
      for ( NutritionalUnit unit : ProportionGoal.weightedGoalUnits() ) {
         Optional.ofNullable( nutritionalUnitGoals.get( unit ) ).ifPresent( 
                  v -> unit.of( goal ).set( v ) 
         );
      }
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
   
   void setFiberProportionType( ProportionType value ) {
      this.fiberProportionType = value;
   }//End Method
   
   void setFiberProportionValue( Double value ) {
      this.fiberProportionValue = value;
   }//End Method
   
   void setNutritionalUnitGoal( NutritionalUnit unit, Double value ) {
      this.nutritionalUnitGoals.put( unit, value );
   }//End Method
   
}//End Class
