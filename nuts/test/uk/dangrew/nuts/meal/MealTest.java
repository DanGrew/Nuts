package uk.dangrew.nuts.meal;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.MacroRatioCalculator;

public class MealTest {

   @Mock private MealChangeListener listener;
   
   private FoodItem food1;
   private FoodItem food2;
   private FoodPortion portion1;
   private FoodPortion portion2;
   
   private FoodProperties properties;
   private FoodAnalytics foodAnalytics;
   @Spy private MacroRatioCalculator ratioCalculator;
   @Mock private MealRegistrations registrations;
   @Mock private MealPropertiesCalculator propertiesCalculator;
   @Spy private StockUsage stockUsage;
   private Meal systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      
      food1 = new FoodItem( "Food1" );
      food2 = new FoodItem( "Food2" );
      portion1 = new FoodPortion();
      portion2 = new FoodPortion();
      
      properties = new FoodProperties( "anything" );
      foodAnalytics = new FoodAnalytics();
      systemUnderTest = new Meal(
               properties, 
               foodAnalytics, 
               registrations, 
               propertiesCalculator, 
               ratioCalculator,
               stockUsage
      );
   }//End Method
   
   @Test public void shouldUseName(){
      systemUnderTest = new Meal( "Name" );
      assertThat( systemUnderTest.properties().nameProperty().get(), is( "Name" ) );
   }//End Method

   @Test public void shouldProvideFoodPortions() {
      assertThat( systemUnderTest.portions(), is( empty() ) );
      systemUnderTest.portions().add( portion1 );
      assertThat( systemUnderTest.portions(), contains( portion1 ) );
   }//End Method
   
   @Test public void shouldProvideRegistrations(){
      assertThat( systemUnderTest.registrations(), is( registrations ) );
   }//End Method
   
   @Test public void shouldAssoiateWithRegistrations(){
      verify( registrations ).associate( systemUnderTest );
   }//End Method
   
   @Test public void shouldAssoiateWithCalculator(){
      verify( propertiesCalculator ).associate( systemUnderTest );
   }//End Method
   
   @Test public void shouldProvideProperties(){
      assertThat( systemUnderTest.properties(), is( properties ) );
   }//End Method
   
   @Test public void shouldProvideFoodAnalytics(){
      assertThat( systemUnderTest.foodAnalytics(), is( foodAnalytics ) );
   }//End Method
   
   @Test public void shouldAssociateRatioCalculator(){
      verify( ratioCalculator ).associate( properties, foodAnalytics );
   }//End Method
   
   @Test public void shouldProvideStockUsage(){
      assertThat( systemUnderTest.stockUsage(), is( stockUsage ) );
   }//End Method
   
   @Test public void shouldAsscoiateStockUsage(){
      verify( stockUsage ).associate( systemUnderTest.portions() );
   }//End Method

}//End Class
