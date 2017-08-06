package uk.dangrew.nuts.graphics.shopping;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.sd.graphics.launch.TestApplication;

public class ShoppingListViewModelTest {

   private FoodItem food1;
   private FoodItem food2;
   
   private Meal list;
   private ShoppingListTable table;
   private ShoppingListViewModel systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      list = new Meal( "List" );
      list.portions().addAll( 
               new FoodPortion( food1 = new FoodItem( "Belvitas" ), 100 ),
               new FoodPortion( food2 = new FoodItem( "Biscuits" ), 100 )
      );
      food1.stockProperties().setWeighting( 100, 500 );
      food2.stockProperties().setWeighting( 30, 210 );
      
      systemUnderTest = new ShoppingListViewModel();
      table = new ShoppingListTable( list, systemUnderTest );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void shouldNotAllowMultipleAssociations(){
      systemUnderTest.associate( table, list );
   }//End Method

   @Test public void shouldAddRowPerFoodItemInShoppingList() {
      assertThat( table.getRows(), hasSize( 2 ) );
      assertRowsMatchStockUsage();
   }//End Method
   
   @Test public void shouldRemoveRowWhenRemovedFromShoppingList() {
      list.portions().remove( 0 );
      assertThat( table.getRows(), hasSize( 1 ) );
      assertRowsMatchStockUsage();
   }//End Method
   
   @Test public void shouldAddRowWhenAddedToShoppingList() {
      list.portions().add( new FoodPortion( new FoodItem( "Eggs" ), 100 ) );
      assertRowsMatchStockUsage();
   }//End Method
   
   @Test public void shouldUpdateRowRequiredWeightWhenListUsageChanges() {
      assertRowsMatchStockUsage();
      list.portions().get( 0 ).setPortion( 50 );
      assertRowsMatchStockUsage();
   }//End Method
   
   /**
    * Method to assert that the {@link uk.dangrew.nuts.meal.StockUsage} matches the content of the table.
    */
   private void assertRowsMatchStockUsage(){
      assertThat( table.getRows(), hasSize( list.stockUsage().totalWeightUsed().size() ) );
      
      for ( int i = 0 ; i < table.getRows().size(); i++ ) {
         ShoppingListRow row = table.getRows().get( i );
         assertThat( list.stockUsage().totalWeightUsed().containsKey( row.food() ), is( notNullValue() ) );
         assertThat( row.requiredWeight().get(), is( list.stockUsage().totalWeightUsed().get( row.food() ) ) );
         double toBuy = Math.ceil( list.stockUsage().stockPortionUsed().get( row.food() ) / 100.0 );
         assertThat( row.toBuy().get(), is( toBuy )  );
      }
   }//End Method

}//End Class
