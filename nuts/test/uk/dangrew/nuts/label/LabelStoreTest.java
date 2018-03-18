package uk.dangrew.nuts.label;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class LabelStoreTest {

   private Label label;
   private LabelStore systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      label = new Label( "label" );
      systemUnderTest = new LabelStore();
   }//End Method

   @Test public void shouldStoreById() {
      assertThat( systemUnderTest.get( label.properties().id() ), is( nullValue() ) );
      systemUnderTest.store( label );
      assertThat( systemUnderTest.get( label.properties().id() ), is( label ) );
   }//End Method
   
   @Test public void shouldCreateNew() {
      Label newlabel = systemUnderTest.createConcept( "NewName" );
      assertThat( systemUnderTest.get( newlabel.properties().id() ), is( newlabel ) );
   }//End Method
   
   @Test public void shouldRemoveExisting() {
      systemUnderTest.store( label );
      assertThat( systemUnderTest.get( label.properties().id() ), is( label ) );
      systemUnderTest.removeConcept( label );
      assertThat( systemUnderTest.get( label.properties().id() ), is( nullValue() ) );
   }//End Method
   
}//End Class
