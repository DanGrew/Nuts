package uk.dangrew.nuts.graphics.food;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;

public class FoodStringConverterTest {

   private ObservableList< Food > foods;
   private FoodStringConverter systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      foods = FXCollections.observableArrayList( 
               new FoodItem( "Beans" ),
               new FoodItem( "Sausages" ),
               new FoodItem( "Chicken" )
      );
      systemUnderTest = new FoodStringConverter( foods );
   }//End Method

   @Test public void shouldIdentifyFood() {
      for ( Food food : foods ) {
         assertThat( systemUnderTest.fromString( food.properties().nameProperty().get() ), is( food ) );
      }
   }//End Method
   
   @Test public void shouldConvertFood() {
      for ( Food food : foods ) {
         assertThat( systemUnderTest.toString( food ), is( food.properties().nameProperty().get() ) );
      }
   }//End Method

}//End Class
