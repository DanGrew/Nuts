package uk.dangrew.nuts.persistence.labels;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.label.Label;
import uk.dangrew.nuts.label.LabelStore;

public class LabelWriteModelTest {

   private LabelStore labels;
   private LabelWriteModel systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      labels = new LabelStore();
      systemUnderTest = new LabelWriteModel( labels );
   }//End Method

   @Test public void shouldNotWriteEmptyReferences() {
      Label label = labels.createConcept( "Label" );
      label.concepts().add( null );
      label.concepts().add( new FoodItem( "Food" ) );
      
      systemUnderTest.startWritingLabels();
      systemUnderTest.startWritingLabel();
      systemUnderTest.startWritingConcepts();
      assertThat( systemUnderTest.getConceptId(), is( label.concepts().get( 1 ).properties().id() ) );
   }//End Method

}//End Class
