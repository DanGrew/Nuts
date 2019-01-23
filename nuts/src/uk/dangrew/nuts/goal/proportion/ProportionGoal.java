package uk.dangrew.nuts.goal.proportion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import uk.dangrew.kode.concept.Properties;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.goal.GoalTypes;
import uk.dangrew.nuts.nutrients.Nutrition;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class ProportionGoal implements Goal {

   private static final Collection< NutritionalUnit > weightedGoalUnits;
   
   static {
      weightedGoalUnits = new ArrayList<>( Arrays.asList( NutritionalUnit.values() ) );
      weightedGoalUnits.remove( NutritionalUnit.Carbohydrate );
      weightedGoalUnits.remove( NutritionalUnit.Fat );
      weightedGoalUnits.remove( NutritionalUnit.Protein );
      weightedGoalUnits.remove( NutritionalUnit.Fibre );
   }
   
   private final Properties properties;
   private final Nutrition nutrition;
   private final FoodAnalytics analytics;
   private final ProportionConfiguration configuration;
   
   public ProportionGoal( String name ) {
      this( 
               new Properties( name ), 
               new Nutrition(),
               new FoodAnalytics() 
      );
   }//End Constructor
   
   public ProportionGoal( String id, String name ) {
      this( 
               new Properties( id, name ), 
               new Nutrition(),
               new FoodAnalytics() 
      );
   }//End Constructor
   
   ProportionGoal( 
            Properties properties, 
            Nutrition nutrition,
            FoodAnalytics analytics 
   ) {
      this.properties = properties;
      this.nutrition = nutrition;
      this.analytics = analytics;
      this.configuration = new ProportionConfiguration();
   }//End Constructor
   
   public static Collection< NutritionalUnit > weightedGoalUnits(){
      return weightedGoalUnits;
   }//End Method

   public ProportionConfiguration configuration(){
      return configuration;
   }//End Method
   
   @Override public Properties properties() {
      return properties;
   }//End Method
   
   @Override public Nutrition nutrition() {
      return nutrition;
   }//End Method
   
   @Override public FoodAnalytics foodAnalytics() {
      return analytics;
   }//End Method
   
   @Override public GoalTypes type() {
      return GoalTypes.Proportion;
   }//End Method
   
   @Override public Food duplicate() {
      return this;
   }//End Method

}//End Class
