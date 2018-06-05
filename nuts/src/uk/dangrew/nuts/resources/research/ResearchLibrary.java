package uk.dangrew.nuts.resources.research;

import uk.dangrew.kode.utility.io.IoCommon;
import uk.dangrew.nuts.research.ResearchArticle;
import uk.dangrew.nuts.research.ResearchArticleStore;

public class ResearchLibrary {
   
   static final String TEXT_FILE = ".txt";
   static final String ARTICLE_CONTROLLING_INSULIN = "01-06-18: Controlling Insulin";
   
   private final IoCommon ioCommon;
   
   public ResearchLibrary() {
      this( new IoCommon() );
   }//End Constructor
   
   public ResearchLibrary( IoCommon ioCommon ) {
      this.ioCommon = ioCommon;
   }//End Constructor
   
   String fileName( String articleName ) {
      return articleName + TEXT_FILE;
   }//End Method

   public void loadResearch( ResearchArticleStore store ) {
      String controllingInsulinText = ioCommon.readFileIntoString( ResearchLibrary.class, fileName( ARTICLE_CONTROLLING_INSULIN ) );
      ResearchArticle controllingInsulin = store.createConcept( ARTICLE_CONTROLLING_INSULIN );
      controllingInsulin.content().set( controllingInsulinText );
   }//End Method
   
}//End Class
