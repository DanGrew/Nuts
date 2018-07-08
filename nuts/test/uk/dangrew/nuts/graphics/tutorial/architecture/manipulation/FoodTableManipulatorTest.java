package uk.dangrew.nuts.graphics.tutorial.architecture.manipulation;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.graphics.food.FoodTableColumns;
import uk.dangrew.nuts.graphics.food.GeneralConceptTableController;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.store.Database;

public class FoodTableManipulatorTest {

   private static final String EGG = "Egg";
   private static final String CREAM = "Cream";
   private static final String CHEESE = "Cheese";
   
   private Database database;
   private ConceptTable< FoodItem > table;
   private FoodTableManipulator< FoodItem > systemUnderTest;

   @Before public void initialiseSystemUnderTest() throws InterruptedException {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      database = new Database();
      TestApplication.launch( () -> table = new TableComponents< FoodItem >()
               .withDatabase( database )
               .applyColumns( FoodTableColumns< FoodItem >::new )
               .withController( new GeneralConceptTableController<>( database.foodItems() ) )
               .buildTable()
      );
      systemUnderTest = new FoodTableManipulator<>( table );
   }//End Method

   @Test public void shouldFindFirstRowWithName() {
      FoodItem egg = database.foodItems().createConcept( EGG );
      FoodItem cream = database.foodItems().createConcept( CREAM );
      FoodItem cheese = database.foodItems().createConcept( CHEESE );
      
      PlatformImpl.runAndWait( () -> {} );
      
      assertThat( systemUnderTest.findRow( EGG ).concept(), is( egg ) );
      assertThat( systemUnderTest.findRow( CREAM ).concept(), is( cream ) );
      assertThat( systemUnderTest.findRow( CHEESE ).concept(), is( cheese ) );
      
      assertThat( systemUnderTest.findRow( EGG ).index(), is( 0 ) );
      assertThat( systemUnderTest.findRow( CREAM ).index(), is( 1 ) );
      assertThat( systemUnderTest.findRow( CHEESE ).index(), is( 2 ) );
   }//End Method
   
   @Test public void shouldFindArbitraryRowWithName() {
      FoodItem egg1 = database.foodItems().createConcept( EGG );
      FoodItem egg2 = database.foodItems().createConcept( EGG );
      FoodItem egg3 = database.foodItems().createConcept( EGG );
      
      PlatformImpl.runAndWait( () -> {} );
      
      assertThat( systemUnderTest.findRow( EGG ).concept(), is( egg1 ) );
      assertThat( systemUnderTest.findRow( EGG, 1 ).concept(), is( egg1 ) );
      assertThat( systemUnderTest.findRow( EGG, 2 ).concept(), is( egg2 ) );
      assertThat( systemUnderTest.findRow( EGG, 3 ).concept(), is( egg3 ) );
      
      assertThat( systemUnderTest.findRow( EGG ).index(), is( 0 ) );
      assertThat( systemUnderTest.findRow( EGG, 1 ).index(), is( 0 ) );
      assertThat( systemUnderTest.findRow( EGG, 2 ).index(), is( 1 ) );
      assertThat( systemUnderTest.findRow( EGG, 3 ).index(), is( 2 ) );
   }//End Method
   
   @Test public void shouldMapConcepts(){
      FoodItem egg = database.foodItems().createConcept( EGG );
      FoodItem cream = database.foodItems().createConcept( CREAM );
      
      systemUnderTest = new FoodTableManipulator<>( table, f -> cream );
      
      PlatformImpl.runAndWait( () -> {} );
      
      assertThat( systemUnderTest.findRow( EGG ), is( nullValue() ) );
      assertThat( systemUnderTest.findRow( CREAM ).concept(), is( egg ) );
   }//End Method

}//End Class
