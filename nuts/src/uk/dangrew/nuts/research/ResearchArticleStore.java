package uk.dangrew.nuts.research;

import uk.dangrew.kode.concept.ConceptStore;
import uk.dangrew.kode.storage.structure.MappedObservableStoreManagerImpl;

public class ResearchArticleStore extends MappedObservableStoreManagerImpl< String, ResearchArticle > implements ConceptStore< ResearchArticle > {

   public ResearchArticleStore() {
      super( f -> f.properties().id() );
   }//End Constructor

   @Override public ResearchArticle createConcept( String name ) {
      ResearchArticle label = new ResearchArticle( name );
      store( label );
      return label;
   }//End Method
   
   @Override public ResearchArticle createConcept( String id, String name ) {
      ResearchArticle label = new ResearchArticle( id, name );
      store( label );
      return label;
   }//End Method
   
   @Override public void store( ResearchArticle label ) {
      super.store( label );
   }//End Method
   
   @Override public void removeConcept( ResearchArticle label ) {
      remove( label.properties().id() );
   }//End Method

}//End Class
