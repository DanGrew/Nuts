package uk.dangrew.nuts.persistence.fooditems;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.Test;

import uk.dangrew.jupa.file.protocol.JarJsonPersistingProtocol;
import uk.dangrew.jupa.json.io.JsonIO;
import uk.dangrew.nuts.persistence.goal.GoalParseModelTest;
import uk.dangrew.nuts.store.Database;

public class FoodSessionsTest {

   @Test public void shouldAcceptMissingOptionalData() throws URISyntaxException{
      Database database = new Database();
      JarJsonPersistingProtocol foodItemsLocation = mock( JarJsonPersistingProtocol.class );
      when( foodItemsLocation.readFromLocation() ).thenReturn( 
               new JsonIO().read( new File( getClass().getResource( "food-items.txt" ).toURI() ) 
      ) );
      
      FoodSessions sessions = new FoodSessions( 
               database,
               foodItemsLocation,
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class )
      );
      sessions.read();
      assertThat( database.foodItems().objectList(), is( not( empty() ) ) );
   }//End Method
   
   @Test public void shouldReadSingleGoalData() throws URISyntaxException{
      Database database = new Database();
      JarJsonPersistingProtocol goalLocation = mock( JarJsonPersistingProtocol.class );
      when( goalLocation.readFromLocation() ).thenReturn( 
               new JsonIO().read( new File( getClass().getResource( "goal-no-id-name.txt" ).toURI() ) 
      ) );
      
      FoodSessions sessions = new FoodSessions( 
               database,
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               goalLocation
      );
      sessions.read();
      assertThat( database.goals().objectList(), is( not( empty() ) ) );
      assertThat( database.templates().defaultGoal().properties().nameProperty().get(), is( GoalParseModelTest.SINGLETON_GOAL_DEFAULT_NAME ) );
   }//End Method
   
   @Test public void untested() {
      System.out.println( "WARNING: UNTESTED - " + getClass().getName() );
   }//End Method

}//End Class
