package uk.dangrew.nuts.graphics.tutorial.database;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorialGlass;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorialSelector;

public class DatabaseTutorialsTest {

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
   }//End Method

   @Test public void shouldProvideDescriptions() {
      for ( DatabaseTutorials tutorial : DatabaseTutorials.values() ) {
         assertThat( tutorial.description(), is( notNullValue() ) );
      }
   }//End Method
   
   @Test public void shouldGenerateTutorials() {
      DatabaseComponents components = new DatabaseComponents();
      TutorialGlass glass = mock( TutorialGlass.class );
      TutorialSelector selector = mock( TutorialSelector.class );
      for ( DatabaseTutorials tutorial : DatabaseTutorials.values() ) {
         assertThat( tutorial.generate( components, glass, selector ), is( notNullValue() ) );
      }
   }//End Method

}//End Class
