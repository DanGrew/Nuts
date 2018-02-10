package uk.dangrew.nuts.day;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
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
import uk.dangrew.nuts.template.TemplateStore;

public class DayPlanOperationsTest {

   private static final int OTHER_DAY_OFFSET = 13;
   
   private Food food1;
   private Food food2;
   private Food food3;
   
   private Template template;
   private DayPlan dayPlan;
   private DayPlan otherDay;
   
   private TemplateStore templates;
   private DayPlanStore dayPlans;
   private DayPlanOperations systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      food1 = new FoodItem( "Food1" );
      food2 = new FoodItem( "Food2" );
      food3 = new FoodItem( "Food3" );
      
      templates = new TemplateStore();
      dayPlans = new DayPlanStore();
      
      template = new Template( "Template" );
      dayPlan = new DayPlan( LocalDate.now() );
      dayPlans.store( otherDay = new DayPlan( LocalDate.now().plusDays( OTHER_DAY_OFFSET ) ) );
      
      systemUnderTest = new DayPlanOperations( templates, dayPlans );
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
   
   @Test public void shouldAddFromTemplate(){
      dayPlan.portions().add( mock( FoodPortion.class ) );
      
      template.portions().add( new FoodPortion( food1, 100 ) );
      template.portions().add( new FoodPortion( food2, 125 ) );
      template.portions().add( new FoodPortion( food3, 40 ) );
      
      systemUnderTest.addFromTemplate( dayPlan, template );
      assertThat( dayPlan.portions(), hasSize( template.portions().size() + 1 ) );
      
      for( int i = 0; i < template.portions().size(); i++ ) {
         FoodPortion copied = dayPlan.portions().get( i + 1 );
         FoodPortion expected = template.portions().get( i );
         assertFalse( copied == expected );
         
         assertTrue( expected.food().get() == copied.food().get() );
         assertThat( expected.portion().get(), is( copied.portion().get() ) );
      }
   }//End Method
   
   @Test public void shouldClearDay(){
      dayPlan.portions().add( mock( FoodPortion.class ) );
      systemUnderTest.clearDayPlan( dayPlan );
      assertThat( dayPlan.portions(), is( empty() ) );
   }//End Method
   
   @Test public void shouldSaveAsTemplate(){
      dayPlan.portions().add( mock( FoodPortion.class ) );
      dayPlan.portions().add( mock( FoodPortion.class ) );
      dayPlan.portions().add( mock( FoodPortion.class ) );
      
      systemUnderTest.saveAsTemplate( "Name", dayPlan );
      
      assertThat( templates.objectList(), hasSize( 1 ) );
      Template template = templates.objectList().get( 0 );
      assertThat( template.properties().nameProperty().get(), is( "Name" ) );
      assertThat( template.portions(), hasSize( dayPlan.portions().size() ) );
      for ( int i = 0; i < dayPlan.portions().size(); i++ ) {
         assertTrue( template.portions().get( i ) == dayPlan.portions().get( i ) );
      }
   }//End Method
   
   @Test public void shouldCopyToOtherDay(){
      dayPlan.portions().add( new FoodPortion( food1, 100 ) );
      dayPlan.portions().add( new FoodPortion( food2, 125 ) );
      dayPlan.portions().add( new FoodPortion( food3, 40 ) );
      
      otherDay.portions().add( new FoodPortion() );
      
      systemUnderTest.copyToDay( dayPlan, LocalDate.now().plusDays( OTHER_DAY_OFFSET ) );

      assertThat( otherDay.portions(), hasSize( dayPlan.portions().size() + 1 ) );
      
      for( int i = 0; i < dayPlan.portions().size(); i++ ) {
         FoodPortion copied = dayPlan.portions().get( i );
         FoodPortion expected = otherDay.portions().get( i + 1 );
         assertFalse( copied == expected );
         
         assertTrue( expected.food().get() == copied.food().get() );
         assertThat( expected.portion().get(), is( copied.portion().get() ) );
      }
   }//End Method
   
   @Test public void shouldIgnoreCopyToOtherDayIfItDoesntExist(){
      dayPlan.portions().add( new FoodPortion( food1, 100 ) );
      dayPlan.portions().add( new FoodPortion( food2, 125 ) );
      dayPlan.portions().add( new FoodPortion( food3, 40 ) );
      
      otherDay.portions().add( mock( FoodPortion.class ) );
      
      systemUnderTest.copyToDay( dayPlan, LocalDate.now().plusDays( 20 ) );

      assertThat( otherDay.portions(), hasSize( 1 ) );
   }//End Method
   
}//End Class
