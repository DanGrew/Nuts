package uk.dangrew.nuts.persistence.fooditems;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.net.URISyntaxException;

import org.junit.Test;

import uk.dangrew.jupa.file.protocol.JarJsonPersistingProtocol;
import uk.dangrew.jupa.file.protocol.WorkspaceJsonPersistingProtocol;
import uk.dangrew.nuts.persistence.goal.GoalParseModelTest;
import uk.dangrew.nuts.store.Database;

public class FoodSessionsTest {

   @Test public void shouldAcceptMissingOptionalData() throws URISyntaxException{
      Database database = new Database();
      FoodSessions sessions = new FoodSessions( 
               database,
               mock( JarJsonPersistingProtocol.class ),
               new WorkspaceJsonPersistingProtocol( "food-items.txt", getClass() ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class )
      );
      sessions.databaseIo().read();

      assertThat( database.foodItems().objectList(), is( not( empty() ) ) );
   }//End Method
   
   @Test public void shouldReadSingleGoalData() throws URISyntaxException{
      Database database = new Database();
      FoodSessions sessions = new FoodSessions( 
               database,
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               new WorkspaceJsonPersistingProtocol( "goal-no-id-name.txt", getClass() ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class ),
               mock( JarJsonPersistingProtocol.class )
      );
      sessions.databaseIo().read();
      assertThat( database.calorieGoals().objectList(), is( not( empty() ) ) );
      assertThat( database.templates().defaultGoal().properties().nameProperty().get(), is( GoalParseModelTest.SINGLETON_GOAL_DEFAULT_NAME ) );
   }//End Method
   
   @Test public void untested() {
      System.out.println( "WARNING: UNTESTED - " + getClass().getName() );
   }//End Method

}//End Class
