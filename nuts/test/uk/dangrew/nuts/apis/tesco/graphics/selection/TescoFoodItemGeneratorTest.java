package uk.dangrew.nuts.apis.tesco.graphics.selection;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.apis.tesco.item.CalculatedNutrition;
import uk.dangrew.nuts.apis.tesco.item.TescoFoodDescription;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;

public class TescoFoodItemGeneratorTest {

   private TescoFoodDescription description;
   private TescoFoodItemGenerator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      description = new TescoFoodDescription( "Food" );
      systemUnderTest = new TescoFoodItemGenerator();
   }//End Method

   @Test public void shouldConstructPer100ItemIfDataPresent() {
      String groceryName = "Some elaborate name";
      double kcal = 101.3;
      double carbs = 20.7;
      double fat = 1.3;
      double protein = 35.9;
      
      description.groceryProperties().name().set( groceryName );
      CalculatedNutrition nutrition = description.productDetail().nutrition();
      nutrition.energyInKcal().valuePer100().set( Double.toString( kcal ) );
      nutrition.carbohydrates().valuePer100().set( Double.toString( carbs ) );
      nutrition.fat().valuePer100().set( Double.toString( fat ) );
      nutrition.protein().valuePer100().set( Double.toString( protein ) );
      
      List< Food > items = systemUnderTest.generateFoodItemsFor( description );
      
      FoodItem first = ( FoodItem )items.get( 0 );
      assertThat( first.properties().nameProperty().get(), is( 
               groceryName + TescoFoodItemGenerator.PER_100_SUFFIX 
      ) );
      assertThat( first.properties().calories().get(), is( kcal ) );
      assertThat( first.properties().carbohydrates().get(), is( carbs ) );
      assertThat( first.properties().fats().get(), is( fat ) );
      assertThat( first.properties().protein().get(), is( protein ) );
   }//End Method
   
   @Test public void shouldNotConstructPer100ItemIfNameNotPresent() {
      assertThat( systemUnderTest.generateFoodItemsFor( description ), is( empty() ) );
      
      description.groceryProperties().name().set( "anything" );
      List< Food > items = systemUnderTest.generateFoodItemsFor( description );
      assertThat( items, is( not( empty() ) ) );
      
      FoodItem first = ( FoodItem )items.get( 0 );
      assertThat( first.properties().calories().get(), is( 0.0 ) );
      assertThat( first.properties().carbohydrates().get(), is( 0.0 ) );
      assertThat( first.properties().fats().get(), is( 0.0 ) );
      assertThat( first.properties().protein().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldConstructPer100ItemEvenIfDataInvalid() {
      String groceryName = "Some elaborate name";
      description.groceryProperties().name().set( groceryName );
      
      CalculatedNutrition nutrition = description.productDetail().nutrition();
      nutrition.energyInKcal().valuePer100().set( "unparsable" );
      nutrition.carbohydrates().valuePer100().set( "unparsable" );
      nutrition.fat().valuePer100().set( "unparsable" );
      nutrition.protein().valuePer100().set( "unparsable" );
      
      List< Food > items = systemUnderTest.generateFoodItemsFor( description );
      
      FoodItem first = ( FoodItem )items.get( 0 );
      assertThat( first.properties().calories().get(), is( 0.0 ) );
      assertThat( first.properties().carbohydrates().get(), is( 0.0 ) );
      assertThat( first.properties().fats().get(), is( 0.0 ) );
      assertThat( first.properties().protein().get(), is( 0.0 ) );
   }//End Method
   
}//End Class
