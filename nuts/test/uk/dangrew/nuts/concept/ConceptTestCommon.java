package uk.dangrew.nuts.concept;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import uk.dangrew.kode.comparator.Comparators;
import uk.dangrew.nuts.system.Concept;

public class ConceptTestCommon {

   public static void assertInAlphabeticalOrder( Collection< Concept > concepts ) {
      List< String > namesInCollectionOrder = concepts.stream()
               .map( c -> c.properties().nameProperty().get() )
               .collect( Collectors.toList() );
      
      List< String > alphabeticalOrder = new ArrayList<>( namesInCollectionOrder );
      alphabeticalOrder.sort( Comparators.STRING_ALPHABETICAL );
      
      for( int i = 0; i < namesInCollectionOrder.size(); i++ ) {
         assertThat( namesInCollectionOrder.get( i ), is( alphabeticalOrder.get( i ) ) );
      }
   }//End Method
   
}//End Class
