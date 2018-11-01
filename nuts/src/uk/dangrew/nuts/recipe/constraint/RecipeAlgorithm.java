package uk.dangrew.nuts.recipe.constraint;

import java.util.Optional;

import org.apache.commons.math3.optim.PointValuePair;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.Meal;

public class RecipeAlgorithm {
   
   private final RecipeConfiguration configuration;
   
   public RecipeAlgorithm( RecipeConfiguration configuration ) {
      this.configuration = configuration;
   }//End Constructor
   
   public void solve(){
      Optional< PointValuePair > solution = configuration.function().get().solve( 
               configuration.ingredients(), configuration.generateConstraints() 
      );
      if ( !solution.isPresent() ) {
         configuration.solution().set( null );
         return;
      }
      
      Meal result = new Meal( "Result" );
      for ( int i = 0; i < configuration.ingredients().size(); i++ ) {
         Food food = configuration.ingredients().get( i );
         double portion = solution.get().getPoint()[ i ];
         result.portions().add( new FoodPortion( food, portion * 100 ) );
      }
      configuration.solution().set( result );
   }//End Constructor

}//End Class
