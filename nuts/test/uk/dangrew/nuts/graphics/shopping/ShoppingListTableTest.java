package uk.dangrew.nuts.graphics.shopping;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.shopping.ShoppingList;
import uk.dangrew.sd.graphics.launch.TestApplication;

public class ShoppingListTableTest {

   private FoodItem food1;
   private FoodItem food2;
   
   private ShoppingList list;
   private ShoppingListTable systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      list = new ShoppingList( "anything" );
      list.portions().addAll( 
               new FoodPortion( food1 = new FoodItem( "Hash Browns" ), 100 ),
               new FoodPortion( food2 = new FoodItem( "Turkey Burgers" ), 200 )
      );
      food1.stockProperties().setWeighting( 375, 1000 );
      food2.stockProperties().setWeighting( 100, 400 );
      systemUnderTest = new ShoppingListTable( list );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> systemUnderTest );
      
      Thread.sleep( 99999999 );
   }//End Method
   
   @Test public void untested() {
      System.out.println( "WARNING: UNTESTED - " + getClass().getName() );
   }//End Method

}//End Class
