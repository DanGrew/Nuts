package uk.dangrew.nuts.graphics.selection.model;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.ModelVerifier;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionFilters;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionModel;
import uk.dangrew.nuts.label.Label;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

public class FoodSelectionModelTest {

   private Database database;
   private FoodSelectionModel systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      database = new Database();
      database.foodItems().createConcept( "Food1" );
      database.foodItems().createConcept( "Food2" );
      database.meals().createConcept( "Meal1" );
      database.meals().createConcept( "Meal2" );
      systemUnderTest = new FoodSelectionModel( database );
   }//End Method

   @Test public void shouldProvideFoods() {
      for ( FoodItem item : database.foodItems().objectList() ) {
         assertThat( systemUnderTest.databaseConcepts().options().contains( item ), is( true ) );
      }
      for ( Meal meal : database.meals().objectList() ) {
         assertThat( systemUnderTest.databaseConcepts().options().contains( meal ), is( true ) );
      }
   }//End Method
   
   @Test public void shouldProvideSelectionConfiguration(){
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideCollection( FoodSelectionModel::filters, FoodSelectionFilters.Labels )
         .shouldProvideCollection( FoodSelectionModel::labels, new Label( "Label" ) );
   }//End Method

}//End Class
