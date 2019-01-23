package uk.dangrew.nuts.food;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import uk.dangrew.kode.concept.Properties;
import uk.dangrew.nuts.nutrients.Nutrition;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class FoodItemTest {

   private Properties properties;
   private Nutrition nutrition;
   private StockProperties stockProperties;
   private FoodAnalytics foodAnalytics;
   @Spy private MacroRatioCalculator ratioCalculator;
   private FoodItem systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      properties = new Properties( "anything" );
      nutrition = new Nutrition();
      stockProperties = new StockProperties();
      foodAnalytics = new FoodAnalytics();
      systemUnderTest = new FoodItem( 
               properties, 
               nutrition,
               stockProperties,
               foodAnalytics, 
               ratioCalculator 
      );
   }//End Method
   
   @Test public void shouldCreateWithId(){
      systemUnderTest = new FoodItem( "3487653", "skdjnvs." );
      assertThat( systemUnderTest.properties().id(), is( "3487653" ) );
      assertThat( systemUnderTest.properties().nameProperty().get(), is( "skdjnvs." ) );
   }//End Method

   @Test public void shouldProvideNutrition(){
      assertThat( systemUnderTest.properties(), is( properties ) );
      assertThat( systemUnderTest.nutrition(), is( nutrition ) );
   }//End Method
   
   @Test public void shouldProvideStockProperties(){
      assertThat( systemUnderTest.stockProperties(), is( stockProperties ) );
   }//End Method
   
   @Test public void shouldProvideFoodAnalytics(){
      assertThat( systemUnderTest.foodAnalytics(), is( foodAnalytics ) );
   }//End Method
   
   @Test public void shouldAssociateRatioCalculator(){
      verify( ratioCalculator ).associate( nutrition, foodAnalytics );
   }//End Method
   
   @Test public void shouldDuplicate(){
      systemUnderTest.nutrition().of( NutritionalUnit.Calories ).set( 100.0 );
      systemUnderTest.nutrition().of( NutritionalUnit.Fibre ).set( 0.567 );
      systemUnderTest.nutrition().of( NutritionalUnit.Carbohydrate ).set( 101.0 );
      systemUnderTest.nutrition().of( NutritionalUnit.Fat ).set( 102.0 );
      systemUnderTest.nutrition().of( NutritionalUnit.Protein ).set( 103.0 );
      
      FoodItem duplicate = systemUnderTest.duplicate();
      assertTrue( duplicate != systemUnderTest );
      assertThat( duplicate.properties().id(), is( not( systemUnderTest.properties().id() ) ) );
      assertThat( duplicate.properties().nameProperty().get(), is( systemUnderTest.properties().nameProperty().get() + "-anything" ) );
      assertThat( duplicate.nutrition().of( NutritionalUnit.Calories ).get(), is( systemUnderTest.nutrition().of( NutritionalUnit.Calories ).get() ) );
      assertThat( duplicate.nutrition().of( NutritionalUnit.Fibre ).get(), is( systemUnderTest.nutrition().of( NutritionalUnit.Fibre ).get() ) );
      assertThat( duplicate.nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( systemUnderTest.nutrition().of( NutritionalUnit.Carbohydrate ).get() ) );
      assertThat( duplicate.nutrition().of( NutritionalUnit.Fat ).get(), is( systemUnderTest.nutrition().of( NutritionalUnit.Fat ).get() ) );
      assertThat( duplicate.nutrition().of( NutritionalUnit.Protein ).get(), is( systemUnderTest.nutrition().of( NutritionalUnit.Protein ).get() ) );
   }//End Method
   
}//End Class
