package uk.dangrew.nuts.graphics.selection.model;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.ModelVerifier;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.database.FoodTypes;
import uk.dangrew.nuts.label.Label;
import uk.dangrew.nuts.store.Database;

public class FoodFilterModelTest {

   private Database database;
   private FoodFilterModel systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      database = new Database();
      database.foodItems().createConcept( "Food1" );
      database.foodItems().createConcept( "Food2" );
      database.meals().createConcept( "Meal1" );
      database.meals().createConcept( "Meal2" );
      systemUnderTest = new FoodFilterModel( database );
   }//End Method

   @Test public void shouldProvideFoods() {
      List< Food > foods = new ArrayList<>();
      for ( FoodTypes type : FoodTypes.values() ) {
         foods.addAll( type.redirect( database ).objectList() );
      }
      for ( Food food : foods ) {
         assertThat( systemUnderTest.databaseConcepts().options().contains( food ), is( true ) );
      }
   }//End Method
   
   @Test public void shouldProvideSelectionConfiguration(){
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideCollection( FoodFilterModel::filters, FoodFilters.Labels )
         .shouldProvideCollection( FoodFilterModel::labels, new Label( "Label" ) );
   }//End Method

}//End Class
