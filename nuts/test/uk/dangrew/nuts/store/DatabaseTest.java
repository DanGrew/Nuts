package uk.dangrew.nuts.store;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.kode.model.ModelVerifier;
import uk.dangrew.kode.storage.structure.MappedObservableStoreManagerImpl;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.day.DayPlanStore;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.goal.calorie.CalorieGoal;
import uk.dangrew.nuts.goal.calorie.CalorieGoalImpl;
import uk.dangrew.nuts.goal.calorie.CalorieGoalStore;
import uk.dangrew.nuts.goal.proportion.ProportionGoal;
import uk.dangrew.nuts.goal.proportion.ProportionGoalStore;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.meal.MealStore;
import uk.dangrew.nuts.template.Template;
import uk.dangrew.nuts.template.TemplateStore;

public class DatabaseTest {

   private Database systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new Database();
   }//End Method

   @Test public void shouldProvideMappedFoods() {
      assertThat( systemUnderTest.foodItems(), is( instanceOf( MappedObservableStoreManagerImpl.class ) ) );
      
      FoodItem foodItem = new FoodItem( "Anything" );
      systemUnderTest.foodItems().store( foodItem );
      assertThat( systemUnderTest.foodItems().get( foodItem.properties().id() ), is( foodItem ) );
   }//End Method
   
   @Test public void shouldProvideMappedMeals() {
      assertThat( systemUnderTest.meals(), is( instanceOf( MappedObservableStoreManagerImpl.class ) ) );
      
      Meal meal = new Meal( "Meal" );
      systemUnderTest.meals().store( meal );
      assertThat( systemUnderTest.meals().get( meal.properties().id() ), is( meal ) );
   }//End Method
   
   @Test public void shouldProvideWeightProgress(){
      assertThat( systemUnderTest.weightProgress(), is( not( nullValue() ) ) );
      assertThat( systemUnderTest.weightProgress(), is( systemUnderTest.weightProgress() ) );
   }//End Method
   
   @Test public void shouldProvidePlans(){
      assertThat( systemUnderTest.templates(), is( instanceOf( TemplateStore.class ) ) );
      
      Template template = new Template( "Template" );
      systemUnderTest.templates().store( template );
      assertThat( systemUnderTest.templates().get( template.properties().id() ), is( template ) );
   }//End Method
   
   @Test public void shouldProvideShoppingLists(){
      assertThat( systemUnderTest.shoppingLists(), is( instanceOf( MealStore.class ) ) );
      
      Meal list = new Meal( "List" );
      systemUnderTest.shoppingLists().store( list );
      assertThat( systemUnderTest.shoppingLists().get( list.properties().id() ), is( list ) );
   }//End Method
   
   @Test public void shouldProvideCalorieGoals(){
      assertThat( systemUnderTest.calorieGoals(), is( instanceOf( CalorieGoalStore.class ) ) );
      
      CalorieGoal calorieGoal = new CalorieGoalImpl( "List" );
      systemUnderTest.calorieGoals().store( calorieGoal );
      assertThat( systemUnderTest.calorieGoals().get( calorieGoal.properties().id() ), is( calorieGoal ) );
   }//End Method
   
   @Test public void shouldProvideProportionGoals(){
      assertThat( systemUnderTest.proportionGoals(), is( instanceOf( ProportionGoalStore.class ) ) );
      
      ProportionGoal goal = new ProportionGoal( "List" );
      systemUnderTest.proportionGoals().store( goal );
      assertThat( systemUnderTest.proportionGoals().get( goal.properties().id() ), is( goal ) );
   }//End Method
   
   @Test public void shouldProvideDayPlans(){
      assertThat( systemUnderTest.dayPlans(), is( instanceOf( DayPlanStore.class ) ) );
      
      DayPlan plan = new DayPlan( LocalDate.now().plusDays( 1000 ) );
      systemUnderTest.dayPlans().store( plan );
      assertThat( systemUnderTest.dayPlans().get( plan.properties().id() ), is( plan ) );
   }//End Method
   
   @Test public void shouldProvideStockList(){
      assertThat( systemUnderTest.stockLists(), is( notNullValue() ) );
      assertThat( systemUnderTest.stockLists(), is( systemUnderTest.stockLists() ) );
   }//End Method
   
   @Test public void shouldProvideLabels(){
      assertThat( systemUnderTest.labels(), is( notNullValue() ) );
      assertThat( systemUnderTest.labels(), is( systemUnderTest.labels() ) );
   }//End Method
   
   @Test public void shouldProvideConcepts(){
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideObject( Database::foodItems )
         .shouldProvideObject( Database::meals )
         .shouldProvideObject( Database::templates )
         .shouldProvideObject( Database::shoppingLists )
         .shouldProvideObject( Database::labels )
         .shouldProvideObject( Database::stockLists )
         .shouldProvideObject( Database::dayPlans )
         .shouldProvideObject( Database::calorieGoals )
         .shouldProvideObject( Database::proportionGoals )
         .shouldProvideObject( Database::progressSeries )
         .shouldProvideObject( Database::researchArticles )
         .shouldProvideObject( Database::resolver )
         ;
   }//End Method

}//End Class
