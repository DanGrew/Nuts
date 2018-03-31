package uk.dangrew.nuts.meal;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.*;

import org.junit.Test;

import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.goal.Gender;
import uk.dangrew.nuts.goal.CalorieGoalImpl;
import uk.dangrew.nuts.manual.data.DataLocation;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

public class ShoppingPlanInteractionTest {

   @Test public void shouldNotChangeMealStatsWithinShopppingListWhenListPortionChanges() {
      Database database = new Database();
      database.templates().setDefaultGoal( new CalorieGoalImpl( "Anything" ) );
      
      DataLocation.loadSampleFoodData( database );
      
      database.templates().defaultGoal().gender().set( Gender.Male );
      database.templates().defaultGoal().age().set( 28.0 );
      database.templates().defaultGoal().weight().set( 197.0 );
      database.templates().defaultGoal().height().set( 1.87 );
      
      Template plan = database.templates().objectList().get( 0 );
      assertThat( plan.goalAnalytics().proteinRatio(), is( lessThan( 200.0 ) ) );
      
      Meal shoppingList = new Meal( "Shopping" );
      database.shoppingLists().store( shoppingList );
      shoppingList.portions().add( new FoodPortion( plan, 100 ) );
      
      assertThat( plan.goalAnalytics().proteinRatio(), is( lessThan( 200.0 ) ) );
      
      shoppingList.portions().get( 0 ).setPortion( 200.0 );
      assertThat( plan.goalAnalytics().proteinRatio(), is( lessThan( 200.0 ) ) );
   }//End Method

}//End Class
