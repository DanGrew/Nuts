package uk.dangrew.nuts.graphics.day;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.food.FoodPortion;

public class ConsumptionPropertiesTest {

   private DayPlan dayPlan;
   private DayPlan dayPlan2;
   private ConsumptionProperties systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      dayPlan = new DayPlan( LocalDate.now() );
      dayPlan.portions().add( mock( FoodPortion.class ) );
      dayPlan.portions().add( mock( FoodPortion.class ) );
      dayPlan.portions().add( mock( FoodPortion.class ) );
      
      dayPlan2 = new DayPlan( LocalDate.now() );
      dayPlan2.portions().add( mock( FoodPortion.class ) );
      
      systemUnderTest = new ConsumptionProperties();
   }//End Method

   @Test public void shouldProvidePropertiesForPortionsInPlan() {
      assertThat( systemUnderTest.propertyFor( dayPlan.portions().get( 0 ) ), is( nullValue() ) );
      systemUnderTest.setDayPlan( dayPlan );
      
      for ( FoodPortion portion : dayPlan.portions() ) {
         assertThat( systemUnderTest.propertyFor( portion ).get(), is( false ) );
      }
      
      dayPlan.consumed().add( dayPlan.portions().get( 1 ) );
      assertThat( systemUnderTest.propertyFor( dayPlan.portions().get( 0 ) ).get(), is( false ) );
      assertThat( systemUnderTest.propertyFor( dayPlan.portions().get( 1 ) ).get(), is( true ) );
      assertThat( systemUnderTest.propertyFor( dayPlan.portions().get( 2 ) ).get(), is( false ) );
      
      dayPlan.consumed().remove( dayPlan.portions().get( 1 ) );
      assertThat( systemUnderTest.propertyFor( dayPlan.portions().get( 0 ) ).get(), is( false ) );
      assertThat( systemUnderTest.propertyFor( dayPlan.portions().get( 1 ) ).get(), is( false ) );
      assertThat( systemUnderTest.propertyFor( dayPlan.portions().get( 2 ) ).get(), is( false ) );
   }//End Method
   
   @Test public void shouldClearPropertiesForPortionsWhenChanged() {
      systemUnderTest.setDayPlan( dayPlan );
      for ( FoodPortion portion : dayPlan.portions() ) {
         assertThat( systemUnderTest.propertyFor( portion ).get(), is( false ) );
      }
      
      systemUnderTest.setDayPlan( dayPlan2 );
      for ( FoodPortion portion : dayPlan.portions() ) {
         assertThat( systemUnderTest.propertyFor( portion ), is( nullValue() ) );
      }
      for ( FoodPortion portion : dayPlan2.portions() ) {
         assertThat( systemUnderTest.propertyFor( portion ).get(), is( false ) );
      }
   }//End Method
   
   @Test public void shouldUpdateConsumedWhenPropertyChanged(){
      systemUnderTest.setDayPlan( dayPlan );
      assertThat( dayPlan.consumed(), is( empty() ) );
      
      systemUnderTest.propertyFor( dayPlan.portions().get( 1 ) ).set( true );;
      assertThat( dayPlan.consumed(), contains( dayPlan.portions().get( 1 ) ) );
   }//End Method
   
}//End Class
