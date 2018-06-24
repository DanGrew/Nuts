package uk.dangrew.nuts.meal;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.goal.calorie.CalorieGoal;
import uk.dangrew.nuts.goal.calorie.CalorieGoalImpl;
import uk.dangrew.nuts.goal.calorie.Gender;
import uk.dangrew.nuts.manual.data.DataLocation;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

public class ShoppingPlanInteractionTest {

   @Test public void shouldNotChangeMealStatsWithinShopppingListWhenListPortionChanges() {
      Database database = new Database();
      CalorieGoal defaultGoal = new CalorieGoalImpl( "Anything" );
      database.templates().setDefaultGoal( defaultGoal );
      
      DataLocation.loadSampleFoodData( database );
      
      defaultGoal.gender().set( Gender.Male );
      defaultGoal.age().set( 28.0 );
      defaultGoal.weight().set( 197.0 );
      defaultGoal.height().set( 1.87 );
      
      Template plan = database.templates().objectList().get( 0 );
      assertThat( plan.goalAnalytics().of( NutritionalUnit.Protein ).get(), is( lessThan( 200.0 ) ) );
      
      Meal shoppingList = new Meal( "Shopping" );
      database.shoppingLists().store( shoppingList );
      shoppingList.portions().add( new FoodPortion( plan, 100 ) );
      
      assertThat( plan.goalAnalytics().of( NutritionalUnit.Protein ).get(), is( lessThan( 200.0 ) ) );
      
      shoppingList.portions().get( 0 ).setPortion( 200.0 );
      assertThat( plan.goalAnalytics().of( NutritionalUnit.Protein ).get(), is( lessThan( 200.0 ) ) );
   }//End Method

}//End Class
