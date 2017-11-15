package uk.dangrew.nuts.day;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.template.Template;

public class DayPlanOperationsTest {

   private Food food1;
   private Food food2;
   private Food food3;
   
   private Template template;
   private DayPlan dayPlan;
   private DayPlanOperations systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      food1 = new FoodItem( "Food1" );
      food2 = new FoodItem( "Food2" );
      food3 = new FoodItem( "Food3" );
      template = new Template( "Template" );
      dayPlan = new DayPlan( LocalDate.now() );
      systemUnderTest = new DayPlanOperations();
   }//End Method
   
   @Test public void shouldCopyAllPortionsFromTemplateAndNotDuplicate() {
      dayPlan.portions().add( mock( FoodPortion.class ) );
      
      template.portions().add( new FoodPortion( food1, 100 ) );
      template.portions().add( new FoodPortion( food2, 125 ) );
      template.portions().add( new FoodPortion( food3, 40 ) );
      
      systemUnderTest.applyTemplate( dayPlan, template );
      assertThat( dayPlan.portions(), hasSize( template.portions().size() ) );
      
      for( int i = 0; i < dayPlan.portions().size(); i++ ) {
         FoodPortion copied = dayPlan.portions().get( i );
         FoodPortion expected = template.portions().get( i );
         assertFalse( copied == expected );
         
         assertTrue( expected.food().get() == copied.food().get() );
         assertThat( expected.portion().get(), is( copied.portion().get() ) );
      }
   }//End Method

   @Test public void shouldCopyAllPortionsFromTemplate() {
      dayPlan.portions().add( mock( FoodPortion.class ) );
      
      template.portions().add( new FoodPortion( food1, 100 ) );
      template.portions().add( new FoodPortion( food2, 125 ) );
      template.portions().add( new FoodPortion( food3, 40 ) );
      
      systemUnderTest.applyTemplateAndDuplicate( dayPlan, template );
      assertThat( dayPlan.portions(), hasSize( template.portions().size() ) );
      
      for( int i = 0; i < dayPlan.portions().size(); i++ ) {
         FoodPortion copied = dayPlan.portions().get( i );
         FoodPortion expected = template.portions().get( i );
         assertFalse( copied == expected );
         
         assertThat( expected.food().get(), is( copied.food().get() ) );
         assertThat( expected.portion().get(), is( copied.portion().get() ) );
      }
   }//End Method
   
   @Test public void shouldPerformDeepCopyDuplicatingMealsAndSubMeals(){
      template.portions().add( new FoodPortion( new Meal( "Meal1" ), 100 ) );
      
      systemUnderTest.applyTemplateAndDuplicate( dayPlan, template );
      assertFalse( dayPlan.portions().get( 0 ).food().get() == template.portions().get( 0 ).food().get() );
   }//End Method
   
   @Test public void shouldUseDayPlanAsReference(){
      template.portions().add( new FoodPortion( new Meal( "Meal1" ), 100 ) );
      
      systemUnderTest.applyTemplateAndDuplicate( dayPlan, template );
      assertThat( dayPlan.portions().get( 0 ).food().get().properties().nameProperty().get().contains( dayPlan.properties().nameProperty().get() ), is( true ) );
   }//End Method

}//End Class
