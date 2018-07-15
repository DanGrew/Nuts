package uk.dangrew.nuts.recipe.constraint;

import java.util.Optional;

import org.apache.commons.math3.optim.PointValuePair;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.Meal;

public class RecipeAlgorithm {
   
   private final RecipeConfiguration configuration;
   
   public RecipeAlgorithm( RecipeConfiguration configuration ) {
      FoodItem peanutButter = new FoodItem( "Peanut Butter" );
      peanutButter.nutrition().setMacroNutrients( 8, 55, 28 );
      FoodItem walnutButter = new FoodItem( "Walnut Butter" );
      walnutButter.nutrition().setMacroNutrients( 3.3, 68.5, 15 );
      FoodItem egg = new FoodItem( "Egg" );
      egg.nutrition().setMacroNutrients( 0.35, 6.3, 8.8 );
      FoodItem cream = new FoodItem( "Cream" );
      cream.nutrition().setMacroNutrients( 0.48, 15, 0.45 );
      
      this.configuration = configuration;
      this.configuration.ingredients().addAll( peanutButter, walnutButter, egg, cream );
   }//End Constructor
   
   public Optional< Meal > solve(){
      Optional< PointValuePair > solution = configuration.function().get().solve( 
               configuration.ingredients(), configuration.generateConstraints() 
      );
      if ( !solution.isPresent() ) {
         return Optional.empty();
      }
      
      Meal result = new Meal( "Result" );
      for ( int i = 0; i < configuration.ingredients().size(); i++ ) {
         Food food = configuration.ingredients().get( i );
         double portion = solution.get().getPoint()[ i ];
         result.portions().add( new FoodPortion( food, portion * 100 ) );
      }
      return Optional.of( result );
   }//End Constructor

}//End Class
