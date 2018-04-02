package uk.dangrew.nuts.goal.proportion;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.FoodProperties;

public class ProportionGoal implements Food {

   private final FoodProperties properties;
   private final FoodAnalytics analytics;
   private final ProportionConfiguration configuration;
   
   public ProportionGoal( String name ) {
      this( 
               new FoodProperties( name ), 
               new FoodAnalytics() 
      );
   }//End Constructor
   
   public ProportionGoal( String id, String name ) {
      this( 
               new FoodProperties( id, name ), 
               new FoodAnalytics() 
      );
   }//End Constructor
   
   ProportionGoal( 
            FoodProperties properties, 
            FoodAnalytics analytics 
   ) {
      this.properties = properties;
      this.analytics = analytics;
      this.configuration = new ProportionConfiguration();
   }//End Constructor

   public ProportionConfiguration configuration(){
      return configuration;
   }//End Method
   
   @Override public FoodProperties properties() {
      return properties;
   }//End Method
   
   @Override public FoodAnalytics foodAnalytics() {
      return analytics;
   }//End Method
   
   @Override public Food duplicate( String referenceId ) {
      return null;
   }//End Method

}//End Class
