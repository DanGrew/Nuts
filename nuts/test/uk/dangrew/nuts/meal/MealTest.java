package uk.dangrew.nuts.meal;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import uk.dangrew.kode.concept.Properties;
import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.food.MacroRatioCalculator;
import uk.dangrew.nuts.nutrients.Nutrition;

public class MealTest {

   @Mock private MealChangeListener listener;
   
   private FoodItem food1;
   private FoodItem food2;
   private FoodPortion portion1;
   private FoodPortion portion2;
   
   private Properties properties;
   private Nutrition nutrition;
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
      
      properties = new Properties( "anything" );
      nutrition = new Nutrition();
      foodAnalytics = new FoodAnalytics();
      systemUnderTest = new Meal(
               properties, 
               nutrition,
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
   
   @Test public void shouldProvideNutrition(){
      assertThat( systemUnderTest.properties(), is( properties ) );
      assertThat( systemUnderTest.nutrition(), is( nutrition ) );
   }//End Method
   
   @Test public void shouldProvideFoodAnalytics(){
      assertThat( systemUnderTest.foodAnalytics(), is( foodAnalytics ) );
   }//End Method
   
   @Test public void shouldAssociateRatioCalculator(){
      verify( ratioCalculator ).associate( nutrition, foodAnalytics );
   }//End Method
   
   @Test public void shouldProvideStockUsage(){
      assertThat( systemUnderTest.stockUsage(), is( stockUsage ) );
   }//End Method
   
   @Test public void shouldAsscoiateStockUsage(){
      verify( stockUsage ).associate( systemUnderTest.portions() );
   }//End Method
   
   @Test public void shouldPerformDeepDuplicate(){
      Meal level3Meal1 = new Meal( "l3m1" );
      level3Meal1.portions().add( new FoodPortion( food1, 31 ) );
      level3Meal1.portions().add( new FoodPortion( food2, 31.5 ) );
      Meal level3Meal2 = new Meal( "l3m2" );
      level3Meal2.portions().add( new FoodPortion( food2, 32 ) );
      
      Meal level2Meal1 = new Meal( "l2m1" );
      level2Meal1.portions().add( new FoodPortion( level3Meal1, 21 ) );
      level2Meal1.portions().add( new FoodPortion( level3Meal2, 21 ) );
      Meal level2Meal2 = new Meal( "l2m2" );
      level2Meal2.portions().add( new FoodPortion( food1, 22 ) );
      
      Meal level1Meal1 = new Meal( "l1m1" );
      level1Meal1.portions().add( new FoodPortion( level2Meal1, 11 ) );
      level1Meal1.portions().add( new FoodPortion( level2Meal2, 11 ) );
      Meal level1Meal2 = new Meal( "l1m2" );
      level1Meal2.portions().add( new FoodPortion( food1, 12 ) );
      
      systemUnderTest.portions().add( new FoodPortion( level1Meal1, 1 ) );
      systemUnderTest.portions().add( new FoodPortion( level1Meal2, 2 ) );
      
      String ref = "-ref";
      Meal duplicate = systemUnderTest.duplicate();
      assertThat( duplicate.properties().nameProperty().get(), is( systemUnderTest.properties().nameProperty().get() + ref ) );
      assertThat( duplicate.properties().id(), is( not( systemUnderTest.properties().id() ) ) );
      
      assertThat( duplicate.portions(), hasSize( systemUnderTest.portions().size() ) );
      for ( int i  = 0; i < duplicate.portions().size(); i++ ) {
         assertThatPortionsAreDuplicate( duplicate.portions().get( i ), systemUnderTest.portions().get( i ) );
      }
   }//End Method
   
   private void assertThatPortionsAreDuplicate( FoodPortion portion1, FoodPortion portion2 ) {
      assertFalse( portion1 == portion2 );
      assertThat( portion1.portion().get(), is( portion2.portion().get() ) );
      assertTrue( portion1.food().get() == portion2.food().get() );
   }//End Method
   
   @Test public void shouldSwapPortions(){
      systemUnderTest.portions().add( portion1 );
      systemUnderTest.portions().add( portion2 );
      assertThat( systemUnderTest.portions(), contains( portion1, portion2 ) );
      
      systemUnderTest.swap( portion1, portion2 );
      assertThat( systemUnderTest.portions(), contains( portion2, portion1 ) );
   }//End Method
   
   @Test public void shouldNotSwapIfNotPresent(){
      systemUnderTest.portions().add( portion1 );
      systemUnderTest.portions().add( portion2 );
      assertThat( systemUnderTest.portions(), contains( portion1, portion2 ) );
      
      systemUnderTest.swap( portion1, mock( FoodPortion.class ) );
      assertThat( systemUnderTest.portions(), contains( portion1, portion2 ) );
      
      systemUnderTest.swap( portion2, mock( FoodPortion.class ) );
      assertThat( systemUnderTest.portions(), contains( portion1, portion2 ) );
   }//End Method

}//End Class
