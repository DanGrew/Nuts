package uk.dangrew.nuts.persistence.settings;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.json.JSONObject;
import org.junit.Test;

import uk.dangrew.jupa.file.protocol.WorkspaceJsonPersistingProtocol;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.persistence.fooditems.DatabaseIo;
import uk.dangrew.nuts.store.Database;

public class SettingsPersistenceTest {

   @Test public void shouldReadData() {
      Database database = new Database();
      new DatabaseIo( database )
         .withSettings( new WorkspaceJsonPersistingProtocol( "settings.txt", getClass() ) )
         .read();
      
      assertThat( database.settings().showingPropertyFor( NutritionalUnit.Calories ).get(), is( true ) );
      assertThat( database.settings().showingPropertyFor( NutritionalUnit.Carbohydrate ).get(), is( false ) );
      assertThat( database.settings().showingPropertyFor( NutritionalUnit.Fat ).get(), is( false ) );
      assertThat( database.settings().showingPropertyFor( NutritionalUnit.Protein ).get(), is( true ) );
      assertThat( database.settings().showingPropertyFor( NutritionalUnit.Fibre ).get(), is( false ) );
   }//End Method
   
   @Test public void shouldWriteData(){
      Database database = new Database();
      
      database.settings().showingPropertyFor( NutritionalUnit.Calories ).set( false );
      database.settings().showingPropertyFor( NutritionalUnit.Carbohydrate ).set( false );
      database.settings().showingPropertyFor( NutritionalUnit.Fat ).set( false );
      database.settings().showingPropertyFor( NutritionalUnit.Protein ).set( true );
      database.settings().showingPropertyFor( NutritionalUnit.Fibre ).set( false );
      
      SettingsPersistence persistence = new SettingsPersistence( database.settings() );
      JSONObject json = new JSONObject();
      persistence.structure().build( json );
      persistence.writeHandles().parse( json );
      
      System.out.println( json.toString() );
      
      database = new Database();
      persistence = new SettingsPersistence( database.settings() );
      persistence.readHandles().parse( json );
      
      assertThat( database.settings().showingPropertyFor( NutritionalUnit.Calories ).get(), is( false ) );
      assertThat( database.settings().showingPropertyFor( NutritionalUnit.Carbohydrate ).get(), is( false ) );
      assertThat( database.settings().showingPropertyFor( NutritionalUnit.Fat ).get(), is( false ) );
      assertThat( database.settings().showingPropertyFor( NutritionalUnit.Protein ).get(), is( true ) );
      assertThat( database.settings().showingPropertyFor( NutritionalUnit.Fibre ).get(), is( false ) );
   }//End Method

}//End Class
