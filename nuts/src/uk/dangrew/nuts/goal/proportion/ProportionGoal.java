package uk.dangrew.nuts.goal.proportion;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.goal.GoalTypes;
import uk.dangrew.nuts.nutrients.Nutrition;
import uk.dangrew.nuts.system.Properties;

public class ProportionGoal implements Goal {

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
   
   @Override public Food duplicate( String referenceId ) {
      return this;
   }//End Method

}//End Class
