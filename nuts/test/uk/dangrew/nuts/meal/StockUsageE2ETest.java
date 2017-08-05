package uk.dangrew.nuts.meal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;

public class StockUsageE2ETest {

   private FoodItem rice;
   private FoodItem edemameBeans;
   private FoodItem peas;
   private FoodItem sweetcorn;
   
   private FoodItem chicken;
   private FoodItem tikkaSauce;
   
   private Meal riceAndBeans;
   private Meal chickenTikka;
   
   private Meal chickenTikkaWithRice;
   
   @Before public void initialiseSystemUnderTest(){
      rice = new FoodItem( "rice" );
      rice.stockProperties().setWeighting( 50, 1000 );
      edemameBeans = new FoodItem( "beans" );
      edemameBeans.stockProperties().setWeighting( 80, 500 );
      peas = new FoodItem( "peas" );
      peas.stockProperties().setWeighting( 100, 1000 );
      sweetcorn = new FoodItem( "sweetcorn" );
      sweetcorn.stockProperties().setWeighting( 100, 1000 );
      
      chicken = new FoodItem( "chicken" );
      chicken.stockProperties().setWeighting( 100, 480 );
      tikkaSauce = new FoodItem( "tikkaSauce" );
      tikkaSauce.stockProperties().setWeighting( 100, 500 );
      
      riceAndBeans = new Meal( "riceAndBeans" );
      riceAndBeans.portions().addAll( 
               new FoodPortion( rice, 100 ),
               new FoodPortion( edemameBeans, 125 )
      );
      
      chickenTikka = new Meal( "chickenTikka" );
      chickenTikka.portions().addAll( 
               new FoodPortion( chicken, 240 ),
               new FoodPortion( tikkaSauce, 250 )
      );
      
      chickenTikkaWithRice = new Meal( "chickenTikkaWithRice" );
      chickenTikkaWithRice.portions().addAll( 
               new FoodPortion( riceAndBeans, 100 ),
               new FoodPortion( chickenTikka, 100 )
      );
   }//End Method
   
   @Test public void shouldHaveCorrectStockUsage() {
      assertThatChickenTikkaHas( chicken, 50.0 );
      assertThatChickenTikkaHas( tikkaSauce, 50.0 );
      assertThatChickenTikkaHas( rice, 5.0 );
      assertThatChickenTikkaHas( edemameBeans, 20.0 );
      assertThat( chickenTikkaWithRice.stockUsage().stockPortionUsed().size(), is( 4 ) );
   }//End Method
   
   @Test public void shouldPopulateStockPropertiesThroughStructure(){
      rice.stockProperties().loggedWeight().set( 100.0 );
      assertThatChickenTikkaHas( rice, 10.0 );
      
      rice.stockProperties().soldInWeight().set( 500.0 );
      assertThatChickenTikkaHas( rice, 20.0 );
   }//End Method
   
   @Test public void shouldPopulateFoodThroughStructure(){
      riceAndBeans.portions().get( 1 ).setFood( peas );
      assertThatChickenTikkaHas( edemameBeans, null );
      assertThatChickenTikkaHas( peas, 12.5 );
      
      riceAndBeans.portions().add( new FoodPortion( sweetcorn, 200 ) );
      assertThatChickenTikkaHas( sweetcorn, 20.0 );
      
      riceAndBeans.portions().remove( 0 );
      assertThatChickenTikkaHas( rice, null );
      assertThatChickenTikkaHas( peas, 12.5 );
   }//End Method
   
   @Test public void shouldPopulatePortionsThroughStructure(){
      riceAndBeans.portions().get( 0 ).setPortion( 200 );
      assertThatChickenTikkaHas( rice, 10.0 );
      
      chickenTikka.portions().get( 1 ).setPortion( 300 );
      assertThatChickenTikkaHas( tikkaSauce, 60.0 );
      
      chickenTikkaWithRice.portions().get( 1 ).setPortion( 200 );
      assertThatChickenTikkaHas( chicken, 100.0 );
      assertThatChickenTikkaHas( tikkaSauce, 120.0 );
   }//End Method
   
   private void assertThatChickenTikkaHas( FoodItem item, Double stock ) {
      assertThat( chickenTikkaWithRice.stockUsage().stockPortionUsed().get( item ), is( stock ) );
   }//End Method

}//End Class
