package uk.dangrew.nuts.graphics.tutorial.database;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.scene.control.Button;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorialSelector;

public class DatabaseTutorialOptionBuilderTest {

   private DatabaseComponents components;
   @Mock private TutorialSelector selector;
   private DatabaseTutorialOptionBuilder systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      components = new DatabaseComponents()
               .withMainTable( mock( ConceptTable.class ) )
               .withMainTableAddButton( mock( Button.class ) );
      systemUnderTest = new DatabaseTutorialOptionBuilder( selector );
   }//End Method

   @Test public void shouldDistinguishOptions() {
      assertThat( 
               systemUnderTest.optionsFor( components, components.mainTable() ).get(), 
               is( not( systemUnderTest.optionsFor( components, components.mainTableAddButton() ).get() ) ) 
      );
   }//End Method
   
   @Test public void shouldCacheOptions() {
      assertThat( 
               systemUnderTest.optionsFor( components, components.mainTable() ).get(), 
               is( systemUnderTest.optionsFor( components, components.mainTable() ).get() ) 
      );
   }//End Method
   
   @Test public void shouldProvideEmptyForNoMatch(){
      assertThat( systemUnderTest.optionsFor( components, components.parent() ), is( Optional.empty() ) );
   }//End Method
   
   @Test public void shouldProvideGridForMainTable() {
      assertThat( systemUnderTest.optionsFor( components, components.mainTable() ).get(), is( notNullValue() ) );
   }//End Method
   
   @Test public void shouldProvideGridForMainTableAddButton() {
      assertThat( systemUnderTest.optionsFor( components, components.mainTableAddButton() ).get(), is( notNullValue() ) );
   }//End Method

}//End Class
