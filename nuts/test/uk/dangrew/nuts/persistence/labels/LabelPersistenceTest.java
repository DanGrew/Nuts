package uk.dangrew.nuts.persistence.labels;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.json.JSONObject;
import org.junit.Test;

import uk.dangrew.kode.TestCommon;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.label.Label;
import uk.dangrew.nuts.persistence.fooditems.FoodItemPersistence;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.system.Concept;

public class LabelPersistenceTest {

   @Test public void shouldReadData() {
      Database database = new Database();
      
      FoodItemPersistence foodItemPersistence = new FoodItemPersistence( database );
      String value = TestCommon.readFileIntoString( getClass(), "food-items.txt" );
      JSONObject json = new JSONObject( value );
      foodItemPersistence.readHandles().parse( json );
      
      LabelPersistence persistence = new LabelPersistence( database );
      
      value = TestCommon.readFileIntoString( getClass(), "labels.txt" );
      json = new JSONObject( value );
      persistence.readHandles().parse( json );
      
      Label label = database.labels().objectList().get( 0 );
      assertLabelProperties( 
               label, "99987", "Label1", 
               "12345",
               "67890",
               "3421" 
      );
      label = database.labels().objectList().get( 1 );
      assertLabelProperties( 
               label, "556676", "Label2", 
               "67890",
               "3421",
               "1324"
      );
      label = database.labels().objectList().get( 2 );
      assertLabelProperties( 
               label, "8878886", "Label3", 
               "3421"
      );
   }//End Method
   
   @Test public void shouldWriteData(){
      Database database = new Database();
      
      FoodItem item1 = new FoodItem( "12345", "Food1" );
      item1.properties().setMacros( 45, 3.4, 98.1 );
      database.foodItems().store( item1 );
      
      FoodItem item2 = new FoodItem( "67890", "Food2" );
      item2.properties().setMacros( 2.11, 0.56, 123 );
      database.foodItems().store( item2 );
      
      FoodItem item3 = new FoodItem( "3421", "Food3" );
      item3.properties().setMacros( 2.3, 3.8, 8.6 );
      database.foodItems().store( item3 );
      
      FoodItem item4 = new FoodItem( "1324", "Food4" );
      item4.properties().setMacros( 0.1, 1.1, 0.3 );
      database.foodItems().store( item4 );
      
      Label label1 = new Label( "99987", "Label1" );
      label1.concepts().add( item1 );
      label1.concepts().add( item2 );
      label1.concepts().add( item3 );
      database.labels().store( label1 );
      
      Label label2 = new Label( "556676", "Label2" );
      label2.concepts().add( item2 );
      label2.concepts().add( item3 );
      label2.concepts().add( item4 );
      database.labels().store( label2 );
      
      Label label3 = new Label( "8878886", "Label3" );
      label3.concepts().add( item3 );
      database.labels().store( label3 );
      
      FoodItemPersistence foodItemPersistence = new FoodItemPersistence( database );
      JSONObject foodItemJson = new JSONObject();
      foodItemPersistence.structure().build( foodItemJson );
      foodItemPersistence.writeHandles().parse( foodItemJson );
      
      LabelPersistence persistence = new LabelPersistence( database );
      JSONObject json = new JSONObject();
      persistence.structure().build( json );
      persistence.writeHandles().parse( json );
      
      System.out.println( json );
      
      database = new Database();
      foodItemPersistence = new FoodItemPersistence( database );
      
      assertThat( database.foodItems().objectList(), is( empty() ) );
      foodItemPersistence.readHandles().parse( foodItemJson );
      assertThat( database.foodItems().objectList(), hasSize( 4 ) );
      
      persistence = new LabelPersistence( database );
      
      assertThat( database.labels().objectList(), is( empty() ) );
      persistence.readHandles().parse( json );
      assertThat( database.labels().objectList(), hasSize( 3 ) );
      
      Label label = database.labels().objectList().get( 0 );
      assertLabelProperties( label, label1 );
      label = database.labels().objectList().get( 1 );
      assertLabelProperties( label, label2 );
      label = database.labels().objectList().get( 2 );
      assertLabelProperties( label, label3 );
   }//End Method
   
   private void assertLabelProperties(
            Label label, 
            String id, String name,
            String... concepts
   ){
      assertThat( label.properties().id(), is( id ) );
      assertThat( label.properties().nameProperty().get(), is( name ) );
      assertThat( label.concepts(), hasSize( concepts.length ) );
      
      for ( int i = 0; i < concepts.length; i++ ) {
         String expected = concepts[ i ];
         Concept concept = label.concepts().get( i );
         assertThat( expected, is( concept.properties().id() ) );
      }
   }//End Method
   
   private void assertLabelProperties(
            Label label, Label expected
   ){
      assertThat( label.properties().id(), is( expected.properties().id() ) );
      assertThat( label.properties().nameProperty().get(), is( expected.properties().nameProperty().get() ) );
      assertThat( label.concepts(), hasSize( expected.concepts().size() ) );
      
      for ( int i = 0; i < expected.concepts().size(); i++ ) {
         Concept expectedConcept = expected.concepts().get( i );
         Concept concept = label.concepts().get( i );
         assertThat( concept.properties().id(), is( expectedConcept.properties().id() ) );
      }
   }//End Method
}//End Class